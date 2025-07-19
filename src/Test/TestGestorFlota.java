package Test;

import modelos.GestorFlota;
import modelos.Vehiculo;
import modelos.VehiculoCarga;
import modelos.VehiculoPasajeros;

public class TestGestorFlota {
    public static void main(String[] args) {
        GestorFlota gestor = new GestorFlota();

        try {
            Vehiculo carga1 = new VehiculoCarga(2018 , 10, "Volvo", "FH", "ABCD12", 15000);
            Vehiculo carga2 = new VehiculoCarga(2017 , 5, "Subaru", "Impreza", "XYZ789", 20000);
            Vehiculo pasajero1 = new VehiculoPasajeros(2022,8,"Chevrolet", "Express", "LMN345", 12);

            gestor.agregarVehiculo(carga1);
            gestor.agregarVehiculo(carga2);
            gestor.agregarVehiculo(pasajero1);

            // Test de duplicado
            try {
                Vehiculo repetido = new VehiculoCarga(2018 , 10, "Volvo", "FH", "ABCD12", 15000);
                gestor.agregarVehiculo(repetido);
            } catch (excepciones.PatenteDuplicadaException e) {
                System.out.println("Deteccion correcta de patente duplicada: " + e.getMessage());
            }

            // Mostrar todos los vehiculos
            System.out.println("\nVehiculos registrados:");
            gestor.listarVehiculos();

            // Mostrar vehiculos con arriendo >= 7 dias
            System.out.println("\nVehículos con arriendo largo plazo (≥ 7 días):");
            for (Vehiculo v : gestor.obtenerVehiculosLargoPlazo()) {
                System.out.println(v.mostrarDatos());
                System.out.println("------");
            }

            // Test de busqueda rapida por patente
            Vehiculo buscado = gestor.buscarVehiculoPorPatente("LMN345");
            System.out.println("\nBúsqueda por patente LMN345:");
            System.out.println(buscado != null ? buscado.mostrarDatos() : "No se encontró vehículo.");

        } catch (Exception e) {
            System.err.println("Error en el test: " + e.getMessage());
        }
    }
}
