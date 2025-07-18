package modelos;

public class VehiculoPasajeros extends Vehiculo {
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
                "Capacidad maxima de pasajeros: " + getMaxPasajeros();
    }
    
}
