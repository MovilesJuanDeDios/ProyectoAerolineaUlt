/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.escinf.proyectoaerolinea.data.service;

/**
 *
 * @author slon
 */
class NoDataException extends Exception {
    
    public NoDataException() {
    }

    public NoDataException(String msg) {
        super(msg);
    }
}
