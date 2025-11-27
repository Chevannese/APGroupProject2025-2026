package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.*;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import model.Invoice;
import model.Shipment;
import model.TrackPackage;
import model.User;
import model.Assignment;
import model.Route;
import model.Trip;
import model.Vehicle;

public class Server {
    private static final Logger logger = LogManager.getLogger(Server.class);
    private static SessionFactory sessionFactory = null;

    private ServerSocket serverSocket;
    private Socket connectionSocket;

    static {
        System.out.println("Before building SessionFactory...");
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
            System.out.println("SessionFactory built successfully!");
        } catch (Throwable ex) {
            System.out.println("Failed to build SessionFactory!");
            ex.printStackTrace();
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
                connectionSocket = serverSocket.accept();
                logger.info("Client connected: " + connectionSocket.getInetAddress());

                new Thread(() -> handleClient(connectionSocket)).start();
            }

        } catch (IOException e) {
            logger.error("Server error: " + e.getMessage(), e);
        }
    }

    private void handleClient(Socket socket) {
        try (
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream  in  = new ObjectInputStream(socket.getInputStream());
        ) {
            out.flush();
            logger.info("Streams configured for " + socket.getInetAddress());

            String action = (String) in.readObject();
            logger.info("Action received: " + action);

            // ========================
            // EXISTING ACTION HANDLERS
            // ========================

            if (action.equals("get-shipments")) {
                try {
                    List<Shipment> shipments = getShipments();
                    out.writeObject(shipments);
                    out.flush();
                } catch (Exception e) {
                    out.writeObject("error-database-issue");
                    out.flush();
                }
            }

            else if ("Create Account".equals(action)) {
                User user = (User) in.readObject();

                try (Session session = getSessionFactory().openSession()) {
                    session.beginTransaction();
                    User existingUser = session.find(User.class, user.getTrn());

                    if (existingUser != null) {
                        out.writeObject("error-duplicate-trn");
                    } else {
                        session.persist(user);
                        session.getTransaction().commit();
                        out.writeObject("done");
                    }
                    out.flush();
                } catch (Exception e) {
                    out.writeObject("error-database-issue");
                    out.flush();
                }
            }

            else if (action.equalsIgnoreCase("SignIn")) {
                User user = (User) in.readObject();

                try (Session session = getSessionFactory().openSession()) {
                    session.beginTransaction();

                    User existingUser = session.find(User.class, user.getTrn());

                    if (existingUser == null) {
                        out.writeObject("User-does-not-exist");
                        out.writeObject(null);
                    } else if (existingUser.getPassword().equals(user.getPassword())) {
                        out.writeObject("success");
                        out.writeObject(existingUser);
                    } else {
                        out.writeObject("The password that was entered by the user is incorrect");
                        out.writeObject(null);
                    }
                    out.flush();

                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }

            else if ("create-shipment".equals(action)) {
                Shipment shipment = (Shipment) in.readObject();

                try (Session session = getSessionFactory().openSession()) {
                    session.beginTransaction();
                    session.persist(shipment);
                    session.flush();
                    session.getTransaction().commit();

                    out.writeObject(shipment);
                } catch (Exception e) {
                    out.writeObject(null);
                }
                out.flush();
            }

            else if ("generate-invoice".equals(action)) {
                Shipment shipment = (Shipment) in.readObject();
                User customer = (User) in.readObject();
                in.readObject(); // staff from client (ignored, clerk is auto-assigned)
                String paymentMethod = (String) in.readObject();
                Invoice newInvoice = (Invoice) in.readObject();

                try (Session session = getSessionFactory().openSession()) {
                    session.beginTransaction();

                    shipment = session.find(Shipment.class, shipment.getPackageNo());
                    customer = session.find(User.class, customer.getTrn());
                    User staff = session.find(User.class, assignToClerk());

                    if ("Express".equals(shipment.getPackageType()))
                        newInvoice.setSurcharge(500);
                    else if ("Fragile".equals(shipment.getPackageType()))
                        newInvoice.setSurcharge(750);

                    newInvoice.setPackageNo(shipment.getPackageNo());
                    newInvoice.setCustNo(customer.getTrn());
                    newInvoice.setStaffNo(staff.getTrn());
                    newInvoice.setPaymentMethod(paymentMethod);
                    newInvoice.setPaymentStatus("Unpaid");
                    newInvoice.setRemainingCost(shipment.getCost());
                    newInvoice.setTotal(shipment.getCost());

                    session.persist(newInvoice);
                    session.getTransaction().commit();
                    out.writeObject("done");

                } catch (Exception e) {
                    out.writeObject("error");
                }
                out.flush();
            }

            else if ("generate-track".equals(action)) {
                Shipment shipment = (Shipment) in.readObject();
                TrackPackage newTrack = (TrackPackage) in.readObject();
                User customer = (User) in.readObject();

                try (Session session = getSessionFactory().openSession()) {
                    session.beginTransaction();

                    shipment = session.find(Shipment.class, shipment.getPackageNo());
                    customer = session.find(User.class, customer.getTrn());

                    newTrack.setPackageNo(shipment.getPackageNo());
                    newTrack.setTrackingNo(null);
                    newTrack.setCustNo(customer.getTrn());
                    newTrack.setDate(LocalDate.now());
                    newTrack.setTime(LocalTime.now());

                    session.persist(newTrack);
                    session.getTransaction().commit();

                    out.writeObject("done");

                } catch (Exception e) {
                    out.writeObject("error");
                }
                out.flush();
            }

            // ================================
            // DRIVER: GET SHIPMENTS
            // ================================

            else if ("GetDriverShipments".equals(action)) {
                String driverTrn = (String) in.readObject();

                try (Session session = getSessionFactory().openSession()) {
                    session.beginTransaction();

                    List<Shipment> shipments =
                        session.createQuery(
                                "select s from Shipment s, Assignment a " +
                                "where a.driverID = :trn and a.packageNo = s.packageNo",
                                Shipment.class)
                            .setParameter("trn", driverTrn)
                            .getResultList();

                    session.getTransaction().commit();

                    out.writeObject("success");
                    out.writeObject(shipments);

                } catch (Exception ex) {
                    out.writeObject("error");
                }
                out.flush();
            }

            // ================================
            // DRIVER: UPDATE SHIPMENT STATUS
            // ================================

            else if ("UpdateShipmentStatus".equals(action)) {
                String pkgIdStr = (String) in.readObject();
                String newStatus = (String) in.readObject();

                try (Session session = getSessionFactory().openSession()) {
                    session.beginTransaction();

                    Integer pkgId = Integer.valueOf(pkgIdStr);
                    Shipment shipment = session.find(Shipment.class, pkgId);

                    if (shipment == null) {
                        out.writeObject("not-found");
                    } else {
                        shipment.setStatus(newStatus);
                        session.merge(shipment);
                        session.getTransaction().commit();
                        out.writeObject("success");
                    }

                } catch (Exception ex) {
                    out.writeObject("error");
                }
                out.flush();
            }

            // ================================
            // DRIVER: GET ROUTE
            // ================================

            else if ("GetDriverRoute".equals(action)) {
                String driverTrn = (String) in.readObject();

                try (Session session = getSessionFactory().openSession()) {
                    session.beginTransaction();

                    Assignment assignment = session.createQuery(
                            "select a from Assignment a where a.driverID = :trn",
                            Assignment.class)
                        .setParameter("trn", driverTrn)
                        .setMaxResults(1)
                        .uniqueResult();

                    Route route = null;

                    if (assignment != null && assignment.getTripID() != null) {

                        Trip trip = session.find(Trip.class, assignment.getTripID());

                        if (trip != null && trip.getRouteID() != null) {
                            Integer routeId = Integer.valueOf(trip.getRouteID());
                            route = session.find(Route.class, routeId);
                        }
                    }

                    session.getTransaction().commit();

                    if (route != null) {
                        out.writeObject("success");
                        out.writeObject(route);
                    } else {
                        out.writeObject("not-found");
                    }

                } catch (Exception ex) {
                    out.writeObject("error");
                }
                out.flush();
            }

            // ================================
            // DRIVER: GET VEHICLE
            // ================================

            else if ("GetDriverVehicle".equals(action)) {
                String driverTrn = (String) in.readObject();

                try (Session session = getSessionFactory().openSession()) {
                    session.beginTransaction();

                    Assignment assignment = session.createQuery(
                            "select a from Assignment a where a.driverID = :trn",
                            Assignment.class)
                        .setParameter("trn", driverTrn)
                        .setMaxResults(1)
                        .uniqueResult();

                    Vehicle vehicle = null;

                    if (assignment != null && assignment.getVehicleNo() != null) {
                        vehicle = session.find(Vehicle.class, assignment.getVehicleNo());
                    }

                    session.getTransaction().commit();

                    if (vehicle != null) {
                        out.writeObject("success");
                        out.writeObject(vehicle);
                    } else {
                        out.writeObject("not-found");
                    }

                } catch (Exception ex) {
                    out.writeObject("error");
                }
                out.flush();
            }

            // ======================
            // GET / UPDATE INVOICES
            // ======================

            else if (action.equals("GET_INVOICES")) {
                User user = (User)in.readObject();
                out.flush();
                List<Invoice> list = getAllInvoices(user);
                out.writeObject(list);
                out.flush();
            }

            else if (action.equals("UPDATE_INVOICES")) {
                @SuppressWarnings("unchecked")
                List<Invoice> updatedList = (List<Invoice>) in.readObject();

                try (Session session = sessionFactory.openSession()) {
                    Transaction tx = session.beginTransaction();
                    for (Invoice inv : updatedList) {
                        session.merge(inv);
                    }
                    tx.commit();
                } catch (Exception e) {
                    out.writeObject("Update failed: " + e.getMessage());
                    out.flush();
                    throw e;
                }

                out.writeObject("Invoices updated successfully!");
                out.flush();
            }

            // ======================
            // GET / UPDATE USERS
            // ======================

            else if (action.equals("GET_USERS")) {
                try (Session session = sessionFactory.openSession()) {
                    List<User> users = session.createQuery("FROM User", User.class).list();
                    out.writeObject(users);
                    out.flush();
                } catch (Exception ex) {
                    out.writeObject(new ArrayList<User>());
                    out.flush();
                }
            }

            else if (action.equals("UPDATE_USERS")) {
                @SuppressWarnings("unchecked")
                List<User> updatedUsers = (List<User>) in.readObject();

                try (Session session = sessionFactory.openSession()) {
                    Transaction transaction = session.beginTransaction();
                    for (User u : updatedUsers) {
                        session.merge(u);
                    }
                    transaction.commit();
                    out.writeObject("Users updated successfully!");
                } catch (Exception ex) {
                    out.writeObject("Update failed: " + ex.getMessage());
                }
                out.flush();
            }

            // ======================
            // DISPATCH: UNASSIGNED SHIPMENTS
            // ======================

            else if ("GET_UNASSIGNED_SHIPMENTS".equals(action)) {
                try (Session session = getSessionFactory().openSession()) {
                    session.beginTransaction();

                    // Shipments with status 'Pending' and not already in Assignment
                    List<Shipment> shipments = session.createQuery(
                        "select s from Shipment s " +
                        "where s.status = 'Pending' " +
                        "and s.packageNo not in (select a.packageNo from Assignment a)",
                        Shipment.class
                    ).getResultList();

                    session.getTransaction().commit();

                    out.writeObject("success");
                    out.writeObject(shipments);
                } catch (Exception e) {
                    logger.error("Error GET_UNASSIGNED_SHIPMENTS", e);
                    out.writeObject("error");
                }
                out.flush();
            }

            // ======================
            // DISPATCH: ALL ROUTES
            // ======================

            else if ("GET_ROUTES".equals(action)) {
                try (Session session = getSessionFactory().openSession()) {
                    session.beginTransaction();

                    List<Route> routes = session.createQuery(
                        "from Route",
                        Route.class
                    ).getResultList();

                    session.getTransaction().commit();

                    out.writeObject("success");
                    out.writeObject(routes);
                } catch (Exception e) {
                    logger.error("Error GET_ROUTES", e);
                    out.writeObject("error");
                }
                out.flush();
            }

            // ======================
            // DISPATCH: ALL VEHICLES
            // ======================

            else if ("GET_VEHICLES".equals(action)) {
                try (Session session = getSessionFactory().openSession()) {
                    session.beginTransaction();

                    List<Vehicle> vehicles = session.createQuery(
                        "from Vehicle",
                        Vehicle.class
                    ).getResultList();

                    session.getTransaction().commit();

                    out.writeObject("success");
                    out.writeObject(vehicles);
                } catch (Exception e) {
                    logger.error("Error GET_VEHICLES", e);
                    out.writeObject("error");
                }
                out.flush();
            }

            // ======================
            // DISPATCH: ASSIGN SHIPMENT
            // One trip per vehicle per day (manager-only)
            // ======================

            else if (action.equals("ASSIGN_SHIPMENT")) 
            {
                Integer packageNo = (Integer) in.readObject();
                Integer routeID   = (Integer) in.readObject();
                String  vehicleNo = (String)  in.readObject();
                String  managerTrn = (String) in.readObject(); // manager's TRN (staffID)

                try (Session session = sessionFactory.openSession()) {

                    Transaction tx = session.beginTransaction();

                    // 1. Load required objects
                    Shipment shipment = session.find(Shipment.class, packageNo);
                    Vehicle vehicle   = session.find(Vehicle.class, vehicleNo);
                    Route route       = session.find(Route.class, routeID);

                    if (shipment == null) {
                        out.writeObject("ERROR: Shipment not found");
                        tx.commit();
                        out.flush();
                        return;
                    }
                    if (vehicle == null) {
                        out.writeObject("ERROR: Vehicle not found");
                        tx.commit();
                        out.flush();
                        return;
                    }
                    if (route == null) {
                        out.writeObject("ERROR: Route not found");
                        tx.commit();
                        out.flush();
                        return;
                    }

                    // 2. Check if shipment is already assigned
                    Long count = session.createQuery(
                            "select count(a) from Assignment a where a.packageNo = :pkg",
                            Long.class
                    ).setParameter("pkg", packageNo).uniqueResult();

                    if (count != null && count > 0) {
                        out.writeObject("ERROR: Shipment already assigned");
                        tx.commit();
                        out.flush();
                        return;
                    }

                    // 3. Check capacity limitations
                    double newWeight = vehicle.getCurrentWeight() + shipment.getWeight();
                    int newQty = vehicle.getCurrentQuantity() + 1;

                    if (newQty > vehicle.getQuantityCap()) {
                        out.writeObject("OVER_CAPACITY_QTY");
                        tx.commit();
                        out.flush();
                        return;
                    }

                    if (newWeight > vehicle.getWeightCap()) {
                        out.writeObject("OVER_CAPACITY_WEIGHT");
                        tx.commit();
                        out.flush();
                        return;
                    }

                    // 4. One trip per vehicle per day
                    LocalDate today = LocalDate.now();

                    Trip trip = session.createQuery(
                            "from Trip t where t.vehicleNo = :veh and t.date = :dt",
                            Trip.class)
                            .setParameter("veh", vehicleNo)
                            .setParameter("dt", today)
                            .setMaxResults(1)
                            .uniqueResult();

                    if (trip == null) {
                        // Create new Trip for this vehicle today
                        Integer maxId = session.createQuery(
                                "select coalesce(max(t.tripID), 0) from Trip t",
                                Integer.class
                        ).uniqueResult();
                        int newTripId = (maxId == null ? 1 : maxId + 1);

                        trip = new Trip();
                        trip.setVehicleNo(vehicleNo);
                        trip.setDriverID(vehicle.getDriverID());
                        trip.setClerkID(managerTrn); // using this to store manager who dispatched
                        trip.setRouteID(routeID);
                        trip.setStatus("Pending");
                        trip.setDate(today);
                        trip.setDepartureTime(LocalTime.now());
                        trip.setArrivalTime(null);

                        session.persist(trip);
                    } else {
                        // Vehicle already has a trip today – must be same route
                        if (trip.getRouteID() != null && !trip.getRouteID().equals(routeID)) {
                            out.writeObject("ERROR: Vehicle already assigned to different route today");
                            tx.commit();
                            out.flush();
                            return;
                        }
                    }

                    // 5. Update vehicle loading
                    vehicle.setCurrentQuantity(newQty);
                    vehicle.setCurrentWeight(newWeight);
                    session.merge(vehicle);

                    // 6. Create assignment
                    Assignment a = new Assignment();
                    a.setPackageNo(packageNo);
                    a.setVehicleNo(vehicleNo);
                    a.setDriverID(vehicle.getDriverID()); 
                    a.setCustID(shipment.getCustID());
                    a.setDate(today);
                    a.setTime(LocalTime.now());
                    a.setTripID(trip.getTripID());
                    a.setStaffID(managerTrn); // manager-only

                    session.persist(a);

                    // 7. Update shipment status to "Assigned"
                    shipment.setStatus("Assigned");
                    session.merge(shipment);

                    tx.commit();
                    out.writeObject("SUCCESS");
                    out.flush();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    out.writeObject("ERROR: " + ex.getMessage());
                    out.flush();
                }
            }else if (action.equals("GET_TRIPS")) {
                try (Session session = sessionFactory.openSession()) {
                    session.beginTransaction();

                    List<Trip> trips = session.createQuery(
                        "from Trip", Trip.class
                    ).getResultList();

                    session.getTransaction().commit();

                    out.writeObject("success");
                    out.writeObject(trips);
                    out.flush();

                } catch (Exception e) {
                    e.printStackTrace();
                    out.writeObject("error");
                    out.flush();
                }
            }


        } catch (Exception e) {
            logger.error("Client handling error: " + e.getMessage(), e);
        }
    }

    private List<Shipment> getShipments() throws HibernateException {
        List<Shipment> shipments = null;

        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            shipments = session
                    .createQuery("from Shipment", Shipment.class)
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return shipments;
    }

    private List<Invoice> getAllInvoices(User loggedInUser) {
        try (Session session = getSessionFactory().openSession()) {
            return session.createQuery(
                    "select a from Invoice a where a.custNo = :custNo", Invoice.class)
                .setParameter("custNo", loggedInUser.getTrn())
                .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return java.util.Collections.emptyList();
        }
    }

    private String assignToClerk() {
        Random clerk = new Random();
        return String.valueOf(clerk.nextInt(200000007 - 200000001 + 1) + 200000001);
    }

    public static void main(String[] args) {
        new Server().waitForRequests();
    }
}
