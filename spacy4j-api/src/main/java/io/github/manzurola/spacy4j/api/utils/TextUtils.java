package io.github.manzurola.spacy4j.api.utils;

import io.github.manzurola.spacy4j.api.containers.Token;
import io.github.manzurola.spacy4j.api.containers.TokenData;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * Get the text represented by concatenating the tokens. Utility method that
     * replaces tokens.stream().map(Token::textWithWs).collect(Collectors
     * .joining())
     *
     * @param tokens
     * @return
     */
    public static String concatTokenTextWithWs(List<Token> tokens) {
        return tokens
            .stream()
            .map(Token::textWithWs)
            .collect(Collectors.joining());
    }

}
