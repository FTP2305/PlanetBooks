/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/planet_books_db";
    private static final String USUARIO = "root"; 
    private static final String CONTRASENA = "outemo123"; 

    private static Connection connection = null;

  
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
                System.out.println("Conexión a la base de datos establecida correctamente.");
            } catch (ClassNotFoundException e) {
                System.err.println("Error: Driver JDBC de MySQL no encontrado.");
                throw new SQLException("Driver JDBC de MySQL no encontrado.", e);
            } catch (SQLException e) {
                System.err.println("Error al conectar con la base de datos: " + e.getMessage());
                throw e; 
            }
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            try {
                connection.close();
                connection = null; 
                System.out.println("Conexion a la base de datos cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
                throw e; // Relanza la excepción
            }
        }
    }

    public static void main(String[] args) {
        Connection testConnection = null;
        try {
            testConnection = ConexionBD.getConnection();
            if (testConnection != null) {
                System.out.println("Prueba de conexion exitosa");
            }
        } catch (SQLException e) {
            System.err.println("¡Fallo en la prueba de conexion " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (testConnection != null) {
                    ConexionBD.closeConnection();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexion de prueba: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
