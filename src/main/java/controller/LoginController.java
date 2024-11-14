package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

        vista.jPanel1.setFocusable(true);
        vista.jPanel1.requestFocusInWindow();

        vista.jPanel1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    autenticarUsuario();
                }
            }
        });

        vista.userForm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    autenticarUsuario();
                }
            }
        });

        vista.passForm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    autenticarUsuario();
                }
            }
        });

        vista.jPanel1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    autenticarUsuario();
                }
            }
        });

        vista.userForm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vista.userForm.setText("");
            }
        });
        vista.jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                autenticarUsuario();
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

    private void autenticarUsuario() {
        if (checkUser(vista.userForm.getText(), new String(vista.passForm.getPassword()))) {
            vista.dispose();
            Inicio inicio = new Inicio();
            new InicioController(inicio);
            inicio.setVisible(true);
        } else {
            vista.mensajeError.setVisible(true);
        }
    }

    public boolean checkUser(String user, String pass) {
        return "dani".equals(user) && "1234".equals(pass);
    }
}
