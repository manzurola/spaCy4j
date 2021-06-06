package edu.guym.spacyj.api;

import edu.guym.spacyj.api.containers.Doc;
import edu.guym.spacyj.api.exceptions.NlpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public interface Spacy {

    Doc fromText(String text) throws NlpException;

    static Spacy create(final SpacyClient client) {
        return new Spacy() {

            private final Logger logger = LoggerFactory.getLogger(this.getClass());

            @Override
            public Doc fromText(String text) throws NlpException {
                if (Objects.isNull(text) || text.isBlank()) {
                    return Doc.EMPTY;
                }

                try {
                    return Doc.create(client.nlp(text));
                } catch (Throwable e) {
                    logger.warn("failed to parse text", new NlpException(e, text));
                    return Doc.EMPTY;
                }
            }
        };
    }
}
