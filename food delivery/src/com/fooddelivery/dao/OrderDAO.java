package com.fooddelivery.dao;

import com.fooddelivery.model.Order;
import com.fooddelivery.model.OrderDetails;
import com.fooddelivery.model.CartItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Order Data Access Object
 */
public class OrderDAO {

    public int createOrder(Order order, List<CartItem> cartItems) {
        String orderSql = "INSERT INTO `order` (user_id, restaurant_id, total_amount, delivery_address, status, payment_method, special_instructions) VALUES (?,?,?,?,?,?,?)";
        String detailSql = "INSERT INTO order_details (order_id, food_id, quantity, unit_price, subtotal) VALUES (?,?,?,?,?)";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // Insert order
            PreparedStatement orderStmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, order.getUserId());
            orderStmt.setInt(2, order.getRestaurantId());
            orderStmt.setDouble(3, order.getTotalAmount());
            orderStmt.setString(4, order.getDeliveryAddress());
            orderStmt.setString(5, "pending");
            orderStmt.setString(6, order.getPaymentMethod());
            orderStmt.setString(7, order.getSpecialInstructions());
            orderStmt.executeUpdate();

            ResultSet rs = orderStmt.getGeneratedKeys();
            int orderId = 0;
            if (rs.next()) {
                orderId = rs.getInt(1);
            }

            // Insert order details
            PreparedStatement detailStmt = conn.prepareStatement(detailSql);
            for (CartItem item : cartItems) {
                detailStmt.setInt(1, orderId);
                detailStmt.setInt(2, item.getFoodId());
                detailStmt.setInt(3, item.getQuantity());
                detailStmt.setDouble(4, item.getPrice());
                detailStmt.setDouble(5, item.getSubtotal());
                detailStmt.addBatch();
            }
            detailStmt.executeBatch();

            conn.commit();
            return orderId;

        } catch (SQLException e) {
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException ex) {
            }
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.setAutoCommit(true);
            } catch (SQLException e) {
            }
            DBConnection.closeConnection(conn);
        }
        return 0;
    }

    public List<Order> getOrdersByUser(int userId) {
        String sql = "SELECT o.*, r.name as restaurant_name FROM `order` o JOIN restaurant r ON o.restaurant_id = r.restaurant_id WHERE o.user_id = ? ORDER BY o.order_date DESC";
        List<Order> orders = new ArrayList<>();

        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return getMockOrdersByUser(userId);
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(extractOrder(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
        return orders;
    }

    private List<Order> getMockOrdersByUser(int userId) {
        List<Order> mocks = new ArrayList<>();
        // Only provide mock orders for our demo users
        if (userId == 1001 || userId == 999) {
            Order o1 = new Order();
            o1.setOrderId(5001);
            o1.setUserId(userId);
            o1.setRestaurantId(10);
            o1.setRestaurantName("Sangeetha Veg Restaurant");
            o1.setTotalAmount(450.00);
            o1.setDeliveryAddress("123 Demo Street, Chennai");
            o1.setStatus("delivered");
            o1.setPaymentStatus("paid");
            o1.setPaymentMethod("UPI");
            o1.setOrderDate(new java.sql.Timestamp(System.currentTimeMillis() - 86400000)); // Yesterday
            mocks.add(o1);

            Order o2 = new Order();
            o2.setOrderId(5002);
            o2.setUserId(userId);
            o2.setRestaurantId(3);
            o2.setRestaurantName("Spice Garden");
            o2.setTotalAmount(1250.50);
            o2.setDeliveryAddress("123 Demo Street, Chennai");
            o2.setStatus("pending");
            o2.setPaymentStatus("pending");
            o2.setPaymentMethod("Cash on Delivery");
            o2.setOrderDate(new java.sql.Timestamp(System.currentTimeMillis() - 3600000)); // 1 hour ago
            mocks.add(o2);
        }
        return mocks;
    }

    public Order getOrderById(int orderId) {
        String sql = "SELECT o.*, r.name as restaurant_name, u.full_name as user_name FROM `order` o JOIN restaurant r ON o.restaurant_id = r.restaurant_id JOIN user u ON o.user_id = u.user_id WHERE o.order_id = ?";

        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            // Check mock orders
            for (Order o : getMockOrdersByUser(1001)) { // Check for demo user
                if (o.getOrderId() == orderId) {
                    o.setUserName("Vaishnavi Dhivakar");
                    o.setOrderItems(getMockOrderDetails(orderId));
                    return o;
                }
            }
            return null;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Order order = extractOrder(rs);
                    order.setUserName(rs.getString("user_name"));
                    order.setOrderItems(getOrderDetails(orderId));
                    return order;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
        return null;
    }

    public List<OrderDetails> getOrderDetails(int orderId) {
        String sql = "SELECT od.*, f.name as food_name FROM order_details od JOIN food_item f ON od.food_id = f.food_id WHERE od.order_id = ?";
        List<OrderDetails> details = new ArrayList<>();

        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return getMockOrderDetails(orderId);
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderDetails d = new OrderDetails();
                    d.setOrderDetailId(rs.getInt("order_detail_id"));
                    d.setOrderId(rs.getInt("order_id"));
                    d.setFoodId(rs.getInt("food_id"));
                    d.setFoodName(rs.getString("food_name"));
                    d.setQuantity(rs.getInt("quantity"));
                    d.setUnitPrice(rs.getDouble("unit_price"));
                    d.setSubtotal(rs.getDouble("subtotal"));
                    details.add(d);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
        return details;
    }

    private List<OrderDetails> getMockOrderDetails(int orderId) {
        List<OrderDetails> details = new ArrayList<>();
        if (orderId == 5001) {
            OrderDetails d1 = new OrderDetails();
            d1.setFoodName("Masala Dosa");
            d1.setQuantity(2);
            d1.setUnitPrice(150.0);
            d1.setSubtotal(300.0);
            details.add(d1);

            OrderDetails d2 = new OrderDetails();
            d2.setFoodName("Medu Vada");
            d2.setQuantity(3);
            d2.setUnitPrice(50.0);
            d2.setSubtotal(150.0);
            details.add(d2);
        } else if (orderId == 5002) {
            OrderDetails d1 = new OrderDetails();
            d1.setFoodName("Paneer Tikka");
            d1.setQuantity(2);
            d1.setUnitPrice(350.0);
            d1.setSubtotal(700.0);
            details.add(d1);

            OrderDetails d2 = new OrderDetails();
            d2.setFoodName("Butter Naan");
            d2.setQuantity(4);
            d2.setUnitPrice(60.0);
            d2.setSubtotal(240.0);
            details.add(d2);
        }
        return details;
    }

    public List<Order> getAllOrders() {
        String sql = "SELECT o.*, r.name as restaurant_name, u.full_name as user_name FROM `order` o JOIN restaurant r ON o.restaurant_id = r.restaurant_id JOIN user u ON o.user_id = u.user_id ORDER BY o.order_date DESC";
        List<Order> orders = new ArrayList<>();

        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            orders.addAll(getMockOrdersByUser(1001));
            for (Order o : orders)
                o.setUserName("Vaishnavi Dhivakar");
            return orders;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Order o = extractOrder(rs);
                o.setUserName(rs.getString("user_name"));
                orders.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
        return orders;
    }

    public boolean updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE `order` SET status = ? WHERE order_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getOrderCount() {
        String sql = "SELECT COUNT(*) FROM `order`";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getTotalRevenue() {
        String sql = "SELECT SUM(total_amount) FROM `order` WHERE status != 'cancelled'";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next())
                return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Order extractOrder(ResultSet rs) throws SQLException {
        Order o = new Order();
        o.setOrderId(rs.getInt("order_id"));
        o.setUserId(rs.getInt("user_id"));
        o.setRestaurantId(rs.getInt("restaurant_id"));
        o.setRestaurantName(rs.getString("restaurant_name"));
        o.setTotalAmount(rs.getDouble("total_amount"));
        o.setDeliveryAddress(rs.getString("delivery_address"));
        o.setStatus(rs.getString("status"));
        o.setPaymentStatus(rs.getString("payment_status"));
        o.setPaymentMethod(rs.getString("payment_method"));
        o.setOrderDate(rs.getTimestamp("order_date"));
        return o;
    }
}
