package es.upm.etsisi.poo;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class cashController {

    public boolean addCash(Cash cash) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

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
            List<Cash> cashes = session.createQuery("from Cash", Cash.class).getResultList();

            cashes.sort((c1, c2) -> c1.getNombre().compareToIgnoreCase(c2.getNombre()));

            if (cashes.isEmpty()) {

            } else {
                for (Cash c : cashes) {
                    System.out.println(c.toString());
                }
            }
        } catch (Exception e) {

        }
    }

    public Cash findCashById(String id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.get(Cash.class, id);
        } catch (Exception e) {
            return null;
        }
    }
}