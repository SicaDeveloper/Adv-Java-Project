package com.tempest.service;

import java.sql.Connection;
import java.sql.SQLException;
import com.tempest.config.DbConfig;
import com.tempest.model.UserModel;


public class RegisterService {
	
	private Connection dbConn;
	
	public RegisterService() {
		try {
			this.dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			System.err.println("Database connection error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public Boolean addCustomer(UserModel user){
		if (dbConn == null) {
			System.err.println("Database connection is not available.");
			return null;
		}
		return false;
	}
}