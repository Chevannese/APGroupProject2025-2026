package view;

import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.LoginController;

import model.*;
import network.Client;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Login extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(Login.class);
    private String passText;
    private LoginController controller;

    public Login(LoginController loginController) {
    	this.controller = loginController;
    	setTitle("Login System");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
		
    	CardLayout cardLayout = new CardLayout();
		JPanel mainPanel = new JPanel(cardLayout);
		JPanel loginPanel = new JPanel(cardLayout);
    	
		
    	 // Create the two cards
        JPanel signInPanel = new JPanel(new GridBagLayout());
        JPanel signUpPanel = new JPanel(new GridBagLayout());
        
        loginPanel.add(signInPanel, "Login");
        loginPanel.add(signUpPanel, "Register");
        

        add(loginPanel);
        cardLayout.show(loginPanel, "Login"); // start with sign-in
        
    	GridBagConstraints gc;
    	gc = new GridBagConstraints();
        gc.insets = new Insets(5, 5, 5, 5);
        gc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Sign In", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JTextFieldLimit usernameFieldL = new JTextFieldLimit(9);
        usernameFieldL.setLimit(9);
        final JPasswordField passwordFieldL = new JPasswordField(15);
        JButton signInBtn = new JButton("Sign In");
        JButton goToSignUpBtn = new JButton("Create Account");
        JButton clearSignUpBtn = new JButton("Clear Form");

        addToGridBag(signInPanel, title, gc, 0, 0, 2, 1);
        addToGridBag(signInPanel, new JLabel("Username:"), gc, 0, 1, 1, 1);
        addToGridBag(signInPanel, usernameFieldL, gc, 1, 1, 1, 1);


        addToGridBag(signInPanel, new JLabel("Password:"),gc, 0, 2, 1, 1);
        addToGridBag(signInPanel, passwordFieldL, gc, 1, 2, 1, 1);

        addToGridBag(signInPanel, signInBtn, gc, 0, 3, 2, 1);
        addToGridBag(signInPanel, goToSignUpBtn,gc, 0, 4, 2, 1);


        signInBtn.addActionListener(e -> {
            String user = usernameFieldL.getText();
            String pass = new String(passwordFieldL.getPassword());
            String hashPass = hashString(pass);

            User loggedInUser = new User();
            loggedInUser.setTrn(user);
            loggedInUser.setPassword(hashPass);
            
            Client client = new Client();
            loggedInUser = client.signIn(loggedInUser);
            
            if(loggedInUser == null)
            {
            	return;
            }
            
            controller.login(loggedInUser);
			
             
        });

        // sign up
        gc.insets = new Insets(5, 5, 5, 5);
        gc.fill = GridBagConstraints.HORIZONTAL;

        title = new JLabel("Sign Up", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JTextFieldLimit trnField = new JTextFieldLimit(9);
        trnField.setLimit(9);
        final JPasswordField passwordFieldR = new JPasswordField(15);
        JTextField firstNameField = new JTextField(20);
        JTextField lastNameField = new JTextField(20);
        JTextField contactNumField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        
        JButton signUpBtn = new JButton("Register");
        JButton goToSignInBtn = new JButton("Back to Sign In");

        addToGridBag(signUpPanel, title, gc, 0, 0, 2, 1);
        
        addToGridBag(signUpPanel, new JLabel("First Name:"), gc, 0, 1, 1, 1);
        addToGridBag(signUpPanel, firstNameField, gc, 1, 1, 1, 1);

        addToGridBag(signUpPanel, new JLabel("Last Name:"),gc, 0, 2, 1, 1);
        addToGridBag(signUpPanel, lastNameField,gc, 1, 2, 1, 1);
        
        addToGridBag(signUpPanel, new JLabel("TRN:"),gc, 0, 3, 1, 1);
        addToGridBag(signUpPanel, trnField,gc, 1, 3, 1, 1);

        addToGridBag(signUpPanel, new JLabel("Contact Number: "),gc, 0, 4, 1, 1);
        addToGridBag(signUpPanel, contactNumField,gc, 1, 4, 1, 1);

        addToGridBag(signUpPanel, new JLabel("Email: "),gc, 0, 5, 1, 1);
        addToGridBag(signUpPanel, emailField,gc, 1, 5, 1, 1);
        
        addToGridBag(signUpPanel, new JLabel("Password:"),gc, 0, 6, 1, 1);
        addToGridBag(signUpPanel, passwordFieldR,gc, 1, 6, 1, 1);
        
        addToGridBag(signUpPanel, signUpBtn,gc, 0, 7, 2, 1);

        addToGridBag(signUpPanel, goToSignInBtn,gc, 0, 8, 2, 1);
        addToGridBag(signUpPanel, clearSignUpBtn,gc, 0, 9, 2, 1);

        
        // add action listeners
        goToSignInBtn.addActionListener(e -> cardLayout.show(loginPanel, "Login"));
        goToSignUpBtn.addActionListener(e -> cardLayout.show(loginPanel, "Register"));

        clearSignUpBtn.addActionListener(e ->{
        	firstNameField.setText("");
        	lastNameField.setText("");
        	trnField.setText("");
        	emailField.setText("");
        	contactNumField.setText("");
        	passwordFieldR.setText("");
        	
        });
        
        
        signUpBtn.addActionListener(e -> {
        	boolean exception = false;
        	if(firstNameField.getText().compareTo("") == 0 || lastNameField.getText().compareTo("") == 0 ||
        		trnField.getText().compareTo("") == 0 || contactNumField.getText().compareTo("") == 0 ||
        		emailField.getText().compareTo("") == 0)
	        {
	        	JOptionPane.showMessageDialog( 
		        		SwingUtilities.getWindowAncestor(mainPanel),
		        		 "One or two fields were not filled");
	        }
	        else
	        {
	        	String firstName = firstNameField.getText();
	        	String lastName = lastNameField.getText();
	        	String trn = trnField.getText();
	        	String contactNum = contactNumField.getText();
	        	String email = emailField.getText();
	        	 passText = new String(passwordFieldR.getPassword());
	        	if(trn.length() != trnField.getColumns())
				{
					JOptionPane.showMessageDialog( 
			        		this,
			        		 "TRN must be exactly 9 digits in the format: 123456789");
					return;
				}
	        	else if(!isValidEmail(email))
	        	{
	        	    JOptionPane.showMessageDialog(this, "Invalid email address!");
	        	    return;
	        	}
	        	else if(!isValidPhone(contactNum))
	        	{
	        		JOptionPane.showMessageDialog(this, "Invalid phone number!");
	        		return;
	        	}
	        	else if(checkPasswordStrength() == false)
	        	{
	        		return;
	        	}
	        	else
					
					try
					{
						//Temporary assigns temp to the converted number of TextField from receiverTRNTxt
						//to check that all values are number
						int temp = Integer.parseInt(trnField.getText());

					}catch(NumberFormatException nf)
					{
						JOptionPane.showMessageDialog(this,"Invalid Format for TRN field\n"
								+ "Must be in format: 123456789");
						
						exception = true;
					}
	        		

					if(exception == false)
					{

						passText = hashString(passText);
						
						
						
						
						Client client = new Client();
						User newUser = new Customer(trn, firstName, lastName, passText, contactNum, email);
						
						boolean success = client.createAccount(newUser);

						if (!success) 
						{
							return;
						}
						
							JOptionPane.showMessageDialog(this, "Account created for: \nName: " + firstName + " " + lastName + "\nTRN: "
									+ trn + "\nContact Number: " + contactNum + "\nEmail: " + email
									+ "\nPassword: " + passText);
						    clearSignUpBtn.doClick();                       // clear form
						    cardLayout.show(loginPanel, "Login");           // go to login page
						client.closeConnection();
					}
	        }
            
        });
        
        this.setVisible(true);
    }
    
	public void addToGridBag(JPanel panel, Component component, GridBagConstraints gbc, int column, int row,  int colspan, int rowspan)
   	{

   		if (panel == null ) {
   		        System.err.println("Panel is null!");
   		        return;
   		    }
   		
   		if(component == null)
   		{
   			System.err.println("Component is null!");
   	        return;
   		}
   			
   	
   	        gbc.gridx = column;
   	        gbc.gridy = row;
   	
   	        gbc.gridwidth = colspan;
   	        gbc.gridheight = rowspan;
   	        
   	        
   	        panel.add(component,gbc);
       }
       
       public boolean isValidEmail(String email) {
           String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
           return email.matches(regex);
       }
       
       public boolean isValidPhone(String phone) {
           return phone.matches("^\\+?[0-9\\-\\s]{7,20}$");
       }

       
       private boolean checkPasswordStrength()
       {
       	if (passText.length() < 8) {
       		JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long.");
   			return false;
   		} else if (passText.length() > 30) {
   			JOptionPane.showMessageDialog(this,"Password must be at most 30 characters long.");
   			return false;
   		}

   		
   		return true;
       }
       
       private String hashString(String input) {
   		try {
   			MessageDigest digest = MessageDigest.getInstance("SHA-256");
   			byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
   			StringBuilder hexString = new StringBuilder();
   			for (byte b : hash) {
   				String hex = Integer.toHexString(0xff & b);
   				if (hex.length() == 1) hexString.append('0');
   				hexString.append(hex);
   			}
   			return hexString.toString();
   		} catch (NoSuchAlgorithmException e) {
   			logger.error(e.getMessage());
   			throw new RuntimeException(e);
   		}
   	}
       
       
       public LoginController getController() {
		return controller;
	}


	public void setController(LoginController controller) {
		this.controller = controller;
	}

	public Login()
	{
	
           Login loginView = new Login(null);
    	   LoginController controller = new LoginController(loginView);
    	   loginView.setController(controller);
       }
	

	public static void main(String[] args)
       {
		new Login();
       }
		
}
