package com.tempest.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.tempest.config.DbConfig;
import com.tempest.model.OrderModel;

public class OrderService {
    private static Connection dbConn;
    private boolean isConnectionError = false;

    // Valid order statuses
    private static final String[] VALID_STATUSES = {"pending", "processing", "shipped", "delivered", "cancelled"};

    public OrderService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Create - Add new order with transaction support
    public Boolean createOrder(OrderModel order) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return false;
        }

        if (!isValidStatus(order.getStatus())) {
            System.out.println("Invalid order status");
            return false;
        }

        String query = "INSERT INTO orders (user_id, total_amount, status, order_date) VALUES (?, ?, ?, ?)";
        
        try {
            dbConn.setAutoCommit(false); // Start transaction
            
            try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
                stmt.setInt(1, order.getUserId());
                stmt.setDouble(2, order.getTotalAmount());
                stmt.setString(3, order.getStatus());
                stmt.setTimestamp(4, order.getOrderDate());
                
                int result = stmt.executeUpdate();
                
                if (result > 0) {
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

    // Read - Get all orders
    public List<OrderModel> getAllOrders() {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return null;
        }

        String query = "SELECT * FROM orders ORDER BY order_date DESC";
        List<OrderModel> orders = new ArrayList<>();

        try (PreparedStatement stmt = dbConn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                OrderModel order = new OrderModel(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getDouble("total_amount"),
                    rs.getString("status"),
                    rs.getTimestamp("order_date")
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

        String query = "SELECT * FROM orders WHERE id = ?";

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new OrderModel(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getDouble("total_amount"),
                    rs.getString("status"),
                    rs.getTimestamp("order_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update - Update order
    public Boolean updateOrder(OrderModel order) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return false;
        }

        if (!isValidStatus(order.getStatus())) {
            System.out.println("Invalid order status");
            return false;
        }

        String query = "UPDATE orders SET user_id = ?, total_amount = ?, status = ?, order_date = ? WHERE id = ?";

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, order.getUserId());
            stmt.setDouble(2, order.getTotalAmount());
            stmt.setString(3, order.getStatus());
            stmt.setTimestamp(4, order.getOrderDate());
            stmt.setInt(5, order.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete - Delete order
    public Boolean deleteOrder(int orderId) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return false;
        }

        String query = "DELETE FROM orders WHERE id = ?";

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get orders by user ID
    public List<OrderModel> getOrdersByUserId(int userId) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return null;
        }

        String query = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";
        List<OrderModel> orders = new ArrayList<>();

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderModel order = new OrderModel(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getDouble("total_amount"),
                    rs.getString("status"),
                    rs.getTimestamp("order_date")
                );
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
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

        String query = "SELECT * FROM orders WHERE status = ? ORDER BY order_date DESC";
        List<OrderModel> orders = new ArrayList<>();

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderModel order = new OrderModel(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getDouble("total_amount"),
                    rs.getString("status"),
                    rs.getTimestamp("order_date")
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
    public List<OrderModel> getOrdersByDateRange(Timestamp startDate, Timestamp endDate) {
        if (isConnectionError) {
            System.out.println("Connection Error");
            return null;
        }

        String query = "SELECT * FROM orders WHERE order_date BETWEEN ? AND ? ORDER BY order_date DESC";
        List<OrderModel> orders = new ArrayList<>();

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setTimestamp(1, startDate);
            stmt.setTimestamp(2, endDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderModel order = new OrderModel(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getDouble("total_amount"),
                    rs.getString("status"),
                    rs.getTimestamp("order_date")
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