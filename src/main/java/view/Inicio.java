package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;

/**
 *
 * @author DaniF
 */
public class Inicio extends javax.swing.JFrame {


    /**
     * Creates new form Inicio
     */
    public Inicio() {
        initComponents();
        this.setSize(500, 400);
        paginaSubir.setSize(597, 347);
        paginaSubir.setVisible(false);
        menuUsuario.add(lblAlmacenamiento);
        menuUsuario.add(new JPopupMenu.Separator());
        menuUsuario.add(btnCerrarSesion);
        lblAlmacenamiento.setText("Almacenamiento: ");
        btnCerrarSesion.setText("Cerrar Sesión");
        this.setIconImage(new ImageIcon("/resources/logoSimpleSinFondoSinLetras.png").getImage());
        //stackoverflow
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2,
                dim.height / 2 - this.getSize().height / 2);
        String filePath = "/resources/botonMenu.png";
        //logos barra superior
        btnMenu.setIcon(new ImageIcon(new ImageIcon(filePath).getImage()));

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
        menuUsuario = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        panelSuperior = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnMenu = new javax.swing.JButton();
        lblAlmacenamiento = new javax.swing.JLabel();
        btnCerrarSesion = new javax.swing.JButton();
        panelInferior = new javax.swing.JPanel();
        botonInicio = new javax.swing.JButton();
        botonFavoritos = new javax.swing.JButton();
        botonSubir = new javax.swing.JButton();
        botonAyuda = new javax.swing.JButton();
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

        panelSuperior.setBackground(new java.awt.Color(2, 34, 57));

        jLabel1.setFont(new java.awt.Font("Nunito Medium", 0, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Inicio");

        btnMenu.setText("Usuario");
        btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSuperiorLayout = new javax.swing.GroupLayout(panelSuperior);
        panelSuperior.setLayout(panelSuperiorLayout);
        panelSuperiorLayout.setHorizontalGroup(
            panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSuperiorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSuperiorLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAlmacenamiento, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSuperiorLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(btnCerrarSesion)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelSuperiorLayout.setVerticalGroup(
            panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSuperiorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSuperiorLayout.createSequentialGroup()
                        .addComponent(lblAlmacenamiento, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSuperiorLayout.createSequentialGroup()
                        .addGap(0, 9, Short.MAX_VALUE)
                        .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(btnMenu))
                        .addGap(18, 18, 18))))
        );

        panelInferior.setBackground(new java.awt.Color(2, 34, 57));

        botonInicio.setBackground(new java.awt.Color(2, 34, 57));
        botonInicio.setFont(new java.awt.Font("Nunito Medium", 0, 13)); // NOI18N
        botonInicio.setForeground(new java.awt.Color(255, 255, 255));
        botonInicio.setText("Inicio");
        botonInicio.setToolTipText("");
        botonInicio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        botonFavoritos.setBackground(new java.awt.Color(2, 34, 57));
        botonFavoritos.setFont(new java.awt.Font("Nunito Medium", 0, 13)); // NOI18N
        botonFavoritos.setForeground(new java.awt.Color(255, 255, 255));
        botonFavoritos.setText("Favoritos");
        botonFavoritos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        botonSubir.setBackground(new java.awt.Color(2, 34, 57));
        botonSubir.setFont(new java.awt.Font("Nunito Medium", 0, 13)); // NOI18N
        botonSubir.setForeground(new java.awt.Color(255, 255, 255));
        botonSubir.setText("Subir");
        botonSubir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonSubir.setMaximumSize(new java.awt.Dimension(78, 73));

        botonAyuda.setText("?");

        javax.swing.GroupLayout panelInferiorLayout = new javax.swing.GroupLayout(panelInferior);
        panelInferior.setLayout(panelInferiorLayout);
        panelInferiorLayout.setHorizontalGroup(
            panelInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInferiorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonAyuda, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(botonInicio)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(botonFavoritos)
                .addGap(18, 21, Short.MAX_VALUE)
                .addComponent(botonSubir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );
        panelInferiorLayout.setVerticalGroup(
            panelInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInferiorLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(panelInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonInicio)
                    .addComponent(botonFavoritos)
                    .addComponent(botonSubir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonAyuda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelSuperior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelInferior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(panelInferior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMenuActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton botonAyuda;
    public javax.swing.JButton botonFavoritos;
    public javax.swing.JButton botonInicio;
    public javax.swing.JButton botonSubir;
    public javax.swing.JButton btnCerrarSesion;
    public javax.swing.JButton btnMenu;
    public javax.swing.JFileChooser jFileChooser2;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JList<String> jList1;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lblAlmacenamiento;
    public javax.swing.JPopupMenu menuUsuario;
    public javax.swing.JFrame paginaSubir;
    public javax.swing.JPanel panelInferior;
    public javax.swing.JPanel panelSuperior;
    // End of variables declaration//GEN-END:variables
}
