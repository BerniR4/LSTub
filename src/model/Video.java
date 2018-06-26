package model;

import java.util.Comparator;

/**
 * Classe corresponent al model Video.
 */
public class Video extends Resultat {
    private double percentatgeLikes;
    private long reproduccions;

    /**
     * Comparador que compara dos videos segons el seu percentatge de likes.
     */
    public static final Comparator<Video> PERCENT_COMPARATOR = (o1, o2) -> (int) (o2.getPercentatgeLikes() - o1.getPercentatgeLikes());

    /**
     * Constructor sense paràmetres.
     */
    public Video() {
    }

    /**
     * Constructor amb paràmetres.
     * @param resultat Resultat a partir del qual construirem la Llista.
     */
    public Video(Resultat resultat) {
        super(resultat.getId(), resultat.getTipus(), resultat.getTitol(),
                resultat.getDescripcio() , resultat.getCanal(), resultat.getThumbnail());
    }

    /**
     * Getter de les reproduccions d'un video.
     * @return Reproduccions.
     */
    long getReproduccions() {
        return reproduccions;
    }

    /**
     * Setter de les reproduccions d'un video.
     * @param reproduccions Reproduccions a settejar.
     */
    public void setReproduccions(long reproduccions) {
        this.reproduccions = reproduccions;
    }

    /**
     * Getter del percentatge de likes d'un video.
     * @return Percentatge de likes.
     */
    public double getPercentatgeLikes() {
        return percentatgeLikes;
    }

    /**
     * Setter del percentatge de likes d'un video.
     * @param likes Likes del video.
     * @param dislikes Dislikes del video.
     */
    public void setPercentatgeLikes(long likes, long dislikes) {
        this.percentatgeLikes = likes * 100 / ((double)(dislikes + likes));
    }
}
