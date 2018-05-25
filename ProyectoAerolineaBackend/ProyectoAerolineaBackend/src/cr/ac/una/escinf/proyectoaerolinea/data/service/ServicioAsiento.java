/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.escinf.proyectoaerolinea.data.service;

import cr.ac.una.escinf.proyectoaerolinea.models.Asiento;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author slon
 */
public class ServicioAsiento extends Servicio {
    
    private static final String INSERTARASIENTO = "{call INSERTAR_ASIENTO(?,?,?,?,?)}";
    private static final String BUSCARASIENTO = "{?=call BUSCAR_ASIENTO(?)}";
    private static final String LISTARASIENTOS = "{?=call LISTAR_ASIENTOS()}";

    public ServicioAsiento() {
    }
   
    public void insertarAsiento(Asiento asiento) throws GlobalException, NoDataException {
        connect();
        CallableStatement pstmt = null;

        try {
            pstmt = cn.prepareCall(INSERTARASIENTO);
            pstmt.setInt(1, asiento.getAsiento());
            pstmt.setString(2, asiento.getNumero());
            pstmt.setString(3, String.valueOf(asiento.getEstado()));
            pstmt.setInt(4, asiento.getVuelo()); 
      
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

    public Asiento buscarAsiento(int pkAsiento) throws GlobalException, NoDataException {
        connect();

        ResultSet rs = null;
        Asiento asiento = null;
        CallableStatement pstmt = null;
        
        try {
            pstmt = cn.prepareCall(BUSCARASIENTO);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setInt(2, pkAsiento);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                 asiento = new Asiento(rs.getInt("asiento"),
                        rs.getString("numero"), 
                        rs.getString("estado").charAt(0),
                        rs.getInt("vuelo"));                 
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
        if (asiento == null) {
            throw new NoDataException("No hay datos");
        }
        return asiento;
    }

    public Collection listarAsientos() throws GlobalException, NoDataException {
        connect();

        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Asiento asiento = null;
        CallableStatement pstmt = null;
        try {
            pstmt = cn.prepareCall(LISTARASIENTOS);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                asiento = new Asiento(rs.getInt("asiento"),
                        rs.getString("numero"), 
                        rs.getString("estado").charAt(0),
                        rs.getInt("vuelo"));
                coleccion.add(asiento);
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
