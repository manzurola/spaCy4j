package io.languagetoys.spacy4j.api.serialize.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
class JsonSentence {

    String text;
    List<JsonToken> tokens = new ArrayList<>();
}
