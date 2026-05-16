package org.DevMex.Reysa.models;

import java.util.ArrayList;
import java.util.List;

public class VehicleManager {
    private List<Vehicle> vehicles;
    private List<VehicleStateListener> listeners;
    private static VehicleManager instance;

    private VehicleManager() {
        this.vehicles = new ArrayList<>();
        this.listeners = new ArrayList<>();
        loadVehiclesFromDB();
    }

    public static VehicleManager getInstance() {
        if (instance == null) {
            instance = new VehicleManager();
        }
        return instance;
    }

    private void loadVehiclesFromDB() {
        try {
            org.DevMex.Reysa.dao.ClientDAO dao = new org.DevMex.Reysa.dao.ClientDAO();
            List<Vehicle> de_la_base = dao.obtenerTodosLosVehiculos();
            
            if (de_la_base != null) {
                this.vehicles = de_la_base;
            }
        } catch (Exception e) {
            System.err.println("Error al cargar desde la DB, iniciando con lista vacía: " + e.getMessage());
            this.vehicles = new ArrayList<>();
        }
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        notifyVehicleAdded(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);
        notifyVehicleRemoved(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles);
    }

    public List<Vehicle> getVehiclesByState(VehicleState state) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getState() == state) {
                result.add(vehicle);
            }
        }
        return result;
    }

    public DashboardStats calculateStats() {
        DashboardStats stats = new DashboardStats(0, 0, 0, 0, 0);
        stats.setTotalVehicles(vehicles.size());
        
        for (Vehicle vehicle : vehicles) {
            switch (vehicle.getState()) {
                case EN_ESPERA:
                    stats.setEnEspera(stats.getEnEspera() + 1);
                    break;
                case EN_REPARACION:
                    stats.setEnReparacion(stats.getEnReparacion() + 1);
                    break;
                case LISTO_PARA_ENTREGAR:
                    stats.setListosParaEntregar(stats.getListosParaEntregar() + 1);
                    break;
                case ENTREGADO:
                    stats.setEntregados(stats.getEntregados() + 1);
                    break;
            }
        }
        return stats;
    }
    

    public void updateVehicleState(Vehicle vehicle, VehicleState newState) {
        org.DevMex.Reysa.dao.ClientDAO dao = new org.DevMex.Reysa.dao.ClientDAO();
        
        boolean ok = dao.actualizarEstado(vehicle.getId(), newState.name());

        if (ok) {
            VehicleState oldState = vehicle.getState();
            vehicle.setState(newState);
            notifyVehicleStatusChanged(vehicle, oldState, newState);
        }
    }
    
    public void addListener(VehicleStateListener listener) {
        listeners.add(listener);
    }

    public void removeListener(VehicleStateListener listener) {
        listeners.remove(listener);
    }

    private void notifyVehicleStatusChanged(Vehicle vehicle, VehicleState oldState, VehicleState newState) {
        for (VehicleStateListener listener : listeners) {
            listener.onVehicleStatusChanged(vehicle, oldState, newState);
        }
    }

    private void notifyVehicleAdded(Vehicle vehicle) {
        for (VehicleStateListener listener : listeners) {
            listener.onVehicleAdded(vehicle);
        }
    }

    private void notifyVehicleRemoved(Vehicle vehicle) {
        for (VehicleStateListener listener : listeners) {
            listener.onVehicleRemoved(vehicle);
        }
    }
}