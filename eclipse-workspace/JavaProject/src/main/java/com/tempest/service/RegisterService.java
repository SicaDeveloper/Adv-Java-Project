package com.tempest.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.tempest.config.DbConfig;
import com.tempest.model.UserModel;


public class RegisterService {
	
	private static Connection dbConn;
	
	public RegisterService() {
		
		try(Connection conn = DbConfig.getDbConnection()){
			if(conn!= null) {
				System.out.println("Connection successful");
				System.out.println("Connected to database" + conn.getCatalog());
				
			}
		} catch (SQLException|ClassNotFoundException e) {
			e.getMessage();
		}
	}
	
	public static Boolean addCustomer(UserModel user){
		try {
			if (dbConn == null) {
				System.err.println("Database connection is not available.");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String insertQuery = "INSERT INTO user(first_name, last_name, gmail, password, phone, address, role)" + "VALUES (?,?,?,?,?,?,?)";

		try {
			PreparedStatement insertStmt = dbConn.prepareStatement(insertQuery);
			
			insertStmt.setString(1, user.getFirst_name());
			insertStmt.setString(2, user.getLast_name());
			insertStmt.setString(3, user.getGmail());
			insertStmt.setString(4, user.getPassword());
			insertStmt.setString(5, user.getPhone());
			insertStmt.setString(6, user.getAddress());
			insertStmt.setObject(7, user.getRole(), java.sql.Types.OTHER);
			
			return insertStmt.executeUpdate() > 0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
}