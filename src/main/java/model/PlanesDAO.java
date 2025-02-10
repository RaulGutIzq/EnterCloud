/**
 * Clase PlanesDAO para manejar el acceso a datos de objetos Plan.
 *
 * Esta clase permite leer y escribir objetos Plan en un archivo binario.
 *
 * Modo de apertura del archivo:
 * - "r": Solo lectura.
 * - "w": Escritura (sobrescribe el archivo si ya existe).
 * - "a": Escritura en modo append (agrega datos al final del archivo).
 *
 * Dependencias: Plan debe ser una clase que implemente los métodos necesarios para
 * acceder a sus atributos: getId(), getCapacidad() y getPrecio().
 *
 * @author CDC
 */
package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author DAM2
 */
public class PlanesDAO {

    // Flujos para manejar la entrada y salida de datos binarios
    private DataInputStream entrada;
    private DataOutputStream salida;

    /**
     * Constructor que inicializa el DAO con una ruta de archivo y un modo.
     *
     * @param ruta Ruta del archivo.
     * @param modo Modo de apertura: "r", "w" o "a".
     * @throws FileNotFoundException Si el archivo no existe y el modo es "r".
     */
    public PlanesDAO(String ruta, String modo) throws FileNotFoundException {
        switch (modo) {
            case "r" ->
                entrada = new DataInputStream(new FileInputStream(ruta));
            case "w" ->
                salida = new DataOutputStream(new FileOutputStream(ruta));
            case "a" ->
                salida = new DataOutputStream(new FileOutputStream(ruta, true));
            default ->
                throw new IllegalArgumentException("El modo debe ser r/w/a");
        }
    }

    /**
     * Constructor que inicializa el DAO con un archivo y un modo.
     *
     * @param arch Archivo.
     * @param modo Modo de apertura: "r", "w" o "a".
     * @throws FileNotFoundException Si el archivo no existe y el modo es "r".
     */
    public PlanesDAO(File arch, String modo) throws FileNotFoundException {
        switch (modo) {
            case "r" ->
                entrada = new DataInputStream(new FileInputStream(arch));
            case "w" ->
                salida = new DataOutputStream(new FileOutputStream(arch));
            case "a" ->
                salida = new DataOutputStream(new FileOutputStream(arch, true));
            default ->
                throw new IllegalArgumentException("El modo debe ser r/w/a");
        }
    }

    /**
     * Lee un objeto Plan del archivo.
     *
     * @return Un objeto Plan leído del archivo o null si no hay entrada.
     * @throws EOFException Si se alcanza el final del archivo.
     * @throws IOException Si ocurre un error de lectura.
     */
    public Plan leer() throws EOFException, IOException {
        if (entrada != null) {
            // Leer los campos de Plan desde el archivo
            return new Plan(entrada.readByte(), entrada.readFloat(), entrada.readFloat());
        } else {
            return null; // Devuelve null si el flujo de entrada no está inicializado
        }
    }

    /**
     * Escribe un objeto Plan en el archivo.
     *
     * @param plan El objeto Plan a escribir.
     * @throws IOException Si ocurre un error de escritura.
     */
    public void escribir(Plan plan) throws IOException {
        if (salida != null) {
            // Escribir los campos del objeto Plan en el archivo
            salida.writeByte(plan.getId());
            salida.writeFloat(plan.getCapacidad());
            salida.writeFloat(plan.getPrecio());
        }
    }

    /**
     * Cierra los flujos de entrada o salida, según cuál esté activo.
     *
     * @throws IOException Si ocurre un error al cerrar el flujo.
     */
    public void cerrar() throws IOException {
        if (entrada != null) {
            entrada.close();
        } else if (salida != null) {
            salida.close();
        }
    }
}
