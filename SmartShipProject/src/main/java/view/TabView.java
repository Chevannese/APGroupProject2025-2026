package view;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class TabView extends JFrame implements PopupMenuListener, ComponentListener, ActionListener {
    private static final long serialVersionUID = -4757134542607256811L;
    protected JPanel outerPanel;
    protected JPanel mainPanel;
	protected JPanel sidebarPanel;
    protected JPanel sidebarContent;
    protected JPopupMenu popupSidebar;
    protected JButton sidebarPopupBtn;
    protected JMenuBar menubar;
    protected CardLayout card;
    
    // window size that the sidebar collapses under
    private static final int SIDEBAR_SHOWN_MAX_WIDTH = 700;
    // is the side bar shown
    private Boolean isSidebarShown = true;
    
    public TabView() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(800, 600)); // Start large
        this.setLocationRelativeTo(null);
        
        this.initialiseComponents();
        this.updateSidebar();
        
        this.addComponentListener(this);
        popupSidebar.addPopupMenuListener(this);
        // button that shows popup sidebar
        sidebarPopupBtn.addActionListener(e -> {
	        popupSidebar.add(sidebarContent);
			// move popup near button
			popupSidebar.show(sidebarPopupBtn, 0, sidebarPopupBtn.getHeight());
        });
        
		JButton logout = new JButton("Logout");
		    	
		    	menubar.add(logout);
		    	
		    	logout.addActionListener(e -> {
		            this.dispose();
		    		new Login();
		    	});
    }
    
    public void addTab(String tabName, JPanel pane) {
    	JButton tabButton = new JButton(tabName);
    	
    	tabButton.setFocusPainted(false);
        tabButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        tabButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		tabButton.addActionListener(e -> card.show(mainPanel, tabName));
		tabButton.addActionListener(this);
		tabButton.setActionCommand(tabName);
		sidebarContent.add(tabButton);
		mainPanel.add(pane, tabName);
    }
    
    private void initialiseComponents() {
        menubar = new JMenuBar();
        sidebarPopupBtn = new JButton(">"); // Use a Unicode hamburger icon
        sidebarPopupBtn.setFocusPainted(false);
        sidebarPopupBtn.setVisible(false); // Hide initially
        
        menubar.add(sidebarPopupBtn);
        //this.add(menubar, BorderLayout.NORTH);
        this.setJMenuBar(menubar);
        
        card = new CardLayout();
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        //mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(card);
        outerPanel = new JPanel(new BorderLayout());
        outerPanel.add(mainPanel, BorderLayout.CENTER);

        // sidebar
        sidebarPanel = new JPanel(new BorderLayout());
        sidebarPanel.setBackground(new Color(230, 230, 230));
        //sidebarPanel.setMinimumSize(new Dimension(200, 300));
        outerPanel.add(sidebarPanel, BorderLayout.WEST);
        this.add(outerPanel);
        
        sidebarContent = new JPanel();
        sidebarPanel.add(sidebarContent, BorderLayout.CENTER); // Add content
        sidebarContent.setLayout(new BoxLayout(sidebarContent, BoxLayout.Y_AXIS));
        sidebarContent.setBackground(Color.gray);        
        sidebarContent.setPreferredSize(new Dimension(180, 200));
        popupSidebar = new JPopupMenu();
    }
    
    private JPanel addToPanel(Component ...components) {
    	JPanel panel = new JPanel();
    	for (Component component : components) {
    		panel.add(component);
    	}
    	
		return panel;
	}

    private void updateSidebar() {
        int frameWidth = this.getWidth();

        // small window = no sidebar shown
        if (frameWidth < SIDEBAR_SHOWN_MAX_WIDTH && isSidebarShown) {
            // Collapse side bar
            outerPanel.remove(sidebarPanel);
            sidebarPopupBtn.setVisible(true);
            isSidebarShown = false;
        }
        // large window = sidebar shown
        else if (frameWidth >= SIDEBAR_SHOWN_MAX_WIDTH && !isSidebarShown) {
            // show sidebar
            outerPanel.add(sidebarPanel, BorderLayout.WEST);
            
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


 
    @Override public void componentResized(ComponentEvent e) { updateSidebar(); }
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

	@Override public void actionPerformed(ActionEvent e) {}
}