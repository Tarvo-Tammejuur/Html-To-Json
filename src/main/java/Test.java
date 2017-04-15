import com.google.gson.JsonObject;
import com.gmail.ta.tarvo.HtmlToJson;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by tarvo on 04-Apr-17.
 */
public class Test {
    public static void main(String[] args) {
        try {
            JsonObject json = new HtmlToJson("<!DOCTYPE html>\n" +
                    "<html asd=2 id=33>\n" +
                    "<head id=3>\n" +
                    "<title>Page Title</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "<h1 id=2>This is a Heading</h1>\n" +
                    "<p>This is a paragraph.</p>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>").transform();

            String jsonStr = HtmlToJson.asJsonNode(json).toString();
            System.out.println(jsonStr);
            System.out.println("size: " + jsonStr.length());

            String jsonStr2 = HtmlToJson.asFormattedJsonString(json);
            System.out.println(jsonStr2);
            System.out.println("Size: " + jsonStr2.length());

            String jsonStr3 = json.toString();
            System.out.println(jsonStr3);
            System.out.println("Size: " + jsonStr3.length());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
