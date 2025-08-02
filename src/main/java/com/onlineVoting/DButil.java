package com.onlineVoting;
import java.sql.*;


public class DButil {
	private static final String URL = "jdbc:mysql://localhost:3306/onlinevoting";
	private static final String USER ="root";
	private static final String PASSWORD = "root";
	 static {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	        } catch (ClassNotFoundException e) {
	            throw new RuntimeException("Failed to load MySQL driver", e);
	        }
	    }
	    
	    public static Connection getConnection() throws SQLException {
	        return DriverManager.getConnection(URL, USER, PASSWORD);
	    
	}

}
