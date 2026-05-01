package org.DevMex.Reysa.models;

import java.awt.Color;

public class Vehicle {
    private String id;
    private String marca;
    private String modelo;
    private String color;
    private String placas;
    private String vin;
    private String status;
    private Color statusColor;

    public Vehicle(String id, String marca, String modelo, String color, String placas, String vin, String status, Color statusColor) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.placas = placas;
        this.vin = vin;
        this.status = status;
        this.statusColor = statusColor;
    }

    public String getId() { return id; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public String getColor() { return color; }
    public String getPlacas() { return placas; }
    public String getVin() { return vin; }
    public String getStatus() { return status; }
    public Color getStatusColor() { return statusColor; }

    public void setId(String id) { this.id = id; }
    public void setMarca(String marca) { this.marca = marca; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setColor(String color) { this.color = color; }
    public void setPlacas(String placas) { this.placas = placas; }
    public void setVin(String vin) { this.vin = vin; }
    public void setStatus(String status) { this.status = status; }
    public void setStatusColor(Color statusColor) { this.statusColor = statusColor; }
}