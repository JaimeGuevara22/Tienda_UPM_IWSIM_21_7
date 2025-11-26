package es.upm.etsisi.poo;
import java.time.LocalDateTime;

public class Meetings extends ProductEvents {
    public Meetings(String expirationDate, int numParticipants, double price, String id, String name) {
        super(LocalDateTime.parse(expirationDate), numParticipants, price, id, name);

    }
    public LocalDateTime getMeetingsExpirationDate(){
        return expirationDate;
    }


}
