package es.upm.etsisi.poo;

public class ProductCatalog {
    private Product[] products; //Mirar si en vez de array usar Lista

    public ProductCatalog(Product[] products) {
        this.products = products;
    }

    public ProductCatalog() {
        this.products = new Product[200];  //Numero máximo de productos
    }

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }

    public boolean addProduct(Product product) {
        for (int i = 0; i < products.length; i++) {
            if (products[i] == null) {
                products[i] = product;
                return true;
            }
            if (producto.getId() == product.getId() || producto.getNombre().equals(product.getNombre())) {
                return false;
            }
        }
        return false;
    }

    public boolean removeProduct(int id) {
        for (int i = 0; i < products.length; i++) {
            if (products[i] != null && products[i].getId() == id) {
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
        for (Product product : products) {
            if (product != null) {
                System.out.println(product.toString()); //Modificar si quitamos el toString de Productos
            }
        }
}
