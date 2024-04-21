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

public class OffresVue extends JFrame{

    private JButton btnRetour;
    private JButton btnOffreRegulier;
    private JButton btnOffreSenior;
    private JButton btnOffreEnfant;

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
    public OffresVue(ActionListener controller) {

        setTitle("Gérer les Offres");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        btnOffreRegulier = createStyledButton("Modifier Offre Régulier");
        btnOffreRegulier.addActionListener(controller);
        add(btnOffreRegulier);

        btnOffreSenior = createStyledButton("Modifier Offre Senior");
        btnOffreSenior.addActionListener(controller);
        add(btnOffreSenior);

        btnOffreEnfant = createStyledButton("Modifier Offre Enfant");
        btnOffreEnfant.addActionListener(controller);
        add(btnOffreEnfant);

        btnRetour = createStyledButton("Retour au menu principal");
        btnRetour.addActionListener(controller);
        add(btnRetour);

        setSize(400, 400);
        setVisible(true);

    }

}
