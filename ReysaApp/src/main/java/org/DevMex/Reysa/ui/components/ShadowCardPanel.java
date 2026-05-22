package org.DevMex.Reysa.ui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ShadowCardPanel extends JPanel {
    private int cornerRadius = 20;
    private int shadowSize = 8;
    private int shadowOpacity = 40; // 0-255
    private Color backgroundColor = Color.WHITE;

    public ShadowCardPanel() {
        super();
        setOpaque(false);
        updateBorder();
    }

    public ShadowCardPanel(int cornerRadius, int shadowSize, Color bg) {
        super();
        setOpaque(false);
        this.cornerRadius = cornerRadius;
        this.shadowSize = shadowSize;
        this.backgroundColor = bg;
        updateBorder();
    }

    private void updateBorder() {
        // Dejamos margen para la sombra. La sombra inferior es más pronunciada (+4)
        setBorder(BorderFactory.createEmptyBorder(
            shadowSize, shadowSize, shadowSize + 4, shadowSize));
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth() - (shadowSize * 2);
        int height = getHeight() - (shadowSize * 2) - 4; 

        // Dibujar sombra concéntrica (Gaussian blur effect simulado)
        for (int i = 0; i < shadowSize; i++) {
            int alpha = (int) (shadowOpacity * (1.0f - (float) i / shadowSize));
            g2.setColor(new Color(0, 0, 0, alpha));
            // Offset Y de +4 para caída de sombra
            g2.fillRoundRect(shadowSize - i, shadowSize - i + 4, width + (i * 2), height + (i * 2), cornerRadius + i, cornerRadius + i);
        }

        // Fondo principal de la tarjeta
        g2.setColor(backgroundColor);
        g2.fillRoundRect(shadowSize, shadowSize, width, height, cornerRadius, cornerRadius);

        g2.dispose();
    }
}
