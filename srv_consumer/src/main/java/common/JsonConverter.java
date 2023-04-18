package common;
import com.google.gson.Gson;
import domain.Contact;

public class JsonConverter {
    private Gson gson;
    public JsonConverter(){
        gson = new Gson();
    }
    public Contact Deserialize(String message){
        return gson.fromJson(message, Contact.class);
    }
}
