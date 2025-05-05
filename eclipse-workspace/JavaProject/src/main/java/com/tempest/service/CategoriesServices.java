package com.tempest.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.tempest.config.DbConfig;
import com.tempest.model.CategoriesModel;

public class CategoriesServices{
	
	private static Connection dbConn;
	private boolean isConnectionError = false;
	
	public CategoriesServices() {
		try{
			dbConn = DbConfig.getDbConnection();
			if(dbConn == null) {
				
			}
		} catch (SQLException|ClassNotFoundException e) {
			e.getMessage();
		}
	}
	
	public List<CategoriesModel> getAllCategoryInfo(){
		
		if(isConnectionError) {
			System.out.println("Connection Error");
			return null;
		}
		
        String query = "SELECT * from category";
        List<CategoriesModel> categoryList = new ArrayList<>();
        try (PreparedStatement pstmt = dbConn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            // Iterate through the ResultSet and create Category objects
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                categoryList.add(new CategoriesModel(id, name, description)); // Add to the list
            }
        } catch (SQLException e) { //Should be SQLException
            e.printStackTrace();
            return null;
        }
        return categoryList;
	}
}