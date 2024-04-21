package Vue;


import Modele.InterfaceDAO.*;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ConnexionVue extends JFrame{

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createAccountButton;




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
    public ConnexionVue(ActionListener controller) {

        setTitle("Connexion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        JLabel emailLabel = new JLabel("Email:");
        add(emailLabel);
        emailField = new JTextField();
        add(emailField);

        JLabel passwordLabel = new JLabel("Mot de passe:");
        add(passwordLabel);
        passwordField = new JPasswordField();
        add(passwordField);

        loginButton = new JButton("Se connecter");
        loginButton.addActionListener(controller);
        add(loginButton);

        createAccountButton = new JButton("Créer un compte");
        createAccountButton.addActionListener(controller);
        add(createAccountButton);

        setSize(400, 300); // Définir une taille moyenne
        setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran
        setVisible(true);

    }

}
