package edu.guym.spacyj.adapters.corenlp.corenlpexamples;

import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.trees.Tree;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Disabled
public class CoreNLPUsageExample {

    @Test
    public void constituents() {
        String text = "You don't write to them every day.";
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        CoreDocument document = new CoreDocument(text);
        pipeline.annotate(document);

        CoreSentence sentence = document.sentences().get(0);
        Tree tree = sentence.constituencyParse();
        tree.constituents();
        tree.children();
        tree.getLeaves();
        System.out.println(tree);
    }

    @Test
    public void testPipelineAnnotation() {

        String text = "My friend may be coming over later tonight.";

        // set up pipeline properties
        Properties props = new Properties();
        // set the list of annotators to run
        props.setProperty("annotators", "tokenize,ssplit,pos");
        // build pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // create a document object
        CoreDocument document = new CoreDocument(text);
        // annnotate the document
        pipeline.annotate(document);
        // examples

        CoreSentence sentence = document.sentences().get(0);

        // 10th token of the document
        for (CoreLabel token : sentence.tokens()) {
            System.out.println(String.format("word: '%s', ner: '%s', tag: '%s', index: %d, begin: %d, end: %d, before: '%s', whitespaceAfter: '%s'", token.word(), token.ner(), token.tag(), token.index(), token.beginPosition(), token.endPosition(), token.before(), token.after()));
        }

    }

    @Test
    public void testSimpleAPI() {
        Sentence sent = new Sentence("Lucy is in the sky with diamonds.");
        List<String> nerTags = sent.nerTags();  // [PERSON, O, O, O, O, O, O, O]
        String firstPOSTag = sent.posTag(0);   // NNP

        System.out.println(sent);
        System.out.println("read first sentence");

        Sentence sent2 = new Sentence("My friend Alon should be coming over later today.");
        System.out.println(sent2);
    }

    @Test
    public void testOpenIe() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,depparse,natlog,openie");
        props.setProperty("openie.max_entailments_per_clause", "1000");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        String text = "It was a long and stern face, but with eyes that twinkled in a kindly way.";

        CoreDocument document = new CoreDocument(text);
        pipeline.annotate(document);

        CoreSentence sentence = document.sentences().get(0);
        Collection<RelationTriple> relations = sentence
                .coreMap()
                .get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
//        List<RelationTriple> relations = sentence.relations();
        for (RelationTriple relation : relations) {
            System.out.println(relation);
        }
    }

    @Test
    public void pipelineUsageExampleAlwaysPasses() {
        String text = "This grammar is too difficult for a child to understand.";

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,depparse,natlog,openie");
        props.setProperty("coref.algorithm", "neural");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        CoreDocument document = new CoreDocument(text);
        pipeline.annotate(document);
        // examples

        // 10th token of the document
        CoreLabel token = document.tokens().get(0);
        System.out.println("Example: token");
        System.out.println(token);
        System.out.println();

        CoreSentence sentence = document.sentences().get(0);

        // text of sentence
        String sentenceText = sentence.text();
        System.out.println("Example: sentence");
        System.out.println(sentenceText);
        System.out.println();

        // list of the part-of-speech tags
        List<String> posTags = sentence.posTags();
        System.out.println("Example: pos tags");
        System.out.println(posTags);
        System.out.println();

        // list of the ner tags
        List<String> nerTags = sentence.nerTags();
        System.out.println("Example: ner tags");
        System.out.println(nerTags);
        System.out.println();

        // constituency parse
        Tree constituencyParse = sentence.constituencyParse();
        System.out.println("Example: constituency parse");
        System.out.println(constituencyParse);
        System.out.println();

        // dependency parse
        SemanticGraph dependencyParse = sentence.dependencyParse();
        System.out.println("Example: dependency parse");
        System.out.println(dependencyParse);
        System.out.println();

        // kbp relations
        List<RelationTriple> relations = sentence.relations();
        System.out.println("Example: relation");
        if (!relations.isEmpty())
            System.out.println(relations.get(0));
        System.out.println();

        // entity mentions
        List<CoreEntityMention> entityMentions = sentence.entityMentions();
        System.out.println("Example: entity mentions");
        System.out.println(entityMentions);
        System.out.println();

        // coreference between entity mentions
        if (!sentence.entityMentions().isEmpty()) {
            CoreEntityMention originalEntityMention = sentence.entityMentions().get(0);
            System.out.println("Example: original entity mention");
            System.out.println(originalEntityMention);
            if (originalEntityMention.canonicalEntityMention().isPresent()) {
                System.out.println("Example: canonical entity mention");
                System.out.println(originalEntityMention.canonicalEntityMention().get());
                System.out.println();
            }
        }

        // get document wide coref info
        Map<Integer, CorefChain> corefChains = document.corefChains();
        System.out.println("Example: coref chains for document");
        System.out.println(corefChains);
        System.out.println();

        // get quotes in document
        List<CoreQuote> quotes = document.quotes();
        if (!quotes.isEmpty()) {
            CoreQuote quote = quotes.get(0);
            System.out.println("Example: quote");
            System.out.println(quote);
            System.out.println();

            if (quote.speaker().isPresent()) {
                // original speaker of quote
                // note that quote.speaker() returns an Optional
                System.out.println("Example: original speaker of quote");
                System.out.println(quote.speaker().get());
                System.out.println();
            }

            if (quote.canonicalSpeaker().isPresent()) {
                // canonical speaker of quote
                System.out.println("Example: canonical speaker of quote");
                System.out.println(quote.canonicalSpeaker().get());
                System.out.println();
            }
        }
    }

}
