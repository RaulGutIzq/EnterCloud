package view;

import javax.swing.*;
import java.awt.*;

public class CustomRenderer extends DefaultListCellRenderer {

    private int hoveredIndex = -1; // Índice del elemento hover

    public void setHoveredIndex(int hoveredIndex) {
        this.hoveredIndex = hoveredIndex;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        // Crear un panel con un JLabel y un JButton
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(value.toString());
        JButton desplegable = new JButton();
        label.setFont(new Font("Nunito Medium", Font.PLAIN, 13));
        desplegable.setFont(new Font("Nunito Medium", Font.PLAIN, 13));

        // Configurar botón
        ImageIcon iconoMenuBoton = new ImageIcon(getClass().getResource("/Iconos_Menus/menu_blanco.png"));
        desplegable.setIcon(iconoMenuBoton);
        desplegable.setBackground(new Color(53, 114, 239));
        desplegable.setPreferredSize(new Dimension(40, 40));

        // Añadir componentes al panel
        panel.add(label, BorderLayout.WEST);
        panel.add(desplegable, BorderLayout.EAST);

        // Cambiar el fondo para selección o hover
        if (isSelected) {
            panel.setBackground(list.getSelectionBackground());
            label.setForeground(list.getSelectionForeground());
        } else if (index == hoveredIndex) {
            panel.setBackground(Color.WHITE); // Fondo para hover
            label.setForeground(Color.BLACK);
        } else {
            panel.setBackground(list.getBackground());
            label.setForeground(list.getForeground());
        }

        return panel;
    }
}
