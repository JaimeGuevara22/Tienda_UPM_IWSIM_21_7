package es.upm.etsisi.poo;

import org.hibernate.*;
import java.util.List;

public class ClientController {

    public boolean addClient(Client client) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            if (session.get(Client.class, client.getId()) != null) {
                return false;
            }

            session.persist(client);
            tx.commit();
            return true;
        } catch (Exception e) {

            return false;
        }
    }

    public void list() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Client> clients = session.createQuery("from Client", Client.class).getResultList();

            clients.sort((c1, c2) -> c1.getNombre().compareToIgnoreCase(c2.getNombre()));

            if (clients.isEmpty()) {
                System.out.println("No clients registered.");
            } else {
                clients.forEach(c -> System.out.println(c.toString()));
            }
        } catch (Exception e) {

        }
    }

    public boolean removeClient(String dni) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Client client = session.get(Client.class, dni);

            if (client != null) {
                session.remove(client);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public Client findClientByDNI(String DNI) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Client.class, DNI);
        } catch (Exception e) {
            return null;
        }
    }
}