package org.DevMex.Reysa.ui.components;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.DevMex.Reysa.ui.themes.appTheme;

public class modularization extends JPanel {

    public modularization(){

        JPanel leftPanel = new JPanel();
        //dimension del panel
        leftPanel.setPreferredSize(new Dimension(600, 0));
        //set color de fondo
        leftPanel.setBackground(appTheme.baseBlack);
        //agregacion con add
        
        //importacion del logo con tamaño reducido
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/org/DevMex/Reysa/resources/icons/ReysaLogo.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(500, 400, Image.SCALE_SMOOTH);
        JLabel reysaLogo = new JLabel(new ImageIcon(scaledImage));


       
        add(reysaLogo);
        
       
    }

    
}
