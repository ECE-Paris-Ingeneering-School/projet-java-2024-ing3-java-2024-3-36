package Vue;

import Controller.FakePaymentPage;
import Controller.PageAccueil;
import Modele.ImplementationsDAO.FilmDAOImpl;
import Modele.InterfaceDAO.ClientDAO;
import Modele.Objets.Billet;
import Modele.Objets.Client;
import Modele.Objets.Seance;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AcceuilVue extends JFrame {
    public JButton btnGererBillets, btnGererClients, btnGererEmployes, btnGererFilms, btnGererSeances, btnOffres, btnQuitter;
    public JPanel mainPanel;

    public AcceuilVue(String mail,int userID, ActionListener controller) {


        setTitle("Page d'Accueil");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnGererBillets = createStyledButton("Gérer mes billets");
        btnGererBillets.setActionCommand("gerer_billets");
        btnGererBillets.addActionListener(controller);

        btnGererClients = createStyledButton("Gérer les clients");
        btnGererClients.setActionCommand("gerer_clients");
        btnGererClients.addActionListener(controller);

        btnGererEmployes = createStyledButton("Gérer les employés");
        btnGererEmployes.setActionCommand("gerer_employes");
        btnGererEmployes.addActionListener(controller);

        btnGererFilms = createStyledButton("Gérer les films");
        btnGererFilms.setActionCommand("gerer_films");
        btnGererFilms.addActionListener(controller);

        btnGererSeances = createStyledButton("Gérer les Séances");
        btnGererSeances.setActionCommand("gerer_seances");
        btnGererSeances.addActionListener(controller);

        btnOffres = createStyledButton("Ajouter des offres");
        btnOffres.setActionCommand("gerer_offres");
        btnOffres.addActionListener(controller);

        btnQuitter = createStyledButton("Quitter");
        btnQuitter.setActionCommand("quitter");
        btnQuitter.addActionListener(controller);

        // Création du label
        JLabel golmonLabel = new JLabel("Golmon Pathé");
        golmonLabel.setHorizontalAlignment(JLabel.CENTER);
        golmonLabel.setVerticalAlignment(JLabel.CENTER); // Centrer verticalement
        Font font = golmonLabel.getFont();
        golmonLabel.setFont(new Font(font.getName(), Font.BOLD, 36));
        golmonLabel.setBackground(Color.DARK_GRAY);

        // Panneau pour le label
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setBackground(new Color(255, 215, 0));
        labelPanel.setPreferredSize(new Dimension(getWidth(), 100));
        labelPanel.add(golmonLabel, BorderLayout.CENTER);

        // Panneau pour les boutons
        JPanel buttonPanel = createButtonPanel(mail);

        // Création du panneau pour les affiches
        JPanel posterPanel = PageAccueil.createPosterPanel();

        // Création du panneau pour le texte en bas de la fenêtre
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Utilisation d'un FlowLayout pour centrer le texte
        footerPanel.setBackground(new Color(255, 215, 0));

        // Création du texte à afficher
        String footerText = "© 2024 Golmon Pathé. Tous droits réservés.";

        // Création du composant texte
        JLabel footerLabel = new JLabel(footerText);
        footerLabel.setForeground(Color.BLACK); // Couleur du texte
        footerPanel.add(footerLabel);

        // Ajout des panneaux au conteneur principal
        // Ajout des panneaux au conteneur principal avec un BorderLayout
        // Panneau pour contenir les éléments
        JPanel mainPanel = new JPanel(new BorderLayout());

// Ajouter le labelPanel en haut du mainPanel
        mainPanel.add(labelPanel, BorderLayout.NORTH);

// Créer un panneau pour contenir les boutons et les affiches
        JPanel centerPanel = new JPanel(new BorderLayout());

// Ajouter les boutons au centre du centerPanel
        centerPanel.add(buttonPanel, BorderLayout.NORTH);

// Ajouter les affiches en dessous des boutons dans le centerPanel
        centerPanel.add(posterPanel, BorderLayout.CENTER);

// Ajouter le centerPanel au centre du mainPanel
        mainPanel.add(centerPanel, BorderLayout.CENTER);

// Ajouter le footerPanel en bas du mainPanel
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

// Ajouter le mainPanel au contenu de la JFrame
        add(mainPanel);



        // Maximiser la fenêtre
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.DARK_GRAY);
        setVisible(true);
    }

    private void prepareGUI() {
        mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

    }

    public JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(255, 215, 0)); // Golden color
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 100));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return button;
    }

    private JPanel createButtonPanel(String mail) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.DARK_GRAY);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (!mail.equals("admin@admin.fr")) {
            btnGererBillets.setPreferredSize(new Dimension((screenSize.width / 2) - 10, 100));
            buttonPanel.add(btnGererBillets);
            btnQuitter.setPreferredSize(new Dimension((screenSize.width / 2) - 10, 100));
            buttonPanel.add(btnQuitter);
        } else {
            btnQuitter.setPreferredSize(new Dimension((screenSize.width / 7) - 10, 100));
            btnGererClients.setPreferredSize(new Dimension((screenSize.width / 7) - 10, 100));
            btnGererEmployes.setPreferredSize(new Dimension((screenSize.width / 7) - 10, 100));
            btnGererFilms.setPreferredSize(new Dimension((screenSize.width / 7) - 10, 100));
            btnGererSeances.setPreferredSize(new Dimension((screenSize.width / 7) - 10, 100));
            btnGererBillets.setPreferredSize(new Dimension((screenSize.width / 7) - 10, 100));
            btnOffres.setPreferredSize(new Dimension((screenSize.width / 7) - 10, 100));

            JPanel adminPanel = new JPanel(new GridLayout(1, 7));
            adminPanel.setBackground(Color.DARK_GRAY);
            adminPanel.setPreferredSize(new Dimension(screenSize.width, 100));

            adminPanel.add(btnGererClients);
            adminPanel.add(btnGererEmployes);
            adminPanel.add(btnGererFilms);
            adminPanel.add(btnGererSeances);
            adminPanel.add(btnGererBillets);
            adminPanel.add(btnOffres);
            adminPanel.add(btnQuitter);


            buttonPanel.add(adminPanel);
        }

        return buttonPanel;
    }


}