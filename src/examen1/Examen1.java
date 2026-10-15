
package examen1;

import controlador.ControladorRetiro;
import javax.swing.SwingUtilities;
import modelo.Cliente;
import modelo.Cuenta;
import vista.VistaRetiro;


public class Examen1 {
 public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Cliente cliente = new Cliente("Juan Carlos Bodoque");
            Cuenta cuenta = new Cuenta("4152313412341234", 1030.50, cliente);
            VistaRetiro vista = new VistaRetiro();
            ControladorRetiro controlador = new ControladorRetiro(cuenta, vista);
            controlador.iniciar();
        });
    }
    }
    

