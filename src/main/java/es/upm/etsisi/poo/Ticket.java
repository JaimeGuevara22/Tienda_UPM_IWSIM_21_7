package es.upm.etsisi.poo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Comparator;

@Entity
@Table(name = "tickets_normales")
public class Ticket extends abstractTicket {

    // Constructor vacío requerido por Hibernate
    protected Ticket() {
        super();
    }

    public Ticket(String ticketId, String cashId) {
        super(ticketId, cashId);
    }

    @Override
    public boolean addItem(TicketItem nuevo) {
        if (nuevo == null || nuevo.getItem() == null) {
            return false;
        }

        // Buscamos si el producto ya existe en el ticket para incrementar la cantidad
        for (TicketItem existente : itemsList) {
            if (existente.getItem().getId() == nuevo.getItem().getId()) {
                // Lógica especial para productos personalizados
                if (existente.getItem() instanceof ProductosPersonalizables a &&
                        nuevo.getItem() instanceof ProductosPersonalizables b) {
                    // Si los textos son diferentes, no se agrupan (se añaden como línea nueva)
                    if (!a.getTextos().equals(b.getTextos())) {
                        continue;
                    }
                }
                // Si coinciden ID (y textos si son personalizados), sumamos cantidad
                existente.setCantidad(existente.getCantidad() + nuevo.getCantidad());
                return true;
            }
        }

        // Si no existe, lo añadimos a la lista persistente
        return itemsList.add(nuevo);
    }

    public double getTotalSinDescuento() {
        return itemsList.stream()
                .mapToDouble(ti -> ti.getItem().getPrecio() * ti.getCantidad())
                .sum();
    }

    public double getTotalConDescuento() {
        return itemsList.stream()
                .mapToDouble(TicketItem::getSubtotal)
                .sum();
    }

    @Override
    public void printTicket() {
        if (itemsList.isEmpty()) {
            System.out.println("Empty ticket");
            return;
        }

        System.out.println("Ticket: " + ticketId);

        // Ordenamos por nombre del producto alfabéticamente (ignorando mayúsculas)
        itemsList.stream()
                .sorted(Comparator.comparing(ti -> ti.getItem().getNombre().toLowerCase()))
                .forEach(ti -> {
                    // Si es un producto físico, imprimimos una línea por cada unidad
                    if (ti.getItem() instanceof Product || ti.getItem() instanceof ProductosPersonalizables) {
                        for (int i = 0; i < ti.getCantidad(); i++) {
                            System.out.println(ti);
                        }
                    } else {
                        // Para otros tipos (como Food/Meetings que suelen ir agrupados), imprimimos la línea del item
                        System.out.println(ti);
                    }
                });

        double sinDesc = getTotalSinDescuento();
        double conDesc = getTotalConDescuento();

        System.out.println("Total price: " + String.format("%.2f", sinDesc));
        System.out.println("Total discount: " + String.format("%.2f", sinDesc - conDesc));
        System.out.println("Final Price: " + String.format("%.2f", conDesc));
    }
}