package view;

import model.Package;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
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
	lblReceiverSection, lblReceiverTRN, lblReceiverName,lblReceiverAddr,
	lblZone,
	lblPackageSection, lblPackageName,lblPackageType,
	lblCartSection, lblCart, lblPaymentMethod, lblItem, lblPrice,
	lblQty, lblWeight,lblTotalWeight,lblSubTotal,lblSurcharge,lblDiscount,lblGrandTotal;
	private JTextField senderAddrTxt, 
	receiverTRNTxt,receiverFNameTxt,receiverLNameTxt, receiverAddrTxt,zoneTxt
	;
	
	private JRadioButton rdbAmazon, rdbEbay,rdbTemu,rdbSheen,
	rdbStandard, rdbExpress,rdbFragile,
	rdbCash,rdbCard;
	private ButtonGroup btnGrpStores,btnGrpPackages;
	private Button clearBtn,btnZone, nextBtn,prevBtn;
	
	private JPanel cardPanel, shipmentForm1,shipmentForm2,shipmentForm3,shipmentForm4, buttonPanel;
	private JCheckBox checkBoxSE1,checkBoxSE2,checkBoxSE3,checkBoxSE4,checkBoxSE5,checkBoxSE6,checkBoxSE7,
	checkBoxF1,checkBoxF2,checkBoxF3,checkBoxF4,checkBoxF5,checkBoxF6,checkBoxF7;
	
	
	private ArrayList<Package> defPackages;
	private GridBagConstraints gc;
    private int currentCard = 1;
    private CardLayout cl;
	
	private void initializeShipmentComponents()
	{
		lblSenderSection = new JLabel("Sender Information Section");
		lblSenderName = new JLabel("Sender Name: ");
		rdbAmazon = new JRadioButton("Amazon");
		rdbEbay = new JRadioButton("Ebay");
		rdbTemu = new JRadioButton("Temu");
		rdbSheen = new JRadioButton("Sheen");
		btnGrpStores = new ButtonGroup();
		btnGrpStores.add(rdbAmazon);
		btnGrpStores.add(rdbEbay);
		btnGrpStores.add(rdbTemu);
		btnGrpStores.add(rdbSheen);
		lblSenderAddr = new JLabel("Sender Address: ");
		senderAddrTxt = new JTextField(20);
		
		//This section should be auto filled based on User Information
		lblReceiverSection = new JLabel("Receiver Information Section");
		lblReceiverTRN = new JLabel("Tax Registration Number: ");
		receiverTRNTxt = new JTextField(9);
		lblReceiverName = new JLabel("Receiver Name: ");
		receiverFNameTxt = new JTextField(20);
		receiverLNameTxt = new JTextField(20);
		lblReceiverAddr = new JLabel("Receiver Address: ");
		receiverAddrTxt = new JTextField(20);
		
		lblZone = new JLabel("Zone: ");
		btnZone = new Button("Generate Zone");
		zoneTxt = new JTextField(5);
		
		
		
		lblPackageSection = new JLabel("Package Information Section");
		
		
		lblPackageType = new JLabel("Package Type: ");
		
		
		rdbStandard = new JRadioButton("Standard");
		rdbExpress = new JRadioButton("Express");
		rdbFragile = new JRadioButton("Fragile");
		btnGrpPackages = new ButtonGroup();
		btnGrpPackages.add(rdbStandard);
		btnGrpPackages.add(rdbExpress);
		btnGrpPackages.add(rdbFragile);
		
		lblPackageName = new JLabel("Package Name: ");
		
		checkBoxSE1 = new JCheckBox("Naruto Manga");
		checkBoxSE2 = new JCheckBox("Sherlock Holmes Book");
		checkBoxSE3 = new JCheckBox("Laptop Backpack");
		checkBoxSE4 = new JCheckBox("Uno Card Game");
		checkBoxSE5 = new JCheckBox("Calculator");
		checkBoxSE6 = new JCheckBox("Brazilian Hair");
		checkBoxSE7 = new JCheckBox("Pillow");
		
		checkBoxF1 = new JCheckBox("Mona Lisa Poster");
		checkBoxF2 = new JCheckBox("iPhone 17");
		checkBoxF3 = new JCheckBox("Guitar");
		checkBoxF4 = new JCheckBox("Roku Smart TV");
		checkBoxF5 = new JCheckBox("ASUS Laptop");
		checkBoxF6 = new JCheckBox("PlayStation 5");
		checkBoxF7 = new JCheckBox("Stanley Cup");
		
		

		defPackages = new ArrayList<Package>();
		//defPackages.add(new Package("Mona Lisa Poster","Fragile",));
		
		
		
		defPackages.add(new Package("ASUS Laptop","Fragile",3.64,12.34,0.57,8.58));
		
		
		nextBtn = new Button("Next");
		prevBtn = new Button("Previous");
		clearBtn = new Button("Clear Form");
		
		shipmentForm1 = new JPanel(new GridBagLayout());
		shipmentForm2 = new JPanel(new GridBagLayout());
		shipmentForm3 = new JPanel(new GridBagLayout());
		shipmentForm4 = new JPanel(new GridBagLayout());
		
		buttonPanel = new JPanel();
		gc = new GridBagConstraints();	
		cl = new CardLayout();
		cardPanel = new JPanel(cl);
		
	}
	
	
	private void addComponentsToPanel()
	{
		//Padding of 10 all around
		gc.insets = new Insets(10,10,10,10);
		gc.anchor = GridBagConstraints.CENTER;
		gc.ipady = 10;
	
		
		//Add lblSenderSection: column 0 of row 0 with colspan of 5
		
		addObjects(shipmentForm1,lblSenderSection,gc,0,0,5);
		

		
		//Add lblSenderName: column 0 of row 1 with colspan of 1
		gc.anchor = GridBagConstraints.EAST;


		addObjects(shipmentForm1,lblSenderName,gc,0,1,1);
		
		
		//Add rdbAmazon: column 1 of row 1 with colspan of 1
		


		addObjects(shipmentForm1,rdbAmazon,gc,1,1,1);
		
		//Add rdbEbay: column 2 of row 1 with colspan of 1

		addObjects(shipmentForm1,rdbEbay,gc,2,1,1);

		//Add rdbTemu: column 3 of row 1 with colspan of 1

		addObjects(shipmentForm1,rdbTemu,gc,3,1,1);

		//Add rdbSheen: column 3 of row 1 with colspan of 1

		addObjects(shipmentForm1,rdbSheen,gc,4,1,1);
		
		addObjects(shipmentForm1,lblSenderAddr,gc,0,2,1);

		addObjects(shipmentForm1,senderAddrTxt,gc,1,2,2);

		
		//Add lblReceiverSection: column 0 of row 2 with colspan of 5
		
		gc.anchor = GridBagConstraints.CENTER;

		addObjects(shipmentForm1,lblReceiverSection,gc,0,3,5);

		//Add lblReceiverTRN: column 0 of row 3 with colspan of 1
		gc.anchor = GridBagConstraints.EAST;


		addObjects(shipmentForm1,lblReceiverTRN,gc,0,4,1);
		
		//Add receiverTRNTxt: column 1 of row 3 with colspan of 2
		
		gc.anchor = GridBagConstraints.WEST;

		addObjects(shipmentForm1,receiverTRNTxt,gc,1,4,2);


		//Add lblReceiverName: column 0 of row 4 with colspan of 1
		
		gc.anchor = GridBagConstraints.EAST;

		addObjects(shipmentForm1,lblReceiverName,gc,0,5,1);
		
		addObjects(shipmentForm1,receiverFNameTxt,gc,1,5,2);
		
		addObjects(shipmentForm1,receiverLNameTxt,gc,2,5,2);
		
		//Add lblReceiverAddr: column 0 of row 5 with colspan of 1
		
		addObjects(shipmentForm1,lblReceiverAddr,gc,0,6,1);
		
		//Add receiverAddrTxt: column 1 of row 5 with colspan of 2

		addObjects(shipmentForm1,receiverAddrTxt,gc,1,6,2);
		
		//Add lblZone: column 0 of row 6 with colspan of 1

		
		addObjects(shipmentForm1,lblZone,gc,0,7,1);
		
		//Add btnZone: column 1 of row 6 with colspan of 1

		addObjects(shipmentForm1,btnZone,gc,1,7,1);
		
		//Add zoneTxt: column 2 of row 6 with colspan of 1
		
		addObjects(shipmentForm1,zoneTxt,gc,2,7,1);
		
		/*END OF SHIPMENT FORM PAGE 1
		 * 
		 * 
		 * START OF SHIPMENT FORM PAGE 2*/

		gc = new GridBagConstraints();
		
		gc.insets = new Insets(10,10,10,10);
		gc.anchor = GridBagConstraints.CENTER;
		

		//Add lblPackageSection: column 0 of row 0 with colspan of 4

		addObjects(shipmentForm2,lblPackageSection,gc,0,0,4);
		
		
		gc.anchor = GridBagConstraints.EAST;

		addObjects(shipmentForm2,lblPackageType,gc,0,1,1);
		gc.anchor = GridBagConstraints.WEST;

		addObjects(shipmentForm2,rdbStandard,gc,1,1,1);
		addObjects(shipmentForm2,rdbExpress,gc,2,1,1);
		addObjects(shipmentForm2,rdbFragile,gc,3,1,1);
		addObjects(shipmentForm2,rdbFragile,gc,3,1,1);
		
		gc.anchor = GridBagConstraints.EAST;

		addObjects(shipmentForm2,lblPackageName,gc,0,2,1);
		gc.anchor = GridBagConstraints.WEST;

		addObjects(shipmentForm2,checkBoxSE1,gc,1,2,1);
		addObjects(shipmentForm2,checkBoxSE2,gc,1,3,1);
		addObjects(shipmentForm2,checkBoxSE3,gc,1,4,1);
		addObjects(shipmentForm2,checkBoxSE4,gc,1,5,1);
		addObjects(shipmentForm2,checkBoxSE5,gc,1,6,1);
		addObjects(shipmentForm2,checkBoxSE6,gc,1,7,1);
		addObjects(shipmentForm2,checkBoxSE7,gc,1,8,1);

		addObjects(shipmentForm2,checkBoxF1,gc,2,2,1);
		addObjects(shipmentForm2,checkBoxF2,gc,2,3,1);
		addObjects(shipmentForm2,checkBoxF3,gc,2,4,1);
		addObjects(shipmentForm2,checkBoxF4,gc,2,5,1);
		addObjects(shipmentForm2,checkBoxF5,gc,2,6,1);
		addObjects(shipmentForm2,checkBoxF6,gc,2,7,1);
		addObjects(shipmentForm2,checkBoxF7,gc,2,8,1);



		/*END OF SHIPMENT FORM PAGE 2
		 * 
		 * 
		 * START OF SHIPMENT FORM PAGE 3*/

		gc = new GridBagConstraints();
		
		gc.insets = new Insets(10,10,10,10);
		gc.anchor = GridBagConstraints.CENTER;
		
		addObjects(shipmentForm3,lblCartSection,gc,0,0,1);


		


		
		

		
		

		
		
	}
	
	private void addPanelsToCardPanel()
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
		setSize(700,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	
	}
	
	public ShipmentView()
	{
		initializeShipmentComponents();
		addComponentsToPanel();
		addPanelsToCardPanel();
		setWindowsProperties();
	}
	
	
	public void addObjects(JPanel panel, Component component, GridBagConstraints gbc, int column, int row,  int colspan)
	{

	if (panel == null ) {
	        System.err.println("Panel or Component is null!");
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
        
        
        panel.add(component,gbc);
    }
	
	public static void main(String[] args)
	{
		new ShipmentView();
	}
	

}
