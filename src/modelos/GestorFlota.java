package modelos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import excepciones.PatenteDuplicadaException;

public class GestorFlota {
    private List<Vehiculo> vehiculos;
    private Map<String, Vehiculo> mapaPatentes;

    public GestorFlota() {
        this.vehiculos = new ArrayList<>();
        this.mapaPatentes = new HashMap<>();
    }

    //GETTERS Y SETTERS

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }  
    
    public void agregarVehiculo(Vehiculo nuevo) throws PatenteDuplicadaException {
        if (mapaPatentes.containsKey(nuevo.getPatente())) {
            throw new PatenteDuplicadaException("Ya existe un vehiculo con la patente: " + nuevo.getPatente());
        }
        vehiculos.add(nuevo);
        mapaPatentes.put(nuevo.getPatente(), nuevo);
    }

    public Vehiculo buscarVehiculoPorPatente(String patente) {
        return mapaPatentes.get(patente);
    }

    public void listarVehiculos() {
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehiculos registrados en la flota.");
            return;
        }

        System.out.println("Listado de vehiculos en la flota:\n");
        for (Vehiculo v : vehiculos) {
            System.out.println(v.mostrarDatos());
            System.out.println("---------------------------");
        }
    }

    public List<Vehiculo> obtenerVehiculosLargoPlazo() {
        List<Vehiculo> largoPlazo = new ArrayList<>();

        for (Vehiculo v : vehiculos) {
            if (v.getArriendo() >= 7) {
                largoPlazo.add(v);
            }
        }

        return largoPlazo;
    }    
}
