package edu.guym.spacyj.api.containers;

import edu.guym.spacyj.api.containers.impl.SpanImpl;

import java.util.List;
import java.util.Optional;

/**
 * A slice from a {@link Doc} object.
 */
public interface Span {

    Span EMPTY = new SpanImpl(Doc.EMPTY, 0, 0);

    static Span create(Doc doc, int start, int end) {
        return new SpanImpl(doc, start, end);
    }

    /**
     * A string representation of the span text.
     */
    String text();

    /**
     * Returns true if the span no has tokens, false otherwise.
     * @return
     */
    boolean isEmpty();

    /**
     * The character offset for the start of the span.
     */
    int charStart();

    /**
     * The character offset for the end of the span.
     */
    int charEnd();

    List<Token> tokens();


    Token getToken(int i);

    /**
     * Get the number of tokens in the span.
     * @return
     */
    int size();

    /**
     * The sentence span that this span is a part of. If the span happens to cross sentence boundaries,
     * only the first sentence will be returned.
     */
    Span sentence();

    /**
     * The doc that this span is a part of.
     */
    Doc doc();

    /**
     * The token offset for the start of the span.
     */
    int start();

    /**
     * The token offset for the end of the span.
     */
    int end();

    /**
     * The token with the shortest path to the root of the sentence (or the root itself).
     * If multiple tokens are equally high in the tree, the first token is taken.
     */
    Optional<Token> root();

}
