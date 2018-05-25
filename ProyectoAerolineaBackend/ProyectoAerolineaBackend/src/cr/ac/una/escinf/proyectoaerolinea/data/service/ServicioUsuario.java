/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.escinf.proyectoaerolinea.data.service;

import cr.ac.una.escinf.proyectoaerolinea.models.Usuario;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleTypes;

/**
 *
 * @author slon
 */
public class ServicioUsuario extends Servicio {
    
    private static final String INSERTARUSUARIO = "{call INSERTAR_USUARIO(?,?,?,?,?,?,?,?,?)}";
    private static final String BUSCARUSUARIO = "{?=call BUSCAR_USUARIO(?)}";
    private static final String ACTUALIZARUSUARIO = "{call ACTUALIZAR_USUARIO(?,?,?,?,?,?,?,?,?)}";

    public ServicioUsuario() {
    }

    public void insertarUsuario(Usuario usuario) throws GlobalException, NoDataException {
        connect();
        CallableStatement pstmt = null;

        try {
            pstmt = cn.prepareCall(INSERTARUSUARIO);
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellidos());
            pstmt.setString(3, usuario.getCorreo());
            pstmt.setString(4, usuario.getFechaNacimiento());
            pstmt.setString(5, usuario.getDireccion());
            pstmt.setString(6, usuario.getCelular());
            pstmt.setString(7, usuario.getTelefono());
            pstmt.setString(8, usuario.getUsuario());
            pstmt.setString(9, usuario.getContrasena());
   
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

    public Usuario buscarUsuario(String nomUsuario) throws GlobalException, NoDataException {
        connect();

        ResultSet rs = null;
        Usuario usuario = null;
        CallableStatement pstmt = null;
        
        try {
            pstmt = cn.prepareCall(BUSCARUSUARIO);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, nomUsuario);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                usuario = new Usuario(rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("correo"),
                        rs.getString("fecha_nac"),
                        rs.getString("direccion"),
                        rs.getString("celular"),
                        rs.getString("telefono"),
                        rs.getString("usuario"),
                        rs.getString("contrasena"));
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
        if (usuario == null) {
            throw new NoDataException("No hay datos");
        }
        return usuario;
    }

    public void actualizarUsuario(Usuario usuario) throws GlobalException, NoDataException {
        connect();
        PreparedStatement pstmt = null;
        try {
            pstmt = cn.prepareStatement(ACTUALIZARUSUARIO);
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellidos());
            pstmt.setString(3, usuario.getCorreo());
            pstmt.setString(4, usuario.getFechaNacimiento());
            pstmt.setString(5, usuario.getDireccion());
            pstmt.setString(6, usuario.getCelular());
            pstmt.setString(7, usuario.getTelefono());
            pstmt.setString(8, usuario.getUsuario());
            pstmt.setString(9, usuario.getContrasena());
            
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
}
