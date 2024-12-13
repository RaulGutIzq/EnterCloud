/**
 * Clase ClientesDAO para manejar el acceso a datos de clientes.
 * Esta clase permite leer y escribir objetos Cliente en un archivo binario.
 *
 * Modo de apertura del archivo:
 * - "r": Solo lectura
 * - "w": Escritura (sobrescribe el archivo si ya existe)
 * - "a": Escritura en modo append (agrega datos al final del archivo)
 *
 * Dependencias: Cliente debe ser una clase que implemente los métodos necesarios para
 * acceder a sus atributos: getId(), getCorreo(), getTelf() y getPlan().
 *
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
 * @author CDC
 */
public class ClientesDAO {

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
    public ClientesDAO(String ruta, String modo) throws FileNotFoundException {
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
    public ClientesDAO(File arch, String modo) throws FileNotFoundException {
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
     * Lee un objeto Cliente del archivo.
     *
     * @return Un objeto Cliente leído del archivo o null si no hay entrada.
     * @throws EOFException Si se alcanza el final del archivo.
     * @throws IOException Si ocurre un error de lectura.
     */
    public Cliente leer() throws EOFException, IOException {
        if (entrada != null) {
            return new Cliente(entrada.readInt(), entrada.readUTF(), entrada.readUTF(), entrada.readByte());
        } else {
            return null;
        }

    }

    /**
     * Escribe un objeto Cliente en el archivo.
     *
     * @param cliente El objeto Cliente a escribir.
     * @throws IOException Si ocurre un error de escritura.
     */
    public void escribir(Cliente cliente) throws IOException {
        if (salida != null) {
            salida.writeInt(cliente.getId());
            salida.writeUTF(cliente.getCorreo());
            salida.writeUTF(cliente.getTelf());
            salida.writeByte(cliente.getPlan());
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

    /**
     * Método no implementado que se debe adaptar o eliminar. Actualmente lanza
     * una excepción.
     */
    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
