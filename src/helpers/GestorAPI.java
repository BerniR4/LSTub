import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class GestorAPI {
    private static final String YT_URL = "https://www.googleapis.com/youtube/v3/search?part=";
    private static final String API_KEY = "&key=AIzaSyD3FSLnYCS3MxLg9MbWVVjOtxPmyhEG7OA";
    private static final String SNIPPET = "snippet&q=";
    private static final String MAX_RESULTS = "&maxResults=";

    private static GestorAPI sharedInstance = new GestorAPI();

    private GestorAPI(){
    }

    public static GestorAPI getSharedInstance() {
        return sharedInstance;
    }

    //TODO Cas 1 i 2: necessiten resultats d'una cerca (la 1 en necessita 3 només)

    public JsonArray getResultList(String param, int maxResults) {
        String enllac;

        if (maxResults == -1) {
            enllac = YT_URL + SNIPPET + param + API_KEY;
        }else {
            enllac = YT_URL + SNIPPET + param + MAX_RESULTS + maxResults + API_KEY;
        }

        try {
            URL url = new URL(enllac);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            Gson gson = new Gson();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            JsonObject jsonObject = gson.fromJson(br, JsonObject.class);
            return jsonObject.getAsJsonArray("items");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


}
