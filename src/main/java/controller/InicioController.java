package controller;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

        /*
        To do:
        - Aqui se sustituira listaObjetosLocales() por el metodo que listara la raiz del bucket del user
        - Hacer funcional el boton de los ficheros para que se pueda descargar (usar downloadObject())
        - Hacer escuchador de los elementos de la lista para que, si es un directorio
            inicie de nuevo inicio pero listando con la ruta de ese directorio en el bucket
        - Hacer funcional el boton del panel superior para que depliegue el menu
            - ver almacenamiento disponible
            - logout
        - Modificar metodo para hacer funcional metodoSubir
         */
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

        // Agregar el MouseListener para detectar clics en el texto y el botón
        vista.jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JList<String> list = (JList<String>) evt.getSource();
                int index = list.locationToIndex(evt.getPoint()); // Índice del elemento clicado

                if (index >= 0) {
                    if (evt.getClickCount() == 1) {
                        // Lógica para clic simple
                        list.setSelectedIndex(index); // Seleccionar el elemento
                        System.out.println("Elemento seleccionado: " + list.getModel().getElementAt(index));
                    } else if (evt.getClickCount() == 2) {
                        // Lógica para doble clic
                        String selectedFileName = list.getModel().getElementAt(index);

                        File sourceFile = new File(dirActual + File.separator + selectedFileName);
                        if (!sourceFile.exists()) {
                            JOptionPane.showMessageDialog(vista,
                                    "El archivo no existe en el directorio actual",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        vista.jFileChooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                        int result = vista.jFileChooser2.showOpenDialog(vista);
                        if (result == JFileChooser.APPROVE_OPTION) {
                            File selectedDirectory = vista.jFileChooser2.getSelectedFile();
                            File destFile = new File(selectedDirectory, selectedFileName);

                            try {
                                copyFile(sourceFile, destFile);
                                JOptionPane.showMessageDialog(vista,
                                        "Archivo copiado exitosamente a: " + selectedDirectory.getAbsolutePath(),
                                        "Descarga completada",
                                        JOptionPane.INFORMATION_MESSAGE);
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(vista,
                                        "Error al copiar el archivo: " + ex.getMessage(),
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            System.out.println("Selección de directorio cancelada.");
                        }
                    }
                }
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



// Configuración de la lista y el hover

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

        vista.botonFavoritos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //Ir a favoritos
            }
        });

        vista.botonSubir.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Antes de abrir el JFileChooser, restablecer el directorio y la selección
                vista.jFileChooser2.setSelectedFile(null);  // Limpiar la selección previa
                vista.jFileChooser2.setCurrentDirectory(new File(System.getProperty("user.home")));  // Establecer un directorio por defecto

                if (vista.paginaSubir.isVisible()) {
                    vista.paginaSubir.setVisible(false);
                } else {
                    vista.paginaSubir.setVisible(true);
                }
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

    /*
    metodo descargar 
    IMPLEMENTAR CON CLOUD
    TRABAJAR CON LOS FAVORITOS
     */
    private void subidaArchivoActionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getActionCommand().equals("ApproveSelection")) {
            subirArchivos(vista.jFileChooser2.getSelectedFiles());
        }
        vista.paginaSubir.setVisible(false);
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

    public static void downloadObject(
            String projectId, String bucketName, String objectName, String destFilePath) {
        // The ID of your GCP project
        // String projectId = "your-project-id";

        // The ID of your GCS bucket
        // String bucketName = "your-unique-bucket-name";
        // The ID of your GCS object
        // String objectName = "your-object-name";
        // The path to which the file should be downloaded
        // String destFilePath = "/local/path/to/file.txt";
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();

        Blob blob = storage.get(BlobId.of(bucketName, objectName));
        blob.downloadTo(Paths.get(destFilePath));

        System.out.println(
                "Downloaded object "
                + objectName
                + " from bucket name "
                + bucketName
                + " to "
                + destFilePath);
    }

    public static Page<Blob> listObjectsWithPrefix(String projectId, String bucketName, String directoryPrefix) {
        // The ID of your GCP project
        // String projectId = "your-project-id";

        // The ID of your GCS bucket
        // String bucketName = "your-unique-bucket-name";
        // The directory prefix to search for
        // String directoryPrefix = "myDirectory/"
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        /**
         * Using the Storage.BlobListOption.currentDirectory() option here
         * causes the results to display in a "directory-like" mode, showing
         * what objects are in the directory you've specified, as well as what
         * other directories exist in that directory. For example, given these
         * blobs:
         *
         * <p>
         * a/1.txt a/b/2.txt a/b/3.txt
         *
         * <p>
         * If you specify prefix = "a/" and don't use
         * Storage.BlobListOption.currentDirectory(), you'll get back:
         *
         * <p>
         * a/1.txt a/b/2.txt a/b/3.txt
         *
         * <p>
         * However, if you specify prefix = "a/" and do use
         * Storage.BlobListOption.currentDirectory(), you'll get back:
         *
         * <p>
         * a/1.txt a/b/
         *
         * <p>
         * Because a/1.txt is the only file in the a/ directory and a/b/ is a
         * directory inside the /a/ directory.
         */
        Page<Blob> blobs
                = storage.list(
                        bucketName,
                        Storage.BlobListOption.prefix(directoryPrefix),
                        Storage.BlobListOption.currentDirectory());
        return blobs;
    }

    public static void uploadObject(
            String projectId, String bucketName, String objectName, String filePath) throws IOException {
        // The ID of your GCP project
        // String projectId = "your-project-id";

        // The ID of your GCS bucket
        // String bucketName = "your-unique-bucket-name";
        // The ID of your GCS object
        // String objectName = "your-object-name";
        // The path to your file to upload
        // String filePath = "path/to/your/file"
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        // Optional: set a generation-match precondition to avoid potential race
        // conditions and data corruptions. The request returns a 412 error if the
        // preconditions are not met.
        Storage.BlobWriteOption precondition;
        if (storage.get(bucketName, objectName) == null) {
            // For a target object that does not yet exist, set the DoesNotExist precondition.
            // This will cause the request to fail if the object is created before the request runs.
            precondition = Storage.BlobWriteOption.doesNotExist();
        } else {
            // If the destination already exists in your bucket, instead set a generation-match
            // precondition. This will cause the request to fail if the existing object's generation
            // changes before the request runs.
            //storage.get(bucketName, objectName).getGeneration()
            precondition
                    = Storage.BlobWriteOption.generationMatch();
        }
        storage.createFrom(blobInfo, Paths.get(filePath)/*, precondition*/);

        System.out.println(
                "File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
    }

    public static Page<Blob> listObjects(String projectId, String bucketName) {
        // The ID of your GCP project
        // String projectId = "your-project-id";

        // The ID of your GCS bucket
        // String bucketName = "your-unique-bucket-name";
        //acceso servicio 
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        Page<Blob> blobs = (Page<Blob>) storage.list(bucketName);
        return blobs;
        /*for (Blob blob : blobs.iterateAll()) {
            System.out.println(blob.getName());
        }*/
    }

    //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
    public static void prepareEnvironment() throws IOException {
        String variable = "GOOGLE_APPLICATION_CREDENTIALS";
        String valor = new File("src\\main\\resources\\aerial-citron-437514-t6-a89b0b905843.json").getPath();
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "set", variable + "=" + valor).inheritIO();
        Process p = pb.start();
    }

    // Método para copiar un archivo de un directorio a otro
    private void copyFile(File sourceFile, File destFile) throws IOException {
        // Verificar si el archivo de destino ya existe, si no, se crea
        if (destFile.exists()) {
            int response = JOptionPane.showConfirmDialog(vista,
                    "El archivo ya existe en el destino. ¿Quieres reemplazarlo?",
                    "Confirmación",
                    JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.NO_OPTION) {
                return; // No hacer nada si el usuario decide no reemplazar
            }
        }

        // Copiar el archivo
        Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
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
