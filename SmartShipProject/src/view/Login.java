import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel loginPanel;
    
    private void addToGridBag(JPanel p, Component c, int x, int y, int w, int h/*, boolean fill*/) {
            GridBagConstraints gc = new GridBagConstraints();
            gc.gridx = x;
            gc.gridy = y;
            gc.gridwidth = w;
            gc.gridheight = h;
            // Integer a = (fill) ? gc.fill = GridBagConstraints.HORIZONTAL : null;
            p.add(c, gc);
    }

    public Login() {
        setTitle("Login System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        loginPanel = new JPanel(cardLayout);

        // Create the two cards
        JPanel signInPanel = new JPanel(new GridBagLayout());
        JPanel signUpPanel = new JPanel(new GridBagLayout());

        loginPanel.add(signInPanel, "Login");
        loginPanel.add(signUpPanel, "Register");
        mainPanel.add(loginPanel, "LoginPage");

        add(mainPanel);
        cardLayout.show(mainPanel, "LoginPage"); // start with sign-in

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Sign In", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        final JTextField usernameFieldL = new JTextField(15);
        final JPasswordField passwordFieldL = new JPasswordField(15);
        JButton signInBtn = new JButton("Sign In");
        JButton goToSignUpBtn = new JButton("Create Account");

        addToGridBag(signInPanel, title, 0, 0, 2, 1);
        addToGridBag(signInPanel, new JLabel("Username:"), 0, 1, 1, 1);
        addToGridBag(signInPanel, usernameFieldL, 1, 1, 1, 1);


        addToGridBag(signInPanel, new JLabel("Password:"), 0, 2, 1, 1);
        addToGridBag(signInPanel, passwordFieldL, 1, 2, 1, 1);

        addToGridBag(signInPanel, signInBtn, 0, 3, 2, 1);
        addToGridBag(signInPanel, goToSignUpBtn, 0, 4, 2, 1);

        goToSignUpBtn.addActionListener(e -> cardLayout.show(loginPanel, "Register"));

        signInBtn.addActionListener(e -> {
            String user = usernameFieldL.getText();
            String pass = new String(passwordFieldL.getPassword());
            // Dummy check
            if (user.equals("admin") && pass.equals("1234")) {
                JOptionPane.showMessageDialog(this, "Welcome " + user + "!");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        });

        // sign up
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        title = new JLabel("Sign Up", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        final JTextField usernameFieldR = new JTextField(15);
        final JPasswordField passwordFieldR = new JPasswordField(15);
        final JPanel userTypeBtnPanel = new JPanel();
        JRadioButton userType1 = new JRadioButton();
        JButton signUpBtn = new JButton("Register");
        JButton goToSignInBtn = new JButton("Back to Sign In");

        addToGridBag(signUpPanel, title, 0, 0, 2, 1);
        addToGridBag(signUpPanel, new JLabel("TRN:"), 0, 1, 1, 1);
        addToGridBag(signUpPanel, usernameFieldR, 1, 1, 1, 1);

        addToGridBag(signUpPanel, new JLabel("Password:"), 0, 2, 1, 1);
        addToGridBag(signUpPanel, passwordFieldR, 1, 2, 1, 1);
        addToGridBag(signUpPanel, signUpBtn, 0, 3, 2, 1);

        addToGridBag(signUpPanel, goToSignInBtn, 0, 4, 2, 1);

        goToSignInBtn.addActionListener(e -> cardLayout.show(loginPanel, "Login"));

        signUpBtn.addActionListener(e -> {
            String user = usernameFieldR.getText();
            String pass = new String(passwordFieldR.getPassword());
            JOptionPane.showMessageDialog(this, "Account created for " + user + "!");
            cardLayout.show(loginPanel, "Login");
        });
        
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Login();
    }
}
