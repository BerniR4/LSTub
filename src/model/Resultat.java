package model;

import java.util.ArrayList;

public class Resultat {

    protected String id;
    protected String tipus;
    protected String titol;
    protected String descripcio;
    protected String canal;

    public Resultat() {
    }

    public Resultat(String id, String tipus, String titol, String descripcio, String canal) {
        this.id = id;
        this.tipus = tipus;
        this.titol = titol;
        this.descripcio = descripcio;
        this.canal = canal;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nom: ").append(titol).append(System.lineSeparator()).append('\t');
        sb.append("Tipus: ").append(tipus).append(System.lineSeparator()).append('\t');
        sb.append("Canal: ").append(canal).append(System.lineSeparator()).append('\t');
        sb.append("Descripció: ").append(descripcio).append(System.lineSeparator());

        return sb.toString();
    }
}
