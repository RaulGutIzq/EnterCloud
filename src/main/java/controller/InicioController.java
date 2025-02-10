package controller;

import java.awt.Cursor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import model.Cliente;
import model.DatabaseConnection;
import view.AyudaDialog;
import view.CustomRenderer;
import view.Inicio;
import view.Login;

/**
 * Controlador de la vista principal que maneja la lógica de negocio para el
 * manejo de archivos y la interacción con el usuario. Proporciona
 * funcionalidades como cargar archivos, subir, descargar, eliminar, y mostrar
 * el almacenamiento disponible.
 *
 * @author Daniel Fraile Leon
 */
public class InicioController {

    private static final String RAIZBUCKET = "E:/DAM2/DI";
    private Inicio vista;
    private String dirActual;
    private Cliente clienteActual;

    /**
     * Constructor del controlador de la vista principal.
     *
     * @param vista Vista principal de la aplicación.
     * @param c Cliente actual que realiza las operaciones.
     */
    public InicioController(Inicio vista, Cliente c) {
        this.vista = vista;
        dirActual = (RAIZBUCKET + "/" + c.getId()).replace('/', File.separatorChar);
        listarArchivos(dirActual);
        this.clienteActual = c;
        vista.clientId = clienteActual.getId();
        vista.jList1.setCellRenderer(new CustomRenderer());
        vista.barraProg.setStringPainted(true);

        vista.jPanel1.setFocusable(true); // Asegurarte de que el panel sea enfocable
        vista.jPanel1.requestFocusInWindow(); // Solicitar el foco para el panel
        vista.jPanel1.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1,
                        0), "showHelp");

        vista.jPanel1.getActionMap().put("showHelp", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                AyudaDialog a = new AyudaDialog(vista);
                a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                a.setVisible(true);
            }

        });

        vista.botonAyuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Usa "vista" como la ventana padre (asumiendo que "vista" es una instancia de Inicio)
                AyudaDialog dialog = new AyudaDialog(vista); // "vista" es la instancia de Inicio
                dialog.setVisible(true);
            }
        });

        vista.btnMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //abrir menu desplegable
            }
        });

        vista.botonFavoritos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //Ir a favoritos
            }
        });
        // Agregar el MouseListener para detectar clics en el texto y el botón
        vista.jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //@TODO: Gestionar java.nio.file.FileAlreadyExistsException
                if (evt.getClickCount() == 2) {
                    vista.paginaSubir.setTitle("Descargar");
                    vista.jFileChooser2.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
                    vista.jFileChooser2.setSelectedFile(null);  // Limpiar la selección previa
                    vista.jFileChooser2.setCurrentDirectory(new File(System.getProperty("user.home")));  // Establecer un directorio por defecto
                    vista.jFileChooser2.setDialogType(JFileChooser.SAVE_DIALOG);
                    vista.paginaSubir.setVisible(true);
                }
            }
        });// Configuración de la lista y el hover
        vista.jList1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                JList<?> list = (JList<?>) e.getSource();
                int index = list.locationToIndex(e.getPoint()); // Obtener índice del elemento bajo el ratón

                if (index != -1) {
                    // Cambiar a cursor de mano si está sobre un elemento
                    list.setCursor(new Cursor(Cursor.HAND_CURSOR));

                    // Actualizar el índice hovered en el renderer
                    CustomRenderer renderer = (CustomRenderer) list.getCellRenderer();
                    renderer.setHoveredIndex(index);
                } else {
                    // Restaurar el cursor predeterminado si no está sobre un elemento
                    list.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }

                // Forzar el repintado para aplicar los cambios
                list.repaint();
            }
        });

        vista.jList1.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_DELETE) {
                    int selectedIndex = vista.jList1.getSelectedIndex();

                    if (selectedIndex >= 0) {
                        // Obtener el nombre del archivo seleccionado
                        String selectedFileName = vista.jList1.getModel().getElementAt(selectedIndex);
                        File fileToDelete = new File(dirActual + File.separator + selectedFileName.split(" - ")[0]); // Quitar el tamaño

                        if (puedeEliminar(fileToDelete)) {
                            int confirmation = JOptionPane.showConfirmDialog(
                                    vista,
                                    "¿Está seguro de que desea eliminar el archivo: '" + selectedFileName + "'?",
                                    "Confirmar eliminación",
                                    JOptionPane.YES_NO_OPTION
                            );

                            if (confirmation == JOptionPane.YES_OPTION) {
                                if (fileToDelete.delete()) {
                                    // Refrescar la lista de archivos
                                    listarArchivos(dirActual);

                                    // Notificar al usuario
                                    JOptionPane.showMessageDialog(vista,
                                            "Archivo eliminado exitosamente.",
                                            "Eliminación completada",
                                            JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(vista,
                                            "No se pudo eliminar el archivo. Verifique si está en uso o tiene permisos suficientes.",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(vista,
                                "No hay ningún elemento seleccionado para eliminar.",
                                "Error",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        vista.botonSubir.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Antes de abrir el JFileChooser, restablecer el directorio y la selección
                vista.paginaSubir.setTitle("Subir");
                vista.jFileChooser2.setFileSelectionMode(javax.swing.JFileChooser.FILES_AND_DIRECTORIES);
                vista.jFileChooser2.setSelectedFile(null);  // Limpiar la selección previa
                vista.jFileChooser2.setCurrentDirectory(new File(System.getProperty("user.home")));  // Establecer un directorio por defecto
                vista.jFileChooser2.setDialogType(JFileChooser.OPEN_DIALOG);

                vista.paginaSubir.setVisible(true);
            }
        });

        vista.botonInicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vista.dispose();
                Inicio inicio = new Inicio();
                new InicioController(inicio, c);
                inicio.setVisible(true);
            }
        });

        vista.jFileChooser2.addActionListener(evt -> {
            String actionCommand = evt.getActionCommand();
            Cliente clienteActual = obtenerClienteActual();
            GregorianCalendar calendario = new GregorianCalendar();
            int anio = calendario.get(Calendar.YEAR);
            int mes = calendario.get(Calendar.MONTH) + 1;
            int dia = calendario.get(Calendar.DAY_OF_MONTH);
            int hora = calendario.get(Calendar.HOUR_OF_DAY);
            int minuto = calendario.get(Calendar.MINUTE);
            int segundo = calendario.get(Calendar.SECOND);

            String fechaHora = String.format("%d-%02d-%02d %02d:%02d:%02d", anio, mes, dia, hora, minuto, segundo);

            if (clienteActual != null) {
                if (vista.paginaSubir.getTitle().equals("Subir")) {
                    if (actionCommand.equals(vista.jFileChooser2.APPROVE_SELECTION)) {
                        File[] selectedFiles = vista.jFileChooser2.getSelectedFiles();
                        if (selectedFiles == null || selectedFiles.length == 0) {
                            File selectedFile = vista.jFileChooser2.getSelectedFile();
                            if (selectedFile != null) {
                                selectedFiles = new File[]{selectedFile};
                            }
                        }

                        if (selectedFiles != null && selectedFiles.length > 0) {
                            subirArchivos(selectedFiles);
                            registrarOperacionEnBD(clienteActual.getId(), selectedFiles, "subidas", fechaHora);
                        }
                    } else if (actionCommand.equals(vista.jFileChooser2.CANCEL_SELECTION)) {
                        System.out.println("Selección de subida cancelada.");
                    }
                } else if (vista.paginaSubir.getTitle().equals("Descargar")) {
                    if (actionCommand.equals(vista.jFileChooser2.APPROVE_SELECTION)) {
                        File archSel = new File(dirActual + File.separatorChar + vista.jList1.getSelectedValue().split(" - ")[0]);
                        File destino = vista.jFileChooser2.getSelectedFile();

                        if (destino.isDirectory()) {
                            try {
                                Files.copy(Path.of(archSel.getAbsolutePath()),
                                        Path.of(destino.getAbsolutePath() + File.separatorChar + archSel.getName()),
                                        StandardCopyOption.REPLACE_EXISTING);
                                JOptionPane.showMessageDialog(vista, "Archivo descargado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                                registrarOperacionEnBD(clienteActual.getId(), new File[]{archSel}, "descargas", fechaHora);
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(vista, "Error al descargar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(vista, "Selecciona un directorio válido.", "Error", JOptionPane.WARNING_MESSAGE);
                        }
                    } else if (actionCommand.equals(vista.jFileChooser2.CANCEL_SELECTION)) {
                        System.out.println("Descarga cancelada.");
                    }
                }
            } else {
                System.out.println("Error: No se pudo obtener el cliente actual.");
            }
            this.listarArchivos(dirActual);
            vista.paginaSubir.setVisible(false);
        });

        vista.menuUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                menuUsuarioFocusLost(evt);
            }
        });

        vista.btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });
        vista.btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });
    }

    /**
     * Lista los archivos en el directorio especificado y actualiza la vista.
     *
     * @param dir El directorio cuyo contenido se listará.
     */
    protected void listarArchivos(String dir) {
        List<String> ficheros = new ArrayList<>();
        File[] listaArchivos = new File(dir).listFiles();

        if (listaArchivos != null) {
            for (File archivo : listaArchivos) {
                String nombre = archivo.getName();
                String tamano = archivo.isDirectory() ? "[Carpeta]" : formatSize(archivo.length());
                ficheros.add(nombre + " - " + tamano);
            }
        }

        String[] listaNombresArchivos = ficheros.toArray(new String[0]);

        // Modifico código para incluir tamaño en la lista
        vista.jList1.setModel(new javax.swing.AbstractListModel<String>() {
            public int getSize() {
                return listaNombresArchivos.length;
            }

            public String getElementAt(int i) {
                return listaNombresArchivos[i];
            }
        });
    }

    /**
     * Formatea el tamaño de un archivo en una cadena legible (en bytes, KB, MB,
     * GB).
     *
     * @param size El tamaño del archivo en bytes.
     * @return El tamaño formateado en una cadena.
     */
    protected String formatSize(long size) {
        if (size < 1024) {
            return size + " B";
        } else if (size < 1024 * 1024) {
            return String.format("%.2f KB", size / 1024.0);
        } else if (size < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", size / (1024.0 * 1024.0));
        } else {
            return String.format("%.2f GB", size / (1024.0 * 1024.0 * 1024.0));
        }
    }

    /**
     * Realiza la subida de los archivos seleccionados al directorio actual.
     *
     * @param archivos Los archivos seleccionados para subir.
     */
    public void subirArchivos(File[] archivos) {
        for (File archivo : archivos) {
            // Ruta de destino
            File destino = new File(dirActual + File.separator + archivo.getName());

            // Asegurarse de que el directorio de destino existe
            if (!destino.getParentFile().exists()) {
                // Crear el directorio si no existe
                boolean directorioCreado = destino.getParentFile().mkdirs();  // mkdirs crea todos los directorios necesarios
                if (directorioCreado) {
                    System.out.println("Directorio creado: " + destino.getParentFile());
                } else {
                    System.out.println("No se pudo crear el directorio: " + destino.getParentFile());
                }
            }

            // Copiar el archivo
            try {
                Files.copy(archivo.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Archivo copiado con éxito: " + archivo.getName());
            } catch (IOException e) {
                System.out.println("Error al copiar el archivo: " + archivo.getName());
                e.printStackTrace();
            }
        }
        System.out.println("pon");
    }

    /**
     * Calcula el espacio de almacenamiento ocupado en el directorio raíz del
     * bucket.
     *
     * Este método recorre recursivamente los directorios y calcula el tamaño
     * total de los archivos en el directorio raíz del bucket, luego lo
     * convierte a gigabytes y lo devuelve como una cadena de texto.
     *
     * @return Una cadena representando el almacenamiento ocupado en GB.
     */
    protected String calcAlmacenamiento() {
        File dir = new File(dirActual);
        while (!dir.getParentFile().getAbsolutePath().equals(RAIZBUCKET.replace('/', File.separatorChar))) {
            dir = dir.getParentFile();
        }
        long espacioOcupado = longDir(dir); // Obtenemos el tamaño de todos los archivos dentro del directorio
        return String.format("%.2f", espacioOcupado / (1024.0 * 1024.0 * 1024.0)); // Convertir bytes a GB
    }

    /**
     *
     * @param usado
     * @param total
     */
    public void actualizarAlmacenamiento(int usado, int total) {
        int porcentaje = (int) ((usado / (double) total) * 100);
        vista.barraProg.setValue(porcentaje);
        vista.barraProg.setStringPainted(true); // Asegúrate de que se muestre el porcentaje como texto
        vista.lblAlmacenamiento.setText("Almacenamiento: " + usado + "MB / " + total + "MB");
        vista.barraProg.repaint();
    }

    private void actualizarBarraProgreso(int almacenamientoOcupado, int capacidadTotal) {
        int porcentaje = (int) ((almacenamientoOcupado / (double) capacidadTotal) * 100);

        if (porcentaje < 0 || porcentaje > 100) {
            System.err.println("Error: Porcentaje inválido: " + porcentaje); // Debug
            return;
        }

        vista.barraProg.setValue(porcentaje);
        vista.barraProg.setString(porcentaje + "% ocupado");
        vista.barraProg.repaint();
    }

    /**
     * Calcula el tamaño total de un directorio y sus subdirectorios de manera
     * recursiva.
     *
     * Este método recorre un directorio y, si encuentra subdirectorios, los
     * explora de manera recursiva, sumando el tamaño de todos los archivos
     * encontrados.
     *
     * @param dir El directorio cuyo tamaño se desea calcular.
     * @return El tamaño total del directorio y sus archivos en bytes.
     */
    protected long longDir(File dir) {
        long tam = 0;
        if (dir.isDirectory()) {
            for (File arch : dir.listFiles()) {
                tam += longDir(arch); // Recursión para explorar subdirectorios
            }
        } else {
            tam = dir.length(); // Si es un archivo, simplemente sumar su tamaño
        }
        return tam;
    }

    /**
     * Maneja la acción de hacer clic en el botón del menú.
     *
     * Este método verifica si el menú está visible o no. Si el menú está
     * visible, lo oculta; si no lo está, lo muestra. Esto se usa para alternar
     * la visibilidad del menú desplegable del usuario.
     *
     * @param evt El evento de acción asociado al clic en el botón.
     */
    protected void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {
        // Verificamos si el método se ejecuta correctamente
        System.out.println("Botón de menú clickeado");

        if (vista.menuUsuario.isVisible()) {
            vista.menuUsuario.setVisible(false);
        } else {
            mostrarMenuDesplegable();
        }
    }

    /**
     * Muestra el menú desplegable y actualiza la información de almacenamiento
     * disponible.
     *
     * Este método calcula el almacenamiento ocupado y disponible, y luego
     * actualiza la etiqueta de almacenamiento en la vista. A continuación,
     * muestra el menú de usuario.
     */
    protected void mostrarMenuDesplegable() {
        System.out.println("Mostrando menú desplegable...");

        // Verificamos el valor calculado del almacenamiento
        String almacenamientoOcupado = calcAlmacenamiento();
        System.out.println("Almacenamiento ocupado calculado: " + almacenamientoOcupado);

        // Reemplazamos la coma por un punto si es necesario
        almacenamientoOcupado = almacenamientoOcupado.replace(",", ".");

        try {
            double almacenamientoTotal = 50.0; // Total en GB (50 GB)
            double almacenamientoDisponible = almacenamientoTotal - Double.parseDouble(almacenamientoOcupado);

            // Verificamos los valores calculados
            System.out.println(String.format("Almacenamiento ocupado: %s, Total: %.2fGB, Disponible: %.2fGB", almacenamientoOcupado, almacenamientoTotal, almacenamientoDisponible));

            // Actualizamos el texto del almacenamiento
            vista.lblAlmacenamiento.setText(String.format("Almacenamiento: %s / %.2fGB (%.2fGB disponibles)", almacenamientoOcupado, almacenamientoTotal, almacenamientoDisponible));

            int porcentaje = (int) ((Double.parseDouble(almacenamientoOcupado) / almacenamientoTotal) * 100);
            actualizarBarraProgreso((int) (Double.parseDouble(almacenamientoOcupado) * 1024), (int) (almacenamientoTotal * 1024));
            // Mostramos el menú
            vista.menuUsuario.setVisible(true);
        } catch (NumberFormatException e) {
            // Imprime el error si no puede parsear el valor
            System.err.println("Error al parsear el almacenamiento ocupado: " + almacenamientoOcupado);
            e.printStackTrace();
        }
    }

    /**
     * Maneja el evento cuando el menú de usuario pierde el foco.
     *
     * Este método oculta el menú de usuario cuando se detecta que el foco ha
     * sido perdido, lo que significa que el usuario ya no está interactuando
     * con él.
     *
     * @param evt El evento de foco perdido.
     */
    protected void menuUsuarioFocusLost(java.awt.event.FocusEvent evt) {
        // TODO add your handling code here:
        vista.menuUsuario.setVisible(false);
    }

    /**
     * Maneja el evento de acción para cerrar sesión.
     *
     * Este método cierra la sesión del usuario actual y muestra la vista de
     * login. Además, se asegura de que el menú de usuario esté oculto antes de
     * cerrar la ventana.
     *
     * @param evt El evento de acción asociado al clic en el botón de cerrar
     * sesión.
     */
    protected void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            // Crear la vista y el controlador para la pantalla de login
            Login view = new Login();
            LoginController controlador = new LoginController(view);  // No es necesario pasar el modelo ClientesDAO

            // Mostrar la vista de login
            view.setVisible(true);

            // Verificar si el menú de usuario está visible y ocultarlo
            if (vista.menuUsuario.isVisible()) {
                vista.menuUsuario.setVisible(false);
            }

            // Cerrar la vista actual (la que contiene el menú del usuario)
            this.vista.dispose();

        } catch (Exception ex) {
            System.out.println("Error al cerrar sesión: " + ex.getMessage());
        }
    }

    /**
     * Lista los archivos y carpetas en el directorio raíz de la unidad C:.
     *
     * Este método obtiene una lista de archivos y carpetas en el directorio
     * raíz de la unidad C: y la muestra en la vista de la interfaz de usuario.
     */
    protected void listaObjetosLocales() {
        File[] ficherosFile = new File("C:/").listFiles();
        String[] ficheros = new String[ficherosFile.length];
        for (int i = 0; i < ficherosFile.length; i++) {
            ficheros[i] = ficherosFile[i].getName();
        }
        vista.jList1.setModel(new javax.swing.AbstractListModel<String>() {
            public int getSize() {

                return ficheros.length;

            }

            public String getElementAt(int i) {
                return ficheros[i];
            }
        });
    }
// Método auxiliar para registrar las operaciones en la base de datos

    private void registrarOperacionEnBD(int idCliente, File[] archivos, String tabla, String fechaHora) {
        String insertSql = String.format("INSERT INTO %s (idCliente, fechaHoraLogin, nombreFichero) VALUES (?, ?, ?)", tabla);
        try (Connection conn = DatabaseConnection.connect(); PreparedStatement stmt = conn.prepareStatement(insertSql)) {
            for (File archivo : archivos) {
                stmt.setInt(1, idCliente);
                stmt.setString(2, fechaHora);
                stmt.setString(3, archivo.getName());
                stmt.executeUpdate();
            }
            System.out.println("Operación registrada correctamente en la tabla: " + tabla);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifica si el archivo puede ser eliminado.
     *
     * @param archivo El archivo que se desea verificar.
     * @return true si el archivo puede ser eliminado, false si no.
     */
    protected boolean puedeEliminar(File archivo) {
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(vista, "El archivo no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Intentar renombrar el archivo como prueba de que no está en uso
        File archivoTemporal = new File(archivo.getAbsolutePath() + ".tmp");
        boolean sePuedeRenombrar = archivo.renameTo(archivoTemporal);
        if (sePuedeRenombrar) {
            archivoTemporal.renameTo(archivo); // Restaurar el nombre original
            return true;
        } else {
            JOptionPane.showMessageDialog(vista, "El archivo está en uso por otra aplicación o no tienes permisos.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    /**
     *
     * @return
     */
    public Cliente obtenerClienteActual() {
        return this.clienteActual;  // Devuelve el cliente que fue asignado en el constructor
    }

}
