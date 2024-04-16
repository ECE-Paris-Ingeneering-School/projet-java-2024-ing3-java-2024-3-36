package Controller;
//test
import Modele.ImplementationsDAO.*;
import Modele.InterfaceDAO.*;
import Modele.Objets.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Test {

    private static BilletDAO billetDAO = new BilletDAOImpl();
    private static ClientDAO clientDAO = new ClientDAOImpl();
    private static EmployeDAO employeDAO = new EmployeDAOImpl();
    private static FilmDAO filmDAO = new FilmDAOImpl();
    private static SeanceDAO seanceDAO = new SeanceDAOImpl();
    private static Scanner scanner = new Scanner(System.in);

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



    public static class PageAccueil extends JFrame implements ActionListener {
        private final int userID;
        private JButton btnGererBillets;
        private JButton btnGererClients;
        private JButton btnGererEmployes;
        private JButton btnGererFilms;
        private JButton btnGererSeances;
        private JButton btnQuitter;

        public PageAccueil(int userID) throws Exception {
            this.userID = userID;
            setTitle("Page d'Accueil");
            String mail = clientDAO.trouverEmailParId(userID);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Création des boutons
            btnGererBillets = createStyledButton("Gérer mes billets");
            btnGererBillets.addActionListener(this);

            btnGererClients = createStyledButton("Gérer les clients");
            btnGererClients.addActionListener(this);

            btnGererEmployes = createStyledButton("Gérer les employés");
            btnGererEmployes.addActionListener(this);

            btnGererFilms = createStyledButton("Gérer les films");
            btnGererFilms.addActionListener(this);

            btnGererSeances = createStyledButton("Gérer les Séances");
            btnGererSeances.addActionListener(this);

            btnQuitter = createStyledButton("Quitter");
            btnQuitter.addActionListener(this);

            // Création du label
            JLabel golmonLabel = new JLabel("Golmon Pathé");
            golmonLabel.setHorizontalAlignment(JLabel.CENTER);
            golmonLabel.setVerticalAlignment(JLabel.CENTER); // Centrer verticalement
            Font font = golmonLabel.getFont();
            golmonLabel.setFont(new Font(font.getName(), Font.BOLD, 36));
            golmonLabel.setBackground(Color.DARK_GRAY);

            // Panneau pour le label
            JPanel labelPanel = new JPanel(new BorderLayout());
            labelPanel.setBackground(new Color(255, 215, 0));
            labelPanel.setPreferredSize(new Dimension(getWidth(), 100));
            labelPanel.add(golmonLabel, BorderLayout.CENTER);

            // Panneau pour les boutons
            JPanel buttonPanel = createButtonPanel(mail);

            // Création du panneau pour les affiches
            JPanel posterPanel = createPosterPanel();

            // Création du panneau pour le texte en bas de la fenêtre
            JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Utilisation d'un FlowLayout pour centrer le texte
            footerPanel.setBackground(new Color(255, 215, 0));

            // Création du texte à afficher
            String footerText = "© 2024 Golmon Pathé. Tous droits réservés.";

            // Création du composant texte
            JLabel footerLabel = new JLabel(footerText);
            footerLabel.setForeground(Color.BLACK); // Couleur du texte
            footerPanel.add(footerLabel);

            // Ajout des panneaux au conteneur principal
            // Ajout des panneaux au conteneur principal avec un BorderLayout
            // Panneau pour contenir les éléments
            JPanel mainPanel = new JPanel(new BorderLayout());

// Ajouter le labelPanel en haut du mainPanel
            mainPanel.add(labelPanel, BorderLayout.NORTH);

// Créer un panneau pour contenir les boutons et les affiches
            JPanel centerPanel = new JPanel(new BorderLayout());

// Ajouter les boutons au centre du centerPanel
            centerPanel.add(buttonPanel, BorderLayout.NORTH);

// Ajouter les affiches en dessous des boutons dans le centerPanel
            centerPanel.add(posterPanel, BorderLayout.CENTER);

// Ajouter le centerPanel au centre du mainPanel
            mainPanel.add(centerPanel, BorderLayout.CENTER);

// Ajouter le footerPanel en bas du mainPanel
            mainPanel.add(footerPanel, BorderLayout.SOUTH);

// Ajouter le mainPanel au contenu de la JFrame
            add(mainPanel);



            // Maximiser la fenêtre
            setExtendedState(JFrame.MAXIMIZED_BOTH);

            setLocationRelativeTo(null);
            getContentPane().setBackground(Color.DARK_GRAY);
            setVisible(true);
        }



        private JPanel createButtonPanel(String mail) {
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.setBackground(Color.DARK_GRAY);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            if (!mail.equals("admin@admin.fr")) {
                btnGererBillets.setPreferredSize(new Dimension((screenSize.width / 2) - 10, 100));
                buttonPanel.add(btnGererBillets);
                btnQuitter.setPreferredSize(new Dimension((screenSize.width / 2) - 10, 100));
                buttonPanel.add(btnQuitter);
            } else {
                btnQuitter.setPreferredSize(new Dimension((screenSize.width / 5) - 10, 100));
                btnGererClients.setPreferredSize(new Dimension((screenSize.width / 5) - 10, 100));
                btnGererEmployes.setPreferredSize(new Dimension((screenSize.width / 5) - 10, 100));
                btnGererFilms.setPreferredSize(new Dimension((screenSize.width / 5) - 10, 100));
                btnGererSeances.setPreferredSize(new Dimension((screenSize.width / 5) - 10, 100));

                JPanel adminPanel = new JPanel(new GridLayout(1, 5)); // Création d'un GridLayout pour aligner les boutons pour l'admin
                adminPanel.setBackground(Color.DARK_GRAY);
                adminPanel.setPreferredSize(new Dimension(screenSize.width, 100));

                adminPanel.add(btnGererClients);
                adminPanel.add(btnGererEmployes);
                adminPanel.add(btnGererFilms);
                adminPanel.add(btnGererSeances);
                adminPanel.add(btnQuitter);

                buttonPanel.add(adminPanel);
            }

            return buttonPanel;
        }

        private JPanel createPosterPanel() {
            JPanel posterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Utilisation d'un FlowLayout pour aligner les affiches
            posterPanel.setBackground(Color.DARK_GRAY);

            FilmDAOImpl filmDAO = new FilmDAOImpl(); // Instancier votre DAO

            // Récupérer les données d'image à partir de la méthode récupérerAfficheBytes() de votre DAO
            List<byte[]> affichesBytes = filmDAO.recupererAfficheBytes();

            // Parcourir la liste des données d'image
            for (byte[] imageData : affichesBytes) {
                try (ByteArrayInputStream bis = new ByteArrayInputStream(imageData)) {
                    Image image = ImageIO.read(bis);
                    if (image != null) {
                        System.out.println("Conversion du BLOB en image réussie !");
                        // Créer un label pour afficher l'image
                        JLabel posterLabel = new JLabel(new ImageIcon(image));
                        posterPanel.add(posterLabel);
                    } else {
                        System.err.println("Le BLOB ne représente pas une image valide.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Erreur lors de la conversion du BLOB en image : " + e.getMessage());
                }
            }

            return posterPanel;
        }



        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnGererBillets) {
                new GererBilletsPage(billetDAO, scanner, userID);
            } else if (e.getSource() == btnGererClients) {
                new GererClientsPage(clientDAO, scanner, userID);
            } else if (e.getSource() == btnGererEmployes) {
                new GererEmployesPage(employeDAO, scanner, userID);
            } else if (e.getSource() == btnGererFilms) {
                new GererFilmsPage(filmDAO, scanner, userID);
            } else if (e.getSource() == btnGererSeances) {
                new GererSeancesPage(seanceDAO, scanner, userID);
            } else if (e.getSource() == btnQuitter) {
                System.out.println("Au revoir !");
                System.exit(0);
            }
        }

    }


    public static void main(String[] args) {
        // Initialisez votre ClientDAO ici
        GererClientsPage.ConnexionPage connexionPage = new GererClientsPage.ConnexionPage(clientDAO);


    }






    public static class GererBilletsPage extends JFrame implements ActionListener {
        private final int userID;
        private BilletDAO billetDAO;
        private Scanner scanner;

        private JButton btnAjouterBillet;

        private JButton btnListerBillets;

        private JButton btnSupprimerBillet;
        private JButton btnRetour;

        public GererBilletsPage(BilletDAO billetDAO, Scanner scanner, int userID) {
            this.userID = userID;
            this.billetDAO = billetDAO;
            this.scanner = scanner;

            setTitle("Gérer Mes billets");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(6, 1));

            // Initialisation des boutons
            btnAjouterBillet = createStyledButton("Réserver une séance");
            btnAjouterBillet.addActionListener(this);
            add(btnAjouterBillet);



            btnListerBillets = createStyledButton("Lister toutes mes séances");
            btnListerBillets.addActionListener(this);
            add(btnListerBillets);



            btnSupprimerBillet = createStyledButton("Supprimer un billet");
            btnSupprimerBillet.addActionListener(this);
            add(btnSupprimerBillet);

            btnRetour = createStyledButton("Retour au menu principal");
            btnRetour.addActionListener(this);
            add(btnRetour);

            // Taille de la fenêtre
            setSize(400, 400);
            setVisible(true);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnAjouterBillet) {
                System.out.println("");

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
                    String[] entetes = {"ID", "Film", "Date et heure", "Salle"};
                    Object[][] donnees = new Object[seances.size()][4];

                    for (int i = 0; i < seances.size(); i++) {
                        Seance seance = seances.get(i);
                        // Récupérer le titre du film associé à cette séance
                        String titreFilm = null;
                        try {
                            Film film = filmDAO.recupFilm(seance.getFilmId());
                            if (film != null) {
                                titreFilm = film.getTitre(); //PROBLEME RECUPERATioN TITRE
                            }
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }

                        donnees[i][0] = seance.getId();
                        donnees[i][1] = titreFilm;
                        donnees[i][2] = seance.getHeure();
                        donnees[i][3] = seance.getSalle();
                    }

                    JTable table = new JTable(donnees, entetes);
                    JScrollPane scrollPane = new JScrollPane(table);
                    JOptionPane.showMessageDialog(null, scrollPane, "Liste des séances", JOptionPane.PLAIN_MESSAGE);

                    // Obtenir l'index de la ligne sélectionnée
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Extraire l'ID de la séance sélectionnée
                        int seanceId = (int) table.getValueAt(selectedRow, 0);
                        double prix = Double.parseDouble(JOptionPane.showInputDialog("Entrez le prix du billet : "));
                        String categorie = JOptionPane.showInputDialog("Entrez la catégorie du billet : ");

                        // Création d'un objet Billet avec les données saisies
                        Billet billet = new Billet(100, seanceId, userID, prix, categorie);

                        // Appel de la méthode pour ajouter le billet dans la base de données
                        try {
                            billetDAO.ajouterBillet(billet);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }

                        // Affichage d'un message de confirmation
                        JOptionPane.showMessageDialog(null, "Billet ajouté avec succès.");
                    }
                }
            }

            else if (e.getSource() == btnListerBillets) {
                // Lister tous les billets de l'utilisateur courant (userID)
                List<Billet> billets = null;
                try {
                    billets = billetDAO.listerBilletsParClientId(userID);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                if (billets.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Aucun billet disponible pour cet utilisateur.");
                } else {
                    // Création d'un modèle de tableau pour afficher les billets dans une JTable
                    String[] entetes = {"ID", "ID Séance", "ID Client", "Prix", "Catégorie"};
                    Object[][] donnees = new Object[billets.size()][5];

                    for (int i = 0; i < billets.size(); i++) {
                        Billet billet = billets.get(i);
                        donnees[i][0] = billet.getId();
                        donnees[i][1] = billet.getSeanceId();// RECUPERER LE TITRE
                        donnees[i][2] = billet.getClientId();
                        donnees[i][3] = billet.getPrix();
                        donnees[i][4] = billet.getCategorie();
                    }

                    JTable table = new JTable(donnees, entetes);
                    JScrollPane scrollPane = new JScrollPane(table);
                    JOptionPane.showMessageDialog(null, scrollPane, "Liste des billets de l'utilisateur", JOptionPane.PLAIN_MESSAGE);
                }
            }

            else if (e.getSource() == btnSupprimerBillet) {
                // Récupérer tous les billets de l'utilisateur
                List<Billet> billetsUtilisateur = null;
                try {
                    System.out.println(userID);
                    billetsUtilisateur = billetDAO.listerBilletsParClientId(userID);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                // Vérifier s'il existe des billets pour l'utilisateur
                if (billetsUtilisateur.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Aucun billet disponible pour cet utilisateur.");
                } else {
                    // Créer un tableau de chaînes pour stocker les options de la liste déroulante
                    String[] optionsBillets = new String[billetsUtilisateur.size()];

                    // Remplir le tableau avec les options de la liste déroulante
                    for (int i = 0; i < billetsUtilisateur.size(); i++) {
                        Billet billet = billetsUtilisateur.get(i);
                        optionsBillets[i] = "ID: " + billet.getId() + ", Séance ID: " + billet.getSeanceId() +
                                ", Prix: " + billet.getPrix() + ", Catégorie: " + billet.getCategorie();
                    }

                    // Afficher la boîte de dialogue pour sélectionner un billet à supprimer
                    String selectedOption = (String) JOptionPane.showInputDialog(null, "Choisissez le billet à supprimer :",
                            "Sélectionner un Billet", JOptionPane.PLAIN_MESSAGE, null, optionsBillets, optionsBillets[0]);

                    if (selectedOption != null) {
                        // Extraire l'ID du billet sélectionné
                        // Trouver l'index de départ de l'ID
                        int idIndex = selectedOption.indexOf("ID: ") + 4;

// Extraire l'ID du billet sélectionné à partir de la chaîne
                        int idSuppression = Integer.parseInt(selectedOption.substring(idIndex, selectedOption.indexOf(",", idIndex)));


                        // Supprimer le billet sélectionné
                        try {
                            billetDAO.supprimerBillet(idSuppression);
                            JOptionPane.showMessageDialog(null, "Billet supprimé avec succès.");
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
            else if (e.getSource() == btnRetour) {
                dispose(); // Ferme la fenêtre actuelle
            }
        }

    }


    public static class GererClientsPage extends JFrame implements ActionListener {
        private ClientDAO clientDAO;
        private Scanner scanner;

        private JButton btnAjouterClient;
        private JButton btnTrouverClient;
        private JButton btnListerClients;
        private JButton btnMettreAJourClient;
        private JButton btnSupprimerClient;
        private JButton btnRetour;

        public GererClientsPage(ClientDAO clientDAO, Scanner scanner, int userID) {
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
            private ClientDAO clientDAO;

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

            public ConnexionPage(ClientDAO clientDAO) {
                this.clientDAO = clientDAO;

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
                            PageAccueil pageAccueil = new PageAccueil(userID);
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
                    String type = JOptionPane.showInputDialog("Entrez votre type de compte:");

                    String[] options = {"régulier", "sénior", "enfant"};
                    String etat = (String) JOptionPane.showInputDialog(
                            null,
                            "Sélectionnez votre catégorie de compte (régulier, sénior ou enfant) :",
                            "Choix de catégorie",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);

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
                String type = JOptionPane.showInputDialog("Entrez le type du client : ");
                String[] options = {"régulier", "sénior", "enfant"};
                String etat = (String) JOptionPane.showInputDialog(
                        null,
                        "Sélectionnez la catégorie de compte (régulier, sénior ou enfant) :",
                        "Choix de catégorie",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);
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

    public static class GererEmployesPage extends JFrame implements ActionListener {
        private EmployeDAO employeDAO;
        private Scanner scanner;

        private JButton btnAjouterEmploye;
        private JButton btnTrouverEmploye;
        private JButton btnListerEmployes;
        private JButton btnMettreAJourEmploye;
        private JButton btnSupprimerEmploye;
        private JButton btnRetour;

        public GererEmployesPage(EmployeDAO employeDAO, Scanner scanner, int userID) {
            this.employeDAO = employeDAO;
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
    public static class GererFilmsPage extends JFrame implements ActionListener {
        private FilmDAO filmDAO;
        private Scanner scanner;

        private JButton btnAjouterFilm;
        private JButton btnTrouverFilm;
        private JButton btnListerFilms;
        private JButton btnMettreAJourFilm;
        private JButton btnSupprimerFilm;
        private JButton btnRetour;
    public GererFilmsPage(FilmDAO filmDAO, Scanner scanner, int userID) {
        this.filmDAO = filmDAO;
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
            Film film = new Film(0, titre, genre, duree, description, realisateur, affiche);
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

                film.setTitre(nouveauTitre);
                film.setGenre(nouveauGenre);
                film.setDuree(nouvelleDuree);
                film.setDescription(nouvelleDescription);
                film.setRealisateur(nouveauRealisateur);

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
    public static class GererSeancesPage extends JFrame implements ActionListener {
        private SeanceDAO seanceDAO;
        private Scanner scanner;

        private JButton btnAjouterSeance;
        private JButton btnTrouverSeance;
        private JButton btnListerSeances;
        private JButton btnMettreAJourSeance;
        private JButton btnSupprimerSeance;
        private JButton btnRetour;

        public GererSeancesPage(SeanceDAO seanceDAO, Scanner scanner, int userID) {
            this.seanceDAO = seanceDAO;
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
                    String[] entetes = {"ID", "ID du film", "Date et heure", "Salle"};
                    Object[][] donnees = new Object[seances.size()][4];

                    for (int i = 0; i < seances.size(); i++) {
                        Seance seance = seances.get(i);
                        donnees[i][0] = seance.getId();
                        donnees[i][1] = seance.getFilmId();
                        donnees[i][2] = seance.getHeure();
                        donnees[i][3] = seance.getSalle();
                    }

                    JTable table = new JTable(donnees, entetes);
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



}
