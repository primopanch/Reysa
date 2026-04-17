package org.DevMex.Reysa;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.DevMex.Reysa.ui.components.modularization;
import org.DevMex.Reysa.ui.themes.appTheme;
import org.DevMex.Reysa.ui.views.MainFrame;

/**
 *
 * @author francisco
 */
public class ReysaApp extends JPanel{

    public static void main(String[] args) {

        //creaciones para el llamado
        modularization leftPanel = new modularization();
        MainFrame mFrame = new MainFrame();
        modularization reysaLogo = new modularization();
        //agregamos en West como contenedor de jbackground
        //leftPanel.add(reysaLogo, BorderLayout.NORTH); primer bug
        mFrame.add(leftPanel, BorderLayout.WEST);

        leftPanel.setBackground(appTheme.baseBlack);
        //llamadas 
        leftPanel.setVisible(true);
        mFrame.setVisible(true);
        reysaLogo.setVisible(true);
        
    }
}