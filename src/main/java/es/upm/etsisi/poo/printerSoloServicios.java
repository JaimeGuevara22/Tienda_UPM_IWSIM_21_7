package es.upm.etsisi.poo;

public class printerSoloServicios implements ticketPrinter {
    @Override
    public void print(ticketEmpresa ticket) {
        System.out.println("Ticket: " + ticket.getTicketId());
        System.out.println("Services included: ");

        for (Service s : ticket.getServicesList()) {
            System.out.println("  " + s.toString());
        }
    }
}