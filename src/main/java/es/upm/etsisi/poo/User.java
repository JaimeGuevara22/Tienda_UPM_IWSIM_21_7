package es.upm.etsisi.poo;

import jakarta.persistence.*; // Si User también es una entidad

import java.util.ArrayList;

@MappedSuperclass // Esta anotación indica que es una clase padre cuyos campos se heredan en la DB
public abstract class User {
    protected String nombre;
    protected String email;

    // AÑADE ESTO:
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
