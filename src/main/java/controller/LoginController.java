package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cliente;
import model.ClientesDAO;
import view.Inicio;
import view.Login;

/**
 *
 * @author Daniel Fraile Leon
 */
public class LoginController {

    private static final String RAIZBUCKET = "E:/DAM2/DI";
    private ClientesDAO cliente;
    private Login vista;

    public LoginController(ClientesDAO cliente, Login vista) {
        this.cliente = cliente;
        this.vista = vista;

        vista.jPanel1.setFocusable(true);
        vista.jPanel1.requestFocusInWindow();
        vista.userForm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vista.userForm.setText("");
            }
        });
        vista.jButton1.addActionListener(evt -> {
            botonLoginActionPerformed(evt);
        });

        vista.passForm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vista.passForm.setText("");
            }
        });
        vista.getRootPane().setDefaultButton(vista.jButton1);

    }

    private void irAInicio(String correo) throws IOException {
        Cliente c = null;
        ClientesDAO fich = null;
        try {
            fich = new ClientesDAO("Clientes.dat", "r");
            try {
                while (true) {
                    c = fich.leer();
                    if (c.getCorreo().equals(correo)) {
                        break;
                    }
                }
            } catch (EOFException eof) {
                c = null;
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Archivo no encontrado: " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("Error de I/O: " + ex.getMessage());
        } finally {
            if (fich != null) {
                try {
                    fich.cerrar();
                } catch (IOException e) {
                    System.err.println("Error al cerrar: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        if (c != null) {

            File raizUser = new File((RAIZBUCKET + "/" + c.getId()).replace('/', File.separatorChar));
            if (!raizUser.exists()) {
                if (!raizUser.mkdir()) {
                    throw new IOException("No se ha podido crear la raiz del usuario: " + c.getId());
                }
            }
            Inicio inicio = new Inicio();
            new InicioController(inicio, c);
            inicio.setVisible(true);
            this.vista.setVisible(false);
        } else {
            System.err.println("No se ha encontrado el cliente: " + correo + " en Clientes.dat");
        }
    }

    private void botonLoginActionPerformed(java.awt.event.ActionEvent evt) {
        boolean esValido;
        String correo = null, pass;
        try {
            correo = vista.userForm.getText();
            pass = new String(vista.passForm.getPassword());
            esValido = correoCorrecto(correo, pass);
        } catch (NullPointerException e) {
            esValido = false;
        }
        vista.mensajeError.setVisible(!esValido);
        vista.setVisible(!esValido);
        if (esValido) {
            try {
                irAInicio(correo);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static boolean correoCorrecto(String correo, String contra) {
        BufferedReader fich = null;
        String linea;
        boolean existeCombo = false;
        try {
            fich = new BufferedReader(new InputStreamReader(new FileInputStream("clientes.txt")));
            linea = fich.readLine();
            while (linea != null && !existeCombo) {
                existeCombo = linea.equals(correo + ":" + contra);
                linea = fich.readLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        } finally {
            if (fich != null) {
                try {
                    fich.close();
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        }

        return existeCombo;
    }

}
