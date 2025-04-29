package com.tempest.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tempest.config.DbConfig;
import com.tempest.model.UserModel;

/**
 * Service class for interacting with the database to retrieve dashboard-related data.
 * This class handles database connections and performs queries to fetch student information.
 */
public class DashboardService {

    private Connection dbConn;
    private boolean isConnectionError = false;

    /**
     * Constructor that initializes the database connection. Sets the connection error
     * flag if the connection fails.
     */
    public DashboardService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            // Log and handle exceptions related to database connection
            ex.printStackTrace();
            isConnectionError = true;
        }
    }

    /**
     * Retrieves all student information from the database.
     * 
     * @return A list of StudentModel objects containing student data. Returns null if
     *         there is a connection error or if an exception occurs during query execution.
     */
    public List<UserModel> getAllUserInfo() {
        if (isConnectionError) {
            System.out.println("Connection Error!");
            return null;
        }

        // SQL query to fetch student details
        String query = "SELECT student_id, first_name, last_name, program_id, email, number FROM student";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            ResultSet result = stmt.executeQuery();
            List<UserModel> userList = new ArrayList<>();

            while (result.next()) {
                // SQL query to fetch program name based on program_id
                try (PreparedStatement programStmt = dbConn.prepareStatement(programQuery)) {
                    programStmt.setInt(1, result.getInt("program_id"));
                    ResultSet programResult = programStmt.executeQuery();



                    programResult.close(); // Close ResultSet to avoid resource leaks
                } catch (SQLException e) {
                    // Log and handle exceptions related to program query execution
                    e.printStackTrace();
                    // Continue to process other students or handle this error appropriately
                }
            }
            return userList;
        } catch (SQLException e) {
            // Log and handle exceptions related to student query execution
            e.printStackTrace();
            return null;
        }
    }
}