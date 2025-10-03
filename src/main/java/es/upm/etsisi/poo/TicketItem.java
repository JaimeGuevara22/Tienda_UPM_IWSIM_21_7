package es.upm.etsisi.poo;

public class TicketItem {
    private Product product;
    private int cantidad;
    private double subtotal;

    public TicketItem (Product product, int cantidad, double subtotal) {
        this.product = product;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Product getProducto () {
        return product;
    }

    public void setProducto (Product product) {
        this.product = product;
    }

    public double getSubtotal () {
        return subtotal;
    }

    public void setSubtotal (double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "Producto: " + product.getNombre() +
                ", Cantidad: " + cantidad +
                ", Subtotal: " + getSubtotal();
    }
}
