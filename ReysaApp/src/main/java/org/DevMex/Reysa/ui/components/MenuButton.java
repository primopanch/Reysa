package org.DevMex.Reysa.ui.components;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import org.DevMex.Reysa.ui.themes.AppTheme;

public class MenuButton extends JButton {
    private boolean isActive = false;

    public MenuButton(String text, ImageIcon icon) {
        super(text, icon);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
        setForeground(AppTheme.textWhite);

        setFont(org.DevMex.Reysa.ui.themes.AppFonts.getRajdhani(20f));
        setIconTextGap(15);
        setHorizontalAlignment(SwingConstants.LEFT);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setMinimumSize(new Dimension(180, 50));
        setPreferredSize(new Dimension(220, 50));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 60)); // Flexible width for responsive layouts
    }

    public void setActive(boolean active) {
        this.isActive = active;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (isActive) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(AppTheme.reysaRed);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            g2.dispose();
        }
        super.paintComponent(g);
    }
}