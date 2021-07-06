package io.languagetoys.spacy4j.api.serialize.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.languagetoys.spacy4j.api.containers.Doc;
import io.languagetoys.spacy4j.api.containers.TokenData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SpaCyJacksonModuleTest {

    @Test
    void testJson() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new SpaCyJacksonModule());

        Doc expected = testDoc();
        String json = mapper.writeValueAsString(expected);
        Doc actual = mapper.readValue(json, Doc.class);
        Assertions.assertEquals(expected, actual);

    }

    private Doc testDoc() {
        List<TokenData> tokens = Arrays.asList(
                TokenData
                        .builder()
                        .setText("The")
                        .setBefore("")
                        .setAfter(" ")
                        .setIndex(0)
                        .setBeginOffset(0)
                        .setEndOffset(3)
                        .setLemma("the")
                        .setTag("DT")
                        .setPos("DET")
                        .setHead(1)
                        .setDependency("det")
                        .setSentenceStart(true)
                        .build(),
                TokenData
                        .builder()
                        .setText("moon")
                        .setBefore(" ")
                        .setAfter(" ")
                        .setIndex(1)
                        .setBeginOffset(4)
                        .setEndOffset(8)
                        .setLemma("moon")
                        .setTag("NN")
                        .setPos("NOUN")
                        .setHead(4)
                        .setDependency("nsubj")
                        .build(),
                TokenData
                        .builder()
                        .setText("is")
                        .setBefore(" ")
                        .setAfter(" ")
                        .setIndex(2)
                        .setBeginOffset(9)
                        .setEndOffset(11)
                        .setLemma("be")
                        .setTag("VBZ")
                        .setPos("VERB")
                        .setHead(4)
                        .setDependency("cop")
                        .build(),
                TokenData
                        .builder()
                        .setText("very")
                        .setBefore(" ")
                        .setAfter(" ")
                        .setIndex(3)
                        .setBeginOffset(12)
                        .setEndOffset(16)
                        .setLemma("very")
                        .setTag("RB")
                        .setPos("ADV")
                        .setHead(4)
                        .setDependency("advmod")
                        .build(),
                TokenData
                        .builder()
                        .setText("bright")
                        .setBefore(" ")
                        .setAfter("")
                        .setIndex(4)
                        .setBeginOffset(17)
                        .setEndOffset(23)
                        .setLemma("bright")
                        .setTag("JJ")
                        .setPos("ADJ")
                        .setHead(-1)
                        .setDependency("root")
                        .build(),
                TokenData
                        .builder()
                        .setText(";")
                        .setBefore("")
                        .setAfter(" ")
                        .setIndex(5)
                        .setBeginOffset(23)
                        .setEndOffset(24)
                        .setLemma(";")
                        .setTag(":")
                        .setPos("PUNCT")
                        .setHead(4)
                        .setDependency("punct")
                        .setIsPunct(true)
                        .build(),
                TokenData
                        .builder()
                        .setText("I")
                        .setBefore(" ")
                        .setAfter(" ")
                        .setIndex(6)
                        .setBeginOffset(25)
                        .setEndOffset(26)
                        .setLemma("I")
                        .setTag("PRP")
                        .setPos("PRON")
                        .setHead(8)
                        .setDependency("nsubj")
                        .build(),
                TokenData
                        .builder()
                        .setText("can")
                        .setBefore(" ")
                        .setAfter(" ")
                        .setIndex(7)
                        .setBeginOffset(27)
                        .setEndOffset(30)
                        .setLemma("can")
                        .setTag("MD")
                        .setPos("VERB")
                        .setHead(8)
                        .setDependency("aux")
                        .build(),
                TokenData
                        .builder()
                        .setText("read")
                        .setBefore(" ")
                        .setAfter(" ")
                        .setIndex(8)
                        .setBeginOffset(31)
                        .setEndOffset(35)
                        .setLemma("read")
                        .setTag("VB")
                        .setPos("VERB")
                        .setHead(4)
                        .setDependency("parataxis")
                        .build(),
                TokenData
                        .builder()
                        .setText("a")
                        .setBefore(" ")
                        .setAfter(" ")
                        .setIndex(9)
                        .setBeginOffset(36)
                        .setEndOffset(37)
                        .setLemma("a")
                        .setTag("DT")
                        .setPos("DET")
                        .setHead(10)
                        .setDependency("det")
                        .build(),
                TokenData
                        .builder()
                        .setText("book")
                        .setBefore(" ")
                        .setAfter(" ")
                        .setIndex(10)
                        .setBeginOffset(38)
                        .setEndOffset(42)
                        .setLemma("book")
                        .setTag("NN")
                        .setPos("NOUN")
                        .setHead(8)
                        .setDependency("obj")
                        .build(),
                TokenData
                        .builder()
                        .setText("by")
                        .setBefore(" ")
                        .setAfter(" ")
                        .setIndex(11)
                        .setBeginOffset(43)
                        .setEndOffset(45)
                        .setLemma("by")
                        .setTag("IN")
                        .setPos("ADP")
                        .setHead(12)
                        .setDependency("case")
                        .build(),
                TokenData
                        .builder()
                        .setText("it")
                        .setBefore(" ")
                        .setAfter("")
                        .setIndex(12)
                        .setBeginOffset(46)
                        .setEndOffset(48)
                        .setLemma("it")
                        .setTag("PRP")
                        .setPos("PRON")
                        .setHead(8)
                        .setDependency("obl")
                        .build(),
                TokenData
                        .builder()
                        .setText(".")
                        .setBefore("")
                        .setAfter("")
                        .setIndex(13)
                        .setBeginOffset(48)
                        .setEndOffset(49)
                        .setLemma(".")
                        .setTag(".")
                        .setPos("PUNCT")
                        .setHead(4)
                        .setDependency("punct")
                        .setIsPunct(true)
                        .build()
        );
        return Doc.create(tokens);
    }

    @Test
    void testJson2() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new SpaCyJacksonModule());

        String text = "My head feels like a frisbee. Twice it's normal size. It feels like a football.";


//        String json = mapper.writeValueAsString(expected);
//        Doc actual = mapper.readValue(json, Doc.class);
//        Assertions.assertEquals(expected, actual);
    }
}
