package com.gmail.ta.tarvo.model;

import lombok.Data;

import java.util.List;

/**
 * Created by tarvo on 05-Apr-17.
 */
@Data
public class JsonNode {
    private String element;
    private String content;
    private JsonNodeAttribute attributes;
    private List<JsonNode> children;

    @Override
    public String toString() {
        return element + "{" +
                "content='" + content + '\'' +
                ", attributes=" + attributes +
                ", children=" + children +
                '}';
    }
}
