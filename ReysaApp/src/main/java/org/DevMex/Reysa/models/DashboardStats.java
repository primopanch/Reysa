package org.DevMex.Reysa.models;

public class DashboardStats {
    private int totalVehicles;
    private int enEspera;
    private int enReparacion;
    private int listosParaEntregar;
    private int entregados;

    public DashboardStats(int total, int espera, int reparacion, int listos, int entregados) {
        this.totalVehicles = total;
        this.enEspera = espera;
        this.enReparacion = reparacion;
        this.listosParaEntregar = listos;
        this.entregados = entregados;
    }

    public int getTotalVehicles() { return totalVehicles; }
    public int getEnEspera() { return enEspera; }
    public int getEnReparacion() { return enReparacion; }
    public int getListosParaEntregar() { return listosParaEntregar; }
    public int getEntregados() { return entregados; }

    public void setTotalVehicles(int totalVehicles) { this.totalVehicles = totalVehicles; }
    public void setEnEspera(int enEspera) { this.enEspera = enEspera; }
    public void setEnReparacion(int enReparacion) { this.enReparacion = enReparacion; }
    public void setListosParaEntregar(int listosParaEntregar) { this.listosParaEntregar = listosParaEntregar; }
    public void setEntregados(int entregados) { this.entregados = entregados; }

    @Override
    public String toString() {
        return "DashboardStats{" +
                "totalVehicles=" + totalVehicles +
                ", enEspera=" + enEspera +
                ", enReparacion=" + enReparacion +
                ", listosParaEntregar=" + listosParaEntregar +
                ", entregados=" + entregados +
                '}';
    }
}
