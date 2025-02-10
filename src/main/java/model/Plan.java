/**
 * Clase Plan que representa un plan con identificador, capacidad y precio.
 * 
 * Esta clase se utiliza para modelar la información de un plan, incluyendo
 * su capacidad y su costo asociado.
 * 
 * @author CDC
 */
package model;

/**
 *
 * @author DAM2
 */
public class Plan {

    // Identificador único del plan
    private byte id;

    // Capacidad del plan, representada como un número flotante
    private float capacidad;

    // Precio del plan, representado como un número flotante
    private float precio;

    /**
     * Constructor para inicializar un objeto Plan con sus valores.
     * 
     * @param id Identificador del plan.
     * @param capacidad Capacidad del plan.
     * @param precio Precio del plan.
     */
    public Plan(byte id, float capacidad, float precio) {
        this.id = id;
        this.capacidad = capacidad;
        this.precio = precio;
    }

    /**
     * Obtiene el identificador del plan.
     * 
     * @return Identificador del plan.
     */
    public byte getId() {
        return id;
    }

    /**
     * Obtiene la capacidad del plan.
     * 
     * @return Capacidad del plan.
     */
    public float getCapacidad() {
        return capacidad;
    }

    /**
     * Obtiene el precio del plan.
     * 
     * @return Precio del plan.
     */
    public float getPrecio() {
        return precio;
    }
}
