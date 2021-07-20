package io.languagetoys.spacy4j.api;

import io.languagetoys.spacy4j.api.containers.Doc;
import io.languagetoys.spacy4j.api.exceptions.SpaCyException;

/**
 * Base interface for SpaCy objects. The default implementation delegates all processing to a {@link SpaCyAdapter}. See
 * the spacy4j-adapters module for available adapters. <br/> This class is meant to be injected and reused across an
 * application. It is thread safe and all adapter implementations supplied with this project are guaranteed to be thread
 * safe and reusable.
 */
public interface SpaCy {

    /**
     * Processes the supplied text and creates a new {@link Doc} object.
     *
     * @param text the target text to process. Should be not null but can be empty.
     * @return a new {@link Doc}
     * @throws SpaCyException if an error occurred.
     */
    Doc nlp(String text) throws SpaCyException;

    /**
     * Create a new SpaCy that delegates all {@link #nlp(String)} requests to the supplied {@link SpaCyAdapter}.
     */
    static SpaCy create(SpaCyAdapter adapter) {
        return new SpaCyImpl(adapter);
    }
}
