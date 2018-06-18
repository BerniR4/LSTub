package model;

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

    public List<Resultat> getVideoList() {
        ArrayList<Video> list = new ArrayList<>();
        for (Resultat r : preferits) {
            if (r instanceof Video){
                ((Video) r).setLikes(GestorJSON.getSharedInstance().getLikes(GestorAPI.getSharedInstance()
                        .getVideoInfo(r.id)));
                System.out.println(((Video) r).getLikes());
                list.add((Video) r);

            }
        }
        return null;
    }
}
