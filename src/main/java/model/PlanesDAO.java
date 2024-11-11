/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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

    private DataInputStream entrada;
    private DataOutputStream salida;

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

    public Plan leer() throws EOFException, IOException {
        if (entrada != null) {
            return new Plan(entrada.readByte(), entrada.readFloat(), entrada.readFloat());

        } else {
            return null;
        }

    }

    public void escribir(Plan plan) throws IOException {
        if (salida != null) {
            salida.writeByte(plan.getId());
            salida.writeFloat(plan.getCapacidad());
            salida.writeFloat(plan.getPrecio());

        }
    }

    public void cerrar() throws IOException {
        if (entrada != null) {
            entrada.close();
        } else if (salida != null) {
            salida.close();
        }

    }
}
