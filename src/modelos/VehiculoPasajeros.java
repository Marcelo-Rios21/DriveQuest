package modelos;

import interfaces.Factura;

public class VehiculoPasajeros extends Vehiculo implements Factura {
    private int maxPasajeros;

    public VehiculoPasajeros() {
        super();
    }

    public VehiculoPasajeros(int anio, int arriendo, String marca, String modelo, String patente, int maxPasajeros) {
        super(anio, arriendo, marca, modelo, patente);
        this.maxPasajeros = maxPasajeros;
    }

    public int getMaxPasajeros() {
        return maxPasajeros;
    }

    public void setMaxPasajeros(int maxPasajeros) {
        this.maxPasajeros = maxPasajeros;
    }

    @Override
    public String mostrarDatos() {
        return "Vehiculo de pasajeros\n" +
                "Patente: " + getPatente() + "\n" +
                "Marca: " + getMarca() + "\n" +
                "Modelo: " + getModelo() + "\n" +
                "Año: " + getAnio() + "\n" +
                "Dias de arriendo: " + getArriendo() + "\n" +
                "Capacidad maxima de pasajeros: " + getMaxPasajeros()+
                "\n--------------------------------";
    }

    @Override
    public String calcularBoleta() {
        double subtotal = getArriendo() * VALOR_DIARIO;
        double iva = subtotal * IVA;
        double descuento = subtotal * DESCUENTO_PASAJEROS;
        double total = subtotal + iva - descuento;

        if (getArriendo() <= 0) {
            return "El numero de dias de arriendo debe ser mayor que cero.";
        }

        return String.format("""
            BOLETA - Vehículo de Pasajeros
            Patente: %s
            Dias de arriendo: %d
            ---------------------------
            Subtotal: $%.2f
            IVA: $%.2f
            Descuento: -$%.2f
            ---------------------------
            Total a pagar: $%.2f
            """,
            getPatente(), getArriendo(),
            subtotal, iva, descuento, total
        );
    } 

    @Override
    public String toString() {
        return mostrarDatos();
    }

    

}
