package com.example.bwengproj.util;

import com.fasterxml.jackson.core.JsonProcessingException;

import static com.example.bwengproj.BwengProjApplication.objectMapper;

public class Util {
    public static String toJson(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }

    public static <T> T toPojo(String json, Class<T> target) throws JsonProcessingException {
        return objectMapper.readValue(json, target);
    }
}
