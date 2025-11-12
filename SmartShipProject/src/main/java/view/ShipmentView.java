package view;

import model.Shipment;

import java.awt.CardLayout;
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
	private JButton clearShipBtn1,zoneBtn,addToCartBtn, checkCartBtn, clearCartBtn, delPackageBtn, 
	nextShipBtn1,nextShipBtn2,prevShipBtn2;
	
	private JPanel mainPanel, orderPage1,shipmentForm2,shipmentForm3,shipmentForm4, buttonPanel,shipmentPanel;
	private JScrollPane scrollCartTable;
	private JTable cartTable;

	private String trn, senderName, senderAddr, receiverFName, receiverLName, zone, shippingLocation;
	private String[] illegalItems = {"gun","knife","drug","money",
									"corpse","organ","rifle","grenade","bomb"};
	private String[] packageCols = {"PackageID","PackageName", "Cost", "Weight", "Qty", "Remove"};
	private int illegalItemsLength = illegalItems.length;
	
	
	private ArrayList<Shipment> shipments;
	private GridBagConstraints gc;
    private int currentCard = 1, temp;
    private CardLayout cardLayout;
    private boolean exception = false, zoneChance = true;
	
	private void initializeShipmentComponents()
	{
		lblSenderSection = new JLabel("Sender Information Section");
		lblSenderSection.setFont(new Font("Verdana", Font.BOLD, 20));
		lblSenderName = new JLabel("Sender Name: ");
		senderNameTxt = new JTextField(30);
		lblSenderAddr = new JLabel("Sender Address: ");
		senderAddrTxt = new JTextField(50);
		
		lblReceiverSection = new JLabel("Receiver Information Section");
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
		zoneBtn = new JButton("Generate Zone");
		zoneTxt = new JTextField(5);
		zoneTxt.setEnabled(false);
		
		
		
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
		
		//nextBtn = new JButton("Next");
		//prevBtn = new JButton("Previous");
		clearShipBtn1 = new JButton("Clear Form");
		nextShipBtn1 = new JButton("Next");
		nextShipBtn2 = new JButton("Next");
		prevShipBtn2 = new JButton("Previous");
		orderPage1 = new JPanel(new GridBagLayout());
		shipmentForm2 = new JPanel(new GridBagLayout());
		shipmentForm3 = new JPanel(new GridBagLayout());
		shipmentForm4 = new JPanel(new GridBagLayout());
		
		buttonPanel = new JPanel();
		gc = new GridBagConstraints();	
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		shipmentPanel = new JPanel(cardLayout);

		
	
		
	
		
	}
	
	
	private void addShipmentComponentsToPanel()
	{
		//Padding of 10 all around
		gc.insets = new Insets(10,10,10,10);
		gc.anchor = GridBagConstraints.CENTER;
		gc.ipady = 5;
			
		addToGridBag(orderPage1,lblSenderSection,gc,0,0,5,1);
				
		gc.anchor = GridBagConstraints.EAST;


		addToGridBag(orderPage1,lblSenderName,gc,0,1,1,1);

		addToGridBag(orderPage1,senderNameTxt,gc,1,1,1,1);

		
		addToGridBag(orderPage1,lblSenderAddr,gc,0,2,1,1);

		addToGridBag(orderPage1,senderAddrTxt,gc,1,2,2,1);

		
		//Add lblReceiverSection: column 0 of row 2 with colspan of 5
		
		gc.anchor = GridBagConstraints.CENTER;

		addToGridBag(orderPage1,lblReceiverSection,gc,0,3,5,1);
		gc.anchor = GridBagConstraints.EAST;


		addToGridBag(orderPage1,lblReceiverTRN,gc,0,4,1,1);
				
		gc.anchor = GridBagConstraints.WEST;

		addToGridBag(orderPage1,receiverTRNTxt,gc,1,4,2,1);


		
		gc.anchor = GridBagConstraints.EAST;

		addToGridBag(orderPage1,lblReceiverFName,gc,0,5,1,1);
		gc.anchor = GridBagConstraints.WEST;

		addToGridBag(orderPage1,receiverFNameTxt,gc,1,5,1,1);
		gc.anchor = GridBagConstraints.EAST;

		addToGridBag(orderPage1,lblReceiverLName,gc,0,6,1,1);
		gc.anchor = GridBagConstraints.WEST;
		addToGridBag(orderPage1,receiverLNameTxt,gc,1,6,2,1);
		
		gc.anchor = GridBagConstraints.EAST;

		addToGridBag(orderPage1,lblReceiverAddr,gc,0,7,1,1);
		

		gc.anchor = GridBagConstraints.WEST;

		addToGridBag(orderPage1,receiverAddrTxt,gc,1,7,2,1);
		gc.anchor = GridBagConstraints.EAST;

		addToGridBag(orderPage1,lblshippingLocation,gc,0,8,1,1);
		gc.anchor = GridBagConstraints.WEST;

		addToGridBag(orderPage1,rdbLocal,gc,1,8,1,1);
		gc.anchor = GridBagConstraints.EAST;
		addToGridBag(orderPage1,rdbInternational,gc,1,8,1,1);
		gc.anchor = GridBagConstraints.WEST;


		gc.anchor = GridBagConstraints.EAST;

		addToGridBag(orderPage1,lblZone,gc,0,9,1,1);
		gc.anchor = GridBagConstraints.WEST;


		addToGridBag(orderPage1,zoneBtn,gc,1,9,1,1);
		
		gc.anchor = GridBagConstraints.EAST;

		addToGridBag(orderPage1,zoneTxt,gc,1,9,1,1);
		
		addToGridBag(orderPage1,nextShipBtn1, gc,0,10,1,1);
		addToGridBag(orderPage1,clearShipBtn1,gc,1,10,1,1);
		
		
		/*END OF SHIPMENT FORM PAGE 1
		 * 
		 * 
		 * START OF SHIPMENT FORM PAGE 2*/

		gc = new GridBagConstraints();
		
		gc.insets = new Insets(10,10,10,10);
		gc.anchor = GridBagConstraints.CENTER;
		

		addToGridBag(shipmentForm2,lblPackageSection,gc,0,0,4,1);
		
		
		gc.anchor = GridBagConstraints.EAST;

		addToGridBag(shipmentForm2,lblPackageType,gc,0,1,1,1);
		gc.anchor = GridBagConstraints.WEST;

		addToGridBag(shipmentForm2,rdbStandard,gc,1,1,1,1);
		addToGridBag(shipmentForm2,rdbExpress,gc,2,1,1,1);
		addToGridBag(shipmentForm2,rdbFragile,gc,3,1,1,1);
		
		gc.anchor = GridBagConstraints.EAST;

		addToGridBag(shipmentForm2,lblPackageName,gc,0,2,1,1);
		gc.anchor = GridBagConstraints.WEST;

		addToGridBag(shipmentForm2,packageNameTxt,gc,1,2,2,1);
		addToGridBag(shipmentForm2,lblWeight,gc,0,3,1,1);
		addToGridBag(shipmentForm2,weightTxt,gc,1,3,1,1);
		gc.anchor = GridBagConstraints.EAST;
		addToGridBag(shipmentForm2,lblPounds,gc,1,3,1,1);

		
		addToGridBag(shipmentForm2,lblDimensions,gc,0,4,1,1);
		gc.anchor = GridBagConstraints.WEST;
		addToGridBag(shipmentForm2,lengthTxt,gc,1,4,1,1);
		gc.anchor = GridBagConstraints.EAST;
		addToGridBag(shipmentForm2,lblLength,gc,1,4,1,1);
		gc.anchor = GridBagConstraints.WEST;
		addToGridBag(shipmentForm2,widthTxt,gc,1,5,1,1);
		gc.anchor = GridBagConstraints.EAST;
		addToGridBag(shipmentForm2,lblWidth,gc,1,5,1,1);
		gc.anchor = GridBagConstraints.WEST;
		addToGridBag(shipmentForm2,heightTxt,gc,1,6,1,1);
		gc.anchor = GridBagConstraints.EAST;
		addToGridBag(shipmentForm2,lblHeight,gc,1,6,1,1); 
		
		addToGridBag(shipmentForm2,lblCartOpt,gc,0,7,1,1);
		gc.anchor = GridBagConstraints.WEST;

		addToGridBag(shipmentForm2,addToCartBtn,gc,1,7,1,1);
		addToGridBag(shipmentForm2,checkCartBtn,gc,2,7,1,1);
		addToGridBag(shipmentForm2,delPackageBtn,gc,1,8,1,1);
		addToGridBag(shipmentForm2,clearCartBtn,gc,2,8,1,1);

		addToGridBag(shipmentForm2,clearCartBtn,gc,2,8,1,1);
		
		addToGridBag(shipmentForm2,nextShipBtn2,gc,0,9,1,1);
		addToGridBag(shipmentForm2,prevShipBtn2,gc,1,9,1,1);




		/*END OF SHIPMENT FORM PAGE 2
		 * 
		 * 
		 * START OF SHIPMENT FORM PAGE 3*/
		gc = new GridBagConstraints();
		
		gc.insets = new Insets(10,10,10,10);
		gc.anchor = GridBagConstraints.CENTER;
		
		addToGridBag(shipmentForm3,lblCartSection,gc,0,0,3,1);
		addToGridBag(shipmentForm3,lblPaymentMethod,gc,0,1,1,1);
		addToGridBag(shipmentForm3,rdbCash,gc,1,1,1,1);
		addToGridBag(shipmentForm3,rdbCard,gc,2,1,1,1);


		
	}
	
	private void addPanelsToMainPanel()
	{
		
		shipmentPanel.add(orderPage1, "1");
		shipmentPanel.add(shipmentForm2,"2");
		shipmentPanel.add(shipmentForm3,"3");
		shipmentPanel.add(shipmentForm4,"4");
		mainPanel.add(shipmentPanel, "ShipmentForm");
		
		add(mainPanel);
		cardLayout.show(mainPanel, "ShipmentForm");
		
		
		
		

		
		
	}
	
	private void registerListeners()
	{
		
		zoneBtn.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) 
					{
						if(zoneChance == true)
						{
							String x = String.valueOf(setZone());
							zoneTxt.setText(x);	
							zoneChance = false;
						}
					}
				}
				);
		
		
		nextShipBtn1.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				exception = false;
				//validation checks
				boolean flag1 = rdbLocal.isSelected();
				boolean flag2 = rdbInternational.isSelected();
				
				if(senderNameTxt.getText().compareTo("") == 0 || senderAddrTxt.getText().compareTo("") == 0
						|| receiverTRNTxt.getText().compareTo("") == 0
						|| receiverFNameTxt.getText().compareTo("") == 0 || receiverLNameTxt.getText().compareTo("") == 0 ||
						receiverAddrTxt.getText().compareTo("") == 0 ||
						zoneTxt.getText().compareTo("") == 0 
					|| flag1 == false && flag2 == false)
				{
						JOptionPane.showMessageDialog( 
				        		SwingUtilities.getWindowAncestor(mainPanel),
				        		 "One or two fields were not filled");
				}
				else
				{
					senderName = senderNameTxt.getText();
					senderAddr = senderAddrTxt.getText();
					receiverFName = receiverFNameTxt.getText();
					receiverLName = receiverLNameTxt.getText();
					trn = receiverTRNTxt.getText();
					if(trn.length() != receiverTRNTxt.getColumns())
					{
						JOptionPane.showMessageDialog( 
				        		SwingUtilities.getWindowAncestor(mainPanel),
				        		 "TRN must be exactly 9 digits in the format: 123456789");
						return;
					}
					else
					
					try
					{
						//Temporary assigns temp to the converted number of TextField from receiverTRNTxt
						//to check that all values are number
						temp = Integer.parseInt(receiverTRNTxt.getText());

					}catch(NumberFormatException nf)
					{
						JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(mainPanel),"Invalid Format for TRN field\n"
								+ "Must be in format: 123456789");
						exception = true;
					}

					if(exception == false)
					{


		                 // show the value of currentcard
		                 cardLayout.show(shipmentPanel, "2");
					}
					
				}
				
				
			}
	
		});
		
		clearShipBtn1.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						senderNameTxt.setText(null);
						senderAddrTxt.setText(null);
						receiverFNameTxt.setText(null);
						receiverLNameTxt.setText(null);
						receiverAddrTxt.setText(null);
						zoneTxt.setText(null);
						receiverTRNTxt.setText(null);
						btnGrpLocation.clearSelection();
						zoneChance = true;

					}
			
				});
		
		nextShipBtn2.addActionListener(new ActionListener() {

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
		addPanelsToMainPanel();
		writeTestData();
		setWindowsProperties();
		registerListeners();
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
	
	private int setZone()
	{
		Random zone = new Random();
		 
		return (zone.nextInt(4 - 1 + 1) + 1);
	}
	
	public static void main(String[] args)
	{
		new ShipmentView();
	}
	
	private void writeTestData()
	{
		senderNameTxt.setText("Amazon");
		senderAddrTxt.setText("1 Cherry Lane");
		receiverFNameTxt.setText("Chevannese");
		receiverLNameTxt.setText("Ellis");
		receiverAddrTxt.setText("7 Constant Spring Road");
		zoneTxt.setText("1");
		receiverTRNTxt.setText("123456789");
		rdbLocal.doClick();
	}
	
	
	

}
