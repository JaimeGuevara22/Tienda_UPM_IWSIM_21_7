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

        if(item instanceof Product p && cantidad > 1) {
            subtotal = precioUnitario* cantidad*(1-p.getCategoria().getDiscount());
        }else{
            subtotal = precioUnitario*cantidad;
        }
    }
    public int getId() {
        if (item instanceof Product p) return p.getId();
        if (item instanceof Food f) return f.getId();
        if (item instanceof Meetings m) return m.getId();
        return -1;
    }
    public double getSubtotal() {
        return subtotal;
    }

    public double getPrecio(Object o) {
        if (o instanceof Product p) return p.getPrecio();
        if (o instanceof Food f) {
            return f.getPrice() * f.getNumParticipants();         // asegúrate que existe getPrice()
        }
        if (o instanceof Meetings m){
            return m.getPrice();
        }
        return 0;
    }

    @Override
    public String toString() {
        if(item instanceof Product p) {
            double descuento = (cantidad > 1) ? getPrecio(item) * p.getCategoria().getDiscount() : 0;
            return item.toString() + " **discount -" + String.format("%.2f", descuento);
        }  else if(item instanceof ProductosPersonalizables pp) {
            double descuento = (cantidad > 1) ? getPrecio(item) * pp.getCategoria().getDiscount() : 0;
            return item.toString() + " **discount -" + String.format("%.2f", descuento);
        }
        return item.toString();
    }
}
