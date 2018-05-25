/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.escinf.proyectoaerolinea.data.service;

import cr.ac.una.escinf.proyectoaerolinea.models.Avion;
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
public class ServicioAvion extends Servicio {
    
    private static final String INSERTARAVION = "{call INSERTAR_AVION(?,?)}";
    private static final String BUSCARAVION = "{?=call BUSCAR_AVION(?)}";
    private static final String ACTUALIZARAVION = "{call ACTUALIZAR_AVION(?,?)}";
    private static final String LISTARAVIONES = "{?=call LISTAR_AVIONES()}";

    public ServicioAvion() {
    }

    public void insertarAvion(Avion avion) throws GlobalException, NoDataException {
        connect();
        CallableStatement pstmt = null;

        try {
            pstmt = cn.prepareCall(INSERTARAVION);
            pstmt.setInt(1, avion.getAvion());
            pstmt.setInt(2, avion.getTipoAvion());

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

    public Avion buscarAvion(int pkAvion) throws GlobalException, NoDataException {
        connect();

        ResultSet rs = null;
        Avion avion = null;
        CallableStatement pstmt = null;
        
        try {
            pstmt = cn.prepareCall(BUSCARAVION);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setInt(2, pkAvion);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                avion = new Avion(rs.getInt("avion"),
                        rs.getInt("tipo_avion"));
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
        if (avion == null) {
            throw new NoDataException("No hay datos");
        }
        return avion;
    }

    public void actualizarAvion(Avion avion) throws GlobalException, NoDataException {
        connect();
        PreparedStatement pstmt = null;
        try {
            pstmt = cn.prepareStatement(ACTUALIZARAVION);
            pstmt.setInt(1, avion.getAvion());
            pstmt.setInt(2, avion.getTipoAvion());
                     
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
    
    public Collection listarAvion() throws GlobalException, NoDataException {
        connect();

        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Avion avion = null;
        CallableStatement pstmt = null;
        try {
            pstmt = cn.prepareCall(LISTARAVIONES);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                avion = new Avion(rs.getInt("avion"),
                        rs.getInt("tipo_avion"));
                coleccion.add(avion);
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
