package org.DevMex.Reysa;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.DevMex.Reysa.ui.components.Modularization;

/**
 *
 * @author francisco
 */
public class Main extends JPanel{

    public static void main(String[] args) {
        //declaramos ventana donde se va a visualizar todo
        JFrame window = new JFrame("Reysa");

        Modularization ui = new Modularization();
        

        
        window.add(ui);
        
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        
        
        
    }

    
}