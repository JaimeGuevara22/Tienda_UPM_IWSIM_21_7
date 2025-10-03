package es.upm.etsisi.poo;

public class DiscountPolicy {
    public double getDiscountRate (Category categoria) {
        switch (categoria) {
            case MERCH:
                return 0.00;
            case PAPELERIA:
                return 0.05;
            case ROPA:
                return 0.07;
            case LIBRO:
                return 0.10;
            case ELECTRONICA:
                return 0.03;
            default:
                return 0.0;
        }
    }
}
