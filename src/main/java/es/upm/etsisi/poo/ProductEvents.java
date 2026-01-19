package es.upm.etsisi.poo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public abstract class ProductEvents extends Productos {

    protected LocalDate expirationDate;

    protected int numParticipants;

    protected ProductEvents() {
        super();
    }

    public ProductEvents(int id , double price, String name, LocalDate expirationDate, int numParticipants) {
        super(id, name, price);
        this.expirationDate = expirationDate;
        if(numParticipants < 0 || numParticipants > 100)
            throw new IllegalArgumentException("Error: número de participantes inválido.");
        this.numParticipants = numParticipants;
    }

    public int getNumParticipants(){
        return numParticipants;
    }

    @Override
    public String toString(){
        return "{Class: " + this.getClass().getSimpleName() +
                ", id: " + getId() +
                ", name: '" + getNombre() + "'" +
                ", price: " + getPrecio() +
                ", date of event: " + expirationDate +
                ", max people allowed: " + numParticipants + "}";
    }
}