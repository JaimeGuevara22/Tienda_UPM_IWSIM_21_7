package es.upm.etsisi.poo;

import java.time.Duration;
import java.time.LocalDateTime;

public class ProductCatalog {

    private Productos[] products;

    public ProductCatalog(Productos[] products) {
        this.products = products;
    }

    public ProductCatalog() {
        this.products = new Productos[200];
    }

    public Productos[] getProducts() {
        return products;
    }

    public void setProducts(Productos[] products) {
        this.products = products;
    }

    public boolean addProduct(Productos product) {
        for (int i = 0; i < products.length; i++) {
            if (products[i] == null) {
                products[i] = product;
                return true;
            }
            if (products[i].getId() == product.getId()) {
                return false;
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
                System.out.println(product.toString()); //Modificar si quitamos el toString de Productos
            }
        }
    }
    public boolean addFood(Food food) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationDate = food.getExpirationDate();
        long days = Duration.between(now, expirationDate).toDays();//me pasa la comparación de fechas a días
        return false;
        //comprobar que es mayor que 3 días para meterla o no
    }
}