package es.upm.etsisi.poo;

public class printerCombinado implements ticketPrinter {
    @Override
    public void print(ticketEmpresa ticket) {
        System.out.println("Ticket: " + ticket.getTicketId());

        System.out.println("Services included: ");
        if (ticket.getServicesList().isEmpty()) {
            System.out.println("  None");
        } else {
            for (Service s : ticket.getServicesList()) {
                System.out.println("  " + s.toString());
            }
        }

        System.out.println("Product included: ");
        double precioTotalItems = 0.0;

        for (TicketItem t : ticket.getItemsList()) {
            System.out.println("  " + t.toString());
            precioTotalItems += t.getSubtotal();
        }

        int numServicios = ticket.getServicesList().size();
        double porcentajeDescuento = 0.15 * numServicios;

        if (porcentajeDescuento > 1.0) porcentajeDescuento = 1.0;

        double descuentoExtra = precioTotalItems * porcentajeDescuento;
        double finalPrice = precioTotalItems - descuentoExtra;

        System.out.println("  Total price: " + String.format("%.2f", precioTotalItems));
        System.out.println(
                "  Extra Discount from services: " + String.format("%.2f", descuentoExtra) +
                        " **discount -" + String.format("%.2f", descuentoExtra)
        );
        System.out.println("  Total discount: " + String.format("%.2f", descuentoExtra));
        System.out.println("  Final Price: " + String.format("%.2f", finalPrice));
    }
}