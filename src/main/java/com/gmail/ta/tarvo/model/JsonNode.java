package com.gmail.ta.tarvo.model;

import com.google.gson.JsonObject;
import lombok.Data;

import java.util.List;

/**
 * Created by tarvo on 05-Apr-17.
 */
@Data
public class JsonNode {
    private String element;
    private String content;
    private JsonObject attributes;
    private List<JsonNode> children;

    @Override
    public String toString() {
        return element + "{" +
                "txt='" + content + '\'' +
                ", attrs=" + attributes +
                ", children=" + children +
                '}';
    }
}
