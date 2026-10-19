
import java.util.ArrayList;
import java.util.List;
import modelo.Banco;
import modelo.Catalogo;
import modelo.Compra;
import modelo.Producto;
import modelo.Tarjeta;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author keppler
 */
public class CompraTest {

    private Compra crearCompra() {
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto("Mouse Optico", 15.00));
        productos.add(new Producto("Teclado Mecanico", 45.00));

        List<Tarjeta> tarjetas = new ArrayList<>();
        tarjetas.add(new Tarjeta("0000000000001234", "Coppel", "Empalme"));
        tarjetas.add(new Tarjeta("0000000000000000", "Amex", "Movas"));

        return new Compra(new Catalogo(productos), new Banco(tarjetas));
    }

    @Test
    public void testAgregarProducto() {
        Compra compra = crearCompra();

        compra.agregarProducto("Mouse Optico", 2);
        compra.agregarProducto("Teclado Mecanico", 1);

        assertEquals(2, compra.getDetalles().size());
        assertEquals(75.00, compra.getTotal());
    }

    @Test
    public void testCantidadInvalida() {
        Compra compra = crearCompra();

        compra.agregarProducto("Mouse Optico", 0);

        assertEquals(0, compra.getDetalles().size());
        assertEquals("cantidad invalida", compra.getMensaje());
    }

    @Test
    public void testProductoRepetido() {
        Compra compra = crearCompra();

        compra.agregarProducto("Mouse Optico", 2);
        compra.agregarProducto("Mouse Optico", 1);

        assertEquals(1, compra.getDetalles().size());
        assertEquals(45.00, compra.getTotal());
    }

    @Test
    public void testListaVacia() {
        Compra compra = crearCompra();

        compra.iniciarPago();

        assertFalse(compra.isEnPago());
        assertEquals("debe agregar al menos un producto", compra.getMensaje());
    }

    @Test
    public void testTarjetaInvalida() {
        Compra compra = crearCompra();

        compra.buscarTarjeta("99999999999");

        assertNull(compra.getTarjeta());
        assertEquals("tarjeta no valida", compra.getMensaje());
    }

    @Test
    public void testFondosInsuficientes() {
        Compra compra = crearCompra();
        compra.agregarProducto("Mouse Optico", 1);
        compra.iniciarPago();
        compra.buscarTarjeta("0000000000000000");

        compra.pagar();

        assertFalse(compra.isPagada());
        assertEquals("fondos insuficientes", compra.getMensaje());
    }

    @Test
    public void testPagoExitoso() {
        Compra compra = crearCompra();
        compra.agregarProducto("Mouse Optico", 1);
        compra.iniciarPago();
        compra.buscarTarjeta("0000000000001234");

        compra.pagar();

        assertTrue(compra.isPagada());
    }
}
