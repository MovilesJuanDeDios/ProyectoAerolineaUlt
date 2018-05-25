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
public class Asiento {
    
    private int asiento;
    private String numero;
    private char estado;
    private int vuelo;

    public Asiento() {
    }

    public Asiento(int asiento, String numero, char estado, int vuelo) {
        this.asiento = asiento;
        this.numero = numero;
        this.estado = estado;
        this.vuelo = vuelo;
    }

    public int getAsiento() {
        return asiento;
    }

    public void setAsiento(int asiento) {
        this.asiento = asiento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public int getVuelo() {
        return vuelo;
    }

    public void setVuelo(int vuelo) {
        this.vuelo = vuelo;
    }

    @Override
    public String toString() {
        return "Asiento{" + "asiento=" + asiento + ", numero=" + numero + ", estado=" + estado + ", vuelo=" + vuelo + '}';
    }
    
    
}
