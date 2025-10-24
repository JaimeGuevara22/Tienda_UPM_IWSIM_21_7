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

        if (item.getCantidad() <= 0) {
            return false;
        }

        int totalActual = 0;
        for (int i = 0; i < items.size(); i++) {
            totalActual += items.get(i).getCantidad();
        }

        if (totalActual + item.getCantidad() > 100) {
            return false;
        }

        for (int i = 0; i < items.size(); i++) {
            TicketItem comprobador = items.get(i);
            if (comprobador.getProducto().getId() == item.getProducto().getId()) {
                if (totalActual - comprobador.getCantidad() + comprobador.getCantidad() + item.getCantidad() > 100) {
                    return false;
                }
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


    public void printTicket() {
        if (items.isEmpty()) {
            System.out.println("Empty ticket");
        } else {
            List<TicketItem> copiaOrdenada = new ArrayList<>(items);

            for (int i = 0; i < copiaOrdenada.size() - 1; i++) {
                for (int j = i + 1; j < copiaOrdenada.size(); j++) {
                    String nombre1 = copiaOrdenada.get(i).getProducto().getNombre().toLowerCase();
                    String nombre2 = copiaOrdenada.get(j).getProducto().getNombre().toLowerCase();
                    if (nombre1.compareTo(nombre2) > 0) {
                        TicketItem temp = copiaOrdenada.get(i);
                        copiaOrdenada.set(i, copiaOrdenada.get(j));
                        copiaOrdenada.set(j, temp);
                    }
                }
            }

            Iterator<TicketItem> it = copiaOrdenada.iterator();
            TicketItem comp;
            while (it.hasNext()) {
                comp = it.next();
                for (int i = 0; i < comp.getCantidad(); i++) {
                    System.out.println(comp);
                }
            }

            System.out.println("Total price: " + String.format("%.2f", getTotalSinDescuento()));
            System.out.println("Total discount: " + String.format("%.2f", (getTotalSinDescuento() - getTotalConDescuento())));
            System.out.println("Final Price: " + String.format("%.2f", getTotalConDescuento()));
        }
    }
}

