package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.User;

public class Client 
{
	private static final Logger logger = LogManager.getLogger(Client.class);
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket connectionSocket;
	private String action = "";
	
	public Client()
	{
		this.createConnection();
		this.configureStreams();
	}
	
	private void createConnection()
	{
		try
		{
			connectionSocket = new Socket("127.0.0.1", 8888);
		}catch(IOException io)
		{
			logger.error(io.getMessage());
		}
	}
	
	private void configureStreams()
    {
    	try
    	{
    		//Create input stream to receive data from the server
    		
    		in = new ObjectInputStream(connectionSocket.getInputStream());
    		
    		//Create output stream to send data to the server
    		out = new ObjectOutputStream(connectionSocket.getOutputStream());
    		
    	}
    	catch(IOException io)
    	{
    		JOptionPane.showMessageDialog(null, "Could not connect to server - from input/output");

    	}catch(Exception e)
    	{
    		JOptionPane.showMessageDialog(null, "Could not connect to server");
    	}
    }
	
	public void closeConnection()
	{
		try
		{
			out.close();
			in.close();
			connectionSocket.close();
		}catch(IOException io)
		{
			logger.error(io.getMessage());
		}
	}
	
	public boolean createAccount(User newUser) {
	    try (
	        Socket socket = new Socket("127.0.0.1", 8888);
	        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
	        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
	    ) {
	        // Tell the server what action this is
	        out.writeObject("Create Account");
	        out.writeObject(newUser);
	        out.flush();

	        // Read the server's response (a String)
	        String response = ((String) in.readObject()).trim();

	        if (response.equals("error-duplicate-trn")) {
	            logger.warn(response);
	            JOptionPane.showMessageDialog(null, response, "Duplicate TRN", JOptionPane.WARNING_MESSAGE);
	            return false;
	        }
	        else if (response.equals("error-database-issue"))
	        {
	        	logger.error(response);
	            JOptionPane.showMessageDialog(null, response, "Database error", JOptionPane.ERROR_MESSAGE);
	            return false;
	        }		
	        else if ("done".equals(response)) 
	        {
	            logger.info("Account created successfully");
	            JOptionPane.showMessageDialog(null, "Account created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
	            return true;
	        } else {
	            JOptionPane.showMessageDialog(null, "Unexpected response from server: " + response);
	            return false;
	        }

	    } catch (Exception e) {
	        logger.error("Error communicating with server: " + e.getMessage(), e);
	        JOptionPane.showMessageDialog(null, "Connection error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
		return false;
	}
	
	public User signIn(User loggedInUser)
	{
		try (
		        Socket socket = new Socket("127.0.0.1", 8888);
		        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		    ) 
		{
			// Tell the server what action this is
	        out.writeObject("SignIn");
	        out.writeObject(loggedInUser);
	        out.flush();
	        
	        
	        String response = ((String) in.readObject()).trim();
	        User test = ((User) in.readObject());
	        if(response.equalsIgnoreCase("User-does-not-exist"))
	        {
	        	JOptionPane.showMessageDialog(null,response,"User does not exist", JOptionPane.WARNING_MESSAGE);
	        	
	        	return test;
	        }else if(response.equalsIgnoreCase("success"))
	        {
	        	JOptionPane.showMessageDialog(null,response,"User has successfully logged on with TRN: " + test.getTrn(), JOptionPane.WARNING_MESSAGE);
	        	return test;
	        }else if(response.equalsIgnoreCase("Username and password does not match"))
	        {
	        	JOptionPane.showMessageDialog(null,response,"The password that was entered by the user is incorrect", JOptionPane.WARNING_MESSAGE);
	        	return test;
	        }else if(response.equalsIgnoreCase("Unknown Error"))
	        {
	        	JOptionPane.showMessageDialog(null,response,"Unknown Error", JOptionPane.WARNING_MESSAGE);
	        	return test;
	        }
	        out.flush();
	        
		}
		catch (Exception e) {
	        logger.error("Error communicating with server: " + e.getMessage(), e);
	        JOptionPane.showMessageDialog(null, "Connection error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
		return null;
		
	}

}
