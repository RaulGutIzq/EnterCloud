package view;

import java.awt.*;
import javax.swing.*;
import java.net.URI;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author DAM2
 */
public class AyudaDialog extends JDialog {

    private JLabel imagenLabel;
    private JButton btnAnterior, btnSiguiente;
    private int indiceImagen = 0;
    private String[] imagenes = {"/Ayuda1.png", "/Ayuda2.png", "/Ayuda3.png", "/Ayuda4.png"};

    public AyudaDialog(JFrame parent) {
        super(parent, "Menú de Ayuda", true);
        setSize(300, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // Panel de botones con estilo unificado
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 5, 5));

        JButton btnTour = createStyledButton("Tour Interactivo", e -> mostrarTourInteractivo());
        JButton btnWeb = createStyledButton("Ayuda en Línea", e -> abrirURLAyuda());
        JButton btnManual = createStyledButton("Manual de Usuario", e -> abrirManualHTML());

        panelBotones.add(btnTour);
        btnTour.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelBotones.add(btnWeb);
        btnWeb.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelBotones.add(btnManual);
        btnManual.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Panel de texto (copyright y licencia)
        JPanel panelTexto = new JPanel(new BorderLayout());
        JLabel lblCopyright = new JLabel("Entercloud © 2025 by Entercloud Company", SwingConstants.CENTER);
        lblCopyright.setFont(new Font("Arial", Font.PLAIN, 10));

        // Usamos HTML con un div centrado para que el enlace se muestre correctamente centrado
        JLabel lblLink = new JLabel(
                "<html><div style='text-align: center;'><a href='https://creativecommons.org/licenses/by-nc-nd/4.0/?ref=chooser-v1'>Licensed under Creative Commons Attribution-NC-ND 4.0 International</a></div></html>",
                SwingConstants.CENTER);
        lblLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    Desktop.getDesktop().browse(new URI("https://creativecommons.org/licenses/by-nc-nd/4.0/?ref=chooser-v1"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        panelTexto.add(lblCopyright, BorderLayout.NORTH);
        panelTexto.add(lblLink, BorderLayout.SOUTH);

        add(panelBotones, BorderLayout.CENTER);
        add(panelTexto, BorderLayout.SOUTH);
    }

    // Método auxiliar para crear botones con un estilo unificado
    private JButton createStyledButton(String text, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        button.setFocusPainted(false);
        button.setBackground(new Color(2, 34, 57));  // Azul acero
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    private void mostrarTourInteractivo() {
        indiceImagen = 0;

        final JDialog tourFrame = new JDialog(this, "Tour Interactivo", true);
        tourFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        tourFrame.setSize(700, 650);
        tourFrame.setResizable(false);
        tourFrame.setLocationRelativeTo(this);

        imagenLabel = new JLabel();
        actualizarImagen();

        btnAnterior = new JButton("Anterior");
        btnAnterior.setEnabled(false);
        btnAnterior.addActionListener(e -> cambiarImagen(-1));

        btnSiguiente = new JButton("Siguiente");
        btnSiguiente.setEnabled(true);
        btnSiguiente.addActionListener(e -> cambiarImagen(1));

        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> tourFrame.dispose());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAnterior);
        panelBotones.add(btnSiguiente);
        panelBotones.add(btnSalir);

        tourFrame.setLayout(new BorderLayout());
        tourFrame.add(imagenLabel, BorderLayout.CENTER);
        tourFrame.add(panelBotones, BorderLayout.SOUTH);
        btnAnterior.setBackground(new Color(2, 34, 57));  // Azul acero
        btnAnterior.setForeground(Color.WHITE);
        btnSiguiente.setBackground(new Color(2, 34, 57));  // Azul acero
        btnSiguiente.setForeground(Color.WHITE);
        btnSalir.setBackground(new Color(2, 34, 57));  // Azul acero
        btnSalir.setForeground(Color.WHITE);
        tourFrame.setVisible(true);
    }

    private void cambiarImagen(int incremento) {
        indiceImagen += incremento;

        if (indiceImagen < 0) {
            indiceImagen = 0;
        } else if (indiceImagen >= imagenes.length) {
            indiceImagen = imagenes.length - 1;
        }

        actualizarImagen();

        btnAnterior.setEnabled(indiceImagen > 0);
        btnSiguiente.setEnabled(indiceImagen < imagenes.length - 1);
    }

    private void actualizarImagen() {
        ImageIcon originalImage = new ImageIcon(getClass().getResource(imagenes[indiceImagen]));
        Image scaledImage = originalImage.getImage().getScaledInstance(681, 590, Image.SCALE_SMOOTH);
        imagenLabel.setIcon(new ImageIcon(scaledImage));
    }

    private void abrirURLAyuda() {
        try {
            File ayuda = File.createTempFile("ayuda_", ".chm");
            Files.copy(getClass().getResourceAsStream("/ayuda.chm"), ayuda.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Desktop.getDesktop().open(ayuda);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirManualHTML() {
        try {
            File manual = File.createTempFile("ManualdeUsuario_", ".pdf");
            Files.copy(getClass().getResourceAsStream("/ManualdeUsuario.pdf"), manual.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Desktop.getDesktop().open(manual);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
