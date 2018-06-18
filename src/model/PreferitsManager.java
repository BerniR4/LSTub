package model;

import com.google.gson.JsonObject;
import helpers.GestorAPI;
import helpers.GestorJSON;

import java.util.ArrayList;
import java.util.List;

public class PreferitsManager {
    private ArrayList<Resultat> preferits;

    public PreferitsManager(ArrayList<Resultat> preferits) {
        this.preferits = preferits;
    }

    public void afegirPreferit(Resultat r) {
        preferits.add(r);
        GestorJSON.getSharedInstance().saveFile(preferits);
    }

    public ArrayList<Video> getVideoList() {
        ArrayList<Video> list = new ArrayList<>();
        for (Resultat r : preferits) {
            if (r.getTipus().equals("Video")){
                JsonObject jsonObject = GestorAPI.getSharedInstance().getVideoInfo(r.id);
                Video video = new Video(r);
                video.setLikes(GestorJSON.getSharedInstance().getLikes(jsonObject));
                video.setDislikes(GestorJSON.getSharedInstance().getDislikes(jsonObject));
                System.out.println(video.getPercent());
                list.add(video);
            }
        }
        return list;
    }
}
