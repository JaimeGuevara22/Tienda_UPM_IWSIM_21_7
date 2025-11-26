package es.upm.etsisi.poo;

import java.time.LocalDateTime;

public abstract class ProductEvents {
    protected LocalDateTime expirationDate;
    protected int numParticipants;
    protected double price;
    protected String id;
    protected String name;

    public ProductEvents(LocalDateTime expirationDate, int numParticipants, double price, String id, String name) {
        if (numParticipants<1 || numParticipants>100){
            throw new IllegalArgumentException("Máximo de participantes inválido (1-100)");
        }else{
            this.name = name;
        this.expirationDate = expirationDate;
        this.numParticipants = numParticipants;
        this.price = price;
        if(this.id.length() >= 5){
            this.id = id;
        }
        }
    }
    public String getId(){
        return id;
    }
    @Override
    public String toString(){
        return ", id: "+id+", name: "+name+", price: "+price+" date of event: "+expirationDate+", max people allowed: "+numParticipants;
    }
}
