package edu.guym.spacyj.api.containers.impl;

import edu.guym.spacyj.api.containers.Doc;
import edu.guym.spacyj.api.containers.Span;
import edu.guym.spacyj.api.containers.Token;
import edu.guym.spacyj.api.containers.TokenData;
import edu.guym.spacyj.api.features.Dependency;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public final class TokenImpl implements Token {

    private final Doc doc;
    private final int index;
    private final TokenData data;

    public TokenImpl(Doc doc, int index) {
        this.doc = doc;
        this.index = index;
        this.data = doc.tokenData().get(index);
    }

    @Override
    public final String text() {
        return data.text();
    }

    @Override
    public final int index() {
        return data.index();
    }

    @Override
    public final int charStart() {
        return data.beginOffset();
    }

    @Override
    public final int charEnd() {
        return data.endOffset();
    }

    @Override
    public final String spaceBefore() {
        return data.whitespaceAfter();
    }

    @Override
    public final String spaceAfter() {
        return data.whitespaceAfter();
    }

    @Override
    public final boolean isSentenceStart() {
        return data.isSentenceStart();
    }

    @Override
    public final String tag() {
        return data.tag();
    }

    @Override
    public final String pos() {
        return data.pos();
    }

    @Override
    public final String lemma() {
        return data.lemma();
    }

    @Override
    public final String dependency() {
        return data.dependency();
    }

    @Override
    public final String lowerCase() {
        return text().toLowerCase();
    }

    @Override
    public final Optional<Token> next() {
        int next = index() + 1;
        if (next < doc.size()) {
            return Optional.of(doc.getToken(next));
        }
        return Optional.empty();
    }

    @Override
    public final Optional<Token> governor() {
        return Optional.ofNullable(doc.getToken(data.head()));
    }

    @Override
    public final boolean isRoot() {
        return Dependency.ROOT.matches(dependency());
    }

    @Override
    public final List<Token> dependencies() {
        return doc.tokenData()
                .stream()
                .filter(t -> t.head() == data.index())
                .map(TokenData::index)
                .map(doc::getToken)
                .collect(Collectors.toList());
    }

    @Override
    public final Span sentence() {
        return doc.sentences()
                .stream()
                .filter(s -> {
                    int firstTokenIndex = s.getToken(0).index();
                    int lastTokenIndex = s.size() + firstTokenIndex;
                    return index >= firstTokenIndex && index < lastTokenIndex;
                })
                .findFirst()
                .orElse(Span.EMPTY);
    }

    @Override
    public final Doc doc() {
        return doc;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenImpl token = (TokenImpl) o;
        return data.equals(token.data);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public final String toString() {
        return "TokenImpl{" +
                "data=" + data +
                '}';
    }


}
