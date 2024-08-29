package com.example.boost.exeutor.entity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Parameter {

    List<Value> values;

    public Parameter(List<Map<String, Object>> parameters) {
        this.values = parameters.stream().map(Value::new).collect(Collectors.toList());
    }

    public List<Value> getInput() {
        return values.stream().filter(v -> "in".equals(v.getType())).collect(Collectors.toList());
    }

    public static class Value {

        String name;

        String type;

        Boolean isMust = false;

        Boolean isJson = false;

        Boolean isMulit = false;

        public Value(Map<String, Object> value) {
            this.name = (String) value.get("name");
            this.type = (String) value.get("type");
            this.isMust = (Boolean) value.get("isMust");
            this.isJson = (Boolean) value.get("isJson");
            this.isMulit = (Boolean) value.get("isMulit");
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public Boolean getMust() {
            return isMust;
        }

        public Boolean getJson() {
            return isJson;
        }

        public Boolean getMulit() {
            return isMulit;
        }
    }
}
