package model;

public class Canal extends Resultat {
    private long subscriptors;

    public Canal(Resultat resultat) {
        super(resultat.getId(), resultat.getTipus(), resultat.getTitol(),
                resultat.getDescripcio() , resultat.getCanal(), resultat.getThumbnail());
    }

    public long getSubscriptors() {
        return subscriptors;
    }

    public void setSubscriptors(long subscriptors) {
        this.subscriptors = subscriptors;
    }
}
