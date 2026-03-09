package com.fooddelivery.dao;

import com.fooddelivery.model.Restaurant;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Restaurant Data Access Object
 */
public class RestaurantDAO {

    public List<Restaurant> getAllRestaurants() {
        String sql = "SELECT * FROM restaurant WHERE is_active = TRUE ORDER BY rating DESC";
        List<Restaurant> restaurants = new ArrayList<>();

        Connection conn = DBConnection.getConnection();
        if (conn == null)
            return getMockRestaurants();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                restaurants.add(extractRestaurant(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
        return restaurants;
    }

    private List<Restaurant> getMockRestaurants() {
        List<Restaurant> mocks = new ArrayList<>();
        Restaurant r1 = new Restaurant();
        r1.setRestaurantId(1);
        r1.setName("The Burger Joint");
        r1.setCuisineType("American");
        r1.setRating(4.8);
        r1.setAddress("45 Gourmet Avenue, Downtown");
        r1.setImageUrl("https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=800");
        mocks.add(r1);

        Restaurant r2 = new Restaurant();
        r2.setRestaurantId(2);
        r2.setName("Artisan Pizza Co.");
        r2.setCuisineType("Italian");
        r2.setRating(4.6);
        r2.setAddress("12 Woodfire Lane, Westside");
        r2.setImageUrl("https://images.unsplash.com/photo-1513104890138-7c749659a591?w=800");
        mocks.add(r2);

        Restaurant r3 = new Restaurant();
        r3.setRestaurantId(3);
        r3.setName("Spice Garden");
        r3.setCuisineType("Indian");
        r3.setRating(4.7);
        r3.setAddress("88 Curry Road, East End");
        r3.setImageUrl("https://images.unsplash.com/photo-1585937421612-70a008356fbe?w=800");
        mocks.add(r3);

        Restaurant r4 = new Restaurant();
        r4.setRestaurantId(4);
        r4.setName("Golden Dragon");
        r4.setCuisineType("Chinese");
        r4.setRating(4.5);
        r4.setAddress("22 Silk Street, Chinatown");
        r4.setImageUrl("https://images.unsplash.com/photo-1585032226651-759b368d7246?w=800");
        mocks.add(r4);

        Restaurant r5 = new Restaurant();
        r5.setRestaurantId(5);
        r5.setName("Urban Sushi");
        r5.setCuisineType("Japanese");
        r5.setRating(4.9);
        r5.setAddress("10 Sakura Lane, Eastside");
        r5.setImageUrl("https://images.unsplash.com/photo-1579871494447-9811cf80d66c?w=800");
        mocks.add(r5);

        Restaurant r6 = new Restaurant();
        r6.setRestaurantId(6);
        r6.setName("Taco Town");
        r6.setCuisineType("Mexican");
        r6.setRating(4.4);
        r6.setAddress("45 Fiesta Square, Southside");
        r6.setImageUrl("https://images.unsplash.com/photo-1565299585323-38d6b0865b47?w=800");
        mocks.add(r6);

        Restaurant r7 = new Restaurant();
        r7.setRestaurantId(7);
        r7.setName("The French Bistro");
        r7.setCuisineType("French");
        r7.setRating(4.7);
        r7.setAddress("12 Eiffel Street, Midtown");
        r7.setImageUrl("https://images.unsplash.com/photo-1559339352-11d035aa65de?w=800");
        mocks.add(r7);

        Restaurant r8 = new Restaurant();
        r8.setRestaurantId(8);
        r8.setName("Steakhouse Prime");
        r8.setCuisineType("American");
        r8.setRating(4.6);
        r8.setAddress("99 Grill Road, West End");
        r8.setImageUrl("https://images.unsplash.com/photo-1544025162-d76694265947?w=800");
        mocks.add(r8);

        Restaurant r9 = new Restaurant();
        r9.setRestaurantId(9);
        r9.setName("Healthy Greens");
        r9.setCuisineType("Healthy");
        r9.setRating(4.5);
        r9.setAddress("5 Vegan Valley, Northside");
        r9.setImageUrl("https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=800");
        mocks.add(r9);

        return mocks;
    }

    public Restaurant getRestaurantById(int id) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            for (Restaurant r : getMockRestaurants()) {
                if (r.getRestaurantId() == id) {
                    return r;
                }
            }
            return null;
        }

        String sql = "SELECT * FROM restaurant WHERE restaurant_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractRestaurant(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
        return null;
    }

    public List<Restaurant> getRestaurantsByCuisine(String cuisineType) {
        String sql = "SELECT * FROM restaurant WHERE cuisine_type = ? AND is_active = TRUE";
        List<Restaurant> restaurants = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            for (Restaurant r : getMockRestaurants()) {
                if (r.getCuisineType().equalsIgnoreCase(cuisineType)) {
                    restaurants.add(r);
                }
            }
            return restaurants;
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cuisineType);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                restaurants.add(extractRestaurant(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
        return restaurants;
    }

    public List<Restaurant> searchRestaurants(String keyword) {
        String sql = "SELECT * FROM restaurant WHERE (name LIKE ? OR cuisine_type LIKE ?) AND is_active = TRUE";
        List<Restaurant> restaurants = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                restaurants.add(extractRestaurant(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurants;
    }

    public boolean addRestaurant(Restaurant r) {
        String sql = "INSERT INTO restaurant (name, description, address, phone, email, image_url, cuisine_type, rating) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, r.getName());
            stmt.setString(2, r.getDescription());
            stmt.setString(3, r.getAddress());
            stmt.setString(4, r.getPhone());
            stmt.setString(5, r.getEmail());
            stmt.setString(6, r.getImageUrl());
            stmt.setString(7, r.getCuisineType());
            stmt.setDouble(8, r.getRating());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateRestaurant(Restaurant r) {
        String sql = "UPDATE restaurant SET name=?, description=?, address=?, phone=?, cuisine_type=?, is_active=? WHERE restaurant_id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, r.getName());
            stmt.setString(2, r.getDescription());
            stmt.setString(3, r.getAddress());
            stmt.setString(4, r.getPhone());
            stmt.setString(5, r.getCuisineType());
            stmt.setBoolean(6, r.isActive());
            stmt.setInt(7, r.getRestaurantId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteRestaurant(int id) {
        String sql = "DELETE FROM restaurant WHERE restaurant_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getAllCuisineTypes() {
        String sql = "SELECT DISTINCT cuisine_type FROM restaurant WHERE is_active = TRUE";
        List<String> cuisines = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            cuisines.add("American");
            cuisines.add("Italian");
            cuisines.add("Indian");
            cuisines.add("Chinese");
            cuisines.add("Mexican");
            return cuisines;
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                cuisines.add(rs.getString("cuisine_type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
        return cuisines;
    }

    public List<com.fooddelivery.model.Cuisine> getCuisineStats() {
        String sql = "SELECT r.cuisine_type, COUNT(DISTINCT r.restaurant_id) as res_count, "
                + "COUNT(f.food_id) as dish_count FROM restaurant r "
                + "LEFT JOIN food_item f ON r.restaurant_id = f.restaurant_id "
                + "WHERE r.is_active = TRUE GROUP BY r.cuisine_type";
        List<com.fooddelivery.model.Cuisine> stats = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            // Return dummy data for mock mode
            stats.add(new com.fooddelivery.model.Cuisine("American", 12, 45,
                    "https://images.unsplash.com/photo-1550547660-d9450f859349?w=100&h=100&fit=crop"));
            stats.add(new com.fooddelivery.model.Cuisine("Italian", 8, 38,
                    "https://images.unsplash.com/photo-1513104890138-7c749659a591?w=100&h=100&fit=crop"));
            stats.add(new com.fooddelivery.model.Cuisine("Indian", 15, 62,
                    "https://images.unsplash.com/photo-1585937421612-70a008356fbe?w=100&h=100&fit=crop"));
            stats.add(new com.fooddelivery.model.Cuisine("Chinese", 10, 50,
                    "https://images.unsplash.com/photo-1585032226651-759b368d7246?w=100&h=100&fit=crop"));
            stats.add(new com.fooddelivery.model.Cuisine("Japanese", 5, 25,
                    "https://images.unsplash.com/photo-1579871494447-9811cf80d66c?w=100&h=100&fit=crop"));
            stats.add(new com.fooddelivery.model.Cuisine("Mexican", 8, 30,
                    "https://images.unsplash.com/photo-1565299585323-38d6b0865b47?w=100&h=100&fit=crop"));
            stats.add(new com.fooddelivery.model.Cuisine("French", 4, 18,
                    "https://images.unsplash.com/photo-1559339352-11d035aa65de?w=100&h=100&fit=crop"));
            stats.add(new com.fooddelivery.model.Cuisine("Healthy", 6, 22,
                    "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=100&h=100&fit=crop"));
            return stats;
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String name = rs.getString("cuisine_type");
                int resCount = rs.getInt("res_count");
                int dishCount = rs.getInt("dish_count");
                stats.add(new com.fooddelivery.model.Cuisine(name, resCount, dishCount, null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
        return stats;
    }

    private Restaurant extractRestaurant(ResultSet rs) throws SQLException {
        Restaurant r = new Restaurant();
        r.setRestaurantId(rs.getInt("restaurant_id"));
        r.setName(rs.getString("name"));
        r.setDescription(rs.getString("description"));
        r.setAddress(rs.getString("address"));
        r.setPhone(rs.getString("phone"));
        r.setEmail(rs.getString("email"));
        r.setImageUrl(rs.getString("image_url"));
        r.setCuisineType(rs.getString("cuisine_type"));
        r.setRating(rs.getDouble("rating"));
        r.setActive(rs.getBoolean("is_active"));
        r.setCreatedAt(rs.getTimestamp("created_at"));
        return r;
    }
}
