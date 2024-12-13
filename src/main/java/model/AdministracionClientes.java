
package model;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Clase principal para la administración de clientes.
 * Se encarga de inicializar el archivo "Clientes.dat" con datos de ejemplo.
 * 
 * Este programa utiliza la clase `ClientesDAO` para realizar operaciones de escritura
 * en el archivo binario y almacenar objetos de tipo `Cliente`.
 * 
 * @author CDC
 */
public class AdministracionClientes {

    /**
     * Método principal (entry point) del programa.
     * Este método crea una instancia de `ClientesDAO` en modo escritura,
     * escribe algunos clientes predefinidos y luego cierra el archivo.
     * 
     * @param args Argumentos de línea de comandos (no se utilizan en este caso).
     * @throws FileNotFoundException Si no se puede encontrar o crear el archivo "Clientes.dat".
     * @throws IOException Si ocurre algún error al escribir en el archivo.
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
         // Crear una instancia de ClientesDAO en modo escritura ("w").
        ClientesDAO fich = new ClientesDAO("Clientes.dat", "w");
        
        // Escribir clientes en el archivo con sus respectivos datos.
        // Formato: (ID, correo electrónico, teléfono, tipo de cliente)
        fich.escribir(new Cliente(1234, "raul@raul.com", "611465858", (byte) 2));
        fich.escribir(new Cliente(5678, "coto@coto.com", "123456789", (byte) 2));
        fich.escribir(new Cliente(1111, "fraile@fraile.com", "987654321", (byte) 1));
        
        // Cerrar el archivo para asegurar que todos los datos se han guardado correctamente.
        fich.cerrar();
    }
}
