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
public class Avion {
    
    private int avion;
    private int tipoAvion;

    public Avion() {
    }

    public Avion(int avion, int tipoAvion) {
        this.avion = avion;
        this.tipoAvion = tipoAvion;
    }

    public int getAvion() {
        return avion;
    }

    public void setAvion(int avion) {
        this.avion = avion;
    }

    public int getTipoAvion() {
        return tipoAvion;
    }

    public void setTipoAvion(int tipoAvion) {
        this.tipoAvion = tipoAvion;
    }

    @Override
    public String toString() {
        return "Avion{" + "avion=" + avion + ", tipoAvion=" + tipoAvion + '}';
    }
    
}
