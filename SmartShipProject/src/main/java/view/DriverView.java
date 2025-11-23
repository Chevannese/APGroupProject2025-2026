package view;
import javax.swing.*;
import java.awt.*;
import model.Driver;
import model.User;
import model.Shipment;
import model.Route;
import model.Vehicle;
import network.Client;
import java.util.List;
public class DriverView extends JFrame {
   private final Driver driver;
   private final Client client;
   public DriverView(User user) {
       if (!(user instanceof Driver)) {
           throw new IllegalArgumentException("User is not a Driver");
       }
       this.driver = (Driver) user;
       this.client = new Client();
       setTitle("SmartShip - Driver Dashboard");
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setSize(800, 500);
       setLayout(new BorderLayout());
       setLocationRelativeTo(null);
       setJMenuBar(createMenuBar());
       add(createHeader(), BorderLayout.NORTH);
       add(createMainPanel(), BorderLayout.CENTER);
       setVisible(true);
   }
   private JPanel createHeader() {
       JPanel header = new JPanel();
       header.setLayout(new GridLayout(2, 1));
       JLabel title = new JLabel("Driver Dashboard", SwingConstants.CENTER);
       title.setFont(new Font("Arial", Font.BOLD, 22));
       JLabel welcome = new JLabel("Welcome, " + driver.getFirstName() + " " + driver.getLastName(),
               SwingConstants.CENTER);
       header.add(title);
       header.add(welcome);
       return header;
   }
   private JPanel createMainPanel() {
       JPanel panel = new JPanel(new GridBagLayout());
       GridBagConstraints gbc = new GridBagConstraints();
       gbc.insets = new Insets(15, 15, 15, 15);
       gbc.fill = GridBagConstraints.HORIZONTAL;
       JButton btnPackages = new JButton("View Assigned Packages");
       JButton btnUpdateStatus = new JButton("Update Package Status");
       JButton btnViewRoute = new JButton("View Route");
       JButton btnViewVehicle = new JButton("View Vehicle");
       gbc.gridx = 0; gbc.gridy = 0; panel.add(btnPackages, gbc);
       gbc.gridy++; panel.add(btnUpdateStatus, gbc);
       gbc.gridy++; panel.add(btnViewRoute, gbc);
       gbc.gridy++; panel.add(btnViewVehicle, gbc);
       btnPackages.addActionListener(e -> viewPackages());
       btnUpdateStatus.addActionListener(e -> updatePackageStatus());
       btnViewRoute.addActionListener(e -> viewRoute());
       btnViewVehicle.addActionListener(e -> viewVehicle());
       return panel;
   }
   private void viewPackages() {
       try {
           List<Shipment> shipments = client.getDriverShipments(driver.getTrn());
           if (shipments == null || shipments.isEmpty()) {
               JOptionPane.showMessageDialog(this, "No assigned packages.");
               return;
           }
           StringBuilder sb = new StringBuilder();
           for (Shipment s : shipments) {
               sb.append("Package #").append(s.getPackageNo())
                 .append(" - ").append(s.getStatus()).append("\n");
           }
           JOptionPane.showMessageDialog(this, sb.toString(), "Assigned Packages", JOptionPane.INFORMATION_MESSAGE);
       } catch (Exception e) {
           JOptionPane.showMessageDialog(this, "Error fetching packages:\n" + e.getMessage());
       }
   }
   private void updatePackageStatus() {
       String pkg = JOptionPane.showInputDialog(this, "Enter Package ID:");
       if (pkg == null || pkg.isBlank()) return;
       String status = JOptionPane.showInputDialog(this, "Enter New Status:");
       if (status == null || status.isBlank()) return;
       try {
           String response = client.updateShipmentStatus(pkg, status);
           JOptionPane.showMessageDialog(this, response.equals("success") ?
                   "Status updated successfully." : "Update failed: " + response);
       } catch (Exception e) {
           JOptionPane.showMessageDialog(this, "Error updating status:\n" + e.getMessage());
       }
   }
   private void viewRoute() {
       try {
           Route route = client.getDriverRoute(driver.getTrn());
           if (route == null) {
               JOptionPane.showMessageDialog(this, "No route assigned.");
               return;
           }
           JOptionPane.showMessageDialog(this,
                   "Route:\n" + route.getOrigin() + " → " + route.getDestination(),
                   "Route Information", JOptionPane.INFORMATION_MESSAGE);
       } catch (Exception e) {
           JOptionPane.showMessageDialog(this, "Error fetching route:\n" + e.getMessage());
       }
   }
   private void viewVehicle() {
       try {
           Vehicle v = client.getDriverVehicle(driver.getTrn());
           if (v == null) {
               JOptionPane.showMessageDialog(this, "No vehicle assigned.");
               return;
           }
           JOptionPane.showMessageDialog(this,
                   "Vehicle Details:\n" +
                   "ID: " + v.getVehicleNo() + "\n" +
                   "Model: " + v.getVehicleName() + "\n" +
                   "Capacity: " + v.getWeightCap(),
                   "Vehicle Information", JOptionPane.INFORMATION_MESSAGE);
       } catch (Exception e) {
           JOptionPane.showMessageDialog(this, "Error fetching vehicle:\n" + e.getMessage());
       }
   }
   private JMenuBar createMenuBar() {
       JMenuBar menuBar = new JMenuBar();
       JMenu file = new JMenu("File");
       JMenuItem logout = new JMenuItem("Logout");
       JMenuItem exit = new JMenuItem("Exit");
       logout.addActionListener(e -> {
           this.dispose();
           new Login(); // back to login
       });
       exit.addActionListener(e -> System.exit(0));
       file.add(logout);
       file.addSeparator();
       file.add(exit);
       menuBar.add(file);
       return menuBar;
   }
}
