package com.tempest.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfig {

	// Database configuration information
	private static final String URL = "jdbc:mysql://localhost:3306/tempest";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";

	public static Connection getDbConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
	
	public static void main(String[] args) {
		try(Connection conn = getDbConnection()){
			if(conn!= null) {
				System.out.println("Connection successful");
				System.out.println("Connected to database" + conn.getCatalog());
				
			}
		} catch (SQLException|ClassNotFoundException e) {
			e.getMessage();
		}
		
	}
}