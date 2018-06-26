package helpers;

import com.google.gson.*;
import model.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * <h1>GestorJSON</h1>
 * GestorJSON és una classe Singleton que conté tots els mètodes necessaris per a llegir i escriure arxius en format
 * JSON.
 *
 * @author  Albert Ferrando i Bernat Rovirosa
 * @version 1.0
 * @since   2018-07-22
 */
public class GestorJSON {
    private static final String FILEPATH = "data" + System.getProperty("file.separator") + "favoriteResults.json";
    private static final String VIDEO = "youtube#video";
    private static final String CANAL = "youtube#channel";
    private static final String LLISTA = "youtube#playlist";

    private static GestorJSON sharedInstance = new GestorJSON();

    /**
     * Constructor sense paràmetres.
     */
    private GestorJSON() {
    }

    /**
     * Getter de la instància del singleton.
     * @return Instància singleton.
     */
    public static GestorJSON getSharedInstance() {
        return sharedInstance;
    }

    /**
     * Mètode que s'encarrega de transformar un jsonObject en un arraylist de resultats.
     * @param jsonObject JSONObject que conté la informació dels resultats que anirem parsejant.
     * @return Arraylist de resultats trets a partir del jsonObject.
     */
    public ArrayList<Resultat> getResultList(JsonObject jsonObject) {
        ArrayList<Resultat> resultats = new ArrayList<>();
        JsonArray items = jsonObject.getAsJsonArray("items");
        for (int i = 0; i < items.size(); i++) {
            Resultat parsed = new Resultat();
            JsonObject unparsed = items.get(i).getAsJsonObject();
            switch (unparsed.getAsJsonObject("id").get("kind").getAsString()) {
                case VIDEO:
                    parsed.setTipus("Video");
                    parsed.setId(unparsed.getAsJsonObject("id").get("videoId").getAsString());
                    break;

                case CANAL:
                    parsed.setTipus("Canal");
                    parsed.setId(unparsed.getAsJsonObject("id").get("channelId").getAsString());
                    break;

                case LLISTA:
                    parsed.setTipus("Llista de reproducció");
                    parsed.setId(unparsed.getAsJsonObject("id").get("playlistId").getAsString());
                    break;
            }
            unparsed = unparsed.getAsJsonObject("snippet");
            parsed.setTitol(unparsed.get("title").getAsString());
            parsed.setCanal(unparsed.get("channelTitle").getAsString());
            parsed.setDescripcio(unparsed.get("description").getAsString());
            parsed.setThumbnail(unparsed.getAsJsonObject("thumbnails").getAsJsonObject("medium").get("url").getAsString());
            resultats.add(parsed);
        }
        return resultats;
    }

    /**
     * Aquest mètode s'encarrega d'obtenir el token de la següent pàgina de resultats.
     * @param result JSONObject de resultats del qual es vol obtenir el token de la següent pàgina de resultats.
     * @return Token de la següent pàgina de resultats.
     */
    public String getNextPageCode(JsonObject result) {
        return result.get("nextPageToken").getAsString();
    }

    /**
     * Aquest mètode s'encarrega d'escriure en format json al fitxer favoriteResults tots els preferits afegits fins al moment.
     * @param preferits Classe PreferitsManager que conté tots els preferits afegits fins al moment.
     */
    public void saveFile(PreferitsManager preferits){
        Gson gson = new Gson();
        String textJson = gson.toJson(preferits);
        PrintWriter fileWriter = null;

        try {
            fileWriter = new PrintWriter(new File(FILEPATH));
            fileWriter.println(textJson);
            fileWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error al guardar el fitxer.");
        }
    }

    /**
     * Aquest mètode s'encarrega de recuperar el nombre de reproduccions donat un jsonObject d'un video concret.
     * @param result Informació d'un video concret del qual es volen recuperar les reproduccions.
     * @return Número de reproduccions.
     */
    public long getReproduccions(JsonObject result) {
        return result.getAsJsonArray("items").get(0).getAsJsonObject().get("statistics").getAsJsonObject()
                .get("viewCount").getAsLong();
    }

    /**
     * Aquest mètode s'encarrega de recuperar el nombre de subscriptors donat un jsonObject d'un canal concret.
     * @param result Informació d'un canal concret del qual es vol recuperar el nombre de subscriptors.
     * @return Nombre de subscriptors..
     */
    public long getSubscriptors(JsonObject result) {
        return result.getAsJsonArray("items").get(0).getAsJsonObject().get("statistics").getAsJsonObject()
                .get("subscriberCount").getAsLong();
    }

    /**
     * Aquest mètode s'encarrega de recuperar el nombre de likes donat un jsonObject d'un video concret.
     * @param result Informació d'un video concret del qual es volen recuperar els likes.
     * @return Número de likes.
     */
    public long getLikes(JsonObject result) {
        return result.getAsJsonArray("items").get(0).getAsJsonObject().get("statistics").getAsJsonObject()
                .get("likeCount").getAsLong();
    }

    /**
     * Aquest mètode s'encarrega de recuperar la data de publicació a partir d'un jsonObject d'una llista de reprducció concreta.
     * @param result Informació de la llista de reproducció de la qual es vol obtenir la data de publicació.
     * @return Data de publicació.
     */
    public Calendar getPublicacio(JsonObject result) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String date = result.getAsJsonArray("items").get(0).getAsJsonObject().
                get("snippet").getAsJsonObject().get("publishedAt").getAsString();
        date = date.substring(0,9) + " " + date.substring(11, 18);
        try {
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }

    /**
     * Aquest mètode s'encarrega de recuperar el nombre de dislikes donat un jsonObject d'un video concret.
     * @param result Informació d'un video concret del qual es volen recuperar els dislikes.
     * @return Número de dislikes.
     */
    public long getDislikes(JsonObject result) {
        return result.getAsJsonArray("items").get(0).getAsJsonObject().get("statistics").getAsJsonObject()
                .get("dislikeCount").getAsLong();
    }

    /**
     * Aquest mètode s'encarrega de retornar els diferents videos d'una llista de reproducció.
     * @param id Id de la llista de reproducció de la qual es volen obtenir els videos.
     * @return Arraylist de videos de la llista de reproducció concreta.
     */
    public ArrayList<Video> getPlayListVideos(String id) {
        boolean keepgoing = true;
        int voltes = 1;
        //Recuperem 50 videos de la llista de reproducció, el màxim que es poden obtenir d'una petició.
        JsonObject jsonObject = GestorAPI.getSharedInstance().getPlaylistItemsInfo(id, "no");
        //Recuperem el nombre de videos totals que té la llista de reproducció.
        int total = jsonObject.getAsJsonObject("pageInfo").get("totalResults").getAsInt();
        ArrayList<Video> resultats = new ArrayList<>();

        //Mentre no haguem obtingut tots els videos de la llista de reproducció seguim obtenint resultats.
        while (keepgoing) {
            JsonArray items = jsonObject.getAsJsonArray("items");
            for(int i = 0; i < items.size(); i++) {
                Video parsed = new Video();
                JsonObject unparsed = items.get(i).getAsJsonObject();
                parsed.setTitol(unparsed.get("snippet").getAsJsonObject().get("title").getAsString());
                parsed.setThumbnail(unparsed.get("snippet").getAsJsonObject().get("thumbnails").getAsJsonObject().
                get("medium").getAsJsonObject().get("url").getAsString());
                resultats.add(parsed);
            }
            if(voltes * 50 >= total) {
                keepgoing = false;
            } else {
                jsonObject = GestorAPI.getSharedInstance().getPlaylistItemsInfo
                        (id, jsonObject.get("nextPageToken").getAsString());
                voltes++;
            }
        }
        return resultats;
    }

    /**
     * Aquest mètode s'encarrega de recuperar tots els canals del fitxer favoriteResults.
     * @return Canals obtinguts a partir del fitxer favoriteResultats.
     * @throws FileNotFoundException En cas que no es pugui accedir al fitxer favoriteResults.
     */
    public ArrayList<Canal> carregaCanals() throws FileNotFoundException {
        Gson gson = new Gson();
        ArrayList<Canal> llista = new ArrayList<>();
        JsonObject jsonObject;
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(new FileReader(FILEPATH));
        if(!jsonElement.toString().equals("null")) {
            jsonObject = jsonElement.getAsJsonObject();
            JsonArray canals = jsonObject.get("canals").getAsJsonArray();
            for(int i = 0; i < canals.size(); i++) {
                Canal c = gson.fromJson(canals.get(i), Canal.class);
                llista.add(c);
            }
        }
        return llista;
    }

    /**
     * Aquest mètode s'encarrega de recuperar tots els videos del fitxer favoriteResults.
     * @return Videos obtinguts a partir del fitxer favoriteResultats.
     * @throws FileNotFoundException En cas que no es pugui accedir al fitxer favoriteResults.
     */
    public ArrayList<Video> carregaVideos() throws FileNotFoundException {
        Gson gson = new Gson();
        ArrayList<Video> llista = new ArrayList<>();
        JsonObject jsonObject;
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(new FileReader(FILEPATH));
        if(!jsonElement.toString().equals("null")) {
            jsonObject = jsonElement.getAsJsonObject();
            JsonArray videos = jsonObject.get("videos").getAsJsonArray();
            for (int i = 0; i < videos.size(); i++) {
                Video v = gson.fromJson(videos.get(i), Video.class);
                llista.add(v);
            }
        }
        return llista;
    }

    /**
     * Aquest mètode s'encarrega de recuperar totes les llistes de reproducció del fitxer favoriteResults.
     * @return Llistes de reproducció obtingudes a partir del fitxer favoriteResultats.
     * @throws FileNotFoundException En cas que no es pugui accedir al fitxer favoriteResults.
     */
    public ArrayList<Llista> carregaLlistes() throws FileNotFoundException {
        Gson gson = new Gson();
        ArrayList<Llista> llista = new ArrayList<>();
        JsonObject jsonObject;
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(new FileReader(FILEPATH));
        if(!jsonElement.toString().equals("null")) {
            jsonObject = jsonElement.getAsJsonObject();
            JsonArray llistes = jsonObject.get("llistes").getAsJsonArray();
            for (int i = 0; i < llistes.size(); i++) {
                Llista l = gson.fromJson(llistes.get(i), Llista.class);
                llista.add(l);
            }
        }
        return llista;
    }
}
