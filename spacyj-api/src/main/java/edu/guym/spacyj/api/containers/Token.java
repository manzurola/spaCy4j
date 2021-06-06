package edu.guym.spacyj.api.containers;

import edu.guym.spacyj.api.containers.impl.TokenImpl;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface Token {

    static Token create(Doc doc, int index) {
        return new TokenImpl(doc, index);
    }

    String text();

    int index();

    int charStart();

    int charEnd();

    String spaceBefore();

    String spaceAfter();

    boolean isSentenceStart();

    String tag();

    String pos();

    String lemma();

    String dependency();

    String lowerCase();

    Optional<Token> next();

    Optional<Token> governor();

    boolean isRoot();

    List<Token> dependencies();

    Span sentence();

    Doc doc();

    default boolean isAlpha() {
        return text().chars().allMatch(Character::isLetter);
    }

    default Optional<Token> filter(Predicate<Token> predicate) {
        return Optional.of(this).filter(predicate);
    }

    default boolean matches(Predicate<Token> predicate) {
        return this.filter(predicate).isPresent();
    }

}
