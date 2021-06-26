package edu.guym.spacyj.adapters.spacyserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.guym.spacyj.api.Spacy;
import edu.guym.spacyj.api.containers.Doc;
import edu.guym.spacyj.api.serialize.jackson.SpacyJacksonModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.io.InputStream;

@Testcontainers
public class SpacyServerTest {

    private Spacy spacy;

    @Container
    public final GenericContainer<?> spacyServer = new GenericContainer<>("neelkamath/spacy-server:2-en_core_web_sm")
            .withExposedPorts(8000);

    @BeforeEach
    void setup() {
        String host = spacyServer.getHost();
        Integer port = spacyServer.getFirstMappedPort();
        spacy = Spacy.create(SpacyServerAdapter.create(host, port));
    }

    @Test
    public void verifySpacyServerResponse() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new SpacyJacksonModule());
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("doc.json");
        Doc expected = mapper.readValue(inputStream, Doc.class);

        Doc actual = spacy.nlp("My head feels like a frisbee. Twice it's normal size. It feels like a football.");

        Assertions.assertEquals(expected, actual);
    }

}
