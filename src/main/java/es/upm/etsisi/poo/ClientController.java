package es.upm.etsisi.poo;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
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
    public void list(){
        Collections.sort(clients, (c1, c2) -> c1.getNombre().compareToIgnoreCase(c2.getNombre()));
        for(Client c : clients){
            System.out.println(c.toString());
        }
    }
}
