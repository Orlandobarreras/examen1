package modelo;

public class Cuenta {
    public static final double COMISION = 30.00;

    private String numero;
    private double saldo;
    private Cliente cliente;

    public Cuenta(String numero, double saldo, Cliente cliente) {
        this.numero = numero;
        this.saldo = saldo;
        this.cliente = cliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getUltimosCuatroDigitos() {
        return numero.substring(numero.length() - 4);
    }

    public double calcularComision(double monto) {
        return COMISION;
    }

    public double calcularSaldoFinal(double monto) {
        return saldo - monto - calcularComision(monto);
    }

    public boolean tieneSaldoSuficiente(double monto) {
        return calcularSaldoFinal(monto) >= 0;
    }

    public void retirar(double monto) {
        saldo = calcularSaldoFinal(monto);
    }
}
