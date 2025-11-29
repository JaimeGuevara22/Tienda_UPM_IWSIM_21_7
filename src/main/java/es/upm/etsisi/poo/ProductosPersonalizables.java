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

    public double getPrecioBase() {
        return super.getPrecio();
    }

    @Override
    public double getPrecio(){
        double precioBase = super.getPrecio();
        double recargo = 0.1 * precioBase * textos.size();
        return recargo + precioBase;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("{class:ProductPersonalized, id:")
                .append(getId())
                .append(", name:'")
                .append(getNombre())
                .append("', category:")
                .append(getCategoria())
                .append(", price:")
                .append(this.getPrecio())
                .append(", maxPersonal:")
                .append(maxTextos);

        // Mostrar textos si existen
        if (textos != null && !textos.isEmpty()) {
            sb.append(", personalizationList:[");
            for (int i = 0; i < textos.size(); i++) {
                sb.append(textos.get(i));
                if (i < textos.size() - 1) sb.append(", ");
            }
            sb.append("]");
        }

        sb.append("}");

        return sb.toString();
    }
}
