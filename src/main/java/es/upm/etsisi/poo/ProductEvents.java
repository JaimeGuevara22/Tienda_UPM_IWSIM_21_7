package es.upm.etsisi.poo;

import java.time.LocalDateTime;

public abstract class ProductEvents {
    protected LocalDateTime expirationDate;
    protected int numParticipants;
    protected double price;

    public ProductEvents(LocalDateTime expirationDate, int numParticipants, double price) {
        if (numParticipants<1 || numParticipants>100){
            throw new IllegalArgumentException("Máximo de participantes inválido (1-100)");
        }else{
        this.expirationDate = expirationDate;
        this.numParticipants = numParticipants;
        this.price = price;
        }
    }


}
