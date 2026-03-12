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
        defaultUser.setPhone("9876543210");
        defaultUser.setRole("customer");
        defaultUser.setStatus("active");
        defaultUser.setTotalOrders(5);
        defaultUser.setTotalSpent(1250.00);
        demoUsers.add(defaultUser);

        // Recovered user account
        User recoveredUser = new User();
        recoveredUser.setUserId(1001);
        recoveredUser.setFullName("Vaishnavi Dhivakar");
        recoveredUser.setEmail("vaishnavi.dhivakar@gmail.com");
        recoveredUser.setPassword("vaish@03");
        recoveredUser.setPhone("1234567890");
        recoveredUser.setRole("customer");
        recoveredUser.setStatus("active");
        demoUsers.add(recoveredUser);

        // Default demo admin
        User defaultAdmin = new User();
        defaultAdmin.setUserId(998);
        defaultAdmin.setFullName("Admin User");
        defaultAdmin.setEmail("admin@foodexpress.com");
        defaultAdmin.setPassword("admin123");
        defaultAdmin.setPhone("9999999999");
        defaultAdmin.setRole("admin");
        defaultAdmin.setStatus("active");
        demoUsers.add(defaultAdmin);

        // Demo Delivery Partner
        User deliveryPartner = new User();
        deliveryPartner.setUserId(997);
        deliveryPartner.setFullName("Ravi Kumar");
        deliveryPartner.setEmail("ravi@delivery.com");
        deliveryPartner.setPassword("ravi123");
        deliveryPartner.setPhone("8888888888");
        deliveryPartner.setRole("delivery_partner");
        deliveryPartner.setStatus("active");
        demoUsers.add(deliveryPartner);
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
            stmt.setString(6, user.getRole() != null ? user.getRole() : "customer");

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
        String sql = "SELECT u.*, COUNT(o.order_id) as total_orders, SUM(o.total_amount) as total_spent " +
                     "FROM user u LEFT JOIN `order` o ON u.user_id = o.user_id " +
                     "GROUP BY u.user_id ORDER BY u.created_at DESC";
        List<User> users = new ArrayList<>();

        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return new ArrayList<>(demoUsers);
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all users: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
        return users;
    }

    public List<User> searchUsers(String keyword) {
        String sql = "SELECT u.*, COUNT(o.order_id) as total_orders, SUM(o.total_amount) as total_spent " +
                     "FROM user u LEFT JOIN `order` o ON u.user_id = o.user_id " +
                     "WHERE u.full_name LIKE ? OR u.email LIKE ? OR u.phone LIKE ? " +
                     "GROUP BY u.user_id ORDER BY u.created_at DESC";
        List<User> users = new ArrayList<>();
        String pattern = "%" + keyword + "%";

        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            for (User u : demoUsers) {
                if (u.getFullName().toLowerCase().contains(keyword.toLowerCase()) || 
                    u.getEmail().toLowerCase().contains(keyword.toLowerCase()) ||
                    u.getPhone().contains(keyword)) {
                    users.add(u);
                }
            }
            return users;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);
            stmt.setString(3, pattern);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
        return users;
    }

    public List<User> getUsersByFilter(String role, String status) {
        StringBuilder sql = new StringBuilder("SELECT u.*, COUNT(o.order_id) as total_orders, SUM(o.total_amount) as total_spent ")
                .append("FROM user u LEFT JOIN `order` o ON u.user_id = o.user_id WHERE 1=1 ");
        
        List<Object> params = new ArrayList<>();
        if (role != null && !"all".equalsIgnoreCase(role)) {
            sql.append("AND u.role = ? ");
            params.add(role);
        }
        if (status != null && !"all".equalsIgnoreCase(status)) {
            sql.append("AND u.status = ? ");
            params.add(status);
        }
        sql.append("GROUP BY u.user_id ORDER BY u.created_at DESC");

        List<User> users = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            for (User u : demoUsers) {
                boolean roleMatch = role == null || "all".equalsIgnoreCase(role) || role.equalsIgnoreCase(u.getRole());
                boolean statusMatch = status == null || "all".equalsIgnoreCase(status) || status.equalsIgnoreCase(u.getStatus());
                if (roleMatch && statusMatch) users.add(u);
            }
            return users;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
        return users;
    }

    public boolean updateUserStatus(int userId, String status) {
        // Update demo if no DB
        for (User u : demoUsers) {
            if (u.getUserId() == userId) {
                u.setStatus(status);
                // In demo mode we still return true
            }
        }

        String sql = "UPDATE user SET status = ? WHERE user_id = ?";
        Connection conn = DBConnection.getConnection();
        if (conn == null) return true;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBConnection.closeConnection(conn);
        }
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
        
        try {
            user.setStatus(rs.getString("status"));
            user.setLastLogin(rs.getTimestamp("last_login"));
            user.setTotalOrders(rs.getInt("total_orders"));
            user.setTotalSpent(rs.getDouble("total_spent"));
        } catch (SQLException e) {
            // Columns might not exist yet if migration hasn't run
            if (user.getStatus() == null) user.setStatus("active");
        }

        user.setCreatedAt(rs.getTimestamp("created_at"));
        user.setUpdatedAt(rs.getTimestamp("updated_at"));
        return user;
    }
}
