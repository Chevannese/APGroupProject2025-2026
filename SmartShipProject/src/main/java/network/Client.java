package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.LoginController;
import model.Shipment;
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
	
	public String receiveResponse() throws ClassNotFoundException, IOException
	{
		return (String) in.readObject();
		/*boolean flag = false;
		try
		{
			if(action.equalsIgnoreCase("Create Account"))
			{
				try
				{
					 flag = (boolean) in.readObject();
				}catch(Exception e)
				{
					logger.error(e.getMessage());
				}
				
				if(flag == true)
				{
					logger.info("Client Record Added Successfully");
				}
			}
			if(action.equalsIgnoreCase("SignIn"))
			{
				User user = new User();
				user = (User) in.readObject();
				if(user == null)
				{
					logger.info("User could not be found for Client");
					return;
				}
						
			}
			if(action.equalsIgnoreCase("Login"));
			{
				
			}
			if(action.equalsIgnoreCase("Logout"));
			{
				
			}
			if(action.equalsIgnoreCase("SignUp"))
			{
				
			}
		}catch(ClassCastException ex)
		{
			logger.error(ex.getMessage());
		}catch(ClassNotFoundException ex)
		{
			logger.error(ex.getMessage());
		}catch(IOException io)
		{
			logger.error(io.getMessage());
		}*/
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
	        String response = (String) in.readObject();

	        if (response.startsWith("Error:")) {
	            logger.warn(response);
	            JOptionPane.showMessageDialog(null, response, "Duplicate TRN", JOptionPane.WARNING_MESSAGE);
	            return false;
	        } else if ("done".equals(response)) {
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

	
	
	public void sendAction(String action)
	{
		this.action = action;
		try
		{
			out.writeObject(action);
		}catch(IOException io)
		{
			logger.error(io.getMessage());
		}catch(Exception e)
		{
			logger.error(e.getMessage());
		}
	}
	
	public void sendUser(User uObj)
	{
		try
		{
			out.writeObject(uObj);
		}catch(IOException io)
		{
			logger.error(io.getMessage());
		}catch(Exception e)
		{
			logger.error(e.getMessage());
		}
	}
	
	public void sendUserTrn(String trn)
	{
		try
		{
			out.writeObject(trn);
		}catch(IOException io)
		{
			logger.error(io.getMessage());
		}
	}
	
	public void sendLoginData(String trn, String passwordHash) {
	    try {
	        out.writeObject(trn);
	        out.writeObject(passwordHash);
	        out.flush(); // optional but recommended
	        logger.info("Sent TRN and password hash to server.");
	    } catch (IOException io) {
	        logger.error("Error sending login data: " + io.getMessage());
	    }
	}
	
	public void sendShipment(Shipment obj)
	{
		try
		{
			out.writeObject(obj);
		}catch(IOException io)
		{
			logger.error(io.getMessage());
		}
	}
	

}
