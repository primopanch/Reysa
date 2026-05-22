CREATE DATABASE IF NOT EXISTS reysa_db;
USE reysa_db;

CREATE TABLE IF NOT EXISTS cliente (
    id_cliente INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS vehiculo (
    id_vehiculo INT PRIMARY KEY,
    id_cliente INT,
    marca VARCHAR(50),
    modelo VARCHAR(50),
    anio INT,
    placa VARCHAR(20),
    color VARCHAR(30),
    vin VARCHAR(50),
    estado VARCHAR(50),
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente) ON DELETE CASCADE
);
