package view;

import javax.swing.*;
import java.awt.*;

/**
 * Renderizador personalizado para los elementos de una lista (`JList`). Permite
 * personalizar la apariencia de cada celda en la lista, añadiendo un botón y
 * gestionando estilos específicos para los estados de selección y "hover".
 *
 * @author CDC
 */
public class CustomRenderer extends DefaultListCellRenderer {

    /**
     * Índice del elemento actualmente "hovered" (bajo el puntero del mouse).
     */
    private int hoveredIndex = -1; // Índice del elemento hover

    /**
     * Configura el índice del elemento que está bajo el puntero del mouse. Este
     * índice se usa para aplicar un estilo de hover a dicho elemento.
     *
     * @param hoveredIndex El índice del elemento hover.
     */
    public void setHoveredIndex(int hoveredIndex) {
        this.hoveredIndex = hoveredIndex;
    }

    /**
     * Sobrescribe el método para renderizar los componentes de cada celda en la
     * lista. Se crea un panel con un `JLabel` para el texto y un `JButton` a la
     * derecha, aplicando estilos personalizados según el estado del elemento.
     *
     * @param list La lista que contiene los elementos.
     * @param value El valor del elemento que se está renderizando.
     * @param index El índice del elemento en la lista.
     * @param isSelected Indica si el elemento está seleccionado.
     * @param cellHasFocus Indica si el elemento tiene el foco.
     * @return Componente configurado para representar el elemento de la lista.
     */
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {
        // Crear un panel con un diseño de borde
        JPanel panel = new JPanel(new BorderLayout());

        // Crear y configurar un JLabel para mostrar el texto del elemento
        JLabel label = new JLabel(value.toString());
        label.setFont(new Font("Nunito Medium", Font.PLAIN, 13));

        // Crear y configurar un JButton para acciones adicionales
        JButton desplegable = new JButton();
        ImageIcon iconoMenuBoton = new ImageIcon(getClass().getResource("/Iconos_Menus/menu_blanco.png"));
        desplegable.setIcon(iconoMenuBoton);
        desplegable.setFont(new Font("Nunito Medium", Font.PLAIN, 13));
        desplegable.setBackground(new Color(53, 114, 239));
        desplegable.setPreferredSize(new Dimension(40, 40));

        // Añadir el JLabel y el JButton al panel
        panel.add(label, BorderLayout.WEST);
        panel.add(desplegable, BorderLayout.EAST);

        // Configurar estilos según el estado del elemento
        if (isSelected) {
            // Estilo de selección
            panel.setBackground(list.getSelectionBackground());
            label.setForeground(list.getSelectionForeground());
        } else if (index == hoveredIndex) {
            // Estilo de hover
            panel.setBackground(Color.WHITE);
            label.setForeground(Color.BLACK);
        } else {
            // Estilo por defecto
            panel.setBackground(list.getBackground());
            label.setForeground(list.getForeground());
        }

        return panel;
    }
}
