package edu.guym.spacyj.api.serialize.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import edu.guym.spacyj.api.containers.Doc;
import edu.guym.spacyj.api.containers.TokenData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DocDeserializer extends StdDeserializer<Doc> {

    private final ObjectMapper mapper;

    public DocDeserializer(ObjectMapper mapper) {
        this(Doc.class, mapper);
    }

    public DocDeserializer(Class<Doc> t, ObjectMapper mapper) {
        super(t);
        this.mapper = mapper;
    }

    @Override
    public Doc deserialize(JsonParser parser,
                           DeserializationContext context) throws IOException {

        if (parser.currentToken() != JsonToken.START_OBJECT) {
            throw new IllegalStateException("Expected content to be an object");
        }

        String text = "";
        List<TokenData> tokens = List.of();
        for (int i = 0; i < 2; i++) {
            String fieldName = parser.nextFieldName();
            switch (fieldName) {
                case "text":
                    text = parser.nextTextValue();
                    break;
                case "tokens":
                    tokens = readTokenArray(parser);
                    break;
                default:
                    throw new RuntimeException("Expected fields 'text' and 'tokens'");
            }
        }
        return Doc.create(text, tokens);
    }

    private List<TokenData> readTokenArray(JsonParser parser) throws IOException {
        if (parser.nextToken() != JsonToken.START_ARRAY) {
            throw new IllegalStateException("Expected content to be an array");
        }

        List<TokenData> tokens = new ArrayList<>();
        while (parser.nextToken() != JsonToken.END_ARRAY) {
            TokenData tokenData = mapper.readValue(parser, TokenData.class);
            tokens.add(tokenData);
        }
        return tokens;
    }
}
