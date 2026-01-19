package es.upm.etsisi.poo;

import jakarta.persistence.*;

import java.util.ArrayList;

@MappedSuperclass
public abstract class User {
    protected String nombre;
    protected String email;


    protected User() {
    }

    public User(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    public String getNombre(){
        return nombre;
    }
    public String getEmail(){
        return email;
    }


}
