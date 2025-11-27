package view;

import model.Clerk;
import javax.swing.*;
import javax.swing.event.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import com.formdev.flatlaf.*;

import model.Shipment;
import model.User;
import network.Client;

public class ClerkView extends TabView implements ActionListener, PopupMenuListener, ComponentListener, KeyListener {
    private static final long serialVersionUID = -4757134542607256811L;
    private JPanel shipmentPanel = new JPanel(new BorderLayout());;
    private JPanel accountInfoPanel = new JPanel();

    JCheckBox shipmentFilter = new JCheckBox("Incomplete");
    JTextField shipmentSearch = new JTextField();;
    JButton loadShipmentsButton = new JButton("(Re)Load Shipment Orders");;
    JPanel shipmentList = new JPanel();

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
        
        this.initialiseComponents();
        this.addActionListeners();
        this.setVisible(true);
    }
    
    private void initialiseComponents() {
        shipmentList.setLayout(new BoxLayout(shipmentList, BoxLayout.Y_AXIS));

		shipmentPanel.add(addToPanel(new JLabel("Search: "), shipmentSearch, addToPanel(loadShipmentsButton, shipmentFilter)), BorderLayout.NORTH);
		shipmentPanel.add(new JScrollPane(shipmentList), BorderLayout.CENTER);

     //mainPanel.add(new JScrollPane(new JLabel(this.clerk.toString())), "accounts");
        
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

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void popupMenuCanceled(PopupMenuEvent e) {
		// TODO Auto-generated method stub
		
	}
    
}