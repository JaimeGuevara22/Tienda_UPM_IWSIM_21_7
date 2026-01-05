package es.upm.etsisi.poo;

import java.time.LocalDate;

public class Service {
    private static int sec = 1;
    private final int id;
    private final LocalDate expirationDate;
    private final ServiceType type;
    public Service(LocalDate expirationDate, ServiceType type) {
        if(expirationDate == null){
            throw new IllegalArgumentException("expirationDate cannot be null");
        }
        this.expirationDate = expirationDate;
        this.type = type;
        this.id = sec++;
    }
    public int getId() { //borrar en caso de no ser necesario
        return id;
    }
    public String getServiceId() {
        return id + "S";
    }
    public ServiceType getType() { //borrar en caso de no ser necesario
        return type;
    }
    public LocalDate getExpirationDate() { //borrar en caso de no ser necesario
        return expirationDate;
    }
    public boolean isValid(LocalDate date){
        if(date.isAfter(expirationDate)){
            return false;
        }else if(date == null){
            throw new IllegalArgumentException("date cannot be null");
        }
        return true;
    }
    @Override
    public String toString() {
        return "{class:ProductService, id:" + id + ", category:" + type + ", expiration:" + expirationDate + "}";
    }
}
