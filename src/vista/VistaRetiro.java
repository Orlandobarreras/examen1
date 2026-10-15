package vista;

import controlador.ControladorRetiro;
import modelo.Recibo;

import javax.swing.*;
import java.awt.*;

public class VistaRetiro extends JFrame {
    private static final String PANTALLA_MENU = "menu";
    private static final String PANTALLA_RETIRO = "retiro";

    private ControladorRetiro controlador;
    private CardLayout cartas;
    private JPanel contenedor;
    private JPanel panelMontos;
    private JPanel panelDerecho;

    public VistaRetiro() {
        super("Cajero Automático");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 420);
        setLocationRelativeTo(null);

        cartas = new CardLayout();
        contenedor = new JPanel(cartas);
        contenedor.add(crearPantalla(), PANTALLA_MENU);
        contenedor.add(crearPantallaRetiro(), PANTALLA_RETIRO);
        add(contenedor);
    }

    private JPanel crearPantalla() {
        JPanel menu = new JPanel(new GridBagLayout());
        JPanel interior = new JPanel(new GridLayout(0, 1, 10, 15));

        JLabel titulo = new JLabel("Cajero Automatico (Solor retiros)", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));

        JButton btnRetirar = new JButton("Retirar efectivo");
        btnRetirar.addActionListener(e -> controlador.seleccionarRetirar());

        interior.add(titulo);
        interior.add(btnRetirar);
        menu.add(interior);
        return menu;
    }

    private JPanel crearPantallaRetiro() {
        JPanel pantalla = new JPanel(new GridLayout(1, 2, 10, 0));

        panelMontos = new JPanel(new GridLayout(0, 2, 8, 8));
        panelMontos.setBorder(BorderFactory.createTitledBorder("Seleccione la cantidad a retirar"));

        panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBorder(BorderFactory.createTitledBorder("Información"));

        pantalla.add(panelMontos);
        pantalla.add(panelDerecho);
        return pantalla;
    }

    public void setControlador(ControladorRetiro c) {
        this.controlador = c;
    }

    public void mostrarMenu() {
        cartas.show(contenedor, PANTALLA_MENU);
    }

    public void mostrarPantallaRetiro() {
        cartas.show(contenedor, PANTALLA_RETIRO);
    }

    public void mostrarMontos(double[] montos) {
        panelMontos.removeAll();
        for (double m : montos) {
            JButton b = new JButton(String.format("$%.2f", m));
            b.addActionListener(e -> controlador.seleccionarMonto(m));
            panelMontos.add(b);
        }
        JButton otra = new JButton("Otra cantidad");
      
        panelMontos.add(otra);
        panelMontos.revalidate();
        panelMontos.repaint();
    }

 
    public String solicitarCantidad() {
        return JOptionPane.showInputDialog(this, "Ingrese la cantidad a retirar (múltiplos de $200):",
                "Otra cantidad", JOptionPane.PLAIN_MESSAGE);
    }

    public void mostrarEstadoCuenta(String nombre, double saldo, double monto,
                                    double comision, double saldoFinal) {
        JPanel datos = new JPanel(new GridLayout(0, 1, 5, 5));
        datos.add(new JLabel("Nombre: " + nombre));
        datos.add(new JLabel(String.format("Monto actual: $%.2f", saldo)));
        datos.add(new JLabel(String.format("Monto a retirar: $%.2f", monto)));
        datos.add(new JLabel(String.format("Comisión: $%.2f", comision)));
        datos.add(new JLabel(String.format("Monto final: $%.2f", saldoFinal)));

        JButton retirar = new JButton("Retirar efectivo");
        retirar.addActionListener(e -> controlador.retirarEfectivo());
        JButton cancelar = new JButton("Cancelar");
        cancelar.addActionListener(e -> controlador.cancelar());

        JPanel botones = new JPanel(new FlowLayout());
        botones.add(retirar);
        botones.add(cancelar);

        cambiarPanelDerecho(datos, botones, "Estado de cuenta");
    }

    public void mostrarRecibo(Recibo r) {
        JPanel datos = new JPanel(new GridLayout(0, 1, 5, 5));
        datos.add(new JLabel("Transacción exitosa. Tome su efectivo"));
        datos.add(new JLabel("Cuenta: **** **** **** " + r.getUltimosDigitos()));
        datos.add(new JLabel("Nombre: " + r.getNombre()));
        datos.add(new JLabel(String.format("Cantidad retirada: $%.2f", r.getCantidadRetirada())));
        datos.add(new JLabel(String.format("Comisión: $%.2f", r.getComision())));
        datos.add(new JLabel("Imprimiendo ticket..."));

        JButton finalizar = new JButton("Finalizar");
        finalizar.addActionListener(e -> controlador.finalizar());
        JPanel botones = new JPanel(new FlowLayout());
        botones.add(finalizar);

        cambiarPanelDerecho(datos, botones, "Recibo de retiro");
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    public void limpiarPanelDerecho() {
        cambiarPanelDerecho(new JPanel(), null, "Información");
    }

    private void cambiarPanelDerecho(JPanel centro, JPanel abajo, String titulo) {
        panelDerecho.removeAll();
        panelDerecho.setBorder(BorderFactory.createTitledBorder(titulo));
        panelDerecho.add(centro, BorderLayout.CENTER);
        if (abajo != null) {
            panelDerecho.add(abajo, BorderLayout.SOUTH);
        }
        panelDerecho.revalidate();
        panelDerecho.repaint();
    }
}
