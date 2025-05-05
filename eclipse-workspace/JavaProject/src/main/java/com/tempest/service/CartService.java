package com.tempest.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tempest.config.DbConfig;
import com.tempest.model.CartModel;

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

        String createCartSQL = "INSERT INTO cart (created_date) VALUES (CURRENT_TIMESTAMP)";
        String linkUserCartSQL = "INSERT INTO user_cart (user_id, cart_id) VALUES (?, ?)";

        try {
            dbConn.setAutoCommit(false);
            PreparedStatement createStmt = dbConn.prepareStatement(createCartSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            createStmt.executeUpdate();
            ResultSet rs = createStmt.getGeneratedKeys();

            if (rs.next()) {
                int cartId = rs.getInt(1);
                PreparedStatement linkStmt = dbConn.prepareStatement(linkUserCartSQL);
                linkStmt.setInt(1, userId);
                linkStmt.setInt(2, cartId);
                linkStmt.executeUpdate();
                dbConn.commit();
                return cartId;
            }
            dbConn.rollback();
            return -1;
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
            System.out.println("Connection Error");
            return null;
        }

        String sql = "SELECT c.* FROM cart c " +
                    "JOIN user_cart uc ON c.cart_id = uc.cart_id " +
                    "WHERE uc.user_id = ? " +
                    "ORDER BY c.created_date DESC LIMIT 1";

        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
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
            System.out.println("Connection Error");
            return false;
        }

        String sql = "INSERT INTO cart_item (cart_id, product_id, quantity) " +
                    "VALUES (?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE quantity = quantity + ?";

        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.setInt(4, quantity);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
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

        String sql = "UPDATE cart_item SET quantity = ? WHERE cart_id = ? AND product_id = ?";

        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
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

        String sql = "DELETE FROM cart_item WHERE cart_id = ? AND product_id = ?";

        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            stmt.setInt(2, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
