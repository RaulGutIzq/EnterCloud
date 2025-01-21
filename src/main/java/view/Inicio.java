package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;

/**
 * Clase que representa la ventana principal de la aplicación. Proporciona la
 * interfaz gráfica con menús, botones y un área para la interacción del
 * usuario.
 *
 * @author CDC
 */
public class Inicio extends javax.swing.JFrame {

    /**
     * Constructor de la clase. Inicializa los componentes gráficos y configura
     * las propiedades iniciales de la ventana.
     */
    public Inicio() {
        initComponents();
        // Configuración inicial de la ventana
        this.setSize(700, 600);
        this.setSize(700, 600);
        this.setIconImage(new ImageIcon(getClass().getResource("/logoSimpleSinFondoSinLetras.png")).getImage());
        centrarVentana();
        // Ocultar la sección de subir archivos hasta que sea necesaria
        paginaSubir.setSize(650, 400);
        paginaSubir.setVisible(false);

        // Configurar el menú desplegable del usuario
        menuUsuario.add(lblAlmacenamiento);
        menuUsuario.add(new JPopupMenu.Separator());
        menuUsuario.add(barraProg);
        menuUsuario.add(new JPopupMenu.Separator());
        menuUsuario.add(btnCerrarSesion);
        // Agregar la barra de progreso al menú

        // Establecer texto inicial de los elementos del menú
        lblAlmacenamiento.setText("Almacenamiento: ");
        btnCerrarSesion.setText("Cerrar Sesión");
        // Asignar un icono a la ventana
        this.setIconImage(new ImageIcon(getClass().getResource("/logoSimpleSinFondoSinLetras.png")).getImage());

        // Centrar la ventana en la pantalla
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2,
                dim.height / 2 - this.getSize().height / 2);

        // Configurar barras de desplazamiento personalizadas
        JScrollBar verticalScrollBar = jScrollPane1.getVerticalScrollBar();
        JScrollBar horizontalScrollBar = jScrollPane1.getHorizontalScrollBar();

        verticalScrollBar.setUI(new CustomScrollBarUI());
        horizontalScrollBar.setUI(new CustomScrollBarUI());

        // Ajustar el tamaño de las barras de desplazamiento
        verticalScrollBar.setPreferredSize(new Dimension(12, Integer.MAX_VALUE));
        horizontalScrollBar.setPreferredSize(new Dimension(Integer.MAX_VALUE, 12));
    }

    /**
     * Inicialización automática de componentes generada por el editor.
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
        barraProg = new javax.swing.JProgressBar();
        panelInferior = new javax.swing.JPanel();
        botonInicio = new javax.swing.JButton();
        botonFavoritos = new javax.swing.JButton();
        botonSubir = new javax.swing.JButton();
        botonAyuda = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();

        paginaSubir.setSize(new java.awt.Dimension(597, 347));

        jFileChooser2.setFileSelectionMode(javax.swing.JFileChooser.FILES_AND_DIRECTORIES);

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

        btnMenu.setBackground(new java.awt.Color(2, 34, 57));
        btnMenu.setForeground(new java.awt.Color(255, 255, 255));
        btnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos_Menus/persona_blanca.png"))); // NOI18N
        btnMenu.setToolTipText("Menu Usuario");
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSuperiorLayout.createSequentialGroup()
                        .addComponent(barraProg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCerrarSesion))
                    .addComponent(lblAlmacenamiento, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelSuperiorLayout.createSequentialGroup()
                                .addComponent(lblAlmacenamiento, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnMenu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(panelSuperiorLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(barraProg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        panelInferior.setBackground(new java.awt.Color(2, 34, 57));

        botonInicio.setBackground(new java.awt.Color(2, 34, 57));
        botonInicio.setFont(new java.awt.Font("Nunito Medium", 0, 13)); // NOI18N
        botonInicio.setForeground(new java.awt.Color(255, 255, 255));
        botonInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos_Barra/home_blanco.png"))); // NOI18N
        botonInicio.setToolTipText("Inicio");
        botonInicio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        botonFavoritos.setBackground(new java.awt.Color(2, 34, 57));
        botonFavoritos.setFont(new java.awt.Font("Nunito Medium", 0, 13)); // NOI18N
        botonFavoritos.setForeground(new java.awt.Color(255, 255, 255));
        botonFavoritos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos_Barra/fav_blanco.png"))); // NOI18N
        botonFavoritos.setToolTipText("Favoritos");
        botonFavoritos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        botonSubir.setBackground(new java.awt.Color(2, 34, 57));
        botonSubir.setFont(new java.awt.Font("Nunito Medium", 0, 13)); // NOI18N
        botonSubir.setForeground(new java.awt.Color(255, 255, 255));
        botonSubir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos_Barra/upload_blanco.png"))); // NOI18N
        botonSubir.setToolTipText("Subir");
        botonSubir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonSubir.setMaximumSize(new java.awt.Dimension(78, 73));

        botonAyuda.setBackground(new java.awt.Color(2, 34, 57));
        botonAyuda.setForeground(new java.awt.Color(255, 255, 255));
        botonAyuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos_Barra/help_blanco.png"))); // NOI18N
        botonAyuda.setToolTipText("Ayuda");
        botonAyuda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout panelInferiorLayout = new javax.swing.GroupLayout(panelInferior);
        panelInferior.setLayout(panelInferiorLayout);
        panelInferiorLayout.setHorizontalGroup(
            panelInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInferiorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonAyuda, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botonFavoritos)
                .addGap(18, 18, 18)
                .addComponent(botonInicio)
                .addGap(18, 18, 18)
                .addComponent(botonSubir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelInferiorLayout.setVerticalGroup(
            panelInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInferiorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonInicio)
                    .addComponent(botonFavoritos)
                    .addComponent(botonSubir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonAyuda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
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
        menuUsuario.show(btnMenu, 0, btnMenu.getHeight());

    }//GEN-LAST:event_btnMenuActionPerformed

    private void configurarMenuUsuario() {
        // Configura el layout del menú
        menuUsuario.setLayout(new javax.swing.BoxLayout(menuUsuario, javax.swing.BoxLayout.Y_AXIS));

        // Etiqueta de almacenamiento
        lblAlmacenamiento.setText("Almacenamiento: 0%");
        menuUsuario.add(lblAlmacenamiento);

        // Separador
        menuUsuario.add(new JPopupMenu.Separator());

        // Barra de progreso
        barraProg.setStringPainted(true); // Mostrar el porcentaje
        barraProg.setValue(50);          // Valor inicial (ejemplo, cámbialo según tu lógica)
        menuUsuario.add(barraProg);

        // Separador
        menuUsuario.add(new JPopupMenu.Separator());

        // Botón de cierre de sesión
        btnCerrarSesion.setText("Cerrar Sesión");
        menuUsuario.add(btnCerrarSesion);
    }

    private void centrarVentana() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2,
                dim.height / 2 - this.getSize().height / 2);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JProgressBar barraProg;
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
