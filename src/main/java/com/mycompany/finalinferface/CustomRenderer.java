package com.mycompany.finalinferface; // Asegúrate de que el paquete sea el mismo que el de la clase Inicio

import javax.swing.*;
import java.awt.*;

public class CustomRenderer extends DefaultListCellRenderer {
    
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        // Crear un panel con un JLabel y un JButton
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(value.toString());
        JComboBox desplegable = new JComboBox();
        desplegable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        /*
        JButton button = new JButton("Acción");
        
        button.setText("");
        ImageIcon iconoMenuBoton = new ImageIcon("C:/Users/DAM2/Downloads/FINALINFERFACE/src/main/java/resources/botonMenu.png");
        button.setIcon(iconoMenuBoton);
        button.setBackground(new java.awt.Color(53, 114, 239));
        button.setPreferredSize(new Dimension(40, 40));
        
        // Añadir el label y el botón al panel
        
        panel.add(button, BorderLayout.EAST);
        */
        panel.add(label, BorderLayout.WEST);
        panel.add(desplegable, BorderLayout.EAST);
        // Configurar el fondo del panel dependiendo de si está seleccionado
        if (isSelected) {
            panel.setBackground(list.getSelectionBackground());
            label.setForeground(list.getSelectionForeground());
        } else {
            panel.setBackground(list.getBackground());
            label.setForeground(list.getForeground());
        }

        return panel;
    }
}
