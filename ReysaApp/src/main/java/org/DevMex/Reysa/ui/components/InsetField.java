package org.DevMex.Reysa.ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import org.DevMex.Reysa.ui.themes.AppFonts;

public class InsetField extends JPanel {
    private final String text;

    public InsetField(String text) {
        this.text = text;
        setOpaque(false);
        setPreferredSize(new Dimension(150, 30)); // Standard size
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth() - 2;
        int h = getHeight() - 2;

       
        g2.setColor(new Color(215, 215, 215));
        g2.fillRoundRect(1, 1, w, h, 15, 15);

        
        g2.setColor(new Color(180, 180, 180));
        g2.drawRoundRect(1, 1, w, h, 15, 15);

       
        g2.setColor(new Color(80, 80, 80));
        g2.setFont(AppFonts.getRajdhaniBold(13f));
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(text, 10, (h - fm.getHeight()) / 2 + fm.getAscent());

        g2.dispose();
    }
}