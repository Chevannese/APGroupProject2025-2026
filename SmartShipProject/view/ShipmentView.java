package view;

import java.awt.Button;

import javax.swing.ButtonGroup;
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
	private JComboBox cbxType;
	private Button btnSubmit,btnClear,btnZone;
	
	private JPanel cardPanel, shipmentForm1,shipmentForm2,shipmentForm3,shipmentForm4;
	
	
	
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
		String[] type = {"Standard","Express", "Fragile"};
		rdbStandard = new JRadioButton("Standard");
		rdbExpress = new JRadioButton("Express");
		rdbFragile = new JRadioButton("Fragile");
		btnGrpPackages.add(rdbStandard);
		btnGrpPackages.add(rdbExpress);
		btnGrpPackages.add(rdbFragile);
		
		lblPackageName = new JLabel("Package Name: ");
		//This part should be auto filled based on package type

		
		
	}
	

}
