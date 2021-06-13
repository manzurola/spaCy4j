package edu.guym.spacyj.api.containers;

import edu.guym.spacyj.api.containers.impl.DocImpl;

import java.util.List;
import java.util.stream.Stream;

/**
 * A Doc is a sequence of {@link Token}.
 * The Doc object holds a list of {@link TokenData} objects. {@link Span} and {@link Token} are views of this array.
 * <p>
 * The API is greatly inspired by <a href="https://spacy.io/api/doc">Spacy</a>, but exposes less functionality.
 * Even the docs are mostly copy-pasted from the official Spacy docs, in an effort to ease the reader.
 */
public interface Doc {

    /**
     * The empty Doc.
     */
    Doc EMPTY = Doc.create(List.of());

    static Doc create(List<TokenData> data) {
        return new DocImpl(data);
    }

    /**
     * A string representation of the document text.
     */
    String text();

    /**
     * Returns true if the doc contains no tokens.
     */
    boolean isEmpty();

    /**
     * The character offset for the start of the document.
     */
    int startChar();

    /**
     * The character offset for the end of the document.
     */
    int endChar();

    /**
     * Get all spans representing the sentences in document.
     */
    List<Span> sentences();

    /**
     * Get all tokens in doc.
     */
    List<Token> tokens();

    /**
     * Get all tokens in doc as a stream.
     */
    Stream<Token> stream();

    /**
     * Get a token at position i.
     *
     * @param i the index of the desired token.
     * @throws IndexOutOfBoundsException if i is out of bounds
     */
    Token getToken(int i) throws IndexOutOfBoundsException;

    /**
     * Get the number of tokens in the document.
     */
    int size();

    /**
     * Get a span of [start, end). Indices pertain to document token offsets.
     * Similar to sublist of List.
     *
     * @param start offset of the first token to include in the span
     * @param end   offset of the last token in the span (exclusive)
     */
    Span spanOf(int start, int end);

    /**
     * Get the underlying token data for serialization/deserialization and creation of {@link Doc} objects.
     */
    List<TokenData> tokenData();

    /**
     * Get the underlying data for a token at position {@code i}.
     *
     * @param i the index of the token
     */
    TokenData getTokenData(int i);
}
