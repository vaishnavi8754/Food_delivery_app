package com.fooddelivery.dao;

import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        System.out.println("Testing DB connection...");
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("SUCCESS: Connection established!");
            DBConnection.closeConnection(conn);
        } else {
            System.out.println("FAILURE: Connection failed!");
        }
    }
}
