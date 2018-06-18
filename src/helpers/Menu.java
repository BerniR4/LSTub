package helpers;

import java.util.InputMismatchException;
import java.util.Scanner;

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

    public void mostraMenu() {

        System.out.println("1. Cerca de Resultats");
        System.out.println("2. Desar Preferits");
        System.out.println("3. Millors videos");
        System.out.println("4. Estadístiques");
        System.out.println("5. Llistes de Reproducció");
        System.out.println("6. El Mosaic");
        System.out.println("7. Sortir");
        System.out.println();

    }

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

        }

    }

    public boolean opcioCorrecta(){

        return opcio <= MAX_OPCIO && opcio >= MIN_OPCIO;

    }

    public boolean sortir(){

        return opcio == 7;

    }


}
