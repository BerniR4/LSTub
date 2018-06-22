package model;

import java.util.ArrayList;
import java.util.Calendar;

public class Llista extends Resultat {
    private Calendar publicacio;
    private ArrayList<Video> videos;

    public Llista(Resultat resultat) {
        super(resultat.getId(), resultat.getTipus(), resultat.getTitol(),
                resultat.getDescripcio() , resultat.getCanal(), resultat.getThumbnail());
    }

    public Calendar getPublicacio() {
        return publicacio;
    }

    public void setPublicacio(Calendar publicacio) {
        this.publicacio = publicacio;
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }
}
