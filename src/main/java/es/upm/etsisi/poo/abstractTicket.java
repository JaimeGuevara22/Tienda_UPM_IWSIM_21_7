package es.upm.etsisi.poo;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class abstractTicket {

    @Id
    protected String ticketId;

    protected String cashId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ticket_id")
    protected List<TicketItem> itemsList = new ArrayList<>();

    @Transient
    protected static final HashSet<String> idsUsados = new HashSet<>();

    @Transient
    protected static final DateTimeFormatter formato = DateTimeFormatter.ofPattern("yy-MM-dd-HH:mm");

    protected LocalDateTime fechaApertura;
    protected LocalDateTime fechaCierre;

    @Enumerated(EnumType.STRING)
    protected TicketState state = TicketState.EMPTY;

    protected abstractTicket() {
    }

    protected abstractTicket(String ticketId, String cashId) {
        this.fechaApertura = LocalDateTime.now();
        this.cashId = cashId;
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

        idsUsados.remove(this.ticketId);
        this.ticketId = nuevoId;
        idsUsados.add(this.ticketId);
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

    public String getTicketId() { return ticketId; }
    public TicketState getState() { return state; }
    public void setState(TicketState state) { this.state = state; }

    public boolean removeItem(int id) {
        return itemsList.removeIf(item -> item.getId() == id);
    }

    public abstract void printTicket();
    public abstract boolean addItem(TicketItem nuevo);

    public int getItemsCount() {
        return itemsList.size();
    }

    public List<TicketItem> getItemsList() {
        return itemsList;
    }
}