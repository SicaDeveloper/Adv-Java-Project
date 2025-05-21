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
		

		String query = "SELECT id, first_name, last_name, email, phone, address, role from user";
		  
        try(PreparedStatement stmt = dbConn.prepareStatement(query)){
        		
            ResultSet rs = stmt.executeQuery();
            List<UserModel> userList = new ArrayList<>();

                   // Iterate through the ResultSet and create Category objects
                   while (rs.next()) {
                       int userId = rs.getInt("id");
                       String firstName = rs.getString("first_name");
                       String lastName = rs.getString("last_name");
                       String email = rs.getString("email");
                       String phone = rs.getString("phone");
                       String address = rs.getString("address");
                       String roleString = rs.getString("role");
                       Roles role = Roles.valueOf(roleString.toLowerCase());
                       
                       UserModel user = new UserModel(firstName, lastName, email, phone, address, role);
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
        if (isConnectionError == true) {
            System.out.println("Connection Error");
            return null;
        }
        
        String query = "SELECT id, first_name, last_name, email, phone, address, role from user where id = ?";
          
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {  // Check if there are any results
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String roleString = rs.getString("role");
                Roles role = Roles.valueOf(roleString.toLowerCase());
              
                UserModel user = new UserModel(firstName, lastName, email, phone, address, role);
                user.setUser_id(id);
                return user;
            }
            return null;  // Return null if no user found
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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
	
    public UserModel getUserById(int id) {
        return getUserDetails(id);
    }

    public boolean updateUser(UserModel user) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return false;
        }

        String query = "UPDATE user SET first_name = ?, last_name = ?, email = ?, phone = ?, address = ?, role = ? WHERE id = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, user.getFirst_name());
            stmt.setString(2, user.getLast_name());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getRole().toString());
            stmt.setInt(7, user.getUser_id());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int userId) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return false;
        }

        try {
            // Start transaction
            dbConn.setAutoCommit(false);
            
            // First delete all cart items associated with the user's carts
            if (!deleteAllUserCartItems(userId)) {
                dbConn.rollback();
                return false;
            }
            
            // Then delete the user's cart
            if (!deleteUserCart(userId)) {
                dbConn.rollback();
                return false;
            }
            
            // Delete user's orders
            if (!deleteUserOrders(userId)) {
                dbConn.rollback();
                return false;
            }
            
            // Finally delete the user
            String query = "DELETE FROM user WHERE id = ?";
            try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
                stmt.setInt(1, userId);
                int rowsAffected = stmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    dbConn.commit();
                    return true;
                } else {
                    dbConn.rollback();
                    return false;
                }
            }
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
    
    private boolean deleteAllUserCartItems(int userId) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return false;
        }

        String query = "DELETE ci FROM cart_item ci " +
                      "JOIN user_cart uc ON ci.cart_id = uc.cart_id " +
                      "WHERE uc.user_id = ?";
                      
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean deleteUserCart(int userId) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return false;
        }

        try {
            // First delete from user_cart
            String deleteUserCart = "DELETE FROM user_cart WHERE user_id = ?";
            try (PreparedStatement stmt = dbConn.prepareStatement(deleteUserCart)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }

            // Then delete from cart (this will only delete carts that are no longer referenced)
            String deleteCart = "DELETE c FROM cart c " +
                              "LEFT JOIN user_cart uc ON c.cart_id = uc.cart_id " +
                              "WHERE uc.cart_id IS NULL";
            try (PreparedStatement stmt = dbConn.prepareStatement(deleteCart)) {
                stmt.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean deleteUserOrders(int userId) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return false;
        }

        try {
            // First delete order_products
            String deleteOrderProducts = "DELETE op FROM order_products op " +
                                       "JOIN user_order uo ON op.order_id = uo.order_id " +
                                       "WHERE uo.user_id = ?";
            try (PreparedStatement stmt = dbConn.prepareStatement(deleteOrderProducts)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }

            // Then delete order_payment
            String deleteOrderPayment = "DELETE op FROM order_payment op " +
                                      "JOIN user_order uo ON op.order_id = uo.order_id " +
                                      "WHERE uo.user_id = ?";
            try (PreparedStatement stmt = dbConn.prepareStatement(deleteOrderPayment)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }

            // Then delete order_delivery
            String deleteOrderDelivery = "DELETE od FROM order_delivery od " +
                                       "JOIN user_order uo ON od.order_id = uo.order_id " +
                                       "WHERE uo.user_id = ?";
            try (PreparedStatement stmt = dbConn.prepareStatement(deleteOrderDelivery)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }

            // Then delete user_order
            String deleteUserOrder = "DELETE FROM user_order WHERE user_id = ?";
            try (PreparedStatement stmt = dbConn.prepareStatement(deleteUserOrder)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }

            // Finally delete the orders
            String deleteOrders = "DELETE o FROM orders o " +
                                "JOIN user_order uo ON o.id = uo.order_id " +
                                "WHERE uo.user_id = ?";
            try (PreparedStatement stmt = dbConn.prepareStatement(deleteOrders)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}