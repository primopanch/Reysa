package org.DevMex.Reysa.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class RoundedImagePanel extends JPanel {
    private BufferedImage image;
    private Color borderColor;
    private int borderWidth;
    private int cornerRadius;

    public RoundedImagePanel(BufferedImage image, Color borderColor, int borderWidth, int cornerRadius) {
        this.image = image;
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
        this.cornerRadius = cornerRadius;
        setOpaque(false); // Fondo transparente para evitar artefactos visuales en las esquinas
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null) return;

        Graphics2D g2 = (Graphics2D) g.create();

        // 1. Activar el anti-aliasing para lograr bordes suaves y nítidos
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        int w = getWidth();
        int h = getHeight();

        // 2. Definir el área del recorte (la forma de la esquina redondeada)
        int offset = borderWidth;
        RoundRectangle2D shape = new RoundRectangle2D.Float(
                offset, 
                offset, 
                w - (offset * 2), 
                h - (offset * 2), 
                cornerRadius, 
                cornerRadius
        );

        // 3. Recortar (clip) el gráfico para que la imagen tome la forma geométrica redondeada
        g2.setClip(shape);
        
        // Dibujar la imagen manteniendo la proporción para evitar distorsiones
        int availableW = w - offset * 2;
        int availableH = h - offset * 2;
        double imageRatio = (double) image.getWidth() / image.getHeight();
        double panelRatio = (double) availableW / availableH;
        int drawW, drawH;
        if (imageRatio > panelRatio) {
            drawW = availableW;
            drawH = (int) (availableW / imageRatio);
        } else {
            drawH = availableH;
            drawW = (int) (availableH * imageRatio);
        }
        int x = offset + (availableW - drawW) / 2;
        int y = offset + (availableH - drawH) / 2;
        g2.drawImage(image, x, y, drawW, drawH, this);

        // 4. Dibujar el borde alrededor de la figura recortada
        if (borderColor != null && borderWidth > 0) {
            g2.setStroke(new BasicStroke(borderWidth));
            g2.setColor(borderColor);
            g2.draw(shape);
        }

        g2.dispose();
    }

    // Métodos para actualizar la imagen o las propiedades en tiempo de ejecución
    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        repaint();
    }
}