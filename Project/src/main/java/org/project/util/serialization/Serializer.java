package org.project.util.serialization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Serializer<T> {

    private int serializationIndex = 1;
    private  ObjectMapper objectMapper = new ObjectMapper();

    public String serialize(T t) throws IOException {
        objectMapper.registerModule(new JavaTimeModule());
        System.out.println(objectMapper.writeValueAsString(t));
        return objectMapper.writeValueAsString(t);
    }

    public T deserialize(File file, Class<T> tClass) throws IOException {
        return objectMapper.readerFor(tClass).readValue(file);
    }

    public List<T> deserialize(File file) throws IOException {
        return objectMapper.readerFor(new TypeReference<List<T>>() {}).readValue(file);
    }

    public T deserialize(String json, Class<T> tClass) throws IOException {
        return objectMapper.readerFor(tClass).readValue(json, tClass);
    }

}
