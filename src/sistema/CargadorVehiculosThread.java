package sistema;

import modelos.GestorFlota;

public class CargadorVehiculosThread implements Runnable {
    private GestorFlota gestor;
    private String archivo;

    public CargadorVehiculosThread(GestorFlota gestor, String archivo) {
        this.gestor = gestor;
        this.archivo = archivo;
    }

    @Override
    public void run() {
        try {
            System.out.println("Iniciando carga de vehiculos...");
            Thread.sleep(2000);
            gestor.cargarDesdeArchivo(archivo);
            System.out.println("Vehiculos cargados con exito.");
        } catch (Exception e) {
            System.out.println("Error al cargar el hilo: " + e.getMessage());
        }
    }
}
