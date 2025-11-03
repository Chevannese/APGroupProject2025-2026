package view;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import model.Customer;
import model.User;




public class MainWindow extends JFrame 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField firstNameField, lastNameField, emailField, trnField, passwordFieldR, contactNumField;
	private String passText;
	private boolean exception = false;
	private CardLayout cardLayout, loginCardLayout;
    private JPanel mainPanel, loginPanel, customerPanel, driverPanel, clerkPanel, managerPanel,
    custMainPanel;
	private GridBagConstraints gc;
	private User loggedInUser;
	private Customer newUser = new Customer();


    
    
    private void initializeSectionPanels()
    {
    	setTitle("Login System");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
		
    	cardLayout = new CardLayout();
    	loginCardLayout = new CardLayout();
    	
		mainPanel = new JPanel(cardLayout);
		
		loginPanel = new JPanel(cardLayout);

		customerPanel = new JPanel(cardLayout);
		driverPanel = new JPanel(cardLayout);
		clerkPanel = new JPanel(cardLayout);
		managerPanel = new JPanel(cardLayout);
    }
    
    
    
    private void initializeLoginComponents()
    {
    	 // Create the two cards
        JPanel signInPanel = new JPanel(new GridBagLayout());
        JPanel signUpPanel = new JPanel(new GridBagLayout());
    	custMainPanel = new JPanel(new GridBagLayout());

        loginPanel.add(signInPanel, "Login");
        loginPanel.add(signUpPanel, "Register");

    	customerPanel.add(custMainPanel, "CustomerIndex");
    	
        mainPanel.add(loginPanel, "LoginPage");
        mainPanel.add(customerPanel, "CustomerPage");

        add(mainPanel);
        cardLayout.show(mainPanel, "LoginPage"); // start with sign-in

        gc = new GridBagConstraints();
        gc.insets = new Insets(5, 5, 5, 5);
        gc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Sign In", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        final JTextField usernameFieldL = new JTextField(15);
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

        goToSignUpBtn.addActionListener(e -> cardLayout.show(loginPanel, "Register"));

        signInBtn.addActionListener(e -> {
            String user = usernameFieldL.getText();
            String pass = new String(passwordFieldL.getPassword());
            loggedInUser = new User();
            loggedInUser = loggedInUser.get(user, pass);
            // Dummy check
             
             JOptionPane.showMessageDialog(this, "Welcome " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
             
             //Call upon another method
             if(loggedInUser.getUserType().equals("Customer"))
             {
            	 initializeCustomerComponents();
             }
             else if(loggedInUser.getUserType().equals("Driver"))
             {
            	 cardLayout.show(driverPanel, "Main Menu");
             }
             else if(loggedInUser.getUserType().equals("Clerk"))
             {
            	 cardLayout.show(clerkPanel, "Main Menu");
             }
             else if(loggedInUser.getUserType().equals("Manager"))
             {
            	 cardLayout.show(managerPanel, "Main Menu");
             }
             
        });

        // sign up
        gc.insets = new Insets(5, 5, 5, 5);
        gc.fill = GridBagConstraints.HORIZONTAL;

        title = new JLabel("Sign Up", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        trnField = new JTextField(9);
        final JPasswordField passwordFieldR = new JPasswordField(15);
         firstNameField = new JTextField(20);
         lastNameField = new JTextField(20);
         contactNumField = new JTextField(20);
         emailField = new JTextField(20);
        
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

        goToSignInBtn.addActionListener(e -> cardLayout.show(loginPanel, "Login"));

        clearSignUpBtn.addActionListener(e ->{
        	firstNameField.setText(null);
        	lastNameField.setText(null);
        	trnField.setText(null);
        	emailField.setText(null);
        	contactNumField.setText(null);
        	passwordFieldR.setText(null);
        	
        });
        
        
        signUpBtn.addActionListener(e -> {
        	exception = false;
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
						JOptionPane.showMessageDialog(this, "Account created for: \nName: " + firstName + " " + lastName + "\nTRN: "
								+ trn + "\nContact Number: " + contactNum + "\nEmail: " + email
								+ "\nPassword: " + passText);
						
						newUser = new Customer(trn,firstName,lastName,passText,contactNum,email,"Customer");
						
						newUser.createAccount();
						
						clearSignUpBtn.doClick();
						
			            cardLayout.show(loginPanel, "Login");
					}
	        }
            
        });

    }
    
    private void initializeCustomerComponents()
    {
    	
    	
    	cardLayout.show(mainPanel, "CustomerPage");
    	
    	JButton accountBtn = new JButton("Account");
    	JButton requestOrderBtn = new JButton("Order");
    	JButton trackBtn = new JButton("Track Package");
    	JButton billBtn = new JButton("Manage Bill");
    	
    	 gc = new GridBagConstraints();
    	 gc.insets = new Insets(5, 5, 5, 5);
    	 gc.fill = GridBagConstraints.HORIZONTAL;
    	 
    	 JLabel title = new JLabel("Customer Main Menu",SwingConstants.CENTER);
    	 title.setFont(new Font("Arial", Font.BOLD, 20));
    	 
    	 JLabel welcome = new JLabel("Welcome back " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName() + "! How may we assist you today");
    	 addToGridBag(custMainPanel, title, gc, 0, 0, 2, 1);
    	 addToGridBag(custMainPanel, welcome, gc, 0, 1, 2, 1);
    	 
    	 addToGridBag(custMainPanel, accountBtn, gc, 0, 2, 1, 1);
    	 addToGridBag(custMainPanel, requestOrderBtn, gc, 0, 3, 1, 1);
    	 addToGridBag(custMainPanel, trackBtn, gc, 0, 4, 1, 1);
    	 addToGridBag(custMainPanel, billBtn, gc, 0, 5, 1, 1);
    	 
    	 
    	 
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

		boolean hasUppercase = false;
		boolean hasLowercase = false;
		boolean hasDigit = false;
		boolean hasSpecialChar = false;

		/*for (char c : passText.toCharArray()) {
			if (Character.isUpperCase(c)) {
				hasUppercase = true;
			} else if (Character.isLowerCase(c)) {
				hasLowercase = true;
			} else if (Character.isDigit(c)) {
				hasDigit = true;
			} else if (!Character.isLetterOrDigit(c)) {
				hasSpecialChar = true;
			}
		}

		if (!hasUppercase || !hasLowercase || !hasDigit || !hasSpecialChar) {
			System.err.println("\nSYSTEM: Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
			return false;
		}*/

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
			throw new RuntimeException(e);
		}
	}

    
    
    public MainWindow()
    {
    	initializeSectionPanels();
    	initializeLoginComponents();
    	WriteTestData();

		setVisible(true);

    	
    }
    
    private void WriteTestData()
    {
    	firstNameField.setText("Chevannese");
    	lastNameField.setText("Ellis");
    	trnField.setText("123456789");
    	emailField.setText("chev@gmail.com");
    	contactNumField.setText("876-249-3133");
    	
    }
    public static void main(String[] args)
    {
    	new MainWindow();
    }
    

}
