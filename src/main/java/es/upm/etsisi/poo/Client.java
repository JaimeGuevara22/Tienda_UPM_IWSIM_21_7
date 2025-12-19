package es.upm.etsisi.poo;

import java.util.ArrayList;

public class Client extends User{
    private String dni;
    private String cashUp;

    public Client (String nombre, String dni, String email, String cashId){
        super(nombre, email);
        this.dni = dni;
        this.cashUp = cashId;
    }
    public String getDni(){
        return dni;
    }
    public String getCashUp(){
        return cashUp;
    }
    @Override
    public String toString(){
        return  "Client{identifier= '" + dni + "', name= '" + nombre +"', email= '" + email + "', cash= " + cashUp + "}";
    }
    public void addTicket(Ticket t){
        tickets.add(t);
    }
}
