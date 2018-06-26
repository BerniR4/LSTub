package helpers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * <h1>GestorAPI</h1>
 * GestorAPI és una classe Singleton que conté tots els mètodes necessaris per a interaccionar amb l'API de YouTube.
 *
 * @author  Albert Ferrando i Bernat Rovirosa
 * @version 1.0
 * @since   2018-07-22
 */
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

    /**
     * Constructor sense paràmetres.
     */
    private GestorAPI(){
    }

    /**
     * Getter de la instància del singleton.
     * @return Instància singleton.
     */
    public static GestorAPI getSharedInstance() {
        return sharedInstance;
    }

    /**
     * Aquest mètode s'encarrega de recuperar la informació de l'API a partir d'un paràmetre de cerca.
     * @param param Paràmetre de cerca per a fer la petició a l'API.
     * @param maxResults Indica el número de resultats que es volen obtenir. En cas de ser -1 tan sols se'n recuperaran 10.
     * @param nextPage En cas que sigui null voldrà dir que no s'afegirà el paràmetre "nextPage" a l'hora de fer la petició.
     *                 En cas contrari s'afegirà el paràmetre i el valor de nextPage serà el TOKEN per a obtenir la següent
     *                 pàgina de resultats
     * @return Retorna la informació retornada per l'API.
     */
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

    /**
     * Aquest mètode s'encarrega de recuperar la informació de l'API referent a un video en concret.
     * @param id Id del video en concret del qual es vol obtenir la informació.
     * @return Retorna la informació retornada per l'API.
     */
    public JsonObject getVideoInfo(String id) {
        String enllac = VIDEO_URL + id + API_KEY;
        return createConnection(enllac);
    }

    /**
     * Aquest mètode s'encarrega de recuperar la informació de l'API referent a un canal en concret.
     * @param id Id del canal en concret del qual es vol obtenir la informació.
     * @return Retorna la informació retornada per l'API.
     */
    public JsonObject getChannelInfo(String id) {
        String enllac = CHANNEL_URL + id + API_KEY;
        return createConnection(enllac);
    }

    /**
     * Aquest mètode s'encarrega de recuperar la informació de l'API referent a una llista de reproducció concreta.
     * @param id Id de la llista concreta de la qual es vol obtenir informació.
     * @return Retorna la informació retornada per l'API.
     */
    public JsonObject getPlaylistInfo(String id) {
        String enllac = PLAYLIST_URL + id + API_KEY;
        return createConnection(enllac);
    }

    /**
     * Aquest mètode s'encarrega de recuperar la informació de l'API referent als videos d'una llista de reproducció
     * concreta.
     * @param id Id de la llista concreta de la qual es volen obtenir els videos.
     * @param nextPage En cas que el valor de nextPage sigui "no", voldrà dir que no s'afegirà el paràmetre de nextPage
     *                 a la petició, en canvi, si nextPage té un valor diferent voldrà dir que s'haurà d'afegir el paràmetre
     *                 nextPage i el valor d'aquesta variable serà el token.
     * @return Retorna la informació retornada per l'API.
     */
    JsonObject getPlaylistItemsInfo(String id, String nextPage) {
        if(nextPage.equals("no")) {
            String enllac = PLAYLIST_ITEMS_URL + id + API_KEY;
            return createConnection(enllac);
        } else {
            String enllac = PLAYLIST_ITEMS_URL + id + "&" + NEXT_PAGE + nextPage + API_KEY;
            return createConnection(enllac);
        }
    }

    /**
     * Aquest mètode s'encarrega de crear una connexió amb l'API a partir d'un enllaç. A més, un cop crea la connexió
     * llegeix la informació retornada per l'API.
     * @param enllac Enllaç en concret a partir del qual volem fer la petició.
     * @return Informació en forma de JSONObject rebuda des de l'API. Si és null vol dir que hi ha hagut un error en la connexió.
     */
    private JsonObject createConnection(String enllac) {
        try {
            URL url = new URL(enllac);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            Gson gson = new Gson();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            return gson.fromJson(br, JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
