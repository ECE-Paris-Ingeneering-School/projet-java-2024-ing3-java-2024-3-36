package Controller;

import Modele.InterfaceDAO.*;
import Modele.Objets.Film;
import Vue.AcceuilVue;
import Vue.FilmsVue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;



public  class GererFilmsPage extends JFrame implements ActionListener {

    private UIUpdater uiUpdater;

    private final int userID;
    private JButton btnAjouterFilm;
    private JButton btnTrouverFilm;
    private JButton btnListerFilms;
    private JButton btnMettreAJourFilm;
    private JButton btnSupprimerFilm;
    private JButton btnRetour;

    private ClientDAO clientDAO;
    private BilletDAO billetDAO;
    private FilmDAO filmDAO;
    private SeanceDAO seanceDAO;
    private OffresDAO offresDAO;
    private EmployeDAO employeDAO;
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

    public GererFilmsPage(FilmDAO filmDAO, ClientDAO clientDAO, EmployeDAO employeDAO, SeanceDAO seanceDAO, BilletDAO billetDAO, OffresDAO offresDAO, Scanner scanner, int userID, UIUpdater uiUpdater) {

        this.uiUpdater = uiUpdater;
        this.clientDAO = clientDAO;
        this.filmDAO = filmDAO;
        this.employeDAO = employeDAO;
        this.seanceDAO = seanceDAO;
        this.billetDAO = billetDAO;
        this.offresDAO = offresDAO;
        this.scanner = scanner;

        this.userID = userID;

        new FilmsVue(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Ajouter un film":
                String titre = JOptionPane.showInputDialog("Entrez le titre du film : ");
                String genre = JOptionPane.showInputDialog("Entrez le genre du film : ");
                int duree = Integer.parseInt(JOptionPane.showInputDialog("Entrez la durée du film : "));
                String description = JOptionPane.showInputDialog("Entrez la description du film : ");
                String realisateur = JOptionPane.showInputDialog("Entrez le réalisateur du film : ");
                byte[] affiche = selectAffiche();
                String url_ba = JOptionPane.showInputDialog("Entrez l'URL de la Bande Annonce du film : ");
                Film film = new Film(0, titre, genre, duree, description, realisateur, affiche, url_ba);
                try {
                    filmDAO.ajouterFilm(film);
                    JOptionPane.showMessageDialog(null, "Film ajouté avec succès.");



                    break;

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage());
                }
                break;
            case "Trouver un film par ID":
                int id = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID du film : "));
                Film filmFound = filmDAO.recupFilm(id);
                JOptionPane.showMessageDialog(null, filmFound != null ? filmFound.toString() : "Film non trouvé.");
                break;
            case "Lister tous les films":
                List<Film> films = null;
                try {
                    films = filmDAO.recupAllFilms();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                if (films.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Aucun film disponible.");
                } else {
                    String[] entetes = {"ID", "Titre", "Genre", "Durée (min)", "Description", "Réalisateur"};
                    Object[][] donnees = new Object[films.size()][6];
                    for (int i = 0; i < films.size(); i++) {
                        Film f = films.get(i);
                        donnees[i][0] = f.getId();
                        donnees[i][1] = f.getTitre();
                        donnees[i][2] = f.getGenre();
                        donnees[i][3] = f.getDuree();
                        donnees[i][4] = f.getDescription();
                        donnees[i][5] = f.getRealisateur();
                    }
                    JTable table = new JTable(donnees, entetes);
                    JScrollPane scrollPane = new JScrollPane(table);
                    JOptionPane.showMessageDialog(null, scrollPane, "Liste des films", JOptionPane.PLAIN_MESSAGE);
                }
                break;
            case "Mettre à jour un film":
                int updateId = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID du film à mettre à jour : "));
                Film updateFilm = filmDAO.recupFilm(updateId);
                if (updateFilm == null) {
                    JOptionPane.showMessageDialog(null, "Film non trouvé.");
                } else {
                    String nouveauTitre = JOptionPane.showInputDialog("Entrez le nouveau titre du film : ");
                    String nouveauGenre = JOptionPane.showInputDialog("Entrez le nouveau genre du film : ");
                    int nouvelleDuree = Integer.parseInt(JOptionPane.showInputDialog("Entrez la nouvelle durée du film : "));
                    String nouvelleDescription = JOptionPane.showInputDialog("Entrez la nouvelle description du film : ");
                    String nouveauRealisateur = JOptionPane.showInputDialog("Entrez le nouveau réalisateur du film : ");
                    String nouveauUrl_ba = JOptionPane.showInputDialog("Entrez le nouveau URL de la Bande Annonce du film : ");
                    updateFilm.setTitre(nouveauTitre);
                    updateFilm.setGenre(nouveauGenre);
                    updateFilm.setDuree(nouvelleDuree);
                    updateFilm.setDescription(nouvelleDescription);
                    updateFilm.setRealisateur(nouveauRealisateur);
                    updateFilm.setUrl_ba(nouveauUrl_ba);
                    try {
                        filmDAO.updateFilm(updateFilm);
                        JOptionPane.showMessageDialog(null, "Film mis à jour avec succès.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage());
                    }
                }
                break;
            case "Supprimer un film":
                int deleteId = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID du film à supprimer : "));
                try {
                    filmDAO.supprimerFilm(deleteId);
                    JOptionPane.showMessageDialog(null, "Film supprimé avec succès.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage());
                }
                break;
            case "Retour au menu principal":
                dispose();
                break;
            default:
                System.out.println("Commande inconnue: " + command);
                break;
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