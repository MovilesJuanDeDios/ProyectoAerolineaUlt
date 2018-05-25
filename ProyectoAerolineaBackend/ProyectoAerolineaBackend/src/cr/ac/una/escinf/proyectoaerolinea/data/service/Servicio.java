/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.escinf.proyectoaerolinea.data.service;

import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author slon
 */
public class Servicio {
    
    protected Connection cn;
    private final String user = "servidor";
    private final String password = "servidor";
    private final String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
    private OracleDataSource ds;
    
    public Servicio() {
    }

    /**
     *
     * @return
     * @throws NoDataException
     * @throws GlobalException
     */
    public Connection connect() throws NoDataException, GlobalException {
        try {
            ds = new OracleDataSource();
            ds.setURL(jdbcUrl);
            cn = ds.getConnection(user, password);
            return cn;
        } catch (SQLException ex) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
    }

    public Boolean disconnect() {
        try {
            cn.close();
            return true;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }
}
