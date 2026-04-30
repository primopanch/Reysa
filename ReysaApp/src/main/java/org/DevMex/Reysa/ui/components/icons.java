package org.DevMex.Reysa.ui.components;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class Icons {

    // --- LOGO PRINCIPAL ---
    public static ImageIcon getLogo() {
        return loadAndScale("/org/DevMex/Reysa/resources/icons/ReysaLogo.png", 170, 130); // Ajusta la altura (50) según la proporción real de tu logo
    }

    // --- ÍCONOS DE NAVEGACIÓN ---
    public static ImageIcon getHomeIcon() {
        return loadAndScale("/org/DevMex/Reysa/resources/icons/homeIcon.png", 24, 24);
    }

    public static ImageIcon getVehiclesIcon() {
        return loadAndScale("/org/DevMex/Reysa/resources/icons/vehicles.png", 24, 24);
    }

    public static ImageIcon getClientsIcon() {
        return loadAndScale("/org/DevMex/Reysa/resources/icons/clients.png", 24, 24);
    }

    public static ImageIcon getCalendarIcon() {
        return loadAndScale("/org/DevMex/Reysa/resources/icons/calendar.png", 24, 24);
    }

    // --- ÍCONO DE USUARIO ---
    public static ImageIcon getProfileIcon() {
        return loadAndScale("/org/DevMex/Reysa/resources/icons/profile.png", 30, 30);
    }

     public static ImageIcon vehicle1E() {
        return loadAndScale("/org/DevMex/Reysa/resources/icons/vehicle1E.png", 30, 30);
    }

     public static ImageIcon vehicle2E() {
        return loadAndScale("/org/DevMex/Reysa/resources/icons/vehicle2E.png", 30, 30);
    }

     public static ImageIcon vehicle3E() {
        return loadAndScale("/org/DevMex/Reysa/resources/icons/vehicle3E.png", 30, 30);
    }

    public static ImageIcon getVehicleImageLarge() {
        return loadAndScale("/org/DevMex/Reysa/resources/icons/vehicle1E.png", 180, 140);
    }

    private static ImageIcon loadAndScale(String path, int width, int height) {
        try {
            java.net.URL imgURL = Icons.class.getResource(path);
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image source = icon.getImage();
                BufferedImage scaledBI = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = scaledBI.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.drawImage(source, 0, 0, width, height, null);
                g2.dispose();
                return new ImageIcon(scaledBI);
            }
        } catch (Exception e) {
            System.err.println("Imagen no encontrada: " + path);
        }
        return null;
    }

}