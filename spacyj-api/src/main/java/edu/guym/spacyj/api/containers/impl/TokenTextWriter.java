package edu.guym.spacyj.api.containers.impl;

import edu.guym.spacyj.api.containers.TokenData;

import java.util.List;

public final class TokenTextWriter {

    enum Singleton {
        INSTANCE(new TokenTextWriter());
        final TokenTextWriter value;

        Singleton(TokenTextWriter value) {
            this.value = value;
        }
    }

    public static TokenTextWriter getInstance() {
        return Singleton.INSTANCE.value;
    }

    public final String getText(List<TokenData> tokens) {
        if (tokens.isEmpty()) {
            return "";
        }
        String spaceBefore = tokens.get(0).whitespaceBefore();
        return tokens.stream()
                .map(t -> t.text().concat(t.whitespaceAfter()))
                .reduce(spaceBefore, String::concat);
    }
}
