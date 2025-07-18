package interfaces;

public interface Factura {
    double IVA = 0.19;
    double DESCUENTO_CARGA = 0.07;
    double DESCUENTO_PASAJEROS = 0.12;
    double VALOR_DIARIO = 50000;

    String calcularBoleta();
}
