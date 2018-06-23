package helpers;

import model.Llista;
import model.Video;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GestorHTML {
    private static GestorHTML ourInstance = new GestorHTML();

    private static final String START = "<!doctype html>\n" +
            "<html lang=\"es\">\n" +
            "<head>\n" +
            "<meta charset=\"UTF-8\">\n" +
            "<title>PlaylistHTML</title>\n" +
            "<meta name=\"author\" content=\"Albert Ferrando Garrido - albert.ferrando.2016 & Bernat Rovirosa Roca " +
            "- bernat.rovirosa.2016\">\n" +
            "</head>\n" +
            "<body>\n" +
            "<h1>";


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
            sb.append(START).append(l.getTitol()).append(": ").append(l.getVideos().size()).append(" videos")
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

    public void createMosaic() {
    }
}
