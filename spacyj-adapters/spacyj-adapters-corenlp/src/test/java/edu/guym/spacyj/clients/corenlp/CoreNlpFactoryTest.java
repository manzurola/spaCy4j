package edu.guym.spacyj.clients.corenlp;

import edu.guym.spacyj.api.Spacy;
import edu.guym.spacyj.api.containers.Doc;
import edu.guym.spacyj.api.containers.Span;
import edu.guym.spacyj.api.containers.TokenData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CoreNlpFactoryTest {

    private final static Spacy spacy = Spacy.create(new StanfordCoreNlpSpacyAdapter());

    @Test
    public void testSentences() {
        List<String> sentences = List.of(
                "A dog is an animal.",
                "A dog is   an animal.",
                "An airport is a busy place.",
                "We can make cakes with flour, milk and eggs.",
                "Dogs hate cats.",
                "I mustn't do it now.",
                "Do they often go to the pictures?",
                "During my friend Maisie's two weeks vacation.",
                "This grammar is very difficult; a child can't understand it.",
                "the house on the hill",
                "he brushed his teeth whitespaceAfter eating dinner",
                "apples and bananas, or oranges",
                "Sue and Paul are running",
                "the long and wide river",
                "the store buys and sells cameras",
                "Sue wants to buy a hat"
        );

        for (String sentence : sentences) {
            Doc parsed = spacy.nlp(sentence);
        }

        // just make sure no exception is thrown
    }

    @Test
    public void verifyEmptyDoc() {
        Doc doc = spacy.nlp("");
        assertEquals(Doc.EMPTY, doc);
    }

    @Test
    public void verifyMultipleSentences() {
        Doc doc = spacy.nlp("Hello my name is guy. I am a nerd. this.");
        assertEquals(3, doc.sentences().size());
        assertFalse(doc.isEmpty());
    }

    @Test
    void verifyEmptySentenceOnNonBreakingSpace() {
        String value = String.valueOf((char) 160);
        Doc doc = spacy.nlp(value);
        Assertions.assertEquals(Doc.create(List.of()), doc);
    }

    @Test
    void verifyEmojiSucceeds() {
        Doc doc = spacy.nlp("\uD83D\uDE1C");
        Assertions.assertNotEquals(Span.EMPTY, doc);
    }

    @Test
    public void createAndVerifyShortSentence() {
        String text = "My dog is hungry.";
        List<TokenData> expectedWords = Arrays.asList(
                TokenData.builder().setText("My").setBefore("").setAfter(" ").setIndex(0).setBeginOffset(0).setEndOffset(2).setLemma("my").setTag("PRP$").setPos("DET").setHead(1).setDependency("nmod:poss").setIsAlpha(true).setSentenceStart(true).build(),
                TokenData.builder().setText("dog").setBefore(" ").setAfter(" ").setIndex(1).setBeginOffset(3).setEndOffset(6).setLemma("dog").setTag("NN").setPos("NOUN").setHead(3).setDependency("nsubj").setIsAlpha(true).build(),
                TokenData.builder().setText("is").setBefore(" ").setAfter(" ").setIndex(2).setBeginOffset(7).setEndOffset(9).setLemma("be").setTag("VBZ").setPos("VERB").setHead(3).setDependency("cop").setIsAlpha(true).build(),
                TokenData.builder().setText("hungry").setBefore(" ").setAfter("").setIndex(3).setBeginOffset(10).setEndOffset(16).setLemma("hungry").setTag("JJ").setPos("ADJ").setHead(-1).setDependency("root").setIsAlpha(true).build(),
                TokenData.builder().setText(".").setBefore("").setAfter("").setIndex(4).setBeginOffset(16).setEndOffset(17).setLemma(".").setTag(".").setPos("PUNCT").setHead(3).setDependency("punct").setIsPunct(true).build()
        );
        assertExpectedSentence(text, expectedWords);
    }

    @Test
    public void createAndVerifyLongSentence() {
        String text = "The moon is very bright; I can read a book by it.";
        List<TokenData> expectedWords = Arrays.asList(
                TokenData.builder().setText("The").setBefore("").setAfter(" ").setIndex(0).setBeginOffset(0).setEndOffset(3).setLemma("the").setTag("DT").setPos("DET").setHead(1).setDependency("det").setSentenceStart(true).setIsAlpha(true).build(),
                TokenData.builder().setText("moon").setBefore(" ").setAfter(" ").setIndex(1).setBeginOffset(4).setEndOffset(8).setLemma("moon").setTag("NN").setPos("NOUN").setHead(4).setDependency("nsubj").setIsAlpha(true).build(),
                TokenData.builder().setText("is").setBefore(" ").setAfter(" ").setIndex(2).setBeginOffset(9).setEndOffset(11).setLemma("be").setTag("VBZ").setPos("VERB").setHead(4).setDependency("cop").setIsAlpha(true).build(),
                TokenData.builder().setText("very").setBefore(" ").setAfter(" ").setIndex(3).setBeginOffset(12).setEndOffset(16).setLemma("very").setTag("RB").setPos("ADV").setHead(4).setDependency("advmod").setIsAlpha(true).build(),
                TokenData.builder().setText("bright").setBefore(" ").setAfter("").setIndex(4).setBeginOffset(17).setEndOffset(23).setLemma("bright").setTag("JJ").setPos("ADJ").setHead(-1).setDependency("root").setIsAlpha(true).build(),
                TokenData.builder().setText(";").setBefore("").setAfter(" ").setIndex(5).setBeginOffset(23).setEndOffset(24).setLemma(";").setTag(":").setPos("PUNCT").setHead(4).setDependency("punct").setIsPunct(true).build(),
                TokenData.builder().setText("I").setBefore(" ").setAfter(" ").setIndex(6).setBeginOffset(25).setEndOffset(26).setLemma("I").setTag("PRP").setPos("PRON").setHead(8).setDependency("nsubj").setIsAlpha(true).build(),
                TokenData.builder().setText("can").setBefore(" ").setAfter(" ").setIndex(7).setBeginOffset(27).setEndOffset(30).setLemma("can").setTag("MD").setPos("VERB").setHead(8).setDependency("aux").setIsAlpha(true).build(),
                TokenData.builder().setText("read").setBefore(" ").setAfter(" ").setIndex(8).setBeginOffset(31).setEndOffset(35).setLemma("read").setTag("VB").setPos("VERB").setHead(4).setDependency("parataxis").setIsAlpha(true).build(),
                TokenData.builder().setText("a").setBefore(" ").setAfter(" ").setIndex(9).setBeginOffset(36).setEndOffset(37).setLemma("a").setTag("DT").setPos("DET").setHead(10).setDependency("det").setIsAlpha(true).build(),
                TokenData.builder().setText("book").setBefore(" ").setAfter(" ").setIndex(10).setBeginOffset(38).setEndOffset(42).setLemma("book").setTag("NN").setPos("NOUN").setHead(8).setDependency("obj").setIsAlpha(true).build(),
                TokenData.builder().setText("by").setBefore(" ").setAfter(" ").setIndex(11).setBeginOffset(43).setEndOffset(45).setLemma("by").setTag("IN").setPos("ADP").setHead(12).setDependency("case").setIsAlpha(true).build(),
                TokenData.builder().setText("it").setBefore(" ").setAfter("").setIndex(12).setBeginOffset(46).setEndOffset(48).setLemma("it").setTag("PRP").setPos("PRON").setHead(8).setDependency("obl").setIsAlpha(true).build(),
                TokenData.builder().setText(".").setBefore("").setAfter("").setIndex(13).setBeginOffset(48).setEndOffset(49).setLemma(".").setTag(".").setPos("PUNCT").setHead(4).setDependency("punct").setIsPunct(true).build()
        );
        assertExpectedSentence(text, expectedWords);
    }

    @Test
    public void emptyTextEmptySentence() {
        String text = "";
        Doc expected = Doc.EMPTY;
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

    @Test
    void testEmoji() {
        Doc doc = spacy.nlp("\uD83D\uDE1C");
        System.out.println(doc);
    }

    private void assertExpectedSentence(String text, List<TokenData> words) {
        Doc actual = spacy.nlp(text);
        assertEquals(text, actual.text());
        assertEquals(words, actual.tokenData());
    }
}
