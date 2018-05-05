public class Main {

    public static void main(String[] args) {

        Menu menu = new Menu();
        GestorJSON gestorJSON = new GestorJSON();
        Funcionalitat funcionalitat = new Funcionalitat();

        gestorJSON.carregaPreferits();

        do {
            do {
                menu.mostraMenu();
                menu.demanaOpcio();
            } while (!menu.opcioCorrecta());
            funcionalitat.executaFuncionalitat(menu.getOpcio());
        } while(!menu.sortir());


    }

}
