package Controller;

import Modele.InterfaceDAO.*;
import Modele.Objets.Seance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public  class GererSeancesPage extends JFrame implements ActionListener {

    private ClientDAO clientDAO;
    private BilletDAO billetDAO;
    private FilmDAO filmDAO;
    private SeanceDAO seanceDAO;
    private EmployeDAO employeDAO;

    private JButton btnAjouterSeance;
    private JButton btnTrouverSeance;
    private JButton btnListerSeances;
    private JButton btnMettreAJourSeance;
    private JButton btnSupprimerSeance;
    private JButton btnRetour;

    private OffresDAO offresDAO;
    private Scanner scanner;
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
    public GererSeancesPage(SeanceDAO seanceDAO, ClientDAO clientDAO, FilmDAO filmDAO, EmployeDAO employeDAO, BilletDAO billetDAO, OffresDAO offresDAO, Scanner scanner, int userID) {

        this.clientDAO = clientDAO;
        this.filmDAO = filmDAO;
        this.employeDAO = employeDAO;
        this.seanceDAO = seanceDAO;
        this.billetDAO = billetDAO;
        this.offresDAO = offresDAO;
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
