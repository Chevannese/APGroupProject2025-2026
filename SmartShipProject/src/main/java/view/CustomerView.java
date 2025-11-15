package view;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.User;

public class CustomerView extends JFrame 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(CustomerView.class);
	private GridBagConstraints gc;
	private JPanel customerPanel, menu, orderPage1,orderPage2,orderPage3;
	private CardLayout cardLayout;
	private JMenu account, nav;
	private JMenuItem track,order,bill,home,logout,info;
	
	public CustomerView(User loggedInUser)
    {
		
			
			setTitle("Customer System");
			setSize(1000,600);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	    
	     cardLayout = new CardLayout();
	    
	     customerPanel = new JPanel(cardLayout);
	    
	    
	    	
    	 menu = new JPanel(new GridBagLayout());
		 orderPage1 = new JPanel(new GridBagLayout());
		 orderPage2 = new JPanel(new GridBagLayout());
		 orderPage3 = new JPanel(new GridBagLayout());
		
		customerPanel.add(menu, "Menu");
		customerPanel.add(orderPage1,"ShipmentForm1");
		customerPanel.add(orderPage2,"ShipmentForm2");
		customerPanel.add(orderPage3, "ShipmentForm3");
		    	   
		add(customerPanel);
    	cardLayout.show(customerPanel, "Menu");
    	
    	
    	JMenuBar menuBar = new JMenuBar();
    	this.setJMenuBar(menuBar);
    	
    	 nav = new JMenu("Navigate");
    	 account = new JMenu("Account");
    	
    	 logout = new JMenuItem("Logout");
    	info = new JMenuItem("View Info");
    	
    	home = new JMenuItem("Home");
    	order = new JMenuItem("Order");
    	track = new JMenuItem("Track Package");
    	bill = new JMenuItem("Manage Bill");
    	
    	menuBar.add(nav);
    	menuBar.add(account);

    	nav.add(home);
    	nav.add(order);
    	nav.add(track);
    	nav.add(bill);
    	
    	account.add(logout);
    	account.add(info);
    	
    	 gc = new GridBagConstraints();
    	 gc.insets = new Insets(5, 5, 5, 5);
    	 gc.fill = GridBagConstraints.HORIZONTAL;
    	 
    	 JLabel title = new JLabel("Customer Main Menu",SwingConstants.CENTER);
    	 title.setFont(new Font("Arial", Font.BOLD, 20));
    	 
    	 JLabel welcome = new JLabel("Welcome back " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName() + "! How may we assist you today");
    	 addToGridBag(menu, title, gc, 0, 0, 2, 1);
    	 addToGridBag(menu, welcome, gc, 0, 1, 2, 1);
    	 
  
    	order.addActionListener(e ->{
     		 	
 				cardLayout.show(customerPanel, "ShipmentForm1");
          });
    	
    	home.addActionListener(e ->{
 		 	
				cardLayout.show(customerPanel, "Menu");
      });
    	
    	logout.addActionListener(e ->{
 		 	
    		this.dispose();
    		new Login();
    	});
    	
   
    	JLabel lblSenderSection = new JLabel("Sender Information Section", SwingConstants.CENTER);
 		lblSenderSection.setFont(new Font("Verdana", Font.BOLD, 20));
 		JLabel lblSenderName = new JLabel("Sender Name: ");
 		JTextField senderNameTxt = new JTextField(30);
 		JLabel lblSenderAddr = new JLabel("Sender Address: ");
 		JTextField senderAddrTxt = new JTextField(50);
 		
 		JLabel lblReceiverSection = new JLabel("Receiver Information Section",SwingConstants.CENTER);
 		JLabel lblReceiverAddr = new JLabel("Receiver Address: ");
 		JTextField receiverAddrTxt = new JTextField(50);
 		
 		JLabel lblshippingLocation = new JLabel("Shipping Location:");
 		JRadioButton rdbLocal = new JRadioButton("Local");
 		JRadioButton rdbInternational = new JRadioButton("International");
 		ButtonGroup btnGrpLocation = new ButtonGroup();
 		btnGrpLocation.add(rdbLocal);
 		btnGrpLocation.add(rdbInternational);
 		
 		
 		JLabel lblZone = new JLabel("Zone: ");
 		JButton zoneBtn = new JButton("Generate Zone");
 		JTextField zoneTxt = new JTextField(5);
 		zoneTxt.setEnabled(false);
 		
 		GridBagConstraints gc2 = new GridBagConstraints();
 		gc2 = new GridBagConstraints();
 		gc2.insets = new Insets(5, 5, 5, 5);
        gc2.fill = GridBagConstraints.HORIZONTAL;
 		
        addToGridBag(orderPage1, lblSenderSection, gc2, 0, 0, 2, 1);
        addToGridBag(orderPage1, lblSenderName, gc2, 0, 1, 1, 1);
        addToGridBag(orderPage1, senderNameTxt, gc2, 1, 1, 1, 1);
        addToGridBag(orderPage1,lblSenderAddr,gc2,0,2,1,1);
		addToGridBag(orderPage1,senderAddrTxt,gc2,1,2,2,1);

		
 		
    	 
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
   			logger.error("Component is null!");
   	        return;
   		}
   			
   	
   	
   	        gbc.gridx = column;
   	        gbc.gridy = row;
   	
   	        gbc.gridwidth = colspan;
   	        gbc.gridheight = rowspan;
   	        
   	        
   	        panel.add(component,gbc);
       }
	
	public static void main(String[] args)
	{
		new CustomerView(new User("123", "BOB", "Brown", "cat123","876-911-9111","bob.brown@gmail.com"));
	}
      
}
