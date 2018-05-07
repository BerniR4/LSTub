package helpers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import model.Resultat;

import java.io.FileNotFoundException;
import java.io.FileReader;
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

    public void carregaPreferits() throws FileNotFoundException {
        System.out.println(FILEPATH);
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(FILEPATH));
        //model.Preferit data = (model.Preferit) gson.fromJson(reader, model.Preferit.class);

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

}
