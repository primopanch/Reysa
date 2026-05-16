package org.DevMex.Reysa.models;

public class Client {
    private String nombre;
    private String correo;
    private int noCliente;
    private String telefono;

    public Client(String nombre, String correo, int noCliente, String telefono) {
        this.nombre = nombre;
        this.correo = correo;
        this.noCliente = noCliente;
        this.telefono = telefono;
    }

    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public int getNoCliente() { return noCliente; }
    public String getTelefono() { return telefono; }
}