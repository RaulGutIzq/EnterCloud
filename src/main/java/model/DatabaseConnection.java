/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Cambia estos valores según tu configuración
    private static final String URL = "jdbc:mysql://192.168.13.22:3306/ENTERCLOUD";
    private static final String USER = "raul"; // Tu usuario de MySQL
    private static final String PASSWORD = "raul"; // Tu contraseña de MySQL

    public static Connection connect() {
        try {
            // Cargar el controlador JDBC de MySQL
            Class.forName("com.mysql.jdbc.Driver");

            // Establecer la conexión
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Conexión exitosa a la base de datos.");
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        connect(); // Probar la conexión
    }
}


