package com.tars.capitalmarket.json;

import io.micronaut.serde.ObjectMapper;
import io.vavr.control.Try;
import jakarta.inject.Singleton;

@Singleton
public class JsonMapper {

    private ObjectMapper objectMapper;

    public JsonMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> String serialize(T t) {
        return Try.of(() -> objectMapper.writeValueAsString(t))
                .onFailure(Throwable::printStackTrace)
                .getOrElseThrow(() -> new RuntimeException("Couldn't serialize object to json string"));
    }

    public <T> byte[] serializeToBytes(T t) {
        return Try.of(() -> objectMapper.writeValueAsBytes(t))
            .getOrElseThrow(() -> new RuntimeException("Couldn't serialize object to json bytes"));
    }

    public <T> T deserialize(String json, Class<T> type) {
        return Try.of(() -> objectMapper.readValue(json, type))
                .onFailure(e -> e.printStackTrace())
                .getOrElseThrow(() -> new RuntimeException("Couldn't serialize object to json bytes"));
    }
}
