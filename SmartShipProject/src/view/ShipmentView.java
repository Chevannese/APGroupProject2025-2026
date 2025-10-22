package view;

import model.Package;

import java.awt.Button;
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
	private Button btnSubmit,btnClear,btnZone;
	
	private JPanel cardPanel, shipmentForm1,shipmentForm2,shipmentForm3,shipmentForm4;
	private JCheckBox checkBoxSE1,checkBoxSE2,checkBoxSE3,checkBoxSE4,checkBoxSE5,
	checkBoxF1,checkBoxF2,checkBoxF3,checkBoxF4,checkBoxF5;
	
	private ArrayList<Package> defPackages;
	
	private void initializeShipmentComponents()
	{
		lblSenderSection = new JLabel("Sender Information Section");
		lblSenderName = new JLabel("Sender Name: ");
		rdbAmazon = new JRadioButton("Amazon");
		rdbEbay = new JRadioButton("Ebay");
		rdbTemu = new JRadioButton("Temu");
		rdbSheen = new JRadioButton("Sheen");
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
		btnGrpPackages.add(rdbStandard);
		btnGrpPackages.add(rdbExpress);
		btnGrpPackages.add(rdbFragile);
		
		lblPackageName = new JLabel("Package Name: ");
		
		checkBoxSE1 = new JCheckBox("Stanley Cup");
		checkBoxSE2 = new JCheckBox("Sherlock Holmes Book");
		checkBoxSE3 = new JCheckBox("Laptop Backpack");
		checkBoxSE4 = new JCheckBox("Uno Card Game");
		checkBoxSE5 = new JCheckBox("");

		defPackages = new ArrayList<Package>();
		//defPackages.add(new Package("Mona Lisa Poster","Fragile",));
		
		defPackages.add(new Package("ASUS Laptop","Fragile",3.64,12.34,0.57,8.58));
		
		
		

		
		
	}
	

}
