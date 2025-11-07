package es.upm.etsisi.poo;
import java.util.ArrayList;
import java.util.List;
public class ClientController {
    private List<Client> clients = new ArrayList<>();

    public boolean addClient(Client client){
        for(Client c : clients){
            if(c.getDni().equals(client.getDni())){
                return false;
            }
        }
        clients.add(client);
        return true;
    }
    public void removeClient(String dni){
        for(Client c : clients){
            if(c.getDni().equals(dni)){
                clients.remove(c);
            }
        }
    }
}
