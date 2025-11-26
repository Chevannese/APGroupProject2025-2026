package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import com.formdev.flatlaf.*;

import model.*;
import network.Client;

public class ManagerView extends TabView implements ActionListener {
    private static final long serialVersionUID = -4604700777404064232L;
    private JPanel manageUsersPanel = new JPanel(new BorderLayout());
    private JPanel managePackagesPanel = new JPanel(new BorderLayout());
    private JComboBox<String> showUserType = new JComboBox<String>(new String[] { "All", "Customers", "Drivers", "Clerks"});
    private JTextField searchUsers = new JTextField(20);
    private JButton findUserByID = new JButton("Search by ID");
    private JButton refreshBtn = new JButton("Refresh");
    
    private User manager;
    private List<User> users;
    private UserTableModel tableModel;
    private JTable userTable;
    
    public ManagerView(User loggedInUser) {
        super();
        this.manager = loggedInUser;
        this.initialiseComponents();
        this.setVisible(true);
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

        
        // Create search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchUsers);
        searchPanel.add(new JLabel("Filter:"));
        searchPanel.add(showUserType);
        searchPanel.add(findUserByID);
        searchPanel.add(refreshBtn);
        
        // Initialize table
        tableModel = new UserTableModel(new ArrayList<>());
        userTable = new JTable(tableModel);
        
        // Apply the green highlight renderer to all columns
        TableCellRenderer renderer = createChangeHighlightRenderer(tableModel);
        for (int i = 0; i < userTable.getColumnCount(); i++) {
            userTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
        
        // Make table editable
        userTable.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()) {
            @Override
            public boolean isCellEditable(EventObject e) {
                // Make all cells except TRN editable
                if (e instanceof KeyEvent) {
                    return true;
                }
                if (e instanceof MouseEvent) {
                    MouseEvent me = (MouseEvent) e;
                    int column = userTable.columnAtPoint(me.getPoint());
                    return column != 0; // TRN column is not editable
                }
                return true;
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setPreferredSize(new Dimension(900, 400));
        
        // Submit button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitBtn = new JButton("Submit Changes");
        buttonPanel.add(submitBtn);
        
        // Add components to main panel
        manageUsersPanel.add(searchPanel, BorderLayout.NORTH);
        manageUsersPanel.add(scrollPane, BorderLayout.CENTER);
        manageUsersPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        addActionListeners();
        //loadUsers();
    }
    
    private void addActionListeners() {
        showUserType.addActionListener(e -> filterUsers());
        findUserByID.addActionListener(e -> searchUserByID());
        refreshBtn.addActionListener(e -> loadUsers());
        
        // Add document listener for real-time search
        searchUsers.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterUsers(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterUsers(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterUsers(); }
        });
    }
    
    private void loadUsers() {
        try {
            this.users = new Client().getUsers();
            filterUsers(); // Apply current filters
            JOptionPane.showMessageDialog(this, "Users loaded successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Could not load users from server!");
        }
    }
    
    private void filterUsers() {
        if (this.users == null) return;
        
        String searchText = searchUsers.getText().toLowerCase();
        String selectedType = (String) showUserType.getSelectedItem();
        
        List<User> filteredUsers = users.stream()
            .filter(user -> matchesType(user, selectedType))
            .filter(user -> matchesSearch(user, searchText))
            .toList();
        
        tableModel.setUsers(new ArrayList<>(filteredUsers));
    }
    
    private boolean matchesType(User user, String type) {
        return switch (type) {
            case "Customers" -> user instanceof Customer;
            case "Drivers" -> user instanceof Driver;
            case "Clerks" -> user instanceof Clerk;
            default -> true; // "All"
        };
    }
    
    private boolean matchesSearch(User user, String searchText) {
        if (searchText.isBlank()) return true;
        
        return user.getFirstName().toLowerCase().contains(searchText) ||
               user.getLastName().toLowerCase().contains(searchText) ||
               user.getEmail().toLowerCase().contains(searchText) ||
               user.getContactNum().toLowerCase().contains(searchText) ||
               user.getTrn().toLowerCase().contains(searchText);
    }
    
    private void searchUserByID() {
        String searchId = JOptionPane.showInputDialog(this, "Enter User TRN:");
        if (searchId != null && !searchId.trim().isEmpty()) {
            searchUsers.setText(searchId.trim());
        }
    }
    
    private TableCellRenderer createChangeHighlightRenderer(UserTableModel model) {
        return new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int col) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                if (model.isCellModified(row, col)) {
                    c.setBackground(new Color(144, 238, 144)); // light green
                } else {
                    c.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
                }
                return c;
            }
        };
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton source = (JButton) e.getSource();
            if (source.getText().equals("Submit Changes")) {
                submitChanges();
            }
        }
    }
    
    private void submitChanges() {
        // Commit any ongoing cell edit
        if (userTable.isEditing()) {
            userTable.getCellEditor().stopCellEditing();
        }

        List<User> modifiedUsers = tableModel.getModifiedUsers();
        if (modifiedUsers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No changes to submit.");
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
            JOptionPane.showMessageDialog(this, response);

            // On success: clear modified marks and refresh data
            tableModel.clearModifiedMarks();
            loadUsers(); // Reload to get fresh data

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Could not update users: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(() -> new ManagerView());
    }
}