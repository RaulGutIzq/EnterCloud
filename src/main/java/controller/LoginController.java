package controller;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;
import model.Cliente;
import model.ClientesDAO;
import view.Inicio;
import view.Login;

/**
 * Controlador para manejar la lógica de la pantalla de inicio de sesión.
 * Gestiona las interacciones entre la vista `Login` y el modelo `ClientesDAO`.
 * Permite validar credenciales, manejar errores, y redirigir al usuario al
 * inicio si las credenciales son correctas.
 *
 * @author CDC
 */
public class LoginController {

    private static final String RAIZBUCKET = "E:/DAM2/DI"; // Ruta raíz para almacenar datos del usuario.
    private ClientesDAO cliente; // Modelo para interactuar con los datos de clientes.
    private Login vista; // Vista para la pantalla de inicio de sesión.

    /**
     * Constructor que inicializa el controlador con el modelo y la vista.
     * Configura los eventos de interacción con la vista.
     *
     * @param cliente Instancia del modelo de datos de clientes.
     * @param vista Vista de inicio de sesión.
     */
    public LoginController(ClientesDAO cliente, Login vista) {
        this.cliente = cliente;
        this.vista = vista;

        // Configuración de eventos de la vista
        vista.jPanel1.setFocusable(true);
        vista.jPanel1.requestFocusInWindow();

        vista.userForm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vista.userForm.setText("");
            }
        });

        vista.passForm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vista.passForm.setText("");
            }
        });

        vista.jButton1.addActionListener(evt -> botonLoginActionPerformed(evt));
        vista.getRootPane().setDefaultButton(vista.jButton1); // Define el botón de inicio de sesión como predeterminado.
    }

    /**
     * Valida las credenciales del usuario y redirige a la ventana de inicio si
     * son correctas.
     *
     * @param correo Correo del usuario ingresado en el formulario.
     * @throws IOException Si ocurre un error al acceder a los datos del
     * cliente.
     */
    private void irAInicio(String correo) throws IOException {
        Cliente c = null;
        ClientesDAO fich = null;
        try {
            fich = new ClientesDAO("Clientes.dat", "r");
            try {
                while (true) {  // Busca al cliente en el archivo de datos.
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
            // Crea el directorio raíz del usuario si no existe.
            File raizUser = new File((RAIZBUCKET + "/" + c.getId()).replace('/', File.separatorChar));
            if (!raizUser.exists()) {
                if (!raizUser.mkdir()) {
                    throw new IOException("No se ha podido crear la raiz del usuario: " + c.getId());
                }
            }

            // Redirige a la pantalla de inicio.
            Inicio inicio = new Inicio();
            new InicioController(inicio, c);
            inicio.setVisible(true);
            this.vista.setVisible(false);
        } else {
            System.err.println("No se ha encontrado el cliente: " + correo + " en Clientes.dat");
        }
    }

    private void registroLogin(String usuario) {
    try (FileWriter writer = new FileWriter("log.txt", true)) { // 'true' para añadir al archivo sin sobrescribir
        GregorianCalendar calendario = new GregorianCalendar();
        int anio = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH) + 1; // Los meses empiezan en 0, por eso se suma 1
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minuto = calendario.get(Calendar.MINUTE);
        int segundo = calendario.get(Calendar.SECOND);

        String log = String.format("El usuario %s ha accedido el %d/%d/%d a las %02d:%02d:%02d%n", 
                                    usuario, dia, mes, anio, hora, minuto, segundo);
        writer.write(log);  // Escribir la línea en el archivo
    } catch (IOException e) {
        e.printStackTrace();  // Manejar error de escritura
    }
}
    
    /**
     * Maneja la acción del botón de inicio de sesión. Valida las credenciales y
     * redirige al usuario o muestra un mensaje de error.
     *
     * @param evt Evento de acción del botón.
     */
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

        vista.mensajeError.setVisible(!esValido); // Muestra un mensaje de error si las credenciales no son válidas.
        vista.setVisible(!esValido);

        if (esValido) {
            try {
                irAInicio(correo);
                registroLogin(correo);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Valida si las credenciales ingresadas (correo y contraseña) coinciden con
     * las almacenadas.
     *
     * @param correo Correo ingresado por el usuario.
     * @param contra Contraseña ingresada por el usuario.
     * @return `true` si las credenciales son correctas, de lo contrario
     * `false`.
     */
    private static boolean correoCorrecto(String correo, String contra) {
        BufferedReader fich = null;
        String linea;
        boolean existeCombo = false;

        try {
            fich = new BufferedReader(new InputStreamReader(new FileInputStream("clientes.txt")));
            linea = fich.readLine();

            while (linea != null && !existeCombo) {
                existeCombo = linea.equals(correo + ":" + contra); // Compara correo y contraseña.
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
