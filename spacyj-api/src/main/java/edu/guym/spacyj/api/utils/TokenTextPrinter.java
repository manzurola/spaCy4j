package edu.guym.spacyj.api.utils;

import edu.guym.spacyj.api.containers.TokenData;

import java.util.List;

public final class TokenTextPrinter {

    enum Singleton {
        INSTANCE(new TokenTextPrinter());
        final TokenTextPrinter value;

        Singleton(TokenTextPrinter value) {
            this.value = value;
        }
    }

    public static TokenTextPrinter getInstance() {
        return Singleton.INSTANCE.value;
    }

    public final String print(List<TokenData> tokens) {
        if (tokens.isEmpty()) {
            return "";
        }
        String spaceBefore = tokens.get(0).whitespaceBefore();
        return tokens.stream()
                .map(t -> t.text().concat(t.whitespaceAfter()))
                .reduce(spaceBefore, String::concat);
    }
}
