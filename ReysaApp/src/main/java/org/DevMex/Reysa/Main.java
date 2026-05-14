package org.DevMex.Reysa;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.DevMex.Reysa.ui.components.Modularization;

public class Main extends JPanel{

    public static void main(String[] args) {
        JFrame window = new JFrame("Reysa");
        window.setIconImage(org.DevMex.Reysa.ui.components.Icons.getAppIconImage());

        Modularization ui = new Modularization();

        window.add(ui);
        
        window.setMinimumSize(new java.awt.Dimension(800, 600));
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }

    
}