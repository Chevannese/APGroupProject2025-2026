package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.Invoice;
import model.Shipment;
import model.TrackPackage;
import model.User;

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

                // Handle each client in a new thread
                new Thread(() -> handleClient(connectionSocket)).start();
            }

        } catch (IOException e) {
            logger.error("Server error: " + e.getMessage(), e);
        }
    }

    private void handleClient(Socket socket) 
    {
        try (
        	ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ) {
            out.flush(); // send header
            logger.info("Streams configured for " + socket.getInetAddress());

            String action = (String) in.readObject();
            logger.info("Action received: " + action);

            
            if ("Create Account".equals(action)) 
            {
                User user = (User) in.readObject();

                try (Session session = getSessionFactory().openSession()) {
                    session.beginTransaction();

                    User existingUser = session.find(User.class, user.getTrn());
                    if (existingUser != null) {
                        logger.warn("Duplicate TRN attempt: " + user.getTrn());
                        out.writeObject("error-duplicate-trn");
                    } else {
                        session.persist(user);
                        session.getTransaction().commit();
                        logger.info("New user created with TRN: " + user.getTrn());
                        out.writeObject("done");
                    }
                    out.flush();
                } catch (Exception e) {
                    logger.error("Error - Database error: " + e.getMessage(), e);
                    out.writeObject("error-database-issue");
                    out.flush();
                }
            }//end of Create Account
            
            else if(action.equalsIgnoreCase("SignIn"))
            {
            	 User user = (User) in.readObject();

                 try (Session session = getSessionFactory().openSession()) {
                     session.beginTransaction();

                     User existingUser = session.find(User.class,  user.getTrn());
                     if(existingUser == null)
                     {
                    	 logger.warn("User-does-not-exist");
                    	 existingUser = null;
                    	 
                    	 out.writeObject("User-does-not-exist");
                    	 out.writeObject(existingUser);
                     }
                     else if (existingUser.getTrn().compareTo(user.getTrn()) == 0 && existingUser.getPassword().compareTo(user.getPassword()) == 0) {
                    	 
                    	 user.setContactNum(existingUser.getContactNum());
                    	 user.setEmail(existingUser.getEmail());
                    	 user.setFirstName(existingUser.getFirstName());
                    	 user.setLastName(existingUser.getLastName());
                    	 
                    	 out.writeObject("success");
                    	 out.writeObject(existingUser);
     		        }else if (existingUser.getTrn().compareTo(user.getTrn()) == 0 && !(existingUser.getPassword().compareTo(user.getPassword()) == 0))
     		        {
     		        	
     		        	logger.warn("The password that was entered by the user is incorrect: " + user.getTrn());
     		        	
     		        	out.writeObject("The password that was entered by the user is incorrect");
     		        	existingUser = null;
     		        	
     		        	
                        out.writeObject(existingUser);
     		        }
     		        else
     		        {
     		        	logger.warn("Unknown Error");
     		        	existingUser = null;
     		        	out.writeObject("Unknown Error");

     		        }
                     
                 }catch(NullPointerException ep)
                 {
                	 logger.error(ep.getMessage());
                 }catch(IOException io)
                 {
                	 logger.error(io.getMessage());
                 }catch(Exception e)
                 {
                	 logger.error(e.getMessage());
                 }
                 }
            else if ("create-shipment".equals(action)) 
            {
                Shipment shipment = (Shipment) in.readObject();

                try (Session session = getSessionFactory().openSession()) {
                    session.beginTransaction();

                    session.persist(shipment);   // ID auto-generated here
                    session.flush();             // Forces Hibernate to fetch ID NOW

                    session.getTransaction().commit();

                    // Send back the shipment with the generated ID
                    out.writeObject(shipment);
                    out.flush();

                    logger.info("Shipment saved with ID: " + shipment.getPackageNo());

                } catch (Exception e) {
                    logger.error("Error saving shipment:", e);
                    out.writeObject(null);
                    out.flush();
                }

            	
            }else if("Next".equalsIgnoreCase(action))
            {
            	out.writeObject("done");
            	out.flush();
            }
            else if ("generate-invoice".equals(action)) {

                Shipment shipment = (Shipment) in.readObject();
                User customer = (User) in.readObject();
                in.readObject(); // ignore staff sent by client
                String paymentMethod = (String) in.readObject();
                Invoice newInvoice = (Invoice) in.readObject();

                try (Session session = getSessionFactory().openSession()) {

                    session.beginTransaction();

                    shipment = session.find(Shipment.class, shipment.getPackageNo());
                    customer = session.find(User.class, customer.getTrn());

                    // Assign staff safely
                    User staff = session.find(User.class, assignToClerk());
                    if (staff == null)
                        throw new Exception("Generated clerk TRN does not exist");

                    if("Express".equals(shipment.getPackageType()))
                    {
                        newInvoice.setSurcharge(500);
                    }
                    else if("Fragile".equals(shipment.getPackageType()))
                    {
                    	newInvoice.setSurcharge(750);
                    }

                    newInvoice.setPackageNo(shipment.getPackageNo());
                    newInvoice.setInvoiceNo(null);
                    newInvoice.setCustNo(customer.getTrn());
                    newInvoice.setStaffNo(staff.getTrn());
                    newInvoice.setPaymentMethod(paymentMethod);
                    newInvoice.setPaymentStatus("Unpaid");
                    newInvoice.setDiscount(0);
                    newInvoice.setRemainingCost(shipment.getCost());
                    newInvoice.setTotal(shipment.getCost());

                    session.persist(newInvoice);
                    session.getTransaction().commit();

                    out.writeObject("done");
                    out.flush();
                    logger.info("Invoice created");

                } catch (Exception y) {

                    out.writeObject("error");
                    out.flush();
                    logger.error("Error generating invoice: ", y);
                }
            }
            else if ("generate-track".equals(action)) {

                Shipment shipment = (Shipment) in.readObject();
                TrackPackage newTrack = (TrackPackage)in.readObject();
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
                    out.flush();
                    logger.info("TrackPackage created");

                } catch (Exception y) {

                    out.writeObject("error");
                    out.flush();
                    logger.error("Error trackPackage invoice: ", y);
                }
            }

            	

        } catch (Exception e) {
            logger.error("Client handling error: " + e.getMessage(), e);
        }
    }
    

    private String assignToClerk()
	{
		Random clerk = new Random();
		 
		return String.valueOf(clerk.nextInt(200000007 - 200000001 + 1) + 200000001);
	}   
            
     		
      
    public static void main(String[] args) {
        new Server().waitForRequests();
    }
}
