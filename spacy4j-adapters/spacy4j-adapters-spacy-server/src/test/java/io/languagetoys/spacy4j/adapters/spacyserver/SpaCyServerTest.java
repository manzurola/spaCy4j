package io.languagetoys.spacy4j.adapters.spacyserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.languagetoys.spacy4j.api.SpaCy;
import io.languagetoys.spacy4j.api.containers.Doc;
import io.languagetoys.spacy4j.api.serialize.jackson.SpaCyJacksonModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.io.InputStream;

@Testcontainers
public class SpaCyServerTest {

    private SpaCy spacy;

    @Container
    public final GenericContainer<?> spacyServer = new GenericContainer<>("neelkamath/spacy-server:2-en_core_web_sm")
            .withExposedPorts(8000);

    @BeforeEach
    void setup() {
        String host = spacyServer.getHost();
        Integer port = spacyServer.getFirstMappedPort();
        spacy = SpaCy.create(SpaCyServerAdapter.create(host, port));
    }

    @Test
    public void verifySpacyServerResponse() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new SpaCyJacksonModule());
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("doc.json");
        Doc expected = mapper.readValue(inputStream, Doc.class);

        Doc actual = spacy.nlp("My head feels like a frisbee. Twice it's normal size. It feels like a football.");

        Assertions.assertEquals(expected, actual);
    }

}
