package es.upm.etsisi.poo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "servicios")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING)
    private ServiceType type;

    protected Service() {}

    public Service(LocalDate expirationDate, ServiceType type) {
        if (expirationDate == null) {
            throw new IllegalArgumentException("expirationDate cannot be null");
        }
        this.expirationDate = expirationDate;
        this.type = type;

    }

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
        return "{class:ProductService, id: " + getServiceId() +
                ", category: " + type +
                ", expiration: " + expirationDate + "}";
    }
}