package org.DevMex.Reysa.ui.components;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import org.DevMex.Reysa.ui.themes.AppTheme;
import org.DevMex.Reysa.ui.themes.AppFonts;

public class TopBarPanel extends JPanel {

    private final JTextField searchField;
    private final JButton btnNuevo;

    public TopBarPanel(ActionListener newClientAction) {
        setLayout(new BorderLayout(20, 0));
        setBackground(AppTheme.bgMetalicGrey);
        setBorder(new BottomShadowBorder(5, 0.15f));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 20));
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Tablero");
        titleLabel.setFont(AppFonts.getRajdhani(24f));
        titleLabel.setForeground(new Color(150, 150, 150));
        titlePanel.add(titleLabel);

        JPanel searchWrapper = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(235, 235, 235));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
                g2.dispose();
            }
        };
        searchWrapper.setOpaque(false);
        searchWrapper.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        searchField = new JTextField(" Buscar orden o cliente...", 20);
        searchField.setOpaque(false);
        searchField.setBorder(null);
        searchField.setFont(AppFonts.getRajdhani(16f));
        searchField.setForeground(Color.GRAY);
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals(" Buscar orden o cliente...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText(" Buscar orden o cliente...");
                }
            }
        });
        searchWrapper.add(searchField, BorderLayout.CENTER);

        btnNuevo = new RoundedButton("+ nuevo cliente");
        btnNuevo.setBackground(AppTheme.reysaRed);
        btnNuevo.setForeground(Color.WHITE);
        btnNuevo.setFont(AppFonts.getRajdhani(16f));
        btnNuevo.addActionListener(newClientAction);

        JPanel rightControls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        rightControls.setOpaque(false);
        rightControls.add(searchWrapper);
        rightControls.add(btnNuevo);

        add(titlePanel, BorderLayout.WEST);
        add(rightControls, BorderLayout.EAST);
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getNewClientButton() {
        return btnNuevo;
    }
}
