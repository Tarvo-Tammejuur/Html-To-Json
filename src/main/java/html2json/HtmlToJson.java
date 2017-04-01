package html2json;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;
/**
 * <h3>Class for transforming Document (any DOM-compatible file) to JSON</h3>
 * You can use <b>{@link org.jsoup.Jsoup}</b> to generate a Document object from a webpage/ local HTML file/ String source/ etc.
 * @author Tarvo Tammejuur
 * @version 0.1
 */
public class HtmlToJson {
    private Document doc;
    private JsonObject root;

    /**
     * Initialises the Document for transformation
     * @param jsoupDocument DOM-compatible document(XML, HTML, etc)
     */
    public HtmlToJson(Document jsoupDocument) {
        this.doc = jsoupDocument;
    }

    /**
     * Makes the actual transformation from HTML(DOM) to JSON
     * @return 'this' to conveniently call 'asXXX' methods on it
     */
    public HtmlToJson transform() {
        root = new JsonObject();
        Element rootElem = doc.child(0);
        root.addProperty("element", rootElem.tagName());
        root.addProperty("content", rootElem.ownText());
        root.add("attributes", getAttributesForElement(rootElem));
        root.add("children", new JsonArray());

        traverseAllChildren(root, rootElem);
        return this;
    }

    private void traverseAllChildren(JsonObject jsonParent, Element elem) {
        for (Element e : elem.children()) {
            JsonObject jsonChild = new JsonObject();
            JsonObject elemAttrs = getAttributesForElement(e);

            jsonChild.addProperty("element", e.tagName());
            jsonChild.addProperty("content", e.ownText());
            jsonChild.add("attributes", elemAttrs);
            jsonChild.add("children", new JsonArray());

            jsonParent.getAsJsonArray("children").add(jsonChild);

            if (e.children().size() > 0) {
                traverseAllChildren(jsonChild, e); //param 'jsonChild' as new parent
            }
        }
    }

    private JsonObject getAttributesForElement(Element elem) {
        List<Attribute> elemAttrs = elem.attributes().asList();
        JsonObject attrs = new JsonObject();

        for (Attribute elemAttr : elemAttrs) {
            attrs.addProperty(elemAttr.getKey(), elemAttr.getValue());
        }

        return attrs;
    }

    /**
     * For displaying a formatted JSON string
     * @return Pretty-print String
     * @throws RuntimeException when 'transform' method is not yet called
     */
    public String asFormattedJsonString() {
        if (root != null) {
            return new GsonBuilder().setPrettyPrinting().create().toJson(root);
        }
        throw new RuntimeException("Json not yet transformed, call 'transform()' first.");
    }

    /**
     * For further JSON manipulation
     * @return Root of JSON nodes
     * @throws RuntimeException when 'transform' method is not yet called
     */
    public JsonObject asJsonObject() {
        if (root != null) {
            return root;
        }
        throw new RuntimeException("Json not yet transformed, call 'transform()' first.");
    }
}
