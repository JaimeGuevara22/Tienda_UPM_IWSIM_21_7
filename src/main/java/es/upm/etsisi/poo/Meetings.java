package es.upm.etsisi.poo;

import java.time.LocalDateTime;

public class Meetings extends ProductEvents {
    public Meetings(String expirationDate, int numParticipants, double price) {
        super(LocalDateTime.parse(expirationDate), numParticipants, price);

    }

}
