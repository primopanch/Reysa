package org.DevMex.Reysa.models;

import java.awt.Color;

public class Vehicle {
    private int id;
    private String marca;
    private String modelo;
    private String color;
    private String placas;
    private String vin;
    private VehicleState state;
    private String imagePath;

    public Vehicle(int id, String marca, String modelo, String color, String placas, String vin, VehicleState state) {
        this(id, marca, modelo, color, placas, vin, state, null);
    }

    public Vehicle(int id, String marca, String modelo, String color, String placas, String vin, VehicleState state, String imagePath) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.placas = placas;
        this.vin = vin;
        this.state = state != null ? state : VehicleState.EN_ESPERA;
        this.imagePath = imagePath;
    }

    public int getId() { return id; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public String getColor() { return color; }
    public String getPlacas() { return placas; }
    public String getVin() { return vin; }
    public VehicleState getState() { return state; }
    public String getStatus() { return state.getDisplayName(); }
    public Color getStatusColor() { return state.getColor(); }

    public void setId(int id) { this.id = id; }
    public void setMarca(String marca) { this.marca = marca; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setColor(String color) { this.color = color; }
    public void setPlacas(String placas) { this.placas = placas; }
    public void setVin(String vin) { this.vin = vin; }
    public void setState(VehicleState state) { this.state = state != null ? state : VehicleState.EN_ESPERA; }
    
    @Deprecated
    public void setStatus(String status) { 
        this.state = VehicleState.fromString(status);
    }
    
    @Deprecated
    public void setStatusColor(Color statusColor) { }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}