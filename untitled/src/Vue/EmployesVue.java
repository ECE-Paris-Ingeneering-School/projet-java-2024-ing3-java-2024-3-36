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

public class EmployesVue extends JFrame{


    private JButton btnAjouterEmploye;
    private JButton btnTrouverEmploye;
    private JButton btnListerEmployes;
    private JButton btnMettreAJourEmploye;
    private JButton btnSupprimerEmploye;
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
    public EmployesVue(ActionListener controller) {

        setTitle("Gérer les employés");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        btnAjouterEmploye = createStyledButton("Ajouter un employé");
        btnAjouterEmploye.addActionListener(controller);
        add(btnAjouterEmploye);

        btnTrouverEmploye = createStyledButton("Trouver un employé par ID");
        btnTrouverEmploye.addActionListener(controller);
        add(btnTrouverEmploye);

        btnListerEmployes = createStyledButton("Lister tous les employés");
        btnListerEmployes.addActionListener(controller);
        add(btnListerEmployes);

        btnMettreAJourEmploye = createStyledButton("Mettre à jour un employé");
        btnMettreAJourEmploye.addActionListener(controller);
        add(btnMettreAJourEmploye);

        btnSupprimerEmploye = createStyledButton("Supprimer un employé");
        btnSupprimerEmploye.addActionListener(controller);
        add(btnSupprimerEmploye);

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

    // Method to close the window
    public void closeWindow() {
        dispose();
    }
}
