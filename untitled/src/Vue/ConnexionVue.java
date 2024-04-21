package Vue;

import Controller.ConnexionPage;

import javax.swing.*;
import java.awt.*;

public class ConnexionVue extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createAccountButton;

    public ConnexionVue(ConnexionPage controller) {
        setTitle("Connexion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        // Email setup
        JLabel emailLabel = new JLabel("Email:");
        add(emailLabel);
        emailField = new JTextField();
        add(emailField);

        // Password setup
        JLabel passwordLabel = new JLabel("Mot de passe:");
        add(passwordLabel);
        passwordField = new JPasswordField();
        add(passwordField);

        // Login button setup
        loginButton = createStyledButton("Se connecter");
        loginButton.addActionListener(controller);
        add(loginButton);

        // Create account button setup
        createAccountButton = createStyledButton("Créer un compte");
        createAccountButton.addActionListener(controller);
        add(createAccountButton);

        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(255, 215, 0));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 100));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return button;
    }

    // Getters
    public JTextField getEmailField() {
        return emailField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void FermerFenetre(){dispose();}
}
