package es.upm.etsisi.poo;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ProductosPersonalizables extends Product {

    private int maxTextos;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "producto_personalizado_textos", joinColumns = @JoinColumn(name = "producto_id"))
    @Column(name = "texto")
    private List<String> textos = new ArrayList<>();

    // Constructor vacío obligatorio para Hibernate
    protected ProductosPersonalizables() {
        super();
    }

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

    public List<String> getTextos() {
        return textos;
    }


    public boolean addTexto(String texto){
        if(texto == null || texto.isBlank()){
            return false;
        }
        if(textos == null) textos = new ArrayList<>(); // Seguridad para Hibernate
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
        // Recargo del 10% por cada texto añadido
        double recargo = 0.1 * precioBase * (textos != null ? textos.size() : 0);
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
                .append(getPrecio())
                .append(", máximo de textos:")
                .append(maxTextos);

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