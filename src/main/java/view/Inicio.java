package view;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.awt.Dimension;
import java.awt.Image;
import java.util.List;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JScrollBar;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

/**
 *
 * @author DaniF
 */
public class Inicio extends javax.swing.JFrame {

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

    /**
     * Creates new form Inicio
     */
    public Inicio() {
        initComponents();
        this.setSize(500, 400);
        paginaSubir.setSize(597, 347);
        paginaSubir.setVisible(false);
        this.setIconImage(new ImageIcon("C:\\Users\\DAM2\\Downloads\\FINALINFERFACE\\src\\main\\java\\resources\\logoSimpleSinFondoSinLetras.png").getImage());
        //stackoverflow
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2,
                dim.height / 2 - this.getSize().height / 2);
        String filePath = "C:\\Users\\DAM2\\Downloads\\FINALINFERFACE\\src\\main\\java\\resources\\botonMenu.png";
        //logos barra superior
        jToggleButton1.setIcon(new ImageIcon(new ImageIcon(filePath).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
        
        
        
        
        
        //LLENAR LA LISTA CON LOS FICHEROS DE LA RAIZ DEL BUCKET
        Page<Blob> ficherosCloud = Inicio.listObjects("NOMBRE-PROYECTO-GCP", "NOMBRE-BUCKET-USUARIO");

        int numBlobs = 0;
        for (Blob blob : ficherosCloud.iterateAll()) {
            numBlobs++;
        }

        String[] listaNombresArchivos = new String[numBlobs];

        int i = 0;
        for (Blob blob : ficherosCloud.iterateAll()) {
            listaNombresArchivos[i] = blob.getName().replaceAll(".*/", "");

            i++;
        }

        //modifico codigo nativo para asignarle el valor segun los ficheros del bucket
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            //String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() {

                return listaNombresArchivos.length;

            }

            public String getElementAt(int i) {
                return listaNombresArchivos[i];
            }
        });

        jList1.setCellRenderer(new CustomRenderer());
        //chatGPT
        JScrollBar verticalScrollBar = jScrollPane1.getVerticalScrollBar();
        JScrollBar horizontalScrollBar = jScrollPane1.getHorizontalScrollBar();

        verticalScrollBar.setUI(new CustomScrollBarUI());
        horizontalScrollBar.setUI(new CustomScrollBarUI());

        // Ajustar el tamaño de la barra de desplazamiento si es necesario
        verticalScrollBar.setPreferredSize(new Dimension(12, Integer.MAX_VALUE));
        horizontalScrollBar.setPreferredSize(new Dimension(Integer.MAX_VALUE, 12));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        paginaSubir = new javax.swing.JFrame();
        jFileChooser2 = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        botonSubir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();

        paginaSubir.setSize(new java.awt.Dimension(597, 347));

        javax.swing.GroupLayout paginaSubirLayout = new javax.swing.GroupLayout(paginaSubir.getContentPane());
        paginaSubir.getContentPane().setLayout(paginaSubirLayout);
        paginaSubirLayout.setHorizontalGroup(
            paginaSubirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 597, Short.MAX_VALUE)
            .addGroup(paginaSubirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(paginaSubirLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jFileChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        paginaSubirLayout.setVerticalGroup(
            paginaSubirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 347, Short.MAX_VALUE)
            .addGroup(paginaSubirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(paginaSubirLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jFileChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inicio - EnterCloud Project");

        jPanel1.setBackground(new java.awt.Color(53, 114, 239));

        jPanel2.setBackground(new java.awt.Color(2, 34, 57));

        jToggleButton1.setBackground(new java.awt.Color(2, 34, 57));
        jToggleButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jToggleButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButton1MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Nunito Medium", 0, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Inicio");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(19, 19, 19))
        );

        jPanel3.setBackground(new java.awt.Color(2, 34, 57));

        jButton1.setBackground(new java.awt.Color(2, 34, 57));
        jButton1.setFont(new java.awt.Font("Nunito Medium", 0, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Inicio");
        jButton1.setToolTipText("");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jButton2.setBackground(new java.awt.Color(2, 34, 57));
        jButton2.setFont(new java.awt.Font("Nunito Medium", 0, 13)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Favoritos");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        botonSubir.setBackground(new java.awt.Color(2, 34, 57));
        botonSubir.setFont(new java.awt.Font("Nunito Medium", 0, 13)); // NOI18N
        botonSubir.setForeground(new java.awt.Color(255, 255, 255));
        botonSubir.setText("Subir");
        botonSubir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonSubir.setMaximumSize(new java.awt.Dimension(78, 73));
        botonSubir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonSubirMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(botonSubir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(botonSubir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jList1.setBackground(new java.awt.Color(53, 114, 239));
        jList1.setForeground(new java.awt.Color(255, 255, 255));
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
   /* private void refreshLista(String dir){
        Page<Blob> ficherosCloud = Inicio.listObjectsWithPrefix("aerial-citron-437514-t6", "primerapruebaentercloud", dir);

        int numBlobs = 0;
        for (Blob blob : ficherosCloud.iterateAll()) {
            numBlobs++;
        }

        String[] listaNombresArchivos = new String[numBlobs];

        int i = 0;
        for (Blob blob : ficherosCloud.iterateAll()) {
            listaNombresArchivos[i] = blob.getName().replaceAll("/", "");

            i++;
        }

        //modifico codigo nativo para asignarle el valor segun los ficheros del bucket
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            //String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() {

                return listaNombresArchivos.length;

            }

            public String getElementAt(int i) {
                return listaNombresArchivos[i];
            }
        });

        jList1.setCellRenderer(new CustomRenderer());
    }
*/
    
    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
        JList list = (JList) evt.getSource();
        if (evt.getClickCount() == 2) {
            /*
            jDialog1.setVisible(true);
            jLabel2.setText(jList1.getSelectedValue());
             */
        }

    }//GEN-LAST:event_jList1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jToggleButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButton1MouseClicked
        // TODO add your handling code here:
        /*
        if (!jPanel4.isVisible()) {
            jPanel4.setVisible(true);
            this.setOpacity(0.5f);
        } else {
            jPanel4.setVisible(false);
        }
         */
    }//GEN-LAST:event_jToggleButton1MouseClicked

    private void botonSubirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonSubirMouseClicked
        // TODO add your handling code here:
        if (paginaSubir.isVisible()) {
            paginaSubir.setVisible(false);
        } else {
            paginaSubir.setVisible(true);
        }
    }//GEN-LAST:event_botonSubirMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Inicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonSubir;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JFileChooser jFileChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JFrame paginaSubir;
    // End of variables declaration//GEN-END:variables
}
