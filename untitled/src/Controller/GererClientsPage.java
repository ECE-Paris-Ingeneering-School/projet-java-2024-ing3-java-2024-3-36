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
import Vue.ClientsVue;


public class GererClientsPage extends JFrame implements ActionListener {
    private ClientDAO clientDAO;
    private static Scanner scanner;

    private JButton btnAjouterClient;
    private JButton btnTrouverClient;
    private JButton btnListerClients;
    private JButton btnMettreAJourClient;
    private JButton btnSupprimerClient;
    private JButton btnRetour;

    private int idSuppression;


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

        new ClientsVue(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Ajouter un client":
                String nom = JOptionPane.showInputDialog("Entrez le nom du client : ");
                String email = JOptionPane.showInputDialog("Entrez l'email du client : ");
                String[] options_type = {"non membre", "membre"};
                String type = (String) JOptionPane.showInputDialog(null, "Sélectionnez le type de compte:", "Choix de type", JOptionPane.QUESTION_MESSAGE, null, options_type, options_type[0]);
                String etat = "null";
                if("membre".equals(type)) {
                    String[] categories = {"régulier", "sénior", "enfant"};
                    etat = (String) JOptionPane.showInputDialog(null, "Sélectionnez la catégorie de compte :", "Choix de catégorie", JOptionPane.QUESTION_MESSAGE, null, categories, categories[0]);
                }
                String motDePasse = JOptionPane.showInputDialog("Entrez votre mot de passe");
                Client client = new Client(0, nom, email, type, motDePasse, etat);
                try {
                    clientDAO.ajouterClient(client);
                    JOptionPane.showMessageDialog(null, "Client ajouté avec succès.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage());
                }
                break;

            case "Trouver un client par ID":
                int id = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID du client : "));
                try {
                    Client foundClient = clientDAO.trouverClientParId(id);
                    JOptionPane.showMessageDialog(null, foundClient != null ? foundClient.toString() : "Client non trouvé.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la recherche : " + ex.getMessage());
                }
                break;

            case "Lister tous les clients":
                try {
                    List<Client> clients = clientDAO.listerTousLesClients();
                    if (clients.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Aucun client disponible.");
                    } else {
                        String[] headers = {"ID", "Nom", "Email", "Type", "Mot de Passe"};
                        Object[][] data = new Object[clients.size()][5];
                        for (int i = 0; i < clients.size(); i++) {
                            Client c = clients.get(i);
                            data[i][0] = c.getId();
                            data[i][1] = c.getNom();
                            data[i][2] = c.getEmail();
                            data[i][3] = c.getType();
                            data[i][4] = c.getMotDePasse();
                        }
                        JTable table = new JTable(data, headers);
                        JScrollPane scrollPane = new JScrollPane(table);
                        JOptionPane.showMessageDialog(null, scrollPane, "Liste des clients", JOptionPane.PLAIN_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors du listing des clients : " + ex.getMessage());
                }
                break;

            case "Mettre à jour un client":
                id = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID du client à mettre à jour : "));
                try {
                    client = clientDAO.trouverClientParId(id);
                    if (client != null) {
                        String nouveauNom = JOptionPane.showInputDialog("Entrez le nouveau nom du client : ");
                        String nouvelEmail = JOptionPane.showInputDialog("Entrez le nouvel email du client : ");
                        String nouveauType = JOptionPane.showInputDialog("Entrez le nouveau type du client : ");
                        String nouveauMotDePasse = JOptionPane.showInputDialog("Entrez le nouveau mot de passe du client : ");
                        client.setNom(nouveauNom);
                        client.setEmail(nouvelEmail);
                        client.setType(nouveauType);
                        client.setMotDePasse(nouveauMotDePasse);
                        clientDAO.mettreAJourClient(client);
                        JOptionPane.showMessageDialog(null, "Client mis à jour avec succès.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Client non trouvé.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la mise à jour : " + ex.getMessage());
                }
                break;

            case "Supprimer un client":
                idSuppression = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID du client à supprimer : "));
                try {
                    clientDAO.supprimerClient(idSuppression);
                    JOptionPane.showMessageDialog(null, "Client supprimé avec succès.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage());
                }
                break;

            case "Retour au menu principal":
                dispose();
                break;

            default:
                JOptionPane.showMessageDialog(null, "Commande inconnue: " + command);
                break;
        }
    }




}
