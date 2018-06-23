package helpers;

import model.Canal;
import model.Llista;
import model.Video;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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

    public static GestorHTML getInstance() {
        return ourInstance;
    }

    private GestorHTML() {
    }

    public void createPlaylistHTML(Llista l) {
        File f = new File("./" + l.getTitol() .replaceAll(" ", "_") + ".html");
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
