package modelos;

public class VehiculoCarga extends Vehiculo {
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
                "Capacidad de carga: " + getCapacidadCarga();
    }

}
