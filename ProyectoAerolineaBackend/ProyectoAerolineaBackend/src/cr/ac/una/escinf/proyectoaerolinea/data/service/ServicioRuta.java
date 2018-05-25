/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.escinf.proyectoaerolinea.data.service;

import cr.ac.una.escinf.proyectoaerolinea.models.Ruta;
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
public class ServicioRuta extends Servicio {
    
    private static final String INSERTARRUTA = "{call INSERTAR_RUTA(?,?,?,?,?,?)}";
    private static final String BUSCARRUTA = "{?=call BUSCAR_RUTA(?)}";
    private static final String ACTUALIZARRUTA = "{call ACTUALIZAR_RUTA(?,?,?,?,?,?)}";
    private static final String LISTAR_RUTAS = "{?=call LISTAR_RUTAS()}";

    public ServicioRuta() {
    }

    public void insertarRuta(Ruta ruta) throws GlobalException, NoDataException {
        connect();
        CallableStatement pstmt = null;
        
        try {
            pstmt = cn.prepareCall(INSERTARRUTA);
            pstmt.setInt(1, ruta.getRuta());
            pstmt.setString(2, ruta.getOrigen());
            pstmt.setString(3, ruta.getDestino());
            pstmt.setString(4, ruta.getDuracion());
            pstmt.setInt(5, ruta.getContCompra());
            pstmt.setInt(6, ruta.getDescuento());
          
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

    public Ruta buscarRuta(int numRuta) throws GlobalException, NoDataException {
        connect();

        ResultSet rs = null;
        Ruta ruta = null;
        CallableStatement pstmt = null;
        
        try {
            pstmt = cn.prepareCall(BUSCARRUTA);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setInt(2, numRuta);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                ruta = new Ruta(rs.getInt("ruta"),
                        rs.getString("origen"),
                        rs.getString("destino"),
                        rs.getString("duracion"),
                        rs.getInt("cont_compra"),
                        rs.getInt("descuento"));
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
        if (ruta == null) {
            throw new NoDataException("No hay datos");
        }
        return ruta;
    }

    public void actualizarRuta(Ruta ruta) throws GlobalException, NoDataException {
        connect();
        PreparedStatement pstmt = null;
        try {
            pstmt = cn.prepareStatement(ACTUALIZARRUTA);
            pstmt.setInt(1, ruta.getRuta());
            pstmt.setString(2, ruta.getOrigen());
            pstmt.setString(3, ruta.getDestino());
            pstmt.setString(4, ruta.getDuracion());
            pstmt.setInt(5, ruta.getContCompra());
            pstmt.setInt(6, ruta.getDescuento());
          
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
    
    public Collection listarRutas() throws GlobalException, NoDataException {
        connect();

        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Ruta ruta = null;
        CallableStatement pstmt = null;
        try {
            pstmt = cn.prepareCall(LISTAR_RUTAS);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                ruta = new Ruta(rs.getInt("ruta"),
                        rs.getString("origen"),
                        rs.getString("destino"),
                        rs.getString("duracion"),
                        rs.getInt("cont_compra"),
                        rs.getInt("descuento"));
                coleccion.add(ruta);
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
