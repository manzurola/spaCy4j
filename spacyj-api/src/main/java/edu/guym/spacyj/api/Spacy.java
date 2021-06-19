package edu.guym.spacyj.api;

import edu.guym.spacyj.api.containers.Doc;
import edu.guym.spacyj.api.exceptions.SpacyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public interface Spacy {

    Doc nlp(String text) throws SpacyException;

    static Spacy create(final SpacyAdapter adapter) {
        return new Spacy() {

            private final Logger logger = LoggerFactory.getLogger(this.getClass());

            @Override
            public Doc nlp(String text) throws SpacyException {
                if (Objects.isNull(text) || text.isBlank()) {
                    return Doc.EMPTY;
                }

                try {
                    return Doc.create(text, adapter.nlp(text));
                } catch (Throwable e) {
                    logger.error("failed to parse text", new SpacyException(e, text));
                    return Doc.EMPTY;
                }
            }
        };
    }
}
