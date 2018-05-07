import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class GestorJSON {
    private static final String FILEPATH = "data" + System.getProperty("file.separator") + "favoriteResults.json";

    public void carregaPreferits() throws FileNotFoundException {
        System.out.println(FILEPATH);
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(FILEPATH));
        //Preferit data = (Preferit) gson.fromJson(reader, Preferit.class);

    }

}
