package com.github.manzurola.spacy4j.api.containers;

import com.github.manzurola.spacy4j.api.utils.TextUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A slice from a {@link Doc} object.
 */
public final class Span {

    private final Doc doc;
    private final int start;
    private final int end;
    private final String text;

    private Span(Doc doc, int start, int end) {
        this.doc = Objects.requireNonNull(doc);
        this.start = start;
        this.end = end;
        this.text = TextUtils.writeTextWithoutWs(doc.data().subList(start, end));
    }

    public static Span create(Doc doc, int start, int end) {
        return new Span(doc, start, end);
    }

    /**
     * The parent document.
     */
    public final Doc doc() {
        return doc;
    }

    /**
     * A string representation of the span text.
     */
    public final String text() {
        return text;
    }

    /**
     * Returns true if the span no has tokens, false otherwise.
     *
     * @return
     */
    public final boolean isEmpty() {
        return end - start > 1;
    }

    /**
     * The character offset for the start of the span.
     */
    public final int startChar() {
        return getToken(start).charStart();
    }

    /**
     * The character offset for the end of the span.
     */
    public final int endChar() {
        return getToken(end - 1).charEnd();
    }


    /**
     * Get all tokens in this span.
     */
    public final List<Token> tokens() {
        return IntStream
                .range(start, end)
                .mapToObj(doc::token)
                .collect(Collectors.toList());
    }


    /**
     * Get a token at position i.
     *
     * @param i the index of the desired token.
     * @throws IndexOutOfBoundsException if i is out of bounds
     */
    public final Token getToken(int i) {
        Objects.checkIndex(i, size());
        return tokens().get(i);
    }

    /**
     * Get the number of tokens in the span.
     */
    public final int size() {
        return end - start;
    }

    /**
     * The sentence span that this span is a part of. If the span happens to cross sentence boundaries, only the first
     * sentence will be returned.
     */
    public final Span sentence() {
        return doc.sentences().stream()
                .filter(s -> s.start() <= start() && s.end() > start())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Could not find the sentence this span is a part of"));
    }

    /**
     * The token offset for the start of the span (inclusive).
     */
    public final int start() {
        return start;
    }

    /**
     * The token offset for the end of the span (exclusive).
     */
    public final int end() {
        return end;
    }

    /**
     * The token with the shortest path to the root of the sentence (or the root itself). If multiple tokens are equally
     * high in the tree, the first token is taken.
     */
    public final Optional<Token> root() {
        List<TokenData> data = doc.data().subList(start, end);
        return data
                .stream()
                .filter(t -> t.head() == 0)
                .map(t -> doc.token(t.index()))
                .findFirst();
    }

    /**
     * Get the underlying token data for this span.
     */
    public final List<TokenData> data() {
        return doc().data().subList(start, end);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Span span = (Span) o;
        return start == span.start && end == span.end && doc.equals(span.doc);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(doc, start, end);
    }

    @Override
    public final String toString() {
        return "Span{" + text + '}';
    }

}
