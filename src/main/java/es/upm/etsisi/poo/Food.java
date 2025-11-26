package es.upm.etsisi.poo;
import java.time.LocalDateTime;

public class Food extends ProductEvents{
    public Food(LocalDateTime expirationDate, int numParticipants, double price, String id, String name) {
        super(expirationDate, numParticipants, price, id, name);
    }
    public LocalDateTime getFoodExpirationDate(){
        return expirationDate;
    }

    @Override
    public String toString() {
        return "{Class: Food "+ super.toString();
    }
}
