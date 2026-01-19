package es.upm.etsisi.poo;

import jakarta.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Meetings extends ProductEvents {

    protected Meetings() {
        super();
    }

    public Meetings(LocalDate expirationDate, int numParticipants, double price, int id, String name) {
        super(id, price, name, expirationDate, numParticipants);
    }

    public LocalDate getMeetingsExpirationDate(){
        return expirationDate;
    }

    @Override
    public String toString() {
        return super.toString();
    }




}