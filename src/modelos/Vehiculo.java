package modelos;

import interfaces.Factura;

public abstract class Vehiculo implements Factura{
    private String patente;
    private String marca;
    private String modelo;
    private int anio;
    private int arriendo;

    protected Vehiculo() {
        //vacio   
    }

    protected Vehiculo(int anio, int arriendo, String marca, String modelo, String patente) {
        this.anio = anio;
        this.arriendo = arriendo;
        this.marca = marca;
        this.modelo = modelo;
        this.patente = patente;
    }

    //GETTERS Y SETTERS

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getArriendo() {
        return arriendo;
    }

    public void setArriendo(int arriendo) {
        this.arriendo = arriendo;
    }

    public abstract String mostrarDatos();

    @Override
    public abstract String calcularBoleta();
}
