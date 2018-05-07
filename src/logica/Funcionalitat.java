import com.google.gson.JsonArray;
import helpers.GestorAPI;

import java.util.Scanner;

public class Funcionalitat {

    public void Funcionalitat(){

    }

    public void executaFuncionalitat(int opcio){
        Scanner sc = new Scanner(System.in);
        if(opcio != 7) {

            switch (opcio){

                case 1:

                    System.out.println();
                    System.out.println("Insereix el pàrametre de cerca:");
                    System.out.println();
                    String param = sc.next();

                    GestorAPI gestorAPI = GestorAPI.getSharedInstance();
                    JsonArray result = gestorAPI.getResultList(param, 3);

                    System.out.println(result.toString());

                    break;
                case 2:
                    System.out.println("XD");
                    break;
                case 3:
                    System.out.println("XD");
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;

            }

        }

    }

}
