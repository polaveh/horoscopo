package com.edutecno.procesaconexion;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConexionBD {

	    public static Connection getConnection() {
	        Connection conn = null;
	        
	        try {
	            InitialContext ctx = new InitialContext();
	            
	            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Horoscopo");
	            
	            conn = ds.getConnection();
	            System.out.println("Conexion exitosa");
	            
	        } catch (NamingException | SQLException e) {
	            e.printStackTrace(); 
	        }
	        return conn;
	    }
	 
	    public static void main(String[] args) {
	        getConnection();
	    }

}