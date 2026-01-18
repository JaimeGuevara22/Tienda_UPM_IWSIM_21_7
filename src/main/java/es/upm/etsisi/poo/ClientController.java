package es.upm.etsisi.poo;

import org.hibernate.*;
import java.util.List;

public class ClientController {

    public boolean addClient(Client client) {
        // Usamos try-with-resources para asegurar el cierre de la sesión
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            // Verificamos si ya existe por ID
            if (session.get(Client.class, client.getId()) != null) {
                return false;
            }

            // En Hibernate 6 se usa persist en lugar de save
            session.persist(client);
            tx.commit();
            return true;
        } catch (Exception e) {
            // No imprimimos el error por consola según requisitos, pero hacemos rollback
            return false;
        }
    }

    public void list() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL: "from Client" selecciona todos los objetos de la clase Client
            List<Client> clients = session.createQuery("from Client", Client.class).getResultList();

            // Ordenamos alfabéticamente
            clients.sort((c1, c2) -> c1.getNombre().compareToIgnoreCase(c2.getNombre()));

            if (clients.isEmpty()) {
                System.out.println("No clients registered.");
            } else {
                clients.forEach(c -> System.out.println(c.toString()));
            }
        } catch (Exception e) {
            // Silenciamos error según enunciado
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