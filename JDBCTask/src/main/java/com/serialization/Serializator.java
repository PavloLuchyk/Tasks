package com.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Serializator<T> {

    private int serializationIndex = 1;
    private  ObjectMapper objectMapper = new ObjectMapper();

    public void serialize(T t) throws IOException {
        objectMapper.registerModule(new JavaTimeModule());
        String fileName = Arrays.stream(t.getClass().toString().replace('.', ' ').split(" "))
                .filter(s -> Character.isUpperCase(s.charAt(0)))
                .findFirst()
                .get() + serializationIndex + ".json";
        System.out.println(fileName);
        objectMapper.writeValue(new File("json/" + fileName), t);
        serializationIndex++;
    }

    public T deserialize(File file, Class<T> tClass) throws IOException {
        return objectMapper.readerFor(tClass).readValue(file);
    }

}
