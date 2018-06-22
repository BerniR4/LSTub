package helpers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class GestorAPI {
    private static final String YT_URL = "https://www.googleapis.com/youtube/v3/search?";
    private static final String NEXT_PAGE = "pageToken=";
    private static final String API_KEY = "&key=AIzaSyD3FSLnYCS3MxLg9MbWVVjOtxPmyhEG7OA";
    private static final String SNIPPET = "part=snippet&q=";
    private static final String MAX_RESULTS = "&maxResults=";
    private static final String VIDEO_URL = "https://www.googleapis.com/youtube/v3/videos?part=id,statistics&id=";
    private static final String CHANNEL_URL = "https://www.googleapis.com/youtube/v3/channels?part=id,statistics&id=";
    private static final String PLAYLIST_ITEMS_URL = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&" +
            "maxResults=50&playlistId=";
    private static final String PLAYLIST_URL = "https://www.googleapis.com/youtube/v3/playlists?part=id,snippet&id=";

    private static GestorAPI sharedInstance = new GestorAPI();

    private GestorAPI(){
    }

    public static GestorAPI getSharedInstance() {
        return sharedInstance;
    }

    public JsonObject getResult(String param, int maxResults, String nextPage) {
        String enllac;
        if (nextPage == null) {
            if (maxResults == -1) {
                enllac = YT_URL + SNIPPET + param + MAX_RESULTS + 10 + API_KEY;
            } else {
                enllac = YT_URL + SNIPPET + param + MAX_RESULTS + maxResults + API_KEY;
            }
        }else {
            enllac = YT_URL + NEXT_PAGE + nextPage + "&" + SNIPPET + param + MAX_RESULTS + 10 + API_KEY;
        }
        return createConnection(enllac);
    }

    public JsonObject getVideoInfo(String id) {
        String enllac = VIDEO_URL + id + API_KEY;
        return createConnection(enllac);
    }

    public JsonObject getChannelInfo(String id) {
        String enllac = CHANNEL_URL + id + API_KEY;
        return createConnection(enllac);
    }

    public JsonObject getPlaylistInfo(String id) {
        String enllac = PLAYLIST_URL + id + API_KEY;
        return createConnection(enllac);
    }

    public JsonObject getPlaylistItemsInfo(String id, String nextPage) {
        if(nextPage.equals("no")) {
            String enllac = PLAYLIST_ITEMS_URL + id + API_KEY;
            return createConnection(enllac);
        } else {
            String enllac = PLAYLIST_ITEMS_URL + id + "&" + NEXT_PAGE + nextPage + API_KEY;
            return createConnection(enllac);
        }
    }

    private JsonObject createConnection(String enllac) {
        try {
            URL url = new URL(enllac);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            Gson gson = new Gson();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            JsonObject jsonObject = gson.fromJson(br, JsonObject.class);
            return jsonObject;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
