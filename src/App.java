import interfaz.VentanaFlota;
import modelos.*;

public class App {
    public static void main(String[] args) {
        GestorFlota gestor = new GestorFlota();
        new VentanaFlota(gestor);
    }
}
