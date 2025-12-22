package es.upm.etsisi.poo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ProductCatalog {
    private int contador;
    private Productos[] items;

    public ProductCatalog() {
        this.items = new Productos[200];
        this.contador = 0;
    }


    public Productos getProductById(int id) {
        for (Productos p : items) {
            if(p != null && p.getId() == id){
                return p;
            }
        }
        return null;
    }

    public boolean addProduct(Productos item) {
        if (contador >= items.length) {
            return false;
        }
        int id = item.getId();
        if (id < 0) return false;
        for (Productos p : items) {
            if (p != null && p.getId() == id) {
                return false;
            }
        }
        if (item instanceof Food) {
            Food f = (Food) item;
            long days = ChronoUnit.DAYS.between(LocalDate.now(), f.getFoodExpirationDate());
            if (days < 3 || contador >= 100) return false;
        }
        if (item instanceof Meetings) {
            Meetings m = (Meetings) item;
            long horas = ChronoUnit.HOURS.between(LocalDateTime.now(), m.getMeetingsExpirationDate().atStartOfDay());

            if (horas< 12 || contador >= 100) return false;
        }
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = item;
                contador++;
                return true;
            }
            if (items[i].getId() > id) {
                for (int j = items.length - 1; j > i; j--) {
                    items[j] = items[j - 1];
                }
                items[i] = item;
                contador++;
                return true;
            }
        }
        return false;
    }

    public boolean remove(int id) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].getId() == id) {
                for (int j = i; j < items.length - 1; j++) {
                    items[j] = items[j + 1];
                }
                items[items.length - 1] = null;
                contador--;
                return true;
            }
        }
        return false;
    }

    public boolean updateField(int id, String field, String value) {
        Productos p = getProductById(id);
            if (p == null) return false;
                switch (field.toUpperCase()) {
                    case "NAME" -> {
                        p.setNombre(value);
                        return true;
                    }
                    case "PRICE" -> {
                        try {
                            double price = Double.parseDouble(value);
                            p.setPrecio(price);
                            return true;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    }
                    case "CATEGORY" -> {
                        try {
                            if(p instanceof Product){
                                Category cat = Category.valueOf(value.toUpperCase());
                                ((Product) p).setCategoria(cat);
                                return true;
                            }
                        } catch (IllegalArgumentException e) {
                            return false;
                        }
                    }
                    default -> {
                        return false;
                    }
                }
        return false;
    }

    public void listProducts() {
        for (Productos product : items) {
            if (product != null) {
                System.out.println(product.toString());
            }
        }
    }

    public boolean addTextToProduct(int id, String texto) {// no necesario tenemos otro igual en productosPersonalizables
        Productos p = getProductById(id);
        if(p instanceof ProductosPersonalizables pp){
            pp.addTexto(texto);
            return true;
        }
        return false;
    }
}