package org.DevMex.Reysa.ui.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.DevMex.Reysa.models.Vehicle;
import org.DevMex.Reysa.models.VehicleManager;
import org.DevMex.Reysa.models.VehicleState;
import org.DevMex.Reysa.ui.themes.AppFonts;
import org.DevMex.Reysa.ui.themes.AppTheme;

public class ClientsPanel extends JPanel {

    private final VehicleManager vehicleManager;
    private JTextField nombreField;
    private JTextField correoField;
    private JTextField clienteIdField;
    private JTextField telefonoField;
    private JTextField marcaField;
    private JTextField modeloField;
    private JTextField colorField;
    private JTextField placasField;
    private JTextField vinField;
    private JLabel photoPreviewLabel;
    private String photoPath;

    public ClientsPanel() {
        vehicleManager = VehicleManager.getInstance();
        setOpaque(true);
        setBackground(AppTheme.bgMetalicGrey);
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(true);
        contentPanel.setBackground(AppTheme.bgMetalicGrey);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        contentPanel.add(createHeader());
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(createMetrics());
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(createClientContactCard());
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(createVehicleDataCard());
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(createActionButtonRow());
        contentPanel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel titleBlock = new JPanel();
        titleBlock.setOpaque(false);
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Clientes");
        title.setFont(AppFonts.getOrbitron(34f));
        title.setForeground(AppTheme.textDark);

        JLabel subtitle = new JLabel("Administra tus clientes y sus solicitudes de servicio.");
        subtitle.setFont(AppFonts.getRajdhani(14f));
        subtitle.setForeground(new Color(110, 110, 110));

        titleBlock.add(title);
        titleBlock.add(Box.createVerticalStrut(8));
        titleBlock.add(subtitle);
        header.add(titleBlock, BorderLayout.WEST);
        return header;
    }

    private JPanel createMetrics() {
        JPanel statsRow = new JPanel(new GridLayout(1, 4, 18, 0));
        statsRow.setOpaque(false);
        statsRow.add(createReservaMetricCard("Clientes Activos", "18", Icons.getClientsIcon()));
        statsRow.add(createReservaMetricCard("Nuevos", "4", Icons.getClientsIcon()));
        statsRow.add(createReservaMetricCard("Pendientes", "2", Icons.getClientsIcon()));
        statsRow.add(createReservaMetricCard("Total", "24", Icons.getClientsIcon()));
        return statsRow;
    }

    private JPanel createClientContactCard() {
        JPanel card = createCardPanel(new GridLayout(4, 2, 16, 16));

        nombreField = createInputField();
        correoField = createInputField();
        clienteIdField = createInputField();
        telefonoField = createInputField();

        card.add(createFormLabel("Nombre:"));
        card.add(nombreField);
        card.add(createFormLabel("Correo electrónico:"));
        card.add(correoField);
        card.add(createFormLabel("No. Cliente:"));
        card.add(clienteIdField);
        card.add(createFormLabel("No. Teléfono:"));
        card.add(telefonoField);

        return card;
    }

    private JPanel createVehicleDataCard() {
        JPanel card = createCardPanel(new BorderLayout(20, 20));

        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JPanel photoFrame = new JPanel(new BorderLayout());
        photoFrame.setOpaque(false);
        photoFrame.setPreferredSize(new Dimension(320, 240));
        photoFrame.setMinimumSize(new Dimension(100, 100));
        photoFrame.setMaximumSize(new Dimension(320, 240));

        photoPreviewLabel = new JLabel();
        photoPreviewLabel.setOpaque(true);
        photoPreviewLabel.setBackground(new Color(245, 245, 245));
        photoPreviewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        photoPreviewLabel.setBorder(new RoundedBorder(22));
        photoPreviewLabel.setIcon(Icons.getVehicleImageLarge());

        photoFrame.add(photoPreviewLabel, BorderLayout.CENTER);
        leftPanel.add(photoFrame);
        leftPanel.add(Box.createVerticalStrut(14));

        JButton uploadPhotoButton = createPrimaryButton("Agregar foto");
        uploadPhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choosePhoto();
            }
        });
        uploadPhotoButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(uploadPhotoButton);

        JPanel rightPanel = new JPanel(new GridLayout(5, 2, 14, 14));
        rightPanel.setOpaque(false);
        rightPanel.add(createFormLabel("Marca"));
        marcaField = createInputField();
        rightPanel.add(marcaField);
        rightPanel.add(createFormLabel("Modelo y Año"));
        modeloField = createInputField();
        rightPanel.add(modeloField);
        rightPanel.add(createFormLabel("Color"));
        colorField = createInputField();
        rightPanel.add(colorField);
        rightPanel.add(createFormLabel("Placas"));
        placasField = createInputField();
        rightPanel.add(placasField);
        rightPanel.add(createFormLabel("# No. de serie (VIN)"));
        vinField = createInputField();
        rightPanel.add(vinField);

        card.add(leftPanel, BorderLayout.WEST);
        card.add(rightPanel, BorderLayout.CENTER);
        return card;
    }

    private JPanel createActionButtonRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        row.setOpaque(false);

        JButton btnCancelar = createSecondaryButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });

        JButton btnConfirmar = createPrimaryButton("Confirmar");
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveNewClient();
            }
        });

        row.add(btnCancelar);
        row.add(btnConfirmar);
        return row;
    }

    private void choosePhoto() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png", "gif"));
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            photoPath = selectedFile.getAbsolutePath();
            ImageIcon icon = Icons.getVehicleImageLarge(photoPath);
            photoPreviewLabel.setIcon(icon);
        }
    }

    private void saveNewClient() {
        String marca = marcaField.getText().trim();
        String modelo = modeloField.getText().trim();
        String color = colorField.getText().trim();
        String placas = placasField.getText().trim();
        String vin = vinField.getText().trim();

        if (marca.isEmpty() || modelo.isEmpty() || color.isEmpty() || placas.isEmpty() || vin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor completa todos los campos del vehículo.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String newId = String.format("ORDEN-2026-%03d", vehicleManager.getAllVehicles().size() + 1);
        Vehicle newVehicle = new Vehicle(newId, marca, modelo, color, placas, vin, VehicleState.EN_ESPERA, photoPath);
        vehicleManager.addVehicle(newVehicle);

        JOptionPane.showMessageDialog(this, "Cliente y vehículo guardados correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        resetForm();
    }

    private void resetForm() {
        nombreField.setText("");
        correoField.setText("");
        clienteIdField.setText("");
        telefonoField.setText("");
        marcaField.setText("");
        modeloField.setText("");
        colorField.setText("");
        placasField.setText("");
        vinField.setText("");
        photoPath = null;
        photoPreviewLabel.setIcon(Icons.getVehicleImageLarge());
    }

    private JPanel createFormLabel(String text) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        JLabel label = new JLabel(text);
        label.setFont(AppFonts.getRajdhani(12f));
        label.setForeground(new Color(90, 90, 90));
        wrapper.add(label, BorderLayout.WEST);
        return wrapper;
    }

    private JTextField createInputField() {
        JTextField field = new JTextField();
        field.setOpaque(true);
        field.setBackground(new Color(245, 245, 245));
        field.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(16),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)));
        field.setFont(AppFonts.getRajdhani(14f));
        return field;
    }

    private JButton createPrimaryButton(String text) {
        RoundedButton button = new RoundedButton(text);
        button.setFont(AppFonts.getRajdhaniBold(14f));
        button.setBackground(AppTheme.reysaRed);
        button.setForeground(Color.WHITE);
        button.setCornerRadius(-1);
        button.setPreferredSize(new Dimension(150, 44));
        return button;
    }

    private JButton createSecondaryButton(String text) {
        RoundedButton button = new RoundedButton(text);
        button.setFont(AppFonts.getRajdhaniBold(14f));
        button.setBackground(new Color(240, 240, 240));
        button.setForeground(AppTheme.textDark);
        button.setCornerRadius(-1);
        button.setPreferredSize(new Dimension(150, 44));
        return button;
    }

    private JPanel createCardPanel(LayoutManager layout) {
        ShadowCardPanel card = new ShadowCardPanel(24, 6, Color.WHITE);
        card.setLayout(layout);
        card.setBorder(BorderFactory.createCompoundBorder(
                card.getBorder(),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        return card;
    }



    private static class RoundedBorder extends AbstractBorder {
        private final int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(210, 210, 210));
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }
    }

    private JPanel createReservaMetricCard(String title, String value, ImageIcon icon) {
        ShadowCardPanel card = new ShadowCardPanel(15, 6, Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                card.getBorder(),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(iconLabel, BorderLayout.NORTH);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(AppFonts.getRajdhani(26f));
        valueLabel.setForeground(AppTheme.textDark);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(valueLabel, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(AppFonts.getRajdhani(12f));
        titleLabel.setForeground(new Color(110, 110, 110));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(titleLabel, BorderLayout.SOUTH);

        return card;
    }
}
