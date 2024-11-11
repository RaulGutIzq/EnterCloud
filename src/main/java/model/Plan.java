/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author DAM2
 */
public class Plan {

    private byte id;
    private float capacidad;
    private float precio;

    public Plan(byte id, float capacidad, float precio) {
        this.id = id;
        this.capacidad = capacidad;
        this.precio = precio;
    }

    public byte getId() {
        return id;
    }

    public float getCapacidad() {
        return capacidad;
    }

    public float getPrecio() {
        return precio;
    }

}
