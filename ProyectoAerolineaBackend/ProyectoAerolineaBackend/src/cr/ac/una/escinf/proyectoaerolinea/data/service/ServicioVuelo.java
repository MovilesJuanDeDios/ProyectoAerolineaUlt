/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.escinf.proyectoaerolinea.data.service;

import cr.ac.una.escinf.proyectoaerolinea.models.Vuelo;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author slon
 */
public class ServicioVuelo extends Servicio {
    
    private static final String INSERTARVUELO = "{call INSERTAR_VUELO(?,?,?,?,?)}";
    private static final String BUSCARVUELO = "{?=call BUSCAR_VUELO(?)}";
    private static final String ACTUALIZARVUELO = "{call ACTUALIZAR_VUELO(?,?,?,?,?)}";
    private static final String LISTARVUELOS = "{?=call LISTAR_VUELOS()}";

    public ServicioVuelo() {
    }
   
    public void insertarVuelo(Vuelo vuelo) throws GlobalException, NoDataException {
        connect();
        CallableStatement pstmt = null;

        try {
            pstmt = cn.prepareCall(INSERTARVUELO);
            pstmt.setInt(1, vuelo.getVuelo());
            pstmt.setInt(2, vuelo.getAvion());
            pstmt.setInt(3, vuelo.getRuta());
            pstmt.setInt(4, vuelo.getHorario());
      
            boolean resultado = pstmt.execute();

            if (resultado == true) {
                throw new NoDataException("No se realizo la inserción");
            } else {
                System.out.println("\nInserción satisfactoria!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Llave duplicada");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                disconnect();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
    }

    public Vuelo buscarVuelo(int pkVuelo) throws GlobalException, NoDataException {
        connect();

        ResultSet rs = null;
        Vuelo vuelo = null;
        CallableStatement pstmt = null;
        
        try {
            pstmt = cn.prepareCall(BUSCARVUELO);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setInt(2, pkVuelo);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                vuelo = new Vuelo(rs.getInt("vuelo"),
                        rs.getInt("avion"),
                        rs.getInt("ruta"),
                        rs.getInt("horario"));                   
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                disconnect();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
        if (vuelo == null) {
            throw new NoDataException("No hay datos");
        }
        return vuelo;
    }

    public void actualizarVuelo(Vuelo vuelo) throws GlobalException, NoDataException {
        connect();
        PreparedStatement pstmt = null;
        try {
            pstmt = cn.prepareStatement(ACTUALIZARVUELO);
            pstmt.setInt(1, vuelo.getVuelo());
            pstmt.setInt(2, vuelo.getAvion());
            pstmt.setInt(3, vuelo.getRuta());
            pstmt.setInt(4, vuelo.getHorario());
                     
            boolean resultado = pstmt.execute();

            if (resultado == true) {
                throw new NoDataException("No se realizo la actualización");
            } else {
                System.out.println("\nModificación Satisfactoria!");
            }
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                disconnect();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
    }
    
    public Collection listarVuelos() throws GlobalException, NoDataException {
        connect();

        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Vuelo vuelo = null;
        CallableStatement pstmt = null;
        try {
            pstmt = cn.prepareCall(LISTARVUELOS);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                vuelo = new Vuelo(rs.getInt("vuelo"),
                        rs.getInt("avion"),
                        rs.getInt("ruta"),
                        rs.getInt("horario"));
                coleccion.add(vuelo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                disconnect();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
        if (coleccion == null || coleccion.size() == 0) {
            throw new NoDataException("No hay datos");
        }
       
        return coleccion;
    }
    
}
