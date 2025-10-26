package view;

import model.Package;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

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
	private ButtonGroup btnGrpPackages,btnGrpLocation;
	private JButton clearBtn,btnZone, nextBtn,prevBtn,addToCartBtn, checkCartBtn, clearCartBtn, delPackageBtn;
	
	private JPanel cardPanel, shipmentForm1,shipmentForm2,shipmentForm3,shipmentForm4, buttonPanel;
	private JCheckBox checkBoxSE1,checkBoxSE2,checkBoxSE3,checkBoxSE4,checkBoxSE5,checkBoxSE6,checkBoxSE7,
	checkBoxF1,checkBoxF2,checkBoxF3,checkBoxF4,checkBoxF5,checkBoxF6,checkBoxF7;
	
	private String[] illegalItems = {"gun","knife","drug","money"};
	private int illegalItemsLength = illegalItems.length;
	
	private ArrayList<Package> defPackages;
	private GridBagConstraints gc;
    private int currentCard = 1;
    private CardLayout cl;
	
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
				
		
		nextBtn = new JButton("Next");
		prevBtn = new JButton("Previous");
		clearBtn = new JButton("Clear Form");
		
		shipmentForm1 = new JPanel(new GridBagLayout());
		shipmentForm2 = new JPanel(new GridBagLayout());
		shipmentForm3 = new JPanel(new GridBagLayout());
		shipmentForm4 = new JPanel(new GridBagLayout());
		
		buttonPanel = new JPanel();
		gc = new GridBagConstraints();	
		cl = new CardLayout();
		cardPanel = new JPanel(cl);
		
	}
	
	
	private void addShipmentComponentsToPanel()
	{
		//Padding of 10 all around
		gc.insets = new Insets(10,10,10,10);
		gc.anchor = GridBagConstraints.CENTER;
		gc.ipady = 5;
	
		
		//Add lblSenderSection: column 0 of row 0 with colspan of 5
		
		addObjects(shipmentForm1,lblSenderSection,gc,0,0,5,1);
		

		
		//Add lblSenderName: column 0 of row 1 with colspan of 1
		gc.anchor = GridBagConstraints.EAST;


		addObjects(shipmentForm1,lblSenderName,gc,0,1,1,1);
		
		
		


		addObjects(shipmentForm1,senderNameTxt,gc,1,1,1,1);

		
		addObjects(shipmentForm1,lblSenderAddr,gc,0,2,1,1);

		addObjects(shipmentForm1,senderAddrTxt,gc,1,2,2,1);

		
		//Add lblReceiverSection: column 0 of row 2 with colspan of 5
		
		gc.anchor = GridBagConstraints.CENTER;

		addObjects(shipmentForm1,lblReceiverSection,gc,0,3,5,1);

		//Add lblReceiverTRN: column 0 of row 3 with colspan of 1
		gc.anchor = GridBagConstraints.EAST;


		addObjects(shipmentForm1,lblReceiverTRN,gc,0,4,1,1);
		
		//Add receiverTRNTxt: column 1 of row 3 with colspan of 2
		
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



		/*END OF SHIPMENT FORM PAGE 2
		 * 
		 * 
		 * START OF SHIPMENT FORM PAGE 3*/

		gc = new GridBagConstraints();
		
		gc.insets = new Insets(10,10,10,10);
		gc.anchor = GridBagConstraints.CENTER;
		
		//addObjects(shipmentForm3,lblCartSection,gc,0,0,1);


		


		
		

		
		

		
		
	}
	
	private void addShipmentPanelsToCardPanel()
	{
		
		cardPanel.add(shipmentForm1, "1");
		cardPanel.add(shipmentForm2,"2");
		cardPanel.add(shipmentForm3,"3");
		cardPanel.add(shipmentForm4,"4");
		
		buttonPanel.add(nextBtn);
		buttonPanel.add(prevBtn);
		buttonPanel.add(clearBtn);
		
		 nextBtn.addActionListener(new ActionListener() 
	     {
	         public void actionPerformed(ActionEvent arg0)
	         {

	             // if condition apply
	             if (currentCard < 4) 
	             {
	                 
	                 // increment the value of currentcard by 1
	                 currentCard += 1;

	                 // show the value of currentcard
	                 cl.show(cardPanel, "" + (currentCard));
	             }
	         }
	     });

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
	                 cl.show(cardPanel, "" + (currentCard));
	             }
	         }
	     });
		 // used to get content pane
        getContentPane().add(cardPanel, BorderLayout.NORTH);

        // used to get content pane
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
	}
	
	private void setWindowsProperties()
	{
		setTitle("Smart Ship Project");
		setSize(1000,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		//setResizable(false);
	
	}
	
	public ShipmentView()
	{
		initializeShipmentComponents();
		addShipmentComponentsToPanel();
		addShipmentPanelsToCardPanel();
		setWindowsProperties();
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
	
	public static void main(String[] args)
	{
		new ShipmentView();
	}
	

}
