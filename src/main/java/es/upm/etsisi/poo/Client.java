package es.upm.etsisi.poo;

public class Client extends User{
    public Client (String nombre, String dni, String email, Cash cashUp){
        super(nombre, email);
        this.dni = dni;
        this.cashUp = cashUp;
    }
}
