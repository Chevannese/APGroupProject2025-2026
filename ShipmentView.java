package view;

import java.awt.Button;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class ShipmentView extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblSenderSection, lblSenderName, lblSenderAddr, 
	lblReceiverSection, lblReceiverName,lblReceiverAddr,
	lblPkgeSection, lblPkgeName,lblPkgeType,
	lblPkgeWeight, lblPkgeWidth, lblPkgeLength, lblPackageType, lblDestination;
	private JTextField senderAddrTxt, txtReceipient, txtWeight, txtWidth, txtLength;
	
	private JRadioButton rdbAmazon, rdbEbay,rdbTemu,rdbSheen;
	private ButtonGroup btnGrpStores;
	private JComboBox cbxType;
	private Button btnSubmit,btnClear;
	
	
	
	private void initializeComponents()
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
		//lblReceiverTrn = new JLabel("Tax Registration Number: ");
		lblReceiverName = new JLabel("Receiver Name: ");
		lblReceiverAddr = new JLabel("Receiver Address: ");
		
		lblPkgeSection = new JLabel("Package Information Section");
		lblPkgeType = new JLabel("Package Type: ");
		String[] type = {"Standard","Express", "Fragile"};
		cbxType = new JComboBox<String>(type);
		lblPkgeName = new JLabel("Package Name: ");
		//This part should be auto filled based on package type
		lblPkgeWeight = new JLabel("Package Weight: ");
		lblPkgeWidth = new JLabel("Width");
		lblPkgeLength = new JLabel("Length");
		
		
		
		
		
		
		
		
		
		
		
	}
	

}
