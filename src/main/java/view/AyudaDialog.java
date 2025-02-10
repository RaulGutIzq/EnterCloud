package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.net.URI;
import java.io.File;

/**
 *
 * @author DAM2
 */
public class AyudaDialog extends JDialog {
    private JLabel imagenLabel;
    private JButton btnAnterior, btnSiguiente;
    private int indiceImagen = 0;
    private String[] imagenes = {"/Ayuda1.png", "/Ayuda2.png", "/Ayuda3.png", "/Ayuda4.png"};
    
    /**
     *
     * @param parent
     */
    public AyudaDialog(JFrame parent) {
        super(parent, "Menú de Ayuda", true);
        setSize(300, 200);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnTour = new JButton("Tour Interactivo");
        btnTour.addActionListener(e -> mostrarTourInteractivo());

        JButton btnWeb = new JButton("Ayuda en Línea");
        btnWeb.addActionListener(e -> abrirURLAyuda());

        JButton btnManual = new JButton("Manual de Usuario");
        btnManual.addActionListener(e -> abrirManualHTML());

        add(btnTour);
        add(btnWeb);
        add(btnManual);
    }

    private void mostrarTourInteractivo() {
    // Reiniciar el índice de la imagen para comenzar desde la primera diapositiva
    indiceImagen = 0;
    
    JDialog tourFrame = new JDialog(this, "Tour Interactivo", true);
    tourFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    tourFrame.setSize(950, 600);
    tourFrame.setResizable(false);
    tourFrame.setLocationRelativeTo(this);

    imagenLabel = new JLabel();
    actualizarImagen(); // Muestra la primera imagen correctamente

    btnAnterior = new JButton("Anterior");
    btnAnterior.setEnabled(false); // Deshabilitado porque estamos en la primera imagen
    btnAnterior.addActionListener(e -> cambiarImagen(-1));

    btnSiguiente = new JButton("Siguiente");
    btnSiguiente.setEnabled(true); // Se asume que hay más imágenes
    btnSiguiente.addActionListener(e -> cambiarImagen(1));

    JPanel panelBotones = new JPanel();
    panelBotones.add(btnAnterior);
    panelBotones.add(btnSiguiente);

    tourFrame.setLayout(new BorderLayout());
    tourFrame.add(imagenLabel, BorderLayout.CENTER);
    tourFrame.add(panelBotones, BorderLayout.SOUTH);

    tourFrame.setVisible(true);
}


    private void cambiarImagen(int incremento) {
    indiceImagen += incremento;

    // Evitar que el índice se salga del rango
    if (indiceImagen < 0) {
        indiceImagen = 0;
    } else if (indiceImagen >= imagenes.length) {
        indiceImagen = imagenes.length - 1;
    }

    // Actualizar la imagen
    actualizarImagen();

    // Deshabilitar botones si estamos en la primera o última imagen
    btnAnterior.setEnabled(indiceImagen > 0);
    btnSiguiente.setEnabled(indiceImagen < imagenes.length - 1);
}

    private void actualizarImagen() {
        ImageIcon originalImage = new ImageIcon(getClass().getResource(imagenes[indiceImagen]));
        Image scaledImage = originalImage.getImage().getScaledInstance(900, 550, Image.SCALE_SMOOTH);
        imagenLabel.setIcon(new ImageIcon(scaledImage));
    }

    private void abrirURLAyuda() {
        try {
            Desktop.getDesktop().browse(new URI("https://tu-url.com/ayuda"));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al abrir la URL", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirManualHTML() {
        try {
            File htmlFile = new File("docs/manual.html");
            Desktop.getDesktop().open(htmlFile);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Archivo no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
