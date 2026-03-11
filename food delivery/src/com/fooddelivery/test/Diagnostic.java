package com.fooddelivery.test;

import com.fooddelivery.dao.DBConnection;
import java.sql.Connection;

public class Diagnostic {
    public static void main(String[] args) {
        System.out.println("Testing Database Connection...");
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("SUCCESS: Connected to database!");
            try {
                conn.close();
            } catch (Exception e) {}
        } else {
            System.out.println("FAILURE: Could not connect to database.");
            System.out.println("Check if MySQL is running on port 3306 and verify password.");
        }
    }
}
