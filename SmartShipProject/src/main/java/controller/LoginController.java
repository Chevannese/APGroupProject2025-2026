package controller;


import javax.swing.JOptionPane;

import model.*;
import network.Server;
import view.*;
import org.apache.logging.log4j.*;
import org.hibernate.*;

public class LoginController {
	
	private static final Logger logger = LogManager.getLogger(LoginController.class);
	private Login window;
	
	public LoginController(Login window) {
		this.window = window;
	}
	
	public void login(String trn, String password) {
		
		
	    Session session = null;
		try
		{
			session = Server.getSessionFactory().openSession();
			
			 User loggedInUser = session.find(User.class, trn);
			 
			 if (loggedInUser == null) {
		            JOptionPane.showMessageDialog(null, "Username does not exist");
		            logger.warn("Login failed: TRN " + trn + " not found");
		            return;
		        }
			 
			// Compare passwords
		        if (!loggedInUser.getPassword().equals(password)) 
		        {
		            JOptionPane.showMessageDialog(null, "Incorrect password");
		            logger.warn("Login failed: Incorrect password for TRN " + trn);
		            return;
		        }

		    JOptionPane.showMessageDialog(null, "Welcome " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName());  
		    logger.info("User has successfully logged on with TRN: " + loggedInUser.getTrn());
			
		    window.dispose();
		    
			if(loggedInUser instanceof Customer)
	        {
				new CustomerView(trn);
	        }
	        else if(loggedInUser instanceof Driver)
	        {
	        	
	        }
	        else if(loggedInUser instanceof Clerk)
	        {
	        	
	        }
	        else if(loggedInUser instanceof Manager)
	        {
	        	
	        }
		}catch(Exception e)
		{
		    session.getTransaction().rollback();
			JOptionPane.showMessageDialog(null, "Please check the Username and password entered");
			logger.error(e.getMessage());
		}
		
	}

	public Login getWindow() {
		return window;
	}

	public void setWindow(Login window) {
		this.window = window;
	}
}
