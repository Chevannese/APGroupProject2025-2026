

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.*;
import model.Driver;

public class DriverView extends JPanel {
    private CardLayout cardLayout;
    private JPanel cards;
    private Driver driver;

    public DriverView() {
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        JPanel loginPanel = createLoginPanel();
        JPanel dashboardPanel = createDashboardPanel();

        cards.add(loginPanel, "Login");
        cards.add(dashboardPanel, "Dashboard");

        setLayout(new BorderLayout());
        add(cards, BorderLayout.CENTER);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(15);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");

        gbc.gridx = 0; gbc.gridy = 0; panel.add(userLabel, gbc);
        gbc.gridx = 1; panel.add(userField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(passLabel, gbc);
        gbc.gridx = 1; panel.add(passField, gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(loginButton, gbc);

        loginButton.addActionListener((ActionEvent e) -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            driver = new Driver(username, "TEMP-LICENSE", 101);
            if (driver.login(username, password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                cardLayout.show(cards, "Dashboard");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.");
            }
        });

        return panel;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton viewPackagesBtn = new JButton("View Packages");
        JButton updateStatusBtn = new JButton("Update Package Status");
        JButton viewRouteBtn = new JButton("View Route");
        JButton viewVehicleBtn = new JButton("View Vehicle");
        JButton logoutBtn = new JButton("Logout");

        gbc.gridx = 0; gbc.gridy = 0; panel.add(viewPackagesBtn, gbc);
        gbc.gridy++; panel.add(updateStatusBtn, gbc);
        gbc.gridy++; panel.add(viewRouteBtn, gbc);
        gbc.gridy++; panel.add(viewVehicleBtn, gbc);
        gbc.gridy++; panel.add(logoutBtn, gbc);

        viewPackagesBtn.addActionListener(e -> {
            if (driver != null) {
                List<String> packages = driver.getPackages();
                JOptionPane.showMessageDialog(this, String.join("\n", packages));
            }
        });

        updateStatusBtn.addActionListener(e -> {
            if (driver != null) {
                String pkg = JOptionPane.showInputDialog("Enter Package ID:");
                String newStatus = JOptionPane.showInputDialog("Enter New Status:");
                driver.updatePackageStatus(pkg, newStatus);
            }
        });

        viewRouteBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, driver.getRoute()));

        viewVehicleBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, driver.getVehicleInfo()));

        logoutBtn.addActionListener(e -> {
            driver = null;
            cardLayout.show(cards, "Login");
        });

        return panel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("SmartShip - Driver View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.add(new DriverView());
        frame.setVisible(true);
    }
}
