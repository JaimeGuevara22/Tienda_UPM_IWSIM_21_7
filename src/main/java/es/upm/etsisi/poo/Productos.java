package es.upm.etsisi.poo;

public class Productos {

    private final int id;
    private String nombre;
    private double precio;
    private Category categoria;


    public Productos(int id, String nombre, double precio, Category categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
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
        StringBuilder sb = new StringBuilder();
        sb.append("{class:").append(getClass()).append(", id:").append(getId()).append(", name:'").append(getNombre()).append("'").append(", category:").append(getCategoria()).append(", price:").append(getPrecio()).append("}");
        return sb.toString();
    }
}

