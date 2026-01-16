package es.upm.etsisi.poo;

public class Client extends User{
    private ClientType clientType;
    private String id;
    private String cashUp;

    public Client (String nombre, String id, String email, String cashId){
        super(nombre, email);
        if(id == null){
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
