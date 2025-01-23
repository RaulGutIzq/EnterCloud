/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import controller.InicioController;
import model.Cliente;
import view.Inicio;

/**
 *
 * @author raulin
 */
public class Bypass {

    public static void main(String[] args) {
        Inicio v = new Inicio();
        InicioController c = new InicioController(v, new Cliente(1, "raul@raul.com", "666666666", (byte) 1));
        v.setVisible(true);
    }
}
