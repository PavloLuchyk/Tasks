package com.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Serializator {

    private int serializationIndex = 1;

    public void serialize(Object t) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String fileName = Arrays.stream(t.getClass().toString().replace('.', ' ').split(" "))
                .filter(s -> Character.isUpperCase(s.charAt(0)))
                .findFirst()
                .get() + serializationIndex + ".json";
        System.out.println(fileName);
        objectMapper.writeValue(new File("json/" + fileName), t);
        serializationIndex++;
    }

}
