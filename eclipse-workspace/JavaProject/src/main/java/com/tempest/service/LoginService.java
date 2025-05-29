package com.tempest.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tempest.config.DbConfig;
import com.tempest.model.UserModel;
import com.tempest.util.PasswordUtil;
import com.tempest.util.QueryUtil;

/**
 * @author Raj Dangol
 */

public class LoginService {
	
	private static Connection dbConnection;
	private static boolean isConnectionError = false;
	
	
	public LoginService() {
		try {
			dbConnection = DbConfig.getDbConnection();
		} catch(SQLException | ClassNotFoundException e) {
			isConnectionError = true;
			e.printStackTrace();
		}
	}
	
	public Boolean loginUser(UserModel user) {
		if (isConnectionError) {
			System.err.println("Database connection is not available");
			return null;
		}

		if (user == null || user.getEmail() == null || user.getPassword() == null) {
			System.err.println("Invalid user credentials provided");
			return false;
		}

		try {
			PreparedStatement statement = dbConnection.prepareStatement(QueryUtil.loginQuery);
			statement.setString(1, user.getEmail());
			ResultSet result = statement.executeQuery();
			
			if (result.next()) {
				return validatePassword(result, user);
			} else {
				System.err.println("User not found with email: " + user.getEmail());
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static boolean validatePassword(ResultSet result, UserModel userModel) throws SQLException {
		try {
			String dbUsername = result.getString("email");
			String dbPassword = result.getString("password");

			if (dbUsername == null || dbPassword == null) {
				System.err.println("Invalid user data in database");
				return false;
			}

			String decryptedPassword = PasswordUtil.decrypt(dbPassword, dbUsername);
			if (decryptedPassword == null) {
				System.err.println("Password decryption failed");
				return false;
			}

			return dbUsername.equals(userModel.getEmail()) && 
				   decryptedPassword.equals(userModel.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}