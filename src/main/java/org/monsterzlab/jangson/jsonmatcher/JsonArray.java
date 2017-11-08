package org.monsterzlab.jangson.jsonmatcher;

import java.util.ArrayList;
import java.util.Map;

public class JsonArray extends ArrayList implements JsonData {

    private final ValueType valueType;

    public JsonArray(ArrayList list, ValueType valueType) {
        this.valueType = valueType;
        for(Object value : list){
            if(value instanceof Map){
                this.add(new JsonObject((Map)value, valueType));
            }else if(value instanceof ArrayList){
                this.add(new JsonArray((ArrayList)value, valueType));
            }else{
                this.add(value);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof JsonArray)){
            return false;
        }
        JsonArray expected = this;
        JsonArray actual = (JsonArray)o;
        if(actual.valueType == ValueType.EXPECTED){
            expected = actual;
            actual = this;
        }

        return actual.containsAll(expected);
    }

}
