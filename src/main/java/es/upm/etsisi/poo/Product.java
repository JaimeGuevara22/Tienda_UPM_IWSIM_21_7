package es.upm.etsisi.poo;
import jakarta.persistence.*;

@Entity
public class Product extends Productos implements sellable {
    @Enumerated(EnumType.STRING)
    protected Category category;

    protected Product() {}

    public Product(int id, String nombre, double precio, Category categoria) {
        super(id,nombre,precio);
        this.category = categoria;
    }
    public Category getCategoria() {
        return category;
    }
    public void setCategoria(Category category) {
        this.category = category;
    }
    @Override
    public boolean tieneDescuento(){
        return true;
    }
    @Override
    public double getDescuento(){
        return getCategoria().getDiscount();
    }
    @Override
    public String toString() {
        return "{Class:Product, id:" + id + ", name:'" + nombre + "', category:" + category + ", price:" + precio + "}";
    }
}
