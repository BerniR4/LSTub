package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

/**
 * Classe corresponent al model Llista.
 */
public class Llista extends Resultat {
    private Calendar publicacio;
    private ArrayList<Video> videos;

    /**
     * Constructor amb paràmetres.
     * @param resultat Resultat a partir del qual construirem la Llista.
     */
    public Llista(Resultat resultat) {
        super(resultat.getId(), resultat.getTipus(), resultat.getTitol(),
                resultat.getDescripcio() , resultat.getCanal(), resultat.getThumbnail());
    }

    /**
     * Comparador que compara les llistes segons la seva data de publicació.
     */
    static final Comparator<Llista> DATE_COMPARATOR = (o1, o2) -> (int) (o2.getPublicacio().compareTo(o1.getPublicacio()));

    Calendar getPublicacio() {
        return publicacio;
    }

    /**
     * Setter de la data de publicació
     * @param publicacio Data de publicació a settejar.
     */
    public void setPublicacio(Calendar publicacio) {
        this.publicacio = publicacio;
    }

    /**
     * Setter dels videos de la llista.
     * @param videos Videos a settejar.
     */
    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }

    /**
     * Getter dels videos de la llista
     * @return Videos de la llista.
     */
    public ArrayList<Video> getVideos() {
        return videos;
    }
}
