package org.DevMex.Reysa.ui.components;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

import org.DevMex.Reysa.ui.themes.AppFonts; // NUEVO: Import explícito de AppFonts
import org.DevMex.Reysa.ui.themes.AppTheme;

public class MenuButton extends JButton {

    private final ImageIcon icon;
    private final String text;
    private boolean active = false;
    private int cornerRadius = 15;

    public MenuButton(String text, ImageIcon icon) {
        this.text = text;
        this.icon = icon;
        this.setText("");
        this.setFocusPainted(false);
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setOpaque(false);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // NUEVO: Fuente Rajdhani para botones de menú, distintiva como en referencia
        this.setFont(AppFonts.getRajdhani(22f)); 

        this.setPreferredSize(new Dimension(220, 60));
        this.setMaximumSize(new Dimension(220, 60));
    }

    public void setActive(boolean active) {
        this.active = active;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        if (active) {
            g2.setColor(AppTheme.reysaRed);
            g2.fillRoundRect(0, 0, width, height, height, height);
            g2.fillRect(0, 0, height / 2, height); // Lado izquierdo cuadrado
        } else {
            g2.setColor(new Color(255, 255, 255, 0)); // Inactivo sin fondo, como en la imagen
            g2.fillRoundRect(0, 0, width, height, height, height);
        }

        int iconX = 20;
        int iconY = 0;
        int iconWidth = 0;

        if (icon != null) {
            iconY = (height - icon.getIconHeight()) / 2;
            icon.paintIcon(this, g2, iconX, iconY);
            iconWidth = icon.getIconWidth();
        }

        g2.setFont(getFont());
        g2.setColor(active ? Color.WHITE : new Color(200, 200, 200)); // Texto blanco para activo, gris para inactivo
        FontMetrics fm = g2.getFontMetrics();
        int textX = iconX + iconWidth + 15;
        int textY = (height - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(text, textX, textY);

        g2.dispose();
    }
}