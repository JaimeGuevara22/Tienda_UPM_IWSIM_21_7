package es.upm.etsisi.poo;

public abstract class ProductEvents {
    protected String expirationDate;
    protected int numParticipants;
    protected double price;

    public ProductEvents(String expirationDate, int numParticipants, double price) {
        if (numParticipants<1 || numParticipants>100){
            throw new IllegalArgumentException("Máximo de participantes inválido (1-100)");
        }else{
        this.expirationDate = expirationDate;
        this.numParticipants = numParticipants;
        this.price = price;
        }
    }


}
