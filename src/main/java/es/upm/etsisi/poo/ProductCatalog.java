package es.upm.etsisi.poo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ProductCatalog {

    private Productos[] products;
    private Food[] foods;
    private Meetings[] meetings;
    private int contador;

    public ProductCatalog() {
        this.products = new Productos[200];
        this.foods = new Food[200];
        this.meetings = new Meetings[200];
        this.contador = 200;
    }

    public Productos[] getProducts() {
        return products;
    }

    public void setProducts(Productos[] products) {
        this.products = products;
    }

    public boolean addProduct(Productos product) {
        if(contador > 0) {

            for (Productos p : products) {
                if (p != null && p.getId() == product.getId()) {
                    return false;
                }
            }
            for (int i = 0; i < products.length; i++) {
                if (products[i] == null) {
                    products[i] = product;
                    contador--;
                    return true;

                }
            }
        }
        return false;
    }

    public boolean removeProduct(int id) {
        for (int i = 0; i < products.length; i++) {
            if (products[i] != null && products[i].getId() == id) {
                System.out.println(products[i].toString());
                products[i] = null;
                return true;
            }
        }
        return false;
    }

    public boolean updateProduct(int id, String nombre, double precio, Category categoria) {
        for (int i = 0; i < products.length; i++) {
            if (products[i] != null && products[i].getId() == id) {
                products[i].setNombre(nombre);
                products[i].setPrecio(precio);
                products[i].setCategoria(categoria);
                return true;
            }
        }
        return false;
    }
    public void listProducts() {
        for (Productos product : products) {
            if (product != null) {
                System.out.println(product.toString());
            }
        }
    }
    public boolean addTextToProduct(int id, String texto){
        for(Productos p : products){
            if(p != null && p.getId() == id){
                if(p instanceof ProductosPersonalizables){
                    ProductosPersonalizables productoPersonalizable = (ProductosPersonalizables) p;
                    return productoPersonalizable.addTexto(texto);
                }else{
                    return false;
                }
            }
        }
        return false;
    }
    public boolean addFood(Food food) {
        LocalDate now = LocalDate.now();
        LocalDate expirationDate = food.getFoodExpirationDate();
        long days = ChronoUnit.DAYS.between(now, expirationDate);
        if(contador > 0) {

            for (Food f : foods) {
                if (f != null && f.getId() == f.getId()) {
                    return false;
                }
            }
            for (int i = 0; i < foods.length; i++) {
                if (foods[i] == null && days >= 3) {
                    foods[i] = food;
                    contador--;
                    return true;
                }
            }
        }
        return false;
    }
    public boolean addMeetings(Meetings meeting){
        LocalDateTime now = LocalDateTime.now();
        LocalDate expirationDate = meeting.getMeetingsExpirationDate();
        long hours = ChronoUnit.HOURS.between(now, expirationDate);
        if(hours < 12){
            return false;
        }else{
            return true;
        }
    }
}