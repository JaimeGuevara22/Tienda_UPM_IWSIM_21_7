package es.upm.etsisi.poo;

import jakarta.persistence.*; // Importante para las anotaciones
import java.time.LocalDate;

@Entity
// Usamos SINGLE_TABLE o TABLE_PER_CLASS dependiendo de tu estrategia en Productos
// Si Productos ya tiene @Inheritance, aquí no hace falta repetirlo.
public abstract class ProductEvents extends Productos {

    protected LocalDate expirationDate;

    protected int numParticipants;

    // 1. Constructor vacío obligatorio para Hibernate
    protected ProductEvents() {
        super();
    }

    public ProductEvents(int id , double price, String name, LocalDate expirationDate, int numParticipants) {
        super(id, name, price);
        this.expirationDate = expirationDate;
        // Validación lógica
        if(numParticipants < 0 || numParticipants > 100)
            throw new IllegalArgumentException("Error: número de participantes inválido.");
        this.numParticipants = numParticipants;
    }

    public int getNumParticipants(){
        return numParticipants;
    }

    @Override
    public String toString(){
        // He corregido un poco el formato para que encaje con el estilo de los otros productos
        return "{Class: " + this.getClass().getSimpleName() +
                ", id: " + getId() +
                ", name: '" + getNombre() + "'" +
                ", price: " + getPrecio() +
                ", date of event: " + expirationDate +
                ", max people allowed: " + numParticipants + "}";
    }
}