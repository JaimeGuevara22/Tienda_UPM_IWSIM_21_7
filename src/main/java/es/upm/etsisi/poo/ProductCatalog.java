package es.upm.etsisi.poo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ProductCatalog {
    private int contador;
    private Object[] items;

    public ProductCatalog() {
        this.items = new Object[200];
        this.contador = 0;
    }

    private int getIdFrom(Object item) {
        try {
            if (item instanceof Productos) {
                return ((Productos) item).getId();
            }
            if (item instanceof Food) {
                return ((Food) item).getId();
            }
            if (item instanceof Meetings) {
                return ((Meetings) item).getId();
            }
            return -1;
        }catch (NumberFormatException e) {
            return -1;
        }
    }

    public Object getProductById(int id) {
        for (Object o : items) {
            if (o instanceof Productos p && p.getId() == id) {
                return p;
            }
            if (o instanceof Food f && f.getId() == id){
                return f;
            }
            if (o instanceof Meetings m && m.getId() == id){
                return m;
            }
            if (o instanceof ProductosPersonalizables pp && pp.getId() == id) {
                return pp;
            }
        }
        return null;
    }

    public boolean addProduct(Object item) {
        if (contador >= 200) {
            return false;
        }
        int id = getIdFrom(item);
        if (id < 0) return false;
        for (Object o : items) {
            if (o != null && getIdFrom(o) == id) {
                return false;
            }
        }
        if (item instanceof Food) {
            Food f = (Food) item;
            long days = ChronoUnit.DAYS.between(LocalDate.now(), f.getFoodExpirationDate());
            if (days < 3) return false;
        }
        if (item instanceof Meetings) {
            Meetings m = (Meetings) item;
            long horas = ChronoUnit.HOURS.between(LocalDateTime.now(), m.getMeetingsExpirationDate().atStartOfDay());

            if (horas<12) return false;
        }
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = item;
                contador++;
                return true;
            }
            if (getIdFrom(items[i]) > id) {
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
            if (items[i] != null && getIdFrom(items[i]) == id) {
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
        for (Object o : items) {
            if (o == null) continue;
            if (o instanceof Product p && p.getId() == id) {
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
                            Category cat = Category.valueOf(value.toUpperCase());
                            p.setCategoria(cat);
                            return true;
                        } catch (IllegalArgumentException e) {
                            return false;
                        }
                    }
                    default -> {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public void listProducts() {
        for (Object product : items) {
            if (product != null) {
                System.out.println(product.toString());
            }
        }
    }

    public boolean addTextToProduct(int id, String texto) {// no necesario tenemos otro igual en productosPersonalizables
        for (Object o : items) {
            if (o instanceof ProductosPersonalizables p && p.getId() == id) {
                return p.addTexto(texto);
            }
        }
        return false;
    }
}