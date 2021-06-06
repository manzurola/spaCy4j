package edu.guym.spacyj.api.containers;

import edu.guym.spacyj.api.containers.impl.DocImpl;

import java.util.List;
import java.util.stream.Stream;

public interface Doc {

    Doc EMPTY = Doc.create(List.of());

    static Doc create(List<TokenData> data) {
        return new DocImpl(data);
    }

    /**
     * A string representation of the document text.
     * @Return
     */
    String text();

    /**
     * Returns true if the doc contains no tokens.
     * @return
     */
    boolean isEmpty();

    /**
     * The character offset for the start of the document.
     */
    int charStart();

    /**
     * The character offset for the end of the document.
     */
    int charEnd();

    /**
     * Get all spans representing the sentences in document.
     * @return
     */
    List<Span> sentences();

    /**
     * Get all tokens in doc.
     * @return
     */
    List<Token> tokens();

    /**
     * Get all tokens in doc as a stream.
     * @return
     */
    Stream<Token> stream();

    /**
     * Get ta token at position i.
     * @param i
     * @return
     * @throws IndexOutOfBoundsException if i is out of range
     */
    Token getToken(int i) throws IndexOutOfBoundsException;

    /**
     * Get the number of tokens in the document.
     * @return
     */
    int size();

    /**
     * Get a span of [start, end). Indices pertain to document token offsets.
     * Similar to sublist of List.
     * @param start offset of the first token to include in the span
     * @param end offset of the last token in the span (exclusive)
     * @return
     */
    Span spanOf(int start, int end);

    /**
     * Get the underlying token data for serialization.
     * @return
     */
    List<TokenData> tokenData();

    TokenData getTokenData(int index);
}
