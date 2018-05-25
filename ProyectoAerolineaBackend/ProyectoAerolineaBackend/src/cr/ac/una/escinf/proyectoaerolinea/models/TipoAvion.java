/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.escinf.proyectoaerolinea.models;

/**
 *
 * @author slon
 */
public class TipoAvion {
    
    private int tipoAvion;
    private int capacidad;
    private String anno;
    private String modelo;
    private String marca;
    private int filas;
    private int asientosPorFila;

    public TipoAvion() {
    }

    public TipoAvion(int tipoAvion, int capacidad, String anno, String modelo, String marca, int filas, int asientosPorFila) {
        this.tipoAvion = tipoAvion;
        this.capacidad = capacidad;
        this.anno = anno;
        this.modelo = modelo;
        this.marca = marca;
        this.filas = filas;
        this.asientosPorFila = asientosPorFila;
    }

    public int getTipoAvion() {
        return tipoAvion;
    }

    public void setTipoAvion(int tipoAvion) {
        this.tipoAvion = tipoAvion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public int getAsientosPorFila() {
        return asientosPorFila;
    }

    public void setAsientosPorFila(int asientosPorFila) {
        this.asientosPorFila = asientosPorFila;
    }

    @Override
    public String toString() {
        return "TipoAvion{" + "tipoAvion=" + tipoAvion + ", capacidad=" + capacidad + ", anno=" + anno + ", modelo=" + modelo + ", marca=" + marca + ", filas=" + filas + ", asientosPorFila=" + asientosPorFila + '}';
    }
       
}
