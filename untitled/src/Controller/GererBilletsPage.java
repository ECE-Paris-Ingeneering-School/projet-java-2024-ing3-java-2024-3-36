package Controller;

import Modele.InterfaceDAO.*;
import Modele.Objets.Billet;
import Modele.Objets.Film;
import Modele.Objets.Seance;
import Vue.BilletsVue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Scanner;
import Vue.BilletsVue;
public class GererBilletsPage extends JFrame implements ActionListener {
    private final int userID;
    private ClientDAO clientDAO;
    private BilletDAO billetDAO;
    private Scanner scanner;
    private FilmDAO filmDAO;
    private EmployeDAO employeDAO;
    private SeanceDAO seanceDAO;
    private OffresDAO offresDAO;
    private JButton btnAjouterBillet;
    private JButton btnListerBillets;
    private JButton btnSupprimerBillet;
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

    public GererBilletsPage(BilletDAO billetDAO, ClientDAO clientDAO, FilmDAO filmDAO, EmployeDAO employeDAO, SeanceDAO seanceDAO, OffresDAO offresDAO, Scanner scanner, int userID) {

        this.userID = userID;
        this.clientDAO = clientDAO;
        this.filmDAO = filmDAO;
        this.employeDAO = employeDAO;
        this.seanceDAO = seanceDAO;
        this.billetDAO = billetDAO;
        this.offresDAO = offresDAO;
        this.scanner = scanner;

        boolean admin;

        try {
            admin = (clientDAO.trouverEmailParId(userID)).equals("admin@admin.fr");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        new BilletsVue(this, admin);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();  
        switch (command) {
            case "Réserver une séance":
                List<Seance> seances;
                try {
                    seances = seanceDAO.listerToutesLesSeances();
                    if (seances.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Aucune séance disponible pour ce film.");
                        return;
                    }

                    String[] headers = {"ID", "Film", "Date et heure", "Salle", "Prix"};
                    Object[][] data = new Object[seances.size()][5];
                    double basePrice = 8;
                    double reduction = 0;

                    String clientState = clientDAO.trouverEtatParId(userID);
                    switch (clientState) {
                        case "regulier":
                            reduction = offresDAO.getOffreRegulier();
                            break;
                        case "senior":
                            reduction = offresDAO.getOffreSenior();
                            break;
                        case "enfant":
                            reduction = offresDAO.getOffreEnfant();
                            break;
                    }

                    for (int i = 0; i < seances.size(); i++) {
                        Seance seance = seances.get(i);
                        String filmTitle = filmDAO.trouverTitreParId(seance.getFilmId());
                        data[i][0] = seance.getId();
                        data[i][1] = filmTitle;
                        data[i][2] = seance.getHeure();
                        data[i][3] = seance.getSalle();
                        data[i][4] = calculatePrice(seance.getSalle(), basePrice, reduction);
                    }

                    JTable table = new JTable(data, headers);
                    JScrollPane scrollPane = new JScrollPane(table);
                    table.setDefaultEditor(Object.class, null);
                    int response = JOptionPane.showConfirmDialog(null, scrollPane, "Liste des séances", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (response == JOptionPane.OK_OPTION) {
                        int selectedRow = table.getSelectedRow();
                        if (selectedRow != -1) {
                            double price = (double) data[selectedRow][4];
                            FakePaymentPage fakePaymentPage = new FakePaymentPage(price);


                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des séances: " + ex.getMessage());
                }
                break;

            case "Lister toutes mes séances":
            case "Lister tous les billets":
                List<Billet> billets;
                try {
                    billets = (clientDAO.trouverEmailParId(userID).equals("admin@admin.fr")) ?
                            billetDAO.listerTousLesBillets() : billetDAO.listerBilletsParClientId(userID);

                    if (billets.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Aucun billet disponible pour cet utilisateur.");
                        return;
                    }

                    String[] headers = {"Billet ID", "Séance ID", "Client ID", "Prix"};
                    Object[][] data = new Object[billets.size()][4];
                    for (int i = 0; i < billets.size(); i++) {
                        Billet billet = billets.get(i);
                        data[i][0] = billet.getId();
                        data[i][1] = billet.getSeanceId();
                        data[i][2] = billet.getClientId();
                        data[i][3] = billet.getPrix();
                    }

                    JTable table = new JTable(data, headers);
                    JScrollPane scrollPane = new JScrollPane(table);
                    JOptionPane.showMessageDialog(null, scrollPane, "Liste des billets", JOptionPane.PLAIN_MESSAGE);


                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des billets: " + ex.getMessage());
                }


                break;

            case "Supprimer un billet":
                int billetID = Integer.parseInt(JOptionPane.showInputDialog("Entrez l'ID du billet à supprimer :"));
                try {
                    billetDAO.supprimerBillet(billetID);
                    JOptionPane.showMessageDialog(null, "Billet supprimé avec succès.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la suppression du billet: " + ex.getMessage());
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


    private double calculatePrice(String salleType, double basePrice, double reduction) {
        double price = basePrice;


        switch (salleType) {
            case "Salle Standard":
                price = basePrice;
                break;
            case "Salle 3D":
                price = basePrice + 1;
                break;
            case "Salle THX":
                price = basePrice + 2;
                break;
            case "Salle UltraAVX":
                price = basePrice + 3;
                break;
            case "Salle Dolby Cinema":
                price = basePrice + 4;
                break;
            case "Salle IMAX":
                price = basePrice + 5;
                break;
            case "Salle D-Box":
                price = basePrice + 6;
                break;
            case "Salle 4DX":
                price = basePrice + 7;
                break;
            case "Salle VIP":
                price = basePrice + 8;
                break;
            case "Salle Gold Class":
                price = basePrice + 9;
                break;
            default:
                price = basePrice;
                break;
        }

        // Apply the discount: calculate the reduction amount and subtract it from the price
        double discountAmount = price * (reduction / 100);
        price -= discountAmount;

        return price;
    }


}