package helpers;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * <h1>Menu</h1>
 * Menu és la classe encarregada de dur a terme les diferents tasques relacionades amb el menú del programa.
 *
 * @author  Albert Ferrando i Bernat Rovirosa
 * @version 1.0
 * @since   2018-07-22
 */
public class Menu {

    private int opcio;
    private final int MAX_OPCIO = 7;
    private final int MIN_OPCIO = 1;
    private Scanner sc;

    public Menu() {
        sc = new Scanner(System.in);
        opcio = 0;
    }

    public int getOpcio(){
        return opcio;
    }

    /**
     * Aquest mètode s'encarrega de mostrar el menú per línia de comandes.
     */
    public void mostraMenu() {
        System.out.println();
        System.out.println("1. Cerca de Resultats");
        System.out.println("2. Desar Preferits");
        System.out.println("3. Millors videos");
        System.out.println("4. Estadístiques");
        System.out.println("5. Llistes de Reproducció");
        System.out.println("6. El Mosaic");
        System.out.println("7. Sortir");
        System.out.println();
    }

    /**
     * Aquest mètode s'encarrega de demanar una opció a l'usuari i seguidament la recupera i mira que aquesta sigui
     * una opció vàlida.
     */
    public void demanaOpcio(){
        System.out.println("Sel·lecciona una opcio:");
        try{
            opcio = sc.nextInt();
            if(opcio > MAX_OPCIO || opcio < MIN_OPCIO){
                System.out.println();
                System.out.println("Opció incorrecta, torna a provar.");
                System.out.println();
            }
        } catch(InputMismatchException e){
            System.out.println();
            System.out.println("Opció incorrecta, torna a provar.");
            System.out.println();
            sc.next();
            opcio = 0;
        } finally {
            System.out.println("");
        }
    }

    /**
     * Aquest mètode s'encarrega de mirar si l'opció rebuda és vàlida o no.
     * @return Si l'opció es vàlida retorna cert, en cas contrari fals.
     */
    public boolean opcioCorrecta(){
        return opcio <= MAX_OPCIO && opcio >= MIN_OPCIO;
    }

    /**
     * Aquest mètode s'encarrega de comprovar si cal sortir del programa o no.
     * @return En cas de ser cert vol dir que cal sortir del programa ja que la opció és 7. En cas contrari, retornarà fals.
     */
    public boolean sortir(){
        return opcio == 7;
    }


}
