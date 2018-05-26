/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.escinf.proyectoaerolinea.models;

import java.util.Date;

/**
 *
 * @author slon
 */
public class Tiquete {
    
    private int tiquete;
    private double precio;
    private String usuario;
    private int vuelo;
    private int asiento;
    private String fechaCompra;

    public Tiquete() {
    }

    public Tiquete(int tiquete, double precio, String usuario, int vuelo, int asiento, String fechaCompra) {
        this.tiquete = tiquete;
        this.precio = precio;
        this.usuario = usuario;
        this.vuelo = vuelo;
        this.asiento = asiento;
        this.fechaCompra = fechaCompra;
    }

    public int getTiquete() {
        return tiquete;
    }

    public void setTiquete(int tiquete) {
        this.tiquete = tiquete;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getVuelo() {
        return vuelo;
    }

    public void setVuelo(int vuelo) {
        this.vuelo = vuelo;
    }

    public int getAsiento() {
        return asiento;
    }

    public void setAsiento(int asiento) {
        this.asiento = asiento;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    @Override
    public String toString() {
        return "Tiquete{" + "tiquete=" + tiquete + ", precio=" + precio + ", usuario=" + usuario + ", vuelo=" + vuelo + ", asiento=" + asiento + ", fechaCompra=" + fechaCompra + '}';
    }
    
}
