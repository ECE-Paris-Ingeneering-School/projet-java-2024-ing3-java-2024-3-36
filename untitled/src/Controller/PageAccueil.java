package Controller;

import Modele.ImplementationsDAO.FilmDAOImpl;
import Modele.InterfaceDAO.*;
import Modele.Objets.Billet;
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
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static Controller.Main.scanner;

public class PageAccueil extends JFrame implements ActionListener {

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(255, 215, 0)); // Couleur bleu foncé
        button.setForeground(Color.WHITE); // Couleur du texte blanc
        button.setFocusPainted(false); // Suppression de la bordure autour du texte lorsqu'on clique sur le bouton
        button.setPreferredSize(new Dimension(200, 100)); // Dimensions du bouton (largeur, hauteur)
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Police du texte
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return button;
    }


    private final int userID;
    private JButton btnGererBillets;
    private JButton btnGererClients;
    private JButton btnGererEmployes;
    private JButton btnGererFilms;
    private JButton btnGererSeances;
    private JButton btnOffres;
    private JButton btnQuitter;
    private ClientDAO clientDAO;
    private FilmDAO filmDAO;
    private EmployeDAO employeDAO;
    private SeanceDAO seanceDAO;
    private BilletDAO billetDAO;
    private OffresDAO offresDAO;

    public PageAccueil(int userID, ClientDAO clientDAO, FilmDAO filmDAO, EmployeDAO employeDAO, SeanceDAO seanceDAO, BilletDAO billetDAO, OffresDAO offresDAO, Scanner scanner) throws Exception {
        this.userID = userID;
        setTitle("Page d'Accueil");
        String mail = clientDAO.trouverEmailParId(userID);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création des boutons
        btnGererBillets = createStyledButton("Gérer mes billets");
        btnGererBillets.addActionListener(this);

        btnGererClients = createStyledButton("Gérer les clients");
        btnGererClients.addActionListener(this);

        btnGererEmployes = createStyledButton("Gérer les employés");
        btnGererEmployes.addActionListener(this);

        btnGererFilms = createStyledButton("Gérer les films");
        btnGererFilms.addActionListener(this);

        btnGererSeances = createStyledButton("Gérer les Séances");
        btnGererSeances.addActionListener(this);

        btnOffres = createStyledButton("Ajouter des offres");
        btnOffres.addActionListener(this);

        btnQuitter = createStyledButton("Quitter");
        btnQuitter.addActionListener(this);

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
        JPanel posterPanel = createPosterPanel();

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
            btnQuitter.setPreferredSize(new Dimension((screenSize.width / 6) - 10, 100));
            btnGererClients.setPreferredSize(new Dimension((screenSize.width / 6) - 10, 100));
            btnGererEmployes.setPreferredSize(new Dimension((screenSize.width / 6) - 10, 100));
            btnGererFilms.setPreferredSize(new Dimension((screenSize.width / 6) - 10, 100));
            btnGererSeances.setPreferredSize(new Dimension((screenSize.width / 6) - 10, 100));
            btnOffres.setPreferredSize(new Dimension((screenSize.width / 6) - 10, 100));

            JPanel adminPanel = new JPanel(new GridLayout(1, 5)); // Création d'un GridLayout pour aligner les boutons pour l'admin
            adminPanel.setBackground(Color.DARK_GRAY);
            adminPanel.setPreferredSize(new Dimension(screenSize.width, 100));

            adminPanel.add(btnGererClients);
            adminPanel.add(btnGererEmployes);
            adminPanel.add(btnGererFilms);
            adminPanel.add(btnGererSeances);
            adminPanel.add(btnOffres);
            adminPanel.add(btnQuitter);

            buttonPanel.add(adminPanel);
        }

        return buttonPanel;
    }

    private JPanel createPosterPanel() {
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

        FilmDAOImpl filmDAO = new FilmDAOImpl(); // Instancier votre DAO

        // Récupérer les données d'image à partir de la méthode récupérerAfficheBytes() de votre DAO
        java.util.List<byte[]> affichesBytes = filmDAO.recupererAfficheBytes();
        java.util.List<ImageIcon> affiches = new ArrayList<>(); // Liste pour stocker les affiches en tant qu'ImageIcon
        java.util.List<String> titres = new ArrayList<>(); // Liste pour stocker les titres des films

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
                            String[] entetes = {"ID", "Film", "Date et heure", "Salle", "Prix"};
                            Object[][] donnees = new Object[seances.size()][5];
                            final double[] prix = {8};
                            double reduction = 1;

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

                                                    fakePaymentPage.addWindowListener(new WindowAdapter() {
                                                        @Override
                                                        public void windowClosed(WindowEvent e) {
                                                            if (fakePaymentPage.isPaymentSuccessful()) {
                                                                try {
                                                                    billetDAO.ajouterBillet(billet);
                                                                    JOptionPane.showMessageDialog(null, "Billet ajouté avec succès.");
                                                                } catch (Exception ex) {
                                                                    throw new RuntimeException(ex);
                                                                }
                                                            }
                                                        }
                                                    });

                                                    fakePaymentPage.setVisible(true);
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


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGererBillets) {
            new Controller.GererBilletsPage(billetDAO, scanner, userID);
        } else if (e.getSource() == btnGererClients) {
            new GererClientsPage(clientDAO, scanner, userID);
        } else if (e.getSource() == btnGererEmployes) {
            new Controller.GererEmployesPage(employeDAO, scanner, userID);
        } else if (e.getSource() == btnGererFilms) {
            new Controller.GererFilmsPage(filmDAO, scanner, userID);
        } else if (e.getSource() == btnGererSeances) {
            new Controller.GererSeancesPage(seanceDAO, scanner, userID);
        } else if (e.getSource() == btnOffres) {
            new Controller.GererOffresPage(offresDAO, scanner, userID);
        } else if (e.getSource() == btnQuitter) {
            System.out.println("Au revoir !");
            System.exit(0);
        }
    }

}

