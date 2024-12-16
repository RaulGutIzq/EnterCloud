package view;

/**
 * Clase que representa la ventana de ayuda en la aplicación. Muestra una imagen
 * estática que contiene información o instrucciones para el usuario.
 *
 * @author CDC
 */
public class Ayuda extends javax.swing.JFrame {

    /**
     * Constructor de la clase Ayuda. Inicializa los componentes gráficos y
     * configura la ventana.
     */
    public Ayuda() {
        initComponents(); // Inicializa los componentes generados automáticamente.
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ayuda - EnterCloud Project");
        setMaximumSize(new java.awt.Dimension(1215, 682));
        setMinimumSize(new java.awt.Dimension(1215, 682));
        setResizable(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Ayuda.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
