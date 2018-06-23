package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

public class Llista extends Resultat {
    private Calendar publicacio;
    private ArrayList<Video> videos;

    public Llista(Resultat resultat) {
        super(resultat.getId(), resultat.getTipus(), resultat.getTitol(),
                resultat.getDescripcio() , resultat.getCanal(), resultat.getThumbnail());
    }

    static final Comparator<Llista> DATE_COMPARATOR = (o1, o2) -> (int) (o2.getPublicacio().compareTo(o1.getPublicacio()));

    Calendar getPublicacio() {
        return publicacio;
    }

    public void setPublicacio(Calendar publicacio) {
        this.publicacio = publicacio;
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }
}
