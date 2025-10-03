package es.upm.etsisi.poo;

import java.util.ArrayList;
import java.util.List;

public class Ticket {
    private List<TicketItem> items;
    public Ticket() {
        this.items = new ArrayList<>();
    }
    public void addItem(TicketItem item) {
        if(this.items.size() <= 100)
            this.items.add(item);
    }
    public void removeItem(int productId) {
        for(int i = 0; i < this.items.size(); i++) {
            if(items.get(i).getProducto().getId() == productId){
                items.remove(i);
                i--;
            }
        }
    }
    public double getTotal() {
        double total = 0;
        for(int i = 0; i < this.items.size(); i++) {
            total += this.items.get(i).getSubtotal();
        }
        return total;
    }
    public void printTicket(){
        for(int i = 0; i < this.items.size(); i++){
            System.out.println(this.items.get(i).toString());
        }
    }
}
