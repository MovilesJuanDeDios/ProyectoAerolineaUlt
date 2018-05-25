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
public class Ruta {
    
    private int ruta;
    private String origen;
    private String destino;
    private String duracion;
    private int contCompra;
    private int descuento;

    public Ruta() {
    }

    public Ruta(int ruta, String origen, String destino, String duracion, int contCompra, int descuento) {
        this.ruta = ruta;
        this.origen = origen;
        this.destino = destino;
        this.duracion = duracion;
        this.contCompra = contCompra;
        this.descuento = descuento;
    }

    public int getRuta() {
        return ruta;
    }

    public void setRuta(int ruta) {
        this.ruta = ruta;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public int getContCompra() {
        return contCompra;
    }

    public void setContCompra(int contCompra) {
        this.contCompra = contCompra;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    @Override
    public String toString() {
        return "Ruta{" + "ruta=" + ruta + ", origen=" + origen + ", destino=" + destino + ", duracion=" + duracion + ", contCompra=" + contCompra + ", descuento=" + descuento + '}';
    }
    
}
