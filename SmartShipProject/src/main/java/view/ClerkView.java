package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import com.formdev.flatlaf.*;

public class ClerkView extends JFrame implements ActionListener, PopupMenuListener, ComponentListener {
    private JPanel mainPanel;
    private JPanel sidebarPanel;
    private JPanel sidebarContent;
    private JPopupMenu popupSidebar;
    private JButton sidebarPopupBtn;
    private JMenuBar menubar;
    private JButton homeBtn;
    private JButton shipmentsBtn;
    private JButton accountInfoBtn;
    private JButton billingBtn;
    private CardLayout card;
    private JPanel shipmentPanel;

    // window size that the sidebar collapses under
    private static final int SIDEBAR_SHOWN_MAX_WIDTH = 700;
    // is the side bar shown
    private Boolean isSidebarShown = true;
    
    public ClerkView() {
        super("Clerk");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(800, 600)); // Start large
        this.setLocationRelativeTo(null);
        
        this.initialiseComponents();
        this.addActionListeners();
        this.updateSidebar();
        
        this.setVisible(true);
    }
    
    private void initialiseComponents() {
        menubar = new JMenuBar();
        sidebarPopupBtn = new JButton(">"); // Use a Unicode hamburger icon
        sidebarPopupBtn.setFocusPainted(false);
        sidebarPopupBtn.setVisible(false); // Hide initially
        menubar.add(sidebarPopupBtn);
        menubar.add(new JLabel("Clerk Management"));
        //this.add(menubar, BorderLayout.NORTH);
        this.setJMenuBar(menubar);
        
        card = new CardLayout();
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(card);
        this.add(mainPanel, BorderLayout.CENTER);

        // sidebar
        sidebarPanel = new JPanel(new BorderLayout());
        sidebarPanel.setBackground(new Color(230, 230, 230));
        this.add(sidebarPanel, BorderLayout.WEST);
        
        sidebarContent = new JPanel();
        sidebarPanel.add(sidebarContent, BorderLayout.CENTER); // Add content
        sidebarContent.setLayout(new BoxLayout(sidebarContent, BoxLayout.Y_AXIS));
        sidebarContent.setBackground(Color.gray);
        
        // Add buttons to sidebar
        homeBtn = new JButton("Home");
        shipmentsBtn = new JButton("Shipment Management");
        accountInfoBtn = new JButton("Account Information");
        billingBtn = new JButton("Billing");
        
        JButton[] buttons = {homeBtn, accountInfoBtn, shipmentsBtn, billingBtn};
        for (JButton button : buttons) {
            button.setFocusPainted(false);
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            //button.setPreferredSize(new Dimension(0, 50));
            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            sidebarContent.add(button);
        }
        
        sidebarContent.setPreferredSize(new Dimension(180, 200));
        popupSidebar = new JPopupMenu();
        
        
        
        // main sections
        mainPanel.add(new JScrollPane(new JTextArea("I don't know what to put here yet", 10, 30)), "home");
        
        shipmentPanel = new JPanel(new BorderLayout());
        JPanel top = new JPanel();
        shipmentPanel.add(new JTextField(), BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(shipmentPanel), "shipments");
    }

    private void updateSidebar() {
        int frameWidth = this.getWidth();

        // small window = no sidebar shown
        if (frameWidth < SIDEBAR_SHOWN_MAX_WIDTH && isSidebarShown) {
            // Collapse side bar
            this.remove(sidebarPanel);
            sidebarPopupBtn.setVisible(true);
            isSidebarShown = false;
        }
        // large window = sidebar shown
        else if (frameWidth >= SIDEBAR_SHOWN_MAX_WIDTH && !isSidebarShown) {
            // show sidebar
            this.add(sidebarPanel, BorderLayout.WEST);
            
            // return content to sidebar
            if (popupSidebar.isAncestorOf(sidebarContent)) {
                sidebarPanel.add(sidebarContent);
            }
            
            sidebarPopupBtn.setVisible(false);
            isSidebarShown = true;
            
            // hide popup because we are using the sidebar now
            popupSidebar.setVisible(false);
        }

        // Tell the frame to update its layout
        this.revalidate();
        this.repaint();
    }
    
    public void addActionListeners() {
    	this.addComponentListener(this);
        popupSidebar.addPopupMenuListener(this);
        
        // button that shows popup sidebar
        sidebarPopupBtn.addActionListener(this);
        homeBtn.addActionListener(this);
        shipmentsBtn.addActionListener(this);
        
        sidebarPopupBtn.setActionCommand("show-sidebar-popup");
        homeBtn.setActionCommand("goto-home");
        shipmentsBtn.setActionCommand("goto-shipments");
    }
    
    @Override public void actionPerformed(ActionEvent e) {
    	String action = e.getActionCommand();
    	if (action.equals("goto-home"))
    			card.show(mainPanel, "home");
    	else if (action.equals("goto-shipments"))
    			card.show(mainPanel, "shipments");
    	else if (action.equals("show-sidebar-popup")) {
    		// move the sidebar to the popup
    		popupSidebar.add(sidebarContent);
    		// move popup near button
    		popupSidebar.show(sidebarPopupBtn, 0, sidebarPopupBtn.getHeight());
    	}
		
	}
    
    @Override public void componentResized(ComponentEvent e) {
    	updateSidebar();
    }
    @Override public void componentMoved(ComponentEvent e) {}
    @Override public void componentShown(ComponentEvent e) {}
    @Override public void componentHidden(ComponentEvent e) {}
    
    @Override public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
    	// move the sidebar back to the mainwindow if the window is large enough
    	if (isSidebarShown) {
    		sidebarPanel.add(sidebarContent);
    		sidebarPanel.revalidate();
    		sidebarPanel.repaint();
    	}
    }
    
    // don't need these but java complains with them cuz they are abstract methods
    @Override public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}
    @Override public void popupMenuCanceled(PopupMenuEvent e) {}

    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread (EDT)
    	FlatLightLaf.setup();

        SwingUtilities.invokeLater(() -> {
            new ClerkView();
        });
    }

}