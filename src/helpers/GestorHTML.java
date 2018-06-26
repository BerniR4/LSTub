package helpers;

import model.Canal;
import model.Llista;
import model.Video;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * <h1>GestorHTML</h1>
 * GestorHTML és una classe Singleton que conté tots els mètodes necessaris per a generar els diferents fitxers HTML
 * que ha de generar el programa.
 *
 * @author  Albert Ferrando i Bernat Rovirosa
 * @version 1.0
 * @since   2018-07-22
 */
public class GestorHTML {
    private static GestorHTML ourInstance = new GestorHTML();

    private static final String START = "<!doctype html>\n" +
            "<html lang=\"es\">\n" +
            "<head>\n" +
            "<meta charset=\"UTF-8\">\n" +
            "<title>LSTubHTML</title>\n" +
            "<meta name=\"author\" content=\"Albert Ferrando Garrido - albert.ferrando.2016 & Bernat Rovirosa Roca " +
            "- bernat.rovirosa.2016\">\n" +
            "</head>\n" +
            "<body>\n";

    /**
     * Getter de la instància del singleton.
     * @return Instància singleton.
     */
    public static GestorHTML getInstance() {
        return ourInstance;
    }

    /**
     * Constructor sense paràmetres.
     */
    private GestorHTML() {
    }

    /**
     * Aquest mètode crea un fitxer HTML amb tots els videos d'una llista de reproducció.
     * @param l Llista de reproducció de la qual s'agafaran els videos per a fer el fitxer HTML.
     */
    public void createPlaylistHTML(Llista l) {
        File f = new File("./" + l.getTitol() .replaceAll(" ", "_").
                replaceAll(":", "_") + ".html");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(f));
            StringBuilder sb = new StringBuilder();
            sb.append(START).append("<h1>").append(l.getTitol()).append(": ").append(l.getVideos().size()).append(" videos")
                    .append("</h1>\n<table>");
            for (Video v: l.getVideos()) {
                sb.append("<tr>\n<td>\n<h4>").append(v.getTitol()).append("</h4>\n<img src=\"").append(v.getThumbnail()).
                        append("\"\nalt=\"Miniatura del video\" />\n</td>\n</tr>");
            }
            sb.append("</table>\n</body>\n</html>");
            writer.write(sb.toString());
        } catch (IOException e) {
            System.out.println("Fail al crear HTML de la playlist " + l.getTitol() + ".");
        } finally {
            if(writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Fail al tancar el writer.");
                }
            }
        }
    }

    /**
     * Aquest mètode crea un HTML on es mostren, en forma de graella, totes les miniatures dels elements rebuts per paràmetre.
     * @param videos Videos dels quals s'agafaran les miniatures.
     * @param llistes Llistes de reproducció de les quals s'agafaran les miniatures.
     * @param canals Canals dels quals s'agafaran les miniatures.
     */
    public void createMosaic(ArrayList<Video> videos, ArrayList<Llista> llistes, ArrayList<Canal> canals) {
        File f = new File("./Mosaic.html");
        BufferedWriter writer = null;
        int i = 0;
        try {
            writer = new BufferedWriter(new FileWriter(f));
            StringBuilder sb = new StringBuilder();
            sb.append(START).append("<table>\n<tr>");
            for (Video v: videos) {
                sb.append("\n<td>\n").append("<img src=\"").append(v.getThumbnail()).
                        append("\"\nalt=\"Miniatura\" height=\"200\" width=\"300\"/>\n</td>");
                i++;
                if(i == 4){
                    i = 0;
                    sb.append("</tr>\n<tr>");
                }
            }
            for (Canal c: canals) {
                sb.append("\n<td>\n").append("<img src=\"").append(c.getThumbnail()).
                        append("\"\nalt=\"Miniatura\" height=\"200\" width=\"300\"/>\n</td>");
                i++;
                if(i == 4){
                    i = 0;
                    sb.append("</tr>\n<tr>");
                }
            }
            for (Llista l: llistes) {
                sb.append("\n<td>\n").append("<img src=\"").append(l.getThumbnail()).
                        append("\"\nalt=\"Miniatura\" height=\"200\" width=\"300\"/>\n</td>");
                i++;
                if(i == 4){
                    i = 0;
                    sb.append("</tr>\n<tr>");
                }
            }
            sb.append("</table>\n</body>\n</html>");
            writer.write(sb.toString());
        } catch (IOException e) {
            System.out.println("Fail al crear el mosaic.");
        } finally {
            if(writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Fail al tancar el writer.");
                }
            }
        }
    }
}
