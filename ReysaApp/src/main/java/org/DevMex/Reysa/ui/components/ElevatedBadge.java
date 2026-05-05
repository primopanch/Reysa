package org.DevMex.Reysa.ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.DevMex.Reysa.ui.themes.AppFonts;

public class ElevatedBadge extends JPanel {
    private final String text;
    private final ImageIcon icon;
    private final Color bgColor;
    private final Color fgColor;
    private final boolean isPill;

    public ElevatedBadge(String text, ImageIcon icon, Color bgColor, Color fgColor, boolean isPill) {
        this.text = text;
        this.icon = icon;
        this.bgColor = bgColor;
        this.fgColor = fgColor;
        this.isPill = isPill;
        setOpaque(false);
        setPreferredSize(new Dimension(isPill ? 120 : 300, isPill ? 35 : 45));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int radius = isPill ? getHeight() - 5 : 15;
        int w = getWidth() - 4;
        int h = getHeight() - 4;

        // Badge shadow
        g2.setColor(new Color(0, 0, 0, 30));
        g2.fillRoundRect(2, 4, w, h, radius, radius);

        // Badge background
        g2.setColor(bgColor);
        g2.fillRoundRect(1, 1, w, h, radius, radius);

        // Border (if necessary)
        g2.setColor(bgColor.darker());
        g2.drawRoundRect(1, 1, w, h, radius, radius);

        // Draw Icon + Text
        g2.setColor(fgColor);
        g2.setFont(AppFonts.getRajdhaniBold(14f));
        FontMetrics fm = g2.getFontMetrics();

        int textX = 15;
        if (icon != null) {
            icon.paintIcon(this, g2, 10, (h - icon.getIconHeight()) / 2);
            textX = 10 + icon.getIconWidth() + 10;
        }
        g2.drawString(text, textX, (h - fm.getHeight()) / 2 + fm.getAscent() + 2);

        g2.dispose();
    }
}