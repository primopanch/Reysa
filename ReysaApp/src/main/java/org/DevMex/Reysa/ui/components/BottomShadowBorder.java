package org.DevMex.Reysa.ui.components;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class BottomShadowBorder extends AbstractBorder {
    private final int shadowSize;
    private final float opacity;

    public BottomShadowBorder(int shadowSize, float opacity) {
        this.shadowSize = shadowSize;
        this.opacity = opacity;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color startColor = new Color(0, 0, 0, (int)(500 * opacity));
        Color endColor = new Color(0, 0, 0, 0);

        GradientPaint shadowPaint = new GradientPaint(
                0, height - shadowSize, startColor,
                0, height, endColor
        );

        g2.setPaint(shadowPaint);
        g2.fillRect(x, height - shadowSize, width, shadowSize);
        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, shadowSize, 0);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(0, 0, shadowSize, 0);
        return insets;
    }
}