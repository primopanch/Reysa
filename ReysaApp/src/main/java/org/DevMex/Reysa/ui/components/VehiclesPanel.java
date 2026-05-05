package org.DevMex.Reysa.ui.components;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import org.DevMex.Reysa.models.Vehicle;
import org.DevMex.Reysa.models.VehicleManager;
import org.DevMex.Reysa.models.VehicleStateListener;
import org.DevMex.Reysa.ui.themes.AppFonts;
import org.DevMex.Reysa.ui.themes.AppTheme;

public class VehiclesPanel extends JPanel implements VehicleStateListener {

    private final VehicleManager vehicleManager;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentCards = new JPanel(cardLayout);
    private final JPanel vehicleListPanel = new JPanel();
    private JPanel detailCard;
    private String currentFilter = "";

    public VehiclesPanel(VehicleManager vehicleManager) {
        this.vehicleManager = vehicleManager;
        this.vehicleManager.addListener(this);

        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(AppTheme.bgMetalicGrey);

        contentCards.add(createListPanel(), "List");
        contentCards.add(new JPanel(), "Detail");
        add(contentCards, BorderLayout.CENTER);
        showList();
    }

    public void refresh() {
        rebuildVehicleList();
    }

    public void setFilter(String filter) {
        this.currentFilter = filter == null ? "" : filter.toLowerCase().trim();
        if (this.currentFilter.equals("buscar orden o cliente...")) {
            this.currentFilter = "";
        }
        rebuildVehicleList();
    }

    private JPanel createListPanel() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setOpaque(false);
        section.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JPanel titleBlock = new JPanel();
        titleBlock.setOpaque(false);
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Vehículos");
        title.setFont(AppFonts.getOrbitron(34f));
        title.setForeground(AppTheme.textDark);

        JLabel subtitle = new JLabel("Monitorea los vehículos en el taller y su progreso.");
        subtitle.setFont(AppFonts.getRajdhani(14f));
        subtitle.setForeground(new Color(110, 110, 110));

        titleBlock.add(title);
        titleBlock.add(Box.createVerticalStrut(8));
        titleBlock.add(subtitle);
        header.add(titleBlock, BorderLayout.WEST);
        section.add(header);
        section.add(Box.createVerticalStrut(20));

        JPanel listWrapper = new JPanel(new BorderLayout());
        listWrapper.setOpaque(true);
        listWrapper.setBackground(Color.WHITE);
        listWrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));

        vehicleListPanel.setLayout(new BoxLayout(vehicleListPanel, BoxLayout.Y_AXIS));
        vehicleListPanel.setOpaque(false);
        rebuildVehicleList();

        JScrollPane scrollPane = new JScrollPane(vehicleListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        listWrapper.add(scrollPane, BorderLayout.CENTER);

        section.add(listWrapper);
        return section;
    }

    private void rebuildVehicleList() {
        vehicleListPanel.removeAll();
        List<Vehicle> vehicles = vehicleManager.getAllVehicles();
        
        if (currentFilter != null && !currentFilter.isEmpty()) {
            vehicles = vehicles.stream()
                .filter(v -> matchesFilter(v, currentFilter))
                .sorted((v1, v2) -> {
                    int score1 = getMatchScore(v1, currentFilter);
                    int score2 = getMatchScore(v2, currentFilter);
                    return Integer.compare(score2, score1); // Descending order
                })
                .collect(java.util.stream.Collectors.toList());
        }

        for (Vehicle vehicle : vehicles) {
            addVehicleCard(vehicle);
        }
        vehicleListPanel.add(Box.createVerticalGlue());
        vehicleListPanel.revalidate();
        vehicleListPanel.repaint();
    }

    private boolean matchesFilter(Vehicle v, String filter) {
        return getMatchScore(v, filter) > 0;
    }

    private int getMatchScore(Vehicle v, String filter) {
        int score = 0;
        String id = v.getId() != null ? v.getId().toLowerCase() : "";
        String marca = v.getMarca() != null ? v.getMarca().toLowerCase() : "";
        String modelo = v.getModelo() != null ? v.getModelo().toLowerCase() : "";
        String placas = v.getPlacas() != null ? v.getPlacas().toLowerCase() : "";
        String vin = v.getVin() != null ? v.getVin().toLowerCase() : "";
        String color = v.getColor() != null ? v.getColor().toLowerCase() : "";

        if (id.equals(filter) || placas.equals(filter) || vin.equals(filter)) score += 100;
        if (id.contains(filter)) score += 50;
        if (placas.contains(filter)) score += 40;
        if (vin.contains(filter)) score += 40;
        if (marca.startsWith(filter) || modelo.startsWith(filter)) score += 30;
        if (marca.contains(filter) || modelo.contains(filter)) score += 20;
        if (color.contains(filter)) score += 10;
        
        return score;
    }

    private void addVehicleCard(Vehicle vehicle) {
        ImageIcon vehicleIcon = vehicle.getImagePath() != null && !vehicle.getImagePath().isBlank()
                ? Icons.getVehicleImageLarge(vehicle.getImagePath())
                : Icons.getVehicleImageLarge();
        VehicleCard card = new VehicleCard(
                vehicleIcon,
                vehicle.getStatusColor(),
                vehicle.getId(),
                vehicle.getMarca(),
                vehicle.getModelo(),
                vehicle.getColor(),
                vehicle.getPlacas(),
                vehicle.getVin(),
                vehicle.getStatus(),
                vehicle.getStatusColor(),
                null);

        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));
        card.setPreferredSize(new Dimension(0, 220));
        card.setMinimumSize(new Dimension(0, 220));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setVehicleCardListener(new VehicleCard.VehicleCardListener() {
            @Override
            public void vehicleCardClicked(VehicleCard selectedCard) {
                showVehicleDetail(vehicle);
            }
        });

        vehicleListPanel.add(card);
        vehicleListPanel.add(Box.createVerticalStrut(12));
    }

    private void showVehicleDetail(Vehicle vehicle) {
        if (detailCard != null) {
            contentCards.remove(detailCard);
        }
        detailCard = createVehicleDetailPanel(vehicle);
        contentCards.add(detailCard, "Detail");
        cardLayout.show(contentCards, "Detail");
    }

    private JPanel createVehicleDetailPanel(Vehicle vehicle) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(true);
        panel.setBackground(AppTheme.bgMetalicGrey);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel titleLabel = new JLabel(vehicle.getMarca() + " " + vehicle.getModelo());
        titleLabel.setFont(AppFonts.getRajdhani(28f));
        titleLabel.setForeground(AppTheme.textDark);
        header.add(titleLabel, BorderLayout.NORTH);

        JLabel subtitleLabel = new JLabel("# ID Interno: " + vehicle.getId());
        subtitleLabel.setFont(AppFonts.getRajdhani(14f));
        subtitleLabel.setForeground(new Color(110, 110, 110));
        header.add(subtitleLabel, BorderLayout.SOUTH);

        panel.add(header);
        panel.add(Box.createVerticalStrut(18));

        JPanel card = new JPanel(new BorderLayout(20, 20));
        card.setOpaque(true);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        ImageIcon vehicleIcon = vehicle.getImagePath() != null && !vehicle.getImagePath().isBlank()
                ? Icons.getVehicleImageLarge(vehicle.getImagePath())
                : Icons.getVehicleImageLarge();
        JLabel vehicleImage = new JLabel(vehicleIcon);
        vehicleImage.setHorizontalAlignment(SwingConstants.CENTER);
        vehicleImage.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 2, true));
        JPanel imageWrapper = new JPanel(new BorderLayout());
        imageWrapper.setOpaque(false);
        imageWrapper.add(vehicleImage, BorderLayout.CENTER);
        imageWrapper.setPreferredSize(new Dimension(340, 255));
        card.add(imageWrapper, BorderLayout.WEST);

        JPanel rightDetails = new JPanel();
        rightDetails.setLayout(new BoxLayout(rightDetails, BoxLayout.Y_AXIS));
        rightDetails.setOpaque(false);
        rightDetails.add(createDetailField("Marca", vehicle.getMarca()));
        rightDetails.add(Box.createVerticalStrut(8));
        rightDetails.add(createDetailField("Modelo y Año", vehicle.getModelo()));
        rightDetails.add(Box.createVerticalStrut(8));
        rightDetails.add(createDetailField("Color", vehicle.getColor()));
        rightDetails.add(Box.createVerticalStrut(8));
        rightDetails.add(createDetailField("Placas", vehicle.getPlacas()));
        rightDetails.add(Box.createVerticalStrut(8));
        rightDetails.add(createDetailField("# No. de serie (VIN)", vehicle.getVin()));

        card.add(rightDetails, BorderLayout.CENTER);
        panel.add(card);
        panel.add(Box.createVerticalStrut(18));

        JPanel actionRow = new JPanel(new BorderLayout());
        actionRow.setOpaque(false);
        JPanel leftButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        leftButtons.setOpaque(false);

        JButton btnEnEspera = createStatusButton("En Espera", new Color(220, 220, 220), AppTheme.textDark);
        JButton btnReparacion = createStatusButton("Reparación", new Color(220, 220, 220), AppTheme.textDark);
        JButton btnListoParaEntregar = createStatusButton("Listo", AppTheme.reysaRed, Color.WHITE);
        JButton btnEntregado = createStatusButton("Entregado", new Color(220, 220, 220), AppTheme.textDark);

        btnEnEspera.addActionListener(e -> vehicleManager.updateVehicleState(vehicle, org.DevMex.Reysa.models.VehicleState.EN_ESPERA));
        btnReparacion.addActionListener(e -> vehicleManager.updateVehicleState(vehicle, org.DevMex.Reysa.models.VehicleState.EN_REPARACION));
        btnListoParaEntregar.addActionListener(e -> vehicleManager.updateVehicleState(vehicle, org.DevMex.Reysa.models.VehicleState.LISTO_PARA_ENTREGAR));
        btnEntregado.addActionListener(e -> vehicleManager.updateVehicleState(vehicle, org.DevMex.Reysa.models.VehicleState.ENTREGADO));

        leftButtons.add(btnEnEspera);
        leftButtons.add(btnReparacion);
        leftButtons.add(btnListoParaEntregar);
        leftButtons.add(btnEntregado);
        actionRow.add(leftButtons, BorderLayout.WEST);

        JPanel separator = new JPanel();
        separator.setPreferredSize(new Dimension(1, 40));
        separator.setBackground(new Color(200, 200, 200));
        actionRow.add(separator, BorderLayout.CENTER);

        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        rightButtons.setOpaque(false);
        JButton btnCancelar = createStatusButton("Cancelar", new Color(220, 220, 220), AppTheme.textDark);
        JButton btnConfirmar = createStatusButton("Confirmar", AppTheme.reysaRed, Color.WHITE);
        btnCancelar.addActionListener(e -> showList());
        btnConfirmar.addActionListener(e -> showList());
        rightButtons.add(btnCancelar);
        rightButtons.add(btnConfirmar);
        actionRow.add(rightButtons, BorderLayout.EAST);

        panel.add(actionRow);
        return panel;
    }

    private JButton createStatusButton(String text, Color background, Color foreground) {
        JButton button = new JButton(text);
        button.setFont(AppFonts.getRajdhaniBold(14f));
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        return button;
    }

    private JPanel createDetailField(String labelText, String valueText) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);

        JLabel title = new JLabel(labelText);
        title.setFont(AppFonts.getRajdhani(12f));
        title.setForeground(new Color(110, 110, 110));
        wrapper.add(title, BorderLayout.NORTH);

        JLabel valueLabel = new JLabel(valueText);
        valueLabel.setFont(AppFonts.getRajdhani(14f));
        valueLabel.setForeground(AppTheme.textDark);
        valueLabel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        valueLabel.setOpaque(true);
        valueLabel.setBackground(new Color(245, 245, 245));
        wrapper.add(valueLabel, BorderLayout.CENTER);

        return wrapper;
    }

    private void showList() {
        cardLayout.show(contentCards, "List");
    }

    @Override
    public void onVehicleStatusChanged(org.DevMex.Reysa.models.Vehicle vehicle, org.DevMex.Reysa.models.VehicleState oldState, org.DevMex.Reysa.models.VehicleState newState) {
        refresh();
    }

    @Override
    public void onVehicleAdded(org.DevMex.Reysa.models.Vehicle vehicle) {
        refresh();
    }

    @Override
    public void onVehicleRemoved(org.DevMex.Reysa.models.Vehicle vehicle) {
        refresh();
    }
}
