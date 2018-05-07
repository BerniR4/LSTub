package logica;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import helpers.GestorAPI;
import helpers.GestorJSON;
import model.Resultat;

import java.util.ArrayList;
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
                    String param = sc.next();

                    GestorAPI gestorAPI = GestorAPI.getSharedInstance();
                    JsonObject result = gestorAPI.getResult(param, 3);

                    GestorJSON gestorJSON = GestorJSON.getSharedInstance();
                    ArrayList<Resultat> llista = gestorJSON.getResultList(result);

                    for (Resultat r : llista) {
                        System.out.println(r.toString());
                    }

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
