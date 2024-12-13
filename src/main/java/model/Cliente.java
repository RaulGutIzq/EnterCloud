/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jdk.jshell.spi.ExecutionControl;

/**
 * Clase `Cliente` que encapsula la información básica de un cliente. Contiene
 * atributos como ID, correo electrónico, teléfono y el plan del cliente.
 * También incluye métodos para obtener los valores de estos atributos.
 *
 * @author CDC
 */
public class Cliente {

    // Atributos privados para encapsular los datos de un cliente.
    private int id; // Identificador único del cliente
    private String correo; // Correo electrónico del cliente
    private String telf; // Número de teléfono del cliente
    private byte plan; // Tipo de plan (codificado como byte)

    /**
     * Constructor de la clase Cliente. Permite inicializar un objeto Cliente
     * con los datos proporcionados.
     *
     * @param id Identificador único del cliente.
     * @param correo Correo electrónico del cliente.
     * @param telf Número de teléfono del cliente.
     * @param plan Tipo de plan (representado como un byte).
     */
    public Cliente(int id, String correo, String telf, byte plan) {
        this.id = id;
        this.correo = correo;
        this.telf = telf;
        this.plan = plan;
    }

    /**
     * Obtiene el identificador único del cliente.
     *
     * @return ID del cliente.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el correo electrónico del cliente.
     *
     * @return Correo electrónico del cliente.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Obtiene el número de teléfono del cliente.
     *
     * @return Teléfono del cliente.
     */
    public String getTelf() {
        return telf;
    }

    /**
     * Obtiene el tipo de plan del cliente.
     *
     * @return Plan del cliente representado como un byte.
     */
    public byte getPlan() {
        return plan;
    }

    /**
     * Método para calcular el almacenamiento restante del cliente. Este método
     * aún no está implementado y lanza una excepción si se llama.
     *
     * @return El almacenamiento restante del cliente (cuando esté
     * implementado).
     * @throws ExecutionControl.NotImplementedException Si el método no está
     * implementado.
     */
    public float almacenamientoRestante() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("No implementado hasta saber como nos devolverán el almacenamiento usado por el usuario");
    }
}
