import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tempest.config.DbConfig;
import com.tempest.model.ProductModel;
import com.tempest.model.UserModel;
public class ProductService{
private static Connection dbConn;
private boolean isConnectionError = false;

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
		
		  String query = "SELECT student_id, first_name, last_name, program_id, email, number FROM student";
	        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
	            ResultSet result = stmt.executeQuery();
	            List<ProductModel> productList = new ArrayList<>();

	            while (result.next()) {
	                // SQL query to fetch program name based on program_id
	                try { 
	                	PreparedStatement programStmt = dbConn.prepareStatement(query);
	                    programStmt.setInt(1, result.getInt("product_id"));
	                    ResultSet programResult = programStmt.executeQuery();



	                    programResult.close(); // Close ResultSet to avoid resource leaks
	                } catch (SQLException e) {
	                    // Log and handle exceptions related to program query execution
	                    e.printStackTrace();
	                    // Continue to process other students or handle this error appropriately
	                }
	            }
	            return productList;
	        }
		
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
			updateStmt.setFloat(3, product.getPrice());
			updateStmt.setString(4, product.getImageUrl());
			
			return updateStmt.executeUpdate() > 0;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}