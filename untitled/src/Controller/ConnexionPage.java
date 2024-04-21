package Controller;

import Modele.InterfaceDAO.*;
import Modele.Objets.Client;
import Vue.ConnexionVue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public  class ConnexionPage extends JFrame implements ActionListener {

    private final Scanner scanner;
    private ClientDAO clientDAO;
    private FilmDAO filmDAO;
    private EmployeDAO employeDAO;
    private SeanceDAO seanceDAO;
    private BilletDAO billetDAO;
    private OffresDAO offresDAO;

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createAccountButton;
    public boolean estConnexionValide() {
        // Récupérer les informations d'identification saisies par l'utilisateur
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        try {
            // Vérifier si le compte existe dans la base de données
            if(clientDAO.trouverNomClientParEmailEtMotDePasse(email, password) != null){
                return true;
            }
            else{
                return false;
            }

        } catch (Exception ex) {
            System.out.println("Erreur lors de la vérification de la connexion : " + ex.getMessage());
            return false;
        }
    }

    public ConnexionPage(ClientDAO clientDAO, BilletDAO billetDAO, EmployeDAO employeDAO, FilmDAO filmDAO, SeanceDAO seanceDAO,OffresDAO offresDAO,Scanner scanner) {
        this.clientDAO = clientDAO;
        this.filmDAO = filmDAO;
        this.employeDAO = employeDAO;
        this.seanceDAO = seanceDAO;
        this.billetDAO = billetDAO;
        this.offresDAO = offresDAO;
        this.scanner = scanner;

        new ConnexionVue(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Se connecter":
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    String nomClient = clientDAO.trouverNomClientParEmailEtMotDePasse(email, password);
                    if (nomClient != null && !nomClient.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Bonjour " + nomClient + " !");
                        int userID = clientDAO.trouverIDParEmailEtMotDePasse(email, password);
                        setVisible(false);
                        PageAccueil pageAccueil = new PageAccueil(userID, clientDAO, filmDAO, employeDAO, seanceDAO, billetDAO, offresDAO, scanner);
                        pageAccueil.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Informations de connexion incorrectes. Veuillez réessayer.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur de connexion : " + ex.getMessage());
                }
                break;
            case "Créer un compte":
                String nom = JOptionPane.showInputDialog("Entrez votre nom:");
                email = JOptionPane.showInputDialog("Entrez votre email:");
                String type = "non membre"; // Default type
                String etat = "null";
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
                        String[] options = {"régulier", "sénior", "enfant"};
                        etat = (String) JOptionPane.showInputDialog(
                                null,
                                "Sélectionnez votre catégorie de compte:",
                                "Choix de catégorie",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]);
                    } else {
                        etat = "null";
                    }
                }
                password = JOptionPane.showInputDialog("Entrez votre mot de passe:");
                Client client = new Client(0, nom, email, type, password, etat);
                try {
                    clientDAO.ajouterClient(client);
                    JOptionPane.showMessageDialog(null, "Compte créé avec succès.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la création du compte : " + ex.getMessage());
                }
                break;
            default:
                System.out.println("Commande inconnue: " + command);
                break;
        }
    }



}