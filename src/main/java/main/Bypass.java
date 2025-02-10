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

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Inicio v = new Inicio();
        InicioController c = new InicioController(v, new Cliente(1, "raul@raul.com", "666666666", (byte) 1));
        v.setVisible(true);
    }
}

/*

menu ayuda en medio
menu subida y descarga en el centro de la pantalla
los 3 menus tamaño fijo
los 3 menus dejan inusable inicio.java hasta que se cierren

*/