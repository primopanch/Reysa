package org.DevMex.Reysa.db;

import org.DevMex.Reysa.dao.ClientDAO;
import org.DevMex.Reysa.models.Client;
import org.DevMex.Reysa.models.Vehicle;
import org.DevMex.Reysa.models.VehicleState;

public class PruebaBackend {
    public static void main(String[] args) {
        ClientDAO dao = new ClientDAO();

        Client cliente = new Client("Xavier Paredes", "xavier@uabcs.mx", 500, "6121234567");
        
        // We use String ID for the vehicle as the application expects
        Vehicle vehiculo = new Vehicle("ORDEN-2026-500", "Toyota", "Supra", "Blanco", "ABC-123", "VIN987654321", VehicleState.EN_ESPERA);

        System.out.println("Intentando registrar cliente y vehículo...");
        
        boolean exito = dao.registrarTodo(cliente, vehiculo);

        if (exito) {
            System.out.println("¡Registro exitoso en la base de datos!");
        } else {
            System.out.println("Error al registrar. Revisa la base de datos o la conexión (SQLException).");
        }
    }
}
