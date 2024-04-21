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
        if (e.getSource() == btnAjouterClient) {
            String nom = JOptionPane.showInputDialog("Entrez le nom du client : ");
            String email = JOptionPane.showInputDialog("Entrez l'email du client : ");
            String[] options_type = {"non membre", "membre"};
            String type = (String) JOptionPane.showInputDialog(null, "Sélectionnez le type de compte:", "Choix de type", JOptionPane.QUESTION_MESSAGE, null, options_type, options_type[0]);
            String etat = "null";
            if("membre".equals(type)) {
                String[] options = {"régulier", "sénior", "enfant"};
                etat = (String) JOptionPane.showInputDialog(null, "Sélectionnez la catégorie de compte :", "Choix de catégorie", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            } else {
                etat = "null";
            }
            String motDePasse = JOptionPane.showInputDialog("Entrez votre mot de passe");
            Client client = new Client(0, nom, email, type, motDePasse, etat);
            try {
                clientDAO.ajouterClient(client);
                JOptionPane.showMessageDialog(null, "Client ajouté avec succès.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage());
            }
        } else if (e.getSource() == btnTrouverClient) {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID du client : "));
            Client client = null;
            try {
                client = clientDAO.trouverClientParId(id);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            JOptionPane.showMessageDialog(null, client != null ? client.toString() : "Client non trouvé.");
        } else if (e.getSource() == btnListerClients) {
            List<Client> clients = null;
            try {
                clients = clientDAO.listerTousLesClients();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            if (clients.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Aucun client disponible.");
            } else {
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
        } else if (e.getSource() == btnMettreAJourClient) {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID du client à mettre à jour : "));
            Client client = null;
            try {
                client = clientDAO.trouverClientParId(id);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            if (client != null) {
                String nouveauNom = JOptionPane.showInputDialog("Entrez le nouveau nom du client : ");
                String nouvelEmail = JOptionPane.showInputDialog("Entrez le nouvel email du client : ");
                String nouveauType = JOptionPane.showInputDialog("Entrez le nouveau type du client : ");
                String nouveauMotDePasse = JOptionPane.showInputDialog("Entrez le nouveau Mot de passe du client : ");
                client.setNom(nouveauNom);
                client.setEmail(nouvelEmail);
                client.setType(nouveauType);
                client.setMotDePasse(nouveauMotDePasse);
                try {
                    clientDAO.mettreAJourClient(client);
                    JOptionPane.showMessageDialog(null, "Client mis à jour avec succès.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Client non trouvé.");
            }
        } else if (e.getSource() == btnSupprimerClient) {
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
