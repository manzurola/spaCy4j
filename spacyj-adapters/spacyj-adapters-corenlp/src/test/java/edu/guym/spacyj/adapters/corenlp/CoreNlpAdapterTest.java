package edu.guym.spacyj.adapters.corenlp;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.guym.spacyj.api.SpaCy;
import edu.guym.spacyj.api.containers.Doc;
import edu.guym.spacyj.api.serialize.jackson.SpaCyJacksonModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CoreNlpAdapterTest {

    private SpaCy spacy;

    @BeforeAll
    void setup() {
        spacy = SpaCy.create(CoreNlpAdapter.create());
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
        // a previous bug parsed ) as -RRB-
        Doc doc = spacy.nlp("ten 10.5 one thousand");
        assertTrue(doc.tokens().get(0).likeNum());
        assertTrue(doc.tokens().get(1).likeNum());
        assertTrue(doc.tokens().get(2).likeNum());
        assertTrue(doc.tokens().get(3).likeNum());
    }

}
