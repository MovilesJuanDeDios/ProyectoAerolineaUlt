/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.escinf.proyectoaerolinea.data.service;

import cr.ac.una.escinf.proyectoaerolinea.models.Horario;
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
public class ServicioHorario extends Servicio {
    
    private static final String INSERTARHORARIO = "{call INSERTAR_HORARIO(?,?,?,?,?,?,?)}";
    private static final String BUSCARHORARIO = "{?=call BUSCAR_HORARIO(?)}";
    private static final String ACTUALIZARHORARIO = "{call ACTUALIZAR_HORARIO(?,?,?,?,?,?,?)}";
    private static final String LISTARHORARIOS = "{?=call LISTAR_HORARIOS()}";

    public ServicioHorario() {
    }

    public void insertarHorario(Horario horario) throws GlobalException, NoDataException {
        connect();
        CallableStatement pstmt = null;

        try {
            pstmt = cn.prepareCall(INSERTARHORARIO);
            pstmt.setInt(1, horario.getHorario());
            pstmt.setInt(2, horario.getRuta());
            pstmt.setDouble(3, horario.getPrecio());
            pstmt.setString(4, horario.getDiaSalida());
            pstmt.setString(5, horario.getHoraSalida());
            pstmt.setString(6, horario.getDiaLlegada());
            pstmt.setString(7, horario.getHoraLlegada());
            
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

    public Horario buscarHorario(int pkHorario) throws GlobalException, NoDataException {
        connect();

        ResultSet rs = null;
        Horario horario = null;
        CallableStatement pstmt = null;
        
        try {
            pstmt = cn.prepareCall(BUSCARHORARIO);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setInt(2, pkHorario);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                horario = new Horario(rs.getInt("horario"),
                        rs.getInt("ruta"),
                        rs.getDouble("precio"),
                        rs.getString("dia_llegada"),
                        rs.getString("hora_llegada"),
                        rs.getString("dia_salida"),
                        rs.getString("hora_salida"));
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
        if (horario == null) {
            throw new NoDataException("No hay datos");
        }
        return horario;
    }

    public void actualizarHorario(Horario horario) throws GlobalException, NoDataException {
        connect();
        PreparedStatement pstmt = null;
        try {
            pstmt = cn.prepareStatement(ACTUALIZARHORARIO);
           pstmt.setInt(1, horario.getHorario());
            pstmt.setInt(2, horario.getRuta());
            pstmt.setDouble(3, horario.getPrecio());
            pstmt.setString(4, horario.getDiaSalida());
            pstmt.setString(5, horario.getHoraSalida());
            pstmt.setString(6, horario.getDiaLlegada());
            pstmt.setString(7, horario.getHoraLlegada());
                     
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
    
    public Collection listarHorarios() throws GlobalException, NoDataException {
        connect();

        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Horario horario = null;
        CallableStatement pstmt = null;
        try {
            pstmt = cn.prepareCall(LISTARHORARIOS);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                horario = new Horario(rs.getInt("horario"),
                        rs.getInt("ruta"),
                        rs.getDouble("precio"),
                        rs.getString("dia_llegada"),
                        rs.getString("hora_llegada"),
                        rs.getString("dia_salida"),
                        rs.getString("hora_salida"));
                coleccion.add(horario);
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
