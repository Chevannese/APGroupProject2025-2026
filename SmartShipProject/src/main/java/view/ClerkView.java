package view;

import model.Clerk;
import javax.swing.*;
import javax.swing.event.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.sqm.tuple.internal.AnonymousTupleSqmAssociationPathSourceNew;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

import com.formdev.flatlaf.*;

import model.Shipment;
import model.User;
import network.Client;

public class ClerkView extends TabView implements ActionListener, PopupMenuListener, ComponentListener, KeyListener {
    private static final long serialVersionUID = -4757134542607256811L;
    private JPanel shipmentPanel;
    private JPanel accountInfoPanel;

    JCheckBox shipmentFilter;
    JTextField shipmentSearch;
    JButton loadShipmentsButton;
    JPanel shipmentList;
    Client client;
    User clerk;
    List<Shipment> shipments;
    private static final Logger logger = LogManager.getLogger(ClerkView.class);
 
    public ClerkView(User loggedInUser) {
    	this();
    	this.clerk = loggedInUser;
    }
    
    public ClerkView() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        
        this.initialiseComponents();
        this.addActionListeners();
        this.setVisible(true);
    }
    
    private void initialiseComponents() {
        // main sections
        shipmentPanel = new JPanel(new BorderLayout());
        JPanel shipmentTop = new JPanel();
        shipmentSearch = new JTextField();
        loadShipmentsButton = new JButton("(Re)Load Shipment Orders");
        shipmentFilter = new JCheckBox("Incomplete");
        shipmentTop.add(new JLabel("Search: "));
        shipmentTop.add(shipmentSearch);
        shipmentTop.add(addToPanel(loadShipmentsButton, shipmentFilter));
        //shipmentTop.setMinimumSize(new Dimension(70, 70));

        shipmentList = new JPanel();
        shipmentList.setLayout(new BoxLayout(shipmentList, BoxLayout.Y_AXIS));

        shipmentPanel.add(shipmentTop, BorderLayout.NORTH);
        //shipmentPanel.add(shipmentSearch, BorderLayout.NORTH);
        shipmentPanel.add(new JScrollPane(shipmentList), BorderLayout.CENTER);
        //mainPanel.add(new JScrollPane(new JLabel(this.clerk.toString())), "accounts");
        
        accountInfoPanel = new JPanel();
        if (clerk != null)
        	accountInfoPanel.add(new JLabel(clerk.toString()));
        
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
    
    @Override public void actionPerformed(ActionEvent e) {
    	String action = e.getActionCommand();
    	System.out.println("Action: " + action);

    	if (action.equals("load-shipments")) {
    		this.loadShipments();
    	}
    	if (action.equals("filter-shipments")) {
    		this.populateShipmentList();
    	}
	}
    
    private void populateShipmentList() {
    	if (shipments == null) {
    		return;
    	}
    	
    	// clear list
    	shipmentList.removeAll();
    	
    	for (Shipment s : shipments) {
    		if (s.getStatus().equalsIgnoreCase("pending") && !shipmentFilter.isSelected()) {
    			continue;
    		}
    		
    		String search = shipmentSearch.getText();
    		if (!shipmentSearch.getText().isBlank()) {
    			if (!s.getPackageName().equalsIgnoreCase(search)
    					|| !s.getReceiverName().equalsIgnoreCase(search)
    					|| !s.getDestination().equalsIgnoreCase(search)
    					|| !s.getSupplierName().equalsIgnoreCase(search)
    					|| !s.getCustID().equalsIgnoreCase(search)
    					|| !s.getSupplierAddr().equalsIgnoreCase(search) ||
    					!s.getPackageType().equalsIgnoreCase(search)) {
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
    }
    
    private void loadShipments() {
    	shipments = null;
    	
    	SwingWorker<List<Shipment>, Integer> worker = new SwingWorker<List<Shipment>, Integer>() {
			@Override protected List<Shipment> doInBackground() throws Exception {
				return new Client().getShipments();
			}
			
			@Override protected void done() {
				try {
					System.out.println("Done getting shipments");
					shipments = get();
					System.out.println(shipments);
					populateShipmentList();
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Failed to load shipments", "Error", JOptionPane.ERROR_MESSAGE);
					logger.error("Failed to load shipments");
				}
			}
		};
		
		worker.execute();
    }
    
    private void processOrder(Shipment shipment) {
		JOptionPane.showConfirmDialog(this, shipment);
	}

	@Override public void keyTyped(KeyEvent e) { populateShipmentList(); }
	@Override public void keyPressed(KeyEvent e) {}
	@Override public void keyReleased(KeyEvent e) {}
	
	public static void main(String[] args) {
		// Run the GUI on the Event Dispatch Thread (EDT)
		FlatLightLaf.setup();
		
		SwingUtilities.invokeLater(() -> {
			new ClerkView();
		});
	}
    
}