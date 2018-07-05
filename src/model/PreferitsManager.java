package model;

import helpers.GestorJSON;
import java.util.ArrayList;

/**
 * La classe PreferitsManager s'encarrega de gestionar els preferits.
 */
public class PreferitsManager {
    private ArrayList<Canal> canals;
    private ArrayList<Video> videos;
    private ArrayList<Llista> llistes;

    /**
     * Constructor amb paràmetres.
     * @param canals Canals recuperats del fitxer favoriteResults a l'iniciar el programa.
     * @param videos Videos recuperats del fitxer favoriteResults a l'iniciar el programa.
     * @param llistes Listes de reproducció recuperades del fitxer favoriteResults a l'iniciar el programa.
     */
    public PreferitsManager(ArrayList<Canal> canals, ArrayList<Video> videos, ArrayList<Llista> llistes) {
        this.canals = canals;
        this.videos = videos;
        this.llistes = llistes;
    }

    /**
     * Getter dels canals.
     * @return Canals preferits.
     */
    public ArrayList<Canal> getCanals() {
        return canals;
    }

    /**
     * Getter dels videos.
     * @return Videos preferits.
     */
    public ArrayList<Video> getVideos() {
        return videos;
    }

    /**
     * Getter de les llistes de reproducció.
     * @return Llistes preferides.
     */
    public ArrayList<Llista> getLlistes() {
        return llistes;
    }

    /**
     * Aquest mètode retorna la mitjana de reproduccions de tots els videos preferits.
     * @return Mitjana de reproduccions.
     */
    public long getAverageReproduccions() {
        long acum=0;
        for(Video v: videos) {
            acum = acum + v.getReproduccions();
        }
        if(videos.size() > 0 ) {
            return acum / videos.size();
        } else {
            System.out.println("No hi ha videos guardats\r\n");
            return acum;
        }
    }

    /**
     * Aquest mètode retorna la mitjana de subscriptors de tots els canals preferits.
     * @return Mitjana de subscriptors.
     */
    public long getAverageSubscripcions() {
        long acum=0;
        for(Canal c: canals) {
            acum = acum + c.getSubscriptors();
        }
        if(videos.size() > 0 ) {
            return acum / videos.size();
        } else {
            return acum;
        }
    }

    /**
     * Aquest mètode s'encarrega d'afegir un video als preferits.
     * @param v Video a afegir.
     */
    public void afegirVideo(Video v) {
        if(!videos.contains(v)) {
            videos.add(v);
            System.out.println("Aquest element ja el tenies a preferits!");
        }
        GestorJSON.getSharedInstance().saveFile(this);
    }

    /**
     * Aquest mètode s'encarrega d'afegir un canal als preferits.
     * @param c Canal a afegir.
     */
    public void afegirCanal(Canal c) {
        if(!canals.contains(c)) {
            canals.add(c);
            System.out.println("Aquest element ja el tenies a preferits!");
        }
        GestorJSON.getSharedInstance().saveFile(this);
    }

    /**
     * Aquest mètode s'encarrega d'afegir una llista als preferits.
     * @param l Llista a afegir.
     */
    public void afegirLlista(Llista l) {
        if(!llistes.contains(l)) {
            llistes.add(l);
            System.out.println("Aquest element ja el tenies a preferits!");
        }
        GestorJSON.getSharedInstance().saveFile(this);
    }

    /**
     * Aquest mètode s'encarrega de recuperar la llista més nova de les presents a preferits.
     * @return Llista més nova.
     */
    public String getNewestPlaylist() {
        llistes.sort(Llista.DATE_COMPARATOR);
        if (llistes.size() > 0) {
            return llistes.get(0).getTitol() + "  -  " + llistes.get(0).getPublicacio().getTime();
        } else {
            return null;
        }
    }

    /**
     * Aquest mètode s'encarrega de recuperar la llista més vella de les presents a preferits.
     * @return Llista més vella.
     */
    public String getOldestPlaylist() {
        llistes.sort(Llista.DATE_COMPARATOR);
        if (llistes.size() > 0) {
            return llistes.get(llistes.size() - 1).getTitol() + "  -  " + llistes.get(llistes.size() - 1).getPublicacio().getTime();
        } else {
            System.out.println("No hi ha llistes desades.\r\n");
            return null;
        }
    }
}
