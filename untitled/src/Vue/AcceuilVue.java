package Vue;

import javax.swing.*;
import java.awt.*;

public class AcceuilVue extends JFrame {
    public JButton btnGererBillets, btnGererClients, btnGererEmployes, btnGererFilms, btnGererSeances, btnOffres, btnQuitter;
    public JPanel mainPanel;

    public AcceuilVue() {
        setTitle("Page d'Accueil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        prepareGUI();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void prepareGUI() {
        mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

    }

    public JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(255, 215, 0)); // Golden color
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 100));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return button;
    }


}