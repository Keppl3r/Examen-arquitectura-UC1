/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.CompraController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import modelo.Compra;
import modelo.CompraObserver;
import modelo.DetalleCompra;
import modelo.Producto;
import modelo.Tarjeta;

/**
 *
 * @author keppler
 */
public class CompraView implements CompraObserver {

    private Compra modelo;
    private CompraController controlador;

    private JFrame frame;

    // productos
    private DefaultTableModel datosProductos;
    private JTable tablaProductos;
    private JTextField txtCantidad;
    private JButton btnAgregar;
    private JLabel lblAvisoProductos;

    // productos seleccionados
    private DefaultTableModel datosSeleccionados;
    private JTable tablaSeleccionados;
    private JLabel lblTotal;
    private JButton btnIrAPagar;
    private JLabel lblAvisoSeleccionados;

    // pago
    private JPanel panelCaptura;
    private JTextField txtTarjeta;
    private JButton btnBuscar;
    private JTextArea txtInfo;
    private JButton btnPagar;
    private JLabel lblAvisoPago;

    public CompraView(Compra modelo, CompraController controlador) {
        this.modelo = modelo;
        this.controlador = controlador;
        this.modelo.addObserver(this);
    }

    /**
     * construye y despliega la ventana
     */
    public void mostrar() {
        frame = new JFrame("Comprar producto");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // productos 
        datosProductos = new DefaultTableModel(new Object[]{"Nombre", "Costo"}, 0);
        tablaProductos = new JTable(datosProductos);
        tablaProductos.setDefaultEditor(Object.class, null);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        txtCantidad = new JTextField("1", 5);
        btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(new AgregarListener());
        lblAvisoProductos = crearAviso();

        JPanel panelCantidad = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCantidad.add(new JLabel("Cantidad:"));
        panelCantidad.add(txtCantidad);
        panelCantidad.add(btnAgregar);
        panelCantidad.add(lblAvisoProductos);

        JPanel panelProductos = new JPanel(new BorderLayout());
        panelProductos.setBorder(BorderFactory.createTitledBorder("Productos"));
        panelProductos.add(BorderLayout.CENTER, new JScrollPane(tablaProductos));
        panelProductos.add(BorderLayout.SOUTH, panelCantidad);

        // productos seleccionados
        datosSeleccionados = new DefaultTableModel(new Object[]{"Producto", "Cantidad", "Subtotal"}, 0);
        tablaSeleccionados = new JTable(datosSeleccionados);
        tablaSeleccionados.setDefaultEditor(Object.class, null);

        lblTotal = new JLabel("Total: $0.00");
        lblTotal.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        btnIrAPagar = new JButton("Ir a pagar");
        btnIrAPagar.addActionListener(new IrAPagarListener());
        lblAvisoSeleccionados = crearAviso();

        JPanel panelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelTotal.add(lblAvisoSeleccionados);
        panelTotal.add(lblTotal);
        panelTotal.add(btnIrAPagar);

        JPanel panelSeleccionados = new JPanel(new BorderLayout());
        panelSeleccionados.setBorder(BorderFactory.createTitledBorder("Productos seleccionados"));
        panelSeleccionados.add(BorderLayout.CENTER, new JScrollPane(tablaSeleccionados));
        panelSeleccionados.add(BorderLayout.SOUTH, panelTotal);

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.X_AXIS));
        panelSuperior.add(panelProductos);
        panelSuperior.add(panelSeleccionados);

        // pago 
        txtTarjeta = new JTextField(16);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new BuscarListener());

        panelCaptura = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCaptura.add(new JLabel("Número de tarjeta:"));
        panelCaptura.add(txtTarjeta);
        panelCaptura.add(btnBuscar);

        txtInfo = new JTextArea(10, 45);
        txtInfo.setEditable(false);
        txtInfo.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));

        btnPagar = new JButton("Pagar");
        btnPagar.addActionListener(new PagarListener());
        lblAvisoPago = crearAviso();

        JPanel panelBotonPagar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotonPagar.add(btnPagar);
        panelBotonPagar.add(lblAvisoPago);

        JPanel panelPago = new JPanel();
        panelPago.setLayout(new BoxLayout(panelPago, BoxLayout.Y_AXIS));
        panelPago.setBorder(BorderFactory.createTitledBorder("Pago"));
        panelPago.add(panelCaptura);
        panelPago.add(txtInfo);
        panelPago.add(panelBotonPagar);

        frame.getContentPane().add(BorderLayout.CENTER, panelSuperior);
        frame.getContentPane().add(BorderLayout.SOUTH, panelPago);

        // lista de productos 
        for (Producto producto : modelo.getProductos()) {
            datosProductos.addRow(new Object[]{producto.getNombre(), String.format("$%.2f", producto.getCosto())});
        }
        update();

        frame.setSize(960, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // clicks
    public void clickAgregar() {
        int fila = tablaProductos.getSelectedRow();
        String nombre = null;
        if (fila >= 0) {
            nombre = (String) datosProductos.getValueAt(fila, 0);
        }
        controlador.onAgregarClicked(nombre, txtCantidad.getText());
    }

    public void clickIrAPagar() {
        controlador.onIrAPagarClicked();
    }

    public void clickBuscar() {
        controlador.onBuscarClicked(txtTarjeta.getText());
    }

    public void clickPagar() {
        controlador.onPagarClicked();
    }

    // observer
    @Override
    public void update() {
        // productos seleccionados y total
        datosSeleccionados.setRowCount(0);
        for (DetalleCompra d : modelo.getDetalles()) {
            datosSeleccionados.addRow(new Object[]{
                d.getProducto().getNombre(), d.getCantidad(), String.format("$%.2f", d.getSubtotal())});
        }
        lblTotal.setText(String.format("Total: $%.2f", modelo.getTotal()));

        // avisos
        String mensaje = modelo.getMensaje();
        lblAvisoProductos.setText(" ");
        lblAvisoSeleccionados.setText(" ");
        lblAvisoPago.setText(" ");
        if (!mensaje.isEmpty()) {
            if (modelo.isEnPago()) {
                lblAvisoPago.setText(mensaje);
            } else if (modelo.getDetalles().isEmpty()) {
                lblAvisoSeleccionados.setText(mensaje);
            } else {
                lblAvisoProductos.setText(mensaje);
            }
        }

        // captura, datos de la tarjeta o ticket
        panelCaptura.setVisible(modelo.isEnPago() && !modelo.isPagada());
        btnPagar.setVisible(modelo.getTarjeta() != null && !modelo.isPagada());
        if (modelo.isPagada()) {
            mostrarTicket();
        } else if (modelo.getTarjeta() != null) {
            mostrarDatosTarjeta();
        } else {
            txtInfo.setText("");
        }
        txtInfo.setVisible(!txtInfo.getText().isEmpty());

        btnAgregar.setEnabled(!modelo.isPagada());
        btnIrAPagar.setEnabled(!modelo.isPagada());

        frame.revalidate();
        frame.repaint();
    }

    private void mostrarDatosTarjeta() {
        Tarjeta t = modelo.getTarjeta();
        txtInfo.setText("Banco emisor: " + t.getBancoEmisor()
                + "\nCiudad: " + t.getCiudad()
                + "\nNúmero de tarjeta: " + t.getNumero());
    }

    private void mostrarTicket() {
        Tarjeta t = modelo.getTarjeta();
        String fecha = modelo.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        txtInfo.setText("");
        txtInfo.append("------ TICKET DE COMPRA ------\n");
        txtInfo.append("Fecha: " + fecha + "\n");
        txtInfo.append("Banco: " + t.getBancoEmisor() + "\n");
        txtInfo.append("Ciudad: " + t.getCiudad() + "\n");
        txtInfo.append("Tarjeta: " + t.enmascararNumero() + "\n\n");

        for (DetalleCompra d : modelo.getDetalles()) {
            txtInfo.append(d.getProducto().getNombre() + " x" + d.getCantidad() + "   $" + d.getSubtotal() + "\n");
        }

        txtInfo.append("\nTotal: $" + modelo.getTotal());
    }

    private JLabel crearAviso() {
        JLabel aviso = new JLabel(" ");
        aviso.setForeground(new Color(176, 0, 32));
        return aviso;
    }

    // listeners
    class AgregarListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            clickAgregar();
        }
    }

    class IrAPagarListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            clickIrAPagar();
        }
    }

    class BuscarListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            clickBuscar();
        }
    }

    class PagarListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            clickPagar();
        }
    }
}
