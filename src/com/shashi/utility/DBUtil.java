package com.shashi.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBUtil {
	private static Connection conn;

	public DBUtil() {
	}

	public static Connection provideConnection() {
	    Connection con = null;

	    try {
	        ResourceBundle rb = ResourceBundle.getBundle("application");

	        String driverName = rb.getString("db.driverName");
	        String username = rb.getString("db.username");
	        String password = rb.getString("db.password");

	        // Read from environment variables (Kubernetes)
	        String host = System.getenv("DB_HOST");
	        String port = System.getenv("DB_PORT");

	        String connectionString;

	        if (host != null && port != null) {
	            // Kubernetes mode
	            connectionString =
	                "jdbc:mysql://" + host + ":" + port + "/shopping-cart" +
	                "?useSSL=false&allowPublicKeyRetrieval=true";
	        } else {
	            // Local system mode (use properties file)
	            connectionString = rb.getString("db.connectionString");
	        }

	        Class.forName(driverName);
	        con = DriverManager.getConnection(connectionString, username, password);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return con;
	}


	public static void closeConnection(Connection con) {
		/*
		 * try { if (con != null && !con.isClosed()) {
		 * 
		 * con.close(); } } catch (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
	}

	public static void closeConnection(ResultSet rs) {
		try {
			if (rs != null && !rs.isClosed()) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void closeConnection(PreparedStatement ps) {
		try {
			if (ps != null && !ps.isClosed()) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
