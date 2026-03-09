package com.fooddelivery.test;

import com.fooddelivery.dao.DBConnection;
import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("Connection SUCCESSFUL!");
            DBConnection.closeConnection(conn);
        } else {
            System.out.println("Connection FAILED!");
        }
    }
}
