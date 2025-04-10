package com.tempest.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tempest.config.DbConfig;
import com.tempest.model.UserModel;
import com.tempest.util.PasswordUtil;
import com.tempest.util.QueryUtil;



public class LoginService{
	
	private static Connection dbConnection;
	private static boolean isConnectionError = false;
	
	
	public void LoginService() {
		try {
		dbConnection = DbConfig.getDbConnection();
		
		} catch(SQLException | ClassNotFoundException e){
			e.printStackTrace();
			isConnectionError = true;
		}
	}
	
	public static Boolean loginUser(UserModel user){
		
		if(isConnectionError){
			System.out.println("The system has failed to connect to the database");
			return false;
		}

		try (PreparedStatement statement = dbConnection.prepareStatement(QueryUtil.loginQuery)){
			statement.setString(1, UserModel.getUsername());
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
		String dbUsername = result.getString("username");
		String dbPassword = result.getString("password");

		return dbUsername.equals(UserModel.getUsername())
				&& PasswordUtil.decrypt(dbPassword, dbUsername).equals(userModel.getPassword());
	}
}