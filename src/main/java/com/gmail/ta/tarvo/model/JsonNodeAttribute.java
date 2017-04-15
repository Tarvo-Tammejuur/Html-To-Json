package com.gmail.ta.tarvo.model;

import lombok.Data;

/**
 * Created by tarvo on 15-Apr-17.
 */
@Data
public class JsonNodeAttribute {
    String key;
    String value;

    @Override
    public String toString() {
        return "{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
