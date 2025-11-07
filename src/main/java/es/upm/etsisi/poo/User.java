package es.upm.etsisi.poo;

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
}
