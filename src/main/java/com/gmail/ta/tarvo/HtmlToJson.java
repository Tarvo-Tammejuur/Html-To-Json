package com.gmail.ta.tarvo;

import com.gmail.ta.tarvo.model.JsonNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * <h3>Class for transforming Document (any DOM-compatible file) to JSON</h3>
 *
 * @author Tarvo Tammejuur
 * @version 0.1
 */
public class HtmlToJson {
    private Document doc;

    /**
     * Initialises the Document for transformation
     *
     * @param jsoupDocument DOM-compatible document(XML, HTML, etc)
     */
    public HtmlToJson(Document jsoupDocument) {
        this.doc = jsoupDocument;
    }

    /**
     * Initialises the Document for transformation
     *
     * @param html DOM-compatible String source
     */
    public HtmlToJson(String html) {
        this.doc = Jsoup.parse(html);
    }

    /**
     * Initialises the Document for transformation
     *
     * @param url web page
     */
    public HtmlToJson(URL url) {
        try {
            this.doc = Jsoup.parse(url, 10000); //10 second timeout
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialises the Document for transformation
     *
     * @param file local DOM compatible file
     */
    public HtmlToJson(File file) {
        try {
            this.doc = Jsoup.parse(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method for getting a formatted JSON string
     *
     * @param jsonObj one-line json
     * @return Formatted json String
     */
    public static String asFormattedJsonString(JsonObject jsonObj) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(jsonObj);
    }

    /**
     * Helper method for easier json navigation.
     * No need to remember actual json object/ attribute/ array node names.
     *
     * @param jsonObj one-line json
     * @return JsonNode object for easier json navigation
     */
    public static JsonNode asJsonNode(JsonObject jsonObj) {
        GsonBuilder gb = new GsonBuilder();
        Gson gson = gb.create();
        return gson.fromJson(jsonObj, JsonNode.class); // map it to JsonNode
    }

    /**
     * Starting point for HTML(DOM) to JSON transformation
     *
     * @return new json object containing DOM structure
     */
    public JsonObject transform() {
        JsonObject root = new JsonObject();
        Element rootElem = doc.child(0);
        root.addProperty("element", rootElem.tagName());
        root.add("children", new JsonArray());

        traverseAllChildren(root, rootElem);
        return root;
    }

    /**
     * Traverses all DOM (child) elements and appends them to json object
     *
     * @param jsonParent parent
     * @param elem       dom element
     */
    private void traverseAllChildren(JsonObject jsonParent, Element elem) {
        for (Element e : elem.children()) {
            JsonObject jsonChild = new JsonObject();

            jsonChild.addProperty("element", e.tagName());

            if(!e.ownText().equals("")) {
                jsonChild.addProperty("content", e.ownText());
            }
            if (e.attributes().asList().size() > 0) {
                JsonObject elemAttrs = getAttributesForElementAsJson(e);
                jsonChild.add("attributes", elemAttrs);
            }

            jsonChild.add("children", new JsonArray());

            jsonParent.getAsJsonArray("children").add(jsonChild);

            if (e.children().size() > 0) {
                traverseAllChildren(jsonChild, e); //param 'jsonChild' as new parent
            } else {
                jsonChild.remove("children");
            }
        }
    }

    /**
     * Traverses through DOM element attributes and appends them to json object
     *
     * @param elem target to extract attributes from
     * @return all attributes from element as json
     */
    private JsonObject getAttributesForElementAsJson(Element elem) {
        List<Attribute> elemAttrs = elem.attributes().asList();
        JsonObject attrs = new JsonObject();

        for (Attribute elemAttr : elemAttrs) {
            attrs.addProperty(elemAttr.getKey(), elemAttr.getValue());
        }
        return attrs;
    }
}
