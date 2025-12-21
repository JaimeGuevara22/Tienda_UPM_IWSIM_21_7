package es.upm.etsisi.poo;

public class Product extends Productos{
    protected Category category;
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
    public String toString() {
        return "{class:Product, id:" + id + ", name:'" + nombre + "', category:" + category + ", price:" + precio + "}";
    }
}
