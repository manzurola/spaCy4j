package edu.guym.spacyj.api;

import edu.guym.spacyj.api.containers.TokenData;
import edu.guym.spacyj.api.exceptions.SpaCyException;

import java.util.List;

public interface SpaCyAdapter {

    List<TokenData> nlp(String text) throws SpaCyException;
}
