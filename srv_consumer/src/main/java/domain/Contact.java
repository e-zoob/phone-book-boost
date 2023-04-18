package domain;

import java.io.Serializable;

public class Contact implements Serializable {
    private String name;
    private String number;

    public Contact(String name, String number){
        this.name = name;
        this.number = number;
    }
}
