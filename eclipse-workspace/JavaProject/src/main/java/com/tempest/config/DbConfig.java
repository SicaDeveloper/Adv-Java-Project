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
		try {
			Connection connection = DbConfig.getDbConnection();
			System.out.println("Successfully connected to the database!");
			// You can perform database operations here using the 'connection' object.
			connection.close(); // It's crucial to close the connection when done.
			System.out.println("Database connection closed.");
		} catch (ClassNotFoundException e) {
			System.err.println("Error: MySQL JDBC driver not found.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Error: Could not connect to the database.");
			e.printStackTrace();
		}
	}
}