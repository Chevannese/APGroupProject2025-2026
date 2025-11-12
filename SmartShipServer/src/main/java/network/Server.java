package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.Database;
import model.*;

public class Server 
{
	private ServerSocket serverSocket = null;
    private Socket socket = null;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
	private static SessionFactory sessionFactory = null;
	

	
    
    static {
        try {
            // Build once when class is loaded
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            //logger.error("Initial SessionFactory creation failed." + ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public  void shutdown() {
        getSessionFactory().close();
        try {
			in.close();
			out.close();
			 socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public Server() {
        try {
            // Create a new instance of ServerSocket
            serverSocket = new ServerSocket(8888);
            System.out.println("Server started. Waiting for client...");
            socket = serverSocket.accept();
            System.out.println("Client connected!");

            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void configureStreams()
    {
    	try
    	{
    		in = 
    		
    	}catch(IOException io)
    	{
    		io.printStackTrace();
    	}
    }
    
    public void createAccount(User newUser) 
	{
		 Session session = null;
	        try {
	        	session = Database.getSessionFactory().openSession();
	            session.beginTransaction();
	            session.persist(newUser);
	            session.getTransaction().commit();
	            logger.info("Account created with trn:" +newUser.getTrn());
	            
		        }catch (Exception e) 
		        {
		        	if (session != null)
		        	{
		        		session.getTransaction().rollback();
		        	}
		            logger.error("Error creating account: " + e.getMessage());
		        
		        }finally 
		        {
		            if (session != null) session.close(); // ensure session always closes
		        }
	}
    
    private void waitForRequests()
    {
    	String action = "";
    	Server.getSessionFactory().openSession();
    	User user = null;
    	try
    	{
    		while(true)
    		{
    			socket = serverSocket.accept();
    			this.configureStreams();
    		}
    	}
    }

}
