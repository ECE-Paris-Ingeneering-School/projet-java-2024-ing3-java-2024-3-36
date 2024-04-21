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

public class FilmsVue extends JFrame{


    private JButton btnAjouterFilm;
    private JButton btnTrouverFilm;
    private JButton btnListerFilms;
    private JButton btnMettreAJourFilm;
    private JButton btnSupprimerFilm;
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
    public FilmsVue(ActionListener controller) {

        setTitle("Gérer les films");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        btnAjouterFilm = createStyledButton("Ajouter un film");
        btnAjouterFilm.addActionListener(controller);
        add(btnAjouterFilm);

        btnTrouverFilm = createStyledButton("Trouver un film par ID");
        btnTrouverFilm.addActionListener(controller);
        add(btnTrouverFilm);

        btnListerFilms = createStyledButton("Lister tous les films");
        btnListerFilms.addActionListener(controller);
        add(btnListerFilms);

        btnMettreAJourFilm = createStyledButton("Mettre à jour un film");
        btnMettreAJourFilm.addActionListener(controller);
        add(btnMettreAJourFilm);

        btnSupprimerFilm = createStyledButton("Supprimer un film");
        btnSupprimerFilm.addActionListener(controller);
        add(btnSupprimerFilm);

        btnRetour = createStyledButton("Retour au menu principal");
        btnRetour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeWindow();
            }
        });
        add(btnRetour);

        setSize(400, 400);
        setVisible(true);
    }

    // Method to close the window
    public void closeWindow() {
        dispose();
    }
}
