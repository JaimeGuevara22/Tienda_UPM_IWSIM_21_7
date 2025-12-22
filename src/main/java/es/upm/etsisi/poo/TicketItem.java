package es.upm.etsisi.poo;

public class TicketItem {

    private Productos item;
    private int cantidad;
    private double subtotal;

    public TicketItem (Productos item, int cantidad) {
        this.item = item;
        this.cantidad = cantidad;
        actualizarSubtotal();

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
    private void actualizarSubtotal() {
        double precioUnitario = item.getPrecio();
        subtotal = precioUnitario * cantidad;
        if(cantidad > 1 && item.tieneDescuento()){
            subtotal *= (1 - item.getDescuento());
        }
    }
    public int getId() {
        return item.getId();
    }
    public double getSubtotal() {
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
