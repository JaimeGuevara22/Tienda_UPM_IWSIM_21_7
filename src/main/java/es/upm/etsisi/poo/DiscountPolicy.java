package es.upm.etsisi.poo;

public class DiscountPolicy {
    public static double getDiscountRate (Category categoria) {
        switch (categoria) {
            case MERCH:
                return 0.00;
            case STATIONERY:
                return 0.05;
            case CLOTHES:
                return 0.07;
            case BOOK:
                return 0.10;
            case ELECTRONICS:
                return 0.03;
            default:
                return 0.0;
        }
    }
}
