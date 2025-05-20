package com.tempest.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tempest.config.DbConfig;
import com.tempest.model.CartModel;
import com.tempest.model.ProductModel;

public class CartService {
    private static Connection dbConn;
    private boolean isConnectionError = false;

    public CartService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException e) {
            isConnectionError = true;
            e.printStackTrace();
        }
    }

    /**
     * Create a new cart for a user
     * @param userId The ID of the user
     * @return The ID of the newly created cart, or -1 if creation failed
     */
    public int createCart(int userId) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return -1;
        }

        // First get the most recent cart ID
        String getLastCartIdSQL = "SELECT MAX(cart_id) as last_cart_id FROM cart";
        String createCartSQL = "INSERT INTO cart (cart_id, created_date) VALUES (?, CURRENT_TIMESTAMP)";
        String linkUserCartSQL = "INSERT INTO user_cart (user_id, cart_id) VALUES (?, ?)";

        try {
            dbConn.setAutoCommit(false);
            
            // Get the last cart ID
            int newCartId = 1; // Default to 1 if no carts exist
            try (PreparedStatement getLastIdStmt = dbConn.prepareStatement(getLastCartIdSQL)) {
                ResultSet rs = getLastIdStmt.executeQuery();
                if (rs.next()) {
                    int lastCartId = rs.getInt("last_cart_id");
                    if (lastCartId > 0) {
                        newCartId = lastCartId + 1;
                    }
                }
            }

            // Create new cart with the incremented ID
            PreparedStatement createStmt = dbConn.prepareStatement(createCartSQL);
            createStmt.setInt(1, newCartId);
            createStmt.executeUpdate();

            // Link the cart to the user
            PreparedStatement linkStmt = dbConn.prepareStatement(linkUserCartSQL);
            linkStmt.setInt(1, userId);
            linkStmt.setInt(2, newCartId);
            linkStmt.executeUpdate();

            dbConn.commit();
            return newCartId;
        } catch (SQLException e) {
            try {
                dbConn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return -1;
        } finally {
            try {
                dbConn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Get a cart by its ID
     * @param cartId The ID of the cart to retrieve
     * @return The cart model, or null if not found
     */
    public CartModel getCartById(int cartId) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return null;
        }

        String sql = "SELECT * FROM cart WHERE cart_id = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                CartModel cart = new CartModel();
                cart.setId(rs.getInt("cart_id"));
                cart.setDate(rs.getDate("created_date"));
                return cart;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Get a user's active cart
     * @param userId The ID of the user
     * @return The user's active cart, or null if not found
     */
    public CartModel getUserCart(int userId) {
        if (isConnectionError) {
            System.out.println("CartService: Connection Error");
            return null;
        }

        System.out.println("CartService: Getting cart for user: " + userId);

        String query = "SELECT c.* FROM cart c " +
                      "JOIN user_cart uc ON c.cart_id = uc.cart_id " +
                      "WHERE uc.user_id = ? " +
                      "ORDER BY c.created_date DESC LIMIT 1";

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                CartModel cart = new CartModel();
                cart.setId(rs.getInt("cart_id"));
                cart.setDate(rs.getDate("created_date"));
                System.out.println("CartService: Found cart with ID: " + cart.getId());
                return cart;
            }
            System.out.println("CartService: No cart found for user");
            return null;
        } catch (SQLException e) {
            System.out.println("CartService: SQL Error in getUserCart: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Get all carts for a user
     * @param userId The ID of the user
     * @return List of all carts for the user
     */
    public List<CartModel> getUserCarts(int userId) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return null;
        }

        String sql = "SELECT c.* FROM cart c " +
                    "JOIN user_cart uc ON c.cart_id = uc.cart_id " +
                    "WHERE uc.user_id = ? " +
                    "ORDER BY c.created_date DESC";
        List<CartModel> carts = new ArrayList<>();

        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CartModel cart = new CartModel();
                cart.setId(rs.getInt("cart_id"));
                cart.setDate(rs.getDate("created_date"));
                carts.add(cart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carts;
    }
    
    /**
     * Delete a cart and its items
     * @param cartId The ID of the cart to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteCart(int cartId) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return false;
        }

        String deleteItemsSQL = "DELETE FROM cart_item WHERE cart_id = ?";
        String deleteUserCartSQL = "DELETE FROM user_cart WHERE cart_id = ?";
        String deleteCartSQL = "DELETE FROM cart WHERE cart_id = ?";

        try {
            dbConn.setAutoCommit(false);
            PreparedStatement deleteItemsStmt = dbConn.prepareStatement(deleteItemsSQL);
            deleteItemsStmt.setInt(1, cartId);
            deleteItemsStmt.executeUpdate();

            PreparedStatement deleteUserCartStmt = dbConn.prepareStatement(deleteUserCartSQL);
            deleteUserCartStmt.setInt(1, cartId);
            deleteUserCartStmt.executeUpdate();

            PreparedStatement deleteCartStmt = dbConn.prepareStatement(deleteCartSQL);
            deleteCartStmt.setInt(1, cartId);
            int result = deleteCartStmt.executeUpdate();

            dbConn.commit();
            return result > 0;
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
    
    /**
     * Add a product to the cart
     * @param cartId The ID of the cart
     * @param productId The ID of the product to add
     * @param quantity The quantity to add
     * @return true if addition was successful, false otherwise
     */
    public boolean addToCart(int cartId, int productId, int quantity) {
        if (isConnectionError) {
            System.out.println("CartService: Connection Error");
            return false;
        }

        System.out.println("CartService: Adding to cart - CartID: " + cartId + ", ProductID: " + productId + ", Quantity: " + quantity);

        // First check if item already exists in cart
        String checkQuery = "SELECT quantity FROM cart_item WHERE cart_id = ? AND product_id = ?";
        try (PreparedStatement checkStmt = dbConn.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, cartId);
            checkStmt.setInt(2, productId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Item exists, update quantity
                int currentQuantity = rs.getInt("quantity");
                System.out.println("CartService: Item exists in cart with quantity: " + currentQuantity);
                return updateCartItemQuantity(cartId, productId, currentQuantity + quantity);
            } else {
                // Item doesn't exist, insert new
                System.out.println("CartService: Item doesn't exist in cart, inserting new");
                String insertQuery = "INSERT INTO cart_item (cart_id, product_id, quantity) VALUES (?, ?, ?)";
                try (PreparedStatement insertStmt = dbConn.prepareStatement(insertQuery)) {
                    insertStmt.setInt(1, cartId);
                    insertStmt.setInt(2, productId);
                    insertStmt.setInt(3, quantity);
                    int result = insertStmt.executeUpdate();
                    System.out.println("CartService: Insert result: " + (result > 0 ? "success" : "failed"));
                    return result > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("CartService: SQL Error in addToCart: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Update the quantity of a product in the cart
     * @param cartId The ID of the cart
     * @param productId The ID of the product to update
     * @param quantity The new quantity
     * @return true if update was successful, false otherwise
     */
    public boolean updateCartItemQuantity(int cartId, int productId, int quantity) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return false;
        }

        if (quantity <= 0) {
            return removeFromCart(cartId, productId);
        }

        String query = "UPDATE cart_item SET quantity = ? WHERE cart_id = ? AND product_id = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, cartId);
            stmt.setInt(3, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Remove a product from the cart
     * @param cartId The ID of the cart
     * @param productId The ID of the product to remove
     * @return true if removal was successful, false otherwise
     */
    public boolean removeFromCart(int cartId, int productId) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return false;
        }

        String query = "DELETE FROM cart_item WHERE cart_id = ? AND product_id = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, cartId);
            stmt.setInt(2, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get all items in a cart with their product details
     * @param cartId The ID of the cart
     * @return List of products in the cart with their quantities
     */
    public List<ProductModel> getCartItems(int cartId) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return null;
        }

        String query = "SELECT p.*, ci.quantity as cart_quantity FROM product p " +
                      "JOIN cart_item ci ON p.id = ci.product_id " +
                      "WHERE ci.cart_id = ?";
        List<ProductModel> cartItems = new ArrayList<>();

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, cartId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProductModel product = new ProductModel(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"), // This is the product's stock quantity
                    rs.getString("imageUrl")
                );
                // Set the cart quantity separately
                product.setCartQuantity(rs.getInt("cart_quantity"));
                cartItems.add(product);
            }
            return cartItems;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
