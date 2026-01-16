package es.upm.etsisi.poo;

import java.util.ArrayList;

public abstract class User {
    protected String nombre;
    protected String email;

    public User(String nombre, String email){
        this.nombre = nombre;
        this.email = email;
    }
    public String getNombre(){
        return nombre;
    }
    public String getEmail(){
        return email;
    }
    protected ArrayList<abstractTicket> tickets = new ArrayList<>();

}
