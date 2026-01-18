package es.upm.etsisi.poo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "servicios")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Hibernate gestiona el ID (1, 2, 3...)
    private int id;

    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING) // Guarda el nombre del ENUM (ej: "REPAIR") en lugar del número
    private ServiceType type;

    // Constructor protegido necesario para que Hibernate recupere los datos del disco
    protected Service() {}

    public Service(LocalDate expirationDate, ServiceType type) {
        if (expirationDate == null) {
            throw new IllegalArgumentException("expirationDate cannot be null");
        }
        this.expirationDate = expirationDate;
        this.type = type;
        // El ID no se asigna aquí, se genera al hacer session.persist()
    }

    /**
     * Devuelve el ID formateado como pide el enunciado (ej: "1S").
     */
    public String getServiceId() {
        return id + "S";
    }

    public ServiceType getType() {
        return type;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public boolean isValid(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        return !date.isAfter(expirationDate);
    }

    @Override
    public String toString() {
        // Formato exacto para el catálogo: {class:ProductService, category: TYPE, expiration: YYYY-MM-DD}
        // Nota: He incluido el ID para facilitar el seguimiento en consola
        return "{class:ProductService, id: " + getServiceId() +
                ", category: " + type +
                ", expiration: " + expirationDate + "}";
    }
}