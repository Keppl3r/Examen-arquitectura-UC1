/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.List;

/**
 * Esta clase es una simulacion de un banco que guarda las tarjetas registradas y procesa el pago
 * @author keppler
 */
public class Banco {
    
     private List<Tarjeta> tarjetas;

    public Banco(List<Tarjeta> tarjetas) {
        this.tarjetas = tarjetas;
    }

    
    public Tarjeta buscarTarjeta(String numero) {
        if (numero == null || numero.length()<16) {
            return null;
        }
        for (Tarjeta tarjeta : tarjetas) {
            if (tarjeta.getNumero().equals(numero)) {
                return tarjeta;
            }
        }
        return null;
    }

    // tarjeta simulada sin fondos, termina con 9999  
    public String procesarPago(Tarjeta tarjeta, double total) {
        if (tarjeta.getNumero().endsWith("9999")) {
            return null;
        }
        return "APROBADO";
    }
}
