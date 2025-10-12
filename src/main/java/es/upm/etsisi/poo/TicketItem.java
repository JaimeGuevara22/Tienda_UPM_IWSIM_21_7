package es.upm.etsisi.poo;

public class TicketItem {

    private Productos product;
    private int cantidad;
    private double subtotal;

    public TicketItem (Productos product, int cantidad) {
        this.product = product;
        this.cantidad = cantidad;
        if (cantidad > 1) {
            this.subtotal = (product.getPrecio() * cantidad) * (1 - DiscountPolicy.getDiscountRate(product.getCategoria()));
        } else this.subtotal = (product.getPrecio() * cantidad);
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        actualizarSubtotal();
    }

    public Productos getProducto () {
        return product;
    }

    public void setProducto (Productos product) {
        this.product = product;
    }

    public double getSubtotal () {
        return subtotal;
    }

    public void setSubtotal (double subtotal) { //creo que puede sobrar este set, porque subTotal no lo escribe el usuario
        this.subtotal = subtotal;
    }

    private void actualizarSubtotal() {
        if (cantidad > 1) {
            this.subtotal = (product.getPrecio() * cantidad) * (1 - DiscountPolicy.getDiscountRate(product.getCategoria()));
        } else this.subtotal = (product.getPrecio() * cantidad);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getProducto().toString()).append(" **discount -").append(String.format("%.2f",
                DiscountPolicy.getDiscountRate(product.getCategoria()) * product.getPrecio()));
        return sb.toString();
    }
}
