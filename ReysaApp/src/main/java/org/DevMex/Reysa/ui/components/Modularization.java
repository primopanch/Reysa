package org.DevMex.Reysa.ui.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import org.DevMex.Reysa.ui.themes.AppTheme;

public class Modularization extends JPanel {

    private JPanel leftPanel;
    private JPanel rightSideContainer;
    
    private CardLayout cardLayout;
    private JPanel contentCards;
    
    private JPanel topBar;
    private JPanel navPanel;
    private JTextField searchField;
    private JButton btnNuevo;

    private ArrayList<MenuButton> menuButtons = new ArrayList<>();

    public Modularization() {
        this.setLayout(new BorderLayout());

        rightSideContainer = new JPanel(new BorderLayout());
        rightSideContainer.setBackground(AppTheme.bgMetalicGrey);

        rightSideContainer.add(createTopBar(), BorderLayout.NORTH);

        cardLayout = new CardLayout();
        contentCards = new JPanel(cardLayout);
        contentCards.setBackground(AppTheme.bgMetalicGrey);
        
        contentCards.add(createDummyPanel("Pantalla: Tablero"), "Tablero");
        contentCards.add(createVehiculosPanel(), "Vehiculos");
        contentCards.add(createDummyPanel("Pantalla: Clientes"), "Clientes");
        contentCards.add(createDummyPanel("Pantalla: Reservas"), "Reservas");
        
        rightSideContainer.add(contentCards, BorderLayout.CENTER);

        leftPanel = createSidebar();

        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightSideContainer, BorderLayout.CENTER);

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                applyResponsiveLayout();
            }
        });

        applyResponsiveLayout();
    }

    private JPanel createVehiculosPanel() {
        JPanel vehiculosPanel = new JPanel();
        vehiculosPanel.setLayout(new BoxLayout(vehiculosPanel, BoxLayout.Y_AXIS));
        vehiculosPanel.setBackground(AppTheme.bgMetalicGrey);
        vehiculosPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        VehicleCard card1 = new VehicleCard(
            Icons.getVehicleImageLarge(),
            new Color(245, 130, 32), 
            "ORDEN-2026-017", "Honda", "Civic 2009", "Gris plata", "B-101-707", "4TX89BN3TXWXXXXXX", 
            "Reparando", new Color(245, 130, 32), null
        );

        card1.setMaximumSize(new Dimension(850, 180)); 
        card1.setAlignmentX(Component.LEFT_ALIGNMENT);

        vehiculosPanel.add(card1);
        
        vehiculosPanel.add(Box.createVerticalGlue());

        return vehiculosPanel;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(260, 0));
        sidebar.setBackground(AppTheme.baseBlack);
        sidebar.setLayout(new BorderLayout());

        JPanel topLogoPanel = new JPanel();
        topLogoPanel.setOpaque(false);
        topLogoPanel.setLayout(new BoxLayout(topLogoPanel, BoxLayout.Y_AXIS));
        topLogoPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0)); 
        
        JLabel logoLabel = new JLabel(Icons.getLogo()); 
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topLogoPanel.add(logoLabel);
        
        topLogoPanel.add(Box.createVerticalStrut(-10)); 
        
        JSeparator topSeparator = new JSeparator();
        topSeparator.setMaximumSize(new Dimension(220, 1));
        topSeparator.setForeground(new Color(100, 100, 100)); 
        topSeparator.setAlignmentX(Component.CENTER_ALIGNMENT);
        topLogoPanel.add(topSeparator);

        JPanel navPanel = new JPanel();
        navPanel.setOpaque(false);
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.add(Box.createVerticalStrut(30)); 

        MenuButton btnTablero = createNavButton("Tablero", Icons.getHomeIcon(), "Tablero");
        MenuButton btnVehiculos = createNavButton("Vehiculos", Icons.getVehiclesIcon(), "Vehiculos");
        MenuButton btnClientes = createNavButton("Clientes", Icons.getClientsIcon(), "Clientes");
        MenuButton btnReservas = createNavButton("Reservas", Icons.getCalendarIcon(), "Reservas");

        btnTablero.setActive(true); 

        navPanel.add(wrapInMargin(btnTablero));
        navPanel.add(wrapInMargin(btnVehiculos));
        navPanel.add(wrapInMargin(btnClientes));
        navPanel.add(wrapInMargin(btnReservas));

        // --- Parte Inferior: Admin ---
        JPanel bottomContainer = new JPanel();
        bottomContainer.setOpaque(false);
        bottomContainer.setLayout(new BoxLayout(bottomContainer, BoxLayout.Y_AXIS));
        
        JSeparator bottomSeparator = new JSeparator();
        bottomSeparator.setMaximumSize(new Dimension(220, 1));
        bottomSeparator.setForeground(new Color(100, 100, 100));
        bottomSeparator.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomContainer.add(bottomSeparator);
        
        JPanel adminPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        adminPanel.setOpaque(false);
        adminPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel adminIconLabel = new JLabel(Icons.getProfileIcon());
        JLabel adminLabel = new JLabel("Admin");
        adminLabel.setForeground(Color.WHITE);
        
        adminPanel.add(adminIconLabel);
        adminPanel.add(adminLabel);
        
        bottomContainer.add(adminPanel);

        sidebar.add(topLogoPanel, BorderLayout.NORTH);
        sidebar.add(navPanel, BorderLayout.CENTER);
        sidebar.add(bottomContainer, BorderLayout.SOUTH);

        return sidebar;
    }

    private JPanel createTopBar() {
        topBar = new JPanel(new BorderLayout());
        topBar.setBackground(AppTheme.bgMetalicGrey);
        topBar.setPreferredSize(new Dimension(0, 75)); 
        topBar.setBorder(new BottomShadowBorder(5, 0.15f));

        JPanel rightControls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        rightControls.setOpaque(false);
        
        searchField = new JTextField(" Buscar orden o cliente...", 20);
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
        btnNuevo.setFocusPainted(false);
        
        rightControls.add(searchField);
        rightControls.add(btnNuevo);

        topBar.add(rightControls, BorderLayout.EAST);

        return topBar;
    }

    private void applyResponsiveLayout() {
        int width = getWidth();
        int height = getHeight();
        if (width <= 0 || height <= 0) {
            return;
        }

        int sidebarWidth = Math.max(220, Math.min(320, width / 5));
        leftPanel.setPreferredSize(new Dimension(sidebarWidth, 0));

        int topHeight = Math.max(70, Math.min(110, height / 10));
        if (topBar != null) {
            topBar.setPreferredSize(new Dimension(0, topHeight));
        }

        int buttonHeight = Math.max(48, Math.min(60, topHeight - 10));
        for (MenuButton mb : menuButtons) {
            mb.setPreferredSize(new Dimension(sidebarWidth - 40, buttonHeight));
        }

        if (searchField != null) {
            searchField.setPreferredSize(new Dimension(Math.max(180, width / 4), Math.max(34, topHeight - 20)));
        }
        if (btnNuevo != null) {
            btnNuevo.setPreferredSize(new Dimension(Math.max(140, width / 8), Math.max(34, topHeight - 20)));
        }

        revalidate();
        repaint();
    }

    private MenuButton createNavButton(String text, ImageIcon icon, String cardName) {
        MenuButton btn = new MenuButton(text, icon);
        menuButtons.add(btn); 

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (MenuButton mb : menuButtons) {
                    mb.setActive(false);
                }
                btn.setActive(true);
                cardLayout.show(contentCards, cardName);
            }
        });
        return btn;
    }

    private JPanel wrapInMargin(JComponent comp) {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.setOpaque(false);
        wrapper.add(comp);
        return wrapper;
    }

    private JPanel createDummyPanel(String text) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(label);
        return panel;
    }
}