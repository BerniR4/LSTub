package model;

/**
 * Classe corresponent al model Canal.
 */
public class Resultat {

    protected String id;
    private String tipus;
    private String titol;
    private String descripcio;
    protected String canal;
    private String thumbnail;

    /**
     * Constructor sense paràmetres.
     */
    public Resultat() {
    }

    /**
     * Constructor amb paràmetres.
     */
    Resultat(String id, String tipus, String titol, String descripcio, String canal, String thumbnail) {
        this.id = id;
        this.tipus = tipus;
        this.titol = titol;
        this.descripcio = descripcio;
        this.canal = canal;
        this.thumbnail = thumbnail;
    }

    /**
     * Getter de l'id del resultat.
     * @return Id del resultat
     */
    public String getId() {
        return id;
    }

    /**
     * Setter de l'Id del resultat.
     * @param id Id a settejar.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter del tipus del resultat.
     * @return Tipus del resultat.
     */
    public String getTipus() {
        return tipus;
    }

    /**
     * Setter del tipus del resultat.
     * @param tipus Tipus a settejar.
     */
    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    /**
     * Getter del titol del resultat.
     * @return Titol del resultat.
     */
    public String getTitol() {
        return titol;
    }

    /**
     * Setter del titol del resultat.
     * @param titol Titol a settejar.
     */
    public void setTitol(String titol) {
        this.titol = titol;
    }

    /**
     * Getter de la descripció del resultat.
     * @return Descripció del resultat.
     */
    String getDescripcio() {
        return descripcio;
    }

    /**
     * Setter de la descripció del resultat.
     * @param descripcio Descripció a settejar.
     */
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    /**
     * Getter del canal del resultat.
     * @return Canal del resultat.
     */
    public String getCanal() {
        return canal;
    }

    /**
     * Setter del canal del resultat.
     * @param canal Canal a settejar.
     */
    public void setCanal(String canal) {
        this.canal = canal;
    }

    /**
     * Setter del thumbnail del resultat.
     * @param thumbnail Thumbnail a settejar.
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * Getter del thumbnail del resultat.
     * @return Thumbnail del resultat.
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * Mètode que s'encarrega de passar tota la informació del resultat a string.
     * @return String amb tot el resum de la informació del resultat.
     */
    @Override
    public String toString() {
        return '\t' + "Nom: " + titol + System.lineSeparator() + '\t' +
                "Tipus: " + tipus + System.lineSeparator() + '\t' +
                "Canal: " + canal + System.lineSeparator() + '\t' +
                "Descripció: " + descripcio + System.lineSeparator();
    }
}
