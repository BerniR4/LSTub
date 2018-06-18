package model;

public class Video extends Resultat {
    private long likes;
    private long dislikes;
    private long reproduccions;

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
}
