/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import model.Plan;
import jdk.jshell.spi.ExecutionControl;

/**
 *
 * @author DAM2
 */
public class Cliente {
    private int id;
    private String correo;
    private String telf;
    private byte plan;

    public Cliente(int id, String correo, String telf, byte plan) {
        this.id = id;
        this.correo = correo;
        this.telf = telf;
        this.plan = plan;
    }

    public int getId() {
        return id;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelf() {
        return telf;
    }

    public byte getPlan() {
        return plan;
    }
    
    public float almacenamientoRestante() throws ExecutionControl.NotImplementedException{
        throw new ExecutionControl.NotImplementedException("No implementado hasta saber como nos devolverán el almacenamiento usado por el usuario");
    }
}
