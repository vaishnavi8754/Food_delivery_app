package com.fooddelivery.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection Utility Class
 * Provides connection to MySQL database
 */
public class DBConnection {
    
    // Database Configuration - Update these values according to your setup
    private static final String DB_URL = "jdbc:mysql://localhost:3306/food_delivery_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // Leave empty to pick up FOODEXPRESS_DB_PASSWORD env var
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    /**
     * Get database connection
     * @return Connection object
     */
    public static Connection getConnection() {
        String[] commonPasswords = {DB_PASSWORD, "root", "password", "1234", "123456", "admin", "foodexpress"};
        
        System.out.println("Attempting to connect to: " + DB_URL + " as " + DB_USER);

        // Prefer environment variable if it exists
        String envPwd = System.getenv("FOODEXPRESS_DB_PASSWORD");
        if (envPwd != null && !envPwd.isEmpty()) {
            commonPasswords = new String[]{envPwd};
        }

        for (String password : commonPasswords) {
            try {
                Class.forName(DB_DRIVER);
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, password);
                if (conn != null) {
                    System.out.println("DATABASE CONNECTED SUCCESSFULLY with password: " + (password.isEmpty() ? "(empty)" : "********"));
                    return conn;
                }
            } catch (ClassNotFoundException e) {
                System.err.println("CRITICAL: MySQL JDBC Driver not found!");
                break;
            } catch (SQLException e) {
                // System.out.println("DEBUG: Failed with password: " + password + " error: " + e.getMessage());
            }
        }
        
        System.err.println("DATABASE CONNECTION FAILED: None of the common passwords worked. Please update DB_PASSWORD in DBConnection.java.");
        return null;
    }
    
    /**
     * Close database connection
     * @param connection Connection to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Test database connection
     * @return true if connection successful, false otherwise
     */
    public static boolean testConnection() {
        Connection conn = getConnection();
        if (conn != null) {
            closeConnection(conn);
            return true;
        }
        return false;
    }
}
