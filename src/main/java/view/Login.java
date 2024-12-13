package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

/**
 * Clase para la interfaz de login de la aplicación. Proporciona un formulario
 * básico para ingresar usuario y contraseña.
 *
 * @author CDC
 */
public class Login extends javax.swing.JFrame {

    /**
     * Constructor de la clase Login. Inicializa los componentes gráficos y
     * configura la ventana.
     */
    public Login() {
        initComponents(); //Inicializa los componentes gráficos generados por NetBeans.
        this.mensajeError.setVisible(false); //Oculta el mensaje de error inicialmente.
        this.setSize(500, 400); //Establece el tamaño de la ventana.

        //Configuración del ícono de la ventana.
        this.setIconImage(new ImageIcon(getClass().getResource("/logoSimpleSinFondoSinLetras.png")).getImage());

        //Centra la ventana en la pantalla.
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2,
                dim.height / 2 - this.getSize().height / 2);

        //Establece un ícono escalado para el componente "logo".
        logo.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/logoSimpleConFondoConNombrePequenio.png"))
                .getImage()));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        userForm = new javax.swing.JTextField();
        passForm = new javax.swing.JPasswordField();
        logo = new javax.swing.JLabel();
        mensajeError = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login - EnterCloud Platform");
        setLocation(new java.awt.Point(1, 1));
        setSize(new java.awt.Dimension(500, 500));

        jPanel1.setBackground(new java.awt.Color(53, 114, 239));

        jButton1.setBackground(new java.awt.Color(2, 34, 57));
        jButton1.setFont(new java.awt.Font("Nunito Medium", 0, 15)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Login");
        jButton1.setToolTipText("Acceder a Entercloud");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        userForm.setFont(new java.awt.Font("Nunito Medium", 0, 12)); // NOI18N
        userForm.setForeground(new java.awt.Color(2, 34, 57));
        userForm.setText("Usuario");
        userForm.setToolTipText("Introduzca su usuario");

        passForm.setFont(new java.awt.Font("Nunito Medium", 0, 12)); // NOI18N
        passForm.setForeground(new java.awt.Color(2, 34, 57));
        passForm.setText("jPasswordField1");
        passForm.setToolTipText("Introduzca su contraseña");

        mensajeError.setBackground(new java.awt.Color(2, 34, 57));
        mensajeError.setFont(new java.awt.Font("Nunito SemiBold", 0, 12)); // NOI18N
        mensajeError.setForeground(new java.awt.Color(255, 0, 51));
        mensajeError.setText("USUARIO O CONTRASEÑA INCORRECTOS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logo)
                    .addComponent(mensajeError))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(passForm, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userForm, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addComponent(logo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                .addComponent(userForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(passForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mensajeError)
                .addContainerGap(45, Short.MAX_VALUE))
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton1;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JLabel logo;
    public javax.swing.JLabel mensajeError;
    public javax.swing.JPasswordField passForm;
    public javax.swing.JTextField userForm;
    // End of variables declaration//GEN-END:variables
}
