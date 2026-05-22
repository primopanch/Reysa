package org.DevMex.Reysa.ui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class RoundedButton extends JButton {
    private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;
    private int cornerRadius = -1; // -1 significa 100% redondeado (píldora)

    public RoundedButton(String text) {
        super(text);
        init();
    }

    public RoundedButton(String text, Icon icon) {
        super(text, icon);
        init();
    }

    private void init() {
        super.setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(new EmptyBorder(8, 20, 8, 20)); // Padding interno
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { repaint(); }
            @Override
            public void mouseExited(MouseEvent e) { repaint(); }
            @Override
            public void mousePressed(MouseEvent e) { repaint(); }
            @Override
            public void mouseReleased(MouseEvent e) { repaint(); }
        });
    }

    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
    }

    public void setHoverBackgroundColor(Color color) {
        this.hoverBackgroundColor = color;
    }

    public void setPressedBackgroundColor(Color color) {
        this.pressedBackgroundColor = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isPressed()) {
            g2.setColor(pressedBackgroundColor != null ? pressedBackgroundColor : getBackground().darker());
        } else if (getModel().isRollover()) {
            g2.setColor(hoverBackgroundColor != null ? hoverBackgroundColor : getBackground().brighter());
        } else {
            g2.setColor(getBackground());
        }

        // Si es -1, calcular el radio basado en la altura para que sea perfectamente curvo en los extremos
        int radius = (cornerRadius == -1) ? getHeight() : cornerRadius; 
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        
        g2.dispose();
        
        // Pinta el texto e icono llamando a super
        super.paintComponent(g); 
    }
}
