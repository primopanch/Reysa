package org.DevMex.Reysa.models;

import java.awt.Color;

public enum VehicleState {
    EN_ESPERA("En Espera", new Color(110, 110, 110)),
    EN_REPARACION("En Reparación", new Color(245, 130, 32)),
    LISTO_PARA_ENTREGAR("Listo para Entregar", new Color(64, 176, 101)),
    ENTREGADO("Entregado", new Color(36, 122, 207));

    private final String displayName;
    private final Color color;

    VehicleState(String displayName, Color color) {
        this.displayName = displayName;
        this.color = color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Color getColor() {
        return color;
    }

    public static VehicleState fromString(String state) {
        try {
            return VehicleState.valueOf(state.toUpperCase().replace(" ", "_").replace("Á", "A"));
        } catch (IllegalArgumentException e) {
            return EN_ESPERA;
        }
    }
}
