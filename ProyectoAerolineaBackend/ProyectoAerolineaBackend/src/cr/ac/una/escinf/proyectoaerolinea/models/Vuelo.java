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
public class Vuelo {
    
    private int vuelo;
    private int avion;
    private int ruta;
    private int horario;

    public Vuelo() {
    }

    public Vuelo(int vuelo, int avion, int ruta, int horario) {
        this.vuelo = vuelo;
        this.avion = avion;
        this.ruta = ruta;
        this.horario = horario;
    }

    public int getVuelo() {
        return vuelo;
    }

    public void setVuelo(int vuelo) {
        this.vuelo = vuelo;
    }

    public int getAvion() {
        return avion;
    }

    public void setAvion(int avion) {
        this.avion = avion;
    }

    public int getRuta() {
        return ruta;
    }

    public void setRuta(int ruta) {
        this.ruta = ruta;
    }

    public int getHorario() {
        return horario;
    }

    public void setHorario(int horario) {
        this.horario = horario;
    }

    @Override
    public String toString() {
        return "Vuelo{" + "vuelo=" + vuelo + ", avion=" + avion + ", ruta=" + ruta + ", horario=" + horario + '}';
    }
    
    
}
