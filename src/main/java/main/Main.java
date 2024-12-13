package main;

import controller.LoginController;
import java.io.FileNotFoundException;
import model.ClientesDAO;
import view.Login;

/**
 * Clase principal de la aplicación. Encargada de inicializar el modelo, la
 * vista y el controlador del sistema.
 *
 * @author CDC
 */
public class Main {

    /**
     * Método principal de la aplicación.
     *
     * @param args Argumentos de línea de comandos (no utilizados en este caso).
     * @throws FileNotFoundException Si el archivo de datos especificado no se
     * encuentra.
     */
    public static void main(String[] args) throws FileNotFoundException {
        // Inicialización del modelo, utilizando un archivo de datos existente.
        // ClientesDAO se encarga de manejar la persistencia de datos de los clientes.
        ClientesDAO model = new ClientesDAO("Clientes.dat", "r");

        // Inicialización de la vista, que corresponde a la interfaz de login.
        Login view = new Login();

        // Inicialización del controlador, que gestiona la interacción entre el modelo y la vista.
        LoginController controlador = new LoginController(model, view);

        // Hacer visible la interfaz gráfica al usuario.
        view.setVisible(true);

    }
}
