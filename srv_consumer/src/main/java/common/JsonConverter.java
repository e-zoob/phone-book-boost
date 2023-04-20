package common;
import com.google.gson.Gson;
import domain.Contact;

public class JsonConverter {
    private static Gson gson;
//    public JsonConverter(){
//        this.gson = new Gson();
//    }
    public static Contact Deserialize(String message, Gson gson){
        return gson.fromJson(message, Contact.class);
    }
}
