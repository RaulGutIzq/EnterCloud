/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clientes;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author DAM2
 */
public class AdministracionClientes {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        ClientesDAO fich = new ClientesDAO("Clientes.dat", "w");
        fich.escribir(new Cliente(1234, "raul@raul.com", "611465858", (byte) 2));
        fich.escribir(new Cliente(5678, "coto@coto.com", "123456789", (byte) 2));
        fich.escribir(new Cliente(1111, "fraile@fraile.com", "987654321", (byte) 1));
        fich.cerrar();
    }
}
