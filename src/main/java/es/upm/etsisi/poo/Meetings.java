package es.upm.etsisi.poo;
import java.time.LocalDate;

public class Meetings extends ProductEvents {
    public Meetings(LocalDate expirationDate, int numParticipants, double price, int id, String name) {
        super(id, price, name, expirationDate, numParticipants);

    }
    public LocalDate getMeetingsExpirationDate(){
        return expirationDate;
    }
    @Override
    public String toString() {
        return "{Class:Meeting"+ super.toString();
    }
}
