package com.fooddelivery.dao;

import com.fooddelivery.model.FoodItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FoodItem Data Access Object
 */
public class FoodItemDAO {

        public List<FoodItem> getFoodByRestaurant(int restaurantId) {
                String sql = "SELECT f.*, r.name as restaurant_name FROM food_item f JOIN restaurant r ON f.restaurant_id = r.restaurant_id WHERE f.restaurant_id = ? AND f.is_available = TRUE ORDER BY f.category, f.name";
                List<FoodItem> items = new ArrayList<>();
                Connection conn = DBConnection.getConnection();
                if (conn == null)
                        return getMockFoodItems(restaurantId);
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, restaurantId);
                        ResultSet rs = stmt.executeQuery();
                        while (rs.next()) {
                                items.add(extractFoodItem(rs));
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                } finally {
                        DBConnection.closeConnection(conn);
                }
                return items;
        }

        private List<FoodItem> getMockFoodItems(int restaurantId) {
                List<FoodItem> mocks = new ArrayList<>();

                switch (restaurantId) {
                        case 1: // The Burger Joint
                                // Recommended
                                addMock(mocks, restaurantId, 1001, "Classic Veg Burger", 79, "Recommended", true,
                                                "Golden crisp vegetable patty with fresh lettuce, crunchy onion and creamy mayo.",
                                                4.2, 150,
                                                "https://images.unsplash.com/photo-1550547660-d9450f859349?w=400");
                                addMock(mocks, restaurantId, 1002, "Paneer Tikka Burger", 129, "Recommended", true,
                                                "Juicy grilled paneer tikka patty with mint chutney and onion rings.",
                                                4.6, 320,
                                                "https://images.unsplash.com/photo-1525059696034-4967a8e1dca2?w=400");
                                addMock(mocks, restaurantId, 1003, "Spicy Chicken Burger", 149, "Recommended", false,
                                                "Tender chicken breast in a spicy marinade, breaded and fried to crisp perfection.",
                                                4.4, 210,
                                                "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400");
                                addMock(mocks, restaurantId, 1004, "Cheese Loaded Fries", 149, "Recommended", true,
                                                "Crispy fries loaded with cheese sauce and jalapenos.",
                                                4.5, 430,
                                                "https://images.unsplash.com/photo-1585109649139-366815a0d713?w=400");
                                addMock(mocks, restaurantId, 1005, "Chocolate Milkshake", 129, "Recommended", true,
                                                "Rich dark chocolate blended with vanilla ice cream.",
                                                4.3, 210,
                                                "https://images.unsplash.com/photo-1572490122747-3968b75cc699?w=400");

                                // Veg Burgers
                                addMock(mocks, restaurantId, 101, "Classic Veg Burger", 79, "Veg Burgers", true,
                                                "Serves 1 | Golden crisp vegetable patty with fresh lettuce, crunchy onion and creamy mayo. (210 Kcal)",
                                                4.2, 150,
                                                "https://images.unsplash.com/photo-1550547660-d9450f859349?w=400");
                                addMock(mocks, restaurantId, 102, "Cheese Veg Burger", 99, "Veg Burgers", true,
                                                "Serves 1 | Classic veg burger topped with a melty cheese slice for extra richness. (250 Kcal)",
                                                4.5, 230,
                                                "https://images.unsplash.com/photo-1525059696034-4967a8e1dca2?w=400");
                                addMock(mocks, restaurantId, 103, "Corn & Cheese Burger", 109, "Veg Burgers", true,
                                                "Serves 1 | Deliciously golden corn and cheese patty with a hint of spicy herbs. (280 Kcal)",
                                                4.3, 110,
                                                "https://images.unsplash.com/photo-1594212699903-ec8a3eca50f5?w=400");
                                addMock(mocks, restaurantId, 104, "Paneer Tikka Burger", 129, "Veg Burgers", true,
                                                "Serves 1 | Juicy grilled paneer tikka patty with mint chutney and onion rings. (310 Kcal)",
                                                4.6, 320,
                                                "https://images.unsplash.com/photo-1460306855393-0410f61241c7?w=400");
                                addMock(mocks, restaurantId, 105, "Crispy Paneer Burger", 139, "Veg Burgers", true,
                                                "Serves 1 | Chunky paneer cube breaded and fried to perfection with tandoori sauce. (340 Kcal)",
                                                4.4, 410,
                                                "https://images.unsplash.com/photo-1547584370-2cc98b8b8dc8?w=400");
                                addMock(mocks, restaurantId, 106, "Veggie Delight Burger", 119, "Veg Burgers", true,
                                                "Serves 1 | Packed with assorted veggies, herbs and a special blend of spices. (230 Kcal)",
                                                4.1, 85,
                                                "https://images.unsplash.com/photo-1525059696034-4967a8e1dca2?w=400");
                                addMock(mocks, restaurantId, 107, "Spicy Mexican Veg Burger", 149, "Veg Burgers", true,
                                                "Serves 1 | Zesty burger with jalapeños, salsa and a spicy bean patty. (290 Kcal)",
                                                4.3, 195,
                                                "https://images.unsplash.com/photo-1594212699903-ec8a3eca50f5?w=400");
                                addMock(mocks, restaurantId, 108, "Double Patty Veg Burger", 169, "Veg Burgers", true,
                                                "Serves 1 | Two veggie patties stacked with double cheese and extra veggies. (420 Kcal)",
                                                4.5, 310,
                                                "https://images.unsplash.com/photo-1551782450-a2132b4ba21d?w=400");
                                addMock(mocks, restaurantId, 109, "Maharaja Veg Burger", 199, "Veg Burgers", true,
                                                "Serves 1 | Three buns, double patties, jalapeños and premium sauce. (480 Kcal)",
                                                4.7, 540,
                                                "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400");

                                // 🍔 Burger Menu (Non-Veg)
                                addMock(mocks, restaurantId, 110, "Classic Chicken Burger", 99, "Non-Veg Burgers",
                                                false,
                                                "Serves 1 | Tender and juicy chicken patty with mayo and fresh onions. (260 Kcal)",
                                                4.3, 210,
                                                "https://images.unsplash.com/photo-1561758033-d89a9ad46330?w=400");
                                addMock(mocks, restaurantId, 111, "Crispy Chicken Burger", 129, "Non-Veg Burgers",
                                                false,
                                                "Serves 1 | Extra crunchy breaded chicken breast strip with spicy pepper mayo. (310 Kcal)",
                                                4.5, 340,
                                                "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400");
                                addMock(mocks, restaurantId, 112, "Spicy Chicken Burger", 149, "Non-Veg Burgers", false,
                                                "Serves 1 | Fiery chicken patty seasoned with secret spices and red chili flakes. (290 Kcal)",
                                                4.4, 280,
                                                "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400");
                                addMock(mocks, restaurantId, 113, "BBQ Chicken Burger", 159, "Non-Veg Burgers", false,
                                                "Serves 1 | Grilled chicken patty glazed with smoky BBQ sauce and onions. (330 Kcal)",
                                                4.6, 420,
                                                "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400");
                                addMock(mocks, restaurantId, 114, "Chicken Cheese Burger", 169, "Non-Veg Burgers",
                                                false,
                                                "Serves 1 | Classic chicken patty with a generous slice of melted cheddar. (300 Kcal)",
                                                4.5, 360,
                                                "https://images.unsplash.com/photo-1553979459-d2229ba7433b?w=400");
                                addMock(mocks, restaurantId, 115, "Chicken Tandoori Burger", 179, "Non-Veg Burgers",
                                                false,
                                                "Serves 1 | Authentic tandoori grilled chicken with spicy mint mayo. (320 Kcal)",
                                                4.7, 510,
                                                "https://images.unsplash.com/photo-1596662951482-0c4ba74a6df6?w=400");
                                addMock(mocks, restaurantId, 116, "Double Chicken Patty Burger", 199, "Non-Veg Burgers",
                                                false,
                                                "Serves 1 | 2 patties with double cheese. Double the chicken, double the fun! (510 Kcal)",
                                                4.8, 620,
                                                "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400");
                                addMock(mocks, restaurantId, 117, "Chicken Supreme Burger", 219, "Non-Veg Burgers",
                                                false,
                                                "Serves 1 | Large premium chicken patty with exotic veggies and house sauce. (450 Kcal)",
                                                4.7, 480,
                                                "https://images.unsplash.com/photo-1525059696034-4967a8e1dca2?w=400");
                                addMock(mocks, restaurantId, 118, "Fish Fillet Burger", 199, "Non-Veg Burgers", false,
                                                "Serves 1 | Flaky white fish fillet breaded and fried, served with tartar sauce. (280 Kcal)",
                                                4.2, 140,
                                                "https://images.unsplash.com/photo-1519708227418-c8fd9a32b7a2?w=400");

                                // American Fast Food
                                addMock(mocks, restaurantId, 119, "French Fries (Regular)", 79, "Fast Food", true,
                                                "Serves 1 | Perfectly golden and salted classic potato fries. (312 Kcal)",
                                                4.1, 120,
                                                "https://images.unsplash.com/photo-1585109649139-366815a0d713?w=400");
                                addMock(mocks, restaurantId, 120, "French Fries (Large)", 119, "Fast Food", true,
                                                "Serves 1 | Larger portion of our classic golden salted fries. (465 Kcal)",
                                                4.2, 180,
                                                "https://images.unsplash.com/photo-1585109649139-366815a0d713?w=400");
                                addMock(mocks, restaurantId, 121, "Cheese Loaded Fries", 149, "Fast Food", true,
                                                "Serves 1 | Fries topped with a generous amount of warm liquid cheese sauce. (520 Kcal)",
                                                4.5, 340,
                                                "https://images.unsplash.com/photo-1585109649139-366815a0d713?w=400");
                                addMock(mocks, restaurantId, 122, "Chicken Nuggets (6 pcs)", 149, "Fast Food", false,
                                                "Serves 1 | Juicy breaded chicken nuggets served with honey mustard dip. (280 Kcal)",
                                                4.3, 210,
                                                "https://images.unsplash.com/photo-1562967914-608f82629710?w=400");
                                addMock(mocks, restaurantId, 123, "Chicken Nuggets (9 pcs)", 189, "Fast Food", false,
                                                "Serves 1 | Larger portion of our popular chicken nuggets. (420 Kcal)",
                                                4.4, 310,
                                                "https://images.unsplash.com/photo-1562967914-608f82629710?w=400");
                                addMock(mocks, restaurantId, 124, "Onion Rings", 129, "Fast Food", true,
                                                "Serves 1 | Crispy batter-fried onion rings served with ranch dip. (320 Kcal)",
                                                4.0, 95,
                                                "https://images.unsplash.com/photo-1639024471283-03518883512d?w=400");
                                addMock(mocks, restaurantId, 125, "Chicken Wings (6 pcs)", 199, "Fast Food", false,
                                                "Serves 1 | Tender chicken wings tossed in your choice of sauce. (450 Kcal)",
                                                4.6, 420,
                                                "https://images.unsplash.com/photo-1567620832903-9fc6debc209f?w=400");
                                addMock(mocks, restaurantId, 126, "Chicken Popcorn", 149, "Fast Food", false,
                                                "Serves 1 | Bited-sized pieces of crunchy seasoned chicken. (380 Kcal)",
                                                4.4, 230,
                                                "https://images.unsplash.com/photo-1562967914-608f82629710?w=400");
                                addMock(mocks, restaurantId, 127, "Hot Dog", 129, "Fast Food", false,
                                                "Serves 1 | Classic grilled sausage in a toasted bun with mustard and ketchup. (290 Kcal)",
                                                4.2, 85,
                                                "https://images.unsplash.com/photo-1541214113241-21578d2d9b62?w=400");
                                addMock(mocks, restaurantId, 128, "Cheese Hot Dog", 149, "Fast Food", false,
                                                "Serves 1 | Hot dog topped with melted cheese sauce and jalapeños. (340 Kcal)",
                                                4.3, 110,
                                                "https://images.unsplash.com/photo-1541214113241-21578d2d9b62?w=400");
                                addMock(mocks, restaurantId, 129, "Grilled Sandwich", 119, "Fast Food", true,
                                                "Serves 1 | Vegetable and cheese sandwich grilled to buttery perfection. (240 Kcal)",
                                                4.1, 75,
                                                "https://images.unsplash.com/photo-1528735602780-2552fd46c7af?w=400");
                                addMock(mocks, restaurantId, 130, "Club Sandwich", 179, "Fast Food", false,
                                                "Serves 1 | Triple decker sandwich with chicken, egg, ham and veggies. (410 Kcal)",
                                                4.4, 190,
                                                "https://images.unsplash.com/photo-1528735602780-2552fd46c7af?w=400");

                                // 🥤 Milkshake Menu
                                addMock(mocks, restaurantId, 131, "Vanilla Milkshake", 119, "Milkshakes", true,
                                                "Serves 1 | Thick and creamy classic vanilla bean milkshake. (320 Kcal)",
                                                4.1, 140,
                                                "https://images.unsplash.com/photo-1755835070338-6049da75951e?w=400");
                                addMock(mocks, restaurantId, 132, "Chocolate Milkshake", 129, "Milkshakes", true,
                                                "Serves 1 | Rich dark chocolate blended with vanilla ice cream. (380 Kcal)",
                                                4.3, 210,
                                                "https://images.unsplash.com/photo-1572490122747-3968b75cc699?w=400");
                                addMock(mocks, restaurantId, 133, "Strawberry Milkshake", 129, "Milkshakes", true,
                                                "Serves 1 | Fresh strawberry syrup blended into a thick milkshake. (340 Kcal)",
                                                4.2, 160,
                                                "https://images.unsplash.com/photo-1648178628415-b410fc1d58bc?w=400");
                                addMock(mocks, restaurantId, 134, "Butterscotch Milkshake", 139, "Milkshakes", true,
                                                "Serves 1 | Crunchy butterscotch bits in a golden syrup base. (390 Kcal)",
                                                4.4, 190,
                                                "https://plus.unsplash.com/premium_photo-1748060692373-586d1492b508?w=400");
                                addMock(mocks, restaurantId, 135, "Oreo Milkshake", 159, "Milkshakes", true,
                                                "Serves 1 | Real Oreo cookies blended for a cookies-and-cream treat. (450 Kcal)",
                                                4.6, 420,
                                                "https://images.unsplash.com/photo-1619158401201-8fa932695178?w=400");
                                addMock(mocks, restaurantId, 136, "KitKat Milkshake", 159, "Milkshakes", true,
                                                "Serves 1 | Chunky KitKat pieces blended into a thick shake. (460 Kcal)",
                                                4.7, 380,
                                                "https://images.unsplash.com/photo-1740103639723-87c39575b2c1?w=400");
                                addMock(mocks, restaurantId, 137, "Brownie Chocolate Shake", 179, "Milkshakes", true,
                                                "Serves 1 | Decadent chocolate shake with chunks of warm brownie. (520 Kcal)",
                                                4.8, 510,
                                                "https://images.unsplash.com/photo-1619158403521-ed9795026d47?w=400");
                                addMock(mocks, restaurantId, 138, "Peanut Butter Shake", 169, "Milkshakes", true,
                                                "Serves 1 | Creamy peanut butter blended for a rich nutty flavor. (550 Kcal)",
                                                4.5, 230,
                                                "https://images.unsplash.com/photo-1632401798178-8cb5935d2812?w=400");
                                addMock(mocks, restaurantId, 139, "Mango Milkshake", 149, "Milkshakes", true,
                                                "Serves 1 | Seasonal fresh mango pulp blended with vanilla ice cream. (340 Kcal)",
                                                4.3, 110,
                                                "https://images.unsplash.com/photo-1654417958787-5d9948c3c04b?w=400");
                                addMock(mocks, restaurantId, 140, "Caramel Milkshake", 149, "Milkshakes", true,
                                                "Serves 1 | Sweet and salty caramel drizzle in a creamy shake. (410 Kcal)",
                                                4.4, 150,
                                                "https://images.unsplash.com/photo-1507750549272-e58742b1df80?w=400");

                                // 🥤 Beverages
                                addMock(mocks, restaurantId, 141, "Coca Cola", 49, "Beverages", true,
                                                "330ml Can | Classic refreshed cola drink.", 4.5, 980,
                                                "https://images.unsplash.com/photo-1554866585-cd94860890b7?w=400");
                                addMock(mocks, restaurantId, 142, "Pepsi", 49, "Beverages", true,
                                                "330ml Can | Refreshing cola beverage.", 4.4, 850,
                                                "https://images.unsplash.com/photo-1581006852262-e4307cf6283a?w=400");
                                addMock(mocks, restaurantId, 143, "Sprite", 49, "Beverages", true,
                                                "330ml Can | Crisp lemon-lime soda.", 4.5, 720,
                                                "https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?w=400");
                                addMock(mocks, restaurantId, 144, "Iced Lemon Tea", 69, "Beverages", true,
                                                "Refreshing lemon flavored chilled tea. (120 Kcal)", 4.2, 140,
                                                "https://images.unsplash.com/photo-1556679343-c7306c1976bc?w=400");
                                addMock(mocks, restaurantId, 145, "Cold Coffee", 89, "Beverages", true,
                                                "Classic chilled coffee with a dash of chocolate. (180 Kcal)", 4.4, 310,
                                                "https://images.unsplash.com/photo-1517701604599-bb29b565090c?w=400");
                                addMock(mocks, restaurantId, 146, "Lemon Soda", 59, "Beverages", true,
                                                "Thirst-quenching fresh lemon soda with mint. (90 Kcal)", 4.1, 95,
                                                "https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?w=400");

                                // 🎁 Combo Meals
                                addMock(mocks, restaurantId, 147, "Veg Burger + Fries + Drink", 179, "Combo Meals",
                                                true,
                                                "Meal combo with classic veg burger, fries and a drink. (650 Kcal)",
                                                4.6, 520,
                                                "https://images.unsplash.com/photo-1674667447541-ea359a9d6a65?w=400");
                                addMock(mocks, restaurantId, 148, "Chicken Burger + Fries + Drink", 219, "Combo Meals",
                                                false,
                                                "Meal combo with classic chicken burger, fries and a drink. (720 Kcal)",
                                                4.7, 610,
                                                "https://images.unsplash.com/photo-1674667447541-ea359a9d6a65?w=400");
                                addMock(mocks, restaurantId, 149, "Paneer Burger + Fries + Drink", 249, "Combo Meals",
                                                true,
                                                "Meal combo with paneer tikka burger, fries and a drink. (780 Kcal)",
                                                4.8, 430,
                                                "https://images.unsplash.com/photo-1674667447541-ea359a9d6a65?w=400");
                                addMock(mocks, restaurantId, 150, "Double Chicken Burger + Fries + Drink", 299,
                                                "Combo Meals",
                                                false,
                                                "Mega meal with double chicken burger, large fries and a drink. (950 Kcal)",
                                                4.9, 750,
                                                "https://images.unsplash.com/photo-1674667447541-ea359a9d6a65?w=400");
                                break;
                        case 2: // Artisan Pizza Co.
                                // Recommended
                                addMock(mocks, restaurantId, 201, "Margherita Pizza", 249, "Recommended", true,
                                                "Classic Italian pizza with fresh basil and mozzarella. (280 Kcal)",
                                                4.4, 210,
                                                "https://images.unsplash.com/photo-1564936281291-294551497d81?w=400");
                                addMock(mocks, restaurantId, 202, "Pepperoni Pizza", 299, "Recommended", false,
                                                "Loaded with spicy pepperoni and double cheese. (350 Kcal)", 4.6, 340,
                                                "https://images.unsplash.com/photo-1564128442383-9201fcc740eb?w=400");
                                addMock(mocks, restaurantId, 203, "Lasagna", 299, "Recommended", false,
                                                "Layered pasta with meat, cheese and tomato sauce. (580 Kcal)", 4.8,
                                                420,
                                                "https://images.unsplash.com/photo-1709429790175-b02bb1b19207?w=400");

                                // Starters
                                addMock(mocks, restaurantId, 205, "Bruschetta", 149, "Starters", true,
                                                "Grilled bread topped with tomatoes, garlic and olive oil. (120 Kcal)",
                                                4.3, 110,
                                                "https://images.unsplash.com/photo-1572695157366-5e585ab2b69f?w=400");
                                addMock(mocks, restaurantId, 206, "Garlic Bread with Cheese", 169, "Starters", true,
                                                "Toasted bread with garlic butter and melted mozzarella. (220 Kcal)",
                                                4.5, 230,
                                                "https://images.unsplash.com/photo-1761344788266-5f6957aeea33?w=400");
                                addMock(mocks, restaurantId, 207, "Caprese Salad", 189, "Starters", true,
                                                "Fresh mozzarella, tomatoes, basil with olive oil. (180 Kcal)", 4.4, 95,
                                                "https://images.unsplash.com/photo-1592417817098-8fd3d9eb14a5?w=400");
                                addMock(mocks, restaurantId, 208, "Fried Mozzarella Sticks", 199, "Starters", true,
                                                "Crispy mozzarella sticks served with marinara sauce. (310 Kcal)", 4.2,
                                                180,
                                                "https://images.unsplash.com/photo-1734774924912-dcbb467f8599?w=400");
                                addMock(mocks, restaurantId, 209, "Italian Antipasto Platter", 299, "Starters", false,
                                                "Mix of olives, cheese, cured meat and vegetables. (450 Kcal)", 4.7,
                                                310,
                                                "https://images.unsplash.com/photo-1614706385060-c0432b5a0299?w=400");

                                // Pizza
                                addMock(mocks, restaurantId, 210, "Margherita Pizza", 249, "Pizza", true,
                                                "Tomato sauce, mozzarella, basil. (280 Kcal)", 4.4, 210,
                                                "https://images.unsplash.com/photo-1564936281291-294551497d81?w=400");
                                addMock(mocks, restaurantId, 211, "Pepperoni Pizza", 299, "Pizza", false,
                                                "Pepperoni slices with mozzarella cheese. (350 Kcal)", 4.6, 340,
                                                "https://images.unsplash.com/photo-1564128442383-9201fcc740eb?w=400");
                                addMock(mocks, restaurantId, 212, "Farmhouse Pizza", 279, "Pizza", true,
                                                "Onion, capsicum, mushrooms, olives. (310 Kcal)", 4.5, 230,
                                                "https://images.unsplash.com/photo-1717883235373-ef10b2a745a3?w=400");
                                addMock(mocks, restaurantId, 213, "BBQ Chicken Pizza", 319, "Pizza", false,
                                                "Chicken with BBQ sauce and cheese. (380 Kcal)", 4.7, 410,
                                                "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=400");
                                addMock(mocks, restaurantId, 214, "Four Cheese Pizza", 329, "Pizza", true,
                                                "Mozzarella, parmesan, cheddar, gorgonzola. (390 Kcal)", 4.8, 520,
                                                "https://images.unsplash.com/photo-1732223229355-95a1433404bf?w=400");
                                addMock(mocks, restaurantId, 215, "Veggie Supreme Pizza", 289, "Pizza", true,
                                                "Loaded with vegetables and herbs. (290 Kcal)", 4.6, 310,
                                                "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=400");

                                // Pasta
                                addMock(mocks, restaurantId, 218, "Spaghetti Aglio e Olio", 229, "Pasta", true,
                                                "Garlic, olive oil, chili flakes. (240 Kcal)", 4.3, 150,
                                                "https://images.unsplash.com/photo-1603801440581-1d3d311cd245?w=400");
                                addMock(mocks, restaurantId, 219, "Penne Alfredo", 249, "Pasta", true,
                                                "Creamy white sauce pasta. (420 Kcal)", 4.5, 310,
                                                "https://images.unsplash.com/photo-1555949258-eb67b1ef0ceb?w=400");
                                addMock(mocks, restaurantId, 220, "Spaghetti Bolognese", 279, "Pasta", false,
                                                "Pasta with meat tomato sauce. (480 Kcal)", 4.6, 350,
                                                "https://images.unsplash.com/photo-1598866594230-a7c12756260f?w=400");
                                addMock(mocks, restaurantId, 221, "Penne Arrabbiata", 239, "Pasta", true,
                                                "Spicy tomato sauce pasta. (260 Kcal)", 4.4, 180,
                                                "https://images.unsplash.com/photo-1582035619445-9c26d5de240d?w=400");
                                addMock(mocks, restaurantId, 222, "Lasagna", 299, "Pasta", false,
                                                "Layered pasta with meat, cheese and tomato sauce. (580 Kcal)", 4.8,
                                                420,
                                                "https://images.unsplash.com/photo-1709429790175-b02bb1b19207?w=400");

                                // Sandwich
                                addMock(mocks, restaurantId, 223, "Chicken Panini", 229, "Sandwich", false,
                                                "Pressed Italian sandwich with grilled chicken and pesto. (350 Kcal)",
                                                4.5, 210,
                                                "https://images.unsplash.com/photo-1619894991209-9f9694be045a?w=400");
                                addMock(mocks, restaurantId, 224, "Veg Panini", 199, "Sandwich", true,
                                                "Italian seasonal vegetable and cheese panini. (310 Kcal)", 4.3, 180,
                                                "https://images.unsplash.com/photo-1619895092538-128341789043?w=400");
                                addMock(mocks, restaurantId, 225, "Grilled Italian Cheese Sandwich", 189, "Sandwich",
                                                true,
                                                "Classic grilled sandwich with provolone and mozzarella. (320 Kcal)",
                                                4.4, 240,
                                                "https://images.unsplash.com/photo-1528736235302-52922df5c122?w=400");

                                // Desserts
                                addMock(mocks, restaurantId, 226, "Tiramisu", 199, "Desserts", true,
                                                "Classic Italian coffee-flavored dessert. (280 Kcal)", 4.9, 510,
                                                "https://images.unsplash.com/photo-1571877227200-a0d98ea607e9?w=400");
                                addMock(mocks, restaurantId, 227, "Panna Cotta", 189, "Desserts", true,
                                                "Creamy Italian custard with berry coulis. (240 Kcal)", 4.7, 230,
                                                "https://images.unsplash.com/photo-1603236268617-d023914d9416?w=400");
                                addMock(mocks, restaurantId, 228, "Chocolate Cannoli", 179, "Desserts", true,
                                                "Crispy pastry shell with chocolate cream. (210 Kcal)", 4.6, 180,
                                                "https://images.unsplash.com/photo-1600835768553-0cc19062f912?w=400");
                                addMock(mocks, restaurantId, 229, "Italian Gelato", 149, "Desserts", true,
                                                "Artisan Italian ice cream (Vanilla/Chocolate). (180 Kcal)", 4.8, 420,
                                                "https://images.unsplash.com/photo-1557480091-8b815bba4064?w=400");

                                // Drinks
                                addMock(mocks, restaurantId, 230, "Italian Soda", 129, "Drinks", true,
                                                "Refreshing carbonated fruit flavored soda. (150 Kcal)", 4.2, 95,
                                                "https://images.unsplash.com/photo-1663056797384-5ca6af2e82c5?w=400");
                                addMock(mocks, restaurantId, 231, "Cold Coffee", 139, "Drinks", true,
                                                "Classic chilled coffee. (180 Kcal)", 4.4, 150,
                                                "https://images.unsplash.com/photo-1517701604599-bb29b565090c?w=400");
                                addMock(mocks, restaurantId, 232, "Fresh Lemon Soda", 99, "Drinks", true,
                                                "Tangy lemon soda. (90 Kcal)", 4.1, 70,
                                                "https://images.unsplash.com/photo-1621330716555-5cad596c4562?w=400");
                                addMock(mocks, restaurantId, 233, "Milkshake", 159, "Drinks", true,
                                                "Flavorful shake (Chocolate / Strawberry / Vanilla). (320 Kcal)", 4.3,
                                                210,
                                                "https://images.unsplash.com/photo-1572490122747-3968b75cc699?w=400");
                                break;
                        case 3: // Spice Garden (South Indian)
                                // Recommended
                                addMock(mocks, restaurantId, 3001, "Masala Dosa", 90, "Recommended", true,
                                                "Crispy dosa stuffed with potato masala.", 4.8, 350,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=400"); // Updated
                                                                                                                       // to
                                                                                                                       // accurate
                                                                                                                       // Dosa
                                                                                                                       // with
                                                                                                                       // Chutney/Sambar
                                addMock(mocks, restaurantId, 3002, "South Indian Meals", 160, "Recommended", true,
                                                "Rice served with sambar, rasam, poriyal, curd and pickle.", 4.8, 400,
                                                "https://images.unsplash.com/photo-1741376509680-9517d487689f?w=400");
                                addMock(mocks, restaurantId, 3003, "Paneer Butter Masala", 180, "Recommended", true,
                                                "Rich and creamy tomato-based paneer curry.", 4.7, 220,
                                                "https://images.unsplash.com/photo-1631452180519-c014fe946bc7?w=400");
                                addMock(mocks, restaurantId, 3004, "Idli (2 pcs)", 40, "Recommended", true,
                                                "Soft steamed rice cakes served with sambar and coconut chutney.", 4.6,
                                                210,
                                                "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=400");

                                // Breakfast
                                addMock(mocks, restaurantId, 301, "Idli (2 pcs)", 40, "Breakfast", true,
                                                "Soft steamed rice cakes served with sambar and coconut chutney.", 4.6,
                                                210,
                                                "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=400");
                                addMock(mocks, restaurantId, 302, "Medu Vada (2 pcs)", 60, "Breakfast", true,
                                                "Crispy urad dal vada served with chutney and sambar.", 4.5, 180,
                                                "https://images.unsplash.com/photo-1730191843435-073792ba22bc?w=400"); // Accurate
                                                                                                                       // Medu
                                                                                                                       // Vada
                                addMock(mocks, restaurantId, 303, "Masala Dosa", 90, "Breakfast", true,
                                                "Crispy dosa stuffed with potato masala.", 4.8, 350,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=400"); // Updated
                                                                                                                       // to
                                                                                                                       // accurate
                                                                                                                       // Masala
                                                                                                                       // Dosa
                                addMock(mocks, restaurantId, 304, "Plain Dosa", 70, "Breakfast", true,
                                                "Golden crispy dosa served with chutney.", 4.5, 200,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=400");
                                addMock(mocks, restaurantId, 305, "Ghee Roast Dosa", 110, "Breakfast", true,
                                                "Crispy dosa roasted in pure ghee.", 4.7, 230,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=400");
                                addMock(mocks, restaurantId, 306, "Onion Uttapam", 90, "Breakfast", true,
                                                "Thick dosa topped with onions.", 4.6, 160,
                                                "https://images.unsplash.com/photo-1694849789325-914b71ab4075?w=400"); // Updated
                                                                                                                       // to
                                                                                                                       // accurate
                                                                                                                       // Uttapam
                                addMock(mocks, restaurantId, 307, "Rava Upma", 70, "Breakfast", true,
                                                "Semolina cooked with vegetables and spices.", 4.4, 120,
                                                "https://images.unsplash.com/photo-1630409349197-b733a524b24e?w=400"); // Updated
                                                                                                                       // to
                                                                                                                       // accurate
                                                                                                                       // Upma
                                addMock(mocks, restaurantId, 308, "Ven Pongal", 80, "Breakfast", true,
                                                "Rice and dal cooked with ghee, pepper and cashews.", 4.5, 140,
                                                "https://images.unsplash.com/photo-1665660710687-b44c50751054?w=400"); // Updated
                                                                                                                       // to
                                                                                                                       // accurate
                                                                                                                       // Pongal

                                // Lunch
                                addMock(mocks, restaurantId, 309, "South Indian Meals", 160, "Lunch", true,
                                                "Rice served with sambar, rasam, poriyal, curd and pickle.", 4.8, 400,
                                                "https://images.unsplash.com/photo-1741376509680-9517d487689f?w=400");
                                addMock(mocks, restaurantId, 310, "Sambar Rice", 110, "Lunch", true,
                                                "Steamed rice mixed with flavorful sambar.", 4.6, 210,
                                                "https://images.unsplash.com/photo-1589899476489-2b5e3e2b323f?w=400"); // Verified
                                                                                                                       // Sambar
                                                                                                                       // Rice
                                addMock(mocks, restaurantId, 311, "Curd Rice", 90, "Lunch", true,
                                                "Rice mixed with fresh curd and tempered spices.", 4.5, 180,
                                                "https://images.unsplash.com/photo-1633383718081-22ac93e3db65?w=400");
                                addMock(mocks, restaurantId, 312, "Lemon Rice", 100, "Lunch", true,
                                                "Tangy rice flavored with lemon and peanuts.", 4.4, 150,
                                                "https://images.unsplash.com/photo-1710091691802-7dedb8af9a77?w=400"); // Verified
                                                                                                                       // Lemon
                                                                                                                       // Rice
                                addMock(mocks, restaurantId, 313, "Tomato Rice", 110, "Lunch", true,
                                                "Spicy rice cooked with tomato masala.", 4.5, 160,
                                                "https://images.unsplash.com/photo-1654333433211-608057c71603?w=400"); // Verified
                                                                                                                       // Tomato
                                                                                                                       // Rice
                                addMock(mocks, restaurantId, 314, "Vegetable Biryani", 160, "Lunch", true,
                                                "Aromatic basmati rice cooked with vegetables and spices.", 4.7, 300,
                                                "https://images.unsplash.com/photo-1645177628172-a94c1f96e6db?w=400"); // Verified
                                                                                                                       // Veg
                                                                                                                       // Biryani
                                addMock(mocks, restaurantId, 315, "Paneer Biryani", 190, "Lunch", true,
                                                "Rich biryani made with paneer cubes and spices.", 4.8, 260,
                                                "https://images.unsplash.com/photo-1631515243349-e0cb75fb8d3a?w=400"); // Verified
                                                                                                                       // Paneer
                                                                                                                       // Biryani

                                // Dinner
                                addMock(mocks, restaurantId, 316, "Butter Naan (2 pcs)", 60, "Dinner", true,
                                                "Soft and buttery Indian flatbread.", 4.6, 190,
                                                "https://images.unsplash.com/photo-1697155406014-04dc649b0953?w=400");
                                addMock(mocks, restaurantId, 317, "Chapati (2 pcs)", 50, "Dinner", true,
                                                "Whole wheat flatbreads.", 4.4, 150,
                                                "https://images.unsplash.com/photo-1546833999-b9f581a1996d?w=400"); // Accurate
                                                                                                                    // Chapati
                                addMock(mocks, restaurantId, 318, "Paneer Butter Masala", 180, "Dinner", true,
                                                "Cottage cheese in a rich tomato and butter gravy.", 4.7, 220,
                                                "https://images.unsplash.com/photo-1631452180519-c014fe946bc7?w=400");
                                addMock(mocks, restaurantId, 319, "Vegetable Kurma", 140, "Dinner", true,
                                                "Assorted vegetables cooked in a coconut and spice base.", 4.5, 160,
                                                "https://images.unsplash.com/photo-1690401767645-595de0e0e5f8?w=400");
                                addMock(mocks, restaurantId, 320, "Kadai Paneer", 190, "Dinner", true,
                                                "Paneer cooked with bell peppers and freshly ground spices.", 4.6, 200,
                                                "https://images.unsplash.com/photo-1701579231378-3726490a407b?w=400");
                                addMock(mocks, restaurantId, 321, "Parotta (2 pcs)", 70, "Dinner", true,
                                                "Layered flaky flatbread made from maida.", 4.5, 210,
                                                "https://images.unsplash.com/photo-1683533743190-89c9b19f9ea6?w=400"); // Verified
                                                                                                                       // Parotta
                                addMock(mocks, restaurantId, 322, "Parotta with Salna", 110, "Dinner", true,
                                                "Parotta served with a spicy flavorful gravy.", 4.7, 250,
                                                "https://images.unsplash.com/photo-1683533743190-89c9b19f9ea6?w=400"); // Verified
                                                                                                                       // Parotta

                                // Snacks
                                addMock(mocks, restaurantId, 323, "Masala Vada", 40, "Snacks", true,
                                                "Crunchy fried lentil fritters.", 4.5, 120,
                                                "https://images.unsplash.com/photo-1683533678033-f5d60f0a3437?w=400"); // Verified
                                                                                                                       // Masala
                                                                                                                       // Vada
                                addMock(mocks, restaurantId, 324, "Bajji (3 pcs)", 60, "Snacks", true,
                                                "Crispy vegetable fritters dipped in gram flour batter.", 4.4, 90,
                                                "https://images.unsplash.com/photo-1666190091191-0cd0c5c8c5b5?w=400");
                                addMock(mocks, restaurantId, 325, "Samosa (2 pcs)", 40, "Snacks", true,
                                                "Crispy pastry shell stuffed with spiced potatoes and peas.", 4.6, 240,
                                                "https://images.unsplash.com/photo-1541544741938-0af808871cc0?w=400");
                                addMock(mocks, restaurantId, 326, "Veg Cutlet", 60, "Snacks", true,
                                                "Savory vegetable patties breaded and deep fried.", 4.3, 85,
                                                "https://images.unsplash.com/photo-1708782340351-25feb5640076?w=400"); // Verified
                                                                                                                       // Cutlet
                                addMock(mocks, restaurantId, 327, "Onion Pakoda", 70, "Snacks", true,
                                                "Crispy deep-fried onion fritters.", 4.5, 110,
                                                "https://images.unsplash.com/photo-1711565129645-e2fd3e09ff18?w=400"); // Verified
                                                                                                                       // Onion
                                                                                                                       // Pakoda

                                // Beverages
                                addMock(mocks, restaurantId, 328, "South Indian Filter Coffee", 35, "Beverages", true,
                                                "Traditional hot coffee brewed with milk and chicory.", 4.8, 300,
                                                "https://images.unsplash.com/photo-1668236482744-b48b28650f12?w=400"); // Verified
                                                                                                                       // Filter
                                                                                                                       // Coffee
                                addMock(mocks, restaurantId, 329, "Masala Tea", 25, "Beverages", true,
                                                "Spiced tea with milk and aromatic herbs.", 4.6, 180,
                                                "https://images.unsplash.com/photo-1683533699004-7f6b9e5a073f?w=400"); // Verified
                                                                                                                       // Masala
                                                                                                                       // Tea
                                addMock(mocks, restaurantId, 330, "Cold Coffee", 90, "Beverages", true,
                                                "Chilled coffee blended with milk and sugar.", 4.5, 210,
                                                "https://images.unsplash.com/photo-1517701604599-bb29b565090c?w=400");
                                addMock(mocks, restaurantId, 331, "Badam Milk", 80, "Beverages", true,
                                                "Warm or cold almond-flavored milk with saffron.", 4.7, 150,
                                                "https://images.unsplash.com/photo-1616429266184-7455498d96db?w=400");
                                addMock(mocks, restaurantId, 332, "Fresh Lime Juice", 50, "Beverages", true,
                                                "Refreshing lime drink with water or soda.", 4.4, 130,
                                                "https://images.unsplash.com/photo-1621263764928-df1444c5e859?w=400");
                                break;
                        case 4: // Golden Dragon (Chinese)
                                // Recommended
                                addMock(mocks, restaurantId, 401, "Chicken Lollipop", 180, "Recommended", false,
                                                "Crispy fried chicken wings tossed in spicy sauce.", 4.8, 320,
                                                "https://images.unsplash.com/photo-1598515214211-89d3c73ae83b?w=400");
                                addMock(mocks, restaurantId, 402, "Veg Spring Rolls", 120, "Recommended", true,
                                                "Crispy rolls stuffed with vegetables.", 4.2, 110,
                                                "https://images.unsplash.com/photo-1544650039-34e9ce140960?w=400");
                                addMock(mocks, restaurantId, 404, "Chicken Fried Rice", 170, "Recommended", false,
                                                "Indo-Chinese style fried rice with chicken.", 4.5, 210,
                                                "https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=400");
                                addMock(mocks, restaurantId, 405, "Chicken Noodles", 170, "Recommended", false,
                                                "Stir fried noodles with chicken and vegetables.", 4.4, 180,
                                                "https://images.unsplash.com/photo-1585032226651-759b368d7246?w=400");

                                // Starters - Veg
                                addMock(mocks, restaurantId, 406, "Gobi Manchurian", 150, "Starters", true,
                                                "Crispy cauliflower florets tossed in a spicy, tangy Manchurian sauce.",
                                                4.6, 250,
                                                "https://images.unsplash.com/photo-1630409346824-4f0e7b080087?w=400");
                                addMock(mocks, restaurantId, 407, "Baby Corn Manchurian", 150, "Starters", true,
                                                "Tender baby corn fried and tossed in spicy Chinese sauce.", 4.3, 120,
                                                "https://images.unsplash.com/photo-1626074353765-517a681e40be?w=400");
                                addMock(mocks, restaurantId, 408, "Veg Manchurian Dry", 140, "Starters", true,
                                                "Vegetable balls in a thick, spicy dry sauce.", 4.4, 140,
                                                "https://images.unsplash.com/photo-1630409346824-4f0e7b080087?w=400");
                                addMock(mocks, restaurantId, 409, "Chilli Paneer", 170, "Starters", true,
                                                "Fried paneer cubes tossed with bell peppers and green chillies.", 4.5,
                                                180,
                                                "https://images.unsplash.com/photo-1567188040759-fbcd1888298d?w=400");

                                // Starters - Non-Veg
                                addMock(mocks, restaurantId, 410, "Chilli Chicken Dry", 180, "Starters", false,
                                                "Spicy fried chicken tossed with green chillies and onions.", 4.7, 210,
                                                "https://images.unsplash.com/photo-1598514983318-2f64f8f4796c?w=400");
                                addMock(mocks, restaurantId, 411, "Dragon Chicken", 190, "Starters", false,
                                                "Spicy, tangy, and slightly sweet stir-fried chicken with cashews.",
                                                4.6, 175,
                                                "https://images.unsplash.com/photo-1525755662778-989d0524087e?w=400");
                                addMock(mocks, restaurantId, 412, "Pepper Chicken", 190, "Starters", false,
                                                "Succulent chicken pieces cooked with crushed black pepper and curry leaves.",
                                                4.5, 130,
                                                "https://images.unsplash.com/photo-1598514982205-f36b96d1e8d4?w=400");

                                // Noodles
                                addMock(mocks, restaurantId, 413, "Veg Hakka Noodles", 150, "Noodles", true,
                                                "Classic Indo-Chinese noodles stir-fried with vegetables.", 4.4, 150,
                                                "https://images.unsplash.com/photo-1585032226651-759b368d7246?w=400");
                                addMock(mocks, restaurantId, 414, "Egg Noodles", 160, "Noodles", false,
                                                "Stir-fried noodles with scrambled egg and vegetables.", 4.3, 110,
                                                "https://images.unsplash.com/photo-1582878826629-29b7ad1cdc43?w=400");
                                addMock(mocks, restaurantId, 415, "Schezwan Noodles", 180, "Noodles", true,
                                                "Spicy noodles tossed in fiery Schezwan sauce.", 4.5, 190,
                                                "https://images.unsplash.com/photo-1585032226651-759b368d7246?w=400");
                                addMock(mocks, restaurantId, 416, "Mixed Noodles", 200, "Noodles", false,
                                                "Noodles stir-fried with chicken, egg, and vegetables.", 4.6, 140,
                                                "https://images.unsplash.com/photo-1585032226651-759b368d7246?w=400");

                                // Fried Rice
                                addMock(mocks, restaurantId, 417, "Veg Fried Rice", 150, "Fried Rice", true,
                                                "Fluffy rice stir-fried with seasonal vegetables and soy sauce.", 4.3,
                                                160,
                                                "https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=400");
                                addMock(mocks, restaurantId, 418, "Egg Fried Rice", 160, "Fried Rice", false,
                                                "Aromatic rice stir-fried with scrambled eggs.", 4.4, 120,
                                                "https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=400");
                                addMock(mocks, restaurantId, 419, "Schezwan Fried Rice", 180, "Fried Rice", true,
                                                "Spicy rice tossed in bold Schezwan sauce.", 4.5, 130,
                                                "https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=400");
                                addMock(mocks, restaurantId, 420, "Mixed Fried Rice", 200, "Fried Rice", false,
                                                "Rice stir-fried with a mix of chicken, egg, and vegetables.", 4.7, 180,
                                                "https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=400");

                                // Gravy Items
                                addMock(mocks, restaurantId, 421, "Chicken Manchurian Gravy", 190, "Gravy Items", false,
                                                "Fried chicken balls in a thick, savory Manchurian gravy.", 4.6, 150,
                                                "https://images.unsplash.com/photo-1598514983318-2f64f8f4796c?w=400");
                                addMock(mocks, restaurantId, 422, "Chilli Chicken Gravy", 190, "Gravy Items", false,
                                                "Spicy chicken in a rich soy-based gravy with bell peppers.", 4.5, 140,
                                                "https://images.unsplash.com/photo-1598514982205-f36b96d1e8d4?w=400");
                                addMock(mocks, restaurantId, 423, "Veg Manchurian Gravy", 160, "Gravy Items", true,
                                                "Vegetable balls in a tangy and spicy Manchurian gravy.", 4.4, 110,
                                                "https://images.unsplash.com/photo-1630409346824-4f0e7b080087?w=400");
                                addMock(mocks, restaurantId, 424, "Paneer Manchurian Gravy", 180, "Gravy Items", true,
                                                "Fried paneer cubes in a flavorful Manchurian gravy.", 4.5, 95,
                                                "https://images.unsplash.com/photo-1567188040759-fbcd1888298d?w=400");

                                // Soups
                                addMock(mocks, restaurantId, 425, "Hot & Sour Soup", 110, "Soups", true,
                                                "A spicy and tangy soup with finely chopped vegetables.", 4.3, 85,
                                                "https://images.unsplash.com/photo-1547592110-8036ee3ee2b6?w=400");
                                addMock(mocks, restaurantId, 426, "Sweet Corn Soup", 110, "Soups", true,
                                                "Creamy and comforting soup with sweet corn kernels.", 4.4, 75,
                                                "https://images.unsplash.com/photo-1626074353765-517a681e40be?w=400");
                                addMock(mocks, restaurantId, 427, "Chicken Manchow Soup", 130, "Soups", false,
                                                "Spicy Indo-Chinese soup with chicken and crunchy fried noodles.", 4.6,
                                                95,
                                                "https://images.unsplash.com/photo-1547928501-136fec61901a?w=400");
                                addMock(mocks, restaurantId, 428, "Veg Manchow Soup", 120, "Soups", true,
                                                "Spicy vegetable soup served with crispy noodles.", 4.5, 65,
                                                "https://images.unsplash.com/photo-1547928501-136fec61901a?w=400");

                                // Drinks
                                addMock(mocks, restaurantId, 429, "Lime Soda", 70, "Drinks", true,
                                                "Refreshing lemon juice with soda and mint.", 4.1, 95,
                                                "https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?w=400");
                                addMock(mocks, restaurantId, 430, "Cold Coffee", 120, "Drinks", true,
                                                "Creamy chilled coffee blended to perfection.", 4.4, 150,
                                                "https://images.unsplash.com/photo-1517701604599-bb29b565090c?w=400");
                                addMock(mocks, restaurantId, 431, "Chocolate Milkshake", 140, "Drinks", true,
                                                "Rich and creamy chocolate flavored milkshake.", 4.3, 110,
                                                "https://images.unsplash.com/photo-1572490122747-3968b75cc699?w=400");
                                addMock(mocks, restaurantId, 432, "Vanilla Milkshake", 140, "Drinks", true,
                                                "Classic creamy vanilla milkshake.", 4.2, 85,
                                                "https://images.unsplash.com/photo-1553787499-6f9133860278?w=400");
                                break;
                        case 5: // Urban Sushi (Japanese)
                                // Recommended
                                addMock(mocks, restaurantId, 501, "Salmon Nigiri (4 pcs)", 250, "Recommended", false,
                                                "Fresh salmon over pressed vinegar rice.", 4.9, 120,
                                                "https://images.unsplash.com/photo-1549635747-380d0d8cdca2?w=400");
                                addMock(mocks, restaurantId, 502, "California Roll (8 pcs)", 220, "Recommended", false,
                                                "Crab, avocado, and cucumber with sesame seeds.", 4.8, 95,
                                                "https://images.unsplash.com/photo-1559410545-0bdcd187e0a6?w=400");
                                addMock(mocks, restaurantId, 503, "Chicken Katsu Roll", 240, "Recommended", false,
                                                "Crispy chicken with cucumber and savory sauce in a roll.", 4.7, 150,
                                                "https://images.unsplash.com/photo-1617196034183-421b4917c92d?w=400");
                                addMock(mocks, restaurantId, 504, "Tempura Prawn Roll", 260, "Recommended", false,
                                                "Crunchy tempura prawns with avocado and spicy mayo.", 4.8, 110,
                                                "https://images.unsplash.com/photo-1562436260-8c9516625ba7?w=400");

                                // Sushi Rolls
                                addMock(mocks, restaurantId, 505, "Veg Sushi Roll", 200, "Sushi Rolls", true,
                                                "Cucumber, avocado, and carrot in a fresh seaweed wrap.", 4.4, 140,
                                                "https://images.unsplash.com/photo-1579871494447-9811cf80d66c?w=400");
                                addMock(mocks, restaurantId, 506, "Chicken Teriyaki Roll", 240, "Sushi Rolls", false,
                                                "Succulent chicken with sweet teriyaki glaze in a roll.", 4.6, 120,
                                                "https://images.unsplash.com/photo-1559410545-0bdcd187e0a6?w=400");
                                addMock(mocks, restaurantId, 507, "Spicy Tuna Roll", 260, "Sushi Rolls", false,
                                                "Fresh tuna with spicy mayo and green onions.", 4.9, 95,
                                                "https://images.unsplash.com/photo-1562436260-8c9516625ba7?w=400");
                                addMock(mocks, restaurantId, 508, "Paneer Tempura Roll", 220, "Sushi Rolls", true,
                                                "Crispy paneer tempura with cucumber and spicy sauce.", 4.5, 80,
                                                "https://images.unsplash.com/photo-1617196034183-421b4917c92d?w=400");
                                addMock(mocks, restaurantId, 509, "Crunchy Veg Roll", 210, "Sushi Rolls", true,
                                                "Vegetable roll topped with crispy tempura flakes.", 4.3, 110,
                                                "https://images.unsplash.com/photo-1579871494447-9811cf80d66c?w=400");

                                // Tempura
                                addMock(mocks, restaurantId, 510, "Prawn Tempura (4 pcs)", 280, "Tempura", false,
                                                "Lightly battered and deep-fried prawns served with dip.", 4.8, 140,
                                                "https://images.unsplash.com/photo-1558961363-fa8fdf82db35?w=400");
                                addMock(mocks, restaurantId, 511, "Chicken Tempura", 240, "Tempura", false,
                                                "Crispy chicken strips in light Japanese tempura batter.", 4.6, 120,
                                                "https://images.unsplash.com/photo-1558961363-fa8fdf82db35?w=400");
                                addMock(mocks, restaurantId, 512, "Veg Tempura", 200, "Tempura", true,
                                                "Assorted seasonal vegetables fried in light tempura batter.", 4.4, 90,
                                                "https://images.unsplash.com/photo-1558961363-fa8fdf82db35?w=400");

                                // Ramen / Noodles
                                addMock(mocks, restaurantId, 513, "Chicken Ramen", 240, "Ramen / Noodles", false,
                                                "Savory broth with noodles, chicken, egg, and bamboo shoots.", 4.7, 180,
                                                "https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=400");
                                addMock(mocks, restaurantId, 514, "Veg Ramen", 210, "Ramen / Noodles", true,
                                                "Clear vegetable broth with traditional ramen noodles and corn.", 4.5,
                                                140,
                                                "https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=400");
                                addMock(mocks, restaurantId, 515, "Spicy Miso Ramen", 250, "Ramen / Noodles", true,
                                                "Fiery miso broth with noodles and traditional toppings.", 4.6, 110,
                                                "https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=400");
                                addMock(mocks, restaurantId, 516, "Chicken Udon Noodles", 230, "Ramen / Noodles", false,
                                                "Thick udon noodles stir-fried with chicken and vegetables.", 4.5, 95,
                                                "https://images.unsplash.com/photo-1582878826629-29b7ad1cdc43?w=400");

                                // Japanese Chicken Dishes
                                addMock(mocks, restaurantId, 517, "Chicken Teriyaki", 260, "Japanese Chicken Dishes",
                                                false,
                                                "Grilled chicken glazed with sweet and savory teriyaki sauce.", 4.8,
                                                210,
                                                "https://images.unsplash.com/photo-1596450514735-24003de66345?w=400");
                                addMock(mocks, restaurantId, 518, "Chicken Katsu", 250, "Japanese Chicken Dishes",
                                                false,
                                                "Breaded and fried chicken cutlet served with katsu sauce.", 4.7, 180,
                                                "https://images.unsplash.com/photo-1626074353765-517a681e40be?w=400");
                                addMock(mocks, restaurantId, 519, "Spicy Garlic Chicken", 240,
                                                "Japanese Chicken Dishes", false,
                                                "Stir-fried chicken in a bold garlic and chili sauce.", 4.6, 130,
                                                "https://images.unsplash.com/photo-1525755662778-989d0524087e?w=400");

                                // Rice Bowls
                                addMock(mocks, restaurantId, 520, "Chicken Teriyaki Rice Bowl", 220, "Rice Bowls",
                                                false,
                                                "Teriyaki chicken served over a steaming bowl of rice.", 4.5, 160,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 521, "Veg Sushi Rice Bowl", 200, "Rice Bowls", true,
                                                "Vinegared rice topped with fresh vegetables and seaweed.", 4.4, 120,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 522, "Egg Fried Rice (Japanese style)", 210, "Rice Bowls",
                                                false,
                                                "Japanese style fried rice with scrambled eggs and spring onions.", 4.4,
                                                90,
                                                "https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=400");
                                addMock(mocks, restaurantId, 523, "Chicken Fried Rice", 230, "Rice Bowls", false,
                                                "Savory Japanese fried rice with chicken chunks.", 4.6, 140,
                                                "https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=400");

                                // Small Bites
                                addMock(mocks, restaurantId, 524, "Chicken Gyoza (Dumplings)", 220, "Small Bites",
                                                false,
                                                "Pan-fried Japanese dumplings filled with minced chicken.", 4.7, 130,
                                                "https://images.unsplash.com/photo-1563245391-744db72be3f5?w=400");
                                addMock(mocks, restaurantId, 525, "Veg Gyoza", 200, "Small Bites", true,
                                                "Healthy vegetable steamed or fried dumplings.", 4.5, 110,
                                                "https://images.unsplash.com/photo-1563245391-744db72be3f5?w=400");
                                addMock(mocks, restaurantId, 526, "Edamame Beans", 180, "Small Bites", true,
                                                "Steamed young soybeans with a sprinkle of sea salt.", 4.2, 85,
                                                "https://images.unsplash.com/photo-1524350300060-93708a4aaa76?w=400");

                                // Desserts
                                addMock(mocks, restaurantId, 527, "Matcha Ice Cream", 180, "Desserts", true,
                                                "Traditional Japanese green tea flavored ice cream.", 4.6, 120,
                                                "https://images.unsplash.com/photo-1505394033323-4241b2213fd3?w=400");
                                addMock(mocks, restaurantId, 528, "Mochi Ice Cream", 200, "Desserts", true,
                                                "Soft rice cake balls with an ice cream center.", 4.8, 95,
                                                "https://images.unsplash.com/photo-1553334410-09e472624447?w=400");
                                addMock(mocks, restaurantId, 529, "Chocolate Dorayaki", 190, "Desserts", true,
                                                "Japanese pancakes filled with rich chocolate.", 4.5, 80,
                                                "https://images.unsplash.com/photo-1582295624795-36da0749007e?w=400");

                                // Drinks
                                addMock(mocks, restaurantId, 530, "Japanese Green Tea", 120, "Drinks", true,
                                                "Authentic hot brewed matcha green tea.", 4.4, 65,
                                                "https://images.unsplash.com/photo-1515696423086-05c72eb845f6?w=400");
                                addMock(mocks, restaurantId, 531, "Lemon Iced Tea", 120, "Drinks", true,
                                                "Chilled and refreshing citrus infused tea.", 4.3, 85,
                                                "https://images.unsplash.com/photo-1556679343-c7306c1976bc?w=400");
                                addMock(mocks, restaurantId, 532, "Cold Coffee", 140, "Drinks", true,
                                                "Creamy chilled coffee with a hint of vanilla.", 4.5, 110,
                                                "https://images.unsplash.com/photo-1461023058943-07fcbe16d735?w=400");
                                addMock(mocks, restaurantId, 533, "Chocolate Milkshake", 150, "Drinks", true,
                                                "Indulgent chocolate shake topped with whipped cream.", 4.6, 130,
                                                "https://images.unsplash.com/photo-1572490122747-3968b75cc699?w=400");
                                break;
                        case 6: // Taco Town (Mexican)
                                addMock(mocks, restaurantId, 601, "Street Tacos (3pcs)", 180, "Recommended", false,
                                                "Authentic street style tacos with cilantro and onion.", 4.4, 150,
                                                "https://images.unsplash.com/photo-1648437595587-e6a8b0cdf1f9?w=400");
                                addMock(mocks, restaurantId, 602, "Burrito Bowl", 210, "Recommended", true,
                                                "Rice, beans, salsa and guac bowl.", 4.3, 210,
                                                "https://images.unsplash.com/photo-1668665771757-4d42737d295a?w=400");
                                addMock(mocks, restaurantId, 603, "Nachos Supreme", 160, "Sides", true,
                                                "Crispy chips with melted cheese and jalapeños.", 4.1, 130,
                                                "https://images.unsplash.com/photo-1582169296194-e4d644c48063?w=400");
                                break;
                        case 7: // The French Bistro
                                addMock(mocks, restaurantId, 701, "Beef Bourguignon", 24.99, "Recommended", false,
                                                "Slow-cooked beef in red wine sauce. (520 Kcal)", 4.8, 85,
                                                "https://images.unsplash.com/photo-1548869206-93b036288d7e?w=400");
                                addMock(mocks, restaurantId, 702, "French Onion Soup", 12.50, "Recommended", false,
                                                "Classic broth with melted cheese and bread. (210 Kcal)", 4.6, 65,
                                                "https://images.unsplash.com/photo-1549203438-a7696aed4dac?w=400");
                                addMock(mocks, restaurantId, 703, "Crème Brûlée", 8.99, "Desserts", true,
                                                "Rich custard with a burnt sugar top. (350 Kcal)", 4.7, 120,
                                                "https://images.unsplash.com/photo-1676300184943-09b2a08319a3?w=400");
                                break;
                        case 8: // Steakhouse Prime
                                addMock(mocks, restaurantId, 801, "Ribeye Steak (12oz)", 29.99, "Recommended", false,
                                                "Premium dry-aged steak grilled to order. (650 Kcal)", 4.9, 310,
                                                "https://images.unsplash.com/photo-1546964124-0cce460f38ef?w=400");
                                addMock(mocks, restaurantId, 802, "Mashed Potatoes", 5.50, "Recommended", true,
                                                "Creamy potatoes with butter and garlic. (180 Kcal)", 4.3, 150,
                                                "https://images.unsplash.com/photo-1707616954324-99c89a78a20d?w=400");
                                addMock(mocks, restaurantId, 803, "Red Wine Sauce", 2.00, "Add-ons", false,
                                                "Rich reduction sauce for steaks. (45 Kcal)", 4.5, 90,
                                                "https://images.unsplash.com/photo-1726946972127-941225b132f2?w=400");
                                break;
                        case 9: // Healthy Greens
                                addMock(mocks, restaurantId, 901, "Quinoa Buddha Bowl", 14.50, "Recommended", true,
                                                "Nutrient-dense bowl with quinoa and fresh veg. (320 Kcal)", 4.6, 210,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 902, "Avocado Toast", 11.99, "Recommended", true,
                                                "Whole grain toast with smashed avo and chili flakes. (240 Kcal)", 4.5,
                                                340,
                                                "https://images.unsplash.com/photo-1525351484163-7529414344d8?w=400");
                                addMock(mocks, restaurantId, 903, "Fresh Green Juice", 6.50, "Recommended", true,
                                                "Cold-pressed juice with kale, apple and lemon. (110 Kcal)", 4.4, 180,
                                                "https://images.unsplash.com/photo-1759006249055-8c4030a2d56a?w=400");
                                break;
                        default:
                                addMock(mocks, restaurantId, 999, "Special Dish", 10.00, "Recommended", true,
                                                "Today's special creation from our chef. (250 Kcal)", 4.5, 50,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                break;
                        case 10: // Sangeetha Veg Restaurant
                                addMock(mocks, restaurantId, 1001, "Plain Dosa", 70, "Breakfast", true,
                                                "Thin and crispy fermented rice crepe", 4.5, 120,
                                                "https://images.unsplash.com/photo-1668236543090-597a339d1082?w=400");
                                addMock(mocks, restaurantId, 1002, "Ghee Pongal", 85, "Breakfast", true,
                                                "Savory rice and lentil mash tempered with black pepper and cashews",
                                                4.7, 95,
                                                "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=400");
                                addMock(mocks, restaurantId, 1003, "Medu Vada (2 pcs)", 60, "Breakfast", true,
                                                "Crispy deep-fried lentil donuts", 4.6, 150,
                                                "https://images.unsplash.com/photo-1626074353765-517a681e40be?w=400");
                                addMock(mocks, restaurantId, 1004, "Mini Idli Sambar", 95, "Breakfast", true,
                                                "14 bite-sized idlis soaked in flavorful sambar", 4.8, 210,
                                                "https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=400");
                                addMock(mocks, restaurantId, 1005, "South Indian Special Meals", 180, "Lunch", true,
                                                "Full thali with rice, sambar, rasam, kootu, poriyal, and curd", 4.6,
                                                320, "https://images.unsplash.com/photo-1546833998-877b37db8778?w=400");
                                addMock(mocks, restaurantId, 1006, "Bisibelebath", 120, "Lunch", true,
                                                "Spicy lentil rice with vegetables and ghee", 4.4, 180,
                                                "https://images.unsplash.com/photo-1546833998-877b37db8778?w=400");
                                addMock(mocks, restaurantId, 1007, "Curd Rice", 90, "Lunch", true,
                                                "Comforting rice mixed with fresh yogurt and tempered spices", 4.3, 110,
                                                "https://images.unsplash.com/photo-1546833998-877b37db8778?w=400");
                                addMock(mocks, restaurantId, 1008, "Onion Pakoda", 75, "Snacks", true,
                                                "Crispy deep-fried onion fritters", 4.2, 140,
                                                "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=400");
                                addMock(mocks, restaurantId, 1009, "Chilli Bajji", 65, "Snacks", true,
                                                "Spicy green chillies dipped in gram flour batter and fried", 4.1, 95,
                                                "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=400");
                                addMock(mocks, restaurantId, 1010, "Poori Masala", 110, "Tiffin", true,
                                                "Fluffy deep-fried bread served with potato masala", 4.5, 160,
                                                "https://images.unsplash.com/photo-1626777552726-4a6b54c97e46?w=400");
                                addMock(mocks, restaurantId, 1011, "Filter Coffee", 45, "Beverages", true,
                                                "Traditional South Indian decoction coffee", 4.9, 1500,
                                                "https://images.unsplash.com/photo-1517701604599-bb29b565090c?w=400");
                                addMock(mocks, restaurantId, 1012, "Elaneer Payasam", 120, "Desserts", true,
                                                "Tender coconut kheer with creamy milk", 4.7, 85,
                                                "https://images.unsplash.com/photo-1572490122747-3968b75cc699?w=400");
                                break;
                        case 11: // Anjappar Chettinad Restaurant
                                addMock(mocks, restaurantId, 1101, "Chicken 65", 210, "Snacks", false,
                                                "Spicy deep-fried chicken cubes with curry leaves", 4.6, 320,
                                                "https://images.unsplash.com/photo-1599487488170-d11ec9c172f0?w=400");
                                addMock(mocks, restaurantId, 1102, "Chettinad Chicken Masala", 280, "Dinner", false,
                                                "Traditional spicy chicken gravy with roasted spices", 4.7, 240,
                                                "https://images.unsplash.com/photo-1604113204910-be02422776fd?w=400");
                                addMock(mocks, restaurantId, 1103, "Mutton Sukka", 320, "Dinner", false,
                                                "Dry-roasted tender mutton with Chettinad masalas", 4.8, 180,
                                                "https://images.unsplash.com/photo-1544025162-d76694265947?w=400");
                                addMock(mocks, restaurantId, 1104, "Chettinad Chicken Biryani", 260, "Biryani", false,
                                                "Fragrant seeraga samba rice cooked with chicken", 4.5, 410,
                                                "https://images.unsplash.com/photo-1563379091339-03b21bc4a6f8?w=400");
                                addMock(mocks, restaurantId, 1105, "Egg Veechu Parotto", 110, "Tiffin", false,
                                                "Thin multi-layered parotto stuffed with egg", 4.4, 95,
                                                "https://images.unsplash.com/photo-1626777552726-4a6b54c97e46?w=400");
                                addMock(mocks, restaurantId, 1106, "Vanjaram Fish Fry", 350, "Lunch", false,
                                                "King fish steak marinated and tawa fried", 4.9, 210,
                                                "https://images.unsplash.com/photo-1519708227418-c8fd9a32b7a2?w=400");
                                addMock(mocks, restaurantId, 1107, "Idiyappam (3 pcs)", 70, "Breakfast", true,
                                                "Steamed string hoppers", 4.3, 130,
                                                "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=400");
                                addMock(mocks, restaurantId, 1108, "Chicken Salna", 140, "Tiffin", false,
                                                "Spicy watery gravy best served with parotta", 4.4, 110,
                                                "https://images.unsplash.com/photo-1604113204910-be02422776fd?w=400");
                                addMock(mocks, restaurantId, 1109, "Mutton Paya", 240, "Breakfast", false,
                                                "Traditional trotters stew served with Idiyappam", 4.6, 85,
                                                "https://images.unsplash.com/photo-1544025162-d76694265947?w=400");
                                addMock(mocks, restaurantId, 1110, "Nannari Sarbat", 60, "Beverages", true,
                                                "Refreshing root extract syrup with lemon", 4.2, 55,
                                                "https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?w=400");
                                addMock(mocks, restaurantId, 1111, "Beetroot Halwa", 95, "Desserts", true,
                                                "Sweet halwa made with grated beetroot and ghee", 4.5, 75,
                                                "https://images.unsplash.com/photo-1582295624795-36da0749007e?w=400");
                                break;
                        case 12: // Nagarjuna Chimney
                                addMock(mocks, restaurantId, 1201, "Andhra Veg Meals", 210, "Lunch", true,
                                                "Spicy Andhra thali featuring gunpowder and ghee", 4.8, 540,
                                                "https://images.unsplash.com/photo-1546833998-877b37db8778?w=400");
                                addMock(mocks, restaurantId, 1202, "Nagarjuna Chicken Roast", 240, "Snacks", false,
                                                "Iconic spicy and dry chicken fry", 4.7, 320,
                                                "https://images.unsplash.com/photo-1599487488170-d11ec9c172f0?w=400");
                                addMock(mocks, restaurantId, 1203, "Andhra Chicken Biryani", 290, "Biryani", false,
                                                "Spicy hyderabadi style long grain rice biryani", 4.6, 450,
                                                "https://images.unsplash.com/photo-1563379091339-03b21bc4a6f8?w=400");
                                addMock(mocks, restaurantId, 1204, "Gongura Mutton", 330, "Lunch", false,
                                                "Succulent mutton cooked with tangy sorrel leaves", 4.9, 180,
                                                "https://images.unsplash.com/photo-1544025162-d76694265947?w=400");
                                addMock(mocks, restaurantId, 1205, "Pesarattu Upma", 130, "Breakfast", true,
                                                "Moong dal dosa stuffed with rava upma", 4.5, 110,
                                                "https://images.unsplash.com/photo-1668236543090-597a339d1082?w=400");
                                addMock(mocks, restaurantId, 1206, "Chicken Guntur", 280, "Dinner", false,
                                                "Extra spicy chicken curry from Guntur", 4.4, 150,
                                                "https://images.unsplash.com/photo-1604113204910-be02422776fd?w=400");
                                addMock(mocks, restaurantId, 1207, "Ghee Roast Dosa", 110, "Breakfast", true,
                                                "Paper thin dosa roasted in pure ghee", 4.7, 320,
                                                "https://images.unsplash.com/photo-1668236543090-597a339d1082?w=400");
                                addMock(mocks, restaurantId, 1208, "Fresh Lime Soda", 75, "Beverages", true,
                                                "Fizzy lemon drink, sweet or salted", 4.3, 180,
                                                "https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?w=400");
                                addMock(mocks, restaurantId, 1209, "Apricot Delight", 160, "Desserts", true,
                                                "Stewed apricots served with fresh cream", 4.8, 95,
                                                "https://images.unsplash.com/photo-1553334410-09e472624447?w=400");
                                break;
                        case 13: // Malabar Cafe
                                addMock(mocks, restaurantId, 1301, "Appam with Veg Stew", 140, "Breakfast", true,
                                                "Lace-edged coconut milk pancakes with mild veg curry", 4.6, 210,
                                                "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=400");
                                addMock(mocks, restaurantId, 1302, "Kerala Parotta (2 pcs)", 60, "Dinner", true,
                                                "Flaky, multi-layered flatbread", 4.8, 850,
                                                "https://images.unsplash.com/photo-1626777552726-4a6b54c97e46?w=400");
                                addMock(mocks, restaurantId, 1303, "Beef Fry (Ularthiyathu)", 250, "Dinner", false,
                                                "Traditional slow-roasted beef with coconut slices", 4.9, 320,
                                                "https://images.unsplash.com/photo-1544025162-d76694265947?w=400");
                                addMock(mocks, restaurantId, 1304, "Malabar Chicken Biryani", 270, "Biryani", false,
                                                "Short grain khaima rice biryani with fried onions", 4.7, 480,
                                                "https://images.unsplash.com/photo-1563379091339-03b21bc4a6f8?w=400");
                                addMock(mocks, restaurantId, 1305, "Karimeen Pollichathu", 350, "Lunch", false,
                                                "Pearl spot fish marinated and grilled in banana leaf", 4.8, 110,
                                                "https://images.unsplash.com/photo-1519708227418-c8fd9a32b7a2?w=400");
                                addMock(mocks, restaurantId, 1306, "Banana Fritters (Pazham Pori)", 50, "Snacks", true,
                                                "Sweet ripe plantains dipped in batter and fried", 4.5, 340,
                                                "https://images.unsplash.com/photo-1563245391-744db72be3f5?w=400");
                                addMock(mocks, restaurantId, 1307, "Sulaimani Tea", 35, "Beverages", true,
                                                "Spiced black tea with a hint of lemon", 4.4, 210,
                                                "https://images.unsplash.com/photo-1515696423086-05c72eb845f6?w=400");
                                addMock(mocks, restaurantId, 1308, "Ada Pradhaman", 120, "Desserts", true,
                                                "Traditional rice flake dessert with coconut milk", 4.7, 95,
                                                "https://images.unsplash.com/photo-1582295624795-36da0749007e?w=400");
                                break;
                        case 14: // Vidyarthi Bhavan
                                addMock(mocks, restaurantId, 1401, "Benne Masala Dosa", 95, "Breakfast", true,
                                                "Iconic butter-laden crispy dosa with potato palya", 4.9, 1200,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=400");
                                addMock(mocks, restaurantId, 1402, "Kesari Bath", 60, "Breakfast", true,
                                                "Sweet semolina pudding with saffron and pineapple", 4.6, 320,
                                                "https://images.unsplash.com/photo-1582295624795-36da0749007e?w=400");
                                addMock(mocks, restaurantId, 1403, "Vangi Bath", 110, "Lunch", true,
                                                "Brinjal rice made with special Karnataka spice mix", 4.4, 210,
                                                "https://images.unsplash.com/photo-1546833998-877b37db8778?w=400");
                                addMock(mocks, restaurantId, 1404, "Filter Kaapi", 40, "Beverages", true,
                                                "Strong decoction coffee served in brass tumbler", 4.9, 2500,
                                                "https://images.unsplash.com/photo-1517701604599-bb29b565090c?w=400");
                                addMock(mocks, restaurantId, 1405, "Mysore Pak", 80, "Desserts", true,
                                                "Rich gram flour sweet made with ghee", 4.8, 540,
                                                "https://images.unsplash.com/photo-1582295624795-36da0749007e?w=400");
                                addMock(mocks, restaurantId, 1406, "Set Dosa", 85, "Breakfast", true,
                                                "Three small, soft, and spongy dosas with sagu", 4.5, 410,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=400");
                                break;
                        case 15: // Rayalaseema Ruchulu
                                addMock(mocks, restaurantId, 1501, "Ulavacharu Chicken Biryani", 310, "Biryani", false,
                                                "Biryani cooked with horse gram soup", 4.6, 210,
                                                "https://images.unsplash.com/photo-1563379091339-03b21bc4a6f8?w=400");
                                addMock(mocks, restaurantId, 1502, "Natu Kodi Pulusu", 320, "Dinner", false,
                                                "Spicy country chicken curry", 4.7, 180,
                                                "https://images.unsplash.com/photo-1604113204910-be02422776fd?w=400");
                                addMock(mocks, restaurantId, 1503, "Mirchi Bajji", 60, "Snacks", true,
                                                "Large batter-fried green chillies stuffed with onions", 4.4, 320,
                                                "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=400");
                                addMock(mocks, restaurantId, 1504, "Gutthi Vankaya", 190, "Dinner", true,
                                                "Traditional stuffed brinjal curry in peanut gravy", 4.5, 140,
                                                "https://images.unsplash.com/photo-1604113204910-be02422776fd?w=400");
                                addMock(mocks, restaurantId, 1505, "Sunnundalu", 90, "Desserts", true,
                                                "Urad dal laddu made with jaggery and ghee", 4.6, 95,
                                                "https://images.unsplash.com/photo-1582295624795-36da0749007e?w=400");
                                break;
                        case 16: // Karavalli Seafood
                                addMock(mocks, restaurantId, 1601, "Kori Gassi", 280, "Dinner", false,
                                                "Traditional Mangalorean chicken curry in coconut milk", 4.7, 190,
                                                "https://images.unsplash.com/photo-1604113204910-be02422776fd?w=400");
                                addMock(mocks, restaurantId, 1602, "Neer Dosa (3 pcs)", 75, "Tiffin", true,
                                                "Paper-thin, lacy rice crepes", 4.6, 320,
                                                "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=400");
                                addMock(mocks, restaurantId, 1603, "Anjal Masala Fry", 340, "Lunch", false,
                                                "Seer fish fried in a thick Mangalorean spice paste", 4.9, 140,
                                                "https://images.unsplash.com/photo-1519708227418-c8fd9a32b7a2?w=400");
                                addMock(mocks, restaurantId, 1604, "Goli Baje (6 pcs)", 80, "Snacks", true,
                                                "Mangalore bonda made with maida and yogurt", 4.4, 210,
                                                "https://images.unsplash.com/photo-1563245391-744db72be3f5?w=400");
                                break;
                        case 17: // Murugan Idli Shop
                                addMock(mocks, restaurantId, 1701, "Malligai Idli", 45, "Breakfast", true,
                                                "Super soft jasmine-like idlis", 4.8, 1500,
                                                "https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=400");
                                addMock(mocks, restaurantId, 1702, "Ghee Podi Idli", 90, "Tiffin", true,
                                                "Idlis tossed in spicy gunpowder and hot ghee", 4.7, 850,
                                                "https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=400");
                                addMock(mocks, restaurantId, 1703, "Jigarthanda", 85, "Beverages", true,
                                                "Madurai's famous cold drink with almond gum", 4.9, 640,
                                                "https://images.unsplash.com/photo-1572490122747-3968b75cc699?w=400");
                                break;
                        case 18: // Thalassery Kitchen
                                addMock(mocks, restaurantId, 1801, "Chicken Dum Biryani", 260, "Biryani", false,
                                                "Famous Thalassery biryani using Jeerakasala rice", 4.8, 920,
                                                "https://images.unsplash.com/photo-1563379091339-03b21bc4a6f8?w=400");
                                addMock(mocks, restaurantId, 1802, "Pathiri with Chicken Curry", 160, "Dinner", false,
                                                "Very thin rice flatbread with spicy coconut chicken", 4.6, 210,
                                                "https://images.unsplash.com/photo-1546833998-877b37db8778?w=400");
                                addMock(mocks, restaurantId, 1803, "Kozhikodan Halwa", 90, "Desserts", true,
                                                "Famous chewy jelly-like wheat halwa", 4.5, 180,
                                                "https://images.unsplash.com/photo-1582295624795-36da0749007e?w=400");
                                break;
                        case 19: // Paradise Biryani Point
                                addMock(mocks, restaurantId, 1901, "Hyderabadi Chicken Biryani", 280, "Biryani", false,
                                                "World-famous long grain basmati rice biryani", 4.9, 2500,
                                                "https://images.unsplash.com/photo-1563379091339-03b21bc4a6f8?w=400");
                                addMock(mocks, restaurantId, 1902, "Mutton Dum Biryani", 340, "Biryani", false,
                                                "Slow-cooked goat meat and rice with saffron", 4.8, 1200,
                                                "https://images.unsplash.com/photo-1563379091339-03b21bc4a6f8?w=400");
                                addMock(mocks, restaurantId, 1903, "Double ka Meetha", 110, "Desserts", true,
                                                "Fried bread pudding soaked in saffron milk and nuts", 4.6, 320,
                                                "https://images.unsplash.com/photo-1582295624795-36da0749007e?w=400");
                                addMock(mocks, restaurantId, 1904, "Irani Chai", 35, "Beverages", true,
                                                "Authentic Hyderabadi creamy tea", 4.7, 540,
                                                "https://images.unsplash.com/photo-1515696423086-05c72eb845f6?w=400");
                                break;
                        case 20: // The Cake Studio
                                addMock(mocks, restaurantId, 2001, "Belgian Chocolate Cake", 350, "Cakes", true,
                                                "Rich layered cake with premium Belgian chocolate ganache", 4.9, 450,
                                                "https://images.unsplash.com/photo-1602683504046-cf7e90664396?w=400", true);
                                addMock(mocks, restaurantId, 2002, "Chocolate Truffle Cake", 320, "Cakes", true,
                                                "Rich dark chocolate truffle cake layered with smooth chocolate cream", 4.8, 390,
                                                "https://images.unsplash.com/photo-1625103709948-30e94496e632?w=400", false);
                                addMock(mocks, restaurantId, 2003, "Red Velvet Cake", 300, "Cakes", true,
                                                "Classic red velvet sponge with cream cheese frosting", 4.7, 340,
                                                "https://images.unsplash.com/photo-1586788680434-30d324b2d46f?w=400", false);
                                addMock(mocks, restaurantId, 2004, "Black Forest Cake", 290, "Cakes", true,
                                                "Chocolate sponge layered with whipped cream and cherries", 4.6, 410,
                                                "https://images.unsplash.com/photo-1606890737304-57a1ca8a5b62?w=400", false);
                                addMock(mocks, restaurantId, 2005, "Butterscotch Cake", 270, "Cakes", true,
                                                "Soft vanilla sponge with butterscotch cream and crunchy praline", 4.7, 370,
                                                "https://images.unsplash.com/photo-1595080622896-844ff207e639?w=400", false);
                                addMock(mocks, restaurantId, 2006, "Chocolate Lava Cake", 180, "Cakes", true,
                                                "Warm chocolate cake with a gooey molten center", 4.9, 520,
                                                "https://images.unsplash.com/photo-1511911063855-2bf39afa5b2e?w=400", true);
                                addMock(mocks, restaurantId, 2007, "Tiramisu Dessert Cup", 240, "Dessert Cups", true,
                                                "Italian coffee-flavored dessert layered with mascarpone cream", 4.8, 260,
                                                "https://images.unsplash.com/photo-1571877227200-a0d98ea607e9?w=400", false);
                                addMock(mocks, restaurantId, 2008, "Strawberry Cheesecake", 280, "Cheesecakes", true,
                                                "Creamy cheesecake topped with fresh strawberry glaze", 4.7, 310,
                                                "https://images.unsplash.com/photo-1553882299-9601a48ebe6a?w=400", false);
                                addMock(mocks, restaurantId, 2009, "Ferrero Rocher Cake", 360, "Cakes", true,
                                                "Chocolate hazelnut cake inspired by Ferrero Rocher flavors", 4.9, 430,
                                                "https://images.unsplash.com/photo-1599939569628-4b16b47c3e8d?w=400", false);
                                addMock(mocks, restaurantId, 2010, "Chocolate Brownie with Ice Cream", 220, "Brownies", true,
                                                "Warm chocolate brownie served with vanilla ice cream", 4.8, 350,
                                                "https://images.unsplash.com/photo-1606313564200-e75d5e30476c?w=400", false);
                                addMock(mocks, restaurantId, 2011, "Mango Mousse Cake", 260, "Cakes", true,
                                                "Light mango mousse layered with soft sponge cake", 4.6, 280,
                                                "https://images.unsplash.com/photo-1619728750848-dc71cd7471ae?w=400", false);
                                addMock(mocks, restaurantId, 2012, "KitKat Chocolate Cake", 330, "Cakes", true,
                                                "Chocolate cake surrounded by KitKat bars and chocolate gems", 4.8, 300,
                                                "https://images.unsplash.com/photo-1590869173972-7868b37913ab?w=400", false);
                                addMock(mocks, restaurantId, 2013, "Caramel Custard", 160, "Dessert Cups", true,
                                                "Smooth baked custard topped with rich caramel syrup", 4.5, 210,
                                                "https://images.unsplash.com/photo-1541782064729-b752e929ebbc?w=400", false);
                                addMock(mocks, restaurantId, 2014, "Blueberry Cheesecake", 280, "Cheesecakes", true,
                                                "Creamy New York style cheesecake with fresh blueberry compote", 4.8, 320,
                                                "https://images.unsplash.com/photo-1695088957420-c3b97d1f1138?w=400", false);
                                break;
                        case 21: // Sweet Temptations
                                addMock(mocks, restaurantId, 2101, "Triple Chocolate Muffin", 85, "Cakes", true,
                                                "Soft and moist chocolate muffin with Belgian chocolate chunks", 4.5, 180,
                                                "https://images.unsplash.com/photo-1555507036-ab1f4038808a?w=400", true);
                                addMock(mocks, restaurantId, 2102, "Warm Apple Pie", 150, "Cakes", true,
                                                "Traditional crusty pie with cinnamon spiced apple filling", 4.7, 250,
                                                "https://images.unsplash.com/photo-1568571780765-9276ac8b75a2?w=400", false);
                                addMock(mocks, restaurantId, 2103, "Classic Plum Cake", 220, "Cakes", true,
                                                "Rich fruit cake soaked with juices and premium dry fruits", 4.6, 320,
                                                "https://images.unsplash.com/photo-1512414776104-742a597a8cb4?w=400", false);
                                addMock(mocks, restaurantId, 2104, "Pineapple Pastry", 95, "Cakes", true,
                                                "Tropical pineapple flavored cake with buttery cream", 4.4, 190,
                                                "https://images.unsplash.com/photo-1621303837483-3642781498eb?w=400", false);
                                addMock(mocks, restaurantId, 2105, "Choco Chip Brownie", 110, "Brownies", true,
                                                "Chewy brownie loaded with dark chocolate chips", 4.8, 310,
                                                "https://images.unsplash.com/photo-1563245391-744db72be3f5?w=400", true);
                                addMock(mocks, restaurantId, 2106, "Vanilla Bean Cupcake", 70, "Cakes", true,
                                                "Light vanilla sponge topped with silky buttercream frosting", 4.6, 215,
                                                "https://images.unsplash.com/photo-1519869325930-281384150729?w=400", false);
                                addMock(mocks, restaurantId, 2107, "Mixed Berry Tart", 130, "Desserts", true,
                                                "Buttery tart shell filled with custard and fresh berries", 4.7, 140,
                                                "https://images.unsplash.com/photo-1519915028121-7d3463d20b13?w=400", false);
                                addMock(mocks, restaurantId, 2108, "Classic Choco Chip Cookie", 45, "Snacks", true,
                                                "Crispy on the edges and soft in the middle", 4.5, 420,
                                                "https://images.unsplash.com/photo-1499636136210-6f4ee915583e?w=400", false);
                                break;
                        case 22: // The Tea House
                                addMock(mocks, restaurantId, 2201, "Masala Chai", 40, "Beverages", true,
                                                "Strong Assam tea brewed with cardamom, ginger, and cloves", 4.8, 45,
                                                "https://images.unsplash.com/photo-1594137260937-f59050746e36?w=400");
                                addMock(mocks, restaurantId, 2202, "Ginger Tea", 45, "Beverages", true,
                                                "Refreshing hot tea with a strong kick of fresh ginger", 4.7, 35,
                                                "https://images.unsplash.com/photo-1515696423086-05c72eb845f6?w=400");
                                addMock(mocks, restaurantId, 2203, "Saffron Tea (Kahwa)", 90, "Beverages", true,
                                                "Premium Kashmiri style tea with saffron and crushed almonds", 4.9, 65,
                                                "https://images.unsplash.com/photo-1613343454178-62b877b1150d?w=400");
                                addMock(mocks, restaurantId, 2204, "Green Tea", 60, "Beverages", true,
                                                "Delicate and healthy organic green tea leaves", 4.5, 20,
                                                "https://images.unsplash.com/photo-1515696423086-05c72eb845f6?w=400");
                                break;
                        case 23: // Namma Karnataka
                                addMock(mocks, restaurantId, 2301, "Bisi Bele Bath", 140, "Lunch", true,
                                                "Traditional spicy rice dish with lentils and mixed vegetables", 4.8,
                                                420,
                                                "https://images.unsplash.com/photo-1546833998-877b37db8778?w=400");
                                addMock(mocks, restaurantId, 2302, "Vangi Bath", 120, "Breakfast", true,
                                                "Brinjal rice made with special Vangi Bath masala and spices", 4.6, 310,
                                                "https://images.unsplash.com/photo-1546833998-877b37db8778?w=400");
                                addMock(mocks, restaurantId, 2303, "Kesari Bath", 80, "Breakfast", true,
                                                "Sweet semolina halwa with saffron and dry fruits", 4.7, 340,
                                                "https://images.unsplash.com/photo-1582295624795-36da0749007e?w=400");
                                addMock(mocks, restaurantId, 2304, "Chow Chow Bath", 180, "Breakfast", true,
                                                "Combination of spicy Khara Bath and sweet Kesari Bath", 4.9, 650,
                                                "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=400");
                                addMock(mocks, restaurantId, 2305, "Mysore Masala Dosa", 110, "Breakfast", true,
                                                "Crispy dosa with spicy red chutney and potato filling", 4.7, 430,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=400");
                                addMock(mocks, restaurantId, 2306, "Maddur Vada (2 pcs)", 70, "Snacks", true,
                                                "Famous crispy onion and semolina snack from Maddur", 4.5, 210,
                                                "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=400");
                                break;
                }

                // Sort by requested order
                Map<String, Integer> categoryOrder = new HashMap<>();
                categoryOrder.put("Recommended", 0);
                categoryOrder.put("Breakfast", 1);
                categoryOrder.put("Lunch", 2);
                categoryOrder.put("Dinner", 3);
                categoryOrder.put("Snacks", 4);
                categoryOrder.put("Biryani", 5);
                categoryOrder.put("Tiffin", 6);
                categoryOrder.put("Beverages", 7);
                categoryOrder.put("Starters", 8);
                categoryOrder.put("Noodles", 9);
                categoryOrder.put("Ramen / Noodles", 9);
                categoryOrder.put("Fried Rice", 10);
                categoryOrder.put("Gravy Items", 11);
                categoryOrder.put("Soups", 12);
                categoryOrder.put("Drinks", 13);
                categoryOrder.put("Veg Burgers", 14);
                categoryOrder.put("Non-Veg Burgers", 15);
                categoryOrder.put("Fast Food", 16);
                categoryOrder.put("Milkshakes", 17);
                categoryOrder.put("Combo Meals", 18);
                categoryOrder.put("Sushi Rolls", 19);
                categoryOrder.put("Tempura", 20);
                categoryOrder.put("Japanese Chicken Dishes", 21);
                categoryOrder.put("Rice Bowls", 22);
                categoryOrder.put("Small Bites", 23);
                categoryOrder.put("Desserts", 24);
                categoryOrder.put("Cakes", 25);
                categoryOrder.put("Cheesecakes", 26);
                categoryOrder.put("Brownies", 27);
                categoryOrder.put("Dessert Cups", 28);

                mocks.sort((a, b) -> {
                        int orderA = categoryOrder.getOrDefault(a.getCategory(), 99);
                        int orderB = categoryOrder.getOrDefault(b.getCategory(), 99);
                        if (orderA != orderB)
                                return orderA - orderB;
                        return Integer.compare(a.getFoodId(), b.getFoodId());
                });

                return mocks;
        }

        private void addMock(List<FoodItem> list, int resId, int foodId, String name, double price, String category,
                        boolean isVeg, String description, double rating, int ratingCount, String url) {
                addMock(list, resId, foodId, name, price, category, isVeg, description, rating, ratingCount, url, false);
        }

        private void addMock(List<FoodItem> list, int resId, int foodId, String name, double price, String category,
                        boolean isVeg, String description, double rating, int ratingCount, String url, boolean isBestseller) {
                FoodItem f = new FoodItem();
                f.setFoodId(foodId);
                f.setRestaurantId(resId);
                f.setName(name);
                f.setDescription(description);
                f.setPrice(price);
                f.setCategory(category);
                f.setVegetarian(isVeg);
                f.setRating(rating);
                f.setRatingCount(ratingCount);
                f.setAvailable(true);
                f.setImageUrl(url);
                list.add(f);
        }

        public FoodItem getFoodById(int foodId) {
                Connection conn = DBConnection.getConnection();
                if (conn == null) {
                        // Better mock fallback: Search in all mock items
                        for (int i = 1; i <= 23; i++) {
                                List<FoodItem> mocks = getMockFoodItems(i);
                                for (FoodItem item : mocks) {
                                        if (item.getFoodId() == foodId) {
                                                return item;
                                        }
                                }
                        }

                        // Last resort fallback
                        FoodItem f = new FoodItem();
                        f.setFoodId(foodId);
                        f.setName("Special Dish");
                        f.setPrice(150.00);
                        return f;
                }

                String sql = "SELECT f.*, r.name as restaurant_name FROM food_item f JOIN restaurant r ON f.restaurant_id = r.restaurant_id WHERE f.food_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, foodId);
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                                return extractFoodItem(rs);
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                } finally {
                        DBConnection.closeConnection(conn);
                }
                return null;
        }

        public List<FoodItem> searchFood(String keyword) {
                List<FoodItem> items = new ArrayList<>();
                Connection conn = DBConnection.getConnection();
                if (conn == null)
                        return items;

                String sql = "SELECT f.*, r.name as restaurant_name FROM food_item f JOIN restaurant r ON f.restaurant_id = r.restaurant_id WHERE (f.name LIKE ? OR f.category LIKE ?) AND f.is_available = TRUE";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        String pattern = "%" + keyword + "%";
                        stmt.setString(1, pattern);
                        stmt.setString(2, pattern);
                        ResultSet rs = stmt.executeQuery();
                        while (rs.next()) {
                                items.add(extractFoodItem(rs));
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                } finally {
                        DBConnection.closeConnection(conn);
                }
                return items;
        }

        public List<FoodItem> getAllFoodItems() {
                List<FoodItem> items = new ArrayList<>();
                Connection conn = DBConnection.getConnection();
                if (conn == null)
                        return items;

                String sql = "SELECT f.*, r.name as restaurant_name FROM food_item f JOIN restaurant r ON f.restaurant_id = r.restaurant_id WHERE f.is_available = TRUE ORDER BY r.name, f.category";
                try (PreparedStatement stmt = conn.prepareStatement(sql);
                                ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                                items.add(extractFoodItem(rs));
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                } finally {
                        DBConnection.closeConnection(conn);
                }
                return items;
        }

        public boolean addFoodItem(FoodItem f) {
                String sql = "INSERT INTO food_item (restaurant_id, name, description, price, image_url, category, is_vegetarian) VALUES (?,?,?,?,?,?,?)";
                try (Connection conn = DBConnection.getConnection();
                                PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, f.getRestaurantId());
                        stmt.setString(2, f.getName());
                        stmt.setString(3, f.getDescription());
                        stmt.setDouble(4, f.getPrice());
                        stmt.setString(5, f.getImageUrl());
                        stmt.setString(6, f.getCategory());
                        stmt.setBoolean(7, f.isVegetarian());
                        return stmt.executeUpdate() > 0;
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return false;
        }

        public boolean updateFoodItem(FoodItem f) {
                String sql = "UPDATE food_item SET name=?, description=?, price=?, category=?, is_vegetarian=?, is_available=? WHERE food_id=?";
                try (Connection conn = DBConnection.getConnection();
                                PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, f.getName());
                        stmt.setString(2, f.getDescription());
                        stmt.setDouble(3, f.getPrice());
                        stmt.setString(4, f.getCategory());
                        stmt.setBoolean(5, f.isVegetarian());
                        stmt.setBoolean(6, f.isAvailable());
                        stmt.setInt(7, f.getFoodId());
                        return stmt.executeUpdate() > 0;
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return false;
        }

        public boolean deleteFoodItem(int foodId) {
                String sql = "DELETE FROM food_item WHERE food_id = ?";
                try (Connection conn = DBConnection.getConnection();
                                PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, foodId);
                        return stmt.executeUpdate() > 0;
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return false;
        }

        private FoodItem extractFoodItem(ResultSet rs) throws SQLException {
                FoodItem f = new FoodItem();
                f.setFoodId(rs.getInt("food_id"));
                f.setRestaurantId(rs.getInt("restaurant_id"));
                f.setRestaurantName(rs.getString("restaurant_name"));
                f.setName(rs.getString("name"));
                f.setDescription(rs.getString("description"));
                f.setPrice(rs.getDouble("price"));
                f.setImageUrl(rs.getString("image_url"));
                f.setCategory(rs.getString("category"));
                f.setVegetarian(rs.getBoolean("is_vegetarian"));
                f.setAvailable(rs.getBoolean("is_available"));
                f.setCreatedAt(rs.getTimestamp("created_at"));
                return f;
        }
}
