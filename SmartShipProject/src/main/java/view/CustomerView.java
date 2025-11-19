package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import model.Database;
import model.Shipment;
import model.User;
import network.Client;
import network.Server;

public class CustomerView extends JFrame 
{
	
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(CustomerView.class);
	private GridBagConstraints gc;
	private JPanel customerPanel, menu, orderPage1,orderPage2,orderPage3, invoicePage;
	private CardLayout cardLayout;
	private JMenu account, nav;
	private JMenuItem track,order,bill,home,logout,info;
	
	private final double weightCap =  2600, lengthCap = 250, widthCap = 100,heightCap = 115;
	private double currentWeight = 0;
	private final int quantityCap = 20;
	private int currentQuantity = 0, packageID = 0; 

	private int distance;
    private boolean zoneChance = true;
    private String senderName, senderAddr, receiverName, receiverAddr, shippingLocation;
    private Shipment newShipment;ButtonGroup btnGrpPackages;
	private ArrayList<Shipment> shipments;
	private JTable table;
	private JScrollPane scrollPane;
	private Client client = new Client();



	
	public CustomerView(User loggedInUser)
    {
		
			
			setTitle("Customer System");
			setSize(1000,650);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	    
	     cardLayout = new CardLayout();
	    
	     customerPanel = new JPanel(cardLayout);
	    
	    
	    	
    	 menu = new JPanel(new GridBagLayout());
		 orderPage1 = new JPanel(new GridBagLayout());
		 orderPage2 = new JPanel(new GridBagLayout());
		 orderPage3 = new JPanel(new GridBagLayout());
		 invoicePage = new JPanel(new GridBagLayout());
		
		customerPanel.add(menu, "Menu");
		customerPanel.add(orderPage1,"ShipmentForm1");
		customerPanel.add(orderPage2,"ShipmentForm2");
		customerPanel.add(orderPage3, "ShipmentForm3");
		customerPanel.add(invoicePage, "InvoicePage");
		    	   
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
     		 	
 				cardLayout.show(customerPanel, "ShipmentForm2");
          });
    	
    	home.addActionListener(e ->{
 		 	
				cardLayout.show(customerPanel, "Menu");
      });
    	
    	logout.addActionListener(e ->{
            logger.info("" + loggedInUser.getTrn() + "has successfully logged out");
    		client.closeConnection();
            this.dispose();
    		new Login();
    	});
    	
   
    	JLabel lblSenderSection = new JLabel("Sender Information Section", SwingConstants.CENTER);
 		lblSenderSection.setFont(new Font("Arial", Font.BOLD, 20));
 		JLabel lblSenderName = new JLabel("Sender Name: ");
 		
 		JTextField senderNameTxt = new JTextField(30);
 		JLabel lblSenderAddr = new JLabel("Sender Address: ");
 		JTextField senderAddrTxt = new JTextField(50);
 		
 		JLabel lblReceiverSection = new JLabel("Receiver Information Section",SwingConstants.CENTER);
 		lblReceiverSection.setFont(new Font("Arial", Font.BOLD, 20));
 		JLabel lblReceiverAddr = new JLabel("Receiver Address: ");
 		JTextField receiverAddrTxt = new JTextField(50);
 		JLabel lblReceiverName = new JLabel("Receiver Name:");
		JTextField receiverNameTxt = new JTextField(30);
		
		/*
 		JLabel lblshippingLocation = new JLabel("Shipping Zone:");
 		JRadioButton rdbLocal = new JRadioButton("Local");
 		rdbLocal.setActionCommand("Local");
 		JRadioButton rdbInternational = new JRadioButton("International");
 		rdbInternational.setActionCommand("International");
 		ButtonGroup btnGrpLocation = new ButtonGroup();
 		btnGrpLocation.add(rdbLocal);
 		btnGrpLocation.add(rdbInternational);
 		*/
 		
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
		
		btnGrpPackages = new ButtonGroup();
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
		JButton goToOrderPage3Btn = new JButton("Next");
		JButton prevOrderPage1Btn = new JButton("Previous");
		JButton clearOrderPage2Btn = new JButton("Clear Form");
		shipments = new ArrayList<Shipment>();
		
		JLabel lblCartSection = new JLabel("Cart Detail Section", SwingConstants.CENTER);
		lblCartSection.setFont(new Font("Arial", Font.BOLD,20));
		JLabel lblPaymentMethod = new JLabel("Payment Method:");
		JRadioButton rdbCash = new JRadioButton("Cash");
		JRadioButton rdbCard = new JRadioButton("Card");
		ButtonGroup btnGrpPayment = new ButtonGroup();
		btnGrpPayment.add(rdbCash);
		btnGrpPayment.add(rdbCard);
		JButton checkoutBtn = new JButton("Checkout");
		JButton prevOrderPage2Btn = new JButton("Back");
		JButton cancelOrderBtn = new JButton("Cancel Order");
 		
 		table = new JTable();
 		scrollPane = new JScrollPane();
 		
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
		
		/*
		addToGridBag(orderPage1,lblshippingLocation,gc2,0,6,1,1);
		addToGridBag(orderPage1,rdbLocal,gc2,1,6,1,1);
		addToGridBag(orderPage1,rdbInternational,gc2,1,7,1,1);
		*/
		
		addToGridBag(orderPage1,lblZone,gc2,0,8,1,1);
        gc2.fill = GridBagConstraints.WEST;
        gc2.anchor = GridBagConstraints.WEST;


		addToGridBag(orderPage1,zoneTxt,gc2,1,8,1,1);
		addToGridBag(orderPage1,zoneBtn,gc2,2,8,1,1);

		addToGridBag(orderPage1,goToOrderPage2Btn, gc2,1,9,1,1);
		addToGridBag(orderPage1,clearOrderPage1Btn,gc2,2,9,1,1);
		
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
				//boolean flag1 = rdbLocal.isSelected();
				//boolean flag2 = rdbInternational.isSelected();
				
				if(senderNameTxt.getText().compareTo("") == 0 || senderAddrTxt.getText().compareTo("") == 0
						|| receiverNameTxt.getText().compareTo("") == 0 ||
						receiverAddrTxt.getText().compareTo("") == 0 ||
						zoneTxt.getText().compareTo("") == 0)
					//|| flag1 == false && flag2 == false)
				{
						JOptionPane.showMessageDialog(customerPanel,
				        		 "One or more fields were not filled");
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
					//shippingLocation = btnGrpLocation.getSelection().getActionCommand();
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
					//	btnGrpLocation.clearSelection();
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
		addToGridBag(orderPage2,goToOrderPage3Btn,gc3,1,10,1,1);
		addToGridBag(orderPage2,prevOrderPage1Btn,gc3,2,10,1,1);
		addToGridBag(orderPage2,clearOrderPage2Btn,gc3,3,10,1,1);

		
		GridBagConstraints gc4 = new GridBagConstraints();
		
		

		
		
		
		
		addToCartBtn.addActionListener(e -> {
			
			//validation checks
			boolean flagS = rdbStandard.isSelected();
			boolean flagE = rdbExpress.isSelected();
			boolean flagF = rdbFragile.isSelected();
			
			
			try
			{

				if(packageNameTxt.getText().compareTo("") == 0 || weightTxt.getText().compareTo("") == 0
						|| lengthTxt.getText().compareTo("") == 0 ||
						widthTxt.getText().compareTo("") == 0 ||
						heightTxt.getText().compareTo("") == 0 
					|| flagS == false && flagE == false && flagF == false)
				{
						JOptionPane.showMessageDialog(customerPanel,
				        		 "One or more fields were not filled");
				}
				else if (!weightTxt.getText().matches("^\\d+(\\.\\d+)?$") ||
						!lengthTxt.getText().matches("^\\d+(\\.\\d+)?$") ||
						!widthTxt.getText().matches("^\\d+(\\.\\d+)?$") ||
						!heightTxt.getText().matches("^\\d+(\\.\\d+)?$")) 
				{
					logger.warn("Invalid number format. Must be like 3 or 3.14");
				    JOptionPane.showMessageDialog(customerPanel,"Invalid number format. Must be like 3 or 3.14");
				    return;
				}
				else 
				{
					String packageType = btnGrpPackages.getSelection().getActionCommand();
					String packageName = packageNameTxt.getText();
					double weight = Double.parseDouble(weightTxt.getText());
					double length = Double.parseDouble(lengthTxt.getText());
					double width = Double.parseDouble(widthTxt.getText());
					double height = Double.parseDouble(heightTxt.getText());
					
					if(weight <= 0 || length <=0 || width <=0 || height <=0)
					{
						logger.warn("No dimensions should be zero or a negative number");
						JOptionPane.showMessageDialog(customerPanel,"No dimensions should be zero or a negative number");
						return;
					}
					
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
						logger.warn("Failed to add item\n Adding this item would exceed the width limit. "
								+ "Width Limit: " + lengthCap);
						return;
					}
					
					if(height > heightCap)
					{
						JOptionPane.showMessageDialog(customerPanel,"Failed to add item\n Adding this item would exceed the height limit\n"
								+ "Height Limit: " + heightCap);
						logger.warn("Failed to add item\n Adding this item would exceed the height limit. "
								+ "Height Limit: " + heightCap);
						return;
					}
					
					currentWeight += weight;
					currentQuantity += 1;
					packageID += 1;
					
					newShipment = new Shipment();
					
					newShipment = new Shipment(packageID , loggedInUser.getTrn(), 
							senderName, senderAddr, 
							receiverName, receiverAddr, 
							packageName, packageType, 
							"Pending",  distance, "" + receiverAddr + " Zone#" + zoneTxt.getText(), 
							weight, length,width,height, 0, LocalDate.now());
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
					logger.info("Shipment item added");
				}
			}catch(NumberFormatException nf)
			{
			    logger.warn("Invalid number format. Must be like 3 or 3.14");
			    JOptionPane.showMessageDialog(customerPanel,"Invalid number format. Must be like 3 or 3.14");
			    return;
			}catch(Exception u)
			{
				logger.error(u.getMessage());
			}
		});
		
		

		goToOrderPage3Btn.addActionListener(e -> 
		{
			
				try
				{
					if(newShipment == null || shipments.get(0).getPackageName().equals(""))
		            {
		            	JOptionPane.showMessageDialog(customerPanel, "No items were added to list.\nPlease add item/s in order to proceed");
		            	logger.warn("No items were added to list. Please add item(s) in order to proceed");
		            	return;
		            }
				}catch(IndexOutOfBoundsException in)
				{
					JOptionPane.showMessageDialog(customerPanel, "No items were added to list.\nPlease add item/s in order to proceed");
	            	logger.warn("No items were added to list. Please add item(s) in order to proceed: " + in.getMessage());
	            	return;
				}
	            
	            
	            table = new JTable();
	            currentQuantity = shipments.size();
	            String[][] data = new String [currentQuantity][4];
	    		String[] columnNames = {"ItemID", "Name", "Price", "Weight"};
	    		
	    		
	    		for (int i = 0; i < data.length; i++) {

	    		    Shipment s = shipments.get(i);

	    		    data[i][0] = String.valueOf(s.getPackageNo());
	    		    data[i][1] = s.getPackageName();
	    		    data[i][2] = "$"+ String.valueOf(s.getCost());
	    		    data[i][3] = String.valueOf(s.getWeight());
	    		}
	    		
	    		table = new JTable(data, columnNames);
	    		scrollPane = new JScrollPane(table);
	    		
	    		orderPage3.removeAll();
	    		
	    		gc4.insets = new Insets(10,10,10,10);
	    		gc4.anchor = GridBagConstraints.CENTER;
	            gc4.fill = GridBagConstraints.HORIZONTAL;
	            
	    		addToGridBag(orderPage3,lblCartSection,gc4,0,0,3,1);
	            
	    		addToGridBag(orderPage3,checkoutBtn,gc4,0,1,1,1);
	    		addToGridBag(orderPage3,prevOrderPage2Btn,gc4,1,1,1,1);
	    		addToGridBag(orderPage3,cancelOrderBtn,gc4,2,1,1,1);

	    		addToGridBag(orderPage3,lblPaymentMethod,gc4,0,2,1,1);
	    		addToGridBag(orderPage3,rdbCash,gc4,1,2,1,1);
	    		addToGridBag(orderPage3,rdbCard,gc4,2,2,1,1);
	    		gc4.fill = GridBagConstraints.BOTH; 

	    		addToGridBag(orderPage3,scrollPane,gc4,0,3,1,1);
	    		
	    		
	    	    orderPage3.revalidate();
	    	    orderPage3.repaint();

	            cardLayout.show(customerPanel, "ShipmentForm3");
	            logger.info("Customer went to page 3 of Shipment Form");
	            
		});


		prevOrderPage1Btn.addActionListener(e ->
		 {
		    cardLayout.show(customerPanel, "ShipmentForm1"); 
            logger.info("Customer went to page 1 of Shipment Form");
		 });
		
		clearCartBtn.addActionListener( e->{
			
			shipments = null;
			shipments = new ArrayList<Shipment>();
			currentQuantity = 0;
			currentWeight = 0;
			logger.info("Item list has been cleared");

		});
		
		

		checkCartBtn.addActionListener( e->{
			if(shipments == null)
			{
				JOptionPane.showMessageDialog(customerPanel,"Item list is currently empty", "Check Cart", JOptionPane.INFORMATION_MESSAGE);
			}
			StringBuilder sb = new StringBuilder();

			for (Object item : shipments) {
			    sb.append(item.toString()).append("\n");
			}
			
			JOptionPane.showMessageDialog(customerPanel, sb.toString(), "Check Cart", JOptionPane.INFORMATION_MESSAGE);
            logger.info("Customer checked cart");

		});
		
		clearOrderPage2Btn.addActionListener(e -> {
			
			btnGrpPackages.clearSelection();
			packageNameTxt.setText("");
			weightTxt.setText("");
			lengthTxt.setText("");
			widthTxt.setText("");
			heightTxt.setText("");			
		});
		
		delPackageBtn.addActionListener(e ->{
			
			if(shipments == null)
			{
				JOptionPane.showMessageDialog(customerPanel, "Item list is empty\nUnable to delete item", "Delete Package",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
				
			 String num = JOptionPane.showInputDialog(customerPanel,"Enter the package number to delete:");
			 shipments.forEach(u ->{
				 if(u.getPackageNo() == Integer.parseInt(num))
				 {
					 currentWeight -= u.getWeight();
					 return;
				 }
			 });
			 shipments.removeIf(y -> y.getPackageNo() == Integer.parseInt(num));

			 
			 currentQuantity -= 1;
			 logger.info("Shipment Item Deleted");
		});
		
		
		
		checkoutBtn.addActionListener(e->{
			
			boolean flagCard = rdbCard.isSelected();
			boolean flagCash = rdbCash.isSelected();

			
			if(flagCard == false && flagCash == false)
			{
				JOptionPane.showMessageDialog(customerPanel, "Payment method has not been select\nPlease choose either cash or card to proceed with order", "Check Out",JOptionPane.WARNING_MESSAGE);
				logger.warn("Payment method has not been select\nPlease choose either cash or card to proceed with order");
				return;
			}
			int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog(customerPanel, "Are you sure you want to confirm your order?\nThis action cannot be undone once you confirm","Checkout Order", dialogButton);
			 if(dialogResult == 0) {
				 JOptionPane.showMessageDialog(customerPanel, "Generating Invoice...", "Checkout Order",JOptionPane.INFORMATION_MESSAGE);
					logger.info("Generating Invoice");

				 	shipments.forEach(g -> 
				 	{
				 		g.setPackageNo(null);
				 		boolean task =  client.requestOrder(g);
				 	});
				 	
					 JOptionPane.showMessageDialog(customerPanel, "Successfully created Invoices\nPlease check the menu - [Manage Bills]", "Checkout Order",JOptionPane.INFORMATION_MESSAGE);
					 clearOrderPage1Btn.doClick();
					 clearOrderPage2Btn.doClick();
					 clearCartBtn.doClick();
					 cardLayout.show(customerPanel, "Menu");
			 }
		});
		
		prevOrderPage2Btn.addActionListener(e->{
			
			cardLayout.show(customerPanel, "ShipmentForm2");
		});
		
		cancelOrderBtn.addActionListener(e->{
			int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog(customerPanel, "Are you sure you want to cancel your order?\nThis cannot be undone once you confirm","Cancel Order", dialogButton);
			 if(dialogResult == 0) {
				 JOptionPane.showMessageDialog(customerPanel, "All shipment items have been cancelled", "Cancel Order",JOptionPane.INFORMATION_MESSAGE);
				 clearOrderPage1Btn.doClick();
				 clearOrderPage2Btn.doClick();
				 clearCartBtn.doClick();
				 cardLayout.show(customerPanel, "Menu");
				} 
		});
         this.setVisible(true); 	 
    }
	
	
	public void addToGridBag(JPanel panel, Component component, GridBagConstraints gbc, int column, int row,  int colspan, int rowspan)
   	{

   		if (panel == null ) {
   		        logger.error("addToGridBag - Panel is null!");
   		        return;
   		    }
   		
   		if(component == null)
   		{
   			logger.error("addToGridBag - Component is null!");
   	        return;
   		}
   			
   	
   	
   	        gbc.gridx = column;
   	        gbc.gridy = row;
   	
   	        gbc.gridwidth = colspan;
   	        gbc.gridheight = rowspan;
   	        
   	        
   	        panel.add(component,gbc);
       }
	
	private void populateInvoice()
	{
		GridBagConstraints gc4 = new GridBagConstraints();
		JScrollPane scroll = new JScrollPane();
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
