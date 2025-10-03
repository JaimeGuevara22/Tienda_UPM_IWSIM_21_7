package es.upm.etsisi.poo;

public class Product {
    private int id;
    private String nombre;
    private double precio;
    private Category categoria;


    public Product(int id, String nombre, double precio, Category categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Category getCategoria() {
        return categoria;
    }


    public void setCategoria(Category categoria) {
        this.categoria = categoria;
    }



    @Override

    public String toString() {
        return "{class:" + getClass() + ", id:" + getId() + ", name:'" + getNombre() + "', category:" + getCategoria().toString() + ", price:" + getPrecio() + "}";
    }
}
