package es.upm.etsisi.poo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class ProductEvents extends Productos{
    protected LocalDate expirationDate;

    protected int numParticipants;
    protected double price;
    protected int id;
    protected String name;

    public ProductEvents(int id , double price, String name, LocalDate expirationDate, int numParticipants) {
     super(id, name, price);
     this.expirationDate = expirationDate;
     if(numParticipants < 0 || numParticipants > 100) throw new IllegalArgumentException("Error: número de participantes inválido.");
     this.numParticipants = numParticipants;
    }
    public int getId(){
        return id;
    }
    public double getPrice() {
        return price;
    }
    public String getNombre(){
        return name;
    }
    public int getNumParticipants(){
        return numParticipants;
    }
    @Override
    public String toString(){
        return ", id: "+id+", name: "+name+", price: "+price+", date of event: "+expirationDate+", max people allowed: "+numParticipants+"}";
    }
}
