package es.upm.etsisi.poo;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket_items")
public class TicketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_interno;

    @ManyToOne(fetch = FetchType.EAGER) // Aseguramos que el producto se cargue siempre
    @JoinColumn(name = "producto_id", nullable = false)
    private Productos item;

    private int cantidad;

    // Marcamos subtotal como @Transient si quieres que se calcule siempre en vivo,
    // o lo dejamos normal pero añadimos un @PostLoad para que Hibernate lo calcule al leer de la DB.
    private double subtotal;

    // Constructor vacío obligatorio para Hibernate
    protected TicketItem() {
    }

    public TicketItem(Productos item, int cantidad) {
        if (item == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        this.item = item;
        this.cantidad = cantidad;
        actualizarSubtotal();
    }

    // Este método le dice a Hibernate que ejecute esta lógica justo después de leer el objeto de la DB
    @PostLoad
    public void actualizarSubtotal() {
        if (item != null) {
            double precioUnitario = item.getPrecio();
            double total = precioUnitario * cantidad;
            if (cantidad > 1 && item.tieneDescuento()) {
                total *= (1 - item.getDescuento());
            }
            this.subtotal = total;
        }
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        actualizarSubtotal();
    }

    public Productos getItem() {
        return item;
    }

    public int getId() {
        return item != null ? item.getId() : -1;
    }

    public double getSubtotal() {
        // Por seguridad, si el subtotal es 0 y hay item, recalculamos
        if (subtotal == 0 && item != null) actualizarSubtotal();
        return subtotal;
    }

    @Override
    public String toString() {
        if (item == null) return "Item no disponible";

        // El enunciado de la tienda suele pedir el formato: nombre x cantidad
        String base = item.getNombre() + " x" + cantidad;

        if (cantidad > 1 && item.tieneDescuento()) {
            double descuentoTotal = (item.getPrecio() * item.getDescuento()) * cantidad;
            return base + " **discount -" + String.format("%.2f", descuentoTotal);
        }
        return base;
    }
}