package model;

/**
 * Classe corresponent al model Canal.
 */
public class Canal extends Resultat {
    private long subscriptors;

    /**
     * Constructor amb paràmetres.
     * @param resultat Resultat a partir del qual construirem el Canal.
     */
    public Canal(Resultat resultat) {
        super(resultat.getId(), resultat.getTipus(), resultat.getTitol(),
                resultat.getDescripcio() , resultat.getCanal(), resultat.getThumbnail());
    }

    /**
     * Getter dels subscriptors.
     * @return Subscriptors del canal.
     */
    long getSubscriptors() {
        return subscriptors;
    }

    /**
     * Setter dels subscriptors.
     * @param subscriptors Subscriptors a settejar.
     */
    public void setSubscriptors(long subscriptors) {
        this.subscriptors = subscriptors;
    }
}
