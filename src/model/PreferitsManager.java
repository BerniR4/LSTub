package model;

import com.google.gson.JsonObject;
import helpers.GestorAPI;
import helpers.GestorJSON;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PreferitsManager {
    private ArrayList<Canal> canals;
    private ArrayList<Video> videos;
    private ArrayList<Llista> llistes;

    public PreferitsManager(ArrayList<Canal> canals, ArrayList<Video> videos, ArrayList<Llista> llistes) {
        this.canals = canals;
        this.videos = videos;
        this.llistes = llistes;
    }

    public ArrayList<Canal> getCanals() {
        return canals;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }

    public ArrayList<Llista> getLlistes() {
        return llistes;
    }

    public long getAverageReproduccions() {
        long acum=0;
        for(Video v: videos) {
            acum = acum + v.getReproduccions();
        }
        return acum/videos.size();
    }

    public long getAverageSubscripcions() {
        long acum=0;
        for(Canal c: canals) {
            acum = acum + c.getSubscriptors();
        }
        return acum/videos.size();
    }

    public void afegirVideo(Video v) {
        videos.add(v);
        GestorJSON.getSharedInstance().saveFile(this);
    }

    public void afegirCanal(Canal c) {
        canals.add(c);
        GestorJSON.getSharedInstance().saveFile(this);
    }

    public void afegirLlista(Llista l) {
        llistes.add(l);
        GestorJSON.getSharedInstance().saveFile(this);
    }

    public String getNewestPlaylist() {
        llistes.sort(Llista.DATE_COMPARATOR);
        return llistes.get(0).getTitol() + "  -  " + llistes.get(0).getPublicacio().getTime();
    }

    public String getOldestPlaylist() {
        llistes.sort(Llista.DATE_COMPARATOR);
        return llistes.get(llistes.size() - 1).getTitol() + "  -  " + llistes.get(llistes.size() - 1).getPublicacio().getTime();
    }
}
