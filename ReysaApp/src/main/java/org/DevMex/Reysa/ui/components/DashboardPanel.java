package org.DevMex.Reysa.ui.components;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.DevMex.Reysa.models.DashboardStats;
import org.DevMex.Reysa.models.Vehicle;
import org.DevMex.Reysa.models.VehicleManager;
import org.DevMex.Reysa.models.VehicleState;
import org.DevMex.Reysa.ui.themes.AppFonts;
import org.DevMex.Reysa.ui.themes.AppTheme;

public class DashboardPanel extends JPanel {

    private final VehicleManager vehicleManager;
    private JLabel labelVehiculosEnTaller;
    private JLabel labelEnEspera;
    private JLabel labelEnReparacion;
    private JLabel labelListosParaEntregar;

    public DashboardPanel(VehicleManager vehicleManager) {
        this.vehicleManager = vehicleManager;
        setOpaque(true);
        setBackground(AppTheme.bgMetalicGrey);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(createHeader());
        add(Box.createVerticalStrut(20));
        add(createStatsRow());
        add(Box.createVerticalStrut(20));
        add(createContentRow());
    }

    public void refreshStats() {
        updateDashboardStats();
        revalidate();
        repaint();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel greeting = new JPanel();
        greeting.setOpaque(false);
        greeting.setLayout(new BoxLayout(greeting, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Hola, Admin 👋");
        title.setFont(AppFonts.getOrbitron(34f));
        title.setForeground(AppTheme.textDark);

        JLabel subtitle = new JLabel("Aquí está el resumen del taller para hoy, Miércoles 8 de Abril");
        subtitle.setFont(AppFonts.getRajdhani(14f));
        subtitle.setForeground(new Color(110, 110, 110));

        greeting.add(title);
        greeting.add(Box.createVerticalStrut(6));
        greeting.add(subtitle);

        JPanel headerButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        headerButtons.setOpaque(false);
        headerButtons.add(createHeaderButton("Ver Agenda"));
        headerButtons.add(createHeaderButton("Directorio"));

        header.add(greeting, BorderLayout.WEST);
        header.add(headerButtons, BorderLayout.EAST);
        return header;
    }

    private JButton createHeaderButton(String text) {
        JButton button = new JButton(text);
        button.setFont(AppFonts.getRajdhani(12f));
        button.setBackground(Color.WHITE);
        button.setForeground(AppTheme.textDark);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        return button;
    }

    private JPanel createStatsRow() {
        DashboardStats stats = vehicleManager.calculateStats();
        int enTaller = stats.getEnEspera() + stats.getEnReparacion() + stats.getListosParaEntregar();

        JPanel statsRow = new JPanel(new GridLayout(1, 4, 18, 0));
        statsRow.setOpaque(false);

        labelVehiculosEnTaller = createMetricLabel(String.valueOf(enTaller));
        labelEnEspera = createMetricLabel(String.valueOf(stats.getEnEspera()));
        labelEnReparacion = createMetricLabel(String.valueOf(stats.getEnReparacion()));
        labelListosParaEntregar = createMetricLabel(String.valueOf(stats.getListosParaEntregar()));

        statsRow.add(createMetricCard("Vehículos en taller", labelVehiculosEnTaller, Icons.getCarIcon(), AppTheme.reysaRed));
        statsRow.add(createMetricCard("En Espera", labelEnEspera, Icons.getClockIcon(), new Color(110, 110, 110)));
        statsRow.add(createMetricCard("En Reparación", labelEnReparacion, Icons.getRanchIcon(), new Color(245, 130, 32)));
        statsRow.add(createMetricCard("Listos para Entregar", labelListosParaEntregar, Icons.getDoneIcon(), new Color(64, 176, 101)));

        return statsRow;
    }

    private JLabel createMetricLabel(String value) {
        JLabel label = new JLabel(value);
        label.setFont(AppFonts.getRajdhani(32f));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        return label;
    }

    private JPanel createMetricCard(String title, JLabel valueLabel, ImageIcon icon, Color accent) {
        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setOpaque(true);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(iconLabel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(AppFonts.getRajdhani(12f));
        titleLabel.setForeground(new Color(110, 110, 110));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(titleLabel, BorderLayout.CENTER);

        valueLabel.setForeground(accent);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(valueLabel, BorderLayout.SOUTH);
        return card;
    }

    private JPanel createContentRow() {
        JPanel contentRow = new JPanel(new GridLayout(1, 2, 18, 0));
        contentRow.setOpaque(false);
        contentRow.add(createDashboardTableCard());
        contentRow.add(createDashboardSummaryCard());
        return contentRow;
    }

    private JPanel createDashboardTableCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setOpaque(true);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));

        JLabel title = new JLabel("Ingresos y Actividad Reciente");
        title.setFont(AppFonts.getRajdhani(16f));
        title.setForeground(AppTheme.textDark);
        card.add(title, BorderLayout.NORTH);

        String[] columns = {"Orden", "Vehículo y Cliente", "Estado", "Ingreso", "Acción"};
        List<Vehicle> vehicles = vehicleManager.getAllVehicles();
        Object[][] rows = new Object[vehicles.size()][5];
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle vehicle = vehicles.get(i);
            rows[i][0] = vehicle.getId();
            rows[i][1] = vehicle.getMarca() + " " + vehicle.getModelo();
            rows[i][2] = vehicle.getStatus();
            rows[i][3] = "Hoy";
            rows[i][4] = "...";
        }

        DefaultTableModel model = new DefaultTableModel(rows, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(32);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(235, 235, 235));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.getColumnModel().getColumn(2).setCellRenderer(new DynamicStatusCellRenderer());
        table.getColumnModel().getColumn(4).setCellRenderer(new CenterCellRenderer());
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(180);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(60);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        card.add(scrollPane, BorderLayout.CENTER);
        return card;
    }

    private JPanel createDashboardSummaryCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(true);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));

        JLabel title = new JLabel("Resumen de la semana");
        title.setFont(AppFonts.getRajdhani(16f));
        title.setForeground(AppTheme.textDark);
        card.add(title);
        card.add(Box.createVerticalStrut(18));

        card.add(createSummaryStat("Ingresos Estimados", "$34,230", "+12%"));
        card.add(Box.createVerticalStrut(18));
        card.add(createProgressStat("Eficiencia del Taller", 85, new Color(64, 176, 101)));
        card.add(Box.createVerticalStrut(12));
        card.add(createProgressStat("Capacidad Ocupada", 62, new Color(245, 130, 32)));
        card.add(Box.createVerticalStrut(18));

        JButton reportButton = new JButton("Generar Reporte Completo");
        reportButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        reportButton.setBackground(AppTheme.baseBlack);
        reportButton.setForeground(Color.WHITE);
        reportButton.setFont(AppFonts.getRajdhaniBold(14f));
        reportButton.setFocusPainted(false);
        reportButton.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));
        card.add(reportButton);

        return card;
    }

    private JPanel createSummaryStat(String label, String value, String trend) {
        JPanel stat = new JPanel(new BorderLayout());
        stat.setOpaque(false);

        JLabel statLabel = new JLabel(label);
        statLabel.setFont(AppFonts.getRajdhani(12f));
        statLabel.setForeground(new Color(110, 110, 110));
        stat.add(statLabel, BorderLayout.NORTH);

        JLabel statValue = new JLabel(value);
        statValue.setFont(AppFonts.getRajdhani(24f));
        statValue.setForeground(AppTheme.textDark);
        stat.add(statValue, BorderLayout.CENTER);

        JLabel trendLabel = new JLabel(trend);
        trendLabel.setFont(AppFonts.getRajdhani(12f));
        trendLabel.setForeground(new Color(76, 175, 80));
        stat.add(trendLabel, BorderLayout.SOUTH);

        return stat;
    }

    private JPanel createProgressStat(String label, int percent, Color fillColor) {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);

        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(AppFonts.getRajdhani(12f));
        labelComponent.setForeground(new Color(110, 110, 110));
        wrapper.add(labelComponent);
        wrapper.add(Box.createVerticalStrut(6));

        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(percent);
        bar.setStringPainted(true);
        bar.setString(percent + "%");
        bar.setForeground(fillColor);
        bar.setBackground(new Color(235, 235, 235));
        bar.setPreferredSize(new Dimension(0, 24));
        wrapper.add(bar);

        return wrapper;
    }

    private class DynamicStatusCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String status = String.valueOf(value);
            label.setOpaque(true);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(AppFonts.getRajdhaniBold(12f));

            VehicleState state = VehicleState.fromString(status);
            switch (state) {
                case EN_ESPERA:
                    label.setBackground(new Color(240, 240, 240));
                    label.setForeground(new Color(110, 110, 110));
                    break;
                case EN_REPARACION:
                    label.setBackground(new Color(255, 235, 205));
                    label.setForeground(new Color(245, 130, 32));
                    break;
                case LISTO_PARA_ENTREGAR:
                    label.setBackground(new Color(214, 242, 228));
                    label.setForeground(new Color(64, 176, 101));
                    break;
                case ENTREGADO:
                    label.setBackground(new Color(221, 235, 247));
                    label.setForeground(new Color(36, 122, 207));
                    break;
                default:
                    label.setBackground(new Color(240, 240, 240));
                    label.setForeground(AppTheme.textDark);
            }
            label.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
            return label;
        }
    }

    private class CenterCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setOpaque(true);
            label.setBackground(Color.WHITE);
            return label;
        }
    }

    private void updateDashboardStats() {
        DashboardStats stats = vehicleManager.calculateStats();
        int enTaller = stats.getEnEspera() + stats.getEnReparacion() + stats.getListosParaEntregar();

        if (labelVehiculosEnTaller != null) labelVehiculosEnTaller.setText(String.valueOf(enTaller));
        if (labelEnEspera != null) labelEnEspera.setText(String.valueOf(stats.getEnEspera()));
        if (labelEnReparacion != null) labelEnReparacion.setText(String.valueOf(stats.getEnReparacion()));
        if (labelListosParaEntregar != null) labelListosParaEntregar.setText(String.valueOf(stats.getListosParaEntregar()));
    }
}
