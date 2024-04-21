package Controller;

import Modele.InterfaceDAO.*;
import Modele.Objets.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Scanner;

import Modele.Objets.Film;
import Utils.*;


public class GererClientsPage extends JFrame implements ActionListener {
    private ClientDAO clientDAO;
    private static Scanner scanner;

    private JButton btnAjouterClient;
    private JButton btnTrouverClient;
    private JButton btnListerClients;
    private JButton btnMettreAJourClient;
    private JButton btnSupprimerClient;
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




    public GererClientsPage(ClientDAO clientDAO, BilletDAO billetDAO, FilmDAO filmDAO, EmployeDAO employeDAO, SeanceDAO seanceDAO, OffresDAO offresDAO, Scanner scanner, int userID) {
        this.clientDAO = clientDAO;
        this.scanner = scanner;

        setTitle("Gérer les clients");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        btnAjouterClient = createStyledButton("Ajouter un client");
        btnAjouterClient.addActionListener(this);
        add(btnAjouterClient);

        btnTrouverClient = createStyledButton("Trouver un client par ID");
        btnTrouverClient.addActionListener(this);
        add(btnTrouverClient);

        btnListerClients = createStyledButton("Lister tous les clients");
        btnListerClients.addActionListener(this);
        add(btnListerClients);

        btnMettreAJourClient = createStyledButton("Mettre à jour un client");
        btnMettreAJourClient.addActionListener(this);
        add(btnMettreAJourClient);

        btnSupprimerClient = createStyledButton("Supprimer un client");
        btnSupprimerClient.addActionListener(this);
        add(btnSupprimerClient);

        btnRetour = createStyledButton("Retour au menu principal");
        btnRetour.addActionListener(this);
        add(btnRetour);

        setSize(400, 400);
        setVisible(true);
    }
    public static class ConnexionPage extends JFrame implements ActionListener {

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

            setTitle("Connexion");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new GridLayout(3, 2));

            JLabel emailLabel = new JLabel("Email:");
            add(emailLabel);
            emailField = new JTextField();
            add(emailField);

            JLabel passwordLabel = new JLabel("Mot de passe:");
            add(passwordLabel);
            passwordField = new JPasswordField();
            add(passwordField);

            loginButton = new JButton("Se connecter");
            loginButton.addActionListener(this);
            add(loginButton);

            createAccountButton = new JButton("Créer un compte");
            createAccountButton.addActionListener(this);
            add(createAccountButton);

            setSize(400, 300); // Définir une taille moyenne
            setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran
            setVisible(true);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButton) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    String nomClient = clientDAO.trouverNomClientParEmailEtMotDePasse(email, password);
                    JOptionPane.showMessageDialog(null, "Bonjour " + nomClient + " !");
                    if (estConnexionValide()) {
                        int userID = clientDAO.trouverIDParEmailEtMotDePasse(email,password);
                        setVisible(false);
                        PageAccueil pageAccueil = new PageAccueil(userID,clientDAO,filmDAO,employeDAO,seanceDAO,billetDAO,offresDAO,scanner);
                        pageAccueil.setVisible(true);
                    } else {
                        // Gérer le cas où la connexion n'est pas valide
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur de connexion : " + ex.getMessage());
                }
            } else if (e.getSource() == createAccountButton) {
                String nom = JOptionPane.showInputDialog("Entrez votre nom:");
                String email = JOptionPane.showInputDialog("Entrez votre email:");
                String type = "null";
                String etat = "null";
                if (!email.equals("admin@admin.fr")) {

                    String[] options_type = {"non membre", "membre"};
                    type = (String) JOptionPane.showInputDialog(
                            null,
                            "Sélectionnez votre type de compte: :",
                            "Choix de type",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options_type,
                            options_type[0]);

                    if (type == "membre") {

                        String[] options = {"régulier", "sénior", "enfant"};
                        etat = (String) JOptionPane.showInputDialog(
                                null,
                                "Sélectionnez votre catégorie de compte :",
                                "Choix de catégorie",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]);
                    } else {
                        etat = "null";
                    }
                }
                else {
                    type = "null";
                    etat = "null";
                }

                String password = JOptionPane.showInputDialog("Entrez votre mot de passe:");

                Client client = new Client(0, nom, email, type, password, etat);
                try {
                    clientDAO.ajouterClient(client);
                    JOptionPane.showMessageDialog(null, "Compte créé avec succès.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la création du compte : " + ex.getMessage());
                }
            }
        }


    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAjouterClient) {
            // Ajouter un client
            String nom = JOptionPane.showInputDialog("Entrez le nom du client : ");
            String email = JOptionPane.showInputDialog("Entrez l'email du client : ");

            String[] options_type = {"non membre", "membre"};
            String type = (String) JOptionPane.showInputDialog(
                    null,
                    "Sélectionnez le type de compte: :",
                    "Choix de type",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options_type,
                    options_type[0]);

            String etat = "null";

            if(type=="membre") {
                String[] options = {"régulier", "sénior", "enfant"};
                etat = (String) JOptionPane.showInputDialog(
                        null,
                        "Sélectionnez la catégorie de compte :",
                        "Choix de catégorie",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);
            }
            else {
                etat = "null";
            }
            String motDePasse = JOptionPane.showInputDialog(("Entrez votre mot de passe"));
            Client client = new Client(10, nom, email, type, motDePasse, etat);
            try {
                clientDAO.ajouterClient(client);
                JOptionPane.showMessageDialog(null, "Client ajouté avec succès.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage());
            }
        } else if (e.getSource() == btnTrouverClient) {
            // Trouver un client par ID
            int id = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID du client : "));
            Client client = null;
            try {
                client = clientDAO.trouverClientParId(id);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            JOptionPane.showMessageDialog(null, client != null ? client.toString() : "Client non trouvé.");
        } else if (e.getSource() == btnListerClients) {
            // Lister tous les clients
            List<Client> clients = null;
            try {
                clients = clientDAO.listerTousLesClients();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            if (clients.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Aucun client disponible.");
            } else {
                // Création d'un modèle de tableau pour afficher les clients dans une JTable
                String[] entetes = {"ID", "Nom", "Email", "Type", "Mot de Passe"};
                Object[][] donnees = new Object[clients.size()][5];

                for (int i = 0; i < clients.size(); i++) {
                    Client client = clients.get(i);
                    donnees[i][0] = client.getId();
                    donnees[i][1] = client.getNom();
                    donnees[i][2] = client.getEmail();
                    donnees[i][3] = client.getType();
                    donnees[i][4] = client.getMotDePasse();
                }

                JTable table = new JTable(donnees, entetes);
                table.setDefaultEditor(Object.class, null); // Désactiver l'édition de la table
                JScrollPane scrollPane = new JScrollPane(table);
                JOptionPane.showMessageDialog(null, scrollPane, "Liste des clients", JOptionPane.PLAIN_MESSAGE);
            }
        }
        else if (e.getSource() == btnMettreAJourClient) {
            // Mettre à jour un client
            int id = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID du client à mettre à jour : "));
            Client client = null;
            try {
                client = clientDAO.trouverClientParId(id);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            if (client == null) {
                JOptionPane.showMessageDialog(null, "Client non trouvé.");
            } else {
                String nouveauNom = JOptionPane.showInputDialog("Entrez le nouveau nom du client : ");
                String nouvelEmail = JOptionPane.showInputDialog("Entrez le nouvel email du client : ");
                String nouveauType = JOptionPane.showInputDialog("Entrez le nouveau type du client : ");
                String nouveauMdp = JOptionPane.showInputDialog("Entrez le nouveau Mot de passe du client : ");
                client.setNom(nouveauNom);
                client.setEmail(nouvelEmail);
                client.setType(nouveauType);
                client.setType(nouveauMdp);
                try {
                    clientDAO.mettreAJourClient(client);
                    JOptionPane.showMessageDialog(null, "Client mis à jour avec succès.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage());
                }
            }
        } else if (e.getSource() == btnSupprimerClient) {
            // Supprimer un client
            int idSuppression = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID du client à supprimer : "));
            try {
                clientDAO.supprimerClient(idSuppression);
                JOptionPane.showMessageDialog(null, "Client supprimé avec succès.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage());
            }
        } else if (e.getSource() == btnRetour) {
            dispose();
        }
    }


}
