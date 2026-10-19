/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author keppler
 */
public class Compra {

    private List<DetalleCompra> detalles = new ArrayList<>();
    private double total;
    private LocalDateTime fecha;
    private Tarjeta tarjeta;
    private boolean pagada;
    private boolean enPago;
    private String mensaje = "";
    private Catalogo catalogo;
    private Banco banco;
    private List<CompraObserver> observers = new ArrayList<>();

    public Compra(Catalogo catalogo, Banco banco) {
        this.catalogo = catalogo;
        this.banco = banco;
    }

    public void addObserver(CompraObserver obs) {
        observers.add(obs);
    }

    public void notifyObservers() {
        for (CompraObserver obs : observers) {
            obs.update();
        }
    }

    public void agregarProducto(String nombreProducto, int cantidad) {
        mensaje = ""; // esto es para limpiar el ultimo mensaje guardado
        if (!validarCantidad(cantidad)) {
            mensaje = "cantidad invalida";
            notifyObservers();
            return;
        }
        Producto producto = catalogo.buscarProducto(nombreProducto);
        if (producto == null) {
            mensaje = "seleccione un producto";
            notifyObservers();
            return;
        }
        DetalleCompra detalle = buscarDetalle(producto);
        if (detalle != null) {
            detalle.sumarCantidad(cantidad);
        } else {
            detalles.add(new DetalleCompra(producto, cantidad));
        }
        calcularTotal();
        notifyObservers();
    }

    public void iniciarPago() {
        mensaje = "";
        if (detalles.isEmpty()) {                     // FA3
            mensaje = "debe agregar al menos un producto";
        } else {
            enPago = true;
        }
        notifyObservers();
    }

    public void buscarTarjeta(String numero) {
        mensaje = "";
        Tarjeta encontrada = banco.buscarTarjeta(numero);
        if (encontrada == null) {
            tarjeta = null;
            mensaje = "tarjeta no valida";
        } else {
            tarjeta = encontrada;
        }
        notifyObservers();
    }

    public void pagar() {
        mensaje = "";
        if (tarjeta == null) {
            mensaje = "tarjeta no valida";
            notifyObservers();
            return;
        }
        String resultado = banco.procesarPago(tarjeta, total);
        if ("APROBADO".equals(resultado)) {
            registrar();
        } else {
            mensaje = "fondos insuficientes";
        }
        notifyObservers();
    }

    private boolean validarCantidad(int cantidad) {
        return cantidad > 0;
    }

    private DetalleCompra buscarDetalle(Producto producto) {
        for (DetalleCompra d : detalles) {
            if (d.getProducto() == producto) {
                return d;
            }
        }
        return null;
    }

    private void calcularTotal() {
        total = 0;
        for (DetalleCompra d : detalles) {
            total += d.getSubtotal();
        }
    }

    private void registrar() {
        pagada = true;
        fecha = LocalDateTime.now();
    }

    //consultas de estado
    public List<Producto> getProductos() {
        return catalogo.getProductos();
    }

    public List<DetalleCompra> getDetalles() {
        return detalles;
    }

    public double getTotal() {
        return total;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public boolean isEnPago() {
        return enPago;
    }

    public boolean isPagada() {
        return pagada;
    }

    public String getMensaje() {
        return mensaje;
    }
}
