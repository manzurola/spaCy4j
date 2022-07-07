package io.github.manzurola.spacy4j.api.containers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * An individual token — i.e. a word, punctuation symbol, whitespace, etc.
 */
public final class Token {

    private final Doc doc;
    private final int index;

    public Token(Doc doc, int index) {
        this.doc = doc;
        this.index = index;
    }

    public static Token create(Doc doc, int index) {
        return new Token(doc, index);
    }

    /**
     * The parent document.
     */
    public final Doc doc() {
        return doc;
    }

    /**
     * The parent sentence.
     */
    public final Span sentence() {
        return doc
            .sentences()
            .stream()
            .filter(sentence -> {
                int firstTokenIndex = sentence.first().index();
                int lastTokenIndex = sentence.last().index();
                return index >= firstTokenIndex && index < lastTokenIndex;
            })
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(
                "could not find parent " +
                "sentence"));
    }

    /**
     * Verbatim text content.
     */
    public final String text() {
        return data().text();
    }

    /**
     * The text content of the token with a trailing whitespace character if
     * this token has one.
     */
    public final String textWithWs() {
        return data().text() + spaceAfter();
    }

    /**
     * The index of the token within the parent document.
     */
    public final int index() {
        return data().index();
    }

    /**
     * The character offset for the start of the document.
     */
    public final int charStart() {
        return data().beginOffset();
    }

    /**
     * The character offset for the end of the document.
     */
    public final int charEnd() {
        return data().endOffset();
    }

    /**
     * The number of unicode characters in the token, i.e.
     * token.text().length().
     */
    public int length() {
        return text().length();
    }

    /**
     * Leading whitespace characters if present, empty string otherwise.
     */
    public final String spaceBefore() {
        return data().whitespaceBefore();
    }

    /**
     * Trailing whitespace characters if present, empty string otherwise.
     */
    public final String spaceAfter() {
        return data().whitespaceAfter();
    }

    /**
     * Is this token the start of its sentence?
     */
    public final boolean isSentenceStart() {
        return data().isSentenceStart();
    }

    /**
     * Fine-grained part-of-speech. Usually the PTB part-of-speech.
     */
    public final String tag() {
        return data().tag();
    }

    /**
     * Coarse-grained part-of-speech from the
     * <a href="https://universaldependencies.org/docs/u/pos/">Universal
     * POS tag set</a>.
     */
    public final String pos() {
        return data().pos();
    }

    /**
     * Base form of the token, with no inflectional suffixes.
     */
    public final String lemma() {
        return data().lemma();
    }

    /**
     * Syntactic dependency relation.
     */
    public final String dependency() {
        return data().dependency();
    }

    /**
     * Lowercase form of the token.
     */
    public final String lower() {
        return text().toLowerCase();
    }

    /**
     * The subsequent token in the parent doc.
     */
    public final Optional<Token> next() {
        int next = index() + 1;
        if (next < doc.size()) {
            return Optional.of(doc.token(next));
        }
        return Optional.empty();
    }

    /**
     * The syntactic parent, or “governor”, of this token.
     */
    public final Optional<Token> head() {
        return Optional.ofNullable(doc.token(data().head()));
    }

    /**
     * A sequence of the token’s immediate syntactic children.
     */
    public final List<Token> children() {
        return doc
            .data()
            .stream()
            .filter(t -> t.head() == data().index())
            .map(TokenData::index)
            .map(doc::token)
            .collect(Collectors.toList());
    }

    /**
     * Does the token consist of only alphabetic characters?
     */
    public final boolean isAlpha() {
        return text().chars().allMatch(Character::isLetter);
    }

    /**
     * Is the token punctuation?
     */
    public final boolean isPunct() {
        return data().isPunct();
    }

    /**
     * Does the token consist of whitespace characters? Equivalent to {@code
     * text().isBlank()}
     */
    public final boolean isWhitespace() {
        return text().isBlank();
    }

    /**
     * Does the token represent a number? e.g. “10.9”, “10”, “ten”, etc.
     */
    public final boolean likeNum() {
        return data().likeNum();
    }

    /**
     * Returns an Optional after applying predicate to Token.
     */
    public final Optional<Token> filter(Predicate<Token> predicate) {
        return Optional.of(this).filter(predicate);
    }

    /**
     * Returns the result of applying predicate to Token.
     */
    public final boolean matches(Predicate<Token> predicate) {
        return this.filter(predicate).isPresent();
    }

    public final TokenData data() {
        return doc.data().get(index);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Token token = (Token) o;
        return data().equals(token.data());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(data());
    }

    @Override
    public final String toString() {
        return text();
    }

}
