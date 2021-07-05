package io.languagetoys.spacy4j.api;

import io.languagetoys.spacy4j.api.containers.Doc;
import io.languagetoys.spacy4j.api.exceptions.SpaCyException;

public interface SpaCy {

    Doc nlp(String text) throws SpaCyException;

    static SpaCy create(SpaCyAdapter adapter) {
        return new SpaCyImpl(adapter);
    }
}
