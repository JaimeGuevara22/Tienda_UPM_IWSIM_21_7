package es.upm.etsisi.poo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Food extends ProductEvents{
    public Food(LocalDateTime expirationDate, int numParticipants, double price) {
        super(expirationDate, numParticipants, price);
    }
    public LocalDateTime getExpirationDate(){
        return expirationDate;
    }
}
