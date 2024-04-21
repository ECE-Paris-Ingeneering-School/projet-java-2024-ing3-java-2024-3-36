package Controller;

import Modele.InterfaceDAO.*;
import Modele.Objets.Employe;
import Vue.EmployesVue;

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

        new EmployesVue(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Ajouter un employé":
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
                break;
            case "Trouver un employé par ID":
                int id = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID de l'employé : "));
                Employe foundEmploye = null;
                try {
                    foundEmploye = employeDAO.trouverEmployeParId(id);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la recherche : " + ex.getMessage());
                }
                JOptionPane.showMessageDialog(null, foundEmploye != null ? foundEmploye.toString() : "Employé non trouvé.");
                break;
            case "Lister tous les employés":
                List<Employe> employes = null;
                try {
                    employes = employeDAO.listerTousLesEmployes();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors du listing des employés : " + ex.getMessage());
                }
                if (employes.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Aucun employé disponible.");
                } else {
                    String[] headers = {"ID", "Nom", "Position", "Email", "Mot de passe"};
                    Object[][] data = new Object[employes.size()][5];
                    for (int i = 0; i < employes.size(); i++) {
                        Employe emp = employes.get(i);
                        data[i][0] = emp.getId();
                        data[i][1] = emp.getNom();
                        data[i][2] = emp.getPosition();
                        data[i][3] = emp.getEmail();
                        data[i][4] = emp.getMotDePasse();
                    }
                    JTable table = new JTable(data, headers);
                    JScrollPane scrollPane = new JScrollPane(table);
                    JOptionPane.showMessageDialog(null, scrollPane, "Liste des employés", JOptionPane.PLAIN_MESSAGE);
                }
                break;
            case "Mettre à jour un employé":
                int updateId = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID de l'employé à mettre à jour : "));
                Employe updateEmploye = null;
                try {
                    updateEmploye = employeDAO.trouverEmployeParId(updateId);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la mise à jour : " + ex.getMessage());
                }
                if (updateEmploye != null) {
                    String nouveauNom = JOptionPane.showInputDialog("Entrez le nouveau nom de l'employé : ");
                    String nouvellePosition = JOptionPane.showInputDialog("Entrez la nouvelle position de l'employé : ");
                    String nouvelEmail = JOptionPane.showInputDialog("Entrez le nouvel email de l'employé : ");
                    String nouveauMotDePasse = JOptionPane.showInputDialog("Entrez le nouveau mot de passe de l'employé : ");
                    updateEmploye.setNom(nouveauNom);
                    updateEmploye.setPosition(nouvellePosition);
                    updateEmploye.setEmail(nouvelEmail);
                    updateEmploye.setMotDePasse(nouveauMotDePasse);
                    try {
                        employeDAO.mettreAJourEmploye(updateEmploye);
                        JOptionPane.showMessageDialog(null, "Employé mis à jour avec succès.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la mise à jour : " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Employé non trouvé pour mise à jour.");
                }
                break;
            case "Supprimer un employé":
                int deleteId = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID de l'employé à supprimer : "));
                try {
                    employeDAO.supprimerEmploye(deleteId);
                    JOptionPane.showMessageDialog(null, "Employé supprimé avec succès.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la suppression : " + ex.getMessage());
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
}