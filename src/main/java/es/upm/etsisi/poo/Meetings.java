package es.upm.etsisi.poo;
import java.time.LocalDate;

public class Meetings extends ProductEvents {
    public Meetings(LocalDate expirationDate, int numParticipants, double price, String id, String name) {
        super(expirationDate, numParticipants, price, id, name);

    }
    public LocalDate getMeetingsExpirationDate(){
        return expirationDate;
    }
    @Override
    public String toString() {
        return "{Class:Meeting"+ super.toString();
    }


}
