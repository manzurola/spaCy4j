package edu.guym.spacyj.clients.corenlp;

import edu.guym.spacyj.api.features.UdPos;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.TypedDependency;
import edu.guym.spacyj.api.SpacyClient;
import edu.guym.spacyj.api.containers.TokenData;
import edu.guym.spacyj.api.exceptions.SpacyException;
import edu.guym.spacyj.api.utils.PtbToUdPosMapper;

import java.util.*;

public class StanfordCoreNlpSpacyClient implements SpacyClient {

    private final StanfordCoreNLP pipeline;
    private final PtbToUdPosMapper udPosMapper;

    public StanfordCoreNlpSpacyClient() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,depparse");
        this.pipeline = new StanfordCoreNLP(props);
        this.udPosMapper = PtbToUdPosMapper.create();
    }

    public StanfordCoreNlpSpacyClient(StanfordCoreNLP pipeline) {
        this.pipeline = pipeline;
        this.udPosMapper = PtbToUdPosMapper.create();
    }

    @Override
    public List<TokenData> nlp(String text) throws SpacyException {
        CoreDocument document = new CoreDocument(text);
        pipeline.annotate(document);
        if (document.sentences().size() == 0) {
            return List.of();
        }
        return extractTokenData(document);
    }

    private List<TokenData> extractTokenData(CoreDocument document) {
        List<TokenData> words = new ArrayList<>();
        int tokenIndex = 0;
        for (CoreSentence sentence : document.sentences()) {
            int currentSentenceOffset = words.size(); // index start of current sentence
            boolean isSentenceStart = true;
            for (CoreLabel coreLabel : sentence.tokens()) {
                String tag = coreLabel.tag();
                String pos = udPosMapper.getUdPos(tag);
                String lemma = coreLabel.lemma();

                String dep = "";
                int head = -1;

                Optional<TypedDependency> coreNlpDependency = getCoreNlpDependency(coreLabel, sentence);
                if (coreNlpDependency.isPresent()) {
                    edu.stanford.nlp.trees.GrammaticalRelation grammaticalRelation = coreNlpDependency.get().reln();
                    dep = grammaticalRelation.getShortName();
                    head = currentSentenceOffset + adaptCoreLabelIndex(coreNlpDependency.get().gov().index());
                }

                String text = coreLabel.originalText();
                words.add(TokenData.builder()
                        .setText(text)
                        .setBefore(coreLabel.before())
                        .setAfter(coreLabel.after())
                        .setIndex(tokenIndex)
                        .setBeginOffset(coreLabel.beginPosition())
                        .setEndOffset(coreLabel.endPosition())
                        .setLemma(lemma)
                        .setTag(tag)
                        .setPos(pos)
                        .setHead(head)
                        .setDependency(dep)
                        .setSentenceStart(isSentenceStart)
                        .setIsAlpha(text.chars().allMatch(Character::isLetter))
                        .setIsPunct(UdPos.PUNCT.matches(pos))
                        .setLikeNum(UdPos.NUM.matches(pos))
                        .build());

                tokenIndex++;
                isSentenceStart = false;
            }
        }

        return words;
    }

    private Optional<TypedDependency> getCoreNlpDependency(CoreLabel label, CoreSentence coreSentence) {
        Collection<TypedDependency> typedDependencies = coreSentence.dependencyParse().typedDependencies();

        // there should be only one typed dependency where a label appears as a dependant
        Optional<TypedDependency> dependency = typedDependencies.stream()
                .filter(typedDependency -> typedDependency.dep().index() == label.index())
                .findFirst();

        if (dependency.isEmpty()) {
            return Optional.empty();
        }

        return dependency;
    }

    private int adaptCoreLabelIndex(int source) {
        return source - 1;
    }

}
