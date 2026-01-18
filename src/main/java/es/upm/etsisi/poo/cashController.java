package es.upm.etsisi.poo;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class cashController {

    public boolean addCash(Cash cash) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            // Verificamos si ya existe por su ID (cashId)
            if (session.get(Cash.class, cash.getCashId()) != null) {
                return false;
            }

            session.persist(cash);
            tx.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean cashRemove(String cashId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Cash cash = session.get(Cash.class, cashId);
            if (cash != null) {
                session.remove(cash);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void list() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL para obtener todos los cajeros
            List<Cash> cashes = session.createQuery("from Cash", Cash.class).getResultList();

            // Ordenamos alfabéticamente por nombre
            cashes.sort((c1, c2) -> c1.getNombre().compareToIgnoreCase(c2.getNombre()));

            if (cashes.isEmpty()) {
                // Opcional: imprimir algo si está vacío
            } else {
                for (Cash c : cashes) {
                    System.out.println(c.toString());
                }
            }
        } catch (Exception e) {
            // Silencio según enunciado
        }
    }

    public Cash findCashById(String id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Buscamos directamente por la clave primaria
            return session.get(Cash.class, id);
        } catch (Exception e) {
            return null;
        }
    }
}