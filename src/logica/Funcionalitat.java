package logica;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import helpers.GestorAPI;
import helpers.GestorJSON;
import model.Resultat;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Funcionalitat {

    private ArrayList<Resultat> preferits;

    public Funcionalitat(ArrayList<Resultat> preferits){
        this.preferits = preferits;
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
                    param = sc.next();

                    gestorAPI = GestorAPI.getSharedInstance();
                    result = gestorAPI.getResult(param, -1, null);

                    gestorJSON = GestorJSON.getSharedInstance();
                    llista = gestorJSON.getResultList(result);
                    String nextPage = gestorJSON.getNextPageCode(result);


                    for (int i = 0; i < 10; i++) {
                        System.out.println(Integer.toString(i + 1) + " - " +
                                llista.get(i).toString());
                    }

                    boolean continuar = true;
                    do {
                        System.out.println("Insereix un número per a guardar el vídeo o 'NEXT' per a mostrar " +
                                "els 10 següents.");
                        System.out.println("Si es vol sortir, instrodueix 'STOP': ");
                        param = sc.next();

                        if (param.compareToIgnoreCase("STOP") == 0){
                            continuar = false;
                        } else if (param.compareToIgnoreCase("NEXT") == 0) {
                            result = gestorAPI.getResult(param, -1, nextPage);
                            llista = gestorJSON.getResultList(result);
                            nextPage = gestorJSON.getNextPageCode(result);
                            for (int i = 0; i < 10; i++) {
                                System.out.println(Integer.toString(i + 1) + " - " +
                                        llista.get(i).toString());
                            }
                        } else {
                            try {
                                int aux = Integer.parseInt(param);
                                if (aux > 0 && aux <= 10) {
                                    preferits.add(llista.get(aux - 1));
                                    continuar = false;
                                } else {
                                    System.out.println("Error, no ha introduït una instrucció vàlida.");
                                }
                            }catch (NumberFormatException e){
                                System.out.println("Error, no ha introduït una instrucció vàlida.");
                            }

                        }
                    } while (continuar);
                    gestorJSON.saveFile(preferits);
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
