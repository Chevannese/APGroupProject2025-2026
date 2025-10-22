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
	lblPackageSection, lblPackageName,lblPackgeType,
	lblPkgeWeight, lblPkgeWidth, lblPkgeLength, lblPackageType, lblDestination;
	private JTextField senderAddrTxt, 
	receiverTRNTxt,receiverFNameTxt,receiverLNameTxt, receiverAddrTxt,zoneTxt;
	
	private JRadioButton rdbAmazon, rdbEbay,rdbTemu,rdbSheen,
	rdbStandard, rdbExpress,rdbFragile;
	private ButtonGroup btnGrpStores,btnGrpPackages;
	private Button btnSubmit,btnClear,btnZone, nextBtn,prevBtn;
	
	private JPanel cardPanel, shipmentForm1,shipmentForm2,shipmentForm3,shipmentForm4, buttonPanel;
	private JCheckBox checkBoxSE1,checkBoxSE2,checkBoxSE3,checkBoxSE4,checkBoxSE5,checkBoxSE6,checkBoxSE7,
	checkBoxF1,checkBoxF2,checkBoxF3,checkBoxF4,checkBoxF5,checkBoxF6,checkBoxF7;
	
	
	private ArrayList<Package> defPackages;
	private GridBagLayout gridBagLayout;
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
		senderAddrTxt = new JTextField();
		
		//This section should be auto filled based on User Information
		lblReceiverSection = new JLabel("Receiver Information Section");
		lblReceiverTRN = new JLabel("Tax Registration Number: ");
		receiverTRNTxt = new JTextField(9);
		lblReceiverName = new JLabel("Receiver Name: ");
		receiverFNameTxt = new JTextField(30);
		receiverLNameTxt = new JTextField(30);
		lblReceiverAddr = new JLabel("Receiver Address: ");
		receiverAddrTxt = new JTextField(50);
		
		lblZone = new JLabel("Zone: ");
		btnZone = new Button("Generate Zone");
		zoneTxt = new JTextField(2);
		
		
		
		lblPackageSection = new JLabel("Package Information Section");
		lblPackgeType = new JLabel("Package Type: ");
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
		
		//defPackages.add(new Package("ASUS Laptop","Fragile",3.64,12.34,0.57,8.58));
		
		nextBtn = new Button("Next");
		prevBtn = new Button("Previous");
		
		shipmentForm1 = new JPanel(new GridBagLayout());
		shipmentForm2 = new JPanel(new GridBagLayout());
		shipmentForm3 = new JPanel(new GridBagLayout());
		shipmentForm4 = new JPanel(new GridBagLayout());
		
		//gridBagLayout = new GridBagLayout();
		buttonPanel = new JPanel();
		gc = new GridBagConstraints();	
		cl = new CardLayout();
		cardPanel = new JPanel(cl);
		
	}
	
	
	private void addComponentsToPanel()
	{
		
		gc.insets = new Insets(10,10,10,10);
		gc.anchor = GridBagConstraints.CENTER;
		
		
		gc.gridwidth = 5;
		gc.gridx = 0;
		gc.gridy = 0;
		
		
		shipmentForm1.add(lblSenderSection,gc);
		
		gc.anchor = GridBagConstraints.WEST;

		gc.gridwidth = 1;
		gc.weightx = 1;
		gc.gridx = 0;
		gc.gridy = 1;
		
		
		shipmentForm1.add(lblSenderName,gc);
		
		
		gc.gridx = 1;
		gc.gridy = 1;
		
		shipmentForm1.add(rdbAmazon,gc);
		
		gc.gridx = 2;
		gc.gridy = 1;
		
		shipmentForm1.add(rdbEbay,gc);
		
		gc.gridx = 3;
		gc.gridy = 1;
		
		shipmentForm1.add(rdbTemu,gc);
		
		gc.gridx = 4;
		gc.gridy = 1;
		
		shipmentForm1.add(rdbSheen,gc);
		
		//gc.gridx = 
		
		
	}
	
	private void addPanelsToCardPanel()
	{
		
		cardPanel.add(shipmentForm1, "1");
		cardPanel.add(shipmentForm2,"2");
		cardPanel.add(shipmentForm3,"3");
		cardPanel.add(shipmentForm4,"4");
		
		buttonPanel.add(nextBtn);
		buttonPanel.add(prevBtn);
		
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
	
	
	public void addObjects(GridBagLayout layout, Container panel, Component component, GridBagConstraints gbc, int column, int row,  int colspan)
	{

        gbc.gridx = column;
        gbc.gridy = row;

        gbc.weightx = colspan;
        
        layout.setConstraints(component, gbc);
        panel.add(component);
    }
	
	public static void main(String[] args)
	{
		new ShipmentView();
	}
	

}
