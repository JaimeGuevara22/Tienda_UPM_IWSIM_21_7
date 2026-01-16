package es.upm.etsisi.poo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Random;

public abstract class abstractTicket {
    protected TicketItem[] items;
    protected int contador;
    protected static final HashSet<String> idsUsados = new HashSet<>();
    protected static final DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("yy-MM-dd-HH:mm");

    protected LocalDateTime fechaApertura;
    protected LocalDateTime fechaCierre;
    protected TicketState state = TicketState.EMPTY;
    protected String ticketId;

    protected abstractTicket(String ticketId, String cashId) {
        this.fechaApertura = LocalDateTime.now();
        this.items = new TicketItem[100];
        this.contador = 0;
        if (ticketId == null) {
            this.ticketId = generarIdApertura();
        } else {
            if (idsUsados.contains(ticketId)) {
                throw new IllegalArgumentException("Error: Id ya usado.");
            }
            this.ticketId = ticketId;
            idsUsados.add(ticketId);
        }
    }

    public void activate() {
        if (state == TicketState.EMPTY) {
            state = TicketState.OPEN;
        }
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
    protected String generarIdApertura() {
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

    protected String generarCadenaId() {
        Random randomNumber = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(randomNumber.nextInt(10));
        }
        return sb.toString();
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

    public abstract void printTicket();
    public abstract boolean addItem(TicketItem nuevo);
    public abstract int getItemsCount();
}
