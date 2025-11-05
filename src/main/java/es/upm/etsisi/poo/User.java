package es.upm.etsisi.poo;

public abstract class User {
    protected String nombre;
    protected String dni;
    protected String email;
    protected Cash cashUp;

    public User(String nombre, String email){
        this.nombre = nombre;
        this.email = email;
    }
}
