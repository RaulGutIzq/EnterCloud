package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Esta clase se encarga de gestionar la conexión con la base de datos MySQL.
 * Proporciona un método estático para establecer la conexión con la base de datos,
 * utilizando credenciales y URL definidas de forma estática.
 * 
 * <p>La clase utiliza el controlador JDBC de MySQL para conectarse a la base de datos y
 * manejar posibles excepciones que puedan surgir durante la conexión.</p>
 */
public class DatabaseConnection {
    
    // Cambia estos valores según tu configuración
    private static final String URL = "jdbc:mysql://192.168.13.22:3306/ENTERCLOUD";
    private static final String USER = "dani"; // Tu usuario de MySQL
    private static final String PASSWORD = "dani"; // Tu contraseña de MySQL

    /**
     * Establece la conexión con la base de datos utilizando los parámetros configurados.
     * 
     * @return Un objeto {@link Connection} que representa la conexión a la base de datos,
     *         o null si la conexión falla.
     */
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

    /**
     * Método principal que prueba la conexión a la base de datos.
     * 
     * @param args Argumentos de la línea de comandos, no se utilizan en este caso.
     */
    public static void main(String[] args) {
        connect(); // Probar la conexión
    }
}
