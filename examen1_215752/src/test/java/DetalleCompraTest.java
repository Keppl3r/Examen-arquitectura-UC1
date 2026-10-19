/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import modelo.DetalleCompra;
import modelo.Producto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author keppler
 */
public class DetalleCompraTest {
   
    @Test
    public void testSubtotal() {
        Producto mouse = new Producto("Mouse", 15.00);
        DetalleCompra detalle = new DetalleCompra(mouse, 3);

        assertEquals(45.00, detalle.getSubtotal());
    }

    @Test
    public void testSumarCantidad() {
        Producto mouse = new Producto("Mouse", 15.00);
        DetalleCompra detalle = new DetalleCompra(mouse, 2);

        detalle.sumarCantidad(1);

        assertEquals(3, detalle.getCantidad());
        assertEquals(45.00, detalle.getSubtotal());
    }

    
}
