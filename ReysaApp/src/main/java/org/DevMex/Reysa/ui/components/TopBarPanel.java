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

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 16));
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Tablero");
        titleLabel.setFont(AppFonts.getOrbitron(14f));
        titleLabel.setForeground(AppTheme.textDark);
        titlePanel.add(titleLabel);

        searchField = new JTextField(" Buscar orden o cliente...", 24);
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

        btnNuevo = new JButton("+ nuevo cliente");
        btnNuevo.setBackground(AppTheme.reysaRed);
        btnNuevo.setForeground(Color.WHITE);
        btnNuevo.setFont(AppFonts.getRajdhani(16f));
        btnNuevo.setFocusPainted(false);
        btnNuevo.addActionListener(newClientAction);

        JPanel rightControls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        rightControls.setOpaque(false);
        rightControls.add(searchField);
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
