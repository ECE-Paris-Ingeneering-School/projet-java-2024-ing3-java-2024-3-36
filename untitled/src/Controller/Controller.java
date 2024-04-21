package Controller;
//test
import Modele.ImplementationsDAO.*;
import Modele.InterfaceDAO.*;
import Modele.Objets.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

import java.io.*;
import java.nio.file.Files;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller {

    private static BilletDAO billetDAO = new BilletDAOImpl();
    private static ClientDAO clientDAO = new ClientDAOImpl();
    private static EmployeDAO employeDAO = new EmployeDAOImpl();
    private static FilmDAO filmDAO = new FilmDAOImpl();
    private static SeanceDAO seanceDAO = new SeanceDAOImpl();
    private static OffresDAO offresDAO = new OffresDAOImpl();
    private static Scanner scanner = new Scanner(System.in);

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


    public static class GererOffresPage extends JFrame implements ActionListener {
        private OffresDAO offresDAO;
        private Scanner scanner;
        private int userID;

        private JButton btnOffreRegulier;
        private JButton btnOffreSenior;
        private JButton btnOffreEnfant;
        private JButton btnRetour;


        public GererOffresPage(OffresDAO offresDAO, Scanner scanner, int userID) {
            this.offresDAO = offresDAO;
            this.scanner = scanner;
            this.userID = userID;

            setTitle("Gérer les Offres");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(4, 1));

            btnOffreRegulier = createStyledButton("Modifier Offre Régulier");
            btnOffreRegulier.addActionListener(this);
            add(btnOffreRegulier);

            btnOffreSenior = createStyledButton("Modifier Offre Senior");
            btnOffreSenior.addActionListener(this);
            add(btnOffreSenior);

            btnOffreEnfant = createStyledButton("Modifier Offre Enfant");
            btnOffreEnfant.addActionListener(this);
            add(btnOffreEnfant);

            btnRetour = createStyledButton("Retour au menu principal");
            btnRetour.addActionListener(this);
            add(btnRetour);

            setSize(400, 400);
            setVisible(true);
        }

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

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnOffreRegulier) {
                modifierOffre("régulier");
            } else if (e.getSource() == btnOffreSenior) {
                modifierOffre("sénior");
            } else if (e.getSource() == btnOffreEnfant) {
                modifierOffre("enfant");
            } else if (e.getSource() == btnRetour) {
                dispose(); //Ferme la fenêtre actuelle
            }
        }

        private void modifierOffre(String categorie) {
            // Demander à l'utilisateur de saisir la nouvelle valeur de l'offre
            String nouvelleValeurStr = JOptionPane.showInputDialog(null, "Entrez la nouvelle valeur pour l'offre " + categorie + " :");
            if (nouvelleValeurStr != null && !nouvelleValeurStr.isEmpty()) {
                try {
                    double nouvelleValeur = Double.parseDouble(nouvelleValeurStr);

                    // Vérifier si la nouvelle valeur est comprise entre 0 et 100
                    if (nouvelleValeur < 0 || nouvelleValeur > 100) {
                        throw new IllegalArgumentException("La valeur doit être comprise entre 0 et 100 !");
                    }

                    // Mettre à jour l'offre dans la base de données
                    if (categorie.equals("régulier")) {
                        offresDAO.modifierOffreRegulier(nouvelleValeur);
                    } else if (categorie.equals("sénior")) {
                        offresDAO.modifierOffreSenior(nouvelleValeur);
                    } else if (categorie.equals("enfant")) {
                        offresDAO.modifierOffreEnfant(nouvelleValeur);
                    }
                    JOptionPane.showMessageDialog(null, "Offre " + categorie + " mise à jour avec succès !");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Veuillez saisir une valeur numérique valide !");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur s'est produite lors de la mise à jour de l'offre " + categorie + " : " + ex.getMessage());
                }
            }
        }
    }

    public static class GererBilletsPage extends JFrame implements ActionListener {
        private final int userID;
        private BilletDAO billetDAO;
        private Scanner scanner;

        private JButton btnAjouterBillet;

        private JButton btnListerBillets;

        private JButton btnSupprimerBillet;
        private JButton btnRetour;

        public GererBilletsPage(BilletDAO billetDAO, Scanner scanner, int userID) {
            this.userID = userID;
            this.billetDAO = billetDAO;
            this.scanner = scanner;

            setTitle("Gérer Mes billets");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(6, 1));

            // Initialisation des boutons
            btnAjouterBillet = createStyledButton("Réserver une séance");
            btnAjouterBillet.addActionListener(this);
            add(btnAjouterBillet);



            btnListerBillets = createStyledButton("Lister toutes mes séances");
            btnListerBillets.addActionListener(this);
            add(btnListerBillets);



            btnSupprimerBillet = createStyledButton("Supprimer un billet");
            btnSupprimerBillet.addActionListener(this);
            add(btnSupprimerBillet);

            btnRetour = createStyledButton("Retour au menu principal");
            btnRetour.addActionListener(this);
            add(btnRetour);

            // Taille de la fenêtre
            setSize(400, 400);
            setVisible(true);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnAjouterBillet) {
                System.out.println("");

                List<Seance> seances = null;
                try {
                    seances = seanceDAO.listerToutesLesSeances();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                if (seances.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Aucune séance disponible pour ce film.");
                } else {
                    // Création d'un modèle de tableau pour afficher les séances dans une JTable
                    String[] entetes = {"ID", "Film", "Date et heure", "Salle", "Prix"};
                    Object[][] donnees = new Object[seances.size()][5];
                    double prix = 8;
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
                        // Récupérer le titre du film associé à cette séance
                        String titreFilm = null;
                        try {
                            titreFilm = filmDAO.trouverTitreParId(seance.getFilmId());
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        donnees[i][0] = seance.getId();
                        donnees[i][1] = titreFilm;
                        donnees[i][2] = seance.getHeure();
                        donnees[i][3] = seance.getSalle();

                        if((seance.getSalle()).equals("Salle Standard")) {
                            prix = 8 - 8*(reduction/100);
                        }
                        else if((seance.getSalle()).equals("Salle 3D")) {
                            prix = 9 - 9*(reduction/100);
                        }
                        else if((seance.getSalle()).equals("Salle THX")) {
                            prix = 10 - 10*(reduction/100);
                        }
                        else if((seance.getSalle()).equals("Salle UltraAVX")) {
                            prix = 11 - 11*(reduction/100);
                        }
                        else if((seance.getSalle()).equals("Salle Dolby Cinema")) {
                            prix = 12 - 12*(reduction/100);
                        }
                        else if((seance.getSalle()).equals("Salle IMAX")) {
                            prix = 13 - 13*(reduction/100);
                        }
                        else if((seance.getSalle()).equals("Salle D-Box")) {
                            prix = 14 - 14*(reduction/100);
                        }
                        else if((seance.getSalle()).equals("Salle 4DX")) {
                            prix = 15 - 15*(reduction/100);
                        }
                        else if((seance.getSalle()).equals("Salle VIP")) {
                            prix = 15 - 15*(reduction/100);
                        }
                        else if((seance.getSalle()).equals("Salle Gold Class")) {
                            prix = 16 - 16*(reduction/100);
                        }
                        donnees[i][4] = prix;
                    }

                    JTable table = new JTable(donnees, entetes);
                    table.setDefaultEditor(Object.class, null); // Désactiver l'édition de la table
                    JScrollPane scrollPane = new JScrollPane(table);
                    JOptionPane.showMessageDialog(null, scrollPane, "Liste des séances", JOptionPane.PLAIN_MESSAGE);

                    // Obtenir l'index de la ligne sélectionnée
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Extraire l'ID de la séance sélectionnée
                        int seanceId = (int) table.getValueAt(selectedRow, 0);

                        Seance seance1 = null;
                        try {
                            seance1 = seanceDAO.trouverSeanceParId(seanceId);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }

                        prix = 8;

                        if((seance1.getSalle()).equals("Salle Standard")) {
                            prix = 8 - 8*(reduction/100);
                        }
                        else if((seance1.getSalle()).equals("Salle 3D")) {
                            prix = 9 - 9*(reduction/100);
                        }
                        else if((seance1.getSalle()).equals("Salle THX")) {
                            prix = 10 - 10*(reduction/100);
                        }
                        else if((seance1.getSalle()).equals("Salle UltraAVX")) {
                            prix = 11 - 11*(reduction/100);
                        }
                        else if((seance1.getSalle()).equals("Salle Dolby Cinema")) {
                            prix = 12 - 12*(reduction/100);
                        }
                        else if((seance1.getSalle()).equals("Salle IMAX")) {
                            prix = 13 - 13*(reduction/100);
                        }
                        else if((seance1.getSalle()).equals("Salle D-Box")) {
                            prix = 14 - 14*(reduction/100);
                        }
                        else if((seance1.getSalle()).equals("Salle 4DX")) {
                            prix = 15 - 15*(reduction/100);
                        }
                        else if((seance1.getSalle()).equals("Salle VIP")) {
                            prix = 15 - 15*(reduction/100);
                        }
                        else if((seance1.getSalle()).equals("Salle Gold Class")) {
                            prix = 16 - 16*(reduction/100);
                        }

                        Billet billet = new Billet(100, seanceId, userID, prix);

                        // Appel de la méthode pour ajouter le billet dans la base de données
                        FakePaymentPage fakePaymentPage = new FakePaymentPage(prix);

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
                }
            }

            else if (e.getSource() == btnListerBillets) {
                // Lister tous les billets de l'utilisateur courant (userID)
                List<Billet> billets = null;
                try {
                    billets = billetDAO.listerBilletsParClientId(userID);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                if (billets.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Aucun billet disponible pour cet utilisateur.");
                } else {
                    // Création d'un modèle de tableau pour afficher les billets dans une JTable
                    String[] entetes = {"ID Séance", "Film", "Heure", "Salle", "Prix"};
                    Object[][] donnees = new Object[billets.size()][5];

                    for (int i = 0; i < billets.size(); i++) {
                        Billet billet = billets.get(i);
                        Seance seance = null;
                        try {
                            seance = seanceDAO.trouverSeanceParId(billet.getSeanceId());
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }

                        Film film = null;
                        try {
                            film = filmDAO.recupFilm(seance.getFilmId());
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }

                        donnees[i][0] = billet.getSeanceId();
                        donnees[i][1] = film.getTitre();
                        donnees[i][2] = seance.getHeure();
                        donnees[i][3] = seance.getSalle();
                        donnees[i][4] = billet.getPrix();
                    }

                    JTable table = new JTable(donnees, entetes);
                    table.setDefaultEditor(Object.class, null); // Désactiver l'édition de la table
                    JScrollPane scrollPane = new JScrollPane(table);
                    JOptionPane.showMessageDialog(null, scrollPane, "Liste des billets de l'utilisateur", JOptionPane.PLAIN_MESSAGE);
                }
            }

            else if (e.getSource() == btnSupprimerBillet) {
                // Récupérer tous les billets de l'utilisateur
                List<Billet> billetsUtilisateur = null;
                try {
                    System.out.println(userID);
                    billetsUtilisateur = billetDAO.listerBilletsParClientId(userID);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                // Vérifier s'il existe des billets pour l'utilisateur
                if (billetsUtilisateur.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Aucun billet disponible pour cet utilisateur.");
                } else {
                    // Créer un tableau de chaînes pour stocker les options de la liste déroulante
                    String[] optionsBillets = new String[billetsUtilisateur.size()];

                    // Remplir le tableau avec les options de la liste déroulante
                    for (int i = 0; i < billetsUtilisateur.size(); i++) {
                        Billet billet = billetsUtilisateur.get(i);
                        optionsBillets[i] = "ID: " + billet.getId() + ", Séance ID: " + billet.getSeanceId() +
                                ", Prix: " + billet.getPrix();
                    }

                    // Afficher la boîte de dialogue pour sélectionner un billet à supprimer
                    String selectedOption = (String) JOptionPane.showInputDialog(null, "Choisissez le billet à supprimer :",
                            "Sélectionner un Billet", JOptionPane.PLAIN_MESSAGE, null, optionsBillets, optionsBillets[0]);

                    if (selectedOption != null) {
                        // Extraire l'ID du billet sélectionné
                        // Trouver l'index de départ de l'ID
                        int idIndex = selectedOption.indexOf("ID: ") + 4;

// Extraire l'ID du billet sélectionné à partir de la chaîne
                        int idSuppression = Integer.parseInt(selectedOption.substring(idIndex, selectedOption.indexOf(",", idIndex)));


                        // Supprimer le billet sélectionné
                        try {
                            billetDAO.supprimerBillet(idSuppression);
                            JOptionPane.showMessageDialog(null, "Billet supprimé avec succès.");
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
            else if (e.getSource() == btnRetour) {
                dispose(); // Ferme la fenêtre actuelle
            }
        }

    }


    public static class GererEmployesPage extends JFrame implements ActionListener {
        private EmployeDAO employeDAO;
        private Scanner scanner;

        private JButton btnAjouterEmploye;
        private JButton btnTrouverEmploye;
        private JButton btnListerEmployes;
        private JButton btnMettreAJourEmploye;
        private JButton btnSupprimerEmploye;
        private JButton btnRetour;

        public GererEmployesPage(EmployeDAO employeDAO, Scanner scanner, int userID) {
            this.employeDAO = employeDAO;
            this.scanner = scanner;

            setTitle("Gérer les employés");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(6, 1));

            btnAjouterEmploye = createStyledButton("Ajouter un employé");
            btnAjouterEmploye.addActionListener(this);
            add(btnAjouterEmploye);

            btnTrouverEmploye = createStyledButton("Trouver un employé par ID");
            btnTrouverEmploye.addActionListener(this);
            add(btnTrouverEmploye);

            btnListerEmployes = createStyledButton("Lister tous les employés");
            btnListerEmployes.addActionListener(this);
            add(btnListerEmployes);

            btnMettreAJourEmploye = createStyledButton("Mettre à jour un employé");
            btnMettreAJourEmploye.addActionListener(this);
            add(btnMettreAJourEmploye);

            btnSupprimerEmploye = createStyledButton("Supprimer un employé");
            btnSupprimerEmploye.addActionListener(this);
            add(btnSupprimerEmploye);

            btnRetour = createStyledButton("Retour au menu principal");
            btnRetour.addActionListener(this);
            add(btnRetour);

            setSize(400, 400);
            setVisible(true);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnAjouterEmploye) {
                // Ajouter un employé
                String nom = JOptionPane.showInputDialog("Entrez le nom de l'employé : ");
                String position = JOptionPane.showInputDialog("Entrez la position de l'employé : ");
                String email = JOptionPane.showInputDialog("Entrez l'email de l'employé : ");
                String motDePasse = JOptionPane.showInputDialog("Entrez le mot de passe de l'employé : ");
                Employe employe = new Employe(0, nom, position, email, motDePasse);
                try {
                    employeDAO.ajouterEmploye(employe);
                    JOptionPane.showMessageDialog(null, "Employé ajouté avec succès.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage());
                }
            } else if (e.getSource() == btnTrouverEmploye) {
                // Trouver un employé par ID
                int id = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID de l'employé : "));
                Employe employe = null;
                try {
                    employe = employeDAO.trouverEmployeParId(id);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(null, employe != null ? employe.toString() : "Employé non trouvé.");
            } else if (e.getSource() == btnListerEmployes) {
                // Lister tous les employés
                List<Employe> employes = null;
                try {
                    employes = employeDAO.listerTousLesEmployes();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                if (employes.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Aucun employé disponible.");
                } else {
                    // Création d'un modèle de tableau pour afficher les employés dans une JTable
                    String[] entetes = {"ID", "Nom", "Position", "Email", "Mot de passe"};
                    Object[][] donnees = new Object[employes.size()][5];

                    for (int i = 0; i < employes.size(); i++) {
                        Employe employe = employes.get(i);
                        donnees[i][0] = employe.getId();
                        donnees[i][1] = employe.getNom();
                        donnees[i][2] = employe.getPosition();
                        donnees[i][3] = employe.getEmail();
                        donnees[i][4] = employe.getMotDePasse(); // Ajout du mot de passe
                    }

                    JTable table = new JTable(donnees, entetes);
                    table.setDefaultEditor(Object.class, null); // Désactiver l'édition de la table
                    JScrollPane scrollPane = new JScrollPane(table);
                    JOptionPane.showMessageDialog(null, scrollPane, "Liste des employés", JOptionPane.PLAIN_MESSAGE);
                }
            }
            else if (e.getSource() == btnMettreAJourEmploye) {
                // Mettre à jour un employé
                int id = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID de l'employé à mettre à jour : "));
                Employe employe = null;
                try {
                    employe = employeDAO.trouverEmployeParId(id);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                if (employe == null) {
                    JOptionPane.showMessageDialog(null, "Employé non trouvé.");
                } else {
                    String nouveauNom = JOptionPane.showInputDialog("Entrez le nouveau nom de l'employé : ");
                    String nouvellePosition = JOptionPane.showInputDialog("Entrez la nouvelle position de l'employé : ");
                    String nouvelEmail = JOptionPane.showInputDialog("Entrez le nouvel email de l'employé : ");
                    String nouveauMotDePasse = JOptionPane.showInputDialog("Entrez le nouveau mot de passe de l'employé : ");
                    employe.setNom(nouveauNom);
                    employe.setPosition(nouvellePosition);
                    employe.setEmail(nouvelEmail);
                    employe.setMotDePasse(nouveauMotDePasse);
                    try {
                        employeDAO.mettreAJourEmploye(employe);
                        JOptionPane.showMessageDialog(null, "Employé mis à jour avec succès.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage());
                    }
                }
            } else if (e.getSource() == btnSupprimerEmploye) {
                // Supprimer un employé
                int idSuppression = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID de l'employé à supprimer : "));
                try {
                    employeDAO.supprimerEmploye(idSuppression);
                    JOptionPane.showMessageDialog(null, "Employé supprimé avec succès.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage());
                }
            } else if (e.getSource() == btnRetour) {
                dispose();
            }
        }


    }
    public static class GererFilmsPage extends JFrame implements ActionListener {
        private FilmDAO filmDAO;
        private Scanner scanner;

        private JButton btnAjouterFilm;
        private JButton btnTrouverFilm;
        private JButton btnListerFilms;
        private JButton btnMettreAJourFilm;
        private JButton btnSupprimerFilm;
        private JButton btnRetour;
        public GererFilmsPage(FilmDAO filmDAO, Scanner scanner, int userID) {
            this.filmDAO = filmDAO;
            this.scanner = scanner;

            setTitle("Gérer les films");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(6, 1));

            btnAjouterFilm = createStyledButton("Ajouter un film");
            btnAjouterFilm.addActionListener(this);
            add(btnAjouterFilm);

            btnTrouverFilm = createStyledButton("Trouver un film par ID");
            btnTrouverFilm.addActionListener(this);
            add(btnTrouverFilm);

            btnListerFilms = createStyledButton("Lister tous les films");
            btnListerFilms.addActionListener(this);
            add(btnListerFilms);

            btnMettreAJourFilm = createStyledButton("Mettre à jour un film");
            btnMettreAJourFilm.addActionListener(this);
            add(btnMettreAJourFilm);

            btnSupprimerFilm = createStyledButton("Supprimer un film");
            btnSupprimerFilm.addActionListener(this);
            add(btnSupprimerFilm);

            btnRetour = createStyledButton("Retour au menu principal");
            btnRetour.addActionListener(this);
            add(btnRetour);

            setSize(400, 400);
            setVisible(true);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnAjouterFilm) {
                // Ajouter un film
                String titre = JOptionPane.showInputDialog("Entrez le titre du film : ");
                String genre = JOptionPane.showInputDialog("Entrez le genre du film : ");
                int duree = Integer.parseInt(JOptionPane.showInputDialog("Entrez la durée du film : "));
                String description = JOptionPane.showInputDialog("Entrez la description du film : ");
                String realisateur = JOptionPane.showInputDialog("Entrez le réalisateur du film : ");
                byte[] affiche = selectAffiche(); // Appel de la méthode pour sélectionner une affiche
                Film film = new Film(0, titre, genre, duree, description, realisateur, affiche);
                try {
                    filmDAO.ajouterFilm(film);
                    JOptionPane.showMessageDialog(null, "Film ajouté avec succès.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage());
                }
            } else if (e.getSource() == btnTrouverFilm) {
                // Trouver un film par ID
                int id = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID du film : "));
                Film film = filmDAO.recupFilm(id);
                JOptionPane.showMessageDialog(null, film != null ? film.toString() : "Film non trouvé.");
            } else if (e.getSource() == btnListerFilms) {
                // Lister tous les films
                List<Film> films = null;
                try {
                    films = filmDAO.recupAllFilms();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                if (films.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Aucun film disponible.");
                } else {
                    // Création d'un modèle de tableau pour afficher les films dans une JTable
                    String[] entetes = {"ID", "Titre", "Genre", "Durée (min)", "Description", "Réalisateur"};
                    Object[][] donnees = new Object[films.size()][6];

                    for (int i = 0; i < films.size(); i++) {
                        Film film = films.get(i);
                        donnees[i][0] = film.getId();
                        donnees[i][1] = film.getTitre();
                        donnees[i][2] = film.getGenre();
                        donnees[i][3] = film.getDuree();
                        donnees[i][4] = film.getDescription();
                        donnees[i][5] = film.getRealisateur();
                    }

                    JTable table = new JTable(donnees, entetes);
                    table.setDefaultEditor(Object.class, null); // Désactiver l'édition de la table
                    JScrollPane scrollPane = new JScrollPane(table);
                    JOptionPane.showMessageDialog(null, scrollPane, "Liste des films", JOptionPane.PLAIN_MESSAGE);
                }
            }

            else if (e.getSource() == btnMettreAJourFilm) {
                // Mettre à jour un film
                int id = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID du film à mettre à jour : "));
                Film film = filmDAO.recupFilm(id);
                if (film == null) {
                    JOptionPane.showMessageDialog(null, "Film non trouvé.");
                } else {
                    String nouveauTitre = JOptionPane.showInputDialog("Entrez le nouveau titre du film : ");
                    String nouveauGenre = JOptionPane.showInputDialog("Entrez le nouveau genre du film : ");
                    int nouvelleDuree = Integer.parseInt(JOptionPane.showInputDialog("Entrez la nouvelle durée du film : "));
                    String nouvelleDescription = JOptionPane.showInputDialog("Entrez la nouvelle description du film : ");
                    String nouveauRealisateur = JOptionPane.showInputDialog("Entrez le nouveau réalisateur du film : ");

                    film.setTitre(nouveauTitre);
                    film.setGenre(nouveauGenre);
                    film.setDuree(nouvelleDuree);
                    film.setDescription(nouvelleDescription);
                    film.setRealisateur(nouveauRealisateur);

                    try {
                        filmDAO.updateFilm(film);
                        JOptionPane.showMessageDialog(null, "Film mis à jour avec succès.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage());
                    }
                }
            } else if (e.getSource() == btnSupprimerFilm) {
                // Supprimer un film
                int idSuppression = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID du film à supprimer : "));
                try {
                    filmDAO.supprimerFilm(idSuppression);
                    JOptionPane.showMessageDialog(null, "Film supprimé avec succès.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage());
                }
            } else if (e.getSource() == btnRetour) {
                dispose();
            }
        }
        private byte[] selectAffiche() {
            // Méthode pour sélectionner une affiche
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choisir une affiche");
            int selection = fileChooser.showOpenDialog(this);
            if (selection == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fileChooser.getSelectedFile();
                    return Files.readAllBytes(file.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

    }
    public static class GererSeancesPage extends JFrame implements ActionListener {
        private SeanceDAO seanceDAO;
        private Scanner scanner;

        private JButton btnAjouterSeance;
        private JButton btnTrouverSeance;
        private JButton btnListerSeances;
        private JButton btnMettreAJourSeance;
        private JButton btnSupprimerSeance;
        private JButton btnRetour;

        public GererSeancesPage(SeanceDAO seanceDAO, Scanner scanner, int userID) {
            this.seanceDAO = seanceDAO;
            this.scanner = scanner;

            setTitle("Gérer les séances");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(6, 1));

            btnAjouterSeance = createStyledButton("Ajouter une séance");
            btnAjouterSeance.addActionListener(this);
            add(btnAjouterSeance);

            btnTrouverSeance = createStyledButton("Trouver une séance par ID");
            btnTrouverSeance.addActionListener(this);
            add(btnTrouverSeance);

            btnListerSeances = createStyledButton("Lister toutes les séances");
            btnListerSeances.addActionListener(this);
            add(btnListerSeances);

            btnMettreAJourSeance = createStyledButton("Mettre à jour une séance");
            btnMettreAJourSeance.addActionListener(this);
            add(btnMettreAJourSeance);

            btnSupprimerSeance = createStyledButton("Supprimer une séance");
            btnSupprimerSeance.addActionListener(this);
            add(btnSupprimerSeance);

            btnRetour = createStyledButton("Retour au menu principal");
            btnRetour.addActionListener(this);
            add(btnRetour);

            setSize(400, 400);
            setVisible(true);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnAjouterSeance) {
                // Ajouter une séance
                int filmId = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID du film : "));
                String dateTimeString = JOptionPane.showInputDialog("Entrez la date et l'heure de la séance (AAAA-MM-JJ HH:MM:SS) : ");
                LocalDateTime heure = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String salle = JOptionPane.showInputDialog("Entrez le numéro de la salle : ");
                Seance seance = new Seance(0, filmId, heure, salle);
                try {
                    seanceDAO.ajouterSeance(seance);
                    JOptionPane.showMessageDialog(null, "Séance ajoutée avec succès.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage());
                }
            } else if (e.getSource() == btnTrouverSeance) {
                // Trouver une séance par ID
                int id = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID de la séance : "));
                Seance seance = null;
                try {
                    seance = seanceDAO.trouverSeanceParId(id);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(null, seance != null ? seance.toString() : "Séance non trouvée.");
            } else if (e.getSource() == btnListerSeances) {
                // Lister toutes les séances
                List<Seance> seances = null;
                try {
                    seances = seanceDAO.listerToutesLesSeances();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                if (seances.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Aucune séance disponible.");
                } else {
                    // Création d'un modèle de tableau pour afficher les séances dans une JTable
                    String[] entetes = {"ID", "ID du film", "Date et heure", "Salle", "Prix"};
                    Object[][] donnees = new Object[seances.size()][5];

                    for (int i = 0; i < seances.size(); i++) {
                        Seance seance = seances.get(i);
                        donnees[i][0] = seance.getId();
                        donnees[i][1] = seance.getFilmId();
                        donnees[i][2] = seance.getHeure();
                        donnees[i][3] = seance.getSalle();
                        if((seance.getSalle()).equals("Salle Standard")) {
                            donnees[i][4] = 8;
                        }
                        else if((seance.getSalle()).equals("Salle 3D")) {
                            donnees[i][4] = 9;
                        }
                        else if((seance.getSalle()).equals("Salle THX")) {
                            donnees[i][4] = 10;
                        }
                        else if((seance.getSalle()).equals("Salle UltraAVX")) {
                            donnees[i][4] = 11;
                        }
                        else if((seance.getSalle()).equals("Salle Dolby Cinema")) {
                            donnees[i][4] = 12;
                        }
                        else if((seance.getSalle()).equals("Salle IMAX")) {
                            donnees[i][4] = 13;
                        }
                        else if((seance.getSalle()).equals("Salle D-Box")) {
                            donnees[i][4] = 14;
                        }
                        else if((seance.getSalle()).equals("Salle 4DX")) {
                            donnees[i][4] = 15;
                        }
                        else if((seance.getSalle()).equals("Salle VIP")) {
                            donnees[i][4] = 15;
                        }
                        else if((seance.getSalle()).equals("Salle Gold Class")) {
                            donnees[i][4] = 16;
                        }
                    }

                    JTable table = new JTable(donnees, entetes);
                    table.setDefaultEditor(Object.class, null); // Désactiver l'édition de la table
                    JScrollPane scrollPane = new JScrollPane(table);
                    JOptionPane.showMessageDialog(null, scrollPane, "Liste des séances", JOptionPane.PLAIN_MESSAGE);
                }
            }

            else if (e.getSource() == btnMettreAJourSeance) {
                // Mettre à jour une séance
                int id = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID de la séance à mettre à jour : "));
                Seance seance = null;
                try {
                    seance = seanceDAO.trouverSeanceParId(id);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                if (seance == null) {
                    JOptionPane.showMessageDialog(null, "Séance non trouvée.");
                } else {
                    int filmId = Integer.parseInt(JOptionPane.showInputDialog("Entrez le nouvel ID du film : "));
                    String dateTimeString = JOptionPane.showInputDialog("Entrez la nouvelle date et heure de la séance (AAAA-MM-JJ HH:MM:SS) : ");
                    LocalDateTime heure = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    String salle = JOptionPane.showInputDialog("Entrez le nouveau numéro de salle : ");

                    seance.setFilmId(filmId);
                    seance.setHeure(heure);
                    seance.setSalle(salle);

                    try {
                        seanceDAO.mettreAJourSeance(seance);
                        JOptionPane.showMessageDialog(null, "Séance mise à jour avec succès.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage());
                    }
                }
            } else if (e.getSource() == btnSupprimerSeance) {
                // Supprimer une séance
                int idSuppression = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID de la séance à supprimer : "));
                try {
                    seanceDAO.supprimerSeance(idSuppression);
                    JOptionPane.showMessageDialog(null, "Séance supprimée avec succès.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage());
                }
            } else if (e.getSource() == btnRetour) {
                dispose();
            }
        }
    }
}
