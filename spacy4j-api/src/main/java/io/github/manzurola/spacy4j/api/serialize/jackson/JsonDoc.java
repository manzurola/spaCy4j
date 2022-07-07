package io.github.manzurola.spacy4j.api.serialize.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
class JsonDoc {

    String text = "";
    List<JsonSentence> sentences = new ArrayList<>();

}
