package es.upm.etsisi.poo;

public class printerCombinado implements ticketPrinter {
    @Override
    public void print(ticketEmpresa ticket) {
        System.out.println("Ticket: " + ticket.getTicketId());

        // 1. Imprimir Servicios
        System.out.println("Services included: ");
        if (ticket.getServicesList().isEmpty()) {
            System.out.println("  None");
        } else {
            for (Service s : ticket.getServicesList()) {
                System.out.println("  " + s.toString());
            }
        }

        // 2. Imprimir Productos
        System.out.println("Product included: ");
        double precioTotalItems = 0.0;

        // Ahora getItemsList() funcionará porque lo hemos añadido a abstractTicket
        for (TicketItem t : ticket.getItemsList()) {
            System.out.println("  " + t.toString());
            precioTotalItems += t.getSubtotal();
        }

        // 3. Cálculos de descuento según enunciado
        // 15% de descuento en el total de productos por cada servicio incluido
        int numServicios = ticket.getServicesList().size();
        double porcentajeDescuento = 0.15 * numServicios;

        // Ojo: el descuento no debería superar el 100% (si hay muchos servicios)
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