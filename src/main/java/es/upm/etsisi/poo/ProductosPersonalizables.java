package es.upm.etsisi.poo;

import java.util.ArrayList;

public class ProductosPersonalizables extends Productos {
    private final int maxTextos;
    private ArrayList<String> textos;
    public ProductosPersonalizables(int id, String nombre, double precio, Category categoria, int maxTextos){
        super(id, nombre, precio, categoria);
        if(maxTextos < 0){
            throw new IllegalArgumentException("Número de textos negativo");
        }
        this.maxTextos = maxTextos;
        this.textos = new ArrayList<>();
    }
    public int getMaxTextos() {
        return maxTextos;
    }
    public ArrayList<String> getTextos() {
        return textos;
    }
    public boolean addTexto(String texto){
        if(texto == null || texto.isBlank()){
            throw new IllegalArgumentException("No se puede añadir un texto null.");
        }
        if(textos.size() >= maxTextos) {
            return false;
        }
        textos.add(texto);
        return true;
    }

    @Override
    public double getPrecio(){
        double precioBase = super.getPrecio();
        double recargo = 0.1 * precioBase * textos.size();
        return recargo + precioBase;
    }
    @Override
    public String toString(){
        return "{class:ProductPersonalized, id:" + getId() + ", name:'" + getNombre() + "', category:" + getCategoria() + ", price:" + this.getPrecio() + "}";
    }
}
