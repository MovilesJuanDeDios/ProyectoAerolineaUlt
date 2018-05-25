/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.escinf.proyectoaerolinea.data.service;

import cr.ac.una.escinf.proyectoaerolinea.models.TipoAvion;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import oracle.jdbc.internal.OracleTypes;

/**
 *
 * @author slon
 */
public class ServicioTipoAvion extends Servicio {
    
    private static final String INSERTARTIPOAVION = "{call INSERTAR_TIPOAVION(?,?,?,?,?,?,?)}";
    private static final String BUSCARTIPOAVION = "{?=call BUSCAR_TIPOAVION(?)}";
    private static final String ACTUALIZARTIPOAVION = "{call ACTUALIZAR_TIPOAVION(?,?,?,?,?,?,?)}";
    private static final String LISTARTIPOAVIONES = "{?=call LISTAR_TIPOAVIONES()}";

    public ServicioTipoAvion() {
    }

    public void insertartTipoAvion(TipoAvion tipoAvion) throws GlobalException, NoDataException {
        connect();
        CallableStatement pstmt = null;

        try {
            pstmt = cn.prepareCall(INSERTARTIPOAVION);
            pstmt.setInt(1, tipoAvion.getTipoAvion());
            pstmt.setInt(2, tipoAvion.getCapacidad());
            pstmt.setString(3, tipoAvion.getAnno());
            pstmt.setString(4, tipoAvion.getModelo());
            pstmt.setString(5, tipoAvion.getMarca());
            pstmt.setInt(6, tipoAvion.getFilas());
            pstmt.setInt(7, tipoAvion.getAsientosPorFila());
         
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

    public TipoAvion buscarTipoAvion(int tipo) throws GlobalException, NoDataException {
        connect();

        ResultSet rs = null;
        TipoAvion tipoAvion = null;
        CallableStatement pstmt = null;
        
        try {
            pstmt = cn.prepareCall(BUSCARTIPOAVION);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setInt(2, tipo);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                tipoAvion = new TipoAvion(rs.getInt("tipo_avion"),
                        rs.getInt("capacidad"),
                        rs.getString("anno"),
                        rs.getString("modelo"),
                        rs.getString("marca"),
                        rs.getInt("filas"),
                        rs.getInt("asientos_por_fila"));
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
        if (tipoAvion == null) {
            throw new NoDataException("No hay datos");
        }
        return tipoAvion;
    }

    public void actualizarTipoAvion(TipoAvion tipoAvion) throws GlobalException, NoDataException {
        connect();
        PreparedStatement pstmt = null;
        try {
            pstmt = cn.prepareStatement(ACTUALIZARTIPOAVION);
            pstmt.setInt(1, tipoAvion.getTipoAvion());
            pstmt.setInt(2, tipoAvion.getCapacidad());
            pstmt.setString(3, tipoAvion.getAnno());
            pstmt.setString(4, tipoAvion.getModelo());
            pstmt.setString(5, tipoAvion.getMarca());
            pstmt.setInt(6, tipoAvion.getFilas());
            pstmt.setInt(7, tipoAvion.getAsientosPorFila());
            
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
    
    public Collection listarTipoAvion() throws GlobalException, NoDataException {
        connect();

        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        TipoAvion tipoAvion = null;
        CallableStatement pstmt = null;
        try {
            pstmt = cn.prepareCall(LISTARTIPOAVIONES);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                tipoAvion = new TipoAvion(rs.getInt("tipo_avion"),
                        rs.getInt("capacidad"),
                        rs.getString("anno"),
                        rs.getString("modelo"),
                        rs.getString("marca"),
                        rs.getInt("filas"),
                        rs.getInt("asientos_por_fila"));
                coleccion.add(tipoAvion);
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
