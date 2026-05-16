package org.DevMex.Reysa.dao;

import org.DevMex.Reysa.db.Conexion;
import org.DevMex.Reysa.models.Client;
import org.DevMex.Reysa.models.Vehicle;
import org.DevMex.Reysa.models.VehicleState;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    public boolean registrarTodo(Client cliente, Vehicle vehiculo) {
        String sqlCliente = "INSERT INTO cliente (id_cliente, nombre, telefono, correo) VALUES (?, ?, ?, ?)";
        String sqlVehiculo = "INSERT INTO vehiculo (id_vehiculo, id_cliente, marca, modelo, anio, placa, color, vin, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection con = null;
        try {
            con = Conexion.getConexion();
            con.setAutoCommit(false);

            try (PreparedStatement psC = con.prepareStatement(sqlCliente)) {
                psC.setInt(1, cliente.getNoCliente());
                psC.setString(2, cliente.getNombre());
                psC.setString(3, cliente.getTelefono());
                psC.setString(4, cliente.getCorreo());
                psC.executeUpdate();
            }

            try (PreparedStatement psV = con.prepareStatement(sqlVehiculo)) {
                psV.setInt(1, vehiculo.getId());
                psV.setInt(2, cliente.getNoCliente());
                psV.setString(3, vehiculo.getMarca());
                psV.setString(4, vehiculo.getModelo());
                psV.setInt(5, 2026);
                psV.setString(6, vehiculo.getPlacas());
                psV.setString(7, vehiculo.getColor());
                psV.setString(8, vehiculo.getVin());
                psV.setString(9, vehiculo.getState().name());
                psV.executeUpdate();
            }

            con.commit();
            return true;
        } catch (SQLException e) {
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (con != null) {
                try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }
   
    public boolean actualizarEstado(int idVehiculo, String nuevoEstado) {
        String sql = "UPDATE vehiculo SET estado = ? WHERE id_vehiculo = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idVehiculo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarVehiculoCompleto(int idVehiculo) {
        String sqlBuscarCliente = "SELECT id_cliente FROM vehiculo WHERE id_vehiculo = ?";
        String sqlDelVehiculo = "DELETE FROM vehiculo WHERE id_vehiculo = ?";
        String sqlDelCliente = "DELETE FROM cliente WHERE id_cliente = ?";
        
        Connection con = null;
        try {
            con = Conexion.getConexion();
            con.setAutoCommit(false);
            
            int idCliente = -1;
            try (PreparedStatement psB = con.prepareStatement(sqlBuscarCliente)) {
                psB.setInt(1, idVehiculo);
                try (ResultSet rs = psB.executeQuery()) {
                    if (rs.next()) {
                        idCliente = rs.getInt("id_cliente");
                    }
                }
            }

            try (PreparedStatement psV = con.prepareStatement(sqlDelVehiculo)) {
                psV.setInt(1, idVehiculo);
                psV.executeUpdate();
            }

            if (idCliente != -1) {
                try (PreparedStatement psC = con.prepareStatement(sqlDelCliente)) {
                    psC.setInt(1, idCliente);
                    psC.executeUpdate();
                }
            }

            con.commit();
            return true;
        } catch (SQLException e) {
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) {}
            }
            e.printStackTrace();
            return false;
        } finally {
            if (con != null) {
                try { con.close(); } catch (SQLException e) {}
            }
        }
    }
    
    public List<Vehicle> obtenerTodosLosVehiculos() {
        List<Vehicle> lista = new ArrayList<>();
        String sql = "SELECT * FROM vehiculo";
        
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                int id = rs.getInt("id_vehiculo");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                String placa = rs.getString("placa");
                String color = rs.getString("color");
                String vin = rs.getString("vin");
                String estadoBD = rs.getString("estado"); 

                VehicleState state = VehicleState.EN_ESPERA;
                try {
                    if (estadoBD != null) {
                        state = VehicleState.valueOf(estadoBD);
                    }
                } catch (Exception e) {}

                lista.add(new Vehicle(id, marca, modelo, color, placa, vin, state));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}