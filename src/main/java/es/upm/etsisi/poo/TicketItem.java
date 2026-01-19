package es.upm.etsisi.poo;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket_items")
public class TicketItem{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_interno;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id", nullable = false)
    private Productos item;

    private int cantidad;

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
        if (subtotal == 0 && item != null) actualizarSubtotal();
        return subtotal;
    }

    @Override
    public String toString() {
        if (cantidad > 1 && item.tieneDescuento()) {
            double descuento = item.getPrecio() * item.getDescuento();
            return item + " **discount -" + String.format("%.2f", descuento);
        }
        return item.toString();
    }
}