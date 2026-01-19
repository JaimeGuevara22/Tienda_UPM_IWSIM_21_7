package es.upm.etsisi.poo;

import jakarta.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Food extends ProductEvents {

    protected Food() {
        super();
    }

    public Food(LocalDate expirationDate, int numParticipants, double price, int id, String name) {
        super(id, price, name, expirationDate, numParticipants);
    }

    public LocalDate getFoodExpirationDate(){
        return expirationDate;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}