package io.github.manzurola.spacy4j.api;

import io.github.manzurola.spacy4j.api.containers.Doc;
import io.github.manzurola.spacy4j.api.exceptions.SpaCyException;

public interface SpaCyAdapter {

    Doc nlp(String text) throws SpaCyException;
}
