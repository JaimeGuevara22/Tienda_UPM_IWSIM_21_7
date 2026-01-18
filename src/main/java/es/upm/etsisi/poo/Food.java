package es.upm.etsisi.poo;

import jakarta.persistence.Entity;
import java.time.LocalDate;

@Entity // Fundamental para que Hibernate cree la tabla o discriminador
public class Food extends ProductEvents {

    // Constructor vacío protegido: Obligatorio para Hibernate
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
        // Quitamos la llave extra si super.toString() ya la incluye al final
        return "{Class:Food" + super.toString();
    }
}