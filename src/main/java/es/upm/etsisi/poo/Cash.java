package es.upm.etsisi.poo;

import jakarta.persistence.*; // Añade esto
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "cashiers") // Si quieres guardar los cajeros en la DB
public class Cash extends User {

    @Id
    private String cashId;

    // Usamos @Transient para que esta lista NO intente guardarse en la DB
    // y así evitar el error de JdbcType que teníamos antes.
    @Transient
    private List<abstractTicket> tickets = new ArrayList<>();

    protected Cash() { super(); } // Requerido por Hibernate

    public Cash (String nombre, String email, String cashId){
        super(nombre, email);
        if(cashId == null){
            this.cashId = generarId();
        } else if(esValido(cashId)){
            this.cashId = cashId;
        } else {
            throw new IllegalArgumentException("El identificador de cash no es válido.");
        }
    }

    public String getCashId(){ return cashId; }

    private boolean esValido(String cashId){
        if(cashId.length() != 9) return false;
        if(!cashId.startsWith("UW")) return false;
        for(int i = 2; i < cashId.length(); i++){
            if(!Character.isDigit(cashId.charAt(i))) return false;
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
        return "Cash{identifier= '" + cashId + "', name='" + this.getNombre() + "', email= '" + this.getEmail() + "'}";
    }

    // Cambiado a List para ser compatible con lo que espera el resto del sistema
    public List<abstractTicket> getTickets() {
        return tickets;
    }

    public void addTicket(abstractTicket ticket) {
        tickets.add(ticket);
    }
}