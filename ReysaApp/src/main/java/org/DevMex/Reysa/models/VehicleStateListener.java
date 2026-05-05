package org.DevMex.Reysa.models;

import org.DevMex.Reysa.models.Vehicle;

public interface VehicleStateListener {
    void onVehicleStatusChanged(Vehicle vehicle, VehicleState oldState, VehicleState newState);
    void onVehicleAdded(Vehicle vehicle);
    void onVehicleRemoved(Vehicle vehicle);
}
