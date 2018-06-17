package helpers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.Resultat;

import java.io.*;
import java.util.ArrayList;

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

    public ArrayList carregaPreferits() throws FileNotFoundException {
        Gson gson = new Gson();
        FileReader file = new FileReader(FILEPATH);
        BufferedReader br = new BufferedReader(file);
        if (gson.fromJson(br, ArrayList.class) == null) {
            return new ArrayList<Resultat>();
        }
        return gson.fromJson(br, ArrayList.class);
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
                    break;

                case CANAL:
                    parsed.setTipus("Canal");
                    break;

                case LLISTA:
                    parsed.setTipus("Llista de reproducció");
                    break;
            }

            parsed.setTitol(unparsed.getAsJsonObject("snippet").get("title").getAsString());
            parsed.setCanal(unparsed.getAsJsonObject("snippet").get("channelTitle").getAsString());
            parsed.setDescripcio(unparsed.getAsJsonObject("snippet").get("description").getAsString());
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
}
