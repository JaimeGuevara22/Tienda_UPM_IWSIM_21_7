package es.upm.etsisi.poo;

import java.util.Random;

public class Cash extends User{
    private String cashId;
    public Cash (String nombre, String email, String cashId){
        super(nombre, email);
        if(cashId == null){
            this.cashId = generarId();
        }else if(esValido(cashId)){
            this.cashId = cashId;
        }else{
            throw new IllegalArgumentException("El identificador de cash no es válido.");
        }

    }

    public String getCashId(){return cashId; }

    private boolean esValido(String cashId){
        if(cashId.length() != 9) return false;
        if(!cashId.startsWith("UW")) return false;
        for(int i = 2; i < cashId.length(); i++){
            if(!Character.isDigit(cashId.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    private String generarId(){
        Random randomNumber = new Random();
        StringBuilder sb = new StringBuilder("UW");
        for(int i = 0; i < 7; i++){
            sb.append(randomNumber.nextInt(10));
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Cash{identifier= " + cashId + ", name='" + this.getNombre() + "', email= '" + this.getEmail() + "'}";

    }
}

