package logica;

import com.google.gson.JsonObject;
import helpers.GestorAPI;
import helpers.GestorHTML;
import helpers.GestorJSON;
import model.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * <h1>Funcionalitat</h1>
 * Funcionalitat és la classe encarregada d'executar totes les opcions que ofereix el menú.
 *
 * @author  Albert Ferrando i Bernat Rovirosa
 * @version 1.0
 * @since   2018-07-22
 */
public class Funcionalitat {

    private PreferitsManager manager;

    /**
     * Constructor amb paràmetres.
     * @param manager Manager dels preferits.
     */
    public Funcionalitat(PreferitsManager manager){
        this.manager = manager;
    }

    /**
     * Aquest mètode s'encarrega d'executar una opció.
     * @param opcio Opció que s'ha d'executar.
     */
    public void executaFuncionalitat(int opcio){
        Scanner sc = new Scanner(System.in);
        if(opcio != 7) {
            switch (opcio){
                case 1:
                    System.out.println();
                    System.out.println("Insereix el pàrametre de cerca:");
                    String param = sc.next();
                    param = param.replaceAll(" ", "%20");

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
                    param = sc.nextLine();
                    param = param.replaceAll(" ", "%20");

                    gestorAPI = GestorAPI.getSharedInstance();
                    result = gestorAPI.getResult(param, -1, null);

                    gestorJSON = GestorJSON.getSharedInstance();
                    llista = gestorJSON.getResultList(result);
                    String nextPage = gestorJSON.getNextPageCode(result);

                    for (int i = 0; i < 10; i++) {
                        System.out.println(Integer.toString(i + 1) + " - " +
                                llista.get(i).toString());
                    }

                    do {
                        System.out.println("Insereix un número per a guardar l'element a preferits o 'NEXT' per a " +
                                "mostrar els 10 següents.");
                        System.out.println("Si es vol sortir, instrodueix 'STOP': ");
                        String inst = sc.nextLine();

                        nextPage = tractaInstruccio(inst, nextPage, llista, param);
                    } while (nextPage != null);
                    break;

                case 3:
                    ArrayList<Video> list = manager.getVideos();
                    list.sort(Video.PERCENT_COMPARATOR);
                    DecimalFormat df = new DecimalFormat("####0.00");
                    for (int i = 0; i < 10 && i < list.size(); i++) {
                        System.out.println(list.get(i).getTitol() + "  -  " +
                                df.format(list.get(i).getPercentatgeLikes()) + "%");
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
                    GestorHTML.getInstance().createMosaic(manager.getVideos(), manager.getLlistes(), manager.getCanals());
                    break;
            }

        }

    }

    private String tractaInstruccio(String inst, String nextPage, ArrayList<Resultat> llista, String param) {
        GestorAPI gestorAPI = GestorAPI.getSharedInstance();
        GestorJSON gestorJSON = GestorJSON.getSharedInstance();

        if (inst.compareToIgnoreCase("STOP") == 0){
            return null;
        } else if (inst.compareToIgnoreCase("NEXT") == 0) {
            JsonObject result = gestorAPI.getResult(param, -1, nextPage);
            llista = gestorJSON.getResultList(result);
            nextPage = gestorJSON.getNextPageCode(result);
            for (int i = 0; i < 10; i++) {
                System.out.println(Integer.toString(i + 1) + " - " +
                        llista.get(i).toString());
            }
        } else {
            try {
                int aux = Integer.parseInt(inst);
                if (!tractaSeleccio(aux, llista)) return null;
            }catch (NumberFormatException e){
                System.out.println("Error, no ha introduït una instrucció vàlida.");
            }
        }
        return nextPage;
    }

    /**
     * Aquest mètode s'encarrega de recuperar tota la informació necessària d'un dels 10 resultats obtinguts.
     * @param aux Enter que indica el resultat del qual volem obtenir tota la informació.
     * @param llista Llista de 10 resultats de la qual agafarem el resultat que ens indiqui aux.
     * @return Retorna cert en cas que hi hagi un error i fals en cas contrari.
     * @throws NumberFormatException
     */
    private boolean tractaSeleccio(int aux, ArrayList<Resultat> llista) {
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
            return false;
        } else {
            System.out.println("Error, no ha introduït una instrucció vàlida.");
        }
        return true;
    }
}
