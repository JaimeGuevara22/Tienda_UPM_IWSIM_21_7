package es.upm.etsisi.poo;

public class printerCombinado implements ticketPrinter{
    @Override
    public void print (ticketEmpresa ticket){
        System.out.println("Ticket: "+ticket.getTicketId());
        System.out.println("Services included: ");
        for(int i = 0; i < ticket.getContadorServicios(); i++){
            System.out.println("  "+ticket.getService(i));
        }
        System.out.println("Product included: ");
        double precioTotal = 0.0;
        for (int i = 0; i < ticket.getItemsCount(); i++){
            TicketItem t = ticket.getItem(i);
            System.out.println("  "+t);
            precioTotal += t.getSubtotal();
        }
        int numServicios = ticket.getContadorServicios();
        double descuentoExtra = precioTotal * 0.15 * numServicios;

        System.out.println("  Total price: " + precioTotal);
        System.out.println(
                "  Extra Discount from services:" + descuentoExtra +
                        " **discount -" + descuentoExtra
        );
        System.out.println("  Total discount: " + descuentoExtra);
        System.out.println("  Final Price: " + (precioTotal - descuentoExtra));
    }
}
