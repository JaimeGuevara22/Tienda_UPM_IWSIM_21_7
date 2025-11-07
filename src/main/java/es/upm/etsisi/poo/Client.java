package es.upm.etsisi.poo;

public class Client extends User{
    private String dni;
    private String cashId;
    public Client (String nombre, String dni, String email, String cashId){
        super(nombre, email);
        this.dni = dni;
        this.cashId = cashId;
    }
    public String getDni(){
        return dni;
    }
    public String getCashId(){
        return cashId;
    }
    @Override
    public String toString(){
        return  "\""+this.getNombre()+"\" "+ this.dni+" "+this.getEmail()+" "+this.cashId;
    }
}
