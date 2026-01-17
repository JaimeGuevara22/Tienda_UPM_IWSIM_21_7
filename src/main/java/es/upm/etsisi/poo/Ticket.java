package es.upm.etsisi.poo;

import java.time.LocalDateTime;
import java.util.*;

public class Ticket extends abstractTicket{

    public Ticket(String ticketId, String cashId) {
        super(ticketId, cashId);
    }
    @Override
    public boolean addItem(TicketItem nuevo){

        Productos newProd = nuevo.getItem();
        if(!(newProd instanceof Productos)){
            return false;
        }
        for (int i = 0; i < contador; i++) {

            TicketItem existente = items[i];
            Productos oldProd = existente.getItem();

            if (oldProd.getId() == newProd.getId()) {

                    if (oldProd instanceof ProductosPersonalizables a && newProd instanceof ProductosPersonalizables b) {
                        if (!a.getTextos().equals(b.getTextos())) {
                            continue;
                        }
                    }
                    existente.setCantidad(existente.getCantidad() + nuevo.getCantidad());
                    return true;
            }
        }

        if (contador < items.length) {
            items[contador++] = nuevo;
            return true;
        }

        return false;
    }

    public double getTotalSinDescuento() {
        double total = 0.0;
        for (int i = 0; i < contador; i++) {
            TicketItem ti = items[i];
            total += ti.getItem().getPrecio() * ti.getCantidad();
        }
        return total;
    }public double getTotalConDescuento() {
        double total = 0.0;
        for (int i = 0; i < contador; i++) {
            TicketItem ti = items[i];
            total += ti.getSubtotal();
        }
        return total;


    }

    private String getNombre(TicketItem ti) {
        Object item = ti.getItem();
        if (item instanceof Productos p) return p.getNombre();
        if (item instanceof Food f) return f.getNombre();
        if (item instanceof Meetings m) return m.getNombre();
        return "";
    }


    public String getTicketId() {
        return ticketId;
    }
    public TicketState getState() {
        return state;
    }
    public void setState(TicketState state) {
        this.state = state;

    }
    @Override
    public int getItemsCount() {
        return contador;
    }
    public TicketItem getItem(int index) {
        if (index < 0 || index >= contador) {
            throw new IndexOutOfBoundsException();
        }
        return items[index];
    }
    @Override
    public void printTicket() {
        if (contador == 0) {  // No hay items en el ticket
            System.out.println("Empty ticket");
            return;
        }

        System.out.println("Ticket: " + ticketId);

        // Hacer copia para ordenar por nombre
        TicketItem[] copia = new TicketItem[contador];
        for (int i = 0; i < contador; i++) {
            copia[i] = items[i];
        }

        // Ordenar por nombre usando la función sort de Java
        Arrays.sort(copia, Comparator.comparing(ti -> ti.getItem().getNombre().toLowerCase()));

        for (TicketItem ti : copia) {
            Productos prod = ti.getItem();
            if(prod instanceof Product || prod instanceof ProductosPersonalizables){
                for(int i = 0; i < ti.getCantidad(); i++){
                    System.out.println(ti);
                }
            }
            else{
                System.out.println(ti);
            }
        }

        System.out.println("Total price: " + String.format("%.2f", getTotalSinDescuento()));
        System.out.println("Total discount: " + String.format("%.2f", getTotalSinDescuento() - getTotalConDescuento()));
        System.out.println("Final Price: " + String.format("%.2f", getTotalConDescuento()));
    }
}