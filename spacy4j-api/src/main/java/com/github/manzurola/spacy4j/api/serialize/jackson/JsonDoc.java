package com.github.manzurola.spacy4j.api.serialize.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
class JsonDoc {

    List<JsonSentence> sentences = new ArrayList<>();

}
