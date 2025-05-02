package com.tempest.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tempest.config.DbConfig;
import com.tempest.model.CategoriesModel;
import com.tempest.model.ProductModel;
import com.tempest.util.ImageUtil;

public class ProductService{
private static Connection dbConn;
private boolean isConnectionError = false;

private final ImageUtil imageUtil = new ImageUtil();

	public ProductService() {
		
		try(Connection conn = DbConfig.getDbConnection()){
			if(conn!= null) {
				System.out.println("Connection successful");
				System.out.println("Connected to database" + conn.getCatalog());
				
			}
		} catch (SQLException|ClassNotFoundException e) {
			e.getMessage();
		}
	}
	
	public List<ProductModel> getAllProductsInfo(){
		
		if(isConnectionError) {
			System.out.println("Connection Error");
			return null;
		}
		
		 String query = "SELECT * from products";
		  
	        try(PreparedStatement stmt = dbConn.prepareStatement(query)){
	        		
	            ResultSet result = stmt.executeQuery();
	            List<ProductModel> productList = new ArrayList<>();
	            
	            try (PreparedStatement pstmt = dbConn.prepareStatement(query);
	                    ResultSet rs = pstmt.executeQuery()) {

	                   // Iterate through the ResultSet and create Category objects
	                   while (rs.next()) {
	                       int id = rs.getInt("product_id");
	                       String name = rs.getString("name");
	                       String description = rs.getString("description");
	                       Double price = rs.getDouble("price");
	                       int quantity = rs.getInt("quantity");
	                       String imageUrl = rs.getString("imageUrl");
	                       
	                       productList.add(new ProductModel(id,name,description,price,quantity,imageUrl));
	                   }
	                   return productList;
	               } catch (SQLException e) { //Should be SQLException
	                   e.printStackTrace();
	                   return null;
	               }
	        }catch(Exception e) {
	        	
	        }
			return null;	
	}
	
	public Boolean createProduct(ProductModel product) {
		
		if(isConnectionError) {
			System.out.println("Connection Error");
		}
		String query = "INSERT INTO products ( name, description, price, quantity, imageUrl)" + " VALUES (?,?,?,?,?);";
		
		try{
			PreparedStatement insertStmt = dbConn.prepareStatement(query);

			insertStmt.setString(1, product.getName());
			insertStmt.setString(2, product.getDescription());
			insertStmt.setDouble(3, product.getPrice());
			insertStmt.setInt(4, product.getQuantity());
			insertStmt.setString(5, product.getImageUrl());
			
			return insertStmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean removeProduct(ProductModel product) {
		if(isConnectionError) {
			System.out.println("Connection Error");
		}
		String query = "DELETE FROM products where id = ?"; 
		try {
			PreparedStatement insertStmt = dbConn.prepareStatement(query);
			insertStmt.setInt(1,product.getProductId());
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean updateProduct(ProductModel product) {
		
		if(isConnectionError) {
			System.out.println("Connection Error");
		}
		
		String searchQuery = "DELETE FROM products where id = ?"; 
		String updateQuery = "UPDATE products"
				+ "SET name = ?"
				+ "SET description = ?"
				+ "SET price = ?"
				+ "SET imageUrl = ?";
		
		try(PreparedStatement searchStmt = dbConn.prepareStatement(searchQuery)){
			
			searchStmt.setInt(1, product.getProductId());
			
			PreparedStatement updateStmt = dbConn.prepareStatement(updateQuery);
			updateStmt.setString(1,product.getName());
			updateStmt.setString(2,product.getDescription());
			updateStmt.setDouble(3, product.getPrice());
			updateStmt.setString(4, product.getImageUrl());
			
			return updateStmt.executeUpdate() > 0;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}