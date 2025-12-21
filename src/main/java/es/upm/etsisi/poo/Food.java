package es.upm.etsisi.poo;
import java.time.LocalDate;

public class Food extends ProductEvents{
    public Food(LocalDate expirationDate, int numParticipants, double price, int id, String name) {
        super(id, price, name, expirationDate, numParticipants);
    }
    public LocalDate getFoodExpirationDate(){
        return expirationDate;
    }

    @Override
    public String toString() {
        return "{Class:Food "+ super.toString();
    }

}
