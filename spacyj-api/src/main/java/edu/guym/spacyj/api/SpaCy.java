package edu.guym.spacyj.api;

import edu.guym.spacyj.api.containers.Doc;
import edu.guym.spacyj.api.containers.TokenData;
import edu.guym.spacyj.api.exceptions.SpaCyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public final class SpaCy {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SpaCyAdapter adapter;

    private SpaCy(SpaCyAdapter adapter) {
        this.adapter = adapter;
    }

    public static SpaCy create(SpaCyAdapter adapter) {
        return new SpaCy(adapter);
    }

    public final Doc nlp(String text) throws SpaCyException {
        Objects.requireNonNull(text);
        try {
            List<TokenData> data = adapter.nlp(text);
            return Doc.create(text, data);
        } catch (Throwable e) {
            logger.error("failed to parse text", e);
            throw new SpaCyException(e, text);
        }
    }
}
