package io.languagetoys.spacy4j.api;

import io.languagetoys.spacy4j.api.containers.TokenData;
import io.languagetoys.spacy4j.api.exceptions.SpaCyException;

import java.util.List;

public interface SpaCyAdapter {

    List<TokenData> nlp(String text) throws SpaCyException;
}
