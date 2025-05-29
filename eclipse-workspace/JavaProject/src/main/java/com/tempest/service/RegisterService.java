package com.tempest.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.tempest.config.DbConfig;
import com.tempest.model.UserModel;


public class RegisterService {
	
	private static Connection dbConn;
	
	public RegisterService() {
		try {
			dbConn = DbConfig.getDbConnection();
			if(dbConn != null) {
				System.out.println("Connection successful");
				System.out.println("Connected to database" + dbConn.getCatalog());
			}
		} catch (SQLException|ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isEmailExists(String email) {
		try {
			String query = "SELECT COUNT(*) FROM user WHERE email = ?";
			PreparedStatement stmt = dbConn.prepareStatement(query);
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean addCustomer(UserModel user){
		try {
			if (dbConn == null) {
				System.err.println("Database connection is not available.");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String insertQuery = "INSERT INTO user(first_name, last_name, email, password, phone, address, role)" + "VALUES (?,?,?,?,?,?,?)";

		try {
			PreparedStatement insertStmt = dbConn.prepareStatement(insertQuery);
			
			insertStmt.setString(1, user.getFirst_name());
			insertStmt.setString(2, user.getLast_name());
			insertStmt.setString(3, user.getEmail());
			insertStmt.setString(4, user.getPassword());
			insertStmt.setString(5, user.getPhone());
			insertStmt.setString(6, user.getAddress());
			// Convert enum to string for database storage
			insertStmt.setString(7, user.getRole().toString());
			
			int result = insertStmt.executeUpdate();
			System.out.println("Insert result: " + result);
			return result > 0;
			
		} catch (SQLException e) {
			System.err.println("Error inserting user: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}