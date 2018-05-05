import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Funcionalitat {

    public void Funcionalitat(){
    }

    public void executaFuncionalitat(int opcio){

        if(opcio != 7) {

            switch (opcio){

                case 1:
                    menu.demanaCerca();

                    URL url = null;
                    String urlToString = "";
                    try {
                        url = new URL("https://www.googleapis.com/youtube/v3/search?part=snippet&q=bacon&maxResults=3&key=AIzaSyD3FSLnYCS3MxLg9MbWVVjOtxPmyhEG7OA");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    try {
                        Scanner scanner = new Scanner(url.openStream());
                        while (scanner.hasNext()){
                            urlToString += (scanner.nextLine());
                        }
                        System.out.println(urlToString);
                    } catch (IOException e) {
                        e.printStackTrace();
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
