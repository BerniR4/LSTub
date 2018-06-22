import helpers.GestorJSON;
import helpers.Menu;
import logica.Funcionalitat;
import model.PreferitsManager;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {

        GestorJSON gestorJSON = GestorJSON.getSharedInstance();
        Menu menu = new Menu();
        PreferitsManager manager = null;

        try {
            manager = new PreferitsManager(gestorJSON.carregaCanals(), gestorJSON.carregaVideos(),
                    gestorJSON.carregaLlistes());
            Funcionalitat funcionalitat = new Funcionalitat(manager);
            do {
                do {
                    menu.mostraMenu();
                    menu.demanaOpcio();
                } while (!menu.opcioCorrecta());

                funcionalitat.executaFuncionalitat(menu.getOpcio());

            } while (!menu.sortir());
        } catch (FileNotFoundException e) {
            System.out.println("Error al llegir el fitxer!");
        }
    }
}
