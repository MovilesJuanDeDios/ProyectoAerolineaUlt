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
public class Horario {
    
    private int horario;
    private int ruta;
    private double precio;
    private String diaSalida;
    private String horaSalida;
    private String diaLlegada;
    private String horaLlegada;

    public Horario() {
    }

    public Horario(int horario, int ruta, double precio, String diaSalida, String horaSalida, String diaLlegada, String horaLlegada) {
        this.horario = horario;
        this.ruta = ruta;
        this.precio = precio;
        this.diaSalida = diaSalida;
        this.horaSalida = horaSalida;
        this.diaLlegada = diaLlegada;
        this.horaLlegada = horaLlegada;
    }

    public int getHorario() {
        return horario;
    }

    public void setHorario(int horario) {
        this.horario = horario;
    }

    public int getRuta() {
        return ruta;
    }

    public void setRuta(int ruta) {
        this.ruta = ruta;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDiaSalida() {
        return diaSalida;
    }

    public void setDiaSalida(String diaSalida) {
        this.diaSalida = diaSalida;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getDiaLlegada() {
        return diaLlegada;
    }

    public void setDiaLlegada(String diaLlegada) {
        this.diaLlegada = diaLlegada;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    @Override
    public String toString() {
        return "Horario{" + "horario=" + horario + ", ruta=" + ruta + ", precio=" + precio + ", diaSalida=" + diaSalida + ", horaSalida=" + horaSalida + ", diaLlegada=" + diaLlegada + ", horaLlegada=" + horaLlegada + '}';
    }
    
    
}
