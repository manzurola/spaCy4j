package edu.guym.spacyj.api.containers.impl;

import edu.guym.spacyj.api.containers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class DocImpl implements Doc {

    private final String text;
    private final List<TokenData> tokenData;

    public DocImpl(List<TokenData> tokenData) {
        this.text = TokenTextPrinter.getInstance().print(tokenData);
        this.tokenData = Objects.requireNonNull(tokenData);
    }

    @Override
    public List<Span> sentences() {
        List<Integer> sentenceStartLocations = tokenData.stream()
                .filter(TokenData::isSentenceStart)
                .map(TokenData::index)
                .collect(Collectors.toList());
        List<Span> sentences = new ArrayList<>();

        for (int start = 0; start < sentenceStartLocations.size(); start++) {
            int end = start == sentenceStartLocations.size() - 1 ?
                    tokenData.size() :
                    sentenceStartLocations.get(start + 1);
            sentences.add(spanOf(start, end));
        }
        return sentences;
    }

    @Override
    public final List<TokenData> tokenData() {
        return tokenData;
    }

    @Override
    public final Span spanOf(int start, int end) {
        Objects.checkFromToIndex(start, end, tokenData.size());
        return Span.create(this, start, end);
    }

    @Override
    public final String text() {
        return text;
    }

    @Override
    public final boolean isEmpty() {
        return tokenData.isEmpty();
    }

    @Override
    public final int startChar() {
        return isEmpty() ? 0 : tokenData.get(0).beginOffset();
    }

    @Override
    public final int endChar() {
        return isEmpty() ? 0 : tokenData.get(size() - 1).beginOffset();
    }

    @Override
    public final List<Token> tokens() {
        return tokenData.stream()
                .map(t -> Token.create(this, t.index()))
                .collect(Collectors.toList());
    }

    @Override
    public Stream<Token> stream() {
        return tokens().stream();
    }

    @Override
    public Token getToken(int i) throws IndexOutOfBoundsException {
        Objects.checkIndex(i, size());
        return tokens().get(i);
    }

    @Override
    public final int size() {
        return tokenData.size();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocImpl doc = (DocImpl) o;
        return text.equals(doc.text) && tokenData.equals(doc.tokenData);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(text, tokenData);
    }

    @Override
    public final String toString() {
        return "Doc{" +
                "text='" + text + '\'' +
                ", tokenData=" + tokenData +
                '}';
    }


}
