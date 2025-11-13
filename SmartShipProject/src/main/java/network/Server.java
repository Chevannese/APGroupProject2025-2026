package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.User;

public class Server {
    private static final Logger logger = LogManager.getLogger(Server.class);
    private static SessionFactory sessionFactory = null;

    private ServerSocket serverSocket;
    private Socket connectionSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

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

    private void handleClient(Socket socket) {
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

                     User existingUser = session.find(User.class, user.getTrn());
                     if(existingUser == null)
                     {
                    	 logger.warn("User does not exist");
                    	 out.writeObject("User-not-exist-error");
                     }
                     else if (existingUser.getTrn().compareTo(user.getTrn()) == 0 && existingUser.getPassword().compareTo(user.getPassword()) == 0) {
                    	 
                    	 user.setContactNum(existingUser.getContactNum());
                    	 user.setEmail(existingUser.getEmail());
                    	 user.setFirstName(existingUser.getFirstName());
                    	 user.setLastName(existingUser.getLastName());
                    	 out.writeObject("User-Exist");
     		        }else if (existingUser.getTrn().compareTo(user.getTrn()) == 0 && !(existingUser.getPassword().compareTo(user.getPassword()) == 0))
     		        {
     		        	logger.warn("Username and password does not match" + user.getTrn());
                        out.writeObject("user-not-match-error");
     		        }else if(!(existingUser.getTrn().compareTo(user.getTrn()) == 0) && !(existingUser.getPassword().compareTo(user.getPassword()) == 0))
     		        {
     		        	logger.warn("Username and password does not exist" + user.getTrn());
     		        	out.writeObject(false);

     		        }else
     		        {
     		        	logger.warn("Error");

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

        } catch (Exception e) {
            logger.error("Client handling error: " + e.getMessage(), e);
        }
    }
    

            
            
     		
      
    public static void main(String[] args) {
        new Server().waitForRequests();
    }
}
