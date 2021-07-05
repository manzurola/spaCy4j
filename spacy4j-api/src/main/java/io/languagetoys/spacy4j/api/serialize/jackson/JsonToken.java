package io.languagetoys.spacy4j.api.serialize.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
class JsonToken {
    String text;
    String whitespaceBefore;
    String whitespaceAfter;
    int index;
    int beginOffset;
    int endOffset;
    String lemma;
    String tag;
    String pos;
    int head;
    String dependency;
    boolean sentenceStart;
    boolean isPunct;
    boolean likeNum;
}
