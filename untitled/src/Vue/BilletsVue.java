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

public class BilletsVue extends JFrame{

    private JButton btnAjouterBillet;

    private JButton btnListerBillets;

    private JButton btnSupprimerBillet;
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
    public BilletsVue(ActionListener controller, boolean admin) {

        setTitle("Gérer Mes billets");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        // Initialisation des boutons
        btnAjouterBillet = createStyledButton("Réserver une séance");
        btnAjouterBillet.addActionListener(controller);
        add(btnAjouterBillet);




            if (!admin) {
                btnListerBillets = createStyledButton("Lister toutes mes séances");
                btnListerBillets.addActionListener(controller);
                add(btnListerBillets);
            }
            else {
                btnListerBillets = createStyledButton("Lister tous les billets");
                btnListerBillets.addActionListener(controller);
                add(btnListerBillets);
            }


        btnSupprimerBillet = createStyledButton("Supprimer un billet");
        btnSupprimerBillet.addActionListener(controller);
        add(btnSupprimerBillet);

        btnRetour = createStyledButton("Retour au menu principal");
        btnRetour.addActionListener(controller);
        add(btnRetour);

        // Taille de la fenêtre
        setSize(400, 400);
        setVisible(true);
    }

}
