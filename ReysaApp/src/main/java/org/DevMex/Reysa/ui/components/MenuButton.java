package org.DevMex.Reysa.ui.components;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import org.DevMex.Reysa.ui.themes.appTheme;

public class MenuButton extends JButton {
    private boolean isActive = false;

    public MenuButton(String text, ImageIcon icon) {
        super(text, icon);
        setFocusPainted(false);
        setContentAreaFilled(false); // Apagamos el renderizado default de Swing
        setBorderPainted(false);
        setOpaque(false);
        setForeground(appTheme.textWhite);
// Reemplaza la antigua línea setFont por esta:
        setFont(org.DevMex.Reysa.ui.themes.AppFonts.getRajdhani(20f));        setIconTextGap(15);
        setHorizontalAlignment(SwingConstants.LEFT);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(220, 50));
        setMaximumSize(new Dimension(220, 50)); // Importante para BoxLayout
    }

    public void setActive(boolean active) {
        this.isActive = active;
        repaint(); // Fuerza a redibujar el botón con el nuevo estado
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (isActive) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(appTheme.reysaRed);
            // Dibuja el fondo rojo con bordes redondeados (radio de 20px)
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            g2.dispose();
        }
        super.paintComponent(g); // Dibuja el texto y el ícono encima
    }
}