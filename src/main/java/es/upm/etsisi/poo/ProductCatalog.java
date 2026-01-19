package es.upm.etsisi.poo;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ProductCatalog {

    public ProductCatalog() {
    }

    public Productos getProductById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Productos.class, id);
        }
    }

    public Service getProductByIdServicio(String id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            int numericId = Integer.parseInt(id.replace("S", "").replace("s", ""));
            return session.get(Service.class, numericId);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean addProduct(Productos p) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            if (session.get(Productos.class, p.getId()) != null) {
                return false;
            }

            session.persist(p);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }
    public boolean addService(Service servicio) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(servicio);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        }
    }

    public boolean remove(int id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Productos p = session.get(Productos.class, id);
            if (p != null) {
                tx = session.beginTransaction();
                session.remove(p);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        }
    }

    public boolean removeService(String id) {
        if (id == null || !id.toUpperCase().endsWith("S")) return false;

        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            int idNumerico = Integer.parseInt(id.substring(0, id.length() - 1));
            Service s = session.get(Service.class, idNumerico);
            if (s != null) {
                tx = session.beginTransaction();
                session.remove(s);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        }
    }

    public void listProducts() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Service> servicios = session.createQuery("from Service", Service.class).list();
            List<Productos> productos = session.createQuery("from Productos", Productos.class).list();

            if (servicios.isEmpty() && productos.isEmpty()) {
                System.out.println("Catalogo vacío");
                return;
            }

            if (!servicios.isEmpty()) {
                System.out.println("Catalogo servicios: ");
                servicios.forEach(s -> System.out.println(s.toString()));
            }

            if (!productos.isEmpty()) {
                System.out.println("Catalogo productos: ");
                productos.forEach(p -> System.out.println(p.toString()));
            }
        }
    }

    public boolean updateField(int id, String field, String value) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Productos p = session.get(Productos.class, id);
            if (p == null) return false;

            tx = session.beginTransaction();

            switch (field.toUpperCase()) {
                case "NAME" -> p.setNombre(value);
                case "PRICE" -> {
                    try {
                        p.setPrecio(Double.parseDouble(value));
                    } catch (NumberFormatException e) { return false; }
                }
                case "CATEGORY" -> {
                    if (p instanceof Product prod) {
                        try {
                            prod.setCategoria(Category.valueOf(value.toUpperCase()));
                        } catch (IllegalArgumentException e) { return false; }
                    } else {
                        return false;
                    }
                }
                default -> { return false; }
            }

            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        }
    }
}