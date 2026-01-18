package es.upm.etsisi.poo;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

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
        if(date == null){
            throw new IllegalArgumentException("date cannot be null");
        }
        if(date.isAfter(expirationDate)){
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        Date date = Date.from(expirationDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return "{class:ProductService, category: " +type+" expiration:" + date + "}";
    }
}
