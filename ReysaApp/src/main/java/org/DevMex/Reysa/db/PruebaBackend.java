package org.DevMex.Reysa.db;

import org.DevMex.Reysa.dao.ClientDAO;
import org.DevMex.Reysa.models.Client;
import org.DevMex.Reysa.models.Vehicle;
import org.DevMex.Reysa.models.VehicleState;

public class PruebaBackend {
    public static void main(String[] args) {
        ClientDAO dao = new ClientDAO();

        Client cliente = new Client("Xavier Paredes", "xavier@uabcs.mx", 500, "6121234567");
        
        Vehicle vehiculo = new Vehicle(500, "Toyota", "Supra", "Blanco", "ABC-123", "VIN987654321", VehicleState.EN_ESPERA);

        System.out.println("Intentando registrar cliente y vehículo...");
        
        boolean exito = dao.registrarTodo(cliente, vehiculo);

        if (exito) {
            System.out.println("¡Registro exitoso en la base de datos!");
        } else {
            System.out.println("Error al registrar. Revisa la consola para ver el SQLException.");
        }
    }
}