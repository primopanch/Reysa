package org.DevMex.Reysa.ui.components;

import org.DevMex.Reysa.ui.themes.AppTheme; 
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

public class VehicleCard extends JPanel {

    private final int cornerRadius = 20;
    private final int shadowSize = 6;
    private final int shadowOpacity = 40; // 0 a 255

    public VehicleCard(ImageIcon imageIcon, Color imgBorderColor, String idText, 
                       String marca, String modelo, String color, String placas, 
                       String vin, String statusText, Color statusColor, ImageIcon statusIcon) {
        
        setOpaque(false); 
        setLayout(new BorderLayout(20, 0)); // Aumentamos la separación central a 20
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15 + shadowSize, 15 + shadowSize));

        BufferedImage image = iconToBufferedImage(imageIcon);

        // 1. SECCIÓN IZQUIERDA: Imagen del vehículo (CORREGIDA)
        // Al usar GridBagLayout aquí, evitamos que la imagen se estire verticalmente si la tarjeta crece
        JPanel leftPanel = new JPanel(new GridBagLayout()); 
        leftPanel.setOpaque(false);
        
        final RoundedImagePanel imgPanel = new RoundedImagePanel(image, imgBorderColor, 4, 25);
        imgPanel.setMinimumSize(new Dimension(140, 110));
        leftPanel.add(imgPanel);

        // 2. SECCIÓN DERECHA: Detalles del vehículo (CORREGIDA)
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0.5;

        // --- Fila 0: ID Interno ---
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0; // Permite que esta fila ocupe todo el ancho
        gbc.insets = new Insets(0, 10, 10, 10);
        ElevatedBadge idBadge = new ElevatedBadge("  # " + idText, null, new Color(225, 225, 225), Color.BLACK, false);
        rightPanel.add(idBadge, gbc);

        // --- Fila 1: Títulos Marca y Modelo ---
        gbc.gridwidth = 1;
        gbc.weightx = 0.5; // Divide el espacio a la mitad para que queden separados
        gbc.insets = new Insets(0, 10, 2, 10);
        gbc.gridy = 1; gbc.gridx = 0;
        rightPanel.add(createLabelTitle("Marca"), gbc);
        gbc.gridx = 1;
        rightPanel.add(createLabelTitle("Modelo y Año"), gbc);

        // --- Fila 2: Valores Marca y Modelo ---
        gbc.insets = new Insets(0, 10, 10, 10);
        gbc.gridy = 2; gbc.gridx = 0;
        rightPanel.add(new InsetField(marca), gbc);
        gbc.gridx = 1;
        rightPanel.add(new InsetField(modelo), gbc);

        // --- Fila 3: Títulos Color y Placas ---
        gbc.insets = new Insets(0, 10, 2, 10);
        gbc.gridy = 3; gbc.gridx = 0;
        rightPanel.add(createLabelTitle("Color"), gbc);
        gbc.gridx = 1;
        rightPanel.add(createLabelTitle("Placas"), gbc);

        // --- Fila 4: Valores Color y Placas ---
        gbc.insets = new Insets(0, 10, 10, 10);
        gbc.gridy = 4; gbc.gridx = 0;
        rightPanel.add(new InsetField(color), gbc);
        gbc.gridx = 1;
        rightPanel.add(new InsetField(placas), gbc);

        // --- Fila 5: VIN y Estado ---
        gbc.gridy = 5; gbc.gridx = 0;
        gbc.weighty = 1.0; // Este peso empuja esta fila hacia abajo y las demás hacia arriba
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        JPanel vinPanel = new JPanel(new BorderLayout(5, 5));
        vinPanel.setOpaque(false);
        vinPanel.add(createLabelTitle("# No. de serie (VIN)"), BorderLayout.NORTH);
        vinPanel.add(new InsetField(vin), BorderLayout.CENTER);
        rightPanel.add(vinPanel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        ElevatedBadge statusBadge = new ElevatedBadge(statusText, statusIcon, statusColor, Color.WHITE, true);
        rightPanel.add(statusBadge, gbc);

        // 3. ENSAMBLAJE
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        setMaximumSize(new Dimension(Integer.MAX_VALUE, 280));
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int panelWidth = getWidth();
                int panelHeight = getHeight();
                if (panelWidth <= 0 || panelHeight <= 0) {
                    return;
                }
                int imageWidth = Math.max(140, Math.min(260, panelWidth / 3));
                int imageHeight = Math.max(110, Math.min(220, panelHeight - 40));
                imgPanel.setPreferredSize(new Dimension(imageWidth, imageHeight));
                revalidate();
            }
        });
    }

    private BufferedImage iconToBufferedImage(ImageIcon icon) {
        if (icon == null) return null;
        BufferedImage bi = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        icon.paintIcon(null, g, 0, 0);
        g.dispose();
        return bi;
    }

    private JLabel createLabelTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 12));
        label.setForeground(new Color(100, 100, 100));
        return label;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth() - shadowSize - 5;
        int height = getHeight() - shadowSize - 5;

        // 1. Sombra Paralela
        for (int i = 0; i < shadowSize; i++) {
            int alpha = (int) (shadowOpacity * (1.0f - (float)i / shadowSize));
            g2.setColor(new Color(0, 0, 0, alpha));
            g2.fillRoundRect(5 + i, 5 + i, width, height, cornerRadius, cornerRadius);
        }

        // 2. Fondo Gris Claro
        g2.setColor(new Color(230, 230, 230)); 
        g2.fillRoundRect(5, 5, width, height, cornerRadius, cornerRadius);

        // 3. Línea separadora ajustada a la nueva anchura
        g2.setColor(new Color(200, 200, 200));
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(200, 20, 200, height - 10); // Movida ligeramente a la derecha

        g2.dispose();
    }

    // =====================================================================
    // SUB-COMPONENTES MODULARES
    // =====================================================================

    private class InsetField extends JPanel {
        private final String text;

        public InsetField(String text) {
            this.text = text;
            setOpaque(false);
            setPreferredSize(new Dimension(0, 30));
            setMinimumSize(new Dimension(120, 30));
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
            g2.setFont(new Font("SansSerif", Font.BOLD, 13));
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(text, 10, (h - fm.getHeight()) / 2 + fm.getAscent());

            g2.dispose();
        }
    }

    private class ElevatedBadge extends JPanel {
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
            setPreferredSize(new Dimension(isPill ? 120 : 0, isPill ? 35 : 40));
            setMinimumSize(new Dimension(isPill ? 100 : 120, isPill ? 35 : 40));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, isPill ? 45 : 45));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int radius = isPill ? getHeight() - 5 : 15;
            int w = getWidth() - 4;
            int h = getHeight() - 4;

            g2.setColor(new Color(0, 0, 0, 30));
            g2.fillRoundRect(2, 4, w, h, radius, radius);

            g2.setColor(bgColor);
            g2.fillRoundRect(1, 1, w, h, radius, radius);

            g2.setColor(bgColor.darker());
            g2.drawRoundRect(1, 1, w, h, radius, radius);

            g2.setColor(fgColor);
            g2.setFont(new Font("SansSerif", Font.BOLD, 14));
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
}