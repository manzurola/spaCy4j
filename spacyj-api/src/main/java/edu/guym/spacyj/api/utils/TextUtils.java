package edu.guym.spacyj.api.utils;

import edu.guym.spacyj.api.containers.TokenData;

import java.util.List;

public class TextUtils {

    private TextUtils() {
    }

    public static String writeTextWithoutWs(List<TokenData> tokens) {
       return writeTextWithWs(tokens).trim();
    }

    public static String writeTextWithWs(List<TokenData> tokens) {
        if (tokens.isEmpty()) {
            return "";
        }
        String spaceBefore = tokens.get(0).whitespaceBefore();
        return tokens.stream()
                .map(t -> t.text().concat(t.whitespaceAfter()))
                .reduce(spaceBefore, String::concat);
    }
}
