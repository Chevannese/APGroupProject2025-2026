package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.Locale;

import com.formdev.flatlaf.*;

import model.*;
import network.Client;

public class ManagerView extends TabView implements ActionListener {
    private static final long serialVersionUID = -4604700777404064232L;

    // ===== EXISTING: User management tab =====
    private JPanel manageUsersPanel = new JPanel(new BorderLayout());
    private JComboBox<String> showUserType = new JComboBox<String>(
            new String[]{"All", "Customers", "Drivers", "Clerks"});
    private JTextField searchUsers = new JTextField(20);
    private JButton findUserByID = new JButton("Search by ID");
    private JButton refreshBtn = new JButton("Refresh");
    private JButton submitBtn = new JButton("Submit Changes");

    private User manager;
    private List<User> users;
    private UserTableModel tableModel;
    private JTable userTable;

    // ===== NEW: Package dispatch tab =====
    private JPanel managePackagesPanel = new JPanel(new BorderLayout());

    private JTable shipmentTable;
    private ShipmentTableModel shipmentTableModel;
    private JButton refreshDispatchBtn = new JButton("Refresh");
    private JButton assignBtn = new JButton("Assign Package");

    private JComboBox<String> routeCombo = new JComboBox<String>();
    private JComboBox<String> vehicleCombo = new JComboBox<String>();

    private List<Shipment> unassignedShipments = new ArrayList<Shipment>();
    private List<Route> routes = new ArrayList<Route>();
    private List<Vehicle> vehicles = new ArrayList<Vehicle>();

    // ===== NEW: Vehicle overview tab =====
    private JPanel manageVehiclesPanel = new JPanel(new BorderLayout());

    private JTable vehicleTable;
    private VehicleTableModel vehicleTableModel;
    private JButton refreshVehiclesBtn = new JButton("Refresh");
    
    // ===== NEW: Reports Tab =====
    private JPanel reportsPanel = new JPanel(new BorderLayout());
    
    
 // ===== NEW: Trip Overview tab =====
    private JPanel manageTripsPanel = new JPanel(new BorderLayout());
    private JTable tripTable;
    private TripTableModel tripTableModel;
    private JButton refreshTripsBtn = new JButton("Refresh");
    private List<Trip> trips = new ArrayList<Trip>();


    // ===== Constructors =====
    public ManagerView(User loggedInUser) {
        super();
        this.manager = loggedInUser;
        initialiseComponents();
        setVisible(true);
    }

    public ManagerView() {
        super();
        initialiseComponents();
        setVisible(true);
    }

    // ===== Init all tabs =====
    private void initialiseComponents() {
        // Three tabs
        addTab("User Management", manageUsersPanel);
        addTab("Manage Packages", managePackagesPanel);
        addTab("Manage Vehicles", manageVehiclesPanel);
        addTab("Reports", reportsPanel);
        addTab("Trip Overview", manageTripsPanel);

        initUserManagementTab();
        initPackageDispatchTab();
        initVehicleTab();
        initReportsTab();
        initTripTab();

        addActionListeners();

        // Optional: auto-load data at start
        // loadUsers();
        // loadDispatchData();
        // loadVehicles();
    }
    
    // REPORTS TAB
    private void initReportsTab() {
		JButton loadDailyReport = new JButton("Daily Report");
		
	}
    
    private void generateReports() {
    	
    	try {
    		//ArrayList<Shipment> shipments = new Client().getShipments();
    	}
    	catch (Exception e) {
			// TODO: handle exception
		}
    	
    	
    	JPanel panel = new JPanel();
    }

	// ---------------------------------------------------------------------
    //  USER MANAGEMENT TAB (existing behaviour kept)
    // ---------------------------------------------------------------------
    private void initUserManagementTab() {
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchUsers);
        searchPanel.add(new JLabel("Filter:"));
        searchPanel.add(showUserType);
        searchPanel.add(findUserByID);
        searchPanel.add(refreshBtn);

        // Table
        tableModel = new UserTableModel(new ArrayList<>());
        userTable = new JTable(tableModel);

        // Green highlight on modified cells
        TableCellRenderer renderer = createChangeHighlightRenderer(tableModel);
        for (int i = 0; i < userTable.getColumnCount(); i++) {
            userTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        // Editable except TRN
        userTable.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(EventObject e) {
                if (e instanceof KeyEvent) {
                    return true;
                }
                if (e instanceof MouseEvent) {
                    MouseEvent me = (MouseEvent) e;
                    int column = userTable.columnAtPoint(me.getPoint());
                    return column != 0; // TRN column not editable
                }
                return true;
            }
        });

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setPreferredSize(new Dimension(900, 400));

        // Submit changes button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(submitBtn);

        manageUsersPanel.add(searchPanel, BorderLayout.NORTH);
        manageUsersPanel.add(scrollPane, BorderLayout.CENTER);
        manageUsersPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addActionListeners() {
        // User tab
        showUserType.addActionListener(e -> filterUsers());
        findUserByID.addActionListener(e -> searchUserByID());
        refreshBtn.addActionListener(e -> loadUsers());

        searchUsers.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {
                    public void insertUpdate(javax.swing.event.DocumentEvent e) { filterUsers(); }
                    public void removeUpdate(javax.swing.event.DocumentEvent e) { filterUsers(); }
                    public void changedUpdate(javax.swing.event.DocumentEvent e) { filterUsers(); }
                });

        submitBtn.addActionListener(this);

        // Dispatch tab
        refreshDispatchBtn.addActionListener(this);
        assignBtn.addActionListener(this);

        // Vehicles tab
        refreshVehiclesBtn.addActionListener(this);
        //Trips tab
        refreshTripsBtn.addActionListener(this);

    }

    // Load & filter users (unchanged logic)
    private void loadUsers() {
        try {
            this.users = new Client().getUsers();
            filterUsers();
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
            case "Drivers"   -> user instanceof Driver;
            case "Clerks"    -> user instanceof Clerk;
            default          -> true; // "All"
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

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, col);

                if (model.isCellModified(row, col)) {
                    c.setBackground(new Color(144, 238, 144)); // light green
                } else {
                    c.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
                }
                return c;
            }
        };
    }

    private void submitChanges() {
        if (userTable.isEditing()) {
            userTable.getCellEditor().stopCellEditing();
        }

        List<User> modifiedUsers = tableModel.getModifiedUsers();
        if (modifiedUsers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No changes to submit.");
            return;
        }

        try (Socket socket = new Socket("127.0.0.1", 8888);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject("UPDATE_USERS");
            out.writeObject(modifiedUsers);
            out.flush();

            Object resp = in.readObject();
            String response = (resp instanceof String) ? (String) resp : "Users updated successfully!";
            JOptionPane.showMessageDialog(this, response);

            tableModel.clearModifiedMarks();
            loadUsers();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Could not update users: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // ---------------------------------------------------------------------
    //  PACKAGES TAB – DISPATCH LOGIC
    // ---------------------------------------------------------------------
    private void initPackageDispatchTab() {
        managePackagesPanel.setLayout(new BorderLayout());

        // Top: controls
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Route:"));
        topPanel.add(routeCombo);
        topPanel.add(Box.createHorizontalStrut(15));
        topPanel.add(new JLabel("Vehicle:"));
        topPanel.add(vehicleCombo);
        topPanel.add(Box.createHorizontalStrut(15));
        topPanel.add(assignBtn);
        topPanel.add(Box.createHorizontalStrut(15));
        topPanel.add(refreshDispatchBtn);

        // Center: table of unassigned shipments
        shipmentTableModel = new ShipmentTableModel(new ArrayList<>());
        shipmentTable = new JTable(shipmentTableModel);
        JScrollPane shipmentScroll = new JScrollPane(shipmentTable);
        shipmentScroll.setPreferredSize(new Dimension(900, 400));

        managePackagesPanel.add(topPanel, BorderLayout.NORTH);
        managePackagesPanel.add(shipmentScroll, BorderLayout.CENTER);
    }

    private void loadDispatchData() {
        try {
            Client client = new Client();

            // ---- You must implement these in Client + Server ----
            // e.g. client sends "GET_UNASSIGNED_SHIPMENTS" etc.
            this.unassignedShipments = client.getUnassignedShipments();
            this.routes             = client.getAllRoutes();
            this.vehicles           = client.getAllVehicles();
            // ------------------------------------------------------

            shipmentTableModel.setShipments(new ArrayList<>(unassignedShipments));

            // Fill route combo
            routeCombo.removeAllItems();
            for (Route r : routes) {
                // Adjust display text to match your Route fields
                String label = r.getRouteID() + " - " + r.getOrigin() + " → " + r.getDestination();
                routeCombo.addItem(label);
            }

            // Fill vehicle combo
            vehicleCombo.removeAllItems();
            for (Vehicle v : vehicles) {
                String label = v.getVehicleNo() + " - " + v.getVehicleName();
                vehicleCombo.addItem(label);
            }

            JOptionPane.showMessageDialog(this, "Dispatch data loaded!");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Could not load dispatch data: " + ex.getMessage());
        }
    }

    private void assignSelectedShipment() {
        int row = shipmentTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select a package to assign.");
            return;
        }
        if (routeCombo.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select a route.");
            return;
        }
        if (vehicleCombo.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select a vehicle.");
            return;
        }

        Shipment shipment = shipmentTableModel.getShipmentAt(row);
        Route route = routes.get(routeCombo.getSelectedIndex());
        Vehicle vehicle = vehicles.get(vehicleCombo.getSelectedIndex());

        try {
            Client client = new Client();

            // SEND THE MANAGER TRN → REQUIRED FOR TRIP CREATION
            String response = client.assignShipmentToVehicleRoute(
                    shipment.getPackageNo(),
                    route.getRouteID(),
                    vehicle.getVehicleNo(),
                    manager.getTrn()                 // <--- NEW
            );

            JOptionPane.showMessageDialog(this, response);

            if (response.equals("SUCCESS")) {
                loadDispatchData();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Failed to assign package: " + ex.getMessage());
        }
    }

    // ---------------------------------------------------------------------
    //  VEHICLES TAB – FLEET OVERVIEW
    // ---------------------------------------------------------------------
    private void initVehicleTab() {
        manageVehiclesPanel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(refreshVehiclesBtn);

        vehicleTableModel = new VehicleTableModel(new ArrayList<>());
        vehicleTable = new JTable(vehicleTableModel);
        JScrollPane scroll = new JScrollPane(vehicleTable);
        scroll.setPreferredSize(new Dimension(900, 400));

        manageVehiclesPanel.add(topPanel, BorderLayout.NORTH);
        manageVehiclesPanel.add(scroll, BorderLayout.CENTER);
    }
    private void loadTrips() {
        try {
            Client client = new Client();
            this.trips = client.getAllTrips();
            tripTableModel.setTrips(new ArrayList<>(trips));
            JOptionPane.showMessageDialog(this, "Trips loaded successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Could not load trips: " + ex.getMessage());
        }
    }

    private void loadVehicles() {
        try {
            Client client = new Client();
            // ---- You must implement this in Client + Server ----
            this.vehicles = client.getAllVehicles();
            // ------------------------------------------------------
            vehicleTableModel.setVehicles(new ArrayList<>(vehicles));
            JOptionPane.showMessageDialog(this, "Vehicles loaded successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Could not load vehicles: " + ex.getMessage());
        }
    }
    private void initTripTab() {
        manageTripsPanel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(refreshTripsBtn);

        tripTableModel = new TripTableModel(new ArrayList<>());
        tripTable = new JTable(tripTableModel);

        JScrollPane scrollPane = new JScrollPane(tripTable);
        scrollPane.setPreferredSize(new Dimension(900, 400));

        manageTripsPanel.add(topPanel, BorderLayout.NORTH);
        manageTripsPanel.add(scrollPane, BorderLayout.CENTER);
    }


    // ---------------------------------------------------------------------
    //  ACTION HANDLING
    // ---------------------------------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == submitBtn) {
            submitChanges();
        } else if (src == refreshDispatchBtn) {
            loadDispatchData();
        } else if (src == assignBtn) {
            assignSelectedShipment();
        } else if (src == refreshVehiclesBtn) {
            loadVehicles();
        }else if (src == refreshTripsBtn) {
            loadTrips();
        }

    }

    // ---------------------------------------------------------------------
    //  TABLE MODELS
    // ---------------------------------------------------------------------

    // Simple table for vehicles
    private static class VehicleTableModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;

        private final String[] columns =
            {"Vehicle No", "Name", "Qty Cap", "Qty Used", "Wt Cap", "Wt Used"};

        private List<Vehicle> vehicles;

        public VehicleTableModel(List<Vehicle> vehicles) {
            this.vehicles = vehicles;
        }

        public void setVehicles(List<Vehicle> vehicles) {
            this.vehicles = vehicles;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return vehicles == null ? 0 : vehicles.size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public String getColumnName(int column) {
            return columns[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Vehicle v = vehicles.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> v.getVehicleNo();
                case 1 -> v.getVehicleName();
                case 2 -> v.getQuantityCap();
                case 3 -> v.getCurrentQuantity();   // NEW
                case 4 -> v.getWeightCap();
                case 5 -> v.getCurrentWeight();     // NEW
                default -> null;
            };
        }


        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    }
    private static class TripTableModel extends AbstractTableModel {

        private final String[] columns = {
            "Trip ID", "Vehicle", "Driver", "Route", "Status", "Date", "Departure", "Arrival"
        };

        private List<Trip> trips;

        public TripTableModel(List<Trip> trips) {
            this.trips = trips;
        }

        public void setTrips(List<Trip> trips) {
            this.trips = trips;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return trips == null ? 0 : trips.size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public String getColumnName(int col) {
            return columns[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            Trip t = trips.get(row);

            return switch (col) {
                case 0 -> t.getTripID();
                case 1 -> t.getVehicleNo();
                case 2 -> t.getDriverID();
                case 3 -> t.getRouteID();
                case 4 -> t.getStatus();
                case 5 -> t.getDate();
                case 6 -> t.getDepartureTime();
                case 7 -> t.getArrivalTime();
                default -> null;
            };
        }

        @Override
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    }


    // ---------------------------------------------------------------------
    //  MAIN FOR TESTING
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(ManagerView::new);
    }
}
