package modelos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    public void cargarDesdeArchivo(String ruta) {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length != 7) {
                    System.out.println("Formato incorrecto: " + linea);
                    continue;
                }

                String tipo = partes[0];
                String patente = partes[1];
                String marca = partes[2];
                String modelo = partes[3];
                int anio = Integer.parseInt(partes[4]);
                int dias = Integer.parseInt(partes[5]);

                if (dias <= 0) {
                    System.out.println("Días de arriendo invalidos para " + patente + ": " + dias);
                    continue;
                }

                Vehiculo vehiculo = null;
                if (tipo.equalsIgnoreCase("Carga")) {
                    double capacidad = Double.parseDouble(partes[6]);
                    vehiculo = new VehiculoCarga(anio, dias, marca, modelo, patente, capacidad);
                } else if (tipo.equalsIgnoreCase("Pasajeros")) {
                    int maxPasajeros = Integer.parseInt(partes[6]);
                    vehiculo = new VehiculoPasajeros(anio, dias, marca, modelo, patente, maxPasajeros);
                } else {
                    System.out.println("Tipo desconocido: " + tipo);
                    continue;
                }

                try {
                    agregarVehiculo(vehiculo);
                } catch (PatenteDuplicadaException e) {
                    System.out.println("Vehiculo duplicado: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir numero: " + e.getMessage());
        }
    }


    public void guardarEnArchivo(String archivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (Vehiculo v : vehiculos) {
                String tipo = (v instanceof VehiculoCarga) ? "CARGA" : "PASAJEROS";
                StringBuilder sb = new StringBuilder();
                sb.append(tipo).append(";");
                sb.append(v.getPatente()).append(";");
                sb.append(v.getMarca()).append(";");
                sb.append(v.getModelo()).append(";");
                sb.append(v.getAnio()).append(";");
                sb.append(v.getArriendo()).append(";");

                if (v instanceof VehiculoCarga carga) {
                    sb.append(carga.getCapacidadCarga());
                } else if (v instanceof VehiculoPasajeros pas) {
                    sb.append(pas.getMaxPasajeros());
                }

                writer.write(sb.toString());
                writer.newLine();
            }
            System.out.println("Vehiculos guardados exitosamente en " + archivo);
        } catch (IOException e) {
            System.err.println("Error al guardar en archivo: " + e.getMessage());
        }
    }
}
