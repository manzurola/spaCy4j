package io.languagetoys.spacy4j.api.serialize.jackson.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonToken {
    public String text;
    public String whitespaceBefore;
    public String whitespaceAfter;
    public int index;
    public int beginOffset;
    public int endOffset;
    public String lemma;
    public String tag;
    public String pos;
    public int head;
    public String dependency;
    public boolean sentenceStart;
    public boolean isPunct;
    public boolean likeNum;
}
