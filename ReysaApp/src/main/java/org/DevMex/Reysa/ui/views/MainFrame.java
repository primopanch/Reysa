package org.DevMex.Reysa.ui.views;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.DevMex.Reysa.ui.themes.appTheme;


public class MainFrame extends JFrame{
    
    public MainFrame(){
        setTitle("ReysaApp");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //definir layout (por eso no queria abrir aunq aun no se que hace exactamente)
        setLayout(new BorderLayout());    
        //creacion de panel we
        JPanel jBackground = new JPanel();

        jBackground.setBackground(appTheme.bgMetalicGrey);
        //el add se agrega siempre para que se agregue como su nombre dice add agregar
        add(jBackground, BorderLayout.CENTER);
        
    
    }

}
