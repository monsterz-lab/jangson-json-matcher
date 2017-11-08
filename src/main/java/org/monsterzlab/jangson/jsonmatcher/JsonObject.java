package org.monsterzlab.jangson.jsonmatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonObject extends HashMap implements JsonData{

    private final ValueType valueType;
    public JsonObject(Map map, ValueType valueType) {
        this.valueType = valueType;

        map.forEach((k, v) ->{
            if(v instanceof Map){
                this.put(k, new JsonObject((Map)v, valueType));
            }else if(v instanceof ArrayList){
                this.put(k, new JsonArray((ArrayList)v, valueType));
            }else{
                this.put(k, v);
            }
        });
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof JsonObject)){
            return false;
        }
        JsonObject expected = this;
        JsonObject actual = (JsonObject)o;
        if(actual.valueType == ValueType.EXPECTED){
            expected = actual;
            actual = this;
        }
        return actual.entrySet().containsAll(expected.entrySet());
    }
}
