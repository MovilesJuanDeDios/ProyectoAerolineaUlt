/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.escinf.proyectoaerolinea.data.service;

import cr.ac.una.escinf.proyectoaerolinea.models.Tiquete;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author slon
 */
public class ServicioTiquete extends Servicio {
    
    private static final String INSERTARTIQUETE = "{call INSERTAR_TIQUETE(?,?,?,?,?,?)}";
    private static final String BUSCARTIQUETE = "{?=call BUSCAR_TIQUETE(?)}";
    private static final String LISTARTIQUETES = "{?=call LISTAR_TIQUETES()}";

    public ServicioTiquete() {
    }

    public void insertarTiquete(Tiquete tiquete) throws GlobalException, NoDataException {
        connect();
        CallableStatement pstmt = null;

        try {
            pstmt = cn.prepareCall(INSERTARTIQUETE);
            pstmt.setInt(1, tiquete.getTiquete());
            pstmt.setDouble(2, tiquete.getPrecio());
            pstmt.setString(3, tiquete.getUsuario());
            pstmt.setInt(4, tiquete.getVuelo());
            pstmt.setInt(5, tiquete.getAsiento());
            pstmt.setString(6, tiquete.getFechaCompra());

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

    public Tiquete buscarTiquete(int pkTiquete) throws GlobalException, NoDataException {
        connect();

        ResultSet rs = null;
        Tiquete tiquete = null;
        CallableStatement pstmt = null;
        
        try {
            pstmt = cn.prepareCall(BUSCARTIQUETE);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setInt(2, pkTiquete);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                tiquete = new Tiquete(rs.getInt("tiquete"),
                        rs.getDouble("precio"),
                        rs.getString("Usuario"),
                        rs.getInt("vuelo"),
                        rs.getInt("asiento"),
                        rs.getString("fecha_compra"));                    
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
        if (tiquete == null) {
            throw new NoDataException("No hay datos");
        }
        return tiquete;
    }
 
    public Collection listarTiquetes() throws GlobalException, NoDataException {
        connect();

        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Tiquete tiquete = null;
        CallableStatement pstmt = null;
        try {
            pstmt = cn.prepareCall(LISTARTIQUETES);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                tiquete = new Tiquete(rs.getInt("tiquete"),
                        rs.getDouble("precio"),
                        rs.getString("Usuario"),
                        rs.getInt("vuelo"),
                        rs.getInt("asiento"),
                        rs.getString("fecha_compra"));  
                coleccion.add(tiquete);
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
