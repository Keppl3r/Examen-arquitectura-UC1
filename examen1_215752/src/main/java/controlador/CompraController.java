/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.Compra;

/**
 *
 * @author keppler
 */
public class CompraController {

    private Compra modelo;

    public CompraController(Compra modelo) {
        this.modelo = modelo;
    }

    public void onAgregarClicked(String nombreProducto, String cantidadTexto) {
        modelo.agregarProducto(nombreProducto, convertirCantidad(cantidadTexto));
    }

    public void onIrAPagarClicked() {
        modelo.iniciarPago();
    }

    public void onBuscarClicked(String numero) {
        modelo.buscarTarjeta(numero.trim());
    }

    public void onPagarClicked() {
        modelo.pagar();
    }

    // convierte el String del formulario a un entero
    private int convertirCantidad(String texto) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
