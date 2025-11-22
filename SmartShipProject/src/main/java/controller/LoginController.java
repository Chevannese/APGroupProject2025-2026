package controller;


import javax.swing.JOptionPane;

import model.*;
import view.*;
import org.apache.logging.log4j.*;

public class LoginController {
	
	private static final Logger logger = LogManager.getLogger(LoginController.class);
	private Login window;
	
	public LoginController(Login window) {
		this.window = window;
	}
	
	public void login(User loggedInUser) {
	   
			try
			{
				
		    window.dispose();
		    
			if(loggedInUser instanceof Customer)
	        {
				new CustomerView(loggedInUser);
	        }
	        else if(loggedInUser instanceof Driver)
	        {
	        	new DriverView(loggedInUser);
	        }
	        else if(loggedInUser instanceof Clerk)
	        {
	        	
	        }
	        else if(loggedInUser instanceof Manager)
	        {
	        	
	        }
		}catch(Exception e)
		{
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
