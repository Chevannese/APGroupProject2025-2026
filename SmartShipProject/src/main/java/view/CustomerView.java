package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.Shipment;
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
	
	private final double weightCap =  2600, lengthCap = 250, widthCap = 100,heightCap = 115;
	private double currentWeight = 0;
	private final int quantityCap = 20;
	private int currentQuantity = 0; 
	private int distance;
    private boolean zoneChance = true;
    private String senderName, senderAddr, receiverName, receiverAddr, shippingLocation;
	private ArrayList<Shipment> shipments;


	
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
 		lblSenderSection.setFont(new Font("Arial", Font.BOLD, 20));
 		JLabel lblSenderName = new JLabel("Sender Name: ");
 		
 		JTextField senderNameTxt = new JTextField(30);
 		JLabel lblSenderAddr = new JLabel("Shipping From: ");
 		JTextField senderAddrTxt = new JTextField(50);
 		
 		JLabel lblReceiverSection = new JLabel("Receiver Information Section",SwingConstants.CENTER);
 		lblReceiverSection.setFont(new Font("Arial", Font.BOLD, 20));
 		JLabel lblReceiverAddr = new JLabel("Shipping To: ");
 		JTextField receiverAddrTxt = new JTextField(50);
 		JLabel lblReceiverName = new JLabel("Receiver Name:");
		JTextField receiverNameTxt = new JTextField(30);
		
 		JLabel lblshippingLocation = new JLabel("Shipping Zone:");
 		JRadioButton rdbLocal = new JRadioButton("Local");
 		rdbLocal.setActionCommand("Local");
 		JRadioButton rdbInternational = new JRadioButton("International");
 		rdbInternational.setActionCommand("International");
 		ButtonGroup btnGrpLocation = new ButtonGroup();
 		btnGrpLocation.add(rdbLocal);
 		btnGrpLocation.add(rdbInternational);
 		
 		
 		JLabel lblZone = new JLabel("Zone #: ");
 		JButton zoneBtn = new JButton("Generate Zone");
 		JTextField zoneTxt = new JTextField(5);
 		zoneTxt.setEnabled(false);
 		
 		JButton goToOrderPage2Btn = new JButton("Next");
 		JButton clearOrderPage1Btn = new JButton("Clear");
 		
 		JLabel lblPackageSection = new JLabel("Package Information Section", SwingConstants.CENTER);
		lblPackageSection.setFont(new Font("Arial", Font.BOLD,20));
		
		JLabel lblPackageType = new JLabel("Package Type: ");
		
		
		JRadioButton rdbStandard = new JRadioButton("Standard");
		rdbStandard.setActionCommand("Standard");

		JRadioButton rdbExpress = new JRadioButton("Express");
		rdbExpress.setActionCommand("Express");
		
		JRadioButton rdbFragile = new JRadioButton("Fragile");
		rdbFragile.setActionCommand("Fragile");
		
		ButtonGroup btnGrpPackages = new ButtonGroup();
		btnGrpPackages.add(rdbStandard);
		btnGrpPackages.add(rdbExpress);
		btnGrpPackages.add(rdbFragile);
		
		JLabel lblPackageName = new JLabel("Package Name: ");
		JTextField packageNameTxt = new JTextField(30);
		JLabel lblWeight = new JLabel("Package Weight:");
		JTextFieldLimit weightTxt = new JTextFieldLimit(3);
		weightTxt.setLimit(6);
		
		JLabel lblPounds = new JLabel("Pounds");
		JLabel lblDimensions = new JLabel("Package Dimensions:");
		JTextFieldLimit lengthTxt = new JTextFieldLimit(3);
		lengthTxt.setLimit(5);
		JTextFieldLimit widthTxt = new JTextFieldLimit(3);
		widthTxt.setLimit(5);
		JTextFieldLimit heightTxt = new JTextFieldLimit(3);
		heightTxt.setLimit(5);
		JLabel lblLength = new JLabel("Length");
		JLabel lblWidth = new JLabel("Width");
		JLabel lblHeight = new JLabel("Height");
		JLabel inchMessage = new JLabel("*Length, Width, and Height are measured in inches");
		inchMessage.setForeground(Color.red);
		inchMessage.setFont((new Font("Arial", Font.ITALIC, 15)));
		
		JLabel lblCartOpt = new JLabel("Cart Options:");
		JButton addToCartBtn = new JButton("Add to Cart");
		JButton checkCartBtn = new JButton("Check Cart");
		JButton clearCartBtn = new JButton("Clear Cart");
		JButton delPackageBtn = new JButton("Delete Package");
		
		shipments = new ArrayList<Shipment>();
 		
 		
 		GridBagConstraints gc2 = new GridBagConstraints();
 		gc2 = new GridBagConstraints();
 		gc2.insets = new Insets(10, 10, 10, 10);
        gc2.fill = GridBagConstraints.HORIZONTAL;
 		
        addToGridBag(orderPage1, lblSenderSection, gc2, 0, 0, 3, 1);
        addToGridBag(orderPage1, lblSenderName, gc2, 0, 1, 1, 1);
        addToGridBag(orderPage1, senderNameTxt, gc2, 1, 1, 1, 1);
        addToGridBag(orderPage1,lblSenderAddr,gc2,0,2,1,1);
		addToGridBag(orderPage1,senderAddrTxt,gc2,1,2,1,1);
		
		addToGridBag(orderPage1,lblReceiverSection,gc2,0,3,3,1);
		addToGridBag(orderPage1,lblReceiverName,gc2,0,4,1,1);
		addToGridBag(orderPage1,receiverNameTxt,gc2,1,4,1,1);
		addToGridBag(orderPage1,lblReceiverAddr,gc2,0,5,1,1);
		addToGridBag(orderPage1,receiverAddrTxt,gc2,1,5,2,1);
		
		
		addToGridBag(orderPage1,lblshippingLocation,gc2,0,6,1,1);
		addToGridBag(orderPage1,rdbLocal,gc2,1,6,1,1);
		addToGridBag(orderPage1,rdbInternational,gc2,1,7,1,1);
		
		addToGridBag(orderPage1,lblZone,gc2,0,8,1,1);
        gc2.fill = GridBagConstraints.WEST;
        gc2.anchor = GridBagConstraints.WEST;


		addToGridBag(orderPage1,zoneTxt,gc2,1,8,1,1);
		addToGridBag(orderPage1,zoneBtn,gc2,2,8,1,1);

		addToGridBag(orderPage1,goToOrderPage2Btn, gc2,0,9,1,1);
		addToGridBag(orderPage1,clearOrderPage1Btn,gc2,1,9,1,1);
		
		zoneBtn.addActionListener( e->
		{

			
				if(zoneChance == true)
				{
					String x = String.valueOf(setZone());
					zoneTxt.setText(x);	
					zoneChance = false;
				}
			
		});
		

		goToOrderPage2Btn.addActionListener(e ->
		{
				//validation checks
				boolean flag1 = rdbLocal.isSelected();
				boolean flag2 = rdbInternational.isSelected();
				
				if(senderNameTxt.getText().compareTo("") == 0 || senderAddrTxt.getText().compareTo("") == 0
						|| receiverNameTxt.getText().compareTo("") == 0 ||
						receiverAddrTxt.getText().compareTo("") == 0 ||
						zoneTxt.getText().compareTo("") == 0 
					|| flag1 == false && flag2 == false)
				{
						JOptionPane.showMessageDialog(customerPanel,
				        		 "One or two fields were not filled");
				}
				else if(receiverNameTxt.getText().length() < 3 || senderNameTxt.getText().length() < 3 || 
						receiverAddrTxt.getText().length()< 3 || senderAddrTxt.getText().length() < 3) 
				{
					JOptionPane.showMessageDialog(customerPanel,"Name too short.\nName must be more than 2 characters");
					return;
				}
				else
				{
					senderName = senderNameTxt.getText();
					senderAddr = senderAddrTxt.getText();
					receiverName = receiverNameTxt.getText();
					receiverAddr = receiverAddrTxt.getText();
					shippingLocation = btnGrpLocation.getSelection().getActionCommand();
					distance = Integer.parseInt(zoneTxt.getText());
					
					
		
					
		
		
		                 // show the value of currentcard
		                 cardLayout.show(customerPanel, "ShipmentForm2");
					
					
				
				
				
			}
		
		});

		clearOrderPage1Btn.addActionListener(e ->
				{
					
						senderNameTxt.setText("");
						senderAddrTxt.setText("");
						receiverNameTxt.setText("");
						receiverAddrTxt.setText("");
						zoneTxt.setText("");
						btnGrpLocation.clearSelection();
						zoneChance = true;
		
					});
		
		GridBagConstraints gc3 = new GridBagConstraints();
 		gc3 = new GridBagConstraints();
 		gc3.insets = new Insets(10, 10, 10, 10);
        gc3.fill = GridBagConstraints.HORIZONTAL;
		
        addToGridBag(orderPage2,lblPackageSection,gc3,0,0,4,1);
		
		
		gc3.anchor = GridBagConstraints.EAST;

		addToGridBag(orderPage2,lblPackageType,gc3,0,1,1,1);
		gc3.anchor = GridBagConstraints.WEST;

		addToGridBag(orderPage2,rdbStandard,gc3,1,1,1,1);
		addToGridBag(orderPage2,rdbExpress,gc3,2,1,1,1);
		addToGridBag(orderPage2,rdbFragile,gc3,3,1,1,1);
		
		gc3.anchor = GridBagConstraints.EAST;

		addToGridBag(orderPage2,lblPackageName,gc3,0,2,1,1);
		gc3.anchor = GridBagConstraints.WEST;

		addToGridBag(orderPage2,packageNameTxt,gc3,1,2,2,1);
		addToGridBag(orderPage2,lblWeight,gc3,0,3,1,1);
		addToGridBag(orderPage2,weightTxt,gc3,1,3,1,1);
		gc3.anchor = GridBagConstraints.EAST;
		addToGridBag(orderPage2,lblPounds,gc3,2,3,1,1);

		
		addToGridBag(orderPage2,lblDimensions,gc3,0,4,1,1);
		gc3.anchor = GridBagConstraints.WEST;
		addToGridBag(orderPage2,lengthTxt,gc3,1,4,1,1);
		gc3.anchor = GridBagConstraints.EAST;
		addToGridBag(orderPage2,lblLength,gc3,2,4,1,1);
		gc3.anchor = GridBagConstraints.WEST;
		addToGridBag(orderPage2,widthTxt,gc3,1,5,1,1);
		gc3.anchor = GridBagConstraints.EAST;
		addToGridBag(orderPage2,lblWidth,gc3,2,5,1,1);
		gc3.anchor = GridBagConstraints.WEST;
		addToGridBag(orderPage2,heightTxt,gc3,1,6,1,1);
		gc3.anchor = GridBagConstraints.EAST;
		addToGridBag(orderPage2,lblHeight,gc3,2,6,1,1); 
		addToGridBag(orderPage2,inchMessage,gc3,1,7,3,1); 

		addToGridBag(orderPage2,lblCartOpt,gc3,0,8,1,1);
		gc3.anchor = GridBagConstraints.WEST;

		addToGridBag(orderPage2,addToCartBtn,gc3,1,8,1,1);
		addToGridBag(orderPage2,checkCartBtn,gc3,2,8,1,1);
		addToGridBag(orderPage2,delPackageBtn,gc3,1,9,1,1);
		addToGridBag(orderPage2,clearCartBtn,gc3,2,9,1,1);

		
		addToCartBtn.addActionListener(e -> {
			
			//validation checks
			boolean flagS = rdbStandard.isSelected();
			boolean flagE = rdbExpress.isSelected();
			boolean flagF = rdbFragile.isSelected();
			
			
			
			if(packageNameTxt.getText().compareTo("") == 0 || weightTxt.getText().compareTo("") == 0
					|| lengthTxt.getText().compareTo("") == 0 ||
					widthTxt.getText().compareTo("") == 0 ||
					heightTxt.getText().compareTo("") == 0 
				|| flagS == false && flagE == false && flagF == false)
			{
					JOptionPane.showMessageDialog(customerPanel,
			        		 "One or two fields were not filled");
			}
			else if (!weightTxt.getText().matches("^\\d+(\\.\\d+)?$") ||
					!lengthTxt.getText().matches("^\\d+(\\.\\d+)?$") ||
					!widthTxt.getText().matches("^\\d+(\\.\\d+)?$") ||
					!heightTxt.getText().matches("^\\d+(\\.\\d+)?$")) 
			{
			    logger.warn("Invalid number format. Must be like 3 or 3.14");
			    throw new NumberFormatException("Invalid number format. Must be like 3 or 3.14");
			}
			else 
			{
				String packageType = btnGrpPackages.getSelection().getActionCommand();
				String packageName = packageNameTxt.getText();
				double weight = Double.parseDouble(weightTxt.getText());
				double length = Double.parseDouble(lengthTxt.getText());
				double width = Double.parseDouble(widthTxt.getText());
				double height = Double.parseDouble(heightTxt.getText());
				
				
				
				if( (currentWeight + weight) > weightCap)
				{
					JOptionPane.showMessageDialog(customerPanel,"Failed to add item\n Adding this item would exceed the weight limit\n"
							+ "Weight Limit: " + weightCap + "\nCurrent Weight: " + currentWeight);
					return;
				}
				
				if((currentQuantity + 1) > quantityCap)
				{
					JOptionPane.showMessageDialog(customerPanel,"Failed to add item\n Adding this item would exceed the quantity limit\n"
							+ "Quantity Limit: " + quantityCap + "\nCurrent Quantity: " + currentQuantity);
					return;
				}
				
				if(length > lengthCap)
				{
					JOptionPane.showMessageDialog(customerPanel,"Failed to add item\n Adding this item would exceed the length limit\n"
							+ "Quantity Limit: " + lengthCap);
					return;
				}
				if(width > widthCap)
				{
					JOptionPane.showMessageDialog(customerPanel,"Failed to add item\n Adding this item would exceed the width limit\n"
							+ "Width Limit: " + lengthCap);
					return;
				}
				
				if(height > heightCap)
				{
					JOptionPane.showMessageDialog(customerPanel,"Failed to add item\n Adding this item would exceed the height limit\n"
							+ "Height Limit: " + heightCap);
					return;
				}
				
				currentWeight += weight;
				currentQuantity += 1;
				
				Shipment newShipment = new Shipment();
				
				newShipment = new Shipment("PACK#" + currentQuantity, loggedInUser.getTrn(), 
						senderName, senderAddr, 
						receiverName, receiverAddr, 
						packageName, packageType, 
						"Pending",  distance, "" + receiverAddr + " Zone " + zoneTxt.getText(), 
						weight, length,width,height, 0);
				newShipment.calculateShippingCost();
				
				
				JOptionPane.showMessageDialog (customerPanel, "Package Number: " + newShipment.getPackageNo() +
						"\nPackage Name: " + newShipment.getPackageName() +
						"\nType: " + newShipment.getPackageType() +
						"\nStatus: " + newShipment.getStatus() +
						"\nWeight: " + newShipment.getWeight() + " lbs" +
						"\nDimensions: " + newShipment.getLength() + " '' " + "x " + newShipment.getWidth() + " '' " + "x " + newShipment.getHeight() + " '' " + 
						"\nCost: $" + newShipment.getCost()
						);	
				shipments.add(newShipment);

				
			}
			

			
			
		});
		
		//addToGridBag(orderPage2,goToOrderBtn3,gc3,0,9,1,1);
		//addToGridBag(orderPage2,prevOrderBtn1,gc,1,9,1,1);
	
		

/*nextShipBtn2.addActionListener(new ActionListener() {

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(exception == false)
		{


             // show the value of currentcard
             cardLayout.show(shipmentPanel, "3");
		}
	}
	
});



 // add previousbtn in ActionListener
 prevShipBtn2.addActionListener(new ActionListener() 
 {
     public void actionPerformed(ActionEvent arg0)
     {	            

             cardLayout.show(shipmentPanel, "1");
         
     }
 });*/
		
		
		
 		
    	 
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
	
	private int setZone()
	{
		Random zone = new Random();
		 
		return (zone.nextInt(4 - 1 + 1) + 1);
	}
	
	public static void main(String[] args)
	{
		new CustomerView(new User("123", "BOB", "Brown", "cat123","876-911-9111","bob.brown@gmail.com"));
	}
      
}
