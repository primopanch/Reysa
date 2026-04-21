package org.DevMex.Reysa.ui.components;

import java.awt.Image;

import javax.swing.ImageIcon;

public class icons {

    // --- LOGO PRINCIPAL ---
    public static ImageIcon getLogo() {
        return loadAndScale("/org/DevMex/Reysa/resources/icons/ReysaLogo.png", 180, 50); // Ajusta la altura (50) según la proporción real de tu logo
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

    private static ImageIcon loadAndScale(String path, int width, int height) {
        try {
            java.net.URL imgURL = icons.class.getResource(path);
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            }
        } catch (Exception e) {
            System.err.println("Imagen no encontrada: " + path);
        }
        return null;
    }
}