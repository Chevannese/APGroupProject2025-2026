package smartshipurl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/
class Driver {
    private String driverID;
    private String name;
    private int age;

    public Driver(String driverID, String name, int age) {
        this.driverID = driverID;
        this.name = name;
        this.age = age;
    }

    public Driver(Driver d) {
        this.driverID = d.driverID;
        this.name = d.name;
        this.age = d.age;
    }

    public String getDriverID() { return driverID; }
    public String getName() { return name; }
    public int getAge() { return age; }

    public boolean login(String username, String password) {
        // Datbase calls go here lmao
        return username.equals("driver1") && password.equals("pass123");
    }

    public ArrayList<String> getPackages() {
    	  // Datbase calls go here lmao
        ArrayList<String> packages = new ArrayList<>();
        packages.add("PKG001 - Pending Delivery");
        packages.add("PKG002 - Delivered");
        return packages;
    }

    public void updatePackageStatus(String pkgID, String status) {
    	  // Datbase calls go here lmao
        System.out.println("Package " + pkgID + " updated to " + status);
    }

    public String getRoute() {
    	  // Datbase calls go here lmao
        return "Route A: Warehouse → Stop 1 → Stop 2 → Destination";
    }

    public String getVehicleInfo() {
    	  // Datbase calls go here lmao
        return "Vehicle ID: VH001\nModel: Toyota Hiace\nPlate: 1234-JM\nStatus: Active";
    }
}

// GUI class for Driver View
public class DriverView extends JPanel {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Driver driver;

    public DriverView() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        setLayout(new BorderLayout());

      
        mainPanel.add(loginPanel(), "login");
        mainPanel.add(driverDashboard(), "dashboard");

        add(mainPanel, BorderLayout.CENTER);
        cardLayout.show(mainPanel, "login");
    }

    private JPanel loginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblTitle = new JLabel("Driver Login");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel lblUser = new JLabel("Username:");
        JTextField txtUser = new JTextField(15);

        JLabel lblPass = new JLabel("Password:");
        JPasswordField txtPass = new JPasswordField(15);

        JButton btnLogin = new JButton("Login");

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblUser, gbc);
        gbc.gridx = 1;
        panel.add(txtUser, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblPass, gbc);
        gbc.gridx = 1;
        panel.add(txtPass, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);

        btnLogin.addActionListener(e -> {
            Driver temp = new Driver("D001", "John Doe", 29);
            if (temp.login(txtUser.getText(), new String(txtPass.getPassword()))) {
                driver = temp;
                cardLayout.show(mainPanel, "dashboard");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!");
            }
        });

        return panel;
    }

    private JPanel driverDashboard() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel lblWelcome = new JLabel("Welcome, Driver");
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 16));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblWelcome, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);

        JButton btnViewPackages = new JButton("View Packages");
        JButton btnUpdatePackage = new JButton("Update Package Status");
        JButton btnViewRoute = new JButton("View Route");
        JButton btnViewVehicle = new JButton("View Vehicle Info");
        JButton btnLogout = new JButton("Logout");

        gbc.gridx = 0; gbc.gridy = 0; buttonPanel.add(btnViewPackages, gbc);
        gbc.gridy = 1; buttonPanel.add(btnUpdatePackage, gbc);
        gbc.gridy = 2; buttonPanel.add(btnViewRoute, gbc);
        gbc.gridy = 3; buttonPanel.add(btnViewVehicle, gbc);
        gbc.gridy = 4; buttonPanel.add(btnLogout, gbc);

        panel.add(buttonPanel, BorderLayout.CENTER);

      
        btnViewPackages.addActionListener(e -> showPackages());
        btnUpdatePackage.addActionListener(e -> updatePackageStatus());
        btnViewRoute.addActionListener(e -> viewRoute());
        btnViewVehicle.addActionListener(e -> viewVehicle());
        btnLogout.addActionListener(e -> cardLayout.show(mainPanel, "login"));

        return panel;
    }

    private void showPackages() {
        if (driver == null) return;
        ArrayList<String> pkgs = driver.getPackages();
        JOptionPane.showMessageDialog(this, String.join("\n", pkgs), "Driver Packages", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updatePackageStatus() {
        String pkgID = JOptionPane.showInputDialog(this, "Enter Package ID:");
        String status = JOptionPane.showInputDialog(this, "Enter New Status:");
        if (pkgID != null && status != null) {
            driver.updatePackageStatus(pkgID, status);
            JOptionPane.showMessageDialog(this, "Status updated successfully!");
        }
    }

    private void viewRoute() {
        JOptionPane.showMessageDialog(this, driver.getRoute(), "Assigned Route", JOptionPane.INFORMATION_MESSAGE);
    }

    private void viewVehicle() {
        JOptionPane.showMessageDialog(this, driver.getVehicleInfo(), "Vehicle Information", JOptionPane.INFORMATION_MESSAGE);
    }

    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Smart Ship - Driver View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.add(new DriverView());
        frame.setVisible(true);
    }
}
