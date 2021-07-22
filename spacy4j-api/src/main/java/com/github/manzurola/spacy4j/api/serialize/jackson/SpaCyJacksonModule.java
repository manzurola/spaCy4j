package com.github.manzurola.spacy4j.api.serialize.jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.manzurola.spacy4j.api.containers.Doc;

import static com.fasterxml.jackson.annotation.PropertyAccessor.*;

public final class SpaCyJacksonModule extends SimpleModule {

    public SpaCyJacksonModule() {
        ObjectMapper mapper = new ObjectMapper()
                .setVisibility(ALL, JsonAutoDetect.Visibility.NONE)
                .setVisibility(CREATOR, JsonAutoDetect.Visibility.ANY)
                .setVisibility(FIELD, JsonAutoDetect.Visibility.ANY)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        addSerializer(Doc.class, new DocSerializer(mapper));
        addDeserializer(Doc.class, new DocDeserializer(mapper));
    }
}
