package Vue;


import Modele.InterfaceDAO.*;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class PaymentVue extends JFrame{

    private JTextField cardNumberField;
    private JPasswordField cvvField;
    private JTextField expiryDateField;
    private double prixAPayer;
    private boolean paymentSuccessful;




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
    public PaymentVue(ActionListener controller, boolean admin) {


        setTitle("Fausse Page de Paiement");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2));

        JLabel prixLabel = new JLabel("Prix à payer: " + prixAPayer + " €");
        prixLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel cardNumberLabel = new JLabel("Numéro de carte:");
        cardNumberField = new JTextField();

        JLabel cvvLabel = new JLabel("CVV:");
        cvvField = new JPasswordField();

        JLabel expiryDateLabel = new JLabel("Date d'expiration (MM/YY):");
        expiryDateField = new JTextField();

        JButton validerButton = new JButton("Valider");

        add(prixLabel);
        add(new JLabel()); // Placeholder
        add(cardNumberLabel);
        add(cardNumberField);
        add(cvvLabel);
        add(cvvField);
        add(expiryDateLabel);
        add(expiryDateField);
        add(new JLabel()); // Placeholder
        add(validerButton);

        validerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validatePaymentInfo()) {
                    JOptionPane.showMessageDialog(null, "Paiement de " + prixAPayer + " € réussi!");
                    paymentSuccessful = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Informations de paiement invalides.");
                }
            }
        });

        setVisible(true); // Rendre la fenêtre visible après avoir ajouté tous les composants
    }

    private boolean validatePaymentInfo() {
        String cardNumber = cardNumberField.getText();
        String cvv = new String(cvvField.getPassword());
        String expiryDate = expiryDateField.getText();

        // Vérifier que les champs ne sont pas vides
        if (cardNumber.isEmpty() || cvv.isEmpty() || expiryDate.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.");
            return false;
        }

        // Vérifier la longueur du numéro de carte
        if (cardNumber.length() != 16) {
            JOptionPane.showMessageDialog(null, "Numéro de carte invalide. Assurez-vous qu'il comporte 16 chiffres.");
            return false;
        }

        // Vérifier la longueur du CVV
        if (cvv.length() != 3) {
            JOptionPane.showMessageDialog(null, "CVV invalide. Assurez-vous qu'il comporte 3 chiffres.");
            return false;
        }

        // Vérifier la longueur de la date d'expiration
        if (expiryDate.length() != 5) {
            JOptionPane.showMessageDialog(null, "Date d'expiration invalide. Assurez-vous qu'elle soit au format MM/YY.");
            return false;
        }

        // Ajoutez ici d'autres validations selon vos besoins

        return true;
    }


}
