package es.upm.etsisi.poo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class Ticket {

    private List<TicketItem> items;

    public Ticket() {
        this.items = new ArrayList<>();
    }

    public boolean addItem(TicketItem item) {
        if (items.size() >= 100 || item.getCantidad() <= 0) {
            return false;
        }
        Iterator<TicketItem> it = items.iterator();
        while (it.hasNext()) {
            TicketItem comprobador = it.next();
            if (comprobador.getProducto().getId() == item.getProducto().getId()) {
                comprobador.setCantidad(comprobador.getCantidad() + item.getCantidad());
                return true;
            }
        }
        items.add(item);
        return true;
    }

    public boolean removeItem(int productId) {
        Iterator<TicketItem> it = items.iterator();
        while (it.hasNext()) {
            TicketItem comp = it.next();
            if (comp.getProducto().getId() == productId) {
                it.remove();
                return true;
            }

        }
        return false;
    }

    public double getTotalSinDescuento() {
        Iterator<TicketItem> it = items.iterator();
        double total = 0.00;
        while (it.hasNext()) {
            TicketItem comp = it.next();
            total += comp.getProducto().getPrecio() * comp.getCantidad();
        }
        return total;
    }

    public double getTotalConDescuento() {
        Iterator<TicketItem> it = items.iterator();
        double total = 0.00;
        while (it.hasNext()) {
            TicketItem comp = it.next();
            total += comp.getSubtotal();
        }
        return total;
    }


    public void printTicket(){
        if (items.isEmpty()) {
            System.out.println("Ticket vacío");
        } else {
            Iterator<TicketItem> it = items.iterator();
            TicketItem comp;
            while (it.hasNext()) {
                comp = it.next();
                for (int i = 0; i < comp.getCantidad(); i++) {
                    System.out.println(comp);
                }
            }
            System.out.println("Total price: " + getTotalSinDescuento());
            System.out.println("Total discount: " + (getTotalSinDescuento() - getTotalConDescuento()));
            System.out.println("Final Price: " + getTotalConDescuento());
        }
    }
}

