package org.monsterzlab.jangson.jsonmatcher;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

//  정 온 유
public class JsonMatcher {

    public static JsonData expected(String json) {
        return create(json, ValueType.EXPECTED);
    }

    public static JsonData actual(String json) {
        return create(json, ValueType.ACTUAL);
    }

    public static boolean match(String expectedJson, String actualJson){
        JsonData expected = expected(expectedJson);
        JsonData actual = actual(actualJson);

        return match(expected, actual);
    }

    public static boolean match(JsonData expected, JsonData actual){
        if(expected instanceof JsonObject && actual instanceof  JsonObject){
            JsonObject e = (JsonObject) expected;
            JsonObject a = (JsonObject) actual;
            return a.entrySet().containsAll(e.entrySet());
        }

        if(expected instanceof JsonArray && actual instanceof  JsonArray){
            JsonArray e = (JsonArray) expected;
            JsonArray a = (JsonArray) actual;
            return a.containsAll(e);
        }

        return false;
    }


    private static JsonData create(String json, ValueType valueType){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);

        try {
            Object value = objectMapper.readValue(json, Object.class);
            if(value instanceof Map){
                return new JsonObject((Map)value, valueType);
            }else if(value instanceof ArrayList){
                return new JsonArray((ArrayList)value, valueType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }
}
