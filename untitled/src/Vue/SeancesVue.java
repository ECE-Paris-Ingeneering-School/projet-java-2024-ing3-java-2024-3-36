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
public class SeancesVue extends JFrame {

    private JButton btnAjouterSeance;
    private JButton btnTrouverSeance;
    private JButton btnListerSeances;
    private JButton btnMettreAJourSeance;
    private JButton btnSupprimerSeance;
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

    public SeancesVue(ActionListener controller) {
        setTitle("Gérer les séances");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        initializeButtons(controller);
        setSize(400, 400);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    private void initializeButtons(ActionListener controller) {
        btnAjouterSeance = createStyledButton("Ajouter une séance");
        btnAjouterSeance.addActionListener(controller);
        add(btnAjouterSeance);

        btnTrouverSeance = createStyledButton("Trouver une séance par ID");
        btnTrouverSeance.addActionListener(controller);
        add(btnTrouverSeance);

        btnListerSeances = createStyledButton("Lister toutes les séances");
        btnListerSeances.addActionListener(controller);
        add(btnListerSeances);

        btnMettreAJourSeance = createStyledButton("Mettre à jour une séance");
        btnMettreAJourSeance.addActionListener(controller);
        add(btnMettreAJourSeance);

        btnSupprimerSeance = createStyledButton("Supprimer une séance");
        btnSupprimerSeance.addActionListener(controller);
        add(btnSupprimerSeance);

        btnRetour = createStyledButton("Retour au menu principal");
        btnRetour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeWindow();
            }
        });
        add(btnRetour);
    }

    // Method to close the window
    public void closeWindow() {
        dispose();
    }
}
