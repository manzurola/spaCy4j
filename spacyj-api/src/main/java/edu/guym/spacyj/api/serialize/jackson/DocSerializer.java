package edu.guym.spacyj.api.serialize.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import edu.guym.spacyj.api.containers.Doc;
import edu.guym.spacyj.api.containers.TokenData;

import java.io.IOException;

public class DocSerializer extends StdSerializer<Doc> {

    private final ObjectMapper mapper;

    public DocSerializer(ObjectMapper mapper) {
        this(Doc.class, mapper);
    }

    public DocSerializer(Class<Doc> t, ObjectMapper mapper) {
        super(t);
        this.mapper = mapper;
    }

    @Override
    public void serialize(Doc doc,
                          JsonGenerator jgen,
                          SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("text", doc.text());
        jgen.writeArrayFieldStart("tokens");

        for (TokenData token : doc.tokenData()) {
            mapper.writeValue(jgen, token);
        }
        jgen.writeEndArray();
    }
}
