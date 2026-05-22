package org.DevMex.Reysa.ui.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import org.DevMex.Reysa.Main;
import org.DevMex.Reysa.ui.themes.AppFonts;

public class LoginPanel extends JPanel {
    private Image bgImage;
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JLabel lblError;
    private JButton btnLogin;

    public LoginPanel() {
        try {
            bgImage = new ImageIcon(getClass().getResource("/org/DevMex/Reysa/resources/icons/login background.png")).getImage();
        } catch (Exception e) {
            System.err.println("No se pudo cargar el fondo de login");
        }

        setLayout(new BorderLayout());

        // Panel derecho (contenedor del formulario)
        JPanel rightPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(155, 155, 155, 240)); // Gris semi-transparente como en la imagen
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new Dimension(480, 0)); // Anchura fija, siempre a la derecha

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);

        // Logo
        JLabel logoLabel = new JLabel();
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/org/DevMex/Reysa/resources/icons/ReysaL.png"));
            Image img = logoIcon.getImage();
            Image scaled = img.getScaledInstance(180, 180, Image.SCALE_SMOOTH); 
            logoLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            logoLabel.setText("Reysa Logo");
            logoLabel.setFont(AppFonts.getOrbitronBold(24f));
            logoLabel.setForeground(Color.WHITE);
        }
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(logoLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Etiqueta Usuario
        JLabel lblUser = new JLabel("Usuario");
        lblUser.setFont(AppFonts.getRajdhani(20f));
        lblUser.setForeground(new Color(50, 50, 50));
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(lblUser);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Campo Usuario
        txtUser = createCustomTextField();
        formPanel.add(txtUser);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Etiqueta Contraseña
        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setFont(AppFonts.getRajdhani(20f));
        lblPass.setForeground(new Color(50, 50, 50));
        lblPass.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(lblPass);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Campo Contraseña
        txtPass = createCustomPasswordField();
        formPanel.add(txtPass);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Etiqueta de Error
        lblError = new JLabel(" ");
        lblError.setFont(AppFonts.getRajdhaniBold(14f));
        lblError.setForeground(Color.RED);
        lblError.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(lblError);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Botón Ingresar
        btnLogin = new JButton("Ingresar") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btnLogin.setFont(AppFonts.getRajdhaniBold(22f));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(new Color(255, 69, 0)); // Naranja-rojo vibrante
        btnLogin.setFocusPainted(false);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setBorder(new EmptyBorder(10, 50, 10, 50));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        
        formPanel.add(btnLogin);
        rightPanel.add(formPanel);
        add(rightPanel, BorderLayout.EAST);
    }

    private JTextField createCustomTextField() {
        JTextField tf = new JTextField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
                super.paintComponent(g);
                g2.dispose();
            }
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color borderColor = (Color) getClientProperty("borderColor");
                if (borderColor == null) borderColor = new Color(180, 180, 180);
                g2.setColor(borderColor);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
                g2.dispose();
            }
        };
        tf.setOpaque(false);
        tf.setFont(AppFonts.getRajdhani(18f));
        tf.setMaximumSize(new Dimension(300, 45));
        tf.setPreferredSize(new Dimension(300, 45));
        tf.setBorder(new EmptyBorder(5, 15, 5, 15));
        tf.setBackground(new Color(230, 230, 230));
        tf.setHorizontalAlignment(JTextField.CENTER);
        return tf;
    }

    private JPasswordField createCustomPasswordField() {
        JPasswordField pf = new JPasswordField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
                super.paintComponent(g);
                g2.dispose();
            }
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color borderColor = (Color) getClientProperty("borderColor");
                if (borderColor == null) borderColor = new Color(180, 180, 180);
                g2.setColor(borderColor);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
                g2.dispose();
            }
        };
        pf.setOpaque(false);
        pf.setFont(AppFonts.getRajdhani(18f));
        pf.setMaximumSize(new Dimension(300, 45));
        pf.setPreferredSize(new Dimension(300, 45));
        pf.setBorder(new EmptyBorder(5, 15, 5, 15));
        pf.setBackground(new Color(230, 230, 230));
        pf.setHorizontalAlignment(JTextField.CENTER);
        return pf;
    }

    private void login() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword()).trim();

        if (user.isEmpty() || pass.isEmpty()) {
            lblError.setText("Ambos campos son requeridos");
            txtUser.putClientProperty("borderColor", Color.RED);
            txtPass.putClientProperty("borderColor", Color.RED);
            txtUser.repaint();
            txtPass.repaint();
            return;
        }

        if (user.equals("Admin") && pass.equals("1234")) {
            lblError.setText("");
            // Reset borders
            txtUser.putClientProperty("borderColor", new Color(180, 180, 180));
            txtPass.putClientProperty("borderColor", new Color(180, 180, 180));
            txtUser.repaint();
            txtPass.repaint();
            Main.loginSuccess();
        } else {
            lblError.setText("Usuario o contraseña incorrectos");
            txtUser.putClientProperty("borderColor", Color.RED);
            txtPass.putClientProperty("borderColor", Color.RED);
            txtUser.repaint();
            txtPass.repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
