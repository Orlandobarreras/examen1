package controlador;

import modelo.Cuenta;
import modelo.Recibo;
import vista.VistaRetiro;

public class ControladorRetiro {
    private Cuenta cuenta;
    private VistaRetiro vista;
    private double montoSeleccionado = 0;

    public ControladorRetiro(Cuenta cuenta, VistaRetiro vista) {
        this.cuenta = cuenta;
        this.vista = vista;
        this.vista.setControlador(this);
    }

    public void iniciar() {
        vista.mostrarMontos(new double[]{200, 400, 600, 800, 1000});
        vista.mostrarMenu();
        vista.setVisible(true);
    }


    public void seleccionarRetirar() {
        montoSeleccionado = 0;
        vista.limpiarPanelDerecho();
        vista.mostrarPantallaRetiro();
    }

    public void seleccionarMonto(double monto) {
        if (!cuenta.tieneSaldoSuficiente(monto)) {
            montoSeleccionado = 0;
            vista.limpiarPanelDerecho();
            vista.mostrarError("Saldo insuficiente para retirar $"
                    + String.format("%.2f", monto) + " más la comisión.");
            return;
        }
        montoSeleccionado = monto;
        double comision = cuenta.calcularComision(monto);
        double saldoFinal = cuenta.calcularSaldoFinal(monto);
        vista.mostrarEstadoCuenta(cuenta.getCliente().getNombre(),
                cuenta.getSaldo(), monto, comision, saldoFinal);
    }


    

    public void retirarEfectivo() {
        if (montoSeleccionado <= 0) {
            vista.mostrarError("Seleccione primero una cantidad.");
            return;
        }
        double comision = cuenta.calcularComision(montoSeleccionado);
        cuenta.retirar(montoSeleccionado);
        Recibo recibo = new Recibo(cuenta.getUltimosCuatroDigitos(),
                cuenta.getCliente().getNombre(), montoSeleccionado, comision);
        montoSeleccionado = 0;
        vista.mostrarRecibo(recibo);
    }

  
    public void cancelar() {
        montoSeleccionado = 0;
        vista.limpiarPanelDerecho();
        vista.mostrarMenu();
    }

  
    public void finalizar() {
        montoSeleccionado = 0;
        vista.limpiarPanelDerecho();
        vista.mostrarMenu();
    }
}
