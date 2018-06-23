package logica;

import com.google.gson.JsonObject;
import helpers.GestorAPI;
import helpers.GestorHTML;
import helpers.GestorJSON;
import model.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Funcionalitat {

    private PreferitsManager manager;

    public Funcionalitat(PreferitsManager manager){
        this.manager = manager;
    }

    public void executaFuncionalitat(int opcio){
        Scanner sc = new Scanner(System.in);
        if(opcio != 7) {
            switch (opcio){
                case 1:
                    System.out.println();
                    System.out.println("Insereix el pàrametre de cerca:");
                    String param = sc.next();

                    GestorAPI gestorAPI = GestorAPI.getSharedInstance();
                    JsonObject result = gestorAPI.getResult(param, 3, null);

                    GestorJSON gestorJSON = GestorJSON.getSharedInstance();
                    ArrayList<Resultat> llista = gestorJSON.getResultList(result);

                    for (Resultat r : llista) {
                        System.out.println(r.toString());
                    }
                    break;

                case 2:

                    System.out.println();
                    System.out.println("Insereix el pàrametre de cerca:");
                    param = sc.next();

                    gestorAPI = GestorAPI.getSharedInstance();
                    result = gestorAPI.getResult(param, -1, null);

                    gestorJSON = GestorJSON.getSharedInstance();
                    llista = gestorJSON.getResultList(result);
                    String nextPage = gestorJSON.getNextPageCode(result);

                    for (int i = 0; i < 10; i++) {
                        System.out.println(Integer.toString(i + 1) + " - " +
                                llista.get(i).toString());
                    }

                    boolean continuar = true;
                    do {
                        System.out.println("Insereix un número per a guardar el vídeo o 'NEXT' per a mostrar " +
                                "els 10 següents.");
                        System.out.println("Si es vol sortir, instrodueix 'STOP': ");
                        param = sc.nextLine();

                        if (param.compareToIgnoreCase("STOP") == 0){
                            continuar = false;
                        } else if (param.compareToIgnoreCase("NEXT") == 0) {
                            result = gestorAPI.getResult(param, -1, nextPage);
                            llista = gestorJSON.getResultList(result);
                            nextPage = gestorJSON.getNextPageCode(result);
                            for (int i = 0; i < 10; i++) {
                                System.out.println(Integer.toString(i + 1) + " - " +
                                        llista.get(i).toString());
                            }
                        } else {
                            try {
                                int aux = Integer.parseInt(param);
                                if (aux > 0 && aux <= 10) {
                                    Resultat r = llista.get(aux - 1);
                                    JsonObject jsonObject;
                                    switch (r.getTipus()) {
                                        case "Video":
                                            Video v = new Video(r);
                                            jsonObject = GestorAPI.getSharedInstance().getVideoInfo(v.getId());
                                            v.setPercentatgeLikes(GestorJSON.getSharedInstance().getLikes(jsonObject),
                                                    GestorJSON.getSharedInstance().getDislikes(jsonObject));
                                            v.setReproduccions(GestorJSON.getSharedInstance().getReproduccions(jsonObject));
                                            manager.afegirVideo(v);
                                            break;
                                        case "Canal":
                                            Canal c = new Canal(r);
                                            jsonObject = GestorAPI.getSharedInstance().getChannelInfo(c.getId());
                                            c.setSubscriptors(GestorJSON.getSharedInstance().getSubscriptors(jsonObject));
                                            manager.afegirCanal(c);
                                            break;
                                        case "Llista de reproducció":
                                            Llista l = new Llista(r);
                                            jsonObject = GestorAPI.getSharedInstance().getPlaylistInfo(l.getId());
                                            l.setPublicacio(GestorJSON.getSharedInstance().getPublicacio(jsonObject));
                                            l.setVideos(GestorJSON.getSharedInstance().getPlayListVideos(l.getId()));
                                            manager.afegirLlista(l);
                                            break;
                                    }
                                    continuar = false;
                                } else {
                                    System.out.println("Error, no ha introduït una instrucció vàlida.");
                                }
                            }catch (NumberFormatException e){
                                System.out.println("Error, no ha introduït una instrucció vàlida.");
                            }
                        }
                    } while (continuar);
                    break;

                case 3:
                    ArrayList<Video> list = manager.getVideos();
                    list.sort(Video.PERCENT_COMPARATOR);
                    for (int i = 0; i < 10 && i < list.size(); i++) {
                        System.out.println(list.get(i).getTitol() + "  -  " + list.get(i).getPercentatgeLikes());
                    }
                    break;
                case 4:
                    System.out.println("Mitjana de reproduccions dels videos: " + manager.getAverageReproduccions());
                    System.out.println("Mitjana de subscriptors dels canals: " + manager.getAverageSubscripcions());
                    System.out.println("La playlist més vella: " + manager.getOldestPlaylist());
                    System.out.println("La playlist més nova: " + manager.getNewestPlaylist());
                    break;
                case 5:
                    for(Llista l: manager.getLlistes()) {
                        GestorHTML.getInstance().createPlaylistHTML(l);
                    }
                    break;
                case 6:
                    GestorHTML.getInstance().createMosaic();
                    break;
            }

        }

    }

}
