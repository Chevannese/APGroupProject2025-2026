package view;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import com.formdev.flatlaf.*;

public class ManagerView extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JPanel sidebarPanel;
    private JPanel sidebarContent;
    private JPopupMenu popupSidebar;
    private JButton sidebarPopupButton;
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
    
    public ManagerView() {
        super("Manager");
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
        sidebarPopupButton = new JButton(">"); // Use a Unicode hamburger icon
        sidebarPopupButton.setFocusPainted(false);
        sidebarPopupButton.setVisible(false); // Hide initially
        menubar.add(sidebarPopupButton);
        menubar.add(new JLabel("Clerk Management"));
        //this.add(menubar, BorderLayout.NORTH);
        this.setJMenuBar(menubar);
        
        card = new CardLayout();
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(card);
        this.add(mainPanel, BorderLayout.CENTER);
        
        mainPanel.add(new JScrollPane(new JTextArea("I don't know what to put here yet", 10, 30)), "home");
        mainPanel.add(shipmentPanel, "shipments");

        shipmentPanel = new JPanel(new BorderLayout());
        shipmentPanel.add(new JTextField(), BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(shipmentPanel));

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

        // E. Popup Menu
        // This will hold the sidebar content when collapsed.
        popupSidebar = new JPopupMenu();
    }

    private void updateSidebar() {
        int frameWidth = this.getWidth();

        // small window = no sidebar shown
        if (frameWidth < SIDEBAR_SHOWN_MAX_WIDTH && isSidebarShown) {
            // Collapse side bar
            this.remove(sidebarPanel);
            sidebarPopupButton.setVisible(true);
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
            
            sidebarPopupButton.setVisible(false);
            isSidebarShown = true;
            
            // hide popup because we are using the sidebar now
            popupSidebar.setVisible(false);
        }

        // Tell the frame to update its layout
        this.revalidate();
        this.repaint();
    }
    
    public void addActionListeners() {
    	// listener for when the window resizes
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateSidebar();
            }
        });

        sidebarPopupButton.addActionListener(e -> {
            // move the sidebar to the popup
            popupSidebar.add(sidebarContent);
            // move popup near button
            popupSidebar.show(sidebarPopupButton, 0, sidebarPopupButton.getHeight());
        });

        popupSidebar.addPopupMenuListener(new PopupMenuListener() {
        	@Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
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
        });
        
        homeBtn.addActionListener(e -> card.show(mainPanel, "home"));
        shipmentsBtn.addActionListener(e -> card.show(mainPanel, "shipments"));
    }
    
    @Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread (EDT)
    	FlatLightLaf.setup();

        SwingUtilities.invokeLater(() -> {
            new ClerkView();
        });
    }
}