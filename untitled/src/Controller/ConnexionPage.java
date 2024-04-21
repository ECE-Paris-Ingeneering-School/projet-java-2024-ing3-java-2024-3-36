package Controller;

import Vue.ConnexionVue;
import Modele.InterfaceDAO.*;
import Modele.Objets.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class ConnexionPage extends JFrame implements ActionListener {

    private ConnexionVue connexionVue;
    private ClientDAO clientDAO;
    private BilletDAO billetDAO;
    private EmployeDAO employeDAO;
    private FilmDAO filmDAO;
    private SeanceDAO seanceDAO;
    private OffresDAO offresDAO;
    private Scanner scanner;

    public ConnexionPage(ClientDAO clientDAO, BilletDAO billetDAO, EmployeDAO employeDAO, FilmDAO filmDAO, SeanceDAO seanceDAO, OffresDAO offresDAO, Scanner scanner) {
        this.clientDAO = clientDAO;
        this.filmDAO = filmDAO;
        this.employeDAO = employeDAO;
        this.seanceDAO = seanceDAO;
        this.billetDAO = billetDAO;
        this.offresDAO = offresDAO;
        this.scanner = scanner;


        this.connexionVue = new ConnexionVue(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if ("Se connecter".equals(command)) {
            handleLogin();
        } else if ("Créer un compte".equals(command)) {
            handleAccountCreation();
        }
    }

    private void handleLogin() {
        String email = connexionVue.getEmailField().getText();
        String password = new String(connexionVue.getPasswordField().getPassword());
        try {
            if (clientDAO.trouverNomClientParEmailEtMotDePasse(email, password) != null) {
                JOptionPane.showMessageDialog(null, "Connexion réussie!");
                int userID = clientDAO.trouverIDParEmailEtMotDePasse(email, password);
                connexionVue.FermerFenetre();
                PageAccueil pageAccueil = new PageAccueil(userID, clientDAO, filmDAO, employeDAO, seanceDAO, billetDAO, offresDAO, scanner);

            } else {
                JOptionPane.showMessageDialog(null, "Informations de connexion incorrectes. Veuillez réessayer.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erreur de connexion : " + ex.getMessage());
        }
    }


    private void handleAccountCreation() {
        String nom = JOptionPane.showInputDialog("Entrez votre nom:");
        String email = JOptionPane.showInputDialog("Entrez votre email:");
        String password = JOptionPane.showInputDialog("Entrez votre mot de passe:");
        String type = "non membre"; // Default type
        String etat = "null"; // Default state

        if (!email.equals("admin@admin.fr")) {
            String[] options_type = {"non membre", "membre"};
            type = (String) JOptionPane.showInputDialog(
                    null,
                    "Sélectionnez votre type de compte:",
                    "Choix de type",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options_type,
                    options_type[0]);

            if ("membre".equals(type)) {
                String[] options_etat = {"régulier", "sénior", "enfant"};
                etat = (String) JOptionPane.showInputDialog(
                        null,
                        "Sélectionnez votre catégorie de compte:",
                        "Choix de catégorie",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options_etat,
                        options_etat[0]);
            }
        }

        Client newClient = new Client(0, nom, email, type, password, etat);
        try {
            clientDAO.ajouterClient(newClient);
            JOptionPane.showMessageDialog(null, "Compte créé avec succès.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la création du compte : " + ex.getMessage());
        }
    }

}
