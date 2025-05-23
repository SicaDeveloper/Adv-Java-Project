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


public class LoginService{
	
	private static Connection dbConnection;
	private static boolean isConnectionError = false;
	
	
	public LoginService() {
		try {
		dbConnection = DbConfig.getDbConnection();
		
		} catch(SQLException | ClassNotFoundException e){
			e.printStackTrace();
			isConnectionError = true;
		}
	}
	
	public Boolean loginUser(UserModel user){
		
		if(isConnectionError == true){
			System.out.println("The system has failed to connect to the database");
			return false;
		}

		try{
			PreparedStatement statement = dbConnection.prepareStatement(QueryUtil.loginQuery);
			statement.setString(1, user.getEmail());
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				return validatePassword(result, user);
			}
		}catch(SQLException e){
			e.printStackTrace();
			return null;	
		}
		return false;
	}
	
	private static boolean validatePassword(ResultSet result,UserModel userModel) throws SQLException {
		String dbUsername = result.getString("email");
		String dbPassword = result.getString("password");

		return dbUsername.equals(userModel.getEmail())
				&& PasswordUtil.decrypt(dbPassword, dbUsername).equals(userModel.getPassword());
	}
}