package Controller;

import Modele.ImplementationsDAO.FilmDAOImpl;
import Modele.InterfaceDAO.*;
import Modele.Objets.Billet;
import Modele.Objets.Seance;
import Vue.AcceuilVue;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.*;

import Vue.*;

public class PageAccueil extends JFrame implements UIUpdater, ActionListener {


    public void refreshUI() {
        createPosterPanel(); // Assume this method refreshes the panel where posters are displayed
        this.revalidate();
        this.repaint();
    }

    private UIUpdater uiUpdater;
    private static int userID = 0;
    private JButton btnGererBillets;
    private JButton btnGererClients;
    private JButton btnGererEmployes;
    private JButton btnGererFilms;
    private JButton btnGererSeances;
    private JButton btnOffres;
    private JButton btnQuitter;
    private static ClientDAO clientDAO;
    private FilmDAO filmDAO;
    private EmployeDAO employeDAO;
    private static SeanceDAO seanceDAO;
    private static BilletDAO billetDAO;
    private static OffresDAO offresDAO;

    private Scanner scanner;

    public PageAccueil(int userID, ClientDAO clientDAO, FilmDAO filmDAO, EmployeDAO employeDAO, SeanceDAO seanceDAO, BilletDAO billetDAO, OffresDAO offresDAO, Scanner scanner) throws Exception {
        this.userID = userID;
        this.clientDAO = clientDAO;
        this.filmDAO = filmDAO;
        this.employeDAO = employeDAO;
        this.seanceDAO = seanceDAO;
        this.billetDAO = billetDAO;
        this.offresDAO = offresDAO;
        this.scanner = scanner;



        String mail = clientDAO.trouverEmailParId(userID);
        new AcceuilVue(mail,userID,this);
    }


    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "gerer_billets":
                new GererBilletsPage(billetDAO, clientDAO, filmDAO, employeDAO, seanceDAO, offresDAO, scanner, userID);
                break;
            case "gerer_clients":
                new GererClientsPage(clientDAO, billetDAO, filmDAO, employeDAO, seanceDAO, offresDAO, scanner, userID);
                break;
            case "gerer_employes":
                new GererEmployesPage(employeDAO, clientDAO, filmDAO, seanceDAO, billetDAO, offresDAO, scanner, userID);
                break;
            case "gerer_films":
                new GererFilmsPage(filmDAO, clientDAO, employeDAO, seanceDAO, billetDAO, offresDAO, scanner, userID, uiUpdater);
                break;
            case "gerer_seances":
                new GererSeancesPage(seanceDAO, clientDAO, filmDAO, employeDAO, billetDAO, offresDAO, scanner, userID);
                break;
            case "gerer_offres":
                new GererOffresPage(offresDAO, clientDAO, filmDAO, employeDAO, seanceDAO, billetDAO, scanner, userID);
                break;
            case "quitter":
                System.out.println("Au revoir !");
                System.exit(0);
                break;
            default:
                break;
        }
    }

    public List GererAffiche(List<ImageIcon> affiches,List<String> titres){
        FilmDAOImpl filmDAO = new FilmDAOImpl();

        // Récupérer les données d'image à partir de la méthode récupérerAfficheBytes() de votre DAO
        List<byte[]> affichesBytes = filmDAO.recupererAfficheBytes();


        // Parcourir la liste des données d'image
        for (byte[] imageData : affichesBytes) {
            if (imageData == null) {
                continue;
            }
            try (ByteArrayInputStream bis = new ByteArrayInputStream(imageData)) {
                Image image = ImageIO.read(bis);
                if (image == null) {
                    // Si l'image est nulle, passer à l'itération suivante sans ajouter d'image à la liste des affiches
                    continue;
                }
                System.out.println("Conversion du BLOB en image réussie!");
                // Ajouter l'image à la liste des affiches en tant qu'ImageIcon
                int maxWidth = 800; // Définir la largeur maximale souhaitée
                int maxHeight = 700; // Définir la hauteur maximale souhaitée
                Image scaledImage = image.getScaledInstance(maxWidth, maxHeight, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);

                // Ajouter l'image redimensionnée à la liste des affiches en tant qu'ImageIcon
                affiches.add(scaledIcon);
                // Récupérer le titre du film correspondant à cette affiche
                String titre = filmDAO.recupererTitreParIndex(affiches.size() - 1); // Supposons que vous ayez une méthode pour récupérer le titre par index dans votre DAO
                titres.add(titre);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de la conversion du BLOB en image : " + e.getMessage());
            }
        }

        return affiches;
    }


    public static JPanel createPosterPanel() {
        JPanel posterPanel = new JPanel(new BorderLayout()); // Utilisation d'un BorderLayout pour placer les boutons de navigation
        posterPanel.setBackground(Color.DARK_GRAY);

        JButton btnPrevious = new JButton("Précédent");
        JButton btnNext = new JButton("Suivant");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.add(btnPrevious);
        buttonPanel.add(btnNext);

        posterPanel.add(buttonPanel, BorderLayout.NORTH); // Ajouter le panneau de boutons en haut du posterPanel

        JPanel imagesPanel = new JPanel(new BorderLayout()); // Utilisation d'un BorderLayout pour placer les affiches
        imagesPanel.setBackground(Color.DARK_GRAY);

        JPanel centerPanel = new JPanel(new GridLayout(0, 1, 10, 10)); // Utilisation d'un GridLayout pour aligner les affiches et les titres verticalement
        centerPanel.setBackground(Color.DARK_GRAY);
        imagesPanel.add(centerPanel, BorderLayout.CENTER);

        FilmDAOImpl filmDAO = new FilmDAOImpl();

        // Récupérer les données d'image à partir de la méthode récupérerAfficheBytes() de votre DAO
        List<byte[]> affichesBytes = filmDAO.recupererAfficheBytes();
        List<ImageIcon> affiches = new ArrayList<>(); // Liste pour stocker les affiches en tant qu'ImageIcon
        List<String> titres = new ArrayList<>(); // Liste pour stocker les titres des films

        // Parcourir la liste des données d'image
        for (byte[] imageData : affichesBytes) {
            if (imageData == null) {
                continue;
            }
            try (ByteArrayInputStream bis = new ByteArrayInputStream(imageData)) {
                Image image = ImageIO.read(bis);
                if (image == null) {
                    // Si l'image est nulle, passer à l'itération suivante sans ajouter d'image à la liste des affiches
                    continue;
                }
                System.out.println("Conversion du BLOB en image réussie!");
                // Ajouter l'image à la liste des affiches en tant qu'ImageIcon
                int maxWidth = 800; // Définir la largeur maximale souhaitée
                int maxHeight = 700; // Définir la hauteur maximale souhaitée
                Image scaledImage = image.getScaledInstance(maxWidth, maxHeight, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);

                // Ajouter l'image redimensionnée à la liste des affiches en tant qu'ImageIcon
                affiches.add(scaledIcon);
                // Récupérer le titre du film correspondant à cette affiche
                String titre = filmDAO.recupererTitreParIndex(affiches.size() - 1); // Supposons que vous ayez une méthode pour récupérer le titre par index dans votre DAO
                titres.add(titre);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de la conversion du BLOB en image : " + e.getMessage());
            }
        }

        // Index de l'affiche actuellement affichée
        AtomicInteger currentIndex = new AtomicInteger(0);

        // Méthode pour afficher l'affiche correspondant à l'index donné
        // Méthode pour afficher l'affiche correspondant à l'index donné
        Runnable showPoster = () -> {
            centerPanel.removeAll(); // Supprimer toutes les affiches et titres actuellement affichés
            centerPanel.revalidate(); // Actualiser le panneau d'affichage
            centerPanel.repaint();
            int index = currentIndex.get();
            if (index >= 0 && index < affiches.size()) {
                // Si l'index est valide, afficher l'affiche correspondante et son titre

                // Créer un JPanel pour chaque affiche et son titre
                // Créer le JPanel pour chaque affiche et son titre
                JPanel posterAndTitlePanel = new JPanel();
                posterAndTitlePanel.setBackground(Color.DARK_GRAY);
                posterAndTitlePanel.setLayout(new BoxLayout(posterAndTitlePanel, BoxLayout.Y_AXIS)); // Utiliser un BoxLayout avec un axe vertical

// Ajouter l'affiche
                JLabel posterLabel = new JLabel(affiches.get(index));
                posterLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrer horizontalement
                posterAndTitlePanel.add(posterLabel);
                // Ajouter un écouteur d'événements à chaque affiche
                posterLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Récupérer l'index de l'affiche cliquée
                        int selectedIndex = affiches.indexOf(posterLabel.getIcon());
                        String titreFilm = titres.get(selectedIndex);
                        String descriptionFilm;
                        try {
                            descriptionFilm = filmDAO.recupererDescriptionParTitre(titreFilm);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération de la description du film: " + ex.getMessage());
                            return;
                        }
                        // Récupérer l'ID du film associé à cette affiche
                        int selectedFilmId = 0;
                        try {
                            selectedFilmId = filmDAO.recupererIdFilmParTitre(titres.get(selectedIndex));
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        // Récupérer les séances du film sélectionné
                        List<Seance> seances = null;
                        try {
                            seances = seanceDAO.listerSeancesParFilm(selectedFilmId);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }

                        // Afficher uniquement les séances du film sélectionné
                        if (seances.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Aucune séance disponible pour ce film.");
                        } else {
                            // Création d'un modèle de tableau pour afficher les séances dans une JTable
                            String[] entetes = {"ID", "Film", "Date et heure", "Salle", "Prix", "Durée"};
                            Object[][] donnees = new Object[seances.size()][6];
                            final double[] prix = {8};
                            double reduction = 0;

                            try {
                                if((clientDAO.trouverEtatParId(userID)).equals("regulier")) {
                                    reduction = offresDAO.getOffreRegulier();
                                }
                                else if((clientDAO.trouverEtatParId(userID)).equals("senior")) {
                                    reduction = offresDAO.getOffreSenior();
                                }
                                else if((clientDAO.trouverEtatParId(userID)).equals("enfant")) {
                                    reduction = offresDAO.getOffreEnfant();
                                }
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }

                            for (int i = 0; i < seances.size(); i++) {
                                Seance seance = seances.get(i);
                                donnees[i][0] = seance.getId();
                                donnees[i][1] = titres.get(selectedIndex); // Utiliser le titre de l'affiche sélectionnée
                                donnees[i][2] = seance.getHeure();
                                donnees[i][3] = seance.getSalle();
                                if((seance.getSalle()).equals("Salle Standard")) {
                                    prix[0] = 8 - 8*(reduction/100);
                                }
                                else if((seance.getSalle()).equals("Salle 3D")) {
                                    prix[0] = 9 - 9*(reduction/100);
                                }
                                else if((seance.getSalle()).equals("Salle THX")) {
                                    prix[0] = 10 - 10*(reduction/100);
                                }
                                else if((seance.getSalle()).equals("Salle UltraAVX")) {
                                    prix[0] = 11 - 11*(reduction/100);
                                }
                                else if((seance.getSalle()).equals("Salle Dolby Cinema")) {
                                    prix[0] = 12 - 12*(reduction/100);
                                }
                                else if((seance.getSalle()).equals("Salle IMAX")) {
                                    prix[0] = 13 - 13*(reduction/100);
                                }
                                else if((seance.getSalle()).equals("Salle D-Box")) {
                                    prix[0] = 14 - 14*(reduction/100);
                                }
                                else if((seance.getSalle()).equals("Salle 4DX")) {
                                    prix[0] = 15 - 15*(reduction/100);
                                }
                                else if((seance.getSalle()).equals("Salle VIP")) {
                                    prix[0] = 15 - 15*(reduction/100);
                                }
                                else if((seance.getSalle()).equals("Salle Gold Class")) {
                                    prix[0] = 16 - 16*(reduction/100);
                                }
                                donnees[i][4] = prix[0];
                                try {
                                    donnees[i][5] = filmDAO.recupererDureeParTitre(titreFilm);
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }

                            String url_ba;
                            try {
                                url_ba = filmDAO.trouverURLParTitre(titres.get(selectedIndex));

                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }

// Appeler la méthode pour afficher la vidéo et la table sur la même fenêtre

                            JTable table = new JTable(donnees, entetes);
                            table.setDefaultEditor(Object.class, null); // Désactiver l'édition de la table


                            SwingUtilities.invokeLater(() -> {
                                // Initialisation de JavaFX Toolkit
                                JFXPanel fxPanel = new JFXPanel();

                                // Créer une fenêtre Swing
                                JFrame frame = new JFrame("Video Player & Table");
                                frame.setSize(800, 600);
                                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                                // Créer un panneau Swing pour contenir le JFXPanel
                                JPanel panel = new JPanel(new BorderLayout());
                                frame.add(panel);

                                Platform.runLater(() -> {
                                    // Créer un WebView pour afficher la vidéo YouTube
                                    WebView webView = new WebView();

                                    // Charger la vidéo YouTube dans le WebView
                                    WebEngine webEngine = webView.getEngine();
                                    webEngine.load(url_ba);

                                    // Créer un conteneur BorderPane pour organiser le WebView
                                    BorderPane borderPane = new BorderPane();
                                    borderPane.setCenter(webView);

                                    // Créer une scène JavaFX et l'ajouter au JFXPanel
                                    Scene scene = new Scene(borderPane);
                                    fxPanel.setScene(scene);
                                });

                                // Créer un JScrollPane pour la table
                                JScrollPane scrollPane = new JScrollPane(table);

                                // Ajouter le JScrollPane au panneau Swing
                                panel.add(scrollPane, BorderLayout.WEST);
                                panel.add(fxPanel, BorderLayout.CENTER);

                                // Création de la JTextArea pour la description avec une police plus grande
                                JTextArea descriptionArea = new JTextArea("Description du film :\n" + descriptionFilm);
                                descriptionArea.setFont(new Font("SansSerif", Font.PLAIN, 16)); // Définir la police
                                descriptionArea.setWrapStyleWord(true);
                                descriptionArea.setLineWrap(true);
                                descriptionArea.setEditable(false);
                                descriptionArea.setOpaque(false);

                                // Utiliser un JScrollPane pour la JTextArea pour gérer les longues descriptions
                                JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
                                descriptionScrollPane.setBorder(null); // Enlever les bordures pour plus de clarté
                                descriptionScrollPane.setOpaque(false);
                                descriptionScrollPane.getViewport().setOpaque(false);

                                // Ajouter la JTextArea au JPanel sous le JScrollPane du tableau
                                JPanel descriptionPanel = new JPanel(new BorderLayout());
                                descriptionPanel.add(descriptionScrollPane, BorderLayout.CENTER);

                                // Mettre à jour le panneau contenant le tableau et la description
                                panel.add(descriptionPanel, BorderLayout.SOUTH);

                                // Mettre à jour l'affichage
                                frame.validate();
                                frame.repaint();
                                // Afficher la fenêtre Swing
                                frame.setVisible(true);
                            });


                            final int[] selectedRow = {-1};

                            // Ajouter un écouteur pour capturer les clics sur les lignes de la table
                            double finalReduction = reduction;
                            table.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent f) {
                                    if (f.getClickCount() == 1) {
                                        selectedRow[0] = table.getSelectedRow();

                                        // Obtenir l'index de la ligne sélectionnée
                                        selectedRow[0] = table.getSelectedRow();
                                        if (selectedRow[0] != -1) {
                                            // Extraire l'ID de la séance sélectionnée
                                            int seanceId = (int) table.getValueAt(selectedRow[0], 0);


                                            Seance seance1 = null;
                                            try {
                                                seance1 = seanceDAO.trouverSeanceParId(seanceId);
                                            } catch (Exception ex) {
                                                throw new RuntimeException(ex);
                                            }

                                            prix[0] = 8;

                                            if ((seance1.getSalle()).equals("Salle Standard")) {
                                                prix[0] = 8 - 8*(finalReduction /100);
                                            } else if ((seance1.getSalle()).equals("Salle 3D")) {
                                                prix[0] = 9 - 9*(finalReduction /100);
                                            } else if ((seance1.getSalle()).equals("Salle THX")) {
                                                prix[0] = 10 - 10*(finalReduction /100);
                                            } else if ((seance1.getSalle()).equals("Salle UltraAVX")) {
                                                prix[0] = 11 - 11*(finalReduction /100);
                                            } else if ((seance1.getSalle()).equals("Salle Dolby Cinema")) {
                                                prix[0] = 12 - 12*(finalReduction /100);
                                            } else if ((seance1.getSalle()).equals("Salle IMAX")) {
                                                prix[0] = 13 - 13*(finalReduction /100);
                                            } else if ((seance1.getSalle()).equals("Salle D-Box")) {
                                                prix[0] = 14 - 14*(finalReduction /100);
                                            } else if ((seance1.getSalle()).equals("Salle 4DX")) {
                                                prix[0] = 15 - 15*(finalReduction /100);
                                            } else if ((seance1.getSalle()).equals("Salle VIP")) {
                                                prix[0] = 15 - 15*(finalReduction /100);
                                            } else if ((seance1.getSalle()).equals("Salle Gold Class")) {
                                                prix[0] = 16 - 16*(finalReduction /100);
                                            }

                                            Billet billet;

                                            try {
                                                if (!(clientDAO.trouverEmailParId(userID)).equals("admin@admin.fr")) {
                                                    // Création d'un objet Billet avec les données saisies
                                                    billet = new Billet(100, seanceId, userID, prix[0]);


                                                    FakePaymentPage fakePaymentPage = new FakePaymentPage(prix[0]);




                                                }
                                                else {
                                                    int clientID = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID du client du billet : "));
                                                    // Création d'un objet Billet avec les données saisies
                                                    billet = new Billet(100, seanceId, clientID, prix[0]);
                                                    billetDAO.ajouterBillet(billet);
                                                    JOptionPane.showMessageDialog(null, "Billet ajouté avec succès.");

                                                }
                                            } catch (Exception ex) {
                                                throw new RuntimeException(ex);
                                            }

                                        }
                                    }
                                }
                            });
                        }
                    }
                });

// Créer un JPanel pour le titre
                JPanel titlePanel = new JPanel();
                titlePanel.setBackground(Color.DARK_GRAY);
                titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS)); // Utiliser un BoxLayout avec un axe horizontal

// Ajouter le titre
                JLabel titleLabel = new JLabel(titres.get(index));
                titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Définir une police plus grande et en gras
                titleLabel.setForeground(Color.WHITE); // Définir la couleur du texte en blanc
                titlePanel.add(titleLabel);
                titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrer horizontalement
                posterAndTitlePanel.add(titlePanel);

// Ajouter le JPanel contenant l'affiche et le titre au centerPanel
                centerPanel.add(posterAndTitlePanel);

            }
            // Activer ou désactiver les boutons de navigation en fonction de la présence d'une affiche précédente ou suivante
            btnPrevious.setEnabled(index > 0);
            btnNext.setEnabled(index < affiches.size() - 1);
        };


        // Ajouter des écouteurs d'événements aux boutons de navigation
        btnPrevious.addActionListener(e -> {
            // Afficher l'affiche précédente
            currentIndex.getAndDecrement();
            showPoster.run();
        });

        btnNext.addActionListener(e -> {
            // Afficher l'affiche suivante
            currentIndex.getAndIncrement();
            showPoster.run();
        });

        showPoster.run(); // Afficher la première affiche lors de l'initialisation

        posterPanel.add(imagesPanel, BorderLayout.CENTER); // Ajouter le panneau des affiches dans le posterPanel

        return posterPanel;
    }

}

