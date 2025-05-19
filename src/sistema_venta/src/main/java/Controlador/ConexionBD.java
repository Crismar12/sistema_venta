/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase ConexionBD implementa el patrón Singleton para manejar una única instancia de conexión a la base de datos.
 */
public class ConexionBD {
    
    // URL de conexión, usuario y contraseña
    private static final String URL = "jdbc:mysql://localhost:3306/sistemaventas";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Instancia única (Singleton)
    private static Connection instance;

    // Constructor privado para evitar instanciación
    private ConexionBD() {}

    /**
     * Método para obtener la instancia única de conexión.
     * @return La instancia de Connection
     */  
        public static Connection getInstance() {
        try {
            // 🔹 Si la conexión está cerrada o es nula, se vuelve a abrir
            if (instance == null || instance.isClosed()) {
                instance = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("📌 Conexión (re)establecida correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar/reconectar: " + e.getMessage());
        }
        return instance;
    }
}
