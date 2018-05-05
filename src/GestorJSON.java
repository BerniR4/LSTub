import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class GestorJSON {
    private Gson gson = new Gson();
    public void carregaPreferits(){
        try {

            JsonReader reader = new JsonReader(new FileReader("./favoriteResults.json"));
            Preferit data = (Preferit) gson.fromJson(reader, Preferit.class);

        }catch (FileNotFoundException e){


        }
    }

}
