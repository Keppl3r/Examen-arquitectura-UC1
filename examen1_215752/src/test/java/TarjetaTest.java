/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import modelo.Tarjeta;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author keppler
 */
public class TarjetaTest {
    
    @Test
    public void testEnmascararNumero() {
        Tarjeta tarjeta = new Tarjeta("0000000000001234", "pruebin", "chucarit");

        assertEquals("****1234", tarjeta.enmascararNumero());
    }
}
