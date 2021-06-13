package edu.guym.spacyj.api.containers.impl;

import edu.guym.spacyj.api.containers.Doc;
import edu.guym.spacyj.api.containers.Span;
import edu.guym.spacyj.api.containers.Token;
import edu.guym.spacyj.api.containers.TokenData;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class SpanImpl implements Span {

    private final Doc doc;
    private final int start;
    private final int end;
    private final String text;

    public SpanImpl(Doc doc, int start, int end) {
        this.doc = Objects.requireNonNull(doc);
        this.start = start;
        this.end = end;
        this.text = TokenTextWriter.getInstance().getText(doc.tokenData().subList(start, end));
    }

    @Override
    public final String text() {
        return text;
    }

    @Override
    public final Doc doc() {
        return doc;
    }

    @Override
    public final boolean isEmpty() {
        return end - start > 1;
    }

    @Override
    public final int start() {
        return start;
    }

    @Override
    public final int end() {
        return end;
    }

    @Override
    public Optional<Token> root() {
        return dataStream()
                .filter(t -> t.head() == 0)
                .map(t -> doc.getToken(t.index()))
                .findFirst();
    }

    @Override
    public final int startChar() {
        return getToken(start).charStart();
    }

    @Override
    public final int endChar() {
        return getToken(end - 1).charEnd();
    }

    @Override
    public final List<Token> tokens() {
        return IntStream
                .range(start, end)
                .mapToObj(doc::getToken)
                .collect(Collectors.toList());
    }

    @Override
    public final int size() {
        return end - start;
    }

    @Override
    public final Token getToken(int i) {
        Objects.checkIndex(i, size());
        return tokens().get(i);
    }

    @Override
    public final Span sentence() {
        return doc.sentences().stream()
                .filter(s -> s.start() <= start() && s.end() > start())
                .findFirst()
                .orElse(Span.EMPTY);
    }

    private Stream<TokenData> dataStream() {
        return IntStream
                .range(start, end)
                .mapToObj(doc::getTokenData);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpanImpl span = (SpanImpl) o;
        return start == span.start && end == span.end && doc.equals(span.doc);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(doc, start, end);
    }

    @Override
    public String toString() {
        return "SpanImpl{" +
                "text='" + text + '\'' +
                '}';
    }


}
