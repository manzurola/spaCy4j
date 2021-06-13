package edu.guym.spacyj.api;

import edu.guym.spacyj.api.containers.TokenData;
import edu.guym.spacyj.api.exceptions.SpacyException;

import java.util.List;

public interface SpacyClient {

    List<TokenData> nlp(String text) throws SpacyException;
}
