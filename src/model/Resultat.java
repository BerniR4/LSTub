package model;

public class Resultat {

    protected String id;
    protected String tipus;
    protected String titol;
    protected String descripcio;
    protected String canal;
    protected String thumbnail;

    public Resultat() {
    }

    public Resultat(String id, String tipus, String titol, String descripcio, String canal, String thumbnail) {
        this.id = id;
        this.tipus = tipus;
        this.titol = titol;
        this.descripcio = descripcio;
        this.canal = canal;
        this.thumbnail = thumbnail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    @Override
    public String toString() {
        return '\t' + "Nom: " + titol + System.lineSeparator() + '\t' +
                "Tipus: " + tipus + System.lineSeparator() + '\t' +
                "Canal: " + canal + System.lineSeparator() + '\t' +
                "Descripció: " + descripcio + System.lineSeparator();
    }
}
