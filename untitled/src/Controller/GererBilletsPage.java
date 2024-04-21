package Controller;

import Modele.InterfaceDAO.*;
import Modele.Objets.Billet;
import Modele.Objets.Film;
import Modele.Objets.Seance;
import Vue.BilletsVue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Scanner;
import Vue.BilletsVue;
public class GererBilletsPage extends JFrame implements ActionListener {
    private final int userID;
    private ClientDAO clientDAO;
    private BilletDAO billetDAO;
    private Scanner scanner;
    private FilmDAO filmDAO;
    private EmployeDAO employeDAO;
    private SeanceDAO seanceDAO;
    private OffresDAO offresDAO;
    private JButton btnAjouterBillet;
    private JButton btnListerBillets;
    private JButton btnSupprimerBillet;
    private JButton btnRetour;

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

    public GererBilletsPage(BilletDAO billetDAO, ClientDAO clientDAO, FilmDAO filmDAO, EmployeDAO employeDAO, SeanceDAO seanceDAO, OffresDAO offresDAO, Scanner scanner, int userID) {

        this.userID = userID;
        this.clientDAO = clientDAO;
        this.filmDAO = filmDAO;
        this.employeDAO = employeDAO;
        this.seanceDAO = seanceDAO;
        this.billetDAO = billetDAO;
        this.offresDAO = offresDAO;
        this.scanner = scanner;

        boolean admin;

        try {
            admin = (clientDAO.trouverEmailParId(userID)).equals("admin@admin.fr");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        new BilletsVue(this, admin);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();
        System.out.println(command);

        if ("Réserver une séance".equals(command)) {
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

                    Billet billet;

                    try {
                        if (!(clientDAO.trouverEmailParId(userID)).equals("admin@admin.fr")) {
                            // Création d'un objet Billet avec les données saisies
                            billet = new Billet(100, seanceId, userID, prix);


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
                        else {
                            int clientID = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID du client du billet : "));
                            // Création d'un objet Billet avec les données saisies
                            billet = new Billet(100, seanceId, clientID, prix);
                            billetDAO.ajouterBillet(billet);
                            JOptionPane.showMessageDialog(null, "Billet ajouté avec succès.");

                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        }

        else if ("Lister toutes mes séances".equals(command) || "Lister tous les billets".equals(command)) {
            List<Billet> billets = null;
            try {
                if (!(clientDAO.trouverEmailParId(userID)).equals("admin@admin.fr")) {
                    // Lister tous les billets de l'utilisateur courant (userID)
                    billets = billetDAO.listerBilletsParClientId(userID);
                }
                else {
                    // Lister tous les billets
                    billets = billetDAO.listerTousLesBillets();
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            if (billets.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Aucun billet disponible pour cet utilisateur.");
            } else {

                try {
                    if (!(clientDAO.trouverEmailParId(userID)).equals("admin@admin.fr")) {

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
                    else {  //VERSION ADMIN

                        // Création d'un modèle de tableau pour afficher les billets dans une JTable
                        String[] entetes = {"ID Séance", "ID Client","Film", "Heure", "Salle", "Prix"};
                        Object[][] donnees = new Object[billets.size()][6];

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
                            donnees[i][1] = billet.getClientId();
                            donnees[i][2] = film.getTitre();
                            donnees[i][3] = seance.getHeure();
                            donnees[i][4] = seance.getSalle();
                            donnees[i][5] = billet.getPrix();
                        }

                        JTable table = new JTable(donnees, entetes);
                        table.setDefaultEditor(Object.class, null); // Désactiver l'édition de la table
                        JScrollPane scrollPane = new JScrollPane(table);
                        JOptionPane.showMessageDialog(null, scrollPane, "Liste de tous les billets", JOptionPane.PLAIN_MESSAGE);

                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        else if ("Supprimer un billet".equals(command)){
            // Récupérer tous les billets de l'utilisateur
            List<Billet> billetsUtilisateur = null;
            try {
                if (!(clientDAO.trouverEmailParId(userID)).equals("admin@admin.fr")) {
                    // Lister tous les billets de l'utilisateur courant (userID)
                    billetsUtilisateur = billetDAO.listerBilletsParClientId(userID);
                }
                else {
                    // Lister tous les billets
                    billetsUtilisateur = billetDAO.listerTousLesBillets();
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            // Vérifier s'il existe des billets pour l'utilisateur
            if (billetsUtilisateur.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Aucun billet disponible pour cet utilisateur.");
            } else {
                // Créer un tableau de chaînes pour stocker les options de la liste déroulante
                String[] optionsBillets = new String[billetsUtilisateur.size()];
                try {
                    if (!(clientDAO.trouverEmailParId(userID)).equals("admin@admin.fr")) {
                        // Remplir le tableau avec les options de la liste déroulante
                        for (int i = 0; i < billetsUtilisateur.size(); i++) {
                            Billet billet = billetsUtilisateur.get(i);
                            optionsBillets[i] = "ID: " + billet.getId() + ", Séance ID: " + billet.getSeanceId() +
                                    ", Prix: " + billet.getPrix();
                        }
                    }
                    else {
                        // Remplir le tableau avec les options de la liste déroulante
                        for (int i = 0; i < billetsUtilisateur.size(); i++) {
                            Billet billet = billetsUtilisateur.get(i);
                            optionsBillets[i] = "Billet ID: " + billet.getId() + ", Séance ID: " + billet.getSeanceId() +
                                    ", Client ID: " + billet.getClientId() + ", Prix: " + billet.getPrix();
                        }
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
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
        else if ("Retour au menu principal".equals(command)) {
            dispose(); // Ferme la fenêtre actuelle
        }
    }
}