package edu.guym.spacyj.api.serialize.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.guym.spacyj.api.containers.Doc;
import edu.guym.spacyj.api.containers.TokenData;
import edu.guym.spacyj.api.utils.TokenTextPrinter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SpacyJacksonModuleTest {

    @Test
    void testJson() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new SpacyJacksonModule());

        Doc expected = testDoc();
        String json = mapper.writeValueAsString(expected);
        Doc actual = mapper.readValue(json, Doc.class);
        Assertions.assertEquals(expected, actual);

    }

    private Doc testDoc() {
        List<TokenData> tokens = Arrays.asList(
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
        String text = TokenTextPrinter.getInstance().print(tokens);
        return Doc.create(text, tokens);
    }
}