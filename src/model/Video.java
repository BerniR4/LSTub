package model;

import java.util.Comparator;

public class Video extends Resultat {
    private double percentatgeLikes;
    private long reproduccions;

    public static final Comparator<Video> PERCENT_COMPARATOR = (o1, o2) -> (int) (o2.getPercentatgeLikes() - o1.getPercentatgeLikes());

    public Video() {
    }

    public Video(Resultat resultat) {
        super(resultat.getId(), resultat.getTipus(), resultat.getTitol(),
                resultat.getDescripcio() , resultat.getCanal(), resultat.getThumbnail());
    }

    public long getReproduccions() {
        return reproduccions;
    }

    public void setReproduccions(long reproduccions) {
        this.reproduccions = reproduccions;
    }

    public double getPercentatgeLikes() {
        return percentatgeLikes;
    }

    public void setPercentatgeLikes(long likes, long dislikes) {
        this.percentatgeLikes = likes * 100 / ((double)(dislikes + likes));
    }
}
