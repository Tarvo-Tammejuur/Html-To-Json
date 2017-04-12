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
            JsonObject json = new HtmlToJson(new URL("http://www.google.com")).transform();
            System.out.println(HtmlToJson.asFormattedJsonString(json));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
