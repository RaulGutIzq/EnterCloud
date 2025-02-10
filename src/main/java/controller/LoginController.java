package controller;

import model.Cliente;
import model.DatabaseConnection;
import view.Inicio;
import view.Login;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * Controlador para manejar la lógica de la pantalla de inicio de sesión.
 * Gestiona las interacciones entre la vista `Login` y la base de datos. Permite
 * validar credenciales, manejar errores, y redirigir al usuario al inicio si
 * las credenciales son correctas.
 *
 * @author CDC
 */
public class LoginController {

    private static final String RAIZBUCKET = "E:/DAM2/DI"; // Ruta raíz para almacenar datos del usuario.
    private Login vista; // Vista para la pantalla de inicio de sesión.

    /**
     * Constructor que inicializa el controlador con la vista. Configura los
     * eventos de interacción con la vista.
     *
     * @param vista Vista de inicio de sesión.
     */
    public LoginController(Login vista) {
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
    protected void irAInicio(String correo) throws IOException {
        Cliente c = obtenerClientePorCorreo(correo);
        if (c != null) {
            // Crea el directorio raíz del usuario si no existe.
            File raizUser = new File((RAIZBUCKET + "/" + c.getId()).replace('/', File.separatorChar));
            if (!raizUser.exists()) {
                if (!raizUser.mkdir()) {
                    throw new IOException("No se ha podido crear la raiz del usuario: " + c.getId());
                }
            }

            // Verifica si la ventana de inicio ya está visible o no.
            if (this.vista instanceof Login) {
                // Si la vista actual es el login, podemos redirigir y crear el controlador de la ventana de inicio
                Inicio inicio = new Inicio();
                new InicioController(inicio, c);
                inicio.setVisible(true);

                // Escondemos la vista actual (login) sin crear una nueva instancia.
                this.vista.setVisible(false);
            }
        } else {
            System.err.println("No se ha encontrado el cliente: " + correo + " en la base de datos");
        }
    }

    /**
     * Consulta la base de datos para obtener los detalles del cliente por su
     * correo.
     *
     * @param correo Correo del cliente.
     * @return Cliente si se encuentra en la base de datos, de lo contrario
     * null.
     */
    private Cliente obtenerClientePorCorreo(String correo) {
        String sql = "SELECT Id, correo, telf_contacto, plan_id FROM clientes WHERE correo = ?";
        Cliente cliente = null;

        try (Connection conn = DatabaseConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, correo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("Id");
                String correoCliente = rs.getString("correo");
                String telf = rs.getString("telf_contacto");
                byte plan = rs.getByte("plan_id");

                cliente = new Cliente(id, correoCliente, telf, plan);
            }
        } catch (SQLException ex) {
            System.err.println("Error al consultar la base de datos: " + ex.getMessage());
        }

        return cliente;
    }

    /**
     *
     * @param usuario
     */
    protected void registroLogin(String usuario) {
        Cliente c = obtenerClientePorCorreo(usuario);

        if (c != null) {
            GregorianCalendar calendario = new GregorianCalendar();
            int anio = calendario.get(Calendar.YEAR);
            int mes = calendario.get(Calendar.MONTH) + 1; // Los meses empiezan en 0
            int dia = calendario.get(Calendar.DAY_OF_MONTH);
            int hora = calendario.get(Calendar.HOUR_OF_DAY);
            int minuto = calendario.get(Calendar.MINUTE);
            int segundo = calendario.get(Calendar.SECOND);

            String fechaHoraLogin = String.format("%d-%02d-%02d %02d:%02d:%02d", anio, mes, dia, hora, minuto, segundo);

            // Obtener la dirección IP pública
            String direccionIP = obtenerDireccionIPPublica();

            // Insertar el registro de login
            String insertSql = "INSERT INTO login (idCliente, fechaHoraLogin, direccionIP) VALUES (?, ?, ?)";

            try (Connection conn = DatabaseConnection.connect(); PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setInt(1, c.getId());
                insertStmt.setString(2, fechaHoraLogin);
                insertStmt.setString(3, direccionIP);

                int rowsAffected = insertStmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Login registrado correctamente.");
                } else {
                    System.out.println("Error: No se pudo registrar el login.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Error: Usuario no encontrado.");
        }
    }

    private String obtenerDireccionIPPublica() {
        try {
            URL url = new URL("http://checkip.amazonaws.com");
            try (Scanner scanner = new Scanner(url.openStream())) {
                return scanner.nextLine().trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Desconocida"; // Valor por defecto si no se puede obtener la IP
        }
    }

    /**
     * Maneja la acción del botón de inicio de sesión. Valida las credenciales y
     * redirige al usuario o muestra un mensaje de error.
     *
     * @param evt Evento de acción del botón.
     */
    protected void botonLoginActionPerformed(ActionEvent evt) {
        boolean esValido;
        String correo = null, pass;

        try {
            correo = vista.userForm.getText();
            pass = new String(vista.passForm.getPassword());
            esValido = correoCorrecto(correo, pass);  // Validamos en la base de datos
        } catch (NullPointerException e) {
            esValido = false;
        }

        vista.mensajeError.setVisible(!esValido); // Muestra un mensaje de error si las credenciales no son válidas.
        vista.setVisible(!esValido);

        if (esValido) {
            try {
                irAInicio(correo);  // Redirigir al usuario al inicio
                registroLogin(correo);  // Registrar el login en la base de datos
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Valida si las credenciales ingresadas (correo y contraseña) coinciden con
     * las almacenadas en la base de datos.
     *
     * @param correo Correo ingresado por el usuario.
     * @param contra Contraseña ingresada por el usuario.
     * @return `true` si las credenciales son correctas, de lo contrario
     * `false`.
     */
    protected static boolean correoCorrecto(String correo, String contra) {
        String sql = "SELECT correo, password FROM clientes WHERE correo = ? AND password = ?";
        boolean existeCombo = false;

        try (Connection conn = DatabaseConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, correo);
            pstmt.setString(2, contra);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                existeCombo = true;  // Si hay un resultado, las credenciales son correctas
            }

        } catch (SQLException ex) {
            System.out.println("Error al consultar la base de datos: " + ex.getMessage());
        }

        return existeCombo;
    }
}
