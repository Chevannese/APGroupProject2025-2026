package view;

import model.Shipment;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ShipmentView extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblSenderSection, lblSenderName, lblSenderAddr, 
	lblReceiverSection, lblReceiverTRN,lblReceiverFName, lblReceiverLName, lblReceiverAddr, lblshippingLocation,
	lblZone, lblWeight,lblLength,lblWidth,lblHeight, lblDimensions, lblPounds, lblCartOpt,
	lblPackageSection, lblPackageName,lblPackageType,
	lblCartSection, lblCart, lblPaymentMethod, lblItem, lblPrice,
	lblQty,lblTotalWeight,lblSubTotal,lblSurcharge,lblDiscount,lblGrandTotal;
	private JTextField senderNameTxt,senderAddrTxt, 
	receiverTRNTxt,receiverFNameTxt,receiverLNameTxt, receiverAddrTxt,zoneTxt,
	weightTxt,lengthTxt,widthTxt,heightTxt, packageNameTxt
	;
	
	private JRadioButton rdbStandard, rdbExpress,rdbFragile,
	rdbLocal,rdbInternational,
	rdbCash,rdbCard;
	private ButtonGroup btnGrpPackages,btnGrpLocation, btnGrpPayment;
	private JButton clearBtn,btnZone, nextBtn,prevBtn,addToCartBtn, checkCartBtn, clearCartBtn, delPackageBtn, nextShipBtn1;
	
	private JPanel mainPanel, shipmentForm1,shipmentForm2,shipmentForm3,shipmentForm4, buttonPanel,shipmentPanel;
	private JProgressBar progress;
	private JScrollPane scrollCartTable;
	private JTable cartTable;

	private String[] illegalItems = {"gun","knife","drug","money"};
	private String[] packageCols = {"PackageID","PackageName", "Cost", "Weight", "Qty", "Remove"};
	private int illegalItemsLength = illegalItems.length;
	
	
	private ArrayList<Shipment> shipments;
	private GridBagConstraints gc;
    private int currentCard = 1;
    private CardLayout cardLayout;
	
	private void initializeShipmentComponents()
	{
		lblSenderSection = new JLabel("Sender Information Section");
		lblSenderSection.setFont(new Font("Verdana", Font.BOLD, 20));
		lblSenderName = new JLabel("Sender Name: ");
		senderNameTxt = new JTextField(30);
		lblSenderAddr = new JLabel("Sender Address: ");
		senderAddrTxt = new JTextField(50);
		
		//This section should be auto filled based on User Information
		lblReceiverSection = new JLabel("Receiver Information Section");
		lblReceiverSection.setFont(new Font("Verdana", Font.BOLD,20));
		lblReceiverTRN = new JLabel("Tax Registration Number: ");
		receiverTRNTxt = new JTextField(9);
		lblReceiverFName = new JLabel("Receiver First Name:");
		lblReceiverLName = new JLabel("Receiver Last Name:");
		receiverFNameTxt = new JTextField(30);
		receiverLNameTxt = new JTextField(30);
		lblReceiverAddr = new JLabel("Receiver Address: ");
		receiverAddrTxt = new JTextField(50);
		
		lblshippingLocation = new JLabel("Shipping Location:");
		rdbLocal = new JRadioButton("Local");
		rdbInternational = new JRadioButton("International");
		btnGrpLocation = new ButtonGroup();
		btnGrpLocation.add(rdbLocal);
		btnGrpLocation.add(rdbInternational);
		
		
		lblZone = new JLabel("Zone: ");
		btnZone = new JButton("Generate Zone");
		zoneTxt = new JTextField(5);
		
		
		
		lblPackageSection = new JLabel("Package Information Section");
		lblPackageSection.setFont(new Font("Verdana", Font.BOLD,20));
		
		lblPackageType = new JLabel("Package Type: ");
		
		
		rdbStandard = new JRadioButton("Standard");
		rdbExpress = new JRadioButton("Express");
		rdbFragile = new JRadioButton("Fragile");
		btnGrpPackages = new ButtonGroup();
		btnGrpPackages.add(rdbStandard);
		btnGrpPackages.add(rdbExpress);
		btnGrpPackages.add(rdbFragile);
		
		lblPackageName = new JLabel("Package Name: ");
		packageNameTxt = new JTextField(30);
		lblWeight = new JLabel("Package Weight:");
		weightTxt = new JTextField(3);
		lblPounds = new JLabel("Pounds");
		lblDimensions = new JLabel("Package Dimensions:");
		lengthTxt = new JTextField(3);
		widthTxt = new JTextField(3);
		heightTxt = new JTextField(3);
		lblLength = new JLabel("Length");
		lblWidth = new JLabel("Width");
		lblHeight = new JLabel("Height");
		
		lblCartOpt = new JLabel("Cart Options:");
		addToCartBtn = new JButton("Add to Cart");
		checkCartBtn = new JButton("Check Cart");
		clearCartBtn = new JButton("Clear Cart");
		delPackageBtn = new JButton("Delete Package");
		
		lblCartSection = new JLabel("Cart Detail Section");
		lblCartSection.setFont(new Font("Verdana", Font.BOLD,20));
		lblPaymentMethod = new JLabel("Payment Method:");
		rdbCash = new JRadioButton("Cash");
		rdbCard = new JRadioButton("Card");
		btnGrpPayment = new ButtonGroup();
		btnGrpPayment.add(rdbCash);
		btnGrpPayment.add(rdbCard);
		
		cartTable =  new JTable();
		
		nextBtn = new JButton("Next");
		prevBtn = new JButton("Previous");
		clearBtn = new JButton("Clear Form");
		nextShipBtn1 = new JButton("Next");
		
		shipmentForm1 = new JPanel(new GridBagLayout());
		shipmentForm2 = new JPanel(new GridBagLayout());
		shipmentForm3 = new JPanel(new GridBagLayout());
		shipmentForm4 = new JPanel(new GridBagLayout());
		
		buttonPanel = new JPanel();
		gc = new GridBagConstraints();	
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		shipmentPanel = new JPanel(cardLayout);
		progress = new JProgressBar();
		progress.setValue(0);
		progress.setStringPainted(true);
		progress.setMaximum(100);
		progress.setForeground(Color.BLUE);
		
		//Color progressDefault;
		//progress.setSize(10,20);
	
		
	}
	
	
	private void addShipmentComponentsToPanel()
	{
		//Padding of 10 all around
		gc.insets = new Insets(10,10,10,10);
		gc.anchor = GridBagConstraints.CENTER;
		gc.ipady = 5;
			
		addObjects(shipmentForm1,lblSenderSection,gc,0,0,5,1);
				
		gc.anchor = GridBagConstraints.EAST;


		addObjects(shipmentForm1,lblSenderName,gc,0,1,1,1);

		addObjects(shipmentForm1,senderNameTxt,gc,1,1,1,1);

		
		addObjects(shipmentForm1,lblSenderAddr,gc,0,2,1,1);

		addObjects(shipmentForm1,senderAddrTxt,gc,1,2,2,1);

		
		//Add lblReceiverSection: column 0 of row 2 with colspan of 5
		
		gc.anchor = GridBagConstraints.CENTER;

		addObjects(shipmentForm1,lblReceiverSection,gc,0,3,5,1);
		gc.anchor = GridBagConstraints.EAST;


		addObjects(shipmentForm1,lblReceiverTRN,gc,0,4,1,1);
				
		gc.anchor = GridBagConstraints.WEST;

		addObjects(shipmentForm1,receiverTRNTxt,gc,1,4,2,1);


		
		gc.anchor = GridBagConstraints.EAST;

		addObjects(shipmentForm1,lblReceiverFName,gc,0,5,1,1);
		gc.anchor = GridBagConstraints.WEST;

		addObjects(shipmentForm1,receiverFNameTxt,gc,1,5,1,1);
		gc.anchor = GridBagConstraints.EAST;

		addObjects(shipmentForm1,lblReceiverLName,gc,0,6,1,1);
		gc.anchor = GridBagConstraints.WEST;
		addObjects(shipmentForm1,receiverLNameTxt,gc,1,6,2,1);
		
		gc.anchor = GridBagConstraints.EAST;

		addObjects(shipmentForm1,lblReceiverAddr,gc,0,7,1,1);
		

		gc.anchor = GridBagConstraints.WEST;

		addObjects(shipmentForm1,receiverAddrTxt,gc,1,7,2,1);
		gc.anchor = GridBagConstraints.EAST;

		addObjects(shipmentForm1,lblshippingLocation,gc,0,8,1,1);
		gc.anchor = GridBagConstraints.WEST;

		addObjects(shipmentForm1,rdbLocal,gc,1,8,1,1);
		gc.anchor = GridBagConstraints.EAST;
		addObjects(shipmentForm1,rdbInternational,gc,1,8,1,1);
		gc.anchor = GridBagConstraints.WEST;


		gc.anchor = GridBagConstraints.EAST;

		addObjects(shipmentForm1,lblZone,gc,0,9,1,1);
		gc.anchor = GridBagConstraints.WEST;


		addObjects(shipmentForm1,btnZone,gc,1,9,1,1);
		
		gc.anchor = GridBagConstraints.EAST;

		addObjects(shipmentForm1,zoneTxt,gc,1,9,1,1);
		addObjects(shipmentForm1,nextShipBtn1, gc,0,10,1,1);
		
		
		/*END OF SHIPMENT FORM PAGE 1
		 * 
		 * 
		 * START OF SHIPMENT FORM PAGE 2*/

		gc = new GridBagConstraints();
		
		gc.insets = new Insets(10,10,10,10);
		gc.anchor = GridBagConstraints.CENTER;
		

		addObjects(shipmentForm2,lblPackageSection,gc,0,0,4,1);
		
		
		gc.anchor = GridBagConstraints.EAST;

		addObjects(shipmentForm2,lblPackageType,gc,0,1,1,1);
		gc.anchor = GridBagConstraints.WEST;

		addObjects(shipmentForm2,rdbStandard,gc,1,1,1,1);
		addObjects(shipmentForm2,rdbExpress,gc,2,1,1,1);
		addObjects(shipmentForm2,rdbFragile,gc,3,1,1,1);
		
		gc.anchor = GridBagConstraints.EAST;

		addObjects(shipmentForm2,lblPackageName,gc,0,2,1,1);
		gc.anchor = GridBagConstraints.WEST;

		addObjects(shipmentForm2,packageNameTxt,gc,1,2,2,1);
		addObjects(shipmentForm2,lblWeight,gc,0,3,1,1);
		addObjects(shipmentForm2,weightTxt,gc,1,3,1,1);
		gc.anchor = GridBagConstraints.EAST;
		addObjects(shipmentForm2,lblPounds,gc,1,3,1,1);

		
		addObjects(shipmentForm2,lblDimensions,gc,0,4,1,1);
		gc.anchor = GridBagConstraints.WEST;
		addObjects(shipmentForm2,lengthTxt,gc,1,4,1,1);
		gc.anchor = GridBagConstraints.EAST;
		addObjects(shipmentForm2,lblLength,gc,1,4,1,1);
		gc.anchor = GridBagConstraints.WEST;
		addObjects(shipmentForm2,widthTxt,gc,1,5,1,1);
		gc.anchor = GridBagConstraints.EAST;
		addObjects(shipmentForm2,lblWidth,gc,1,5,1,1);
		gc.anchor = GridBagConstraints.WEST;
		addObjects(shipmentForm2,heightTxt,gc,1,6,1,1);
		gc.anchor = GridBagConstraints.EAST;
		addObjects(shipmentForm2,lblHeight,gc,1,6,1,1); 
		
		addObjects(shipmentForm2,lblCartOpt,gc,0,7,1,1);
		gc.anchor = GridBagConstraints.WEST;

		addObjects(shipmentForm2,addToCartBtn,gc,1,7,1,1);
		addObjects(shipmentForm2,checkCartBtn,gc,2,7,1,1);
		addObjects(shipmentForm2,delPackageBtn,gc,1,8,1,1);
		addObjects(shipmentForm2,clearCartBtn,gc,2,8,1,1);

		addObjects(shipmentForm2,clearCartBtn,gc,2,8,1,1);
		gc.anchor = GridBagConstraints.CENTER;
		
	



		/*END OF SHIPMENT FORM PAGE 2
		 * 
		 * 
		 * START OF SHIPMENT FORM PAGE 3*/
		gc = new GridBagConstraints();
		
		gc.insets = new Insets(10,10,10,10);
		gc.anchor = GridBagConstraints.CENTER;
		
		addObjects(shipmentForm3,lblCartSection,gc,0,0,3,1);
		addObjects(shipmentForm3,lblPaymentMethod,gc,0,1,1,1);
		addObjects(shipmentForm3,rdbCash,gc,1,1,1,1);
		addObjects(shipmentForm3,rdbCard,gc,2,1,1,1);


		
	}
	
	private void addPanelsTomainPanel()
	{
		
		shipmentPanel.add(shipmentForm1, "1");
		shipmentPanel.add(shipmentForm2,"2");
		shipmentPanel.add(shipmentForm3,"3");
		shipmentPanel.add(shipmentForm4,"4");
		mainPanel.add(shipmentPanel, "ShipmentForm");
		
		add(mainPanel);
		cardLayout.show(mainPanel, "ShipmentForm");
		
		
		
		/*

	     // add previousbtn in ActionListener
	     prevBtn.addActionListener(new ActionListener() 
	     {
	         public void actionPerformed(ActionEvent arg0)
	         {
	             // if condition apply
	             if (currentCard > 1) {

	                 // decrement the value 
	                 // of currentcard by 1
	                 currentCard -= 1;

	                 // show the value of currentcard
	                 cardLayout.show(mainPanel, "" + (currentCard));
	             }
	         }
	     });*/

		
		
	}
	
	private void registerListeners()
	{
		nextShipBtn1.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				//validation checks
				//mainPanel.show(shipmentForm1,"1");
				
				if(senderNameTxt.getText().compareTo("") == 0)
				{	
				        JOptionPane.showMessageDialog( 
				        		SwingUtilities.getWindowAncestor(mainPanel),
				        		 "One or two fields were not filled");
				   
				}
				 
				
				
				 // if condition apply
				else if (currentCard < 4) 
	             {
	                 
	                 // increment the value of currentcard by 1
	                 currentCard += 1;


	                 // show the value of currentcard
	                 cardLayout.show(shipmentPanel, "" + (currentCard));
	             }
			}
	
		});
	}
	
	private void setWindowsProperties()
	{
		
		//setResizable(false);
	
	}
	
	public ShipmentView()
	{
		setTitle("Smart Ship Project");
		setSize(1000,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
		initializeShipmentComponents();
		addShipmentComponentsToPanel();
		addPanelsTomainPanel();
		setWindowsProperties();
		registerListeners();
	}
	
	
	public void addObjects(JPanel panel, Component component, GridBagConstraints gbc, int column, int row,  int colspan, int rowspan)
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
	
	private int setZone()
	{
		Random zone = new Random();
		 
		return (zone.nextInt(4 - 1 + 1) + 1);
	}
	
	public static void main(String[] args)
	{
		new ShipmentView();
	}
	

}
