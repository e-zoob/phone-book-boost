package domain;

import org.bson.types.ObjectId;

import java.io.Serializable;

public class Contact implements Serializable {
    private ObjectId id;
    private String name;
    private String number;

    public Contact(){
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName(){ return name;}

    public String getNumber() {return number;}

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
