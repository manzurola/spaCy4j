package io.github.manzurola.spacy4j.api.serialize.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.github.manzurola.spacy4j.api.containers.Doc;
import io.github.manzurola.spacy4j.api.containers.Span;
import io.github.manzurola.spacy4j.api.containers.TokenData;

import java.io.IOException;
import java.util.List;

class DocSerializer extends StdSerializer<Doc> {

    private final ObjectMapper mapper;

    DocSerializer(ObjectMapper mapper) {
        this(Doc.class, mapper);
    }

    DocSerializer(Class<Doc> t, ObjectMapper mapper) {
        super(t);
        this.mapper = mapper;
    }

    @Override
    public void serialize(Doc doc,
                          JsonGenerator jgen,
                          SerializerProvider provider) throws IOException {
        JsonDoc jsonDoc = new JsonDoc();
        jsonDoc.text = doc.text();
        for (Span span : doc.sentences()) {
            List<TokenData> data = span.data();
            JsonSentence sentence = new JsonSentence();

            sentence.text = span.text();
            for (TokenData datum : data) {
                JsonToken token = new JsonToken();
                token.text = datum.text();
                token.beginOffset = datum.beginOffset();
                token.endOffset = datum.endOffset();
                token.dependency = datum.dependency();
                token.head = datum.head();
                token.index = datum.index();
                token.isPunct = datum.isPunct();
                token.lemma = datum.lemma();
                token.likeNum = datum.likeNum();
                token.pos = datum.pos();
                token.tag = datum.tag();
                token.sentenceStart = datum.isSentenceStart();
                token.whitespaceBefore = datum.whitespaceBefore();
                token.whitespaceAfter = datum.whitespaceAfter();

                sentence.tokens.add(token);
            }
            jsonDoc.sentences.add(sentence);
        }

        mapper.writeValue(jgen, jsonDoc);
    }
}
