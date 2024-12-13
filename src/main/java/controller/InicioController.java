package controller;

import java.awt.Cursor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
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
                vista.paginaSubir.setTitle("Descargar");
                vista.jFileChooser2.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
                vista.jFileChooser2.setSelectedFile(null);  // Limpiar la selección previa
                vista.jFileChooser2.setCurrentDirectory(new File(System.getProperty("user.home")));  // Establecer un directorio por defecto
                vista.jFileChooser2.setDialogType(JFileChooser.SAVE_DIALOG);
                vista.paginaSubir.setVisible(true);
            }
        });// Configuración de la lista y el hover
        vista.jList1.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_DELETE) {
                    int selectedIndex = vista.jList1.getSelectedIndex();

                    if (selectedIndex >= 0) {
                        // Obtener el nombre del archivo seleccionado
                        String selectedFileName = vista.jList1.getModel().getElementAt(selectedIndex);
                        File fileToDelete = new File(dirActual + File.separator + selectedFileName);

                        int confirmation = JOptionPane.showConfirmDialog(
                                vista,
                                "¿Está seguro de que desea eliminar el archivo: '" + selectedFileName + "'?",
                                "Confirmar eliminación",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (confirmation == JOptionPane.YES_OPTION) {
                            if (fileToDelete.exists() && fileToDelete.delete()) {
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
                    }

                    // Ocultamos el panel después de procesar
                } else if (actionCommand.equals(vista.jFileChooser2.CANCEL_SELECTION)) {
                    System.out.println("Selección cancelada.");
                    // Solo ocultamos el panel, sin ninguna otra acción
                }
            } else {
                if (actionCommand.equals(vista.jFileChooser2.APPROVE_SELECTION)) {
                    File archSel = new File(dirActual + File.separatorChar + vista.jList1.getSelectedValue());
                    try {
                        Files.copy(Path.of(archSel.getAbsolutePath()),
                                Path.of(vista.jFileChooser2.getSelectedFile().getAbsolutePath() + File.separatorChar + archSel.getName()));
                    } catch (IOException ex) {
                        Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (actionCommand.equals(vista.jFileChooser2.CANCEL_SELECTION)) {
                    System.out.println("Selección cancelada.");
                }
            }

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
        for (int i = 0; i < listaArchivos.length; i++) {
            ficheros.add(listaArchivos[i].getName());
        }
        String[] listaNombresArchivos = new String[ficheros.size()];
        for (int i = 0; i < ficheros.size(); i++) {
            listaNombresArchivos[i] = ficheros.get(i);
        }
        //modifico codigo nativo
        vista.jList1.setModel(new javax.swing.AbstractListModel<String>() {
            //String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() {
                return listaNombresArchivos.length;
            }

            public String getElementAt(int i) {
                return listaNombresArchivos[i];
            }
        });
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
        System.err.println(dir.getAbsolutePath());
        System.err.println(longDir(dir));
        return String.format("%.2f", longDir(dir) / 1073741824f);
    }

    private long longDir(File dir) {
        long tam = 0;
        if (dir.isDirectory()) {
            for (File arch : dir.listFiles()) {
                tam = tam + longDir(arch);
            }
        } else {
            tam = dir.length();
        }
        return tam;
    }

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {
        if (vista.menuUsuario.isVisible()) {
            vista.menuUsuario.setVisible(false);
        } else {
            mostrarMenuDesplegable();
        }
    }

    private void mostrarMenuDesplegable() {
        vista.lblAlmacenamiento.setText("Almacenamiento: " + calcAlmacenamiento() + "/50GB");
        vista.menuUsuario.show(vista.getContentPane(), vista.btnMenu.getX(), vista.btnMenu.getY() + vista.btnMenu.getHeight());
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
}
