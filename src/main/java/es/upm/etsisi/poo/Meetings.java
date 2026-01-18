package es.upm.etsisi.poo;

import jakarta.persistence.Entity;
import java.time.LocalDate;

@Entity // Indica a Hibernate que esta clase es una entidad en la DB
public class Meetings extends ProductEvents {

    // 1. Constructor vacío protegido para Hibernate
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
        // Corregido para que no duplique llaves si super.toString() ya las trae
        return "{Class:Meeting" + super.toString();
    }
}