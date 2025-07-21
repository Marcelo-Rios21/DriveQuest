package modelos;

import interfaces.Factura;

public class VehiculoCarga extends Vehiculo implements Factura {
    private double capacidadCarga;

    public VehiculoCarga() {
        super();
    }

    public VehiculoCarga(int anio, int arriendo, String marca, String modelo, String patente, double capacidadCarga) {
        super(anio, arriendo, marca, modelo, patente);
        this.capacidadCarga = capacidadCarga;
    }

    //GETTERS Y SETTERS

    public double getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(double capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    @Override
    public String mostrarDatos() {
        return "Vehiculo de carga\n" +
                "Patente: " + getPatente() + "\n" +
                "Marca: " + getMarca() + "\n" +
                "Modelo: " + getModelo() + "\n" +
                "Año: " + getAnio() + "\n" +
                "Dias de arriendo: " + getArriendo() + "\n" +
                "Capacidad de carga: " + getCapacidadCarga()+
                "\n--------------------------------";
    }

    @Override
    public String calcularBoleta() {
        double subtotal = getArriendo() * VALOR_DIARIO;
        double iva = subtotal * IVA;
        double descuento = subtotal * DESCUENTO_CARGA;
        double total = subtotal + iva - descuento;

        if (getArriendo() <= 0) {
            return "El numero de dias de arriendo debe ser mayor que cero.";
        }

        return String.format("""
            BOLETA - Vehículo de Carga
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
