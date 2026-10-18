/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author keppler
 */
public class Tarjeta {
    
    private String numero;
    private String bancoEmisor;
    private String ciudad;

    public Tarjeta() {
    }

    public Tarjeta(String numero, String bancoEmisor, String ciudad) {
        this.numero = numero;
        this.bancoEmisor = bancoEmisor;
        this.ciudad = ciudad;
    }

    public String getNumero() {
        return numero;
    }

    public String getBancoEmisor() {
        return bancoEmisor;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setBancoEmisor(String bancoEmisor) {
        this.bancoEmisor = bancoEmisor;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    
    private String enmascararNumero(){
        if (numero == null || numero.length() < 16) {
       throw new IllegalArgumentException("Numero de tarjeta menor a 16 digitos");
    }
        String digitos=numero.substring(numero.length()-4);
        
        return "****" + digitos;
}
    }
