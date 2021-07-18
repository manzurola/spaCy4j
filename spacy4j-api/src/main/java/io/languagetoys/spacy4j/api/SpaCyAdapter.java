package io.languagetoys.spacy4j.api;

import io.languagetoys.spacy4j.api.containers.Doc;
import io.languagetoys.spacy4j.api.containers.TokenData;
import io.languagetoys.spacy4j.api.exceptions.SpaCyException;

import java.util.List;

public interface SpaCyAdapter {

    Doc nlp(String text) throws SpaCyException;
}
