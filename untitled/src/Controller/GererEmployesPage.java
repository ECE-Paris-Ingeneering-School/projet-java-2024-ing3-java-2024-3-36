package Controller;

import Modele.InterfaceDAO.*;
import Modele.Objets.Employe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Scanner;

public class GererEmployesPage extends JFrame implements ActionListener {
    private EmployeDAO employeDAO;
    private Scanner scanner;

    private JButton btnAjouterEmploye;
    private JButton btnTrouverEmploye;
    private JButton btnListerEmployes;
    private JButton btnMettreAJourEmploye;
    private JButton btnSupprimerEmploye;
    private JButton btnRetour;

    private ClientDAO clientDAO;
    private BilletDAO billetDAO;
    private FilmDAO filmDAO;
    private SeanceDAO seanceDAO;
    private OffresDAO offresDAO;


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
    public GererEmployesPage(EmployeDAO employeDAO, ClientDAO clientDAO, FilmDAO filmDAO, SeanceDAO seanceDAO, BilletDAO billetDAO, OffresDAO offresDAO, Scanner scanner, int userID) {


        this.clientDAO = clientDAO;
        this.filmDAO = filmDAO;
        this.employeDAO = employeDAO;
        this.seanceDAO = seanceDAO;
        this.billetDAO = billetDAO;
        this.offresDAO = offresDAO;
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