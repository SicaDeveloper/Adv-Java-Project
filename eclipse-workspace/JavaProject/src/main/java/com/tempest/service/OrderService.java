package com.tempest.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.tempest.config.DbConfig;
import com.tempest.model.OrderModel;

public class OrderService {
    private static Connection dbConn;
    private boolean isConnectionError = false;

    // Valid order statuses from the database enum
    private static final String[] VALID_STATUSES = {"pending", "delivered", "received"};

    public OrderService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            isConnectionError = true;
        }
    }

    // Create - Add new order with products
    public Boolean createOrder(OrderModel order, List<Integer> productIds) {8
        if (isConnectionError) {
            System.out.println("Connection Error");
            return false;
        }

        if (!isValidStatus(order.getStatus())) {
            System.out.println("Invalid order status");
            return false;
        }

        String orderQuery = "INSERT INTO orders (status, date, amount) VALUES (?, ?, ?)";
        String orderProductsQuery = "INSERT INTO order_products (order_id, product_id, quantity) VALUES (?, ?, ?)";
        String userOrderQuery = "INSERT INTO user_order (user_id, order_id) VALUES (?, ?)";
        
        try {
            dbConn.setAutoCommit(false); // Start transaction
            
            // First insert the order
            try (PreparedStatement orderStmt = dbConn.prepareStatement(orderQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                orderStmt.setString(1, order.getStatus());
                orderStmt.setDate(2, new Date(order.getOrderDate().getTime()));
                orderStmt.setDouble(3, order.getTotalAmount());
                
                int affectedRows = orderStmt.executeUpdate();
                if (affectedRows == 0) {
                    dbConn.rollback();
                    return false;
                }

                // Get the generated order ID
                try (ResultSet generatedKeys = orderStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int orderId = generatedKeys.getInt(1);
                        
                        // Insert user_order relationship
                        try (PreparedStatement userOrderStmt = dbConn.prepareStatement(userOrderQuery)) {
                            userOrderStmt.setInt(1, order.getUserId());
                            userOrderStmt.setInt(2, orderId);
                            userOrderStmt.executeUpdate();
                        }
                        
                        // Then insert the order products with quantities
                        try (PreparedStatement productsStmt = dbConn.prepareStatement(orderProductsQuery)) {
                            // Create a map to count occurrences of each product ID
                            java.util.Map<Integer, Integer> productQuantities = new java.util.HashMap<>();
                            for (Integer productId : productIds) {
                                productQuantities.merge(productId, 1, Integer::sum);
                            }
                            
                            // Insert each product with its quantity
                            for (java.util.Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {
                                productsStmt.setInt(1, orderId);
                                productsStmt.setInt(2, entry.getKey());
                                productsStmt.setInt(3, entry.getValue());
                                productsStmt.addBatch();
                            }
                            productsStmt.executeBatch();
                        }
                        
                        dbConn.commit();
                        return true;
                    } else {
                        dbConn.rollback();
                        return false;
                    }
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

    // Read - Get all orders
    public List<OrderModel> getAllOrders() {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return null;
        }

        String query = "SELECT o.*, uo.user_id FROM orders o " +
                      "LEFT JOIN user_order uo ON o.id = uo.order_id " +
                      "ORDER BY o.date DESC";
        List<OrderModel> orders = new ArrayList<>();

        try (PreparedStatement stmt = dbConn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                OrderModel order = new OrderModel(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("status"),
                    rs.getDate("date"),
                    rs.getInt("amount")
                );
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Read - Get order by ID
    public OrderModel getOrderById(int orderId) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return null;
        }

        String query = "SELECT o.*, uo.user_id FROM orders o " +
                      "LEFT JOIN user_order uo ON o.id = uo.order_id " +
                      "WHERE o.id = ?";

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new OrderModel(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("status"),
                    rs.getDate("date"),
                    rs.getInt("amount")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get orders for a user
    public List<OrderModel> getOrdersByUserId(int userId) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return null;
        }

        String query = "SELECT o.* FROM orders o " +
                      "JOIN user_order uo ON o.id = uo.order_id " +
                      "WHERE uo.user_id = ? " +
                      "ORDER BY o.date DESC";
        List<OrderModel> orders = new ArrayList<>();

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderModel order = new OrderModel(
                    rs.getInt("id"),
                    userId,
                    rs.getString("status"),
                    rs.getDate("date"),
                    rs.getInt("amount")
                );
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get products for an order
    public List<Integer> getOrderProducts(int orderId) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return null;
        }

        String query = "SELECT product_id FROM order_products WHERE order_id = ?";
        List<Integer> productIds = new ArrayList<>();

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                productIds.add(rs.getInt("product_id"));
            }
            return productIds;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update - Update order and its products
    public Boolean updateOrder(OrderModel order, List<Integer> productIds) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return false;
        }

        if (!isValidStatus(order.getStatus())) {
            System.out.println("Invalid order status");
            return false;
        }

        String orderQuery = "UPDATE orders SET status = ?, date = ?, amount = ? WHERE id = ?";
        String deleteProductsQuery = "DELETE FROM order_products WHERE order_id = ?";
        String insertProductsQuery = "INSERT INTO order_products (order_id, product_id, quantity) VALUES (?, ?, ?)";
        String updateUserOrderQuery = "UPDATE user_order SET user_id = ? WHERE order_id = ?";

        try {
            dbConn.setAutoCommit(false); // Start transaction
            
            // First update the order
            try (PreparedStatement orderStmt = dbConn.prepareStatement(orderQuery)) {
                orderStmt.setString(1, order.getStatus());
                orderStmt.setDate(2, new Date(order.getOrderDate().getTime()));
                orderStmt.setDouble(3, order.getTotalAmount());
                orderStmt.setInt(4, order.getId());
                
                if (orderStmt.executeUpdate() == 0) {
                    dbConn.rollback();
                    return false;
                }
            }

            // Update user_order relationship
            try (PreparedStatement userOrderStmt = dbConn.prepareStatement(updateUserOrderQuery)) {
                userOrderStmt.setInt(1, order.getUserId());
                userOrderStmt.setInt(2, order.getId());
                userOrderStmt.executeUpdate();
            }

            // Then update the products
            try (PreparedStatement deleteStmt = dbConn.prepareStatement(deleteProductsQuery);
                 PreparedStatement insertStmt = dbConn.prepareStatement(insertProductsQuery)) {
                
                // Delete existing products
                deleteStmt.setInt(1, order.getId());
                deleteStmt.executeUpdate();
                
                // Create a map to count occurrences of each product ID
                java.util.Map<Integer, Integer> productQuantities = new java.util.HashMap<>();
                for (Integer productId : productIds) {
                    productQuantities.merge(productId, 1, Integer::sum);
                }
                
                // Insert new products with quantities
                for (java.util.Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {
                    insertStmt.setInt(1, order.getId());
                    insertStmt.setInt(2, entry.getKey());
                    insertStmt.setInt(3, entry.getValue());
                    insertStmt.addBatch();
                }
                insertStmt.executeBatch();
            }
            
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

    // Delete - Delete order and its products
    public Boolean deleteOrder(int orderId) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return false;
        }

        // First delete related records in order_delivery, order_payment, order_products, and user_order
        String[] relatedQueries = {
            "DELETE FROM order_delivery WHERE order_id = ?",
            "DELETE FROM order_payment WHERE order_id = ?",
            "DELETE FROM order_products WHERE order_id = ?",
            "DELETE FROM user_order WHERE order_id = ?",
            "DELETE FROM orders WHERE id = ?"
        };

        try {
            dbConn.setAutoCommit(false); // Start transaction
            
            for (String query : relatedQueries) {
                try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
                    stmt.setInt(1, orderId);
                    stmt.executeUpdate();
                }
            }
            
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

    // Get orders by status
    public List<OrderModel> getOrdersByStatus(String status) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return null;
        }

        if (!isValidStatus(status)) {
            System.out.println("Invalid order status");
            return null;
        }

        String query = "SELECT o.*, uo.user_id FROM orders o " +
                      "LEFT JOIN user_order uo ON o.id = uo.order_id " +
                      "WHERE o.status = ? ORDER BY o.date DESC";
        List<OrderModel> orders = new ArrayList<>();

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderModel order = new OrderModel(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("status"),
                    rs.getDate("date"),
                    rs.getInt("amount")
                );
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get orders by date range
    public List<OrderModel> getOrdersByDateRange(Date startDate, Date endDate) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return null;
        }

        String query = "SELECT o.*, uo.user_id FROM orders o " +
                      "LEFT JOIN user_order uo ON o.id = uo.order_id " +
                      "WHERE o.date BETWEEN ? AND ? ORDER BY o.date DESC";
        List<OrderModel> orders = new ArrayList<>();

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderModel order = new OrderModel(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("status"),
                    rs.getDate("date"),
                    rs.getInt("amount")
                );
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Helper method to validate order status
    private boolean isValidStatus(String status) {
        if (status == null) return false;
        for (String validStatus : VALID_STATUSES) {
            if (validStatus.equalsIgnoreCase(status)) {
                return true;
            }
        }
        return false;
    }
} 