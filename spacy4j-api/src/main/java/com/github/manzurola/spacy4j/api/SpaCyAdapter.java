package com.github.manzurola.spacy4j.api;

import com.github.manzurola.spacy4j.api.containers.Doc;
import com.github.manzurola.spacy4j.api.exceptions.SpaCyException;

public interface SpaCyAdapter {

    Doc nlp(String text) throws SpaCyException;
}
