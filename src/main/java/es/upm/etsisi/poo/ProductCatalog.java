package es.upm.etsisi.poo;

public class ProductCatalog {

    private Productos[] products; //Mirar si en vez de array usar Lista

    public ProductCatalog(Productos[] products) {
        this.products = products;
    }

    public ProductCatalog() {
        this.products = new Productos[200];  //Numero máximo de productos
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
            if (products[i].getId() == product.getId() || products[i].getNombre().equals(product.getNombre())) {
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


}