package com.tempest.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tempest.config.DbConfig;
import com.tempest.model.CategoriesModel;
import com.tempest.model.ProductModel;

public class ProductService{
	private static Connection dbConn;
	private boolean isConnectionError = false;


	public ProductService() {
		try{
			dbConn = DbConfig.getDbConnection();
			} catch (SQLException|ClassNotFoundException e) {
			e.getMessage();
		}
	}
	
	public List<ProductModel> getAllProductsInfo(){
		
		if(isConnectionError == true) {
			System.out.println("Connection Error");
			return null;
		}
		
		String query = "SELECT p.*, pc.category_id FROM product p " +
					  "LEFT JOIN product_category pc ON p.id = pc.product_id";
		
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			List<ProductModel> productList = new ArrayList<>();

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				Double price = rs.getDouble("price");
				int quantity = rs.getInt("quantity");
				String imageUrl = rs.getString("imageUrl");
				int categoryId = rs.getInt("category_id");
				
				ProductModel product = new ProductModel(id, name, description, price, quantity, imageUrl);
				product.setCategoryId(categoryId);
				productList.add(product);
			}
			return productList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	public Boolean createProduct(ProductModel product) {
		
		if(isConnectionError) {
			System.out.println("Connection Error");
			return false;
		}
		
		try {
			// Start transaction
			dbConn.setAutoCommit(false);

			// Insert product
			String productQuery = "INSERT INTO product (name, description, price, quantity, imageUrl) VALUES (?,?,?,?,?)";
			try (PreparedStatement productStmt = dbConn.prepareStatement(productQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
				productStmt.setString(1, product.getName());
				productStmt.setString(2, product.getDescription());
				productStmt.setDouble(3, product.getPrice());
				productStmt.setInt(4, product.getQuantity());
				productStmt.setString(5, product.getImageUrl());

				int affectedRows = productStmt.executeUpdate();
				if (affectedRows == 0) {
					dbConn.rollback();
					return false;
				}

				// Get the generated product ID
				try (ResultSet generatedKeys = productStmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						int productId = generatedKeys.getInt(1);

						// Insert category relationship
						if (product.getCategoryId() > 0) {
							String categoryQuery = "INSERT INTO product_category (product_id, category_id) VALUES (?, ?)";
							try (PreparedStatement categoryStmt = dbConn.prepareStatement(categoryQuery)) {
								categoryStmt.setInt(1, productId);
								categoryStmt.setInt(2, product.getCategoryId());
								if (categoryStmt.executeUpdate() == 0) {
									dbConn.rollback();
									return false;
								}
							}
						}
					}
				}
			}

			// Commit transaction
			dbConn.commit();
			return true;
		} catch (SQLException e) {
			try {
				dbConn.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return false;
		} finally {
			try {
				dbConn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Boolean removeProduct(ProductModel product) {
		if(isConnectionError) {
			System.out.println("Connection Error");
			return false;
		}
		
		try {
			// Start transaction
			dbConn.setAutoCommit(false);

			// First remove the category relationship
			String categoryQuery = "DELETE FROM product_category WHERE product_id = ?";
			try (PreparedStatement categoryStmt = dbConn.prepareStatement(categoryQuery)) {
				categoryStmt.setInt(1, product.getId());
				categoryStmt.executeUpdate();
			}

			// Then remove the product
			String productQuery = "DELETE FROM product WHERE id = ?";
			try (PreparedStatement productStmt = dbConn.prepareStatement(productQuery)) {
				productStmt.setInt(1, product.getId());
				int affectedRows = productStmt.executeUpdate();
				if (affectedRows == 0) {
					dbConn.rollback();
					return false;
				}
			}

			// Commit transaction
			dbConn.commit();
			return true;
		} catch (SQLException e) {
			try {
				dbConn.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return false;
		} finally {
			try {
				dbConn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Boolean updateProduct(ProductModel product) {
		if(isConnectionError) {
			System.out.println("Connection Error");
			return false;
		}
		
		try {
			// Start transaction
			dbConn.setAutoCommit(false);

			// Update product
			String productQuery = "UPDATE product SET name = ?, description = ?, price = ?, quantity = ?, imageUrl = ? WHERE id = ?";
			try (PreparedStatement productStmt = dbConn.prepareStatement(productQuery)) {
				productStmt.setString(1, product.getName());
				productStmt.setString(2, product.getDescription());
				productStmt.setDouble(3, product.getPrice());
				productStmt.setInt(4, product.getQuantity());
				productStmt.setString(5, product.getImageUrl());
				productStmt.setInt(6, product.getId());

				int affectedRows = productStmt.executeUpdate();
				if (affectedRows == 0) {
					dbConn.rollback();
					return false;
				}
			}

			// Update category relationship
			if (product.getCategoryId() > 0) {
				// First check if the relationship exists
				String checkQuery = "SELECT COUNT(*) FROM product_category WHERE product_id = ?";
				boolean relationshipExists = false;
				try (PreparedStatement checkStmt = dbConn.prepareStatement(checkQuery)) {
					checkStmt.setInt(1, product.getId());
					try (ResultSet rs = checkStmt.executeQuery()) {
						if (rs.next()) {
							relationshipExists = rs.getInt(1) > 0;
						}
					}
				}

				if (relationshipExists) {
					// Update existing relationship
					String updateCategoryQuery = "UPDATE product_category SET category_id = ? WHERE product_id = ?";
					try (PreparedStatement categoryStmt = dbConn.prepareStatement(updateCategoryQuery)) {
						categoryStmt.setInt(1, product.getCategoryId());
						categoryStmt.setInt(2, product.getId());
						categoryStmt.executeUpdate();
					}
				} else {
					// Insert new relationship
					String insertCategoryQuery = "INSERT INTO product_category (product_id, category_id) VALUES (?, ?)";
					try (PreparedStatement categoryStmt = dbConn.prepareStatement(insertCategoryQuery)) {
						categoryStmt.setInt(1, product.getId());
						categoryStmt.setInt(2, product.getCategoryId());
						categoryStmt.executeUpdate();
					}
				}
			}

			// Commit transaction
			dbConn.commit();
			return true;
		} catch (SQLException e) {
			try {
				dbConn.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return false;
		} finally {
			try {
				dbConn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String findProductCategory(int productId) {
		if(isConnectionError) {
			System.out.println("Connection Error");
			return null;
		}
		
		String query = "SELECT\r\n"
				+ "    pc.product_id,\r\n"
				+ "    pc.category_id AS product_category_id,\r\n"
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
	
	public ProductModel getProductById(int productId) {
		if(isConnectionError) {
			System.out.println("Connection Error");
			return null;
		}
		
		String query = "SELECT p.*, pc.category_id FROM product p " +
					  "LEFT JOIN product_category pc ON p.id = pc.product_id " +
					  "WHERE p.id = ?";
		
		try(PreparedStatement searchStmt = dbConn.prepareStatement(query)){
			searchStmt.setInt(1, productId);
			ResultSet resultSet = searchStmt.executeQuery();
			
			if (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				Double price = resultSet.getDouble("price");
				int quantity = resultSet.getInt("quantity");
				String imageUrl = resultSet.getString("imageUrl");
				int categoryId = resultSet.getInt("category_id");
				
				ProductModel product = new ProductModel(id, name, description, price, quantity, imageUrl);
				product.setCategoryId(categoryId);
				return product;
			}
			return null;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Boolean updateProductCategory(int productId, int categoryId) {
	    if(isConnectionError) {
	        System.out.println("Connection Error");
	        return false;
	    }
	    
	    String query = "UPDATE product_category SET category_id = ? WHERE product_id = ?";
	    
	    try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
	        stmt.setInt(1, categoryId);
	        stmt.setInt(2, productId);
	        return stmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public CategoriesModel getProductCategory(int productId) {
        if(isConnectionError) {
            System.out.println("Connection Error");
            return null;
        }
        
        String query = "SELECT c.* FROM category c " +
                      "JOIN product_category pc ON c.id = pc.category_id " +
                      "WHERE pc.product_id = ?";
        
        try(PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                return new CategoriesModel(id, name, description);
            }
            return null;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get products by their IDs
    public List<ProductModel> getProductsByIds(List<Integer> productIds) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return null;
        }

        if (productIds == null || productIds.isEmpty()) {
            return new ArrayList<>();
        }

        // Create a parameterized query with the correct number of placeholders
        String placeholders = String.join(",", Collections.nCopies(productIds.size(), "?"));
        String query = "SELECT * FROM product WHERE id IN (" + placeholders + ")";

        List<ProductModel> products = new ArrayList<>();

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            // Set each product ID as a parameter
            for (int i = 0; i < productIds.size(); i++) {
                stmt.setInt(i + 1, productIds.get(i));
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProductModel product = new ProductModel(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("price"),
                    rs.getInt("quantity"),
                    rs.getString("imageUrl")
                );
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}