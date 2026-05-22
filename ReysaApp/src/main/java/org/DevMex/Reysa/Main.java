package org.DevMex.Reysa;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.DevMex.Reysa.ui.components.Modularization;
import org.DevMex.Reysa.ui.components.LoginPanel;
import org.DevMex.Reysa.ui.components.Icons;

public class Main {
    private static JFrame window;
    private static CardLayout cardLayout;
    private static JPanel mainContainer;

    public static void main(String[] args) {
        window = new JFrame("Reysa");
        window.setIconImage(Icons.getAppIconImage());

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        // Añadir las pantallas
        LoginPanel loginPanel = new LoginPanel();
        Modularization ui = new Modularization();

        mainContainer.add(loginPanel, "LOGIN");
        mainContainer.add(ui, "APP");

        window.add(mainContainer);
        
        window.setMinimumSize(new java.awt.Dimension(800, 600));
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Mostrar Login al inicio
        cardLayout.show(mainContainer, "LOGIN");
    }

    public static void loginSuccess() {
        cardLayout.show(mainContainer, "APP");
    }
}