package interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import excepciones.PatenteDuplicadaException;
import modelos.GestorFlota;
import modelos.Vehiculo;
import modelos.VehiculoCarga;
import modelos.VehiculoPasajeros;

public class VentanaFlota extends JFrame {
    private GestorFlota gestor;
    private JTextArea areaTexto; 

    public VentanaFlota(GestorFlota gestor) {
        this.gestor = gestor;

        setTitle("--- Gestion de Flota ---");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(7, 1, 10, 10));

        JButton btnAgregar = new JButton("Agregar Vehiculo");
        JButton btnListar = new JButton("Listar Vehiculos");
        JButton btnBoleta = new JButton("Ver Boleta");
        JButton btnResumen = new JButton("Resumen Largo Plazo");
        JButton btnCargarCSV = new JButton("Cargar CSV");
        JButton btnGuardarCSV = new JButton("Guardar CSV");
        JButton btnSalir = new JButton("Salir");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnListar);
        panelBotones.add(btnBoleta);
        panelBotones.add(btnResumen);
        panelBotones.add(btnCargarCSV);
        panelBotones.add(btnGuardarCSV);
        panelBotones.add(btnSalir);

        panelPrincipal.add(panelBotones, BorderLayout.WEST); 

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);

        JScrollPane scroll = new JScrollPane(areaTexto); 
        panelPrincipal.add(scroll, BorderLayout.CENTER); 


        add(panelPrincipal);

        btnAgregar.addActionListener(e -> mostrarDialogoAgregarVehiculo());
        btnListar.addActionListener(e -> listarVehiculos());
        btnBoleta.addActionListener(e -> generarBoletaVehiculo());
        btnResumen.addActionListener(e -> mostrarResumenLargoPlazo());
        btnCargarCSV.addActionListener(e -> cargarVehiculosDesdeArchivo());

        btnGuardarCSV.addActionListener(e -> guardarVehiculosEnArchivo());

        btnSalir.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private void mostrarDialogoAgregarVehiculo() {
        JDialog dialogo = new JDialog(this, "Agregar Vehiculo", true);
        dialogo.setSize(400, 400);
        dialogo.setLayout(new GridLayout(8, 2, 10, 10));
        dialogo.setLocationRelativeTo(this);

        JTextField campoPatente = new JTextField();
        JTextField campoMarca = new JTextField();
        JTextField campoModelo = new JTextField();
        JTextField campoAnio = new JTextField();
        JTextField campoDias = new JTextField();
        JTextField campoValorExtra = new JTextField();

        String[] tipos = {"Carga", "Pasajeros"};
        JComboBox<String> comboTipo = new JComboBox<>(tipos);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        JLabel labelValorExtra = new JLabel("Capacidad de carga (kg):");

        comboTipo.addActionListener(e -> {
            String tipoSeleccionado = (String) comboTipo.getSelectedItem();
            if ("Carga".equalsIgnoreCase(tipoSeleccionado)) {
                labelValorExtra.setText("Capacidad de carga (kg):");
            } else {
                labelValorExtra.setText("Capacidad máxima de pasajeros:");
            }
        });

        dialogo.add(new JLabel("Tipo:"));
        dialogo.add(comboTipo);
        dialogo.add(new JLabel("Patente:"));
        dialogo.add(campoPatente);
        dialogo.add(new JLabel("Marca:"));
        dialogo.add(campoMarca);
        dialogo.add(new JLabel("Modelo:"));
        dialogo.add(campoModelo);
        dialogo.add(new JLabel("Año:"));
        dialogo.add(campoAnio);
        dialogo.add(new JLabel("Días Arriendo:"));
        dialogo.add(campoDias);
        dialogo.add(labelValorExtra);
        dialogo.add(campoValorExtra);
        dialogo.add(btnGuardar);
        dialogo.add(btnCancelar);

        btnCancelar.addActionListener(e -> dialogo.dispose());

        btnGuardar.addActionListener(e -> {
            try {
                int anio = Integer.parseInt(campoAnio.getText().trim());
                String tipo = (String) comboTipo.getSelectedItem();
                String patente = campoPatente.getText().trim();
                String marca = campoMarca.getText().trim();
                String modelo = campoModelo.getText().trim();
                int dias = Integer.parseInt(campoDias.getText().trim());
                double valorExtra = Double.parseDouble(campoValorExtra.getText().trim());

                if (patente.isEmpty() || marca.isEmpty() || modelo.isEmpty()) {
                    throw new IllegalArgumentException("Campos vacios no permitidos");
                }
                if (patente.length() != 6) {
                    throw new IllegalArgumentException("La patente debe tener exactamente 6 caracteres.");
                }
                if (dias <= 0) throw new IllegalArgumentException("Dias deben ser positivos");

                Vehiculo nuevo;
                if ("Carga".equals(tipo)) {
                    nuevo = new VehiculoCarga(anio, dias, marca, modelo, patente, valorExtra);
                } else {
                    nuevo = new VehiculoPasajeros(anio, dias, marca, modelo, patente, (int) valorExtra);
                }

                gestor.agregarVehiculo(nuevo);
                JOptionPane.showMessageDialog(this, "Vehiculo agregado correctamente");
                dialogo.dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error en campos numericos: " + ex.getMessage());
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Validacion: " + ex.getMessage());
            } catch (PatenteDuplicadaException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        dialogo.setVisible(true);
    }

    private void mostrarResumenLargoPlazo() {
        List<Vehiculo> largoPlazo = gestor.obtenerVehiculosLargoPlazo();

        if (largoPlazo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay vehículos de largo plazo.");
        } else {
            StringBuilder resumen = new StringBuilder();
            resumen.append("RESUMEN LARGO PLAZO\n");
            resumen.append("------------------------------\n");

            for (Vehiculo v : largoPlazo) {
                resumen.append(v.calcularBoleta());
                resumen.append("\n------------------------------\n");
            }

            areaTexto.setText(resumen.toString());
        }
    }

    private void listarVehiculos() {
        StringBuilder sb = new StringBuilder();
        var vehiculos = gestor.getVehiculos();
        if (vehiculos.isEmpty()) {
            areaTexto.setText("No hay vehículos registrados.");
        } else {
            for (Vehiculo v : vehiculos) {
                sb.append(v).append("\n");
            }
            areaTexto.setText(sb.toString());
        }
    }

    private void generarBoletaVehiculo() {
        String patente = JOptionPane.showInputDialog(this, "Ingrese patente:");
        Vehiculo elegido = gestor.buscarVehiculoPorPatente(patente);
        if (elegido != null) {
            areaTexto.setText(elegido.calcularBoleta());
        } else {
            areaTexto.setText("Vehículo no encontrado.");
        }
    }

    private void cargarVehiculosDesdeArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        int seleccion = fileChooser.showOpenDialog(this);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            gestor.cargarDesdeArchivo(ruta);
            JOptionPane.showMessageDialog(this, "Archivo cargado: " + ruta);
        }
    }

    private void guardarVehiculosEnArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        int seleccion = fileChooser.showSaveDialog(this);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            gestor.guardarEnArchivo(ruta);
            JOptionPane.showMessageDialog(this, "Archivo guardado: " + ruta);
        }
    }
}
