package org.DevMex.Reysa.ui.themes;

import java.awt.Font;
import java.io.InputStream;

public class AppFonts {

    // Método para cargar la fuente Orbitron (Para Títulos)
    public static Font getOrbitron(float size) {
        return loadFont("/org/DevMex/Reysa/resources/fonts/Orbitron-Regular.ttf", size);
    }

    // Método para cargar la fuente Orbitron en Negrita (Para Títulos destacados)
    public static Font getOrbitronBold(float size) {
        return loadFont("/org/DevMex/Reysa/resources/fonts/Orbitron-Bold.ttf", size);
    }

    // Método para cargar la fuente Rajdhani (Para UI y Contenido)
    public static Font getRajdhani(float size) {
        // Rajdhani suele ser más legible en SemiBold o Medium para botones
        return loadFont("/org/DevMex/Reysa/resources/fonts/Rajdhani-SemiBold.ttf", size); 
    }

    // Método privado que realiza la carga segura
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
        // Fallback: Si no encuentra el archivo .ttf, usa una fuente del sistema
        return new Font("SansSerif", Font.PLAIN, (int)size);
    }
}