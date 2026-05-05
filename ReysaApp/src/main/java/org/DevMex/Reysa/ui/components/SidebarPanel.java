package org.DevMex.Reysa.ui.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import org.DevMex.Reysa.ui.themes.AppFonts;
import org.DevMex.Reysa.ui.themes.AppTheme;

public class SidebarPanel extends JPanel {

    public interface NavigationListener {
        void navigateTo(String cardName);
    }

    private final ArrayList<MenuButton> menuButtons = new ArrayList<>();
    private MenuButton btnTablero;
    private MenuButton btnVehiculos;
    private MenuButton btnClientes;
    private MenuButton btnReservas;

    public SidebarPanel(NavigationListener listener) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(260, 0));
        setBackground(AppTheme.baseBlack);

        add(createLogoSection(), BorderLayout.NORTH);
        add(createNavSection(listener), BorderLayout.CENTER);
        add(createAdminSection(), BorderLayout.SOUTH);
    }

    private JPanel createLogoSection() {
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

        return topLogoPanel;
    }

    private JPanel createNavSection(NavigationListener listener) {
        JPanel navPanel = new JPanel();
        navPanel.setOpaque(false);
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.add(Box.createVerticalStrut(30));

        btnTablero = createNavButton("Tablero", Icons.getHomeIcon(), "Tablero", listener);
        btnVehiculos = createNavButton("Vehiculos", Icons.getVehiclesIcon(), "Vehiculos", listener);
        btnClientes = createNavButton("Clientes", Icons.getClientsIcon(), "Clientes", listener);
        btnReservas = createNavButton("Reservas", Icons.getCalendarIcon(), "Reservas", listener);

        btnTablero.setActive(true);

        navPanel.add(wrapInMargin(btnTablero));
        navPanel.add(wrapInMargin(btnVehiculos));
        navPanel.add(wrapInMargin(btnClientes));
        navPanel.add(wrapInMargin(btnReservas));

        return navPanel;
    }

    private JPanel createAdminSection() {
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
        adminLabel.setFont(AppFonts.getRajdhani(14f));
        adminLabel.setForeground(Color.WHITE);

        adminPanel.add(adminIconLabel);
        adminPanel.add(adminLabel);
        bottomContainer.add(adminPanel);

        return bottomContainer;
    }

    private MenuButton createNavButton(String text, ImageIcon icon, String cardName, NavigationListener listener) {
        MenuButton btn = new MenuButton(text, icon);
        menuButtons.add(btn);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setActiveButton(cardName);
                listener.navigateTo(cardName);
            }
        });
        return btn;
    }

    public void setActiveButton(String cardName) {
        for (MenuButton button : menuButtons) {
            boolean active = false;
            if (button == btnTablero && "Tablero".equals(cardName)) active = true;
            if (button == btnVehiculos && "Vehiculos".equals(cardName)) active = true;
            if (button == btnClientes && "Clientes".equals(cardName)) active = true;
            if (button == btnReservas && "Reservas".equals(cardName)) active = true;
            button.setActive(active);
        }
    }

    private JPanel wrapInMargin(JComponent comp) {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.setOpaque(false);
        wrapper.add(comp);
        return wrapper;
    }
}
