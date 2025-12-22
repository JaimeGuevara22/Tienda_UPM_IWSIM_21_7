package es.upm.etsisi.poo;

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ticket {
    private static final HashSet<String> idsUsados = new HashSet<>();
    private TicketItem[] items;
    private LocalDateTime fechaApertura;
    private LocalDateTime fechaCierre;
    private TicketState state = TicketState.EMPTY;
    private int contador;
    private static final DateTimeFormatter formato = DateTimeFormatter.ofPattern("yy-MM-dd-HH:mm");

    private String ticketId;

    public Ticket(String ticketId, String cashId) {
        this.items = new TicketItem[100];
        this.fechaApertura = LocalDateTime.now();
        this.contador=0;
        if(ticketId == null){
            this.ticketId = generarIdApertura();
        }else {
            if (idsUsados.contains(ticketId)) {
                throw new IllegalArgumentException("Error: Id ya usado.");
            }
            this.ticketId = ticketId;
            idsUsados.add(ticketId);
        }
    }
    public void activate(){
        if(state == TicketState.EMPTY){
            state = TicketState.OPEN;
        }
    }
    private String generarIdApertura() {
        String fecha = fechaApertura.format(formato);
        String random = generarCadenaId();

        String id = fecha + "-" + random;

        while (idsUsados.contains(id)) {
            random = generarCadenaId();
            id = fecha + "-" + random;
        }

        idsUsados.add(id);
        return id;
    }
    public void close() {
        if (state == TicketState.CLOSED) return;

        state = TicketState.CLOSED;
        this.fechaCierre = LocalDateTime.now();
        String fechaClose = fechaCierre.format(formato);

        String nuevoId = this.ticketId + "-" + fechaClose;

        if (!idsUsados.add(nuevoId)) {
            throw new RuntimeException("ERROR: identificador repetido al cerrar el ticket.");
        }

        idsUsados.remove(this.ticketId);
        this.ticketId = nuevoId;
    }

    public boolean addItem(TicketItem nuevo) {

        Object newObj = nuevo.getItem();

        for (int i = 0; i < contador; i++) {

            TicketItem existente = items[i];
            Object oldObj = existente.getItem();

            if (oldObj instanceof Productos p1 && newObj instanceof Productos p2) {
                if (p1.getId() == p2.getId()) {

                    if (p1 instanceof ProductosPersonalizables a && p2 instanceof ProductosPersonalizables b) {
                        if (!a.getTextos().equals(b.getTextos())) {
                            continue;
                        }
                    }
                    existente.setCantidad(existente.getCantidad() + nuevo.getCantidad());
                    return true;
                }
            }
        }

        if (contador < items.length) {
            items[contador++] = nuevo;
            return true;
        }

        return false;
    }



    public boolean removeItem(int id) {
        for (int i = 0; i < contador; i++) {
            if (items[i].getId() == id) {
                items[i] = items[contador - 1]; // Reemplazamos por el último
                items[contador - 1] = null;
                contador--;
                return true;
            }
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

        // Ordenar por nombre (toLowerCase)
        for (int i = 0; i < copia.length - 1; i++) {
            for (int j = i + 1; j < copia.length; j++) {
                String nombre1 = getNombre(copia[i]).toLowerCase();
                String nombre2 = getNombre(copia[j]).toLowerCase();
                if (nombre1.compareTo(nombre2) > 0) {
                    TicketItem temp = copia[i];
                    copia[i] = copia[j];
                    copia[j] = temp;
                }
            }
        }

        for (TicketItem ti : copia) {
            Object prod = ti.getItem();

            if (prod instanceof Productos || prod instanceof ProductosPersonalizables) {
                for (int k = 0; k < ti.getCantidad(); k++) {
                    System.out.println(ti);
                }
            }
            //Si son meetings o food lo muestro en una línea
            else if (prod instanceof Food || prod instanceof Meetings) {
                System.out.println(ti);
            }
        }

        System.out.println("Total price: " + String.format("%.2f", getTotalSinDescuento()));
        System.out.println("Total discount: " + String.format("%.2f", getTotalSinDescuento() - getTotalConDescuento()));
        System.out.println("Final Price: " + String.format("%.2f", getTotalConDescuento()));
    }
    private String generarCadenaId(){
        Random randomNumber = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 5; i++){
            sb.append(randomNumber.nextInt(10));
        }
        return sb.toString();
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

    public int getItemsCount() {
        return contador;
    }
}