package io.github.manzurola.spacy4j.api.serialize.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.github.manzurola.spacy4j.api.containers.Doc;
import io.github.manzurola.spacy4j.api.containers.TokenData;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class DocDeserializer extends StdDeserializer<Doc> {

    private final ObjectMapper mapper;

    DocDeserializer(ObjectMapper mapper) {
        this(Doc.class, mapper);
    }

    DocDeserializer(Class<Doc> t, ObjectMapper mapper) {
        super(t);
        this.mapper = mapper;
    }

    @Override
    public Doc deserialize(JsonParser parser,
                           DeserializationContext context) throws IOException {

        JsonDoc jsonDoc = mapper.readValue(parser, JsonDoc.class);

        String text = jsonDoc.text;
        List<TokenData> data = jsonDoc.sentences
                .stream()
                .map(sentence -> sentence.tokens)
                .flatMap(Collection::stream)
                .map(token -> TokenData.builder()
                        .setText(token.text)
                        .setIsPunct(token.isPunct)
                        .setLikeNum(token.likeNum)
                        .setAfter(token.whitespaceAfter)
                        .setBefore(token.whitespaceBefore)
                        .setBeginOffset(token.beginOffset)
                        .setEndOffset(token.endOffset)
                        .setDependency(token.dependency)
                        .setHead(token.head)
                        .setIndex(token.index)
                        .setLemma(token.lemma)
                        .setPos(token.pos)
                        .setTag(token.tag)
                        .setSentenceStart(token.sentenceStart)
                        .build()
                )
                .collect(Collectors.toList());

        return Doc.create(text, data);
    }
}
