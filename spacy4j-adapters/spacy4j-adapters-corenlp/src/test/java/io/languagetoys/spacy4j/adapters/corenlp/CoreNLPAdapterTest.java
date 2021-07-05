package io.languagetoys.spacy4j.adapters.corenlp;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.languagetoys.spacy4j.api.SpaCy;
import io.languagetoys.spacy4j.api.containers.Doc;
import io.languagetoys.spacy4j.api.serialize.jackson.SpaCyJacksonModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CoreNLPAdapterTest {

    private SpaCy spacy;

    @BeforeAll
    void setup() {
        spacy = SpaCy.create(CoreNLPAdapter.create());
    }

    @Test
    public void verifyCoreNlpResponse() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new SpaCyJacksonModule());
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("doc.json");
        Doc expected = mapper.readValue(inputStream, Doc.class);

        Doc actual = spacy.nlp("My head feels like a frisbee. Twice it's normal size. It feels like a football.");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void verifyEmptyDoc() {
        Doc doc = spacy.nlp("");
        assertEquals(Doc.create("", List.of()), doc);
    }

    @Test
    void verifyEmptySentenceOnNonBreakingSpace() {
        String text = String.valueOf((char) 160);
        Doc doc = spacy.nlp(text);
        Assertions.assertEquals(Doc.create(text, List.of()), doc);
    }

    @Test
    void verifyEmojiSucceeds() {
        Doc doc = spacy.nlp("\uD83D\uDE1C");
        Assertions.assertNotEquals(Doc.create("", List.of()), doc);
    }

    @Test
    public void emptyTextEmptySentence() {
        String text = "";
        Doc expected = Doc.create("", List.of());
        Doc actual = spacy.nlp(text);
        assertEquals(expected, actual);
    }

    @Test
    public void verifyParenthesisParse() {
        // a previous bug parsed ) as -RRB-
        Doc doc = spacy.nlp(":)");
        assertEquals(doc.tokens().get(1).text(), ")");
    }

    @Test
    public void verifyLikeNum() {
        Doc doc = spacy.nlp("ten 10.5 one thousand");
        assertTrue(doc.tokens().get(0).likeNum());
        assertTrue(doc.tokens().get(1).likeNum());
        assertTrue(doc.tokens().get(2).likeNum());
        assertTrue(doc.tokens().get(3).likeNum());
    }

    @Test
    public void verifyParseTextSucceeds() throws IOException, URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("testtext.txt");
        List<String> lines = Files.readAllLines(Path.of(url.toURI()));
        for (String line : lines) {
            spacy.nlp(line);
        }
    }

}
