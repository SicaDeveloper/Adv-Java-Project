package com.tempest.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tempest.config.DbConfig;
import com.tempest.model.ProductModel;
import com.tempest.model.UserModel;
import com.tempest.model.UserModel.Roles;
public class UserService{

	
	private static Connection dbConn;
	private boolean isConnectionError = false;
	
	public UserService() {
		try{
			dbConn = DbConfig.getDbConnection();
		} catch (SQLException|ClassNotFoundException e) {
			e.getMessage();
		}
	}
	
	public List<UserModel> getAllUserDetails(){
		
		if(isConnectionError == true) {
			System.out.println("Connection Error");
			return null;
		}
		

		String query = "SELECT * from user";
		  
        try(PreparedStatement stmt = dbConn.prepareStatement(query)){
        		
            ResultSet rs = stmt.executeQuery();
            List<UserModel> userList = new ArrayList<>();

                   // Iterate through the ResultSet and create Category objects
                   while (rs.next()) {
                       int userId = rs.getInt("user_id");
                       String firstName = rs.getString("first_name");
                       String lastName = rs.getString("last_name");
                       String email = rs.getString("email");
                       String phone = rs.getString("phone");
                       String address = rs.getString("address");
                       String password = rs.getString("password");
                       String roleString = rs.getString("role");
                       Roles role = Roles.valueOf(roleString.toLowerCase());
                       
                       UserModel user = new UserModel(firstName, lastName, email, phone, address, password, role);
                       user.setUser_id(userId);
                       userList.add(user);
                   }
                   return userList;
               } catch (SQLException e) { //Should be SQLException
                   e.printStackTrace();
               }
		return null;	
	}
	
	
    public UserModel getUserDetails(int id) {
    	
    	if(isConnectionError == true) {
			System.out.println("Connection Error");
			return null;
		}
    	
    	 String query = "SELECT * from user where user_id = ?";
		  
	        try(PreparedStatement stmt = dbConn.prepareStatement(query)){
	        	stmt.setInt(1, id);
	            ResultSet rs = stmt.executeQuery();
	            
	            String firstName = rs.getString("first_name");
	            String lastName = rs.getString("last_name");
	            String email = rs.getString("email");
	            String phone = rs.getString("phone");
	            String address = rs.getString("address");
	            String password = rs.getString("password");
	            String roleString = rs.getString("role");
	            Roles role = null;
	            role = Roles.valueOf(roleString.toLowerCase());
	          
	            
	            UserModel user = new UserModel(firstName, lastName, email, phone, address, password, role);
	            
	            return user;
	            
	        } catch (SQLException e) { //Should be SQLException
	                   e.printStackTrace();
	        }
			return null;
    	
    }
    
    public Roles getUserRole(String email) {

    	if(isConnectionError == true) {
			System.out.println("Connection Error");
			return null; // Or throw an exception
		}

    	 String query = "SELECT role from user where email = ?";

	        try(PreparedStatement stmt = dbConn.prepareStatement(query)){
	        	stmt.setString(1, email);
	            ResultSet rs = stmt.executeQuery();

	            if (rs.next()) { // Check if a row was returned
		            String roleString = rs.getString("role");
		            try {
		                return Roles.valueOf(roleString.toLowerCase());
		            } catch (IllegalArgumentException e) {
		                System.err.println("Invalid role value in database: " + roleString + " for user ID: " + email);
		                return null; // Or throw a custom exception
		            }
	            } else {
	            	System.out.println("User with ID " + email + " not found.");
	            	return null; // Or throw a custom exception
	            }

	        } catch (SQLException e) {
	                   e.printStackTrace();
	                   return null; // Or throw a custom exception
	        }
}
    
	public UserModel getUserByEmail(String email) {
		if (isConnectionError) {
			System.out.println("Connection Error");
			return null;
		}

		String query = "SELECT * FROM user WHERE email = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return new UserModel(
					rs.getInt("id"),
					rs.getString("first_name"),
					rs.getString("last_name"),
					rs.getString("email"),
					rs.getString("password"),
					rs.getString("phone"),
					rs.getString("address"),
					Roles.valueOf(rs.getString("role"))
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
    
    
}