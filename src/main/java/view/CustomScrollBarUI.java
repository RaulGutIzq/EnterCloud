package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * Clase CustomScrollBarUI.
 *
 * Esta clase personaliza la apariencia de una barra de desplazamiento
 * utilizando la clase base {@link BasicScrollBarUI}. Se definen colores,
 * tamaños y estilos personalizados para el "pulgar" y el "track" de la barra de
 * desplazamiento.
 *
 * Uso: Esta clase puede aplicarse a una barra de desplazamiento (JScrollBar)
 * para cambiar su apariencia predeterminada.
 *
 * @author CDC
 */
public class CustomScrollBarUI extends BasicScrollBarUI {

    /**
     * Configura los colores del "pulgar" (thumb) y el "track" de la barra de
     * desplazamiento. El "pulgar" es la parte que el usuario arrastra, y el
     * "track" es el fondo.
     */
    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = new Color(100, 149, 237);  // Color azul claro para el "pulgar"
        this.trackColor = new Color(211, 211, 211);  // Color gris claro para el "track"
    }

    /**
     * Define el tamaño mínimo del "pulgar". Esto asegura que el "pulgar" sea lo
     * suficientemente grande para que sea fácil de usar.
     *
     * @return Las dimensiones mínimas del "pulgar".
     */
    @Override
    protected Dimension getMinimumThumbSize() {
        return new Dimension(30, 30);  // Tamaño mínimo de 30x30 píxeles
    }

    /**
     * Pinta el "pulgar" (thumb) de la barra de desplazamiento con esquinas
     * redondeadas.
     *
     * @param g El objeto {@link Graphics} usado para dibujar.
     * @param c El componente al que pertenece el "pulgar".
     * @param thumbBounds El área ocupada por el "pulgar".
     */
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2 = (Graphics2D) g.create();
        // Activa el antialiasing para bordes suaves
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Pinta el fondo del "pulgar"
        g2.setColor(thumbColor);
        g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);

        // Pinta el borde del "pulgar"
        g2.setColor(Color.GRAY);
        g2.drawRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);

        g2.dispose(); // Libera los recursos gráficos
    }

    /**
     * Pinta el "track" de la barra de desplazamiento.
     *
     * @param g El objeto {@link Graphics} usado para dibujar.
     * @param c El componente al que pertenece el "track".
     * @param trackBounds El área ocupada por el "track".
     */
    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        // Pinta el fondo del "track"
        g.setColor(trackColor);
        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
    }
}
