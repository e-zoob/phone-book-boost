package domain;

import org.bson.types.ObjectId;

import java.io.Serializable;

public class Contact implements Serializable {
    private ObjectId id;
    private String name;
    private String number;

    public Contact(String name, String number){
        this.name = name;
        this.number = number;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
