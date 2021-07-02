package io.languagetoys.spacy4j.api.serialize.jackson.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonSentence {

    public String text;
    public List<JsonToken> tokens = new ArrayList<>();
}
