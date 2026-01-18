package es.upm.etsisi.poo;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tickets_empresa")
public class ticketEmpresa extends abstractTicket { // Quitamos <Object>

    @Transient
    private ticketPrinter printer;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ticket_empresa_servicios") // Tabla intermedia para la relación ManyToMany
    private List<Service> services = new ArrayList<>();

    protected ticketEmpresa() { super(); }

    public ticketEmpresa(String ticketId, String cashId, ticketPrinter printer) {
        super(ticketId, cashId);
        this.printer = printer;
    }

    public boolean addService(Service service) {
        if (service != null) {
            return services.add(service);
        }
        return false;
    }

    @Override
    public boolean addItem(TicketItem nuevo) {
        if (services.isEmpty()) return false;
        return itemsList.add(nuevo);
    }

    @Override
    public void printTicket() {
        if (printer != null) {
            printer.print(this);
        } else {
            System.out.println("No printer assigned for company ticket");
        }
    }

    @Override
    public void close() {
        if (services.isEmpty() && !itemsList.isEmpty()) {
            throw new IllegalStateException("Ticket empresa no puede tener solo productos");
        }
        super.close();
    }

    public List<Service> getServicesList() { return services; }
}