package helpers;

import com.google.gson.*;
import model.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class GestorJSON {
    private static final String FILEPATH = "data" + System.getProperty("file.separator") + "favoriteResults.json";
    private static final String VIDEO = "youtube#video";
    private static final String CANAL = "youtube#channel";
    private static final String LLISTA = "youtube#playlist";

    private static GestorJSON sharedInstance = new GestorJSON();

    private GestorJSON() {

    }

    public static GestorJSON getSharedInstance() {
        return sharedInstance;
    }

    public ArrayList<Resultat> carregaPreferits() throws FileNotFoundException {
        Gson gson = new Gson();
        FileReader file = new FileReader(FILEPATH);
        BufferedReader br = new BufferedReader(file);
        Resultat[] list = gson.fromJson(br, Resultat[].class);
        if (list == null) return new ArrayList<>();
        return new ArrayList<>(Arrays.asList(list));
    }

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

    public String getNextPageCode(JsonObject result) {
        return result.get("nextPageToken").getAsString();
    }

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

    public long getReproduccions(JsonObject result) {
        return result.getAsJsonArray("items").get(0).getAsJsonObject().get("statistics").getAsJsonObject()
                .get("viewCount").getAsLong();
    }

    public long getSubscriptors(JsonObject result) {
        return result.getAsJsonArray("items").get(0).getAsJsonObject().get("statistics").getAsJsonObject()
                .get("subscriberCount").getAsLong();
    }

    public long getLikes(JsonObject result) {
        return result.getAsJsonArray("items").get(0).getAsJsonObject().get("statistics").getAsJsonObject()
                .get("likeCount").getAsLong();
    }

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

    public long getDislikes(JsonObject result) {
        return result.getAsJsonArray("items").get(0).getAsJsonObject().get("statistics").getAsJsonObject()
                .get("dislikeCount").getAsLong();
    }

    public ArrayList<Video> getPlayListVideos(String id) {
        boolean keepgoing = true;
        int voltes = 1;
        JsonObject jsonObject = GestorAPI.getSharedInstance().getPlaylistItemsInfo(id, "no");
        int total = jsonObject.getAsJsonObject("pageInfo").get("totalResults").getAsInt();
        ArrayList<Video> resultats = new ArrayList<>();
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
