package Controller;

import Modele.InterfaceDAO.*;
import Vue.OffresVue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class GererOffresPage extends JFrame implements ActionListener {
    private OffresDAO offresDAO;
    private Scanner scanner;
    private int userID;

    private JButton btnOffreRegulier;
    private JButton btnOffreSenior;
    private JButton btnOffreEnfant;
    private JButton btnRetour;

    private ClientDAO clientDAO;
    private BilletDAO billetDAO;
    private FilmDAO filmDAO;
    private SeanceDAO seanceDAO;
    private EmployeDAO employeDAO;



    public GererOffresPage(OffresDAO offresDAO, ClientDAO clientDAO, FilmDAO filmDAO, EmployeDAO employeDAO, SeanceDAO seanceDAO, BilletDAO billetDAO, Scanner scanner, int userID) {
        this.clientDAO = clientDAO;
        this.filmDAO = filmDAO;
        this.employeDAO = employeDAO;
        this.seanceDAO = seanceDAO;
        this.billetDAO = billetDAO;
        this.offresDAO = offresDAO;
        this.scanner = scanner;
        this.userID = userID;

        new OffresVue(this);
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
        String command = e.getActionCommand();
        switch (command) {
            case "Modifier Offre Régulier":
                modifierOffre("régulier");
                break;
            case "Modifier Offre Senior":
                modifierOffre("sénior");
                break;
            case "Modifier Offre Enfant":
                modifierOffre("enfant");
                break;
            case "Retour au menu principal":
                dispose(); // Ferme la fenêtre actuelle
                break;
            default:
                System.out.println("Commande inconnue: " + command);
                break;
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
