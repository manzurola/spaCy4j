package edu.guym.spacyj.api;

import edu.guym.spacyj.api.containers.Doc;
import edu.guym.spacyj.api.exceptions.SpacyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public final class Spacy {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SpacyAdapter adapter;

    private Spacy(SpacyAdapter adapter) {
        this.adapter = adapter;
    }

    public static Spacy create(SpacyAdapter adapter) {
        return new Spacy(adapter);
    }

    public final Doc nlp(String text) throws SpacyException {
        if (Objects.isNull(text) || text.isBlank()) {
            return Doc.EMPTY;
        }

        try {
            return Doc.create(text, adapter.nlp(text));
        } catch (Throwable e) {
            logger.error("failed to parse text", e);
            throw new SpacyException(e, text);
        }
    }
}
