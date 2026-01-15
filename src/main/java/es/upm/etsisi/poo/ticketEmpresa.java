package es.upm.etsisi.poo;
import java.util.*;
public class ticketEmpresa extends abstractTicket{
    private ticketPrinter printer;
    public ticketEmpresa(String ticketId, String cashId,ticketPrinter printer) {
        super(ticketId, cashId);
        this.printer = printer;
    }

    @Override
    public void printTicket() {
    }
}
