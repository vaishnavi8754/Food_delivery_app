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

	private static final List<FoodItem> customDishes = new ArrayList<>();

	public List<FoodItem> getFoodByRestaurant(int restaurantId) {
		String sql = "SELECT f.*, r.name as restaurant_name FROM food_item f JOIN restaurant r ON f.restaurant_id = r.restaurant_id WHERE f.restaurant_id = ? AND f.is_available = TRUE ORDER BY f.category, f.name";
		List<FoodItem> items = new ArrayList<>();
		
		// Add custom added dishes for this restaurant
		for (FoodItem f : customDishes) {
			if (f.getRestaurantId() == restaurantId) {
				items.add(f);
			}
		}

		Connection conn = DBConnection.getConnection();
		// Force mock data for specified restaurants to ensure new items/tags are visible
		if (conn == null || restaurantId == 3 || restaurantId == 7 || restaurantId == 8 || restaurantId == 16 || restaurantId == 17 || restaurantId == 18 || restaurantId == 19 || restaurantId == 22) {
			items.addAll(getMockFoodItems(restaurantId));
			return items;
		}
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
                                                "https://images.unsplash.com/photo-1586190848861-99aa4a171e90?w=400");
                                addMock(mocks, restaurantId, 103, "Corn & Cheese Burger", 109, "Veg Burgers", true,
                                                "Serves 1 | Deliciously golden corn and cheese patty with a hint of spicy herbs. (280 Kcal)",
                                                4.3, 110,
                                                "https://images.unsplash.com/photo-1586190848861-99aa4a171e90?w=400");
                                addMock(mocks, restaurantId, 104, "Paneer Tikka Burger", 129, "Veg Burgers", true,
                                                "Serves 1 | Juicy grilled paneer tikka patty with mint chutney and onion rings. (310 Kcal)",
                                                4.6, 320,
                                                "https://images.unsplash.com/photo-1520072959219-c595dc870360?w=400");
                                addMock(mocks, restaurantId, 105, "Crispy Paneer Burger", 139, "Veg Burgers", true,
                                                "Serves 1 | Chunky paneer cube breaded and fried to perfection with tandoori sauce. (340 Kcal)",
                                                4.4, 410,
                                                "https://images.unsplash.com/photo-1661529515567-dcb300f41da5?w=400");
                                addMock(mocks, restaurantId, 106, "Veggie Delight Burger", 119, "Veg Burgers", true,
                                                "Serves 1 | Packed with assorted veggies, herbs and a special blend of spices. (230 Kcal)",
                                                4.1, 85,
                                                "https://images.unsplash.com/photo-1520073201527-6b044ba2ca9f?w=400");
                                addMock(mocks, restaurantId, 107, "Spicy Mexican Veg Burger", 149, "Veg Burgers", true,
                                                "Serves 1 | Zesty burger with jalapeños, salsa and a spicy bean patty. (290 Kcal)",
                                                4.3, 195,
                                                "https://images.unsplash.com/photo-1585238341267-1cfec2046a55?w=400");
                                addMock(mocks, restaurantId, 108, "Double Patty Veg Burger", 169, "Veg Burgers", true,
                                                "Serves 1 | Two veggie patties stacked with double cheese and extra veggies. (420 Kcal)",
                                                4.5, 310,
                                                "https://images.unsplash.com/photo-1661529515593-bba89f12e8de?w=400");
                                addMock(mocks, restaurantId, 109, "Maharaja Veg Burger", 199, "Veg Burgers", true,
                                                "Serves 1 | Three buns, double patties, jalapeños and premium sauce. (480 Kcal)",
                                                4.7, 540,
                                                "https://images.unsplash.com/photo-1562634382-d41bfc15aa4a?w=400");

                                // 🍔 Burger Menu (Non-Veg)
                                addMock(mocks, restaurantId, 110, "Classic Chicken Burger", 99, "Non-Veg Burgers",
                                                false,
                                                "Serves 1 | Tender and juicy chicken patty with mayo and fresh onions. (260 Kcal)",
                                                4.3, 210,
                                                "https://images.unsplash.com/photo-1615719413546-198b25453f85?w=400");
                                addMock(mocks, restaurantId, 111, "Crispy Chicken Burger", 129, "Non-Veg Burgers",
                                                false,
                                                "Serves 1 | Extra crunchy breaded chicken breast strip with spicy pepper mayo. (310 Kcal)",
                                                4.5, 340,
                                                "https://images.unsplash.com/photo-1637710847214-f91d99669e18?w=400");
                                addMock(mocks, restaurantId, 112, "Spicy Chicken Burger", 149, "Non-Veg Burgers", false,
                                                "Serves 1 | Fiery chicken patty seasoned with secret spices and red chili flakes. (290 Kcal)",
                                                4.4, 280,
                                                "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400");
                                addMock(mocks, restaurantId, 113, "BBQ Chicken Burger", 159, "Non-Veg Burgers", false,
                                                "Serves 1 | Grilled chicken patty glazed with smoky BBQ sauce and onions. (330 Kcal)",
                                                4.6, 420,
                                                "https://images.unsplash.com/photo-1692737349870-e3bfc704ebf9?w=400");
                                addMock(mocks, restaurantId, 114, "Chicken Cheese Burger", 169, "Non-Veg Burgers",
                                                false,
                                                "Serves 1 | Classic chicken patty with a generous slice of melted cheddar. (300 Kcal)",
                                                4.5, 360,
                                                "https://images.unsplash.com/photo-1671106571674-a89083d27e60?w=400");
                                addMock(mocks, restaurantId, 115, "Chicken Tandoori Burger", 179, "Non-Veg Burgers",
                                                false,
                                                "Serves 1 | Authentic tandoori grilled chicken with spicy mint mayo. (320 Kcal)",
                                                4.7, 510,
                                                "https://images.unsplash.com/photo-1615719413546-198b25453f85?w=400");
                                addMock(mocks, restaurantId, 116, "Double Chicken Patty Burger", 199, "Non-Veg Burgers",
                                                false,
                                                "Serves 1 | 2 patties with double cheese. Double the chicken, double the fun! (510 Kcal)",
                                                4.8, 620,
                                                "https://images.unsplash.com/photo-1703575571935-058bc1a91d39?w=400");
                                addMock(mocks, restaurantId, 117, "Chicken Supreme Burger", 219, "Non-Veg Burgers",
                                                false,
                                                "Serves 1 | Large premium chicken patty with exotic veggies and house sauce. (450 Kcal)",
                                                4.7, 480,
                                                "https://images.unsplash.com/photo-1525059696034-4967a8e1dca2?w=400");
                                addMock(mocks, restaurantId, 118, "Fish Fillet Burger", 199, "Non-Veg Burgers", false,
                                                "Serves 1 | Flaky white fish fillet breaded and fried, served with tartar sauce. (280 Kcal)",
                                                4.2, 140,
                                                "https://images.unsplash.com/photo-1603064752734-4c48eff53d05?w=400");

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
                                                "https://images.unsplash.com/photo-1595854341625-f33ee10dbf94?w=400");
                                addMock(mocks, restaurantId, 211, "Pepperoni Pizza", 299, "Pizza", false,
                                                "Pepperoni slices with mozzarella cheese. (350 Kcal)", 4.6, 340,
                                                "https://images.unsplash.com/photo-1564128442383-9201fcc740eb?w=400");
                                addMock(mocks, restaurantId, 212, "Farmhouse Pizza", 279, "Pizza", true,
                                                "Onion, capsicum, mushrooms, olives. (310 Kcal)", 4.5, 230,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 213, "BBQ Chicken Pizza", 319, "Pizza", false,
                                                "Chicken with BBQ sauce and cheese. (380 Kcal)", 4.7, 410,
                                                "https://images.unsplash.com/photo-1604382354936-07c5d9983bd3?w=400");
                                addMock(mocks, restaurantId, 214, "Four Cheese Pizza", 329, "Pizza", true,
                                                "Mozzarella, parmesan, cheddar, gorgonzola. (390 Kcal)", 4.8, 520,
                                                "https://images.unsplash.com/photo-1593504049359-74330189a345?w=400");
                                addMock(mocks, restaurantId, 215, "Veggie Supreme Pizza", 289, "Pizza", true,
                                                "Loaded with vegetables and herbs. (290 Kcal)", 4.6, 310,
                                                "https://images.unsplash.com/photo-1593560708920-61dd98c46a4e?w=400");

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
                        case 3: // Spice Garden (Indian)
                                // Recommended
                                addMock(mocks, restaurantId, 3001, "Butter Chicken with Rice", 220, "Recommended", false,
                                                "Creamy tomato gravy with tender chicken pieces.", 4.8, 450,
                                                "https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?auto=format&fit=crop&w=400&q=80", true, "Customer Favorite");
                                addMock(mocks, restaurantId, 3002, "Paneer Butter Masala", 180, "Recommended", true,
                                                "Soft paneer cubes in rich buttery gravy.", 4.7, 320,
                                                "https://images.unsplash.com/photo-1567188040759-fb8a883dc6d8?auto=format&fit=crop&w=400&q=80", true, "Most Ordered");
                                addMock(mocks, restaurantId, 3003, "Chicken Biryani", 200, "Recommended", false,
                                                "Aromatic basmati rice with spiced chicken.", 4.9, 580,
                                                "https://images.unsplash.com/photo-1633945274405-b6c8069047b0?auto=format&fit=crop&w=400&q=80", true, "Customer Favorite");
                                addMock(mocks, restaurantId, 3004, "Masala Dosa", 90, "Recommended", true,
                                                "Crispy dosa filled with spiced potato masala.", 4.6, 210,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?auto=format&fit=crop&w=400&q=80", true, "Most Ordered");

                                // Main Dishes
                                addMock(mocks, restaurantId, 311, "Butter Chicken with Rice", 220, "Main Dishes", false,
                                                "Creamy tomato gravy with tender chicken pieces.", 4.8, 450,
                                                "https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?auto=format&fit=crop&w=400&q=80", false, "Customer Favorite");
                                addMock(mocks, restaurantId, 312, "Paneer Butter Masala", 180, "Main Dishes", true,
                                                "Soft paneer cubes in rich buttery gravy.", 4.7, 320,
                                                "https://images.unsplash.com/photo-1567188040759-fb8a883dc6d8?auto=format&fit=crop&w=400&q=80", false, "Most Ordered");
                                addMock(mocks, restaurantId, 313, "Chicken Biryani", 200, "Main Dishes", false,
                                                "Aromatic basmati rice with spiced chicken.", 4.9, 580,
                                                "https://images.unsplash.com/photo-1633945274405-b6c8069047b0?auto=format&fit=crop&w=400&q=80", false, "Customer Favorite");
                                addMock(mocks, restaurantId, 314, "Veg Biryani", 160, "Main Dishes", true,
                                                "Mixed vegetables cooked with basmati rice.", 4.5, 230,
                                                "https://images.unsplash.com/photo-1543353071-873f17a7a088?auto=format&fit=crop&w=400&q=80", false, "Budget Pick");
                                addMock(mocks, restaurantId, 315, "Masala Dosa", 90, "Main Dishes", true,
                                                "Crispy dosa filled with spiced potato masala.", 4.6, 210,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?auto=format&fit=crop&w=400&q=80", false, "Most Ordered");

                                // Snacks & Starters
                                addMock(mocks, restaurantId, 321, "Chicken 65", 150, "Snacks & Starters", false,
                                                "Spicy deep-fried chicken bites.", 4.6, 180,
                                                "https://images.unsplash.com/photo-1610057099443-fde8c4d50f91?auto=format&fit=crop&w=400&q=80", true, "Customer Favorite");
                                addMock(mocks, restaurantId, 322, "Paneer Tikka", 160, "Snacks & Starters", true,
                                                "Grilled paneer cubes with spices.", 4.5, 140,
                                                "https://images.unsplash.com/photo-1599487488170-d11ec9c172f0?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 323, "Veg Samosa (2 pcs)", 40, "Snacks & Starters", true,
                                                "Crispy pastry filled with potato masala.", 4.4, 310,
                                                "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=400", false, "Budget Pick");
                                addMock(mocks, restaurantId, 324, "French Fries", 80, "Snacks & Starters", true,
                                                "Golden crispy potato fries.", 4.2, 110,
                                                "https://images.unsplash.com/photo-1585109649139-366815a0d713?w=400");

                                // Quick Meals
                                addMock(mocks, restaurantId, 331, "Veg Fried Rice", 120, "Quick Meals", true,
                                                "Delicious mixed vegetable fried rice.", 4.3, 150,
                                                "https://images.unsplash.com/photo-1603133872878-684f208fb84b?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 332, "Egg Fried Rice", 130, "Quick Meals", false,
                                                "Scrambled egg and rice with spices.", 4.4, 120,
                                                "https://images.unsplash.com/photo-1603133872878-684f208fb84b?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 333, "Chicken Noodles", 140, "Quick Meals", false,
                                                "Wok-tossed noodles with chicken and veggies.", 4.5, 190,
                                                "https://images.unsplash.com/photo-1582878826629-29b7ad1cdc43?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 334, "Veg Noodles", 110, "Quick Meals", true,
                                                "Tossed noodles with fresh vegetables.", 4.2, 110,
                                                "https://images.unsplash.com/photo-1585032226651-759b368d7246?auto=format&fit=crop&w=400&q=80");

                                // Beverages
                                addMock(mocks, restaurantId, 341, "Masala Chai", 20, "Beverages", true,
                                                "Authentic Indian spiced tea.", 4.7, 420,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400", false, "Budget Pick");
                                addMock(mocks, restaurantId, 342, "Cold Coffee", 80, "Beverages", true,
                                                "Refreshing chilled coffee.", 4.5, 230,
                                                "https://images.unsplash.com/photo-1517701604599-bb29b565090c?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 343, "Fresh Lime Soda", 40, "Beverages", true,
                                                "Tangy lime soda with a kick.", 4.4, 150,
                                                "https://images.unsplash.com/photo-1621263764928-df1444c5e859?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 344, "Buttermilk", 30, "Beverages", true,
                                                "Chilled spiced yogurt drink.", 4.6, 180,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");

                                // Desserts
                                addMock(mocks, restaurantId, 351, "Gulab Jamun (2 pcs)", 40, "Desserts", true,
                                                "Soft berry-sized balls in sugar syrup.", 4.8, 380,
                                                "https://images.unsplash.com/photo-1589119908995-c6837fa14848?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 352, "Chocolate Cake Slice", 90, "Desserts", true,
                                                "Rich and decadent chocolate cake slice.", 4.7, 210,
                                                "https://images.unsplash.com/photo-1578985545062-69928b1d9587?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 353, "Vanilla Ice Cream", 60, "Desserts", true,
                                                "Classic smooth vanilla ice cream.", 4.6, 150,
                                                "https://images.unsplash.com/photo-1501443762994-82bd5dace89a?auto=format&fit=crop&w=400&q=80");
                                break;
                        case 4: // Golden Dragon (Chinese)
                                // Recommended
                                addMock(mocks, restaurantId, 401, "Chicken Lollipop", 180, "Recommended", false,
                                                "Crispy fried chicken wings tossed in spicy sauce.", 4.8, 320,
                                                "https://images.unsplash.com/photo-1567620832903-9fc6debc209f?w=400");
                                addMock(mocks, restaurantId, 402, "Veg Spring Rolls", 120, "Recommended", true,
                                                "Crispy rolls stuffed with vegetables.", 4.2, 110,
                                                "https://images.unsplash.com/photo-1734774924912-dcbb467f8599?w=400");
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
                                                "https://images.unsplash.com/photo-1598514983318-2f64f8f4796c?w=400");

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
                                                "https://images.unsplash.com/photo-1598514983318-2f64f8f4796c?w=400");

                                // Soups
                                addMock(mocks, restaurantId, 425, "Hot & Sour Soup", 110, "Soups", true,
                                                "A spicy and tangy soup with finely chopped vegetables.", 4.3, 85,
                                                "https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=400");
                                addMock(mocks, restaurantId, 426, "Sweet Corn Soup", 110, "Soups", true,
                                                "Creamy and comforting soup with sweet corn kernels.", 4.4, 75,
                                                "https://images.unsplash.com/photo-1549203438-a7696aed4dac?w=400");
                                addMock(mocks, restaurantId, 427, "Chicken Manchow Soup", 130, "Soups", false,
                                                "Spicy Indo-Chinese soup with chicken and crunchy fried noodles.", 4.6,
                                                95,
                                                "https://images.unsplash.com/photo-1582878826629-29b7ad1cdc43?w=400");
                                addMock(mocks, restaurantId, 428, "Veg Manchow Soup", 120, "Soups", true,
                                                "Spicy vegetable soup served with crispy noodles.", 4.5, 65,
                                                "https://images.unsplash.com/photo-1620147123631-7291b2b5a4f7?w=400");

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
                                                  case 5: // Urban Sushi (Japanese)
                                // Recommended
                                addMock(mocks, restaurantId, 501, "Salmon Nigiri (4 pcs)", 250, "Recommended", false,
                                                "Fresh salmon over pressed vinegar rice.", 4.9, 120,
                                                "https://images.unsplash.com/photo-1680675228874-9b9963812b7c?w=400");
                                addMock(mocks, restaurantId, 502, "California Roll (8 pcs)", 220, "Recommended", false,
                                                "Crab, avocado, and cucumber with sesame seeds.", 4.8, 95,
                                                "https://images.unsplash.com/photo-1559410545-0bdcd187e0a6?w=400");
                                addMock(mocks, restaurantId, 503, "Chicken Katsu Roll", 240, "Recommended", false,
                                                "Crispy chicken with cucumber and savory sauce in a roll.", 4.7, 150,
                                                "https://images.unsplash.com/photo-1617196034183-421b4917c92d?w=400");
                                addMock(mocks, restaurantId, 504, "Tempura Prawn Roll", 260, "Recommended", false,
                                                "Crunchy tempura prawns with avocado and spicy mayo.", 4.8, 110,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");

                                // Sushi Rolls
                                addMock(mocks, restaurantId, 505, "Veg Sushi Roll", 200, "Sushi Rolls", true,
                                                "Cucumber, avocado, and carrot in a fresh seaweed wrap.", 4.4, 140,
                                                "https://images.unsplash.com/photo-1579871494447-9811cf80d66c?w=400");
                                addMock(mocks, restaurantId, 506, "Chicken Teriyaki Roll", 240, "Sushi Rolls", false,
                                                "Succulent chicken with sweet teriyaki glaze in a roll.", 4.6, 120,
                                                "https://images.unsplash.com/photo-1559410545-0bdcd187e0a6?w=400");
                                addMock(mocks, restaurantId, 507, "Spicy Tuna Roll", 260, "Sushi Rolls", false,
                                                "Fresh tuna with spicy mayo and green onions.", 4.9, 95,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 508, "Paneer Tempura Roll", 220, "Sushi Rolls", true,
                                                "Crispy paneer tempura with cucumber and spicy sauce.", 4.5, 80,
                                                "https://images.unsplash.com/photo-1617196034183-421b4917c92d?w=400");
                                addMock(mocks, restaurantId, 509, "Crunchy Veg Roll", 210, "Sushi Rolls", true,
                                                "Vegetable roll topped with crispy tempura flakes.", 4.3, 110,
                                                "https://images.unsplash.com/photo-1579871494447-9811cf80d66c?w=400");

                                // Tempura
                                addMock(mocks, restaurantId, 510, "Prawn Tempura (4 pcs)", 280, "Tempura", false,
                                                "Lightly battered and deep-fried prawns served with dip.", 4.8, 140,
                                                "https://images.unsplash.com/photo-1651328236028-77b585d3f4e5?w=400");
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
                                                "https://images.unsplash.com/photo-1620147123631-7291b2b5a4f7?w=400");
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
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
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
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 525, "Veg Gyoza", 200, "Small Bites", true,
                                                "Healthy vegetable steamed or fried dumplings.", 4.5, 110,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 526, "Edamame Beans", 180, "Small Bites", true,
                                                "Steamed young soybeans with a sprinkle of sea salt.", 4.2, 85,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");

                                // Desserts
                                addMock(mocks, restaurantId, 527, "Matcha Ice Cream", 180, "Desserts", true,
                                                "Traditional Japanese green tea flavored ice cream.", 4.6, 120,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 528, "Mochi Ice Cream", 200, "Desserts", true,
                                                "Soft rice cake balls with an ice cream center.", 4.8, 95,
                                                "https://images.unsplash.com/photo-1706350091276-ba994c35cd99?w=400");
                                addMock(mocks, restaurantId, 529, "Chocolate Dorayaki", 190, "Desserts", true,
                                                "Japanese pancakes filled with rich chocolate.", 4.5, 80,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");

                                // Drinks
                                addMock(mocks, restaurantId, 530, "Japanese Green Tea", 120, "Drinks", true,
                                                "Authentic hot brewed matcha green tea.", 4.4, 65,
                                                "https://images.unsplash.com/photo-1524914415733-3eb400768a13?w=400");
                                addMock(mocks, restaurantId, 531, "Lemon Iced Tea", 120, "Drinks", true,
                                                "Chilled and refreshing citrus infused tea.", 4.3, 85,
                                                "https://images.unsplash.com/photo-1556679343-c7306c1976bc?w=400");
                                addMock(mocks, restaurantId, 532, "Cold Coffee", 140, "Drinks", true,
                                                "Creamy chilled coffee with a hint of vanilla.", 4.5, 110,
                                                "https://images.unsplash.com/photo-1461023058943-07fcbe16d735?w=400");
                                addMock(mocks, restaurantId, 533, "Chocolate Milkshake", 150, "Drinks", true,
                                                "Indulgent chocolate shake topped with whipped cream.", 4.6, 130,
                                                "https://images.unsplash.com/photo-1572490122747-3968b75cc699?w=400");
                                                 case 6: // Taco Town (Mexican)
                                addMock(mocks, restaurantId, 601, "Street Tacos (3 pcs)", 120, "Recommended", false,
                                                "Authentic street-style tacos with onion, cilantro, and salsa.", 4.4, 150,
                                                "https://images.unsplash.com/photo-1650939959602-d8b3e9d50083?w=400");
                                addMock(mocks, restaurantId, 602, "Burrito Bowl", 130, "Recommended", true,
                                                "Rice, beans, salsa, and fresh vegetables in a bowl.", 4.3, 210,
                                                "https://images.unsplash.com/photo-1636600631971-f8e7dabb8c32?w=400");
                                addMock(mocks, restaurantId, 603, "Veg Loaded Nachos", 100, "Recommended", true,
                                                "Crunchy nachos topped with cheese, beans, and jalapeños.", 4.4, 170,
                                                "https://images.unsplash.com/photo-1669624272709-c5b91f66b1b7?w=400");
                                addMock(mocks, restaurantId, 604, "Chicken Tacos (3 pcs)", 140, "Recommended", false,
                                                "Soft tacos filled with grilled chicken and salsa.", 4.5, 190,
                                                "https://images.unsplash.com/photo-1687483137128-de6b05b80901?w=400");
                                addMock(mocks, restaurantId, 605, "Paneer Tacos (3 pcs)", 130, "Tacos", true,
                                                "Soft tortillas stuffed with grilled paneer and veggies.", 4.5, 160,
                                                "https://images.unsplash.com/photo-1564834724105-918b73d1b9e0?w=400");
                                addMock(mocks, restaurantId, 606, "Spicy Bean Tacos (3 pcs)", 110, "Tacos", true,
                                                "Mexican beans with salsa and cheese.", 4.3, 130,
                                                "https://images.unsplash.com/photo-1593759608136-45eb2ad9507d?w=400");
                                addMock(mocks, restaurantId, 607, "Crispy Veg Tacos (3 pcs)", 115, "Tacos", true,
                                                "Crunchy taco shells filled with seasoned vegetables.", 4.4, 140,
                                                "https://images.unsplash.com/photo-1726514730004-f3a07597c7b0?w=400");
                                addMock(mocks, restaurantId, 608, "Veg Burrito", 130, "Burritos", true,
                                                "Soft tortilla wrap with rice, beans, and grilled veggies.", 4.4, 150,
                                                "https://images.unsplash.com/photo-1717429863975-4d45cbdb3d42?w=400");
                                addMock(mocks, restaurantId, 609, "Chicken Burrito", 150, "Burritos", false,
                                                "Chicken, rice, beans, and salsa wrapped in a tortilla.", 4.5, 180,
                                                "https://images.unsplash.com/photo-1730878423239-0fd430bbac37?w=400");
                                addMock(mocks, restaurantId, 610, "Cheese Quesadilla", 95, "Quesadillas", true,
                                                "Grilled tortilla with melted cheese.", 4.4, 150,
                                                "https://images.unsplash.com/photo-1707387312816-112e5ca3c145?w=400");
                                addMock(mocks, restaurantId, 611, "Veg Quesadilla", 110, "Quesadillas", true,
                                                "Tortilla filled with vegetables and cheese.", 4.5, 160,
                                                "https://images.unsplash.com/photo-1541544741938-0af808871cc0?w=400");
                                addMock(mocks, restaurantId, 612, "Classic Nachos", 90, "Snacks", true,
                                                "Crispy tortilla chips with salsa dip.", 4.3, 140,
                                                "https://images.unsplash.com/photo-1748765968997-ba9bae9cfd7b?w=400");
                                addMock(mocks, restaurantId, 613, "Jalapeño Cheese Poppers", 120, "Snacks", true,
                                                "Crispy fried jalapeños stuffed with cheese.", 4.5, 120,
                                                "https://images.unsplash.com/photo-1734774924912-dcbb467f8599?w=400");
                                addMock(mocks, restaurantId, 614, "Lemon Mint Cooler", 60, "Drinks", true,
                                                "Refreshing lemon and mint drink.", 4.4, 180,
                                                "https://images.unsplash.com/photo-1772723822838-915561797031?w=400");
                                addMock(mocks, restaurantId, 615, "Iced Tea", 55, "Drinks", true,
                                                "Chilled tea with lemon flavor.", 4.3, 160,
                                                "https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?w=400");
                                break;
                        case 7: // The French Bistro
                                addMock(mocks, restaurantId, 7001, "Butter Chicken with Rice", 220, "Recommended", false,
                                                "Creamy tomato gravy with tender chicken pieces.", 4.8, 450,
                                                "https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?auto=format&fit=crop&w=400&q=80", true, "Customer Favorite");
                                addMock(mocks, restaurantId, 7002, "Paneer Butter Masala", 180, "Recommended", true,
                                                "Soft paneer cubes in rich buttery gravy.", 4.7, 320,
                                                "https://images.unsplash.com/photo-1567188040759-fb8a883dc6d8?auto=format&fit=crop&w=400&q=80", true, "Most Ordered");
                                addMock(mocks, restaurantId, 7003, "Chicken Biryani", 200, "Recommended", false,
                                                "Aromatic basmati rice with spiced chicken.", 4.9, 580,
                                                "https://images.unsplash.com/photo-1633945274405-b6c8069047b0?auto=format&fit=crop&w=400&q=80", true, "Customer Favorite");
                                addMock(mocks, restaurantId, 7004, "Masala Dosa", 90, "Recommended", true,
                                                "Crispy dosa filled with spiced potato masala.", 4.6, 210,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?auto=format&fit=crop&w=400&q=80", true, "Most Ordered");

                                // French Originals
                                addMock(mocks, restaurantId, 701, "Beef Bourguignon", 1850, "Recommended", false,
                                                "Slow-cooked beef in red wine sauce. (520 Kcal)", 4.8, 85,
                                                "https://images.unsplash.com/photo-1548869206-93b036288d7e?w=400");
                                addMock(mocks, restaurantId, 702, "French Onion Soup", 950, "Recommended", false,
                                                "Classic broth with melted cheese and bread. (210 Kcal)", 4.6, 65,
                                                "https://images.unsplash.com/photo-1549203438-a7696aed4dac?w=400");

                                // Main Dishes
                                addMock(mocks, restaurantId, 711, "Butter Chicken with Rice", 220, "Main Dishes", false,
                                                "Creamy tomato gravy with tender chicken pieces.", 4.8, 450,
                                                "https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?auto=format&fit=crop&w=400&q=80", false, "Customer Favorite");
                                addMock(mocks, restaurantId, 712, "Paneer Butter Masala", 180, "Main Dishes", true,
                                                "Soft paneer cubes in rich buttery gravy.", 4.7, 320,
                                                "https://images.unsplash.com/photo-1567188040759-fb8a883dc6d8?auto=format&fit=crop&w=400&q=80", false, "Most Ordered");
                                addMock(mocks, restaurantId, 713, "Chicken Biryani", 200, "Main Dishes", false,
                                                "Aromatic basmati rice with spiced chicken.", 4.9, 580,
                                                "https://images.unsplash.com/photo-1633945274405-b6c8069047b0?auto=format&fit=crop&w=400&q=80", false, "Customer Favorite");
                                addMock(mocks, restaurantId, 714, "Veg Biryani", 160, "Main Dishes", true,
                                                "Mixed vegetables cooked with basmati rice.", 4.5, 230,
                                                "https://images.unsplash.com/photo-1543353071-873f17a7a088?auto=format&fit=crop&w=400&q=80", false, "Budget Pick");
                                addMock(mocks, restaurantId, 715, "Masala Dosa", 90, "Main Dishes", true,
                                                "Crispy dosa filled with spiced potato masala.", 4.6, 210,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?auto=format&fit=crop&w=400&q=80", false, "Most Ordered");

                                // Snacks & Starters
                                addMock(mocks, restaurantId, 721, "Chicken 65", 150, "Snacks & Starters", false,
                                                "Spicy deep-fried chicken bites.", 4.6, 180,
                                                "https://images.unsplash.com/photo-1610057099443-fde8c4d50f91?auto=format&fit=crop&w=400&q=80", true, "Customer Favorite");
                                addMock(mocks, restaurantId, 722, "Paneer Tikka", 160, "Snacks & Starters", true,
                                                "Grilled paneer cubes with spices.", 4.5, 140,
                                                "https://images.unsplash.com/photo-1599487488170-d11ec9c172f0?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 723, "Veg Samosa (2 pcs)", 40, "Snacks & Starters", true,
                                                "Crispy pastry filled with potato masala.", 4.4, 310,
                                                "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=400", false, "Budget Pick");
                                addMock(mocks, restaurantId, 724, "French Fries", 80, "Snacks & Starters", true,
                                                "Golden crispy potato fries.", 4.2, 110,
                                                "https://images.unsplash.com/photo-1585109649139-366815a0d713?w=400");

                                // Quick Meals
                                addMock(mocks, restaurantId, 731, "Veg Fried Rice", 120, "Quick Meals", true,
                                                "Delicious mixed vegetable fried rice.", 4.3, 150,
                                                "https://images.unsplash.com/photo-1603133872878-684f208fb84b?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 732, "Egg Fried Rice", 130, "Quick Meals", false,
                                                "Scrambled egg and rice with spices.", 4.4, 120,
                                                "https://images.unsplash.com/photo-1603133872878-684f208fb84b?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 733, "Chicken Noodles", 140, "Quick Meals", false,
                                                "Wok-tossed noodles with chicken and veggies.", 4.5, 190,
                                                "https://images.unsplash.com/photo-1582878826629-29b7ad1cdc43?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 734, "Veg Noodles", 110, "Quick Meals", true,
                                                "Tossed noodles with fresh vegetables.", 4.2, 110,
                                                "https://images.unsplash.com/photo-1585032226651-759b368d7246?auto=format&fit=crop&w=400&q=80");

                                // Beverages
                                addMock(mocks, restaurantId, 741, "Masala Chai", 20, "Beverages", true,
                                                "Authentic Indian spiced tea.", 4.7, 420,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400", false, "Budget Pick");
                                addMock(mocks, restaurantId, 742, "Cold Coffee", 80, "Beverages", true,
                                                "Refreshing chilled coffee.", 4.5, 230,
                                                "https://images.unsplash.com/photo-1517701604599-bb29b565090c?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 743, "Fresh Lime Soda", 40, "Beverages", true,
                                                "Tangy lime soda with a kick.", 4.4, 150,
                                                "https://images.unsplash.com/photo-1621263764928-df1444c5e859?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 744, "Buttermilk", 30, "Beverages", true,
                                                "Chilled spiced yogurt drink.", 4.6, 180,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");

                                // Desserts
                                addMock(mocks, restaurantId, 751, "Gulab Jamun (2 pcs)", 40, "Desserts", true,
                                                "Soft berry-sized balls in sugar syrup.", 4.8, 380,
                                                "https://images.unsplash.com/photo-1589119908995-c6837fa14848?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 752, "Chocolate Cake Slice", 90, "Desserts", true,
                                                "Rich and decadent chocolate cake slice.", 4.7, 210,
                                                "https://images.unsplash.com/photo-1578985545062-69928b1d9587?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 753, "Vanilla Ice Cream", 60, "Desserts", true,
                                                "Classic smooth vanilla ice cream.", 4.6, 150,
                                                "https://images.unsplash.com/photo-1501443762994-82bd5dace89a?auto=format&fit=crop&w=400&q=80");

                                // French Dessert
                                addMock(mocks, restaurantId, 703, "Crème Brûlée", 650, "Desserts", true,
                                                "Rich custard with a burnt sugar top. (350 Kcal)", 4.7, 120,
                                                "https://images.unsplash.com/photo-1676300184943-09b2a08319a3?w=400");
                                break;
                        case 8: // Steakhouse Prime
                                // Recommended
                                addMock(mocks, restaurantId, 8001, "Ribeye Steak (12oz)", 2200, "Recommended", false,
                                                "Premium dry-aged steak grilled to order. (650 Kcal)", 4.9, 310,
                                                "https://images.unsplash.com/photo-1546964124-0cce460f38ef?auto=format&fit=crop&w=400&q=80", true, "Customer Favorite");
                                addMock(mocks, restaurantId, 8002, "Chicken Burger", 180, "Recommended", false,
                                                "Juicy grilled chicken patty with lettuce and mayo. (420 Kcal)", 4.6, 210,
                                                "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&w=400&q=80", true, "Most Ordered");
                                addMock(mocks, restaurantId, 8003, "Grilled Chicken Steak", 280, "Recommended", false,
                                                "Tender grilled chicken steak with pepper sauce. (420 Kcal)", 4.6, 170,
                                                "https://images.unsplash.com/photo-1598515214211-89d3c73ae83b?auto=format&fit=crop&w=400&q=80", true, "Most Ordered");

                                // Main Course
                                addMock(mocks, restaurantId, 801, "Ribeye Steak (12oz)", 2200, "Main Course", false,
                                                "Premium dry-aged steak grilled to order. (650 Kcal)", 4.9, 310,
                                                "https://images.unsplash.com/photo-1546964124-0cce460f38ef?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 810, "Grilled Chicken Steak", 280, "Main Course", false,
                                                "Tender grilled chicken steak with pepper sauce. (420 Kcal)", 4.6, 170,
                                                "https://images.unsplash.com/photo-1598515214211-89d3c73ae83b?auto=format&fit=crop&w=400&q=80", false, "Most Ordered");

                                // Burgers
                                addMock(mocks, restaurantId, 804, "Chicken Burger", 180, "Burgers", false,
                                                "Juicy grilled chicken patty with lettuce and mayo. (420 Kcal)", 4.6, 210,
                                                "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&w=400&q=80", false, "Most Ordered");
                                addMock(mocks, restaurantId, 805, "Veg Cheese Burger", 150, "Burgers", true,
                                                "Crispy veg patty with melted cheese and fresh veggies. (380 Kcal)", 4.4, 180,
                                                "https://images.unsplash.com/photo-1550547660-d9450f859349?auto=format&fit=crop&w=400&q=80", false, "Budget Pick");

                                // Sandwiches & Wraps
                                addMock(mocks, restaurantId, 808, "Grilled Chicken Sandwich", 200, "Sandwiches & Wraps", false,
                                                "Grilled chicken with cheese and vegetables. (390 Kcal)", 4.5, 160,
                                                "https://images.unsplash.com/photo-1525059696034-4967a8e1dca2?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 809, "Veg Sandwich", 120, "Sandwiches & Wraps", true,
                                                "Fresh vegetables with butter toasted bread. (260 Kcal)", 4.3, 140,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 814, "Chicken Wrap", 170, "Sandwiches & Wraps", false,
                                                "Soft wrap filled with grilled chicken and veggies. (350 Kcal)", 4.5, 150,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 815, "Paneer Wrap", 160, "Sandwiches & Wraps", true,
                                                "Grilled paneer with spicy sauce in soft wrap. (340 Kcal)", 4.4, 130,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");

                                // Sides & Starters
                                addMock(mocks, restaurantId, 806, "French Fries", 120, "Sides & Starters", true,
                                                "Golden crispy potato fries with seasoning. (300 Kcal)", 4.5, 200,
                                                "https://images.unsplash.com/photo-1585109649139-366815a0d713?w=400");
                                addMock(mocks, restaurantId, 807, "Chicken Wings (6 pcs)", 220, "Sides & Starters", false,
                                                "Spicy crispy wings with BBQ sauce. (450 Kcal)", 4.7, 240,
                                                "https://images.unsplash.com/photo-1527477396000-e27163b481c2?auto=format&fit=crop&w=400&q=80", false, "Customer Favorite");
                                addMock(mocks, restaurantId, 811, "Mashed Potatoes", 150, "Sides & Starters", true,
                                                "Creamy mashed potatoes with butter and garlic. (180 Kcal)", 4.3, 150,
                                                "https://images.unsplash.com/photo-1621330716555-5cad596c4562?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 812, "Chicken Nuggets (8 pcs)", 190, "Sides & Starters", false,
                                                "Crispy fried chicken nuggets with dip. (400 Kcal)", 4.6, 210,
                                                "https://images.unsplash.com/photo-1562967914-608f82629710?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 816, "Garlic Bread", 110, "Sides & Starters", true,
                                                "Toasted bread with garlic butter and herbs. (210 Kcal)", 4.3, 140,
                                                "https://images.unsplash.com/photo-1761344788266-5f6957aeea33?auto=format&fit=crop&w=400&q=80");

                                // Salads
                                addMock(mocks, restaurantId, 813, "Veg Caesar Salad", 160, "Salads", true,
                                                "Fresh lettuce, croutons and creamy dressing. (220 Kcal)", 4.2, 110,
                                                "https://images.unsplash.com/photo-1550304943-4f24f54ddde9?auto=format&fit=crop&w=400&q=80");

                                // Beverages
                                addMock(mocks, restaurantId, 817, "Cold Coffee", 90, "Beverages", true,
                                                "Chilled coffee blended with milk and ice cream.", 4.5, 120,
                                                "https://images.unsplash.com/photo-1517701604599-bb29b565090c?auto=format&fit=crop&w=400&q=80");

                                // Desserts
                                addMock(mocks, restaurantId, 818, "Brownie with Ice Cream", 130, "Desserts", true,
                                                "Warm chocolate brownie served with vanilla ice cream.", 4.8, 160,
                                                "https://images.unsplash.com/photo-1606313564200-e75d5e30476c?auto=format&fit=crop&w=400&q=80");

                                // Add-ons
                                addMock(mocks, restaurantId, 803, "Red Wine Sauce", 150, "Add-ons", false,
                                                "Rich reduction sauce for steaks. (45 Kcal)", 4.5, 90,
                                                "https://images.unsplash.com/photo-1726946972127-941225b132f2?auto=format&fit=crop&w=400&q=80");
                                break;
                        case 9: // Healthy Greens
                                addMock(mocks, restaurantId, 901, "Quinoa Veg Bowl", 95, "Healthy Bowls", true,
                                                "Simple quinoa bowl with mixed vegetables and light seasoning.", 4.5, 210,
                                                "https://images.unsplash.com/photo-1623428187969-5da2dcea5ebf?w=400");
                                addMock(mocks, restaurantId, 902, "Avocado Toast", 80, "Snacks", true,
                                                "Whole wheat toast topped with mashed avocado and chili flakes.", 4.4, 180,
                                                "https://images.unsplash.com/photo-1613769049987-b31b641f25b1?w=400");
                                addMock(mocks, restaurantId, 903, "Sprouts Energy Bowl", 70, "Healthy Bowls", true,
                                                "Protein-rich sprouts with cucumber, onion, and lemon dressing.", 4.6, 150,
                                                "https://images.unsplash.com/photo-1622732777601-e744c3401d44?w=400");
                                addMock(mocks, restaurantId, 904, "Veg Protein Salad", 85, "Salads", true,
                                                "Chickpeas, carrots, and fresh greens tossed with herbs.", 4.5, 200,
                                                "https://images.unsplash.com/photo-1688807462845-a06bc0abcadb?w=400");
                                addMock(mocks, restaurantId, 905, "Fresh Garden Salad", 60, "Salads", true,
                                                "Cucumber, tomato, carrot, and lettuce with lemon dressing.", 4.3, 170,
                                                "https://images.unsplash.com/photo-1605291535126-2d71fea483c1?w=400");
                                addMock(mocks, restaurantId, 906, "Chickpea Salad", 75, "Salads", true,
                                                "Boiled chickpeas mixed with onion, tomato, and spices.", 4.4, 160,
                                                "https://images.unsplash.com/photo-1568803776598-57e3cf2f90fc?w=400");
                                addMock(mocks, restaurantId, 907, "Corn & Veg Salad", 70, "Salads", true,
                                                "Sweet corn tossed with capsicum and light seasoning.", 4.5, 140,
                                                "https://images.unsplash.com/photo-1768716574889-e9af8e7f7ae8?w=400");
                                addMock(mocks, restaurantId, 908, "Brown Rice Veg Bowl", 90, "Healthy Bowls", true,
                                                "Brown rice served with sautéed vegetables.", 4.5, 190,
                                                "https://images.unsplash.com/photo-1666819691822-29a09f0992e5?w=400");
                                addMock(mocks, restaurantId, 909, "Tofu Veg Bowl", 110, "Healthy Bowls", true,
                                                "Grilled tofu with fresh veggies and light soy dressing.", 4.6, 130,
                                                "https://images.unsplash.com/photo-1763000215238-38350d3e41ac?w=400");
                                addMock(mocks, restaurantId, 910, "Green Detox Bowl", 85, "Healthy Bowls", true,
                                                "Spinach, sprouts, cucumber, and seeds mix.", 4.4, 150,
                                                "https://images.unsplash.com/photo-1622790176973-2c27952176fe?w=400");
                                addMock(mocks, restaurantId, 911, "Banana Peanut Smoothie", 65, "Smoothies & Drinks", true,
                                                "Banana blended with milk and peanut butter.", 4.6, 210,
                                                "https://images.unsplash.com/photo-1685967836529-b0e8d6938227?w=400");
                                addMock(mocks, restaurantId, 912, "Mango Smoothie", 70, "Smoothies & Drinks", true,
                                                "Fresh mango blended with yogurt.", 4.5, 190,
                                                "https://images.unsplash.com/photo-1623065422902-30a2d299bbe4?w=400");
                                addMock(mocks, restaurantId, 913, "Green Detox Juice", 60, "Smoothies & Drinks", true,
                                                "Spinach, apple, and lemon juice.", 4.4, 160,
                                                "https://images.unsplash.com/photo-1610622930110-3c076902312a?w=400");
                                addMock(mocks, restaurantId, 914, "Sweet Potato Fries", 75, "Snacks", true,
                                                "Lightly baked sweet potato fries.", 4.5, 200,
                                                "https://images.unsplash.com/photo-1598679253544-2c97992403ea?w=400");
                                addMock(mocks, restaurantId, 915, "Multigrain Veg Sandwich", 80, "Snacks", true,
                                                "Whole grain sandwich filled with fresh vegetables.", 4.4, 180,
                                                "https://images.unsplash.com/photo-1634632071708-68d98ca65f04?w=400");
                                break;
                        default:
                                addMock(mocks, restaurantId, 999, "Special Dish", 10.00, "Recommended", true,
                                                "Today's special creation from our chef. (250 Kcal)", 4.5, 50,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                break;
                        case 10: // Sangeetha Veg Restaurant
                                addMock(mocks, restaurantId, 1001, "Plain Dosa", 70, "Breakfast", true,
                                                "Thin and crispy fermented rice crepe", 4.5, 120,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
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
                                                320, "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 1006, "Bisibelebath", 120, "Lunch", true,
                                                "Spicy lentil rice with vegetables and ghee", 4.4, 180,
                                                "https://images.unsplash.com/photo-1645177628172-a94c1f96e6db?w=400");
                                addMock(mocks, restaurantId, 1007, "Curd Rice", 90, "Lunch", true,
                                                "Comforting rice mixed with fresh yogurt and tempered spices", 4.3, 110,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
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
                                                "https://images.unsplash.com/photo-1549203438-a7696aed4dac?w=400");
                                break;
                        case 11: // Anjappar Chettinad Restaurant
                                addMock(mocks, restaurantId, 1101, "Chicken 65", 210, "Snacks", false,
                                                "Spicy deep-fried chicken cubes with curry leaves", 4.6, 320,
                                                "https://images.unsplash.com/photo-1765360024320-b2ab819c6f75?w=400");
                                addMock(mocks, restaurantId, 1102, "Chettinad Chicken Masala", 280, "Dinner", false,
                                                "Traditional spicy chicken gravy with roasted spices", 4.7, 240,
                                                "https://images.unsplash.com/photo-1764304733301-3a9f335f0c67?w=400");
                                addMock(mocks, restaurantId, 1103, "Mutton Sukka", 320, "Dinner", false,
                                                "Dry-roasted tender mutton with Chettinad masalas", 4.8, 180,
                                                "https://images.unsplash.com/photo-1596797038530-2c107229654b?w=400");
                                addMock(mocks, restaurantId, 1104, "Chettinad Chicken Biryani", 260, "Biryani", false,
                                                "Fragrant seeraga samba rice cooked with chicken", 4.5, 410,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 1105, "Egg Veechu Parotto", 110, "Tiffin", false,
                                                "Thin multi-layered parotto stuffed with egg", 4.4, 95,
                                                "https://images.unsplash.com/photo-1626777552726-4a6b54c97e46?w=400");
                                addMock(mocks, restaurantId, 1106, "Vanjaram Fish Fry", 350, "Lunch", false,
                                                "King fish steak marinated and tawa fried", 4.9, 210,
                                                "https://images.unsplash.com/photo-1665401015549-712c0dc5ef85?w=400");
                                addMock(mocks, restaurantId, 1107, "Idiyappam (3 pcs)", 70, "Breakfast", true,
                                                "Steamed string hoppers", 4.3, 130,
                                                "https://images.unsplash.com/photo-1569723650154-b787178cc880?w=400");
                                addMock(mocks, restaurantId, 1108, "Chicken Salna", 140, "Tiffin", false,
                                                "Spicy watery gravy best served with parotta", 4.4, 110,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 1109, "Mutton Paya", 240, "Breakfast", false,
                                                "Traditional trotters stew served with Idiyappam", 4.6, 85,
                                                "https://images.unsplash.com/photo-1624765660252-05bb5ee87aba?w=400");
                                addMock(mocks, restaurantId, 1110, "Nannari Sarbat", 60, "Beverages", true,
                                                "Refreshing root extract syrup with lemon", 4.2, 55,
                                                "https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?w=400");
                                addMock(mocks, restaurantId, 1111, "Beetroot Halwa", 95, "Desserts", true,
                                                "Sweet halwa made with grated beetroot and ghee", 4.5, 75,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                break;
                        case 12: // Nagarjuna Chimney
                                addMock(mocks, restaurantId, 1201, "Andhra Veg Meals", 210, "Lunch", true,
                                                "Spicy Andhra thali featuring gunpowder and ghee", 4.8, 540,
                                                "https://images.unsplash.com/photo-1645177628172-a94c1f96e6db?w=400");
                                addMock(mocks, restaurantId, 1202, "Nagarjuna Chicken Roast", 240, "Snacks", false,
                                                "Iconic spicy and dry chicken fry", 4.7, 320,
                                                "https://images.unsplash.com/photo-1626777552726-4a6b54c97e46?w=400");
                                addMock(mocks, restaurantId, 1203, "Andhra Chicken Biryani", 290, "Biryani", false,
                                                "Spicy hyderabadi style long grain rice biryani", 4.6, 450,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 1204, "Gongura Mutton", 330, "Lunch", false,
                                                "Succulent mutton cooked with tangy sorrel leaves", 4.9, 180,
                                                "https://images.unsplash.com/photo-1544025162-d76694265947?w=400");
                                addMock(mocks, restaurantId, 1205, "Pesarattu Upma", 130, "Breakfast", true,
                                                "Moong dal dosa stuffed with rava upma", 4.5, 110,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 1206, "Chicken Guntur", 280, "Dinner", false,
                                                "Extra spicy chicken curry from Guntur", 4.4, 150,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 1207, "Ghee Roast Dosa", 110, "Breakfast", true,
                                                "Paper thin dosa roasted in pure ghee", 4.7, 320,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 1208, "Fresh Lime Soda", 75, "Beverages", true,
                                                "Fizzy lemon drink, sweet or salted", 4.3, 180,
                                                "https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?w=400");
                                addMock(mocks, restaurantId, 1209, "Apricot Delight", 160, "Desserts", true,
                                                "Stewed apricots served with fresh cream", 4.8, 95,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                break;
                        case 13: // Malabar Cafe
                                // Beverages
                                addMock(mocks, restaurantId, 1301, "Filter Coffee", 20, "Beverages", true,
                                                "Strong South Indian degree coffee", 4.8, 120,
                                                "https://images.unsplash.com/photo-1517701604599-bb29b565090c?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1302, "Tea", 15, "Beverages", true,
                                                "Regular milk tea", 4.5, 90,
                                                "https://images.unsplash.com/photo-1619581073186-5b4ae1b0caad?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1303, "Strong Tea", 20, "Beverages", true,
                                                "Strong milk tea", 4.6, 95,
                                                "https://images.unsplash.com/photo-1564890369478-c89ca6d9cde9?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1304, "Sukku Coffee (Herbal)", 25, "Beverages", true,
                                                "Dry ginger herbal drink", 4.4, 50,
                                                "https://images.unsplash.com/photo-1514432324607-a09d9b4aefdd?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1305, "Boost / Horlicks", 30, "Beverages", true,
                                                "Hot milk with Boost or Horlicks", 4.3, 110,
                                                "https://images.unsplash.com/photo-1514432324607-a09d9b4aefdd?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1306, "Badam Milk", 40, "Beverages", true,
                                                "Warm milk with almond powder", 4.5, 120,
                                                "https://images.unsplash.com/photo-1517701550927-30cf4ba1dba5?auto=format&fit=crop&w=400&q=80");

                                // Snacks (Café Style)
                                addMock(mocks, restaurantId, 1307, "Veg Sandwich", 50, "Snacks", true,
                                                "Fresh vegetables in sandwich", 4.4, 150,
                                                "https://images.unsplash.com/photo-1528735602780-2552fd46c7af?w=400");
                                addMock(mocks, restaurantId, 1308, "Bread Omelette", 40, "Snacks", false,
                                                "Bread cooked with omelette", 4.6, 180,
                                                "https://images.unsplash.com/photo-1525059696034-4967a8e1dca2?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1309, "Egg Sandwich", 60, "Snacks", false,
                                                "Sandwich stuffed with egg", 4.5, 210,
                                                "https://images.unsplash.com/photo-1634632071708-68d98ca65f04?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1310, "Samosa (2 pcs)", 30, "Snacks", true,
                                                "Crispy deep fried pastry", 4.3, 200,
                                                "https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1311, "Bajji (Chilli / Potato)", 25, "Snacks", true,
                                                "Crispy fried bajjis", 4.5, 180,
                                                "https://images.unsplash.com/photo-1626074353765-517a681e40be?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1312, "Bonda", 25, "Snacks", true,
                                                "Deep fried round snack", 4.4, 170,
                                                "https://images.unsplash.com/photo-1626074353765-517a681e40be?auto=format&fit=crop&w=400&q=80");

                                // Light Sweet / Bakery
                                addMock(mocks, restaurantId, 1313, "Tea Cake Slice", 40, "Desserts", true,
                                                "Soft slice of tea cake", 4.2, 110,
                                                "https://images.unsplash.com/photo-1519869325930-281384150729?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1314, "Biscuit Pack", 20, "Desserts", true,
                                                "Pack of crisp biscuits", 4.3, 80,
                                                "https://images.unsplash.com/photo-1555507036-ab1f4038808a?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1315, "Rusk (2 pcs)", 15, "Desserts", true,
                                                "Crunchy rusk pieces", 4.4, 60,
                                                "https://images.unsplash.com/photo-1761344788266-5f6957aeea33?auto=format&fit=crop&w=400&q=80");

                                // Lunch
                                addMock(mocks, restaurantId, 1316, "Sambar Rice", 60, "Lunch", true,
                                                "Rice mixed with sambar", 4.5, 250,
                                                "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=400");
                                addMock(mocks, restaurantId, 1317, "Curd Rice", 50, "Lunch", true,
                                                "Cooling curd rice with pickle", 4.6, 210,
                                                "https://images.unsplash.com/photo-1516714435131-44d6b64dc6a2?w=400");
                                addMock(mocks, restaurantId, 1318, "Lemon Rice", 50, "Lunch", true,
                                                "Tangy flavored rice", 4.4, 230,
                                                "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1319, "Tomato Rice", 60, "Lunch", true,
                                                "Spiced tomato rice", 4.4, 240,
                                                "https://images.unsplash.com/photo-1645177628172-a94c1f96e6db?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1320, "Veg Meals (Mini)", 120, "Lunch", true,
                                                "Rice + sambar + rasam + poriyal", 4.7, 450,
                                                "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=400");

                                // Dinner / Tiffin
                                addMock(mocks, restaurantId, 1321, "Plain Dosa", 30, "Tiffin", true,
                                                "Thin rice crepe", 4.5, 120,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=400");
                                addMock(mocks, restaurantId, 1322, "Kal Dosa", 50, "Tiffin", true,
                                                "Soft thick dosa", 4.6, 150,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=400");
                                addMock(mocks, restaurantId, 1323, "Masala Dosa", 80, "Tiffin", true,
                                                "Dosa stuffed with potato masala", 4.7, 210,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=400");
                                addMock(mocks, restaurantId, 1324, "Onion Uttapam", 70, "Tiffin", true,
                                                "Thick dosa topped with onions", 4.5, 180,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=400");
                                addMock(mocks, restaurantId, 1325, "Ragi Dosa", 60, "Tiffin", true,
                                                "Healthy finger millet dosa", 4.4, 110,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=400");
                                break;
                        case 14: // Vidyarthi Bhavan
                                addMock(mocks, restaurantId, 1401, "Thatte Idli (2 pcs)", 25, "Breakfast", true,
                                                "Large soft idli served with chutney and sambar", 4.6, 250,
                                                "https://images.unsplash.com/photo-1589302168068-964664d93dc0?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1402, "Khara Bath", 30, "Breakfast", true,
                                                "Spicy semolina upma with vegetables", 4.5, 300,
                                                "https://images.unsplash.com/photo-1663446783008-c34241daff22?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1403, "Chow Chow Bath", 50, "Breakfast", true,
                                                "Combination of khara bath and kesari bath", 4.7, 450,
                                                "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1404, "Plain Dosa", 40, "Breakfast", true,
                                                "Crispy dosa served with chutney and sambar", 4.5, 200,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1405, "Set Dosa (2 pcs)", 45, "Breakfast", true,
                                                "Soft thick dosa served with veg saagu", 4.7, 300,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1406, "Rava Dosa", 55, "Breakfast", true,
                                                "Crispy semolina dosa with spices", 4.6, 250,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1407, "Onion Uttapam", 60, "Breakfast", true,
                                                "Thick dosa topped with onions", 4.5, 350,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1408, "Pongal", 35, "Breakfast", true,
                                                "Creamy rice and dal dish with ghee", 4.4, 400,
                                                "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1409, "Poori (2 pcs)", 40, "Breakfast", true,
                                                "Fluffy poori with potato masala", 4.6, 350,
                                                "https://images.unsplash.com/photo-1626777552726-4a6b54c97e46?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1410, "Bisibele Bath", 60, "Lunch", true,
                                                "Spicy rice with lentils and vegetables", 4.7, 450,
                                                "https://images.unsplash.com/photo-1630409351211-d62ab2d24da4?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1411, "Lemon Rice", 35, "Lunch", true,
                                                "Tangy rice with lemon and peanuts", 4.5, 300,
                                                "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1412, "Curd Rice", 30, "Lunch", true,
                                                "Cool and creamy curd mixed rice", 4.6, 250,
                                                "https://images.unsplash.com/photo-1516714435131-44d6b64dc6a2?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1413, "Filter Coffee", 20, "Beverages", true,
                                                "Authentic South Indian filter coffee", 4.9, 120,
                                                "https://images.unsplash.com/photo-1517701604599-bb29b565090c?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1414, "Mysore Pak", 30, "Desserts", true,
                                                "Soft and rich traditional sweet.", 4.8, 450,
                                                "https://images.unsplash.com/photo-1589119908995-c6837fa14848?auto=format&fit=crop&w=400&q=80");
                                break;
                        case 15: // Rayalaseema Ruchulu
                                // Non-Veg
                                addMock(mocks, restaurantId, 1501, "Chicken Fry (Rayalaseema Style)", 180, "Dinner", false,
                                                "Spicy dry chicken fry with curry leaves", 4.6, 320,
                                                "https://images.unsplash.com/photo-1610057099443-fde8c4d50f91?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1502, "Kodi Vepudu", 170, "Dinner", false,
                                                "Andhra-style spicy chicken roast", 4.5, 280,
                                                "https://images.unsplash.com/photo-1599487488170-d11ec9c172f0?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1503, "Egg Curry", 90, "Dinner", false,
                                                "Boiled eggs in spicy masala gravy", 4.4, 200,
                                                "https://images.unsplash.com/photo-1596797038530-2c107229654b?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1504, "Egg Burji", 70, "Dinner", false,
                                                "Scrambled eggs with onion and spices", 4.3, 180,
                                                "https://images.unsplash.com/photo-1534939561126-855b8675edd7?auto=format&fit=crop&w=400&q=80");

                                // Veg Curries
                                addMock(mocks, restaurantId, 1505, "Pappu (Dal)", 80, "Lunch", true,
                                                "Simple dal with mild spices", 4.5, 200,
                                                "https://images.unsplash.com/photo-1546833999-b9f581a1996d?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1506, "Tomato Pappu", 90, "Lunch", true,
                                                "Tangy dal cooked with tomatoes", 4.6, 220,
                                                "https://images.unsplash.com/photo-1585937421612-70a008356fbe?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1507, "Aloo Fry", 70, "Lunch", true,
                                                "Crispy potato fry with spices", 4.4, 180,
                                                "https://images.unsplash.com/photo-1518013431117-eb1465fa5752?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1508, "Beans Fry", 75, "Lunch", true,
                                                "Stir-fried beans with coconut", 4.3, 160,
                                                "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?auto=format&fit=crop&w=400&q=80");

                                // Rice & Breads
                                addMock(mocks, restaurantId, 1509, "Curd Rice", 60, "Lunch", true,
                                                "Cooling curd rice with tempering", 4.6, 250,
                                                "https://images.unsplash.com/photo-1516714435131-44d6b64dc6a2?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1510, "Plain Rice", 40, "Lunch", true,
                                                "Steamed rice", 4.5, 200,
                                                "https://images.unsplash.com/photo-1516684732162-798a0062be99?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1511, "Chapati (2 pcs)", 40, "Dinner", true,
                                                "Soft wheat chapati", 4.4, 180,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1512, "Parotta (2 pcs)", 50, "Dinner", true,
                                                "Layered soft parotta", 4.5, 220,
                                                "https://images.unsplash.com/photo-1668357530437-72a12c660f94?auto=format&fit=crop&w=400&q=80");

                                // Soups & Drinks
                                addMock(mocks, restaurantId, 1513, "Rasam", 50, "Lunch", true,
                                                "Spicy and tangy South Indian soup", 4.6, 120,
                                                "https://images.unsplash.com/photo-1547592180-85f173990554?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1514, "Buttermilk", 30, "Beverages", true,
                                                "Refreshing spiced buttermilk", 4.5, 100,
                                                "https://images.unsplash.com/photo-1621263764928-df1444c5e859?auto=format&fit=crop&w=400&q=80");
                                break;
                        case 16: // Karavalli Seafood
                                // Lunch
                                addMock(mocks, restaurantId, 1601, "Anjal Masala Fry", 340, "Lunch", false,
                                                "Seer fish fried in authentic Mangalorean spice paste.", 4.9, 140,
                                                "https://images.unsplash.com/photo-1543353071-873f17a7a088?auto=format&fit=crop&w=400&q=80", true, "Must Try");
                                addMock(mocks, restaurantId, 1602, "Fish Curry Meal", 220, "Lunch", false,
                                                "Rice served with spicy coastal fish curry.", 4.7, 180,
                                                "https://images.unsplash.com/photo-1626074353765-517a681e40be?auto=format&fit=crop&w=400&q=80", false, "Coastal Favorite");
                                addMock(mocks, restaurantId, 1603, "Neer Dosa (2 pcs)", 120, "Lunch", true,
                                                "Soft rice crepes served with coconut chutney.", 4.6, 160,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1604, "Chicken Sukka", 240, "Lunch", false,
                                                "Dry roasted chicken with coconut and spices.", 4.7, 170,
                                                "https://images.unsplash.com/photo-1598515214211-89d3c73ae83b?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1605, "Prawn Ghee Roast", 320, "Lunch", false,
                                                "Juicy prawns roasted in ghee and red chilli masala.", 4.8, 150,
                                                "https://images.unsplash.com/photo-1565557623262-b51c2513a641?auto=format&fit=crop&w=400&q=80", true, "Bestseller");
                                addMock(mocks, restaurantId, 1606, "Fish Rava Fry", 260, "Lunch", false,
                                                "Crispy fish coated in rava and fried.", 4.7, 130,
                                                "https://images.unsplash.com/photo-1534939561126-855b8675edd7?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1607, "Veg Meals (South Indian)", 180, "Lunch", true,
                                                "Rice with sambar, rasam, vegetables and curd.", 4.5, 120,
                                                "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?auto=format&fit=crop&w=400&q=80");

                                // Dinner
                                addMock(mocks, restaurantId, 1608, "Kori Gassi", 260, "Dinner", false,
                                                "Traditional Mangalorean chicken curry with coconut gravy.", 4.7, 190,
                                                "https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?auto=format&fit=crop&w=400&q=80", true, "Customer Favorite");
                                addMock(mocks, restaurantId, 1609, "Mangalorean Fish Curry", 240, "Dinner", false,
                                                "Tangy fish curry with coconut and tamarind.", 4.6, 150,
                                                "https://images.unsplash.com/photo-1626074353765-517a681e40be?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1610, "Chicken Ghee Roast", 300, "Dinner", false,
                                                "Spicy roasted chicken cooked in ghee.", 4.8, 200,
                                                "https://images.unsplash.com/photo-1567620832903-9fc6debc209f?auto=format&fit=crop&w=400&q=80", true, "Most Ordered");
                                addMock(mocks, restaurantId, 1611, "Prawn Curry", 290, "Dinner", false,
                                                "Fresh prawns cooked in coastal spice gravy.", 4.6, 140,
                                                "https://images.unsplash.com/photo-1565557623262-b51c2513a641?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1612, "Crab Masala", 330, "Dinner", false,
                                                "Spicy crab cooked in rich Mangalorean masala.", 4.7, 120,
                                                "https://images.unsplash.com/photo-1519708227418-c8fd9a32b7a2?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1613, "Appam (2 pcs)", 100, "Dinner", true,
                                                "Soft fermented rice pancakes perfect with curry.", 4.5, 110,
                                                "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1614, "Steamed Rice", 60, "Dinner", true,
                                                "Freshly steamed rice served hot.", 4.4, 100,
                                                "https://images.unsplash.com/photo-1516684732162-798a0062be99?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1615, "Buttermilk", 40, "Dinner", true,
                                                "Refreshing spiced buttermilk.", 4.6, 130,
                                                "https://images.unsplash.com/photo-1621263764928-df1444c5e859?auto=format&fit=crop&w=400&q=80");
                                break;
                        case 17: // Murugan Idli Shop
                                // Beverages
                                addMock(mocks, restaurantId, 1703, "Jigarthanda", 85, "Beverages", true,
                                                "Madurai\u2019s famous cold drink with milk, ice cream and almond gum.", 4.9, 640,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400", false, "Madurai Special");
                                addMock(mocks, restaurantId, 1704, "Filter Coffee", 40, "Beverages", true,
                                                "Traditional South Indian strong filter coffee.", 4.8, 520,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 1705, "Masala Tea", 25, "Beverages", true,
                                                "Hot tea brewed with aromatic spices.", 4.6, 410,
                                                "https://images.unsplash.com/photo-1564890369478-c89ca6d9cde9?auto=format&fit=crop&w=400&q=80");

                                // Breakfast / Tiffin
                                addMock(mocks, restaurantId, 1701, "Malligai Idli (2 pcs)", 60, "Breakfast", true,
                                                "Soft jasmine idlis served with chutney and sambar.", 4.8, 1500,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400", true, "Bestseller");
                                addMock(mocks, restaurantId, 1702, "Ghee Podi Idli", 75, "Breakfast", true,
                                                "Idlis tossed in ghee and spicy podi powder.", 4.7, 900,
                                                "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?auto=format&fit=crop&w=400&q=80", false, "Customer Favorite");
                                addMock(mocks, restaurantId, 1706, "Mini Idli Sambar", 70, "Breakfast", true,
                                                "Small idlis soaked in hot sambar.", 4.7, 820,
                                                "https://images.unsplash.com/photo-1589302168068-964664d93dc0?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1707, "Plain Dosa", 65, "Breakfast", true,
                                                "Crispy dosa served with chutney and sambar.", 4.6, 760,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1708, "Masala Dosa", 90, "Breakfast", true,
                                                "Dosa stuffed with potato masala.", 4.8, 1200,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?auto=format&fit=crop&w=400&q=80", false, "Must Try");
                                addMock(mocks, restaurantId, 1709, "Onion Uttapam", 80, "Breakfast", true,
                                                "Thick dosa topped with onions.", 4.6, 620,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1710, "Rava Dosa", 85, "Breakfast", true,
                                                "Crispy semolina dosa with spices.", 4.7, 700,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?auto=format&fit=crop&w=400&q=80");

                                // Lunch / South Indian Meals
                                addMock(mocks, restaurantId, 1711, "Sambar Rice", 90, "Lunch", true,
                                                "Rice mixed with flavorful sambar and vegetables.", 4.6, 500,
                                                "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1712, "Curd Rice", 70, "Lunch", true,
                                                "Rice mixed with fresh curd and seasoning.", 4.7, 650,
                                                "https://images.unsplash.com/photo-1516714435131-44d6b64dc6a2?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1713, "Lemon Rice", 75, "Lunch", true,
                                                "Tangy rice flavored with lemon and spices.", 4.5, 450,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 1714, "Pongal", 85, "Lunch", true,
                                                "Creamy rice and lentil dish with ghee and pepper.", 4.7, 780,
                                                "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1715, "Vada (2 pcs)", 50, "Lunch", true,
                                                "Crispy medu vada served with chutney and sambar.", 4.8, 900,
                                                "https://images.unsplash.com/photo-1626074353765-517a681e40be?auto=format&fit=crop&w=400&q=80", true, "Bestseller");
                                break;
                        case 18: // Thalassery Kitchen
                                // Dinner
                                addMock(mocks, restaurantId, 1801, "Pathiri with Chicken Curry", 160, "Dinner", false,
                                                "Thin rice flatbread served with spicy coconut chicken curry.", 4.6, 210,
                                                "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?auto=format&fit=crop&w=400&q=80", false, "Authentic Moplah");
                                addMock(mocks, restaurantId, 1802, "Malabar Parotta with Chicken Curry", 150, "Dinner", false,
                                                "Flaky Kerala parotta served with rich chicken gravy.", 4.7, 320,
                                                "https://images.unsplash.com/photo-1668357530437-72a12c660f94?auto=format&fit=crop&w=400&q=80", false, "Bestseller");
                                addMock(mocks, restaurantId, 1803, "Kappa with Fish Curry", 140, "Dinner", false,
                                                "Boiled tapioca served with spicy Kerala fish curry.", 4.6, 190,
                                                "https://images.unsplash.com/photo-1718942899999-b3da4177ee2a?auto=format&fit=crop&w=400&q=80", false, "Coastal Classic");
                                addMock(mocks, restaurantId, 1804, "Kerala Chicken Roast", 180, "Dinner", false,
                                                "Dry roasted chicken with coconut oil and spices.", 4.7, 250,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 1805, "Fish Curry with Rice", 160, "Dinner", false,
                                                "Traditional Kerala style fish curry served with steamed rice.", 4.5, 200,
                                                "https://images.unsplash.com/photo-1626074353765-517a681e40be?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1806, "Egg Roast with Appam", 140, "Dinner", false,
                                                "Boiled eggs cooked in spicy onion masala with soft appam.", 4.6, 210,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 1807, "Veg Stew with Appam", 130, "Dinner", true,
                                                "Creamy coconut vegetable stew served with appam.", 4.5, 170,
                                                "https://images.unsplash.com/photo-1549203438-a7696aed4dac?auto=format&fit=crop&w=400&q=80");

                                // Biryani
                                addMock(mocks, restaurantId, 1808, "Chicken Dum Biryani", 190, "Biryani", false,
                                                "Authentic Thalassery style chicken dum biryani.", 4.8, 920,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400", false, "Must Try");
                                addMock(mocks, restaurantId, 1809, "Egg Biryani", 150, "Biryani", false,
                                                "Fragrant basmati rice cooked with boiled eggs and spices.", 4.6, 340,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 1810, "Veg Biryani", 140, "Biryani", true,
                                                "Aromatic rice cooked with mixed vegetables.", 4.5, 280,
                                                "https://images.unsplash.com/photo-1543353071-873f17a7a088?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1811, "Fish Biryani", 200, "Biryani", false,
                                                "Spicy Kerala style biryani with fresh fish.", 4.7, 260,
                                                "https://images.unsplash.com/photo-1519708227418-c8fd9a32b7a2?auto=format&fit=crop&w=400&q=80");

                                // Drinks / Sides
                                addMock(mocks, restaurantId, 1812, "Kerala Parotta (2 pcs)", 60, "Drinks / Sides", true,
                                                "Soft flaky layered parotta.", 4.6, 300,
                                                "https://images.unsplash.com/photo-1668357530437-72a12c660f94?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1813, "Lime Soda", 40, "Drinks / Sides", true,
                                                "Refreshing lemon soda drink.", 4.5, 210,
                                                "https://images.unsplash.com/photo-1621263764928-df1444c5e859?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1814, "Buttermilk", 35, "Drinks / Sides", true,
                                                "Cooling spiced buttermilk.", 4.6, 180,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 1815, "Payasam", 70, "Drinks / Sides", true,
                                                "Traditional Kerala sweet dessert made with milk and vermicelli.", 4.7, 220,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400", false, "Sweet Treat");
                                break;
                        case 19: // Paradise Biryani Point
                                // Biryani
                                addMock(mocks, restaurantId, 1901, "Chicken Biryani (Regular)", 190, "Biryani", false,
                                                "Hyderabadi style chicken biryani with basmati rice.", 4.8, 2100,
                                                "https://images.unsplash.com/photo-1633945274405-b6c8069047b0?auto=format&fit=crop&w=400&q=80", true, "Legendary Biryani");
                                addMock(mocks, restaurantId, 1902, "Mini Chicken Biryani", 150, "Biryani", false,
                                                "Small portion chicken biryani served with raita.", 4.7, 1800,
                                                "https://images.unsplash.com/photo-1589302168068-964664d93dc0?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1903, "Egg Biryani", 120, "Biryani", false,
                                                "Spiced basmati rice with boiled eggs.", 4.6, 1200,
                                                "https://images.unsplash.com/photo-1589302168068-964664d93dc0?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1904, "Veg Biryani", 110, "Biryani", true,
                                                "Mixed vegetables cooked with aromatic spices.", 4.4, 900,
                                                "https://images.unsplash.com/photo-1543353071-873f17a7a088?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1905, "Mushroom Biryani", 130, "Biryani", true,
                                                "Flavored biryani rice with mushroom masala.", 4.5, 700,
                                                "https://images.unsplash.com/photo-1543353071-873f17a7a088?auto=format&fit=crop&w=400&q=80");

                                // Tamil Favourite Starters
                                addMock(mocks, restaurantId, 1906, "Chicken 65", 180, "Tamil Favourite Starters", false,
                                                "Crispy spicy chicken deep fried with special masala.", 4.7, 1500,
                                                "https://images.unsplash.com/photo-1610057099443-fde8c4d50f91?auto=format&fit=crop&w=400&q=80", true, "Madrasi Special");
                                addMock(mocks, restaurantId, 1907, "Pepper Chicken", 190, "Tamil Favourite Starters", false,
                                                "South Indian pepper style chicken fry.", 4.6, 1000,
                                                "https://images.unsplash.com/photo-1599487488170-d11ec9c172f0?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1908, "Gobi 65", 140, "Tamil Favourite Starters", true,
                                                "Crispy cauliflower tossed in spicy masala.", 4.5, 850,
                                                "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=400");
                                addMock(mocks, restaurantId, 1909, "Chilli Chicken", 190, "Tamil Favourite Starters", false,
                                                "Chicken cooked with chilli sauce and capsicum.", 4.6, 900,
                                                "https://images.unsplash.com/photo-1585032226651-759b368d7246?auto=format&fit=crop&w=400&q=80");

                                // Rice & Meals
                                addMock(mocks, restaurantId, 1910, "Chicken Fried Rice", 170, "Rice & Meals", false,
                                                "Indo-Chinese style fried rice with chicken.", 4.5, 750,
                                                "https://images.unsplash.com/photo-1603133872878-684f208fb84b?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1911, "Egg Fried Rice", 140, "Rice & Meals", false,
                                                "Fried rice tossed with egg and vegetables.", 4.4, 650,
                                                "https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=400");
                                addMock(mocks, restaurantId, 1912, "Veg Fried Rice", 130, "Rice & Meals", true,
                                                "Classic fried rice with mixed vegetables.", 4.3, 600,
                                                "https://images.unsplash.com/photo-1603133872878-684f208fb84b?auto=format&fit=crop&w=400&q=80");

                                // Parotta Items
                                addMock(mocks, restaurantId, 1913, "Parotta (2 pcs) with Salna", 90, "Parotta Items", true,
                                                "Soft layered parotta served with spicy gravy.", 4.6, 1000,
                                                "https://images.unsplash.com/photo-1668357530437-72a12c660f94?auto=format&fit=crop&w=400&q=80", true, "Tamil Popular");
                                addMock(mocks, restaurantId, 1914, "Egg Parotta", 120, "Parotta Items", false,
                                                "Parotta mixed with egg and masala.", 4.5, 800,
                                                "https://images.unsplash.com/photo-1668357530437-72a12c660f94?auto=format&fit=crop&w=400&q=80");

                                // Add-ons
                                addMock(mocks, restaurantId, 1915, "Boiled Egg", 25, "Add-ons", false,
                                                "Single boiled egg.", 4.0, 100,
                                                "https://images.unsplash.com/photo-1482049016688-2d3e1b311543?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1916, "Raita", 20, "Add-ons", true,
                                                "Cooling yogurt dip with onions and cucumbers.", 4.0, 200,
                                                "https://images.unsplash.com/photo-1571167452945-1b9e84bdbf71?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 1917, "Extra Salna", 30, "Add-ons", true,
                                                "Spicy and flavorful extra gravy.", 4.2, 150,
                                                "https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?auto=format&fit=crop&w=400&q=80");
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
                                                "https://images.unsplash.com/photo-1527904324834-3bda86da6771?auto=format&fit=crop&w=400&q=80", true);
                                addMock(mocks, restaurantId, 2102, "Warm Apple Pie", 150, "Cakes", true,
                                                "Traditional crusty pie with cinnamon spiced apple filling", 4.7, 250,
                                                "https://images.unsplash.com/photo-1568571780765-9276ac8b75a2?auto=format&fit=crop&w=400&q=80", false);
                                addMock(mocks, restaurantId, 2103, "Classic Plum Cake", 220, "Cakes", true,
                                                "Rich fruit cake soaked with juices and premium dry fruits", 4.6, 320,
                                                "https://images.unsplash.com/photo-1512418490979-92798cec1380?auto=format&fit=crop&w=400&q=80", false);
                                addMock(mocks, restaurantId, 2104, "Pineapple Pastry", 95, "Cakes", true,
                                                "Tropical pineapple flavored cake with buttery cream", 4.4, 190,
                                                "https://images.unsplash.com/photo-1571115177098-24ec42ed204d?auto=format&fit=crop&w=400&q=80", false);
                                addMock(mocks, restaurantId, 2105, "Choco Chip Brownie", 110, "Brownies", true,
                                                "Chewy brownie loaded with dark chocolate chips", 4.8, 310,
                                                "https://images.unsplash.com/photo-1606313564200-e75d5e30476c?auto=format&fit=crop&w=400&q=80", true);
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
                                // Tea
                                addMock(mocks, restaurantId, 2201, "Cutting Chai", 15, "Tea", true,
                                                "Small strong tea served in traditional cutting style.", 4.7, 120,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400", true, "Traditional");
                                addMock(mocks, restaurantId, 2202, "Masala Chai", 40, "Tea", true,
                                                "Assam tea brewed with cardamom, ginger, and spices.", 4.8, 45,
                                                "https://images.unsplash.com/photo-1619581073186-5b4ae1b0caad?auto=format&fit=crop&w=400&q=80", true, "Best Seller");
                                addMock(mocks, restaurantId, 2203, "Ginger Tea", 45, "Tea", true,
                                                "Refreshing hot tea with fresh ginger flavor.", 4.7, 35,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 2204, "Lemon Tea", 35, "Tea", true,
                                                "Light tea with lemon and mild sweetness.", 4.6, 50,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400");
                                addMock(mocks, restaurantId, 2205, "Green Tea", 50, "Tea", true,
                                                "Healthy antioxidant-rich green tea.", 4.5, 40,
                                                "https://images.unsplash.com/photo-1627435601361-ec25f5b1d0e5?auto=format&fit=crop&w=400&q=80");

                                // Coffee
                                addMock(mocks, restaurantId, 2206, "Filter Coffee", 60, "Coffee", true,
                                                "South Indian style strong filter coffee.", 4.8, 150,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400", true, "Favorite");
                                addMock(mocks, restaurantId, 2207, "Cappuccino", 120, "Coffee", true,
                                                "Espresso with steamed milk and foam.", 4.6, 80,
                                                "https://images.unsplash.com/photo-1514432324607-a09d9b4aefdd?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 2208, "Cold Coffee", 140, "Coffee", true,
                                                "Chilled coffee blended with milk and ice.", 4.7, 90,
                                                "https://images.unsplash.com/photo-1517701550927-30cf4ba1dba5?auto=format&fit=crop&w=400&q=80");

                                // Milk & Chocolate Drinks
                                addMock(mocks, restaurantId, 2209, "Boost Milk", 70, "Milk & Chocolate Drinks", true,
                                                "Hot milk with Boost energy drink mix.", 4.6, 60,
                                                "https://images.unsplash.com/photo-1550507992-eb63ffee0847?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 2210, "Horlicks Milk", 65, "Milk & Chocolate Drinks", true,
                                                "Warm milk with Horlicks malt flavor.", 4.5, 55,
                                                "https://images.unsplash.com/photo-1550507992-eb63ffee0847?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 2211, "Hot Chocolate", 120, "Milk & Chocolate Drinks", true,
                                                "Creamy hot chocolate drink.", 4.7, 75,
                                                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400", true, "Must Try");

                                // Coolers & Shakes
                                addMock(mocks, restaurantId, 2212, "Lemon Mint Cooler", 60, "Coolers & Shakes", true,
                                                "Refreshing lemon drink with mint.", 4.6, 85,
                                                "https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 2213, "Mango Milkshake", 150, "Coolers & Shakes", true,
                                                "Sweet mango blended with chilled milk.", 4.7, 95,
                                                "https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 2214, "Chocolate Milkshake", 160, "Coolers & Shakes", true,
                                                "Rich chocolate shake topped with cream.", 4.6, 80,
                                                "https://images.unsplash.com/photo-1572490122747-3968b75cc699?auto=format&fit=crop&w=400&q=80");
                                addMock(mocks, restaurantId, 2215, "Oreo Milkshake", 180, "Coolers & Shakes", true,
                                                "Creamy milkshake blended with Oreo biscuits.", 4.8, 110,
                                                "https://images.unsplash.com/photo-1572490122747-3968b75cc699?auto=format&fit=crop&w=400&q=80", true, "Trending");
                                break;
                        case 23: // Namma Karnataka
                                addMock(mocks, restaurantId, 2301, "Bisi Bele Bath", 140, "Lunch", true,
                                                "Traditional spicy rice dish with lentils and mixed vegetables", 4.8,
                                                420,
                                                "https://images.unsplash.com/photo-1630409351211-d62ab2d24da4?w=400");
                                addMock(mocks, restaurantId, 2302, "Vangi Bath", 120, "Breakfast", true,
                                                "Brinjal rice made with special Vangi Bath masala and spices", 4.6, 310,
                                                "https://images.unsplash.com/photo-1645177628172-a94c1f96e6db?w=400");
                                addMock(mocks, restaurantId, 2303, "Kesari Bath", 80, "Breakfast", true,
                                                "Sweet semolina halwa with saffron and dry fruits", 4.7, 340,
                                                "https://images.unsplash.com/photo-1663446783008-c34241daff22?w=400");
                                addMock(mocks, restaurantId, 2304, "Chow Chow Bath", 180, "Breakfast", true,
                                                "Combination of spicy Khara Bath and sweet Kesari Bath", 4.9, 650,
                                                "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=400");
                                addMock(mocks, restaurantId, 2305, "Mysore Masala Dosa", 110, "Breakfast", true,
                                                "Crispy dosa with spicy red chutney and potato filling", 4.7, 430,
                                                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=400");
                                addMock(mocks, restaurantId, 2306, "Maddur Vada (2 pcs)", 70, "Snacks", true,
                                                "Famous crispy onion and semolina snack from Maddur", 4.5, 210,
                                                "https://images.unsplash.com/photo-1683533678033-f5d60f0a3437?w=400");
                                break;
                }

                // Sort by requested order
                Map<String, Integer> categoryOrder = new HashMap<>();
                categoryOrder.put("Recommended", 0);
                categoryOrder.put("Main Course", 1);
                categoryOrder.put("Breakfast", 2);
                categoryOrder.put("Lunch", 3);
                categoryOrder.put("Dinner", 4);
                categoryOrder.put("Main Dishes", 5);
                categoryOrder.put("Quick Meals", 6);
                categoryOrder.put("Biryani", 7);
                categoryOrder.put("Tiffin", 8);
                categoryOrder.put("Starters", 9);
                categoryOrder.put("Burgers", 10);
                categoryOrder.put("Sandwiches & Wraps", 11);
                categoryOrder.put("Sides & Starters", 12);
                categoryOrder.put("Salads", 13);
                categoryOrder.put("Beverages", 14);
                categoryOrder.put("Drinks", 15);
                categoryOrder.put("Drinks / Sides", 16);
                categoryOrder.put("Desserts", 17);
                categoryOrder.put("Add-ons", 18);
                categoryOrder.put("Snacks", 19);
                categoryOrder.put("Noodles", 20);
                categoryOrder.put("Ramen / Noodles", 21);
                categoryOrder.put("Fried Rice", 22);
                categoryOrder.put("Gravy Items", 23);
                categoryOrder.put("Soups", 24);
                categoryOrder.put("Veg Burgers", 25);
                categoryOrder.put("Non-Veg Burgers", 26);
                categoryOrder.put("Fast Food", 27);
                categoryOrder.put("Milkshakes", 28);
                categoryOrder.put("Combo Meals", 29);
                categoryOrder.put("Sushi Rolls", 30);
                categoryOrder.put("Tempura", 31);
                categoryOrder.put("Japanese Chicken Dishes", 32);
                categoryOrder.put("Rice Bowls", 33);
                categoryOrder.put("Small Bites", 34);
                categoryOrder.put("Cakes", 35);
                categoryOrder.put("Cheesecakes", 36);
                categoryOrder.put("Brownies", 37);
                categoryOrder.put("Dessert Cups", 38);
                categoryOrder.put("Tamil Favourite Starters", 39);
                categoryOrder.put("Rice & Meals", 40);
                categoryOrder.put("Parotta Items", 41);
                categoryOrder.put("Healthy Bowls", 42);
                categoryOrder.put("Smoothies & Drinks", 43);
                categoryOrder.put("Tacos", 44);
                categoryOrder.put("Burritos", 45);
                categoryOrder.put("Quesadillas", 46);
                categoryOrder.put("Tea", 47);
                categoryOrder.put("Coffee", 48);
                categoryOrder.put("Milk & Chocolate Drinks", 49);
                categoryOrder.put("Coolers & Shakes", 50);

                mocks.sort((a, b) -> {
                        String catA = a.getCategory() != null ? a.getCategory().trim() : "";
                        String catB = b.getCategory() != null ? b.getCategory().trim() : "";
                        int rankA = categoryOrder.getOrDefault(catA, 99);
                        int rankB = categoryOrder.getOrDefault(catB, 99);
                        if (rankA != rankB)
                                return rankA - rankB;
                        return Integer.compare(a.getFoodId(), b.getFoodId());
                });

                return mocks;
        }

        private void addMock(List<FoodItem> list, int resId, int foodId, String name, double price, String category,
                        boolean isVeg, String description, double rating, int ratingCount, String url) {
                addMock(list, resId, foodId, name, price, category, isVeg, description, rating, ratingCount, url, false, null);
        }

        private void addMock(List<FoodItem> list, int resId, int foodId, String name, double price, String category,
                        boolean isVeg, String description, double rating, int ratingCount, String url, boolean isBestseller) {
                addMock(list, resId, foodId, name, price, category, isVeg, description, rating, ratingCount, url,
                                isBestseller, null);
        }

        private void addMock(List<FoodItem> list, int resId, int foodId, String name, double price, String category,
                        boolean isVeg, String description, double rating, int ratingCount, String url,
                        boolean isBestseller, String itemTag) {
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
                f.setBestseller(isBestseller);
                f.setItemTag(itemTag);
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
		List<FoodItem> items = new ArrayList<>(customDishes);
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
		// Maintain in memory for demo purposes
		f.setFoodId(2000 + customDishes.size());
		customDishes.add(f);

		Connection conn = DBConnection.getConnection();
		if (conn == null) {
			System.out.println("DEMO MODE: Added food item to memory: " + f.getName());
			return true;
		}

		String sql = "INSERT INTO food_item (restaurant_id, name, description, price, image_url, category, is_vegetarian) VALUES (?,?,?,?,?,?,?)";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
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
		} finally {
			DBConnection.closeConnection(conn);
		}
		return false;
	}

	public boolean updateFoodItem(FoodItem f) {
		// Update in memory for demo purposes
		for (int i = 0; i < customDishes.size(); i++) {
			if (customDishes.get(i).getFoodId() == f.getFoodId()) {
				customDishes.set(i, f);
				break;
			}
		}

		Connection conn = DBConnection.getConnection();
		if (conn == null) {
			return true;
		}

		String sql = "UPDATE food_item SET name=?, description=?, price=?, category=?, is_vegetarian=?, is_available=? WHERE food_id=?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
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
		} finally {
			DBConnection.closeConnection(conn);
		}
		return false;
	}

	public boolean deleteFoodItem(int foodId) {
		// Remove from memory for demo purposes
		customDishes.removeIf(f -> f.getFoodId() == foodId);

		Connection conn = DBConnection.getConnection();
		if (conn == null) {
			return true;
		}

		String sql = "DELETE FROM food_item WHERE food_id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, foodId);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(conn);
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
                try {
                        f.setItemTag(rs.getString("item_tag"));
                } catch (Exception e) {
                        // Column might not exist in old schema
                }
                f.setCreatedAt(rs.getTimestamp("created_at"));
                return f;
        }
}
