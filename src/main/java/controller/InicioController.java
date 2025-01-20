package controller;

import java.awt.Cursor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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
import model.ClientesDAO;
import view.Ayuda;
import view.CustomRenderer;
import view.Inicio;
import view.Login;

/**
 *
 * @author Daniel Fraile Leon
 */
public class InicioController {

    private static final String RAIZBUCKET = "E:/DAM2/DI";
    private Inicio vista;
    private String dirActual;

    public InicioController(Inicio vista, Cliente c) {
        this.vista = vista;
        dirActual = (RAIZBUCKET + "/" + c.getId()).replace('/', File.separatorChar);
        listarArchivos(dirActual);
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
                Ayuda a = new Ayuda();
                a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                a.setVisible(true);
            }

        });

        vista.botonAyuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Ayuda a = new Ayuda();
                a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                a.setVisible(true);
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
            System.out.println("ActionCommand: " + actionCommand); // Debug del comando
            if (vista.paginaSubir.getTitle().equals("Subir")) {
                if (actionCommand.equals(vista.jFileChooser2.APPROVE_SELECTION)) {
                    // Obtenemos los archivos seleccionados
                    File[] selectedFiles = vista.jFileChooser2.getSelectedFiles();
                    if (selectedFiles == null || selectedFiles.length == 0) {
                        // Si no se seleccionaron múltiples, intentamos con uno
                        File selectedFile = vista.jFileChooser2.getSelectedFile();
                        if (selectedFile != null) {
                            selectedFiles = new File[]{selectedFile};
                        }
                    }

                    // Si hay archivos seleccionados, intentamos subirlos
                    if (selectedFiles != null && selectedFiles.length > 0) {
                        subirArchivos(selectedFiles);
                        try (FileWriter writer = new FileWriter("log.txt", true)) {
                            GregorianCalendar calendario = new GregorianCalendar();
                            int anio = calendario.get(Calendar.YEAR);
                            int mes = calendario.get(Calendar.MONTH) + 1;
                            int dia = calendario.get(Calendar.DAY_OF_MONTH);
                            int hora = calendario.get(Calendar.HOUR_OF_DAY);
                            int minuto = calendario.get(Calendar.MINUTE);
                            int segundo = calendario.get(Calendar.SECOND);

                            for (File file : selectedFiles) {
                                String log = String.format("Se ha subido el fichero '%s' el %d/%d/%d a las %02d:%02d:%02d%n",
                                        file.getName(), dia, mes, anio, hora, minuto, segundo);
                                writer.write(log);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    // Ocultamos el panel después de procesar
                } else if (actionCommand.equals(vista.jFileChooser2.CANCEL_SELECTION)) {
                    System.out.println("Selección cancelada.");
                    // Solo ocultamos el panel, sin ninguna otra acción
                }
            } else if (vista.paginaSubir.getTitle().equals("Descargar")) {
                if (actionCommand.equals(vista.jFileChooser2.APPROVE_SELECTION)) {
                    File archSel = new File(dirActual + File.separatorChar + vista.jList1.getSelectedValue().split(" - ")[0]);
                    File destino = vista.jFileChooser2.getSelectedFile(); // Directorio destino

                    if (destino.isDirectory()) {
                        try {
                            // Copiar archivo al directorio seleccionado
                            Files.copy(Path.of(archSel.getAbsolutePath()),
                                    Path.of(destino.getAbsolutePath() + File.separatorChar + archSel.getName()),
                                    StandardCopyOption.REPLACE_EXISTING);
                            JOptionPane.showMessageDialog(vista, "Archivo descargado correctamente en: " + destino.getAbsolutePath(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            try (FileWriter writer = new FileWriter("log.txt", true)) {
                                GregorianCalendar calendario = new GregorianCalendar();
                                int anio = calendario.get(Calendar.YEAR);
                                int mes = calendario.get(Calendar.MONTH) + 1;
                                int dia = calendario.get(Calendar.DAY_OF_MONTH);
                                int hora = calendario.get(Calendar.HOUR_OF_DAY);
                                int minuto = calendario.get(Calendar.MINUTE);
                                int segundo = calendario.get(Calendar.SECOND);

                                String log = String.format("Se ha descargado el fichero '%s' el %d/%d/%d a las %02d:%02d:%02d en el directorio '%s'%n",
                                        archSel.getName(), dia, mes, anio, hora, minuto, segundo, destino.getAbsolutePath());
                                writer.write(log);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(vista, "Error al descargar el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(vista, "Por favor, selecciona un directorio válido.", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } else if (actionCommand.equals(vista.jFileChooser2.CANCEL_SELECTION)) {
                    System.out.println("Descarga cancelada por el usuario.");
                }
            }

            // Ocultar el diálogo del JFileChooser
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

    private void listarArchivos(String dir) {
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

    private String formatSize(long size) {
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

    private void subirArchivos(File[] selectedFiles) {
        for (File arch : selectedFiles) {
            System.out.println(arch.getName());
        }
        for (File arch : selectedFiles) {
            try {
                Files.copy(Path.of(arch.getAbsolutePath()), Path.of(this.dirActual + File.separator + arch.getName()), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.listarArchivos(dirActual);
    }

    private String calcAlmacenamiento() {
        File dir = new File(dirActual);
        while (!dir.getParentFile().getAbsolutePath().equals(RAIZBUCKET.replace('/', File.separatorChar))) {
            dir = dir.getParentFile();
        }
        long espacioOcupado = longDir(dir); // Obtenemos el tamaño de todos los archivos dentro del directorio
        return String.format("%.2f", espacioOcupado / (1024.0 * 1024.0 * 1024.0)); // Convertir bytes a GB
    }

    private long longDir(File dir) {
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

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {
        // Verificamos si el método se ejecuta correctamente
        System.out.println("Botón de menú clickeado");

        if (vista.menuUsuario.isVisible()) {
            vista.menuUsuario.setVisible(false);
        } else {
            mostrarMenuDesplegable();
        }
    }

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

    private void mostrarMenuDesplegable() {
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

    private void menuUsuarioFocusLost(java.awt.event.FocusEvent evt) {
        // TODO add your handling code here:
        vista.menuUsuario.setVisible(false);
    }

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            ClientesDAO model = new ClientesDAO("Clientes.dat", "r");
            Login view = new Login();
            LoginController controlador = new LoginController(model, view);
            view.setVisible(true);
            if (vista.menuUsuario.isVisible()) {
                vista.menuUsuario.setVisible(false);
            }
            this.vista.dispose();

        } catch (FileNotFoundException ex) {
            System.out.println("Fichero Clientes.dat no encontrado.");
        }
    }

    public void listaObjetosLocales() {
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

    private boolean puedeEliminar(File archivo) {
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
}
