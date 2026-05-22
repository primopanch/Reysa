package org.DevMex.Reysa.models;

import java.util.ArrayList;
import java.util.List;
import org.DevMex.Reysa.dao.ClientDAO;

public class VehicleManager {
    private List<Vehicle> vehicles;
    private List<VehicleStateListener> listeners;
    private static VehicleManager instance;

    private VehicleManager() {
        this.vehicles = new ArrayList<>();
        this.listeners = new ArrayList<>();
        
        ClientDAO dao = new ClientDAO();
        List<Vehicle> dbVehicles = dao.obtenerTodosLosVehiculos();
        if (dbVehicles != null && !dbVehicles.isEmpty()) {
            this.vehicles.addAll(dbVehicles);
        } else {
            initializeSampleVehicles();
        }
    }

    public static VehicleManager getInstance() {
        if (instance == null) {
            instance = new VehicleManager();
        }
        return instance;
    }

    private void initializeSampleVehicles() {
        addVehicle(new Vehicle("ORDEN-2026-017", "Honda", "Civic 2009", "Gris plata", "B-101-707", "4TX89BN3TXWXXXXXX", VehicleState.EN_REPARACION));
        addVehicle(new Vehicle("ORDEN-2026-018", "Toyota", "RAV4 2021", "Blanco", "A-202-808", "3T1BF1KA5CU234567", VehicleState.EN_ESPERA));
        addVehicle(new Vehicle("ORDEN-2026-019", "Nissan", "NP300 2022", "Negro", "C-303-909", "1N6AD0EX5CN345678", VehicleState.EN_ESPERA));
        addVehicle(new Vehicle("ORDEN-2026-020", "Ford", "F-150 XL 2020", "Azul", "D-404-010", "1FTFW1ET5DFA12345", VehicleState.LISTO_PARA_ENTREGAR));
        addVehicle(new Vehicle("ORDEN-2026-021", "Chevrolet", "Spark 2017", "Rojo", "E-505-111", "3G1YE57K672123456", VehicleState.EN_REPARACION));
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
        ClientDAO dao = new ClientDAO();
        boolean ok = dao.actualizarEstado(vehicle.getId(), newState.name());
        
        if (ok) {
            VehicleState oldState = vehicle.getState();
            vehicle.setState(newState);
            notifyVehicleStatusChanged(vehicle, oldState, newState);
        } else {
            // Even if DB fails, update local state or log error (for demo purposes we update it anyway)
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
