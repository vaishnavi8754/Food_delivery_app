package com.fooddelivery.dao;

import com.fooddelivery.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User Data Access Object
 * Handles all database operations related to User
 */
public class UserDAO {

    private Connection connection;

    // Static storage for demo users when DB is not available
    private static final List<User> demoUsers = new ArrayList<>();

    static {
        // Default demo user
        User defaultUser = new User();
        defaultUser.setUserId(999);
        defaultUser.setFullName("Demo User");
        defaultUser.setEmail("demo@example.com");
        defaultUser.setPassword("demo123");
        defaultUser.setRole("user");
        demoUsers.add(defaultUser);

        // Recovered user account (fallback for DB connection issues)
        User recoveredUser = new User();
        recoveredUser.setUserId(1001);
        recoveredUser.setFullName("Vaishnavi Dhivakar");
        recoveredUser.setEmail("vaishnavi.dhivakar@gmail.com");
        recoveredUser.setPassword("vaish@03");
        recoveredUser.setRole("user");
        demoUsers.add(recoveredUser);
    }

    /**
     * Constructor - Initialize database connection
     */
    public UserDAO() {
        // Connection is retrieved per method call
    }

    /**
     * Register a new user
     * 
     * @param user User object to register
     * @return true if registration successful, false otherwise
     */
    public boolean registerUser(User user) {
        String sql = "INSERT INTO user (full_name, email, password, phone, address, role) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            connection = DBConnection.getConnection();

            // DEMO BYPASS: If DB connection fails, store user in session memory
            if (connection == null) {
                System.out.println("DEMO MODE: Registration temporarily stored in memory for: " + user.getEmail());
                // Increment ID for demo users
                user.setUserId(1000 + demoUsers.size());
                demoUsers.add(user);
                return true;
            }

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getRole() != null ? user.getRole() : "user");

            int rowsAffected = stmt.executeUpdate();
            stmt.close();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                DBConnection.closeConnection(connection);
            }
        }
    }

    /**
     * Validate user login credentials
     * 
     * @param email    User email
     * @param password User password
     * @return User object if valid, null otherwise
     */
    public User validateUser(String email, String password) {
        // DEMO LOGIN BYPASS - Check our temporary memory first
        for (User u : demoUsers) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                return u;
            }
        }

        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        User user = null;

        try {
            connection = DBConnection.getConnection();
            if (connection == null)
                return null; // Avoid NPE if DB fails
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = extractUserFromResultSet(rs);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("Error validating user: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }

        return user;
    }

    /**
     * Check if email already exists
     * 
     * @param email Email to check
     * @return true if exists, false otherwise
     */
    public boolean emailExists(String email) {
        // DEMO BYPASS - Check our temporary memory
        for (User u : demoUsers) {
            if (u.getEmail().equals(email)) {
                return true;
            }
        }

        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";

        try {
            connection = DBConnection.getConnection();
            if (connection == null)
                return false; // Avoid NPE if DB fails
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("Error checking email: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }

        return false;
    }

    /**
     * Get user by ID
     * 
     * @param userId User ID
     * @return User object or null
     */
    public User getUserById(int userId) {
        String sql = "SELECT * FROM user WHERE user_id = ?";
        User user = null;

        try {
            connection = DBConnection.getConnection();
            if (connection == null)
                return null;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = extractUserFromResultSet(rs);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("Error getting user: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                DBConnection.closeConnection(connection);
            }
        }

        return user;
    }

    /**
     * Get user by email
     * 
     * @param email User email
     * @return User object or null
     */
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        User user = null;

        try {
            connection = DBConnection.getConnection();
            if (connection == null)
                return null;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = extractUserFromResultSet(rs);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("Error getting user by email: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                DBConnection.closeConnection(connection);
            }
        }

        return user;
    }

    /**
     * Get all users
     * 
     * @return List of all users
     */
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM user ORDER BY created_at DESC";
        List<User> users = new ArrayList<>();

        try {
            connection = DBConnection.getConnection();
            if (connection == null)
                return users;
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("Error getting all users: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                DBConnection.closeConnection(connection);
            }
        }

        return users;
    }

    /**
     * Update user profile
     * 
     * @param user User with updated information
     * @return true if update successful
     */
    public boolean updateUser(User user) {
        String sql = "UPDATE user SET full_name = ?, phone = ?, address = ?, updated_at = CURRENT_TIMESTAMP WHERE user_id = ?";

        try {
            connection = DBConnection.getConnection();
            if (connection == null)
                return false;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getPhone());
            stmt.setString(3, user.getAddress());
            stmt.setInt(4, user.getUserId());

            int rowsAffected = stmt.executeUpdate();
            stmt.close();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                DBConnection.closeConnection(connection);
            }
        }
    }

    /**
     * Update user password
     * 
     * @param userId      User ID
     * @param newPassword New password
     * @return true if update successful
     */
    public boolean updatePassword(int userId, String newPassword) {
        String sql = "UPDATE user SET password = ?, updated_at = CURRENT_TIMESTAMP WHERE user_id = ?";

        try {
            connection = DBConnection.getConnection();
            if (connection == null)
                return false;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);

            int rowsAffected = stmt.executeUpdate();
            stmt.close();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating password: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                DBConnection.closeConnection(connection);
            }
        }
    }

    /**
     * Delete user
     * 
     * @param userId User ID to delete
     * @return true if deletion successful
     */
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM user WHERE user_id = ?";

        try {
            connection = DBConnection.getConnection();
            if (connection == null)
                return false;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, userId);

            int rowsAffected = stmt.executeUpdate();
            stmt.close();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                DBConnection.closeConnection(connection);
            }
        }
    }

    /**
     * Helper method to extract User from ResultSet
     * 
     * @param rs ResultSet
     * @return User object
     * @throws SQLException
     */
    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setPhone(rs.getString("phone"));
        user.setAddress(rs.getString("address"));
        user.setRole(rs.getString("role"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        user.setUpdatedAt(rs.getTimestamp("updated_at"));
        return user;
    }
}
