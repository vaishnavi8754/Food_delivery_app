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
    
    private static Connection connection = null;
    
    /**
     * Get database connection
     * @return Connection object
     */
    public static Connection getConnection() {
        try {
            // Load the MySQL JDBC driver
            Class.forName(DB_DRIVER);
            
            // Determine password: prefer environment variable FOODEXPRESS_DB_PASSWORD
            String password = DB_PASSWORD;
            String envPwd = System.getenv("FOODEXPRESS_DB_PASSWORD");
            if (envPwd != null && !envPwd.isEmpty()) {
                password = envPwd;
            }
            // Create connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, password);
            
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
        }
        
        return connection;
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
