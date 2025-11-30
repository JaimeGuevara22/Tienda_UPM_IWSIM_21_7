package es.upm.etsisi.poo;

public class TicketItem {

    private Object item;
    private int cantidad;
    private double subtotal;

    public TicketItem (Object item, int cantidad) {
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

    public Object getItem() {
        return item;
    }
    private void actualizarSubtotal() {
        double precioUnitario = getPrecio(item) ;

        if(item instanceof Productos p && cantidad > 1) {
            this.subtotal=precioUnitario* cantidad*(1-p.getCategoria().getDiscount());
        }else{
            this.subtotal=precioUnitario*cantidad;
        }
    }
    public String getId() {
        if (item instanceof Productos p) return String.valueOf(p.getId());
        if (item instanceof Food f) return f.getId();
        if (item instanceof Meetings m) return m.getId();
        return null;
    }
    public double getSubtotal() {
        return subtotal;
    }

    public double getPrecio(Object o) {
        if (o instanceof Productos p) return p.getPrecio();
        if (o instanceof Food f) return f.getPrice();         // asegúrate que existe getPrice()
        if (o instanceof Meetings m) return m.getPrice();     // idem
        return 0;
    }

    @Override
    public String toString() {
        double precioUnitario = getPrecio(item);
        double descuento = 0;
        if (item instanceof Productos p && cantidad > 1) {
            descuento = precioUnitario * p.getCategoria().getDiscount();
        }
        return item.toString() + " **discount -" + String.format("%.2f", descuento);
    }
}
