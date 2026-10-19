

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
import controlador.CompraController;
import java.util.ArrayList;
import java.util.List;
import modelo.Banco;
import modelo.Catalogo;
import modelo.Compra;
import modelo.Producto;
import modelo.Tarjeta;
import vista.CompraView;

/**
 *
 * @author keppler
 */
public class App {

    public static void main(String[] args) {

        // Datos simulados
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto("Teclado Mecanico", 45.00));
        productos.add(new Producto("Mouse Optico", 15.00));
        productos.add(new Producto("Monitor 24\"", 120.00));
        productos.add(new Producto("Auriculares USB", 30.00));
        productos.add(new Producto("Camara Web HD", 25.00));
        productos.add(new Producto("Cable HDMI 2m", 8.00));

        List<Tarjeta> tarjetas = new ArrayList<>();
        tarjetas.add(new Tarjeta("0000000000001234", "Banco Nacional", "Ciudad de Mexico"));
        tarjetas.add(new Tarjeta("0000000000001111", "BBVA", "Ciudad Obregon"));
        tarjetas.add(new Tarjeta("0000000000000000", "MercadoPago", "Navojoa"));

        Catalogo catalogo = new Catalogo(productos);
        Banco banco = new Banco(tarjetas);
        Compra modelo = new Compra(catalogo, banco);

        CompraController controlador = new CompraController(modelo);
        CompraView vista = new CompraView(modelo, controlador);
        vista.mostrar();
    }

}
