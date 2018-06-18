package model;

import java.util.Comparator;

public class Video extends Resultat {
    private long likes;
    private long dislikes;
    private long reproduccions;

    public static final Comparator<Video> PERCENT_COMPARATOR = (o1, o2) -> (int) (o2.getPercent() - o1.getPercent());


    public Video() {
    }

    public Video(Resultat resultat) {
        super(resultat.getId(), resultat.getTipus(), resultat.getTitol(),
                resultat.getDescripcio() , resultat.getCanal());
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public long getDislikes() {
        return dislikes;
    }

    public void setDislikes(long dislikes) {
        this.dislikes = dislikes;
    }

    public long getReproduccions() {
        return reproduccions;
    }

    public void setReproduccions(long reproduccions) {
        this.reproduccions = reproduccions;
    }

    public double getPercent() {
        System.out.println(likes + "   " + dislikes);
        return ( likes * 100 / ((double)(dislikes + likes)));
    }
}
