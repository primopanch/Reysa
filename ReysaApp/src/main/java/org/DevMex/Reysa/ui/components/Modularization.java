package org.DevMex.Reysa.ui.components;

import java.awt.*;
import javax.swing.*;
import org.DevMex.Reysa.models.Vehicle;
import org.DevMex.Reysa.models.VehicleManager;
import org.DevMex.Reysa.models.VehicleState;
import org.DevMex.Reysa.models.VehicleStateListener;
import org.DevMex.Reysa.models.DashboardStats;
import org.DevMex.Reysa.ui.themes.AppFonts;
import org.DevMex.Reysa.ui.themes.AppTheme;

public class Modularization extends JPanel implements VehicleStateListener {

    private final SidebarPanel sidebarPanel;
    private final TopBarPanel topBarPanel;
    private final JPanel contentCards;
    private final DashboardPanel dashboardPanel;
    private final VehiclesPanel vehiclesPanel;
    private final ClientsPanel clientsPanel;
    private final ReservasPanel reservasPanel;
    private final VehicleManager vehicleManager;
    private final CardLayout cardLayout;

    public Modularization() {
        setLayout(new BorderLayout());
        vehicleManager = VehicleManager.getInstance();
        vehicleManager.addListener(this);

        dashboardPanel = new DashboardPanel(vehicleManager);
        vehiclesPanel = new VehiclesPanel(vehicleManager);
        clientsPanel = new ClientsPanel();
        reservasPanel = new ReservasPanel();

        contentCards = new JPanel(new CardLayout());
        cardLayout = (CardLayout) contentCards.getLayout();
        contentCards.setBackground(AppTheme.bgMetalicGrey);
        contentCards.add(dashboardPanel, "Tablero");
        contentCards.add(vehiclesPanel, "Vehiculos");
        contentCards.add(clientsPanel, "Clientes");
        contentCards.add(reservasPanel, "Reservas");

        sidebarPanel = new SidebarPanel(cardName -> {
            cardLayout.show(contentCards, cardName);
        });
        sidebarPanel.setActiveButton("Tablero");

        topBarPanel = new TopBarPanel(e -> {
            cardLayout.show(contentCards, "Clientes");
            sidebarPanel.setActiveButton("Clientes");
        });

        topBarPanel.getSearchField().getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            private void filter() {
                String text = topBarPanel.getSearchField().getText();
                if (!text.equals(" Buscar orden o cliente...") && !text.trim().isEmpty()) {
                    cardLayout.show(contentCards, "Vehiculos");
                    sidebarPanel.setActiveButton("Vehiculos");
                }
                vehiclesPanel.setFilter(text);
            }
        });

        JPanel rightSideContainer = new JPanel(new BorderLayout());
        rightSideContainer.setBackground(AppTheme.bgMetalicGrey);
        rightSideContainer.add(topBarPanel, BorderLayout.NORTH);
        rightSideContainer.add(contentCards, BorderLayout.CENTER);

        add(sidebarPanel, BorderLayout.WEST);
        add(rightSideContainer, BorderLayout.CENTER);
    }

    @Override
    public void onVehicleStatusChanged(Vehicle vehicle, VehicleState oldState, VehicleState newState) {
        dashboardPanel.refreshStats();
        vehiclesPanel.refresh();
    }

    @Override
    public void onVehicleAdded(Vehicle vehicle) {
        dashboardPanel.refreshStats();
        vehiclesPanel.refresh();
    }

    @Override
    public void onVehicleRemoved(Vehicle vehicle) {
        dashboardPanel.refreshStats();
        vehiclesPanel.refresh();
    }
}
