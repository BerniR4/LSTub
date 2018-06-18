package helpers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.Canal;
import model.Llista;
import model.Resultat;
import model.Video;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            Resultat parsed = null;
            JsonObject unparsed = items.get(i).getAsJsonObject();
            switch (unparsed.getAsJsonObject("id").get("kind").getAsString()) {
                case VIDEO:
                    parsed = new Video();
                    parsed.setTipus("Video");
                    parsed.setId(unparsed.getAsJsonObject("id").get("videoId").getAsString());
                    break;

                case CANAL:
                    parsed = new Canal();
                    parsed.setTipus("Canal");
                    parsed.setId(unparsed.getAsJsonObject("id").get("channelId").getAsString());
                    break;

                case LLISTA:
                    parsed = new Llista();
                    parsed.setTipus("Llista de reproducció");
                    parsed.setId(unparsed.getAsJsonObject("id").get("playlistId").getAsString());
                    break;
            }
            unparsed = unparsed.getAsJsonObject("snippet");
            parsed.setTitol(unparsed.get("title").getAsString());
            parsed.setCanal(unparsed.get("channelTitle").getAsString());
            parsed.setDescripcio(unparsed.get("description").getAsString());
            resultats.add(parsed);
        }

        return resultats;
    }

    public String getNextPageCode(JsonObject result) {
        return result.get("nextPageToken").getAsString();
    }

    public void saveFile(ArrayList<Resultat> preferits){
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

    public long getLikes(JsonObject result) {
        return result.getAsJsonArray("items").get(0).getAsJsonObject().get("likeCount").getAsLong();
    }

    public long getDislikes(JsonObject result) {
        return result.getAsJsonArray("items").get(0).getAsJsonObject().get("dislikeCount").getAsLong();
    }

}
