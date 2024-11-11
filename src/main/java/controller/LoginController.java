package controller;

import model.ClientesDAO;
import view.Inicio;
import view.Login;

/**
 *
 * @author Daniel Fraile Leon
 */
public class LoginController {

    private ClientesDAO cliente;
    private Login vista;

    public LoginController(ClientesDAO cliente, Login vista) {
        this.cliente = cliente;
        this.vista = vista;
        //aqui creo los listeners de los botones y formulario

        vista.userForm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vista.userForm.setText("");
            }
        });
        vista.jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (checkUser(vista.userForm.getText(), new String(vista.passForm.getPassword()))) {
                    vista.dispose();
                    Inicio inicio = new Inicio();
                    inicio.setVisible(true);
                } else {
                    vista.mensajeError.setVisible(true);
                }
            }
        });
        vista.passForm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vista.passForm.setText("");
            }
        });

    }
//aqui hago los metodos de validacion
//a estos metodos los llamo desde el listener del constructor

    public boolean checkUser(String user, String pass) {
        boolean exists = false;
        if (user.equals("dani") & pass.equals("1234")) {
            exists = true;
        }
        return exists;
    }

}
