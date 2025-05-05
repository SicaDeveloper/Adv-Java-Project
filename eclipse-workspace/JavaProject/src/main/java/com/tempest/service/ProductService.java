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


	public ProductService() {
		try{
			dbConn = DbConfig.getDbConnection();
			if(dbConn!= null) {
				System.out.println("Connection successful");
				System.out.println("Connected to database" + dbConn.getCatalog());
				
			}
		} catch (SQLException|ClassNotFoundException e) {
			e.getMessage();
		}
	}
	
	public List<ProductModel> getAllProductsInfo(){
		
		if(isConnectionError == true) {
			System.out.println("Connection Error");
			return null;
		}
		
		 String query = "SELECT * from product";
		  
	        try(PreparedStatement stmt = dbConn.prepareStatement(query)){
	        		
	            ResultSet rs = stmt.executeQuery();
	            List<ProductModel> productList = new ArrayList<>();

	                   // Iterate through the ResultSet and create Category objects
	                   while (rs.next()) {
	                       int id = rs.getInt("id");
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
	               }
			return null;	
	}
	
	public Boolean createProduct(ProductModel product) {
		
		if(isConnectionError) {
			System.out.println("Connection Error");
		}
		String query = "INSERT INTO product (name, description, price, quantity, imageUrl)" + " VALUES (?,?,?,?,?);";
		
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
			insertStmt.setInt(1,product.getId());
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
			
			searchStmt.setInt(1, product.getId());
			
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
	
	public String findProductCategory(int productId) {
		if(isConnectionError) {
			System.out.println("Connection Error");
			return null;
		}
		
		String query = "SELECT\r\n"
				+ "    pc.product_id,\r\n"
				+ "    pc.category_id AS product_category_id, -- Alias to avoid ambiguity\r\n"
				+ "    c.category_id,\r\n"
				+ "    c.category_name\r\n"
				+ "FROM\r\n"
				+ "    product_category pc\r\n"
				+ "JOIN\r\n"
				+ "    category c ON pc.category_id = c.category_id;";
		
		try (PreparedStatement searchStmt = dbConn.prepareStatement(query)) {
	        searchStmt.setInt(1, productId); // Set the value for the placeholder
	        ResultSet resultSet = searchStmt.executeQuery();

	        if (resultSet.next()) {
	            return resultSet.getString("category_name");
	        } else {
	            // Product ID not found in product_category table
	            return null;
	        }

	    } catch (SQLException e) {
	        System.err.println("Error executing SQL query: " + e.getMessage());
	        return null; // Or throw the exception depending on your error handling strategy
	    }
	}
}