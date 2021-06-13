package edu.guym.spacyj.api.containers;

import edu.guym.spacyj.api.containers.impl.TokenImpl;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * An individual token — i.e. a word, punctuation symbol, whitespace, etc.
 */
public interface Token {

    static Token create(Doc doc, int index) {
        return new TokenImpl(doc, index);
    }

    /**
     * The parent document.
     */
    Doc doc();

    /**
     * The parent sentence.
     */
    Span sentence();

    /**
     * Verbatim text content.
     */
    String text();

    /**
     * The index of the token within the parent document.
     */
    int index();

    /**
     * The character offset for the start of the document.
     */
    int charStart();

    /**
     * The character offset for the end of the document.
     */
    int charEnd();

    /**
     * The number of unicode characters in the token, i.e. token.text().length().
     */
    int length();

    /**
     * Leading whitespace characters if present, empty string otherwise.
     */
    String spaceBefore();

    /**
     * Trailing whitespace characters if present, empty string otherwise.
     */
    String spaceAfter();

    /**
     * Is this token the start of its sentence?
     */
    boolean isSentenceStart();

    /**
     * Fine-grained part-of-speech. Usually the PTB part-of-speech.
     */
    String tag();

    /**
     * Coarse-grained part-of-speech from the <a href="https://universaldependencies.org/docs/u/pos/">Universal POS tag set</a>.
     */
    String pos();

    /**
     * Base form of the token, with no inflectional suffixes.
     */
    String lemma();

    /**
     * Syntactic dependency relation.
     */
    String dependency();

    /**
     * Lowercase form of the token.
     */
    String lower();

    /**
     * The subsequent token in the parent doc.
     */
    Optional<Token> next();

    /**
     * The syntactic parent, or “governor”, of this token.
     */
    Optional<Token> head();

    /**
     * A sequence of the token’s immediate syntactic children.
     */
    List<Token> children();

    /**
     * Is this token the root of the parent sentence?
     */
    boolean isRoot();

    /**
     * Does the token consist of alphabetic characters? Equivalent to text().chars().allMatch(Character::isLetter).
     */
    default boolean isAlpha() {
        return text().chars().allMatch(Character::isLetter);
    }

    /**
     * Returns an Optional after applying predicate to Token.
     * @param predicate
     */
    default Optional<Token> filter(Predicate<Token> predicate) {
        return Optional.of(this).filter(predicate);
    }

    /**
     * Returns the result of applying predicate to Token.
     * @param predicate
     */
    default boolean matches(Predicate<Token> predicate) {
        return this.filter(predicate).isPresent();
    }

}
