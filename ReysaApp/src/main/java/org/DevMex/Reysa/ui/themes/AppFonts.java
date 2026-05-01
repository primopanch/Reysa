package org.DevMex.Reysa.ui.themes;

import java.awt.Font;
import java.io.InputStream;

public class AppFonts {

    public static Font getOrbitron(float size) {
        return loadFont("/org/DevMex/Reysa/resources/fonts/Orbitron-Regular.ttf", size);
    }

    public static Font getOrbitronBold(float size) {
        return loadFont("/org/DevMex/Reysa/resources/fonts/Orbitron-Bold.ttf", size);
    }

    public static Font getRajdhani(float size) {
        return loadFont("/org/DevMex/Reysa/resources/fonts/Rajdhani-SemiBold.ttf", size); 
    }

    private static Font loadFont(String path, float size) {
        try {
            InputStream is = AppFonts.class.getResourceAsStream(path);
            if (is != null) {
                Font font = Font.createFont(Font.TRUETYPE_FONT, is);
                return font.deriveFont(size);
            }
        } catch (Exception e) {
            System.err.println("No se pudo cargar la fuente: " + path + ". Usando fuente por defecto.");
        }
        return new Font("SansSerif", Font.PLAIN, (int)size);
    }
}