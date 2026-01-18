package es.upm.etsisi.poo;

import jakarta.persistence.*; // Importante para usar anotaciones [cite: 168]
import java.util.ArrayList;
import java.util.List;

@Entity // Indica que esta clase se guardará en la base de datos
@Table(name = "clientes")
public class Client extends User {

    @Enumerated(EnumType.STRING) // Guarda el Enum como texto (PERSONAL/BUSINESS)
    private ClientType clientType;

    @Id // Define el NIF/DNI como clave primaria
    private String id;

    private String cashUp;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private List<abstractTicket> tickets = new ArrayList<>();

    // Hibernate requiere un constructor vacío (protegido o público) [cite: 170]
    protected Client() {
        super();
    }

    public Client(String nombre, String id, String email, String cashId) {
        super(nombre, email);
        if (id == null) {
            throw new IllegalArgumentException("El identificador no puede ser nulo");
        }
        this.id = id;
        this.cashUp = cashId;
        this.clientType = type(id);
    }
    private ClientType type(String id){
        char primer = Character.toUpperCase(id.charAt(0));
        if(Character.isLetter(primer)){
            return clientType = ClientType.BUSINESS;
        }
            return clientType = ClientType.PERSONAL;
    }
    public String getId(){
        return id;
    }
    public String getCashUp(){
        return cashUp;
    }
    @Override
    public String toString(){
        return  "Client{identifier= '" + id + "', name= '" + nombre +"', email= '" + email + "', cash= " + cashUp + "}";
    }
    public void addTicket(abstractTicket t){
        tickets.add(t);
    }
    public boolean esEmpresa(){
        return clientType == ClientType.BUSINESS;
    }
    public ClientType getClientType() {
        return clientType;
    }
}
