

package main;

import controller.LoginController;
import java.io.FileNotFoundException;
import model.ClientesDAO;
import view.Login;

/**
 *
 * @author Daniel Fraile Leon
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        ClientesDAO model = new ClientesDAO("Clientes.dat","r");
        Login view = new Login();
        LoginController controlador = new LoginController(model,view);
    }
}
