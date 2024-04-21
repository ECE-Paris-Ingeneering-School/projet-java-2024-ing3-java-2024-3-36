package Controller;

import Modele.InterfaceDAO.*;
import Modele.Objets.Seance;
import Vue.SeancesVue;

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

    public GererSeancesPage(SeanceDAO seanceDAO, ClientDAO clientDAO, FilmDAO filmDAO, EmployeDAO employeDAO, BilletDAO billetDAO, OffresDAO offresDAO, Scanner scanner, int userID) {


        this.clientDAO = clientDAO;
        this.filmDAO = filmDAO;
        this.employeDAO = employeDAO;
        this.seanceDAO = seanceDAO;
        this.billetDAO = billetDAO;
        this.offresDAO = offresDAO;
        this.scanner = scanner;

        new SeancesVue(this);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        System.out.println("Action Command: " + command);
        switch (command) {
            case "Ajouter une séance":
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
                break;
            case "Trouver une séance par ID":
                int id = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID de la séance : "));
                try {
                    seance = seanceDAO.trouverSeanceParId(id);
                    JOptionPane.showMessageDialog(null, seance != null ? seance.toString() : "Séance non trouvée.");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "Lister toutes les séances":
                List<Seance> seances = null;
                try {
                    seances = seanceDAO.listerToutesLesSeances();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                if (seances.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Aucune séance disponible.");
                } else {
                    String[] entetes = {"ID", "ID du film", "Date et heure", "Salle", "Prix"};
                    Object[][] donnees = new Object[seances.size()][5];
                    for (int i = 0; i < seances.size(); i++) {
                        Seance s = seances.get(i);
                        donnees[i][0] = s.getId();
                        donnees[i][1] = s.getFilmId();
                        donnees[i][2] = s.getHeure();
                        donnees[i][3] = s.getSalle();
                        if((s.getSalle()).equals("Salle Standard")) {
                            donnees[i][4] = 8;
                        }
                        else if((s.getSalle()).equals("Salle 3D")) {
                            donnees[i][4] = 9;
                        }
                        else if((s.getSalle()).equals("Salle THX")) {
                            donnees[i][4] = 10;
                        }
                        else if((s.getSalle()).equals("Salle UltraAVX")) {
                            donnees[i][4] = 11;
                        }
                        else if((s.getSalle()).equals("Salle Dolby Cinema")) {
                            donnees[i][4] = 12;
                        }
                        else if((s.getSalle()).equals("Salle IMAX")) {
                            donnees[i][4] = 13;
                        }
                        else if((s.getSalle()).equals("Salle D-Box")) {
                            donnees[i][4] = 14;
                        }
                        else if((s.getSalle()).equals("Salle 4DX")) {
                            donnees[i][4] = 15;
                        }
                        else if((s.getSalle()).equals("Salle VIP")) {
                            donnees[i][4] = 15;
                        }
                        else if((s.getSalle()).equals("Salle Gold Class")) {
                            donnees[i][4] = 16;
                        }
                    }
                    JTable table = new JTable(donnees, entetes);
                    JScrollPane scrollPane = new JScrollPane(table);
                    JOptionPane.showMessageDialog(null, scrollPane, "Liste des séances", JOptionPane.PLAIN_MESSAGE);
                }
                break;
            case "Mettre à jour une séance":
                int updateId = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID de la séance à mettre à jour : "));
                try {
                    Seance updateSeance = seanceDAO.trouverSeanceParId(updateId);
                    if (updateSeance != null) {
                        filmId = Integer.parseInt(JOptionPane.showInputDialog("Entrez le nouvel ID du film : "));
                        dateTimeString = JOptionPane.showInputDialog("Entrez la nouvelle date et heure de la séance (AAAA-MM-JJ HH:MM:SS) : ");
                        heure = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        salle = JOptionPane.showInputDialog("Entrez le nouveau numéro de salle : ");
                        updateSeance.setFilmId(filmId);
                        updateSeance.setHeure(heure);
                        updateSeance.setSalle(salle);
                        seanceDAO.mettreAJourSeance(updateSeance);
                        JOptionPane.showMessageDialog(null, "Séance mise à jour avec succès.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Séance non trouvée.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage());
                }
                break;
            case "Supprimer une séance":
                int deleteId = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID de la séance à supprimer : "));
                try {
                    seanceDAO.supprimerSeance(deleteId);
                    JOptionPane.showMessageDialog(null, "Séance supprimée avec succès.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage());
                }
                break;
            case "Retour au menu principal":
                
                break;
            default:
                System.out.println("Commande inconnue: " + command);
                break;
        }
    }
}