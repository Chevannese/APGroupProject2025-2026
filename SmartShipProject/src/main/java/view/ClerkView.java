package view;

import model.*;
import javax.swing.*;
import javax.swing.event.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import com.formdev.flatlaf.*;

import network.Client;

public class ClerkView extends TabView implements ActionListener, PopupMenuListener, ComponentListener, KeyListener {
    private static final long serialVersionUID = -4757134542607256811L;
    private JPanel shipmentPanel = new JPanel(new BorderLayout());;
    private JPanel accountInfoPanel = new JPanel();

    JCheckBox shipmentFilter = new JCheckBox("Incomplete");
    JTextField shipmentSearch = new JTextField();;
    JButton loadShipmentsButton = new JButton("(Re)Load Shipment Orders");;
    JPanel shipmentList = new JPanel();

    User clerk;
    List<Shipment> shipments;
    private static final Logger logger = LogManager.getLogger(ClerkView.class);
    
	public static void main(String[] args) {
		// Run the GUI on the Event Dispatch Thread (EDT)
		FlatLightLaf.setup();
		
		SwingUtilities.invokeLater(() -> {
			new ClerkView();
		});
	}
 
    public ClerkView(User loggedInUser) {
    	this();
    	this.clerk = loggedInUser;
    }
    
    public ClerkView() {
        super();
        
        this.initialiseComponents();
        this.addActionListeners();
        this.setVisible(true);
    }
    
    private void initialiseComponents() {
        shipmentList.setLayout(new BoxLayout(shipmentList, BoxLayout.Y_AXIS));

		shipmentPanel.add(addToPanel(new JLabel("Search: "), shipmentSearch, addToPanel(loadShipmentsButton, shipmentFilter)), BorderLayout.NORTH);
		shipmentPanel.add(new JScrollPane(shipmentList), BorderLayout.CENTER);

		// mainPanel.add(new JScrollPane(new JLabel(this.clerk.toString())), "accounts");
        
        if (clerk != null) {
        	accountInfoPanel.add(new JLabel(clerk.toString()));
        }
        
        addTab("Shipment Management", shipmentPanel);
        addTab("Accounts", accountInfoPanel);
    }
    
    private JPanel addToPanel(Component ...components) {
    	JPanel panel = new JPanel();
    	
    	for (Component component : components) {
    		panel.add(component);
    	}
    	
		return panel;
	}

    
    public void addActionListeners() {
    	loadShipmentsButton.addActionListener(this);
        shipmentFilter.addActionListener(this);
        shipmentSearch.addKeyListener(this);
        
        loadShipmentsButton.setActionCommand("load-shipments");
        shipmentFilter.setActionCommand("filter-shipments");
    }
    
    public void generateInvoice() {
    	
    }
    
    @Override public void actionPerformed(ActionEvent e) {
    	String action = e.getActionCommand();
    	System.out.println("Action: " + action);

    	if (action.equals("load-shipments")) {
    		this.loadShipments();
		}
    	if (action.equals("filter-shipments")) {
    		this.populateShipmentList();
    	}
    	if (action.equals("")) {
    		
    	}
	}
    

private void populateShipmentList() {
    if (shipments == null) {
        return;
    }

    // Clear old panels
    shipmentList.removeAll();

    String search = shipmentSearch.getText().trim().toLowerCase();

    for (Shipment s : shipments) {
        // Filter by checkbox
        if (s.getStatus().equalsIgnoreCase("pending") && !shipmentFilter.isSelected()) {
            continue;
        }

        // Filter by search
        if (!search.isBlank()) {
            boolean matches = s.getPackageName().toLowerCase().contains(search)
                    || s.getReceiverName().toLowerCase().contains(search)
                    || s.getDestination().toLowerCase().contains(search)
                    || s.getSupplierName().toLowerCase().contains(search)
                    || s.getCustID().toLowerCase().contains(search)
                    || s.getSupplierAddr().toLowerCase().contains(search)
                    || s.getPackageType().toLowerCase().contains(search);

            if (!matches) {
                continue;
            }
        }

        JPanel panel = new JPanel(new BorderLayout());
        JPanel leftJPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel id = new JLabel(s.getPackageNo().toString());
        JLabel name = new JLabel(s.getPackageName());
        JButton button = new JButton("Open");

        leftJPanel.add(id);
        leftJPanel.add(name);

        panel.add(leftJPanel, BorderLayout.CENTER);
        panel.add(button, BorderLayout.EAST);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        button.addActionListener(e -> processOrder(s));

        shipmentList.add(panel);
    }

    shipmentList.revalidate();
    shipmentList.repaint();
}

    private void loadShipments() {
    	shipments = null;
    	try {
			shipments = new Client().getShipments();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		populateShipmentList();
    }
    
    private void processOrder(Shipment shipment) {
    	JDialog dialog = new JDialog(this, "Package", true);
    	JPanel panel = new JPanel(new BorderLayout());
    	JTable packageInfoTable = new JTable(
    		new Object[][] { new Object[] { shipment.getPackageNo(), shipment.getPackageName(), shipment.getPackageType() }},
    		new Object[] { "Package No.", "Package Name", "Package Type" }
    	);
    	
    	JButton close = new JButton("Close");
    	JButton addPayment = new JButton("Add payment");
    	JButton assignVehicle = new JButton("Assign Vehicle");
    	
    	addPayment.addActionListener(e -> addPayment(shipment));
    	assignVehicle.addActionListener(e -> assignVehicle(shipment));
    	
    	addPayment.setEnabled(shipment.getStatus().equals("Pending"));
    	//assignVehicle.eventEnabled(shipment.getPackageNo()); // TODO get assignment to match
    	panel.add(addToPanel(close, addPayment, assignVehicle));
    	
    	dialog.setVisible(true);
	}
    
    private Object addPayment(Shipment shipment) {
		// TODO Auto-generated method stub
		return null;
	}

	public void assignVehicle(Shipment shipment) {
    	List<Vehicle> vehicles;
    	JDialog dialog = new JDialog();
	}

	@Override public void keyTyped(KeyEvent e) { populateShipmentList(); }
	@Override public void keyPressed(KeyEvent e) {}
	@Override public void keyReleased(KeyEvent e) {}
}