package es.upm.etsisi.poo;

public class Productos {

    private final int id;
    private String nombre;
    private double precio;
    private Category categoria;


    public Productos(int id, String nombre, double precio, Category categoria) {
        if (id > 0 && id <= 200) {
         this.id = id;   
        } else throw new IllegalArgumentException("Id no valido");
        if (nombre.length() > 0 && nombre.length() <= 200) {
            this.nombre = nombre;
        } else throw new IllegalArgumentException("El nombre supera el maximo de caracteres validos");
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
        if (nombre.length() > 0 && nombre.length() <= 200) {
            this.nombre = nombre;
        } else throw new IllegalArgumentException("El nombre supera el maximo de caracteres validos");
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
        return "{class:Product, id:" + id + ", name:'" + nombre + "', category:" + categoria + ", price:" + precio + "}";
    }
}

