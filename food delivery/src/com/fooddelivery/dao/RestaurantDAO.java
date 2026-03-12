package com.fooddelivery.dao;

import com.fooddelivery.model.Restaurant;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Restaurant Data Access Object
 */
public class RestaurantDAO {

    private static final List<Restaurant> customRestaurants = new ArrayList<>();

    public List<Restaurant> getAllRestaurants() {
        String sql = "SELECT * FROM restaurant WHERE is_active = TRUE ORDER BY rating DESC";
        List<Restaurant> restaurants = new ArrayList<>(customRestaurants);

        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            restaurants.addAll(getMockRestaurants());
            return restaurants;
        }

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
        r1.setAddress("45 Gourmet Avenue, Indiranagar, Bangalore");
        r1.setImageUrl("https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=800");
        mocks.add(r1);

        Restaurant r2 = new Restaurant();
        r2.setRestaurantId(2);
        r2.setName("Artisan Pizza Co.");
        r2.setCuisineType("Italian");
        r2.setRating(4.6);
        r2.setAddress("12 Woodfire Lane, Koramangala, Bangalore");
        r2.setImageUrl("https://images.unsplash.com/photo-1513104890138-7c749659a591?w=800");
        mocks.add(r2);

        Restaurant r3 = new Restaurant();
        r3.setRestaurantId(3);
        r3.setName("Spice Garden");
        r3.setCuisineType("Indian");
        r3.setRating(4.7);
        r3.setAddress("88 Curry Road, HSR Layout, Bangalore");
        r3.setImageUrl("https://images.unsplash.com/photo-1585937421612-70a008356fbe?w=800");
        mocks.add(r3);

        Restaurant r4 = new Restaurant();
        r4.setRestaurantId(4);
        r4.setName("Golden Dragon");
        r4.setCuisineType("Chinese");
        r4.setRating(4.5);
        r4.setAddress("22 Silk Street, Whitefield, Bangalore");
        r4.setImageUrl("https://images.unsplash.com/photo-1585032226651-759b368d7246?w=800");
        mocks.add(r4);

        Restaurant r5 = new Restaurant();
        r5.setRestaurantId(5);
        r5.setName("Urban Sushi");
        r5.setCuisineType("Japanese");
        r5.setRating(4.9);
        r5.setAddress("10 Sakura Lane, Jayanagar, Bangalore");
        r5.setImageUrl("https://images.unsplash.com/photo-1579871494447-9811cf80d66c?w=800");
        mocks.add(r5);

        Restaurant r6 = new Restaurant();
        r6.setRestaurantId(6);
        r6.setName("Taco Town");
        r6.setCuisineType("Mexican");
        r6.setRating(4.4);
        r6.setAddress("45 Fiesta Square, JP Nagar, Bangalore");
        r6.setImageUrl("https://images.unsplash.com/photo-1565299585323-38d6b0865b47?w=800");
        mocks.add(r6);

        Restaurant r7 = new Restaurant();
        r7.setRestaurantId(7);
        r7.setName("The French Bistro");
        r7.setCuisineType("French");
        r7.setRating(4.7);
        r7.setAddress("12 Eiffel Street, Lavelle Road, Bangalore");
        r7.setImageUrl("https://images.unsplash.com/photo-1559339352-11d035aa65de?w=800");
        mocks.add(r7);

        Restaurant r8 = new Restaurant();
        r8.setRestaurantId(8);
        r8.setName("Steakhouse Prime");
        r8.setCuisineType("American");
        r8.setRating(4.6);
        r8.setAddress("99 Grill Road, Malleshwaram, Bangalore");
        r8.setImageUrl("https://images.unsplash.com/photo-1544025162-d76694265947?w=800");
        mocks.add(r8);

        Restaurant r9 = new Restaurant();
        r9.setRestaurantId(9);
        r9.setName("Healthy Greens");
        r9.setCuisineType("Healthy");
        r9.setRating(4.5);
        r9.setAddress("5 Vegan Valley, Sadashivanagar, Bangalore");
        r9.setImageUrl("https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=800");
        mocks.add(r9);

        Restaurant r10 = new Restaurant();
        r10.setRestaurantId(10);
        r10.setName("Sangeetha Veg Restaurant");
        r10.setCuisineType("South Indian");
        r10.setRating(4.6);
        r10.setAddress("12 Gandhi Road, Jayanagar, Bangalore");
        r10.setPhone("9876501234");
        r10.setImageUrl("https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=800");
        mocks.add(r10);

        Restaurant r11 = new Restaurant();
        r11.setRestaurantId(11);
        r11.setName("Anjappar Chettinad Restaurant");
        r11.setCuisineType("Chettinad");
        r11.setRating(4.4);
        r11.setAddress("45 Residency Road, Bangalore");
        r11.setPhone("9840123456");
        r11.setImageUrl("https://images.unsplash.com/photo-1516714435131-44d6b64dc6a2?w=800");
        mocks.add(r11);

        Restaurant r12 = new Restaurant();
        r12.setRestaurantId(12);
        r12.setName("Nagarjuna Chimney");
        r12.setCuisineType("Andhra");
        r12.setRating(4.7);
        r12.setAddress("Residency Road, Bangalore");
        r12.setPhone("8023456789");
        r12.setImageUrl("https://images.unsplash.com/photo-1626777552726-4a6b54c97e46?w=800");
        mocks.add(r12);

        Restaurant r13 = new Restaurant();
        r13.setRestaurantId(13);
        r13.setName("Malabar Cafe");
        r13.setCuisineType("Kerala");
        r13.setRating(4.5);
        r13.setAddress("MG Road, Bangalore");
        r13.setPhone("9447012345");
        r13.setImageUrl("https://images.unsplash.com/photo-1567337710282-00832b415979?w=800");
        mocks.add(r13);

        Restaurant r14 = new Restaurant();
        r14.setRestaurantId(14);
        r14.setName("Vidyarthi Bhavan");
        r14.setCuisineType("Karnataka");
        r14.setRating(4.8);
        r14.setAddress("Gandhi Bazaar, Basavanagudi, Bangalore");
        r14.setPhone("8026677451");
        r14.setImageUrl("https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=800");
        mocks.add(r14);

        Restaurant r15 = new Restaurant();
        r15.setRestaurantId(15);
        r15.setName("Rayalaseema Ruchulu");
        r15.setCuisineType("Telugu");
        r15.setRating(4.3);
        r15.setAddress("Indiranagar, Bangalore");
        r15.setPhone("4023556789");
        r15.setImageUrl("https://images.unsplash.com/photo-1631452180519-c014fe946bc7?w=800");
        mocks.add(r15);

        Restaurant r16 = new Restaurant();
        r16.setRestaurantId(16);
        r16.setName("Karavalli Seafood");
        r16.setCuisineType("Mangalorean");
        r16.setRating(4.6);
        r16.setAddress("Brigade Road, Bangalore");
        r16.setPhone("8025585858");
        r16.setImageUrl("https://images.unsplash.com/photo-1765265432607-cdc1060294ba?w=800");
        mocks.add(r16);

        Restaurant r17 = new Restaurant();
        r17.setRestaurantId(17);
        r17.setName("Murugan Idli Shop");
        r17.setCuisineType("Tamil");
        r17.setRating(4.7);
        r17.setAddress("Commercial Street, Bangalore");
        r17.setPhone("4522341234");
        r17.setImageUrl("https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=800");
        mocks.add(r17);

        Restaurant r18 = new Restaurant();
        r18.setRestaurantId(18);
        r18.setName("Thalassery Kitchen");
        r18.setCuisineType("Moplah");
        r18.setRating(4.4);
        r18.setAddress("Majestic Station Road, Bangalore");
        r18.setPhone("4902322334");
        r18.setImageUrl("https://images.unsplash.com/photo-1631515243349-e0cb75fb8d3a?w=800");
        mocks.add(r18);

        Restaurant r19 = new Restaurant();
        r19.setRestaurantId(19);
        r19.setName("Paradise Biryani Point");
        r19.setCuisineType("Hyderabadi");
        r19.setRating(4.5);
        r19.setAddress("Electronic City, Bangalore");
        r19.setPhone("4023411122");
        r19.setImageUrl("https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=800");
        mocks.add(r19);

        Restaurant r20 = new Restaurant();
        r20.setRestaurantId(20);
        r20.setName("The Cake Studio");
        r20.setCuisineType("Cakes & Desserts");
        r20.setRating(4.7);
        r20.setAddress("Indiranagar, Bangalore");
        r20.setPhone("8041235566");
        r20.setImageUrl("https://images.unsplash.com/photo-1739132124985-6c9277e268b5?w=800");
        mocks.add(r20);

        Restaurant r21 = new Restaurant();
        r21.setRestaurantId(21);
        r21.setName("Sweet Temptations");
        r21.setCuisineType("Bakery");
        r21.setRating(4.6);
        r21.setAddress("Koramangala, Bangalore");
        r21.setPhone("4428223344");
        r21.setImageUrl("https://images.unsplash.com/photo-1648596799000-646ec880d4e7?w=800");
        mocks.add(r21);

        Restaurant r22 = new Restaurant();
        r22.setRestaurantId(22);
        r22.setName("The Tea House");
        r22.setCuisineType("Beverages & Tea");
        r22.setRating(4.8);
        r22.setAddress("Cunningham Road, Bangalore");
        r22.setPhone("4023112233");
        r22.setImageUrl("https://images.unsplash.com/photo-1765809330985-a59b80931d12?w=800");
        mocks.add(r22);

        Restaurant r23 = new Restaurant();
        r23.setRestaurantId(23);
        r23.setName("Namma Karnataka");
        r23.setCuisineType("Karnataka, Bath");
        r23.setRating(4.5);
        r23.setAddress("Jayanagar, Bangalore");
        r23.setPhone("8026633445");
        r23.setImageUrl("https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=800"); // Standard South Indian
                                                                                               // meal image to
                                                                                               // represent variety
        mocks.add(r23);

        return mocks;
    }

    public Restaurant getRestaurantById(int id) {
        // Check memory first
        for (Restaurant r : customRestaurants) {
            if (r.getRestaurantId() == id) {
                return r;
            }
        }

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

        // Add from memory
        for (Restaurant r : customRestaurants) {
            if (r.getCuisineType().equalsIgnoreCase(cuisineType)) {
                restaurants.add(r);
            }
        }

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
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllRestaurants();
        }

        List<Restaurant> restaurants = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase().trim();

        // Search memory
        for (Restaurant r : customRestaurants) {
            if (r.getName().toLowerCase().contains(lowerKeyword) ||
                    r.getCuisineType().toLowerCase().contains(lowerKeyword)) {
                restaurants.add(r);
            }
        }

        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            for (Restaurant r : getMockRestaurants()) {
                if (r.getName().toLowerCase().contains(lowerKeyword) ||
                        r.getCuisineType().toLowerCase().contains(lowerKeyword)) {
                    restaurants.add(r);
                }
            }
            return restaurants;
        }

        String sql = "SELECT * FROM restaurant WHERE (name LIKE ? OR cuisine_type LIKE ?) AND is_active = TRUE";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword.trim() + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);
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

    public boolean addRestaurant(Restaurant r) {
        // Set temp ID for demo purposes
        r.setRestaurantId(100 + customRestaurants.size());
        customRestaurants.add(r);

        String sql = "INSERT INTO restaurant (name, description, address, phone, email, image_url, cuisine_type, rating) VALUES (?,?,?,?,?,?,?,?)";
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.out.println("DEMO MODE: Added restaurant to memory: " + r.getName());
            return true;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        } finally {
            DBConnection.closeConnection(conn);
        }
        return false;
    }

    public boolean updateRestaurant(Restaurant r) {
        // Update memory
        for (int i = 0; i < customRestaurants.size(); i++) {
            if (customRestaurants.get(i).getRestaurantId() == r.getRestaurantId()) {
                customRestaurants.set(i, r);
                break;
            }
        }

        String sql = "UPDATE restaurant SET name=?, description=?, address=?, phone=?, cuisine_type=?, is_active=? WHERE restaurant_id=?";
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return true;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        } finally {
            DBConnection.closeConnection(conn);
        }
        return false;
    }

    public boolean deleteRestaurant(int id) {
        // Remove from memory
        customRestaurants.removeIf(r -> r.getRestaurantId() == id);

        String sql = "DELETE FROM restaurant WHERE restaurant_id = ?";
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return true;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn);
        }
        return false;
    }

    public List<String> getAllCuisineTypes() {
        String sql = "SELECT DISTINCT cuisine_type FROM restaurant WHERE is_active = TRUE";
        List<String> cuisines = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            for (Restaurant r : getMockRestaurants()) {
                if (!cuisines.contains(r.getCuisineType())) {
                    cuisines.add(r.getCuisineType());
                }
            }
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
            stats.add(new com.fooddelivery.model.Cuisine("South Indian", 18, 85,
                    "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=100&h=100&fit=crop"));
            stats.add(new com.fooddelivery.model.Cuisine("Mexican", 8, 30,
                    "https://images.unsplash.com/photo-1565299585323-38d6b0865b47?w=100&h=100&fit=crop"));
            stats.add(new com.fooddelivery.model.Cuisine("Andhra", 6, 28,
                    "https://images.unsplash.com/photo-1626777552726-4a6b54c97e46?w=100&h=100&fit=crop"));
            stats.add(new com.fooddelivery.model.Cuisine("Japanese", 5, 25,
                    "https://images.unsplash.com/photo-1579871494447-9811cf80d66c?w=100&h=100&fit=crop"));
            stats.add(new com.fooddelivery.model.Cuisine("Healthy", 6, 22,
                    "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=100&h=100&fit=crop"));
            stats.add(new com.fooddelivery.model.Cuisine("Beverages", 12, 40,
                    "https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?w=100&h=100&fit=crop"));
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
