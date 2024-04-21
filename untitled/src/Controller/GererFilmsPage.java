package Controller;

import Modele.InterfaceDAO.*;
import Modele.Objets.Film;

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

    public GererFilmsPage(FilmDAO filmDAO, ClientDAO clientDAO, EmployeDAO employeDAO, SeanceDAO seanceDAO, BilletDAO billetDAO, OffresDAO offresDAO, Scanner scanner, int userID) {
        this.clientDAO = clientDAO;
        this.filmDAO = filmDAO;
        this.employeDAO = employeDAO;
        this.seanceDAO = seanceDAO;
        this.billetDAO = billetDAO;
        this.offresDAO = offresDAO;
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
            String url_ba = JOptionPane.showInputDialog("Entrez l'URL de la Bande Annonce du film : ");
            Film film = new Film(0, titre, genre, duree, description, realisateur, affiche, url_ba);
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
                String nouveauUrl_ba = JOptionPane.showInputDialog("Entrez le nouveau URL de la Bande Annoce du film : ");

                film.setTitre(nouveauTitre);
                film.setGenre(nouveauGenre);
                film.setDuree(nouvelleDuree);
                film.setDescription(nouvelleDescription);
                film.setRealisateur(nouveauRealisateur);
                film.setUrl_ba(nouveauUrl_ba);

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