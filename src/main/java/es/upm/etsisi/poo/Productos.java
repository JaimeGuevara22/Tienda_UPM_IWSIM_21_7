package es.upm.etsisi.poo;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Estrategia recomendada en el Tema 5
@Table(name = "productos")
public abstract class Productos {
    @Id
    protected int id; // El ID que ya tenías
    protected String nombre;
    protected double precio;

    protected Productos() {} // Obligatorio para Hibernate

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
    public boolean tieneDescuento(){
        return false;
    }
    public double getDescuento(){
        return 0.0;
    }
}

