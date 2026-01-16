package es.upm.etsisi.poo;

public class printerSoloServicios implements ticketPrinter {
    @Override
    public void print(ticketEmpresa ticket) {
        System.out.println("Ticket: "+ticket.getTicketId());
        System.out.println("Services included: ");
        for(int i = 0; i < ticket.getContadorServicios(); i++){
            System.out.println("  "+ticket.getService(i));
        }
    }
}
