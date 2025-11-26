package es.upm.etsisi.poo;
import java.time.LocalDate;

public class Food extends ProductEvents{
    public Food(LocalDate expirationDate, int numParticipants, double price, String id, String name) {
        super(expirationDate, numParticipants, price, id, name);
    }
    public LocalDate getFoodExpirationDate(){
        return expirationDate;
    }

    @Override
    public String toString() {
        return "{Class:Food "+ super.toString();
    }
}
