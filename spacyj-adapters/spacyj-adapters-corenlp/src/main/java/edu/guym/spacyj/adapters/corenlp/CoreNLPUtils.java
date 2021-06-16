package edu.guym.spacyj.adapters.corenlp;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

public final class CoreNLPUtils {

    private CoreNLPUtils() {
    }

    public static Properties defaultProps() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,depparse");
        return props;
    }

    public static Properties defaultPropsAndSingleSentenceSplit() {
        Properties props = defaultProps();
        props.setProperty("ssplit.isOneSentence", "true");
        return props;
    }

    public static StanfordCoreNLP createPipeline(Properties props) {
        return new StanfordCoreNLP(props);
    }
}
