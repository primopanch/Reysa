package org.DevMex.Reysa.ui.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import org.DevMex.Reysa.ui.themes.appTheme;

public class Modularization extends JPanel {

    // Contenedores principales
    private JPanel leftPanel;
    private JPanel rightSideContainer;
    
    // Sistema de navegación (CardLayout)
    private CardLayout cardLayout;
    private JPanel contentCards;
    
    // Lista para manejar el estado visual de los botones
    private ArrayList<MenuButton> menuButtons = new ArrayList<>();

    public Modularization() {
        this.setLayout(new BorderLayout());

        // 1. Inicializar el contenedor derecho (Topbar + Contenido)
        rightSideContainer = new JPanel(new BorderLayout());
        rightSideContainer.setBackground(appTheme.bgMetalicGrey);

        // 2. Crear y añadir la barra superior
        rightSideContainer.add(createTopBar(), BorderLayout.NORTH);

        // 3. Configurar el CardLayout para el contenido central
        cardLayout = new CardLayout();
        contentCards = new JPanel(cardLayout);
        contentCards.setBackground(appTheme.bgMetalicGrey);
        
        // Añadir las "pantallas" falsas por ahora
        contentCards.add(createDummyPanel("Pantalla: Tablero"), "Tablero");
        contentCards.add(createDummyPanel("Pantalla: Vehículos"), "Vehiculos");
        contentCards.add(createDummyPanel("Pantalla: Clientes"), "Clientes");
        contentCards.add(createDummyPanel("Pantalla: Reservas"), "Reservas");
        
        rightSideContainer.add(contentCards, BorderLayout.CENTER);

        // 4. Crear el Sidebar
        leftPanel = createSidebar();

        // 5. Ensamblar todo
        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightSideContainer, BorderLayout.CENTER);
    }

    // --- MÉTODOS DE CONSTRUCCIÓN DE UI ---
private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(260, 0));
        sidebar.setBackground(appTheme.baseBlack);
        sidebar.setLayout(new BorderLayout());

        // --- Parte Superior: Logo ---
        JPanel topLogoPanel = new JPanel();
        topLogoPanel.setOpaque(false);
        topLogoPanel.setLayout(new BoxLayout(topLogoPanel, BoxLayout.Y_AXIS));
        topLogoPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0)); // Margen superior
        
        // 1. Añadimos el Logo Real
        JLabel logoLabel = new JLabel(icons.getLogo()); 
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topLogoPanel.add(logoLabel);
        
        topLogoPanel.add(Box.createVerticalStrut(20)); // Espacio antes de la línea
        
        // 2. Línea separadora sutil
        JSeparator topSeparator = new JSeparator();
        topSeparator.setMaximumSize(new Dimension(220, 1));
        topSeparator.setForeground(new Color(100, 100, 100)); // Gris oscuro
        topSeparator.setAlignmentX(Component.CENTER_ALIGNMENT);
        topLogoPanel.add(topSeparator);

        // --- Parte Central: Botones de Navegación ---
        JPanel navPanel = new JPanel();
        navPanel.setOpaque(false);
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.add(Box.createVerticalStrut(30)); // Espacio inicial

        // 3. Crear botones con los Íconos Reales
        MenuButton btnTablero = createNavButton("Tablero", icons.getHomeIcon(), "Tablero");
        MenuButton btnVehiculos = createNavButton("Vehiculos", icons.getVehiclesIcon(), "Vehiculos");
        MenuButton btnClientes = createNavButton("Clientes", icons.getClientsIcon(), "Clientes");
        MenuButton btnReservas = createNavButton("Reservas", icons.getCalendarIcon(), "Reservas");

        btnTablero.setActive(true); // Estado inicial

        navPanel.add(wrapInMargin(btnTablero));
        navPanel.add(wrapInMargin(btnVehiculos));
        navPanel.add(wrapInMargin(btnClientes));
        navPanel.add(wrapInMargin(btnReservas));

        // --- Parte Inferior: Admin ---
        JPanel bottomContainer = new JPanel();
        bottomContainer.setOpaque(false);
        bottomContainer.setLayout(new BoxLayout(bottomContainer, BoxLayout.Y_AXIS));
        
        // Línea separadora inferior
        JSeparator bottomSeparator = new JSeparator();
        bottomSeparator.setMaximumSize(new Dimension(220, 1));
        bottomSeparator.setForeground(new Color(100, 100, 100));
        bottomSeparator.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomContainer.add(bottomSeparator);
        
        // Panel del usuario Admin
        JPanel adminPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        adminPanel.setOpaque(false);
        adminPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel adminIconLabel = new JLabel(icons.getProfileIcon());
        JLabel adminLabel = new JLabel("Admin");
        adminLabel.setForeground(Color.WHITE);
        // 4. Aplicamos fuente Rajdhani al texto del Admin
        adminLabel.setFont(org.DevMex.Reysa.ui.themes.AppFonts.getRajdhani(20f)); 
        
        adminPanel.add(adminIconLabel);
        adminPanel.add(adminLabel);
        
        bottomContainer.add(adminPanel);

        // Ensamblar Sidebar
        sidebar.add(topLogoPanel, BorderLayout.NORTH);
        sidebar.add(navPanel, BorderLayout.CENTER);
        sidebar.add(bottomContainer, BorderLayout.SOUTH);

        return sidebar;
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(appTheme.bgMetalicGrey);
        topBar.setPreferredSize(new Dimension(0, 70));
        topBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));

        // 5. Título con fuente Orbitron
        JLabel titleLabel = new JLabel("   Vehículos");
        titleLabel.setFont(org.DevMex.Reysa.ui.themes.AppFonts.getOrbitron(18f)); 
        titleLabel.setForeground(new Color(100, 100, 100)); // Gris

        JPanel rightControls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        rightControls.setOpaque(false);
        
        // El campo de búsqueda también lleva la tipografía base
        JTextField searchField = new JTextField(" Buscar orden o cliente...", 20);
        searchField.setFont(org.DevMex.Reysa.ui.themes.AppFonts.getRajdhani(16f));
        
        JButton btnNuevo = new JButton("+ nuevo cliente");
        btnNuevo.setBackground(appTheme.reysaRed);
        btnNuevo.setForeground(Color.WHITE);
        btnNuevo.setFont(org.DevMex.Reysa.ui.themes.AppFonts.getRajdhani(16f));
        btnNuevo.setFocusPainted(false);
        
        rightControls.add(searchField);
        rightControls.add(btnNuevo);

        topBar.add(titleLabel, BorderLayout.WEST);
        topBar.add(rightControls, BorderLayout.EAST);

        return topBar;
    }

    // --- LÓGICA DE NAVEGACIÓN ---

    private MenuButton createNavButton(String text, ImageIcon icon, String cardName) {
        MenuButton btn = new MenuButton(text, icon);
        menuButtons.add(btn); // Guardamos referencia para cambiar colores después

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Apagar todos los botones
                for (MenuButton mb : menuButtons) {
                    mb.setActive(false);
                }
                // 2. Encender solo el que fue presionado
                btn.setActive(true);
                
                // 3. Cambiar la vista en el panel central
                cardLayout.show(contentCards, cardName);
            }
        });
        return btn;
    }

    // Método de utilidad para dar margen al botón en el BoxLayout
    private JPanel wrapInMargin(JComponent comp) {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.setOpaque(false);
        wrapper.add(comp);
        return wrapper;
    }

    // Panel temporal para visualizar los cambios
    private JPanel createDummyPanel(String text) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(label);
        return panel;
    }
}