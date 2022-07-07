package io.github.manzurola.spacy4j.adapters.corenlp;

import io.github.manzurola.spacy4j.api.SpaCyAdapter;
import io.github.manzurola.spacy4j.api.containers.Doc;
import io.github.manzurola.spacy4j.api.containers.TokenData;
import io.github.manzurola.spacy4j.api.exceptions.SpaCyException;
import io.github.manzurola.spacy4j.api.features.Pos;
import io.github.manzurola.spacy4j.api.utils.PtbToUdPosMapper;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.trees.TypedDependency;

import java.util.*;

/**
 * Stanford CoreNLP Java client adapter. Loads in memory. Adapts the corenlp
 * tokens as if they were spaCy's: Index starts at 0, supports likeNum, isPunct
 * etc.
 */
public final class CoreNLPAdapter implements SpaCyAdapter {

    private final StanfordCoreNLP pipeline;
    private final PtbToUdPosMapper udPosMapper;

    private CoreNLPAdapter(StanfordCoreNLP pipeline) {
        this.pipeline = pipeline;
        this.udPosMapper = PtbToUdPosMapper.create();
    }

    /**
     * Create a CoreNlpAdapter with a StanfordCoreNLP configured with default
     * annotators {@code "tokenize, ssplit, pos, lemma, depparse"}.
     */
    public static CoreNLPAdapter forEnglish() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,depparse");
        return new CoreNLPAdapter(new StanfordCoreNLP(props));
    }

    /**
     * Create a CoreNlpAdapter with a custom StanfordCoreNLP pipeline.
     * <p>
     * Use at your own peril.
     */
    public static CoreNLPAdapter create(StanfordCoreNLP pipeline) {
        return new CoreNLPAdapter(pipeline);
    }

    @Override
    public final Doc nlp(String text) throws SpaCyException {
        CoreDocument document = new CoreDocument(text);
        pipeline.annotate(document);
        if (document.sentences().size() == 0) {
            return Doc.create(text, List.of());
        }
        List<TokenData> tokens = extractTokenData(document);
        return Doc.create(text, tokens);
    }

    private List<TokenData> extractTokenData(CoreDocument document) {
        List<TokenData> words = new ArrayList<>();
        int tokenIndex = 0;
        for (CoreSentence sentence : document.sentences()) {
            int currentSentenceOffset = words.size(); // index start of
            // current sentence
            boolean isSentenceStart = true;
            for (CoreLabel coreLabel : sentence.tokens()) {
                String tag = coreLabel.tag();
                String pos = udPosMapper.getUdPos(tag);
                String lemma = coreLabel.lemma();

                String dep = "";
                int head = -1;

                Optional<TypedDependency> coreNlpDependency =
                    getCoreNlpDependency(
                        coreLabel,
                        sentence
                    );
                if (coreNlpDependency.isPresent()) {
                    GrammaticalRelation grammaticalRelation = coreNlpDependency
                        .get()
                        .reln();
                    dep = grammaticalRelation.getShortName();
                    head = currentSentenceOffset +
                           adaptCoreLabelIndex(coreNlpDependency
                               .get()
                               .gov()
                               .index());
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
                    .setIsPunct(Pos.PUNCT.matches(pos))
                    .setLikeNum(Pos.NUM.matches(pos))
                    .build());

                tokenIndex++;
                isSentenceStart = false;
            }
        }

        return words;
    }

    private Optional<TypedDependency> getCoreNlpDependency(
        CoreLabel label,
        CoreSentence coreSentence
    ) {
        Collection<TypedDependency> typedDependencies = coreSentence
            .dependencyParse()
            .typedDependencies();

        // there should be only one typed dependency where a label appears as
        // a dependant
        Optional<TypedDependency> dependency = typedDependencies.stream()
            .filter(typedDependency -> typedDependency.dep().index() ==
                                       label.index())
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
