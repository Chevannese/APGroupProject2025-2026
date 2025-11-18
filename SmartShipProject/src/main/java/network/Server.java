package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.Route;
import model.Shipment;
import model.User;
import model.Vehicle;
import model.Assignment;
import model.Trip;

public class Server {
    private static final Logger logger = LogManager.getLogger(Server.class);
    private static SessionFactory sessionFactory = null;

    private ServerSocket serverSocket;

    static {
        try {
            sessionFactory = new Configuration().configure()
                .addAnnotatedClass(model.User.class)
                .addAnnotatedClass(model.Driver.class)
                .addAnnotatedClass(model.Customer.class)
                .addAnnotatedClass(model.Clerk.class)
                .addAnnotatedClass(model.Manager.class)
                .addAnnotatedClass(model.Shipment.class)
                .addAnnotatedClass(model.Assignment.class)
                .addAnnotatedClass(model.Route.class)
                .addAnnotatedClass(model.Trip.class)
                .addAnnotatedClass(model.Vehicle.class)
                .buildSessionFactory();

            logger.info("SessionFactory initialized successfully.");
        } catch (Throwable ex) {
            logger.error("Failed to initialize SessionFactory.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void waitForRequests() {
        try {
            serverSocket = new ServerSocket(8888);
            logger.info("Server running on port 8888...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("Client connected: " + clientSocket.getInetAddress());
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            logger.error("Server error: " + e.getMessage(), e);
        }
    }

    private void handleClient(Socket socket) {
        try (
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ) {
            out.flush();
            String action = (String) in.readObject();
            logger.info("Action received: {}", action);

            switch (action) {
                case "Create Account":
                    handleCreateAccount(in, out);
                    break;
                case "SignIn":
                    handleSignIn(in, out);
                    break;
                case "Request Order":
                    handleRequestOrder(in, out);
                    break;
                case "GetDriverShipments":
                    handleGetDriverShipments(in, out);
                    break;
                case "UpdateShipmentStatus":
                    handleUpdateShipmentStatus(in, out);
                    break;
                case "GetDriverRoute":
                    handleGetDriverRoute(in, out);
                    break;
                case "GetDriverVehicle":
                    handleGetDriverVehicle(in, out);
                    break;
                default:
                    logger.warn("Unknown action: {}", action);
                    out.writeObject("error-unknown-action");
            }
            out.flush();
        } catch (Exception e) {
            logger.error("Client handling error: " + e.getMessage(), e);
        }
    }

    // ======================================================
    // ACTION HANDLERS
    // ======================================================

    private void handleCreateAccount(ObjectInputStream in, ObjectOutputStream out) throws Exception {
        User user = (User) in.readObject();
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            if (session.find(User.class, user.getTrn()) != null) {
                out.writeObject("error-duplicate-trn");
            } else {
                session.persist(user);
                session.getTransaction().commit();
                out.writeObject("done");
            }
        } catch (Exception e) {
            out.writeObject("error-database-issue");
            throw e;
        }
    }

    private void handleSignIn(ObjectInputStream in, ObjectOutputStream out) throws Exception {
        User inputUser = (User) in.readObject();
        try (Session session = getSessionFactory().openSession()) {
            User existingUser = session.find(User.class, inputUser.getTrn());

            if (existingUser == null) {
                out.writeObject("User-does-not-exist");
                out.writeObject(null);
            } else if (existingUser.getPassword().equals(inputUser.getPassword())) {
                out.writeObject("success");
                out.writeObject(existingUser);
            } else {
                out.writeObject("The password that was entered by the user is incorrect");
                out.writeObject(null);
            }
        }
    }

    private void handleRequestOrder(ObjectInputStream in, ObjectOutputStream out) throws Exception {
        Shipment shipment = (Shipment) in.readObject();
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(shipment);
            session.getTransaction().commit();
            out.writeObject("done");
        }
    }

    private void handleGetDriverShipments(ObjectInputStream in, ObjectOutputStream out) throws Exception {
        String trn = (String) in.readObject();
        try (Session session = getSessionFactory().openSession()) {

            List<Shipment> shipments = session.createQuery(
                "SELECT s FROM Shipment s JOIN Assignment a ON a.packageNo = s.packageNo WHERE a.driverID = :trn",
                Shipment.class
            ).setParameter("trn", trn).getResultList();

            out.writeObject("success");
            out.writeObject(shipments);
        } catch (Exception e) {
            out.writeObject("error");
            logger.error("Error fetching shipments for driver {}: {}", trn, e.getMessage());
        }
    }

    private void handleUpdateShipmentStatus(ObjectInputStream in, ObjectOutputStream out) throws Exception {
        String pkgId = (String) in.readObject();
        String newStatus = (String) in.readObject();

        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            Shipment shipment = session.get(Shipment.class, pkgId);
            if (shipment != null) {
                shipment.setStatus(newStatus);
                session.merge(shipment);
                session.getTransaction().commit();
                out.writeObject("success");
            } else {
                out.writeObject("error - no such package");
            }
        } catch (Exception e) {
            out.writeObject("error");
            logger.error("Error updating shipment status: {}", e.getMessage());
        }
    }

    private void handleGetDriverRoute(ObjectInputStream in, ObjectOutputStream out) throws Exception {
        String trn = (String) in.readObject();
        try (Session session = getSessionFactory().openSession()) {

            Route route = session.createQuery(
                "SELECT r FROM Route r JOIN Trip t ON t.routeId = r.routeId WHERE t.driverTrn = :trn",
                Route.class
            ).setParameter("trn", trn).setMaxResults(1).uniqueResult();

            out.writeObject("success");
            out.writeObject(route);
        } catch (Exception e) {
            out.writeObject("error");
            logger.error("Error fetching route for driver {}: {}", trn, e.getMessage());
        }
    }

    private void handleGetDriverVehicle(ObjectInputStream in, ObjectOutputStream out) throws Exception {
        String trn = (String) in.readObject();
        try (Session session = getSessionFactory().openSession()) {

            Vehicle vehicle = session.createQuery(
                "SELECT v FROM Vehicle v JOIN Trip t ON t.vehicleNo = v.vehicleNo WHERE t.driverTrn = :trn",
                Vehicle.class
            ).setParameter("trn", trn).setMaxResults(1).uniqueResult();

            out.writeObject("success");
            out.writeObject(vehicle);
        } catch (Exception e) {
            out.writeObject("error");
            logger.error("Error fetching vehicle for driver {}: {}", trn, e.getMessage());
        }
    }

    // ======================================================
    // MAIN ENTRY POINT
    // ======================================================
    public static void main(String[] args) {
        new Server().waitForRequests();
    }
}


