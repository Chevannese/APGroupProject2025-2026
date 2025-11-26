package view;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import com.formdev.flatlaf.*;

import model.*;
import network.Client;

public class ManagerView extends TabView implements KeyListener {
    private static final long serialVersionUID = -4604700777404064232L;
    private JPanel manageUsersPanel = new JPanel(new BorderLayout());
    private JPanel managePackagesPanel = new JPanel(new BorderLayout());
    private JComboBox<String> showUserType = new JComboBox<String>(new String[] { "All", "Customers", "Drivers", "Clerks"});
    private JTextField searchUsers = new JTextField();
    private JButton findUserByID = new JButton("Search by ID");
    private JPanel userList = new JPanel();
    
	private User manager;
	private List<User> users;
    
    public ManagerView(User loggedInUser) {
    	this.manager = loggedInUser;
    }
    
    public ManagerView() {
        super();
        this.initialiseComponents();
        this.setVisible(true);
    }
    
    private void initialiseComponents() {
    	addTab("User Management", manageUsersPanel);
    	addTab("Manage Packages", managePackagesPanel);
    	addTab("Manage Vehicles", managePackagesPanel);
    	
    	userList.setLayout(new BoxLayout(userList, BoxLayout.Y_AXIS));
    	manageUsersPanel.add(addToPanel(new JLabel("Search"), searchUsers, showUserType, findUserByID), BorderLayout.NORTH);
    	manageUsersPanel.add(new JScrollPane(userList), BorderLayout.CENTER);
    }
    
    private void addActionListeners() {
		showUserType.addActionListener(this);
		searchUsers.addKeyListener(this);
		findUserByID.addActionListener(this);
	}
    
    private JPanel addToPanel(Component ...components) {
    	JPanel panel = new JPanel();
    	
    	for (Component component : components) {
    		panel.add(component);
    	}
    	
		return panel;
	}
 
    @Override
	public void actionPerformed(ActionEvent e) {
	}

    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread (EDT)
    	FlatLightLaf.setup();

        SwingUtilities.invokeLater(() -> {
            new ManagerView();
        });
    }

	@Override public void keyTyped(KeyEvent e) {
		this.populateUserList();
	}

	private Class<?> getSelectedClass() {
		switch ((String) showUserType.getSelectedItem()) {
			case "Customer": return Customer.class;
			case "Clerk":	 return Clerk.class;
			case "Driver":	 return Driver.class;
			case "All":		 return User.class;
			default:		 return User.class;
		}
	}
	
	private void loadUsers() {
		this.users = null;
		
		try {
			users = new Client().getUsers();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void populateUserList() {
		if (this.users == null) {
			return;
		}
		
		String search = searchUsers.getText().toLowerCase();
		
		List<User> filter = users.stream().filter(u -> this.getSelectedClass().isInstance(u)).toList();

		for (User u : users) {
			if (!getSelectedClass().isInstance(u)) {
				continue;
			}
	        // Filter by search
	        if (!search.isBlank()) {
	            boolean matches = String.format("%s %s", u.getFirstName(), u.getLastName()).toLowerCase().contains(search) 
	            		|| u.getFirstName().toLowerCase().contains(search)
	                    || u.getLastName().toLowerCase().contains(search)
	                    || u.getEmail().toLowerCase().contains(search)
	                    || u.getContactNum().toLowerCase().contains(search);
	
	            if (!matches) {
	                continue;
	            }
	        }
	
	        JPanel panel = new JPanel(new BorderLayout());
	        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        JLabel trn = new JLabel(u.getTrn());
	        JLabel name = new JLabel(String.format("%s %s", u.getFirstName(), u.getLastName()));
	        JButton button = new JButton("Open");
	
	        infoPanel.add(trn);
	        infoPanel.add(name);
	
	        panel.add(infoPanel, BorderLayout.CENTER);
	        panel.add(button, BorderLayout.EAST);
	        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
	        button.addActionListener(e -> editUser(u));
	
	        userList.add(panel);
		}
    }
	
	private void addToGridBag(JPanel panel, Component component, int x, int y, int w, int h) {
		GridBagConstraints gc = new GridBagConstraints(x, y, w, h, 0, 0, 0, 0, null, h, h);
		panel.add(component, gc);
	}

	private void editUser(User u) {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		JTextField trnField = new JTextField();
		JTextField firstNameField = new JTextField();
		JTextField lastNameField = new JTextField();
		JTextField contactField = new JTextField();
		JTextField emailField = new JTextField();
		
		trnField.setEnabled(false);
		
		addToGridBag(panel, new JLabel("Name"), 0, 2, 2, 1);
		addToGridBag(panel, firstNameField, 	0, 3, 1, 1);
		addToGridBag(panel, lastNameField,		1, 3, 1, 1);
		addToGridBag(panel, new JLabel("Phone"), 0, 4, 2, 1);
		addToGridBag(panel, contactField, 		0, 5, 2, 1);
		addToGridBag(panel, new JLabel("Email"), 0, 6, 2, 1);
		addToGridBag(panel, emailField,			0, 7, 2, 1);
		
		if (JOptionPane.showConfirmDialog(this, u, "Edit user details", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
			// TODO Add checks to user info
			User editedUser = new User(u);
			try {
				new Client().updateUser(editedUser);
				u.setFirstName(editedUser.getFirstName());
				u.setLastName(editedUser.getLastName());
				u.setContactNum(editedUser.getContactNum());
				u.setEmail(editedUser.getEmail());
				JOptionPane.showMessageDialog(this, "User info successfully updated", null, JOptionPane.PLAIN_MESSAGE);
			}
			catch (Exception err) {
				JOptionPane.showMessageDialog(this, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		userList.add(panel);
	}

	@Override public void keyPressed(KeyEvent e) {}

	@Override public void keyReleased(KeyEvent e) {}
	
	private void showUserTable(List<User> userList) {
	    UserTableModel model = new UserTableModel(userList);
	    JTable table = new JTable(model);

	    // Apply the green highlight renderer to all columns
	    TableCellRenderer renderer = createChangeHighlightRenderer(model);
	    for (int i = 0; i < table.getColumnCount(); i++) {
	        table.getColumnModel().getColumn(i).setCellRenderer(renderer);
	    }

	    JScrollPane scroll = new JScrollPane(table);
	    scroll.setPreferredSize(new Dimension(900, 400));

	    // Assuming you are reusing your existing panel (invoicePage) for display
	    manageUsersPanel.removeAll();

	    GridBagConstraints gc = new GridBagConstraints();
	    gc.insets = new Insets(10, 10, 10, 10);
	    gc.fill = GridBagConstraints.HORIZONTAL;

	    JLabel title = new JLabel("Manage Users", SwingConstants.CENTER);
	    title.setFont(new Font("Arial", Font.BOLD, 20));

	    JButton submit = new JButton("Submit");

	    submit.addActionListener(e -> {
	        // Commit any ongoing cell edit
	        if (table.isEditing()) {
	            table.getCellEditor().stopCellEditing();
	        }

	        List<User> modifiedUsers = model.getModifiedUsers();
	        if (modifiedUsers.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "No changes to submit.");
	            return;
	        }

	        // Send only changed users to server
	        try (Socket socket = new Socket("127.0.0.1", 8888);
	             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
	             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

	            out.writeObject("UPDATE_USERS");
	            out.writeObject(modifiedUsers);
	            out.flush();

	            Object resp = in.readObject();
	            String response = (resp instanceof String) ? (String) resp : "Users updated successfully!";
	            JOptionPane.showMessageDialog(null, response);

	            // On success: clear modified marks and refresh snapshot
	            model.clearModifiedMarks();

	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(null, "Could not update users!");
	            ex.printStackTrace();
	        }
	    });


	    manageUsersPanel.add(scroll, gc);

	    manageUsersPanel.revalidate();
	    manageUsersPanel.repaint();
	}

	
	
	private TableCellRenderer createChangeHighlightRenderer(UserTableModel model) {
	    return new DefaultTableCellRenderer() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public Component getTableCellRendererComponent(
	                JTable table, Object value, boolean isSelected,
	                boolean hasFocus, int row, int col) {

	            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

	            if (model.isCellModified(row, col)) {
	                c.setBackground(new java.awt.Color(144, 238, 144)); // light green
	            } else {
	                c.setBackground(isSelected ? table.getSelectionBackground() : java.awt.Color.WHITE);
	            }
	            return c;
	        }
	    };
	}
	
}