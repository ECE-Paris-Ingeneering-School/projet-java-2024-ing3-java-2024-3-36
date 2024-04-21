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

public class ClientsVue extends JFrame{

    private JButton btnAjouterClient;
    private JButton btnTrouverClient;
    private JButton btnListerClients;
    private JButton btnMettreAJourClient;
    private JButton btnSupprimerClient;
    private JButton btnRetour;




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
    public ClientsVue(ActionListener controller) {

        setTitle("Gérer les clients");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        btnAjouterClient = createStyledButton("Ajouter un client");
        btnAjouterClient.addActionListener(controller);
        add(btnAjouterClient);

        btnTrouverClient = createStyledButton("Trouver un client par ID");
        btnTrouverClient.addActionListener(controller);
        add(btnTrouverClient);

        btnListerClients = createStyledButton("Lister tous les clients");
        btnListerClients.addActionListener(controller);
        add(btnListerClients);

        btnMettreAJourClient = createStyledButton("Mettre à jour un client");
        btnMettreAJourClient.addActionListener(controller);
        add(btnMettreAJourClient);

        btnSupprimerClient = createStyledButton("Supprimer un client");
        btnSupprimerClient.addActionListener(controller);
        add(btnSupprimerClient);

        btnRetour = createStyledButton("Retour au menu principal");
        btnRetour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeWindow();
            }
        });
        add(btnRetour);

        setSize(400, 400);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);

    }

    public void closeWindow() {
        dispose();
    }

}
