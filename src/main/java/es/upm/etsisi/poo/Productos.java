package es.upm.etsisi.poo;

public abstract class Productos {

    protected final int id;
    protected String nombre;
    protected double precio;


    public Productos(int id, String nombre, double precio) {
         this.id = id;
        if (nombre.length() > 0 && nombre.length() <= 200) {
            this.nombre = nombre;
        } else throw new IllegalArgumentException("El nombre supera el maximo de caracteres validos");
        this.precio = precio;
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
}

