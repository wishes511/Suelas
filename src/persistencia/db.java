/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mich
 */
public class db {
    //servidor local de pruebas

    //String url = "jdbc:sqlserver://192.168.6.75\\SQLEXPRESS:9205;" + "databaseName=CFDI33;user=mich; password=mich;";
    String url = "jdbc:sqlserver://192.168.90.1\\datos620:1433;database=RACobranzaPhylon";
//            + "databaseName=avances;user=mich; password=mich;";
//    jdbc:sqlserver://192.168.6.75\SQLEXPRESS:9205;databaseName=avances
//   String url ="jdbc:sqlserver://192.168.6.8\\datos65:9205;databaseName=Avances;";
    String drive = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    // Declaramos los sioguientes objetos
    Connection conexion = null;
    Statement stmt = null;
    ResultSet rs = null;

    public Connection getConexion() {
        return this.conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public void abrir() throws ClassNotFoundException, SQLException {
        Class.forName(drive);
        conexion = DriverManager.getConnection(url, "sa", "Admin1305");
//        conexion = DriverManager.getConnection(url, "sa", "Prok2001");
        // System.out.println("hecho :]");
    }

    public void cerrar() throws SQLException {
        conexion.close();
    }
}
