package controller;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.awt.Component;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import view.CustomRenderer;
import view.Inicio;

/**
 *
 * @author Daniel Fraile Leon
 */
public class InicioController {

    private Inicio vista;
    private String dirActual;

    public InicioController(Inicio vista) {
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
        listaObjetosLocales();

        vista.jList1.setCellRenderer(new CustomRenderer());

// Agregar el MouseListener para detectar clics en el texto y el botón
vista.jList1.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        JList<String> list = (JList<String>) evt.getSource();
        int index = list.locationToIndex(evt.getPoint()); // Obtén el índice del elemento clicado

        if (index >= 0) {
            String item = list.getModel().getElementAt(index);
            System.out.println("Elemento clicado: " + item);

            // Verificar si se ha hecho clic en el botón o en el texto
            int x = evt.getX(); // Coordenada X del clic
            int y = evt.getY(); // Coordenada Y del clic

            // Obtener el componente renderizado en esa posición
            Component component = list.getCellRenderer().getListCellRendererComponent(list, item, index, false, false);

            // Comprobar si el clic está dentro de la zona del botón
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;

                // La región donde se encuentra el botón se encuentra en el BorderLayout.EAST
                Rectangle buttonBounds = panel.getComponent(1).getBounds(); // El segundo componente es el botón (en BorderLayout.EAST)

                // Verificar si el clic ocurrió dentro del área del botón
                if (buttonBounds.contains(x, y)) {
                    System.out.println("Botón clicado para el elemento: " + item);
                } else {
                    System.out.println("Texto clicado: " + item);
                }
            }
        }
    }
});

        vista.botonMenuSuperior.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //abrir menu desplegable
            }
        });

        vista.botonFavoritos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //Ir a favoritos
            }
        });

        vista.botonSubir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (vista.paginaSubir.isVisible()) {
                    vista.paginaSubir.setVisible(false);
                } else {
                    vista.paginaSubir.setVisible(true);
                }
            }
        });

        vista.jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() == 2) {
                    /*
            jDialog1.setVisible(true);
            jLabel2.setText(jList1.getSelectedValue());
                     */
                }
            }
        });

        vista.botonInicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vista.dispose();
                Inicio inicio = new Inicio();
                new InicioController(inicio);
                inicio.setVisible(true);
            }
        });
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

    private void subirArchivos(File[] selectedFiles) {
        for (File selectedFile : selectedFiles) {
            try {
                Files.copy(Path.of(selectedFile.getAbsolutePath()), Path.of(dirActual + "/".replace('/', File.separatorChar) + selectedFile.getName()), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

}
