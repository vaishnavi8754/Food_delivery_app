-- =====================================================
-- FoodExpress Database Schema
-- Online Food Delivery Application
-- =====================================================

-- Create Database
CREATE DATABASE IF NOT EXISTS food_delivery_db;
USE food_delivery_db;

-- =====================================================
-- USER TABLE
-- =====================================================
CREATE TABLE IF NOT EXISTS user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    address TEXT,
    role ENUM('user', 'admin') DEFAULT 'user',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_email (email),
    INDEX idx_role (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- RESTAURANT TABLE
-- =====================================================
CREATE TABLE IF NOT EXISTS restaurant (
    restaurant_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    address TEXT NOT NULL,
    phone VARCHAR(15),
    email VARCHAR(100),
    image_url VARCHAR(255),
    cuisine_type VARCHAR(50),
    rating DECIMAL(2,1) DEFAULT 0.0,
    is_active BOOLEAN DEFAULT TRUE,
    opening_time TIME DEFAULT '09:00:00',
    closing_time TIME DEFAULT '22:00:00',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_cuisine (cuisine_type),
    INDEX idx_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- FOOD ITEM TABLE
-- =====================================================
CREATE TABLE IF NOT EXISTS food_item (
    food_id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    image_url VARCHAR(255),
    category VARCHAR(50),
    is_vegetarian BOOLEAN DEFAULT FALSE,
    is_available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(restaurant_id) ON DELETE CASCADE,
    INDEX idx_restaurant (restaurant_id),
    INDEX idx_category (category),
    INDEX idx_available (is_available)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- ORDER TABLE
-- =====================================================
CREATE TABLE IF NOT EXISTS `order` (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    restaurant_id INT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    delivery_address TEXT NOT NULL,
    status ENUM('pending', 'confirmed', 'preparing', 'out_for_delivery', 'delivered', 'cancelled') DEFAULT 'pending',
    payment_status ENUM('pending', 'paid', 'failed', 'refunded') DEFAULT 'pending',
    payment_method VARCHAR(50),
    special_instructions TEXT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    delivery_date TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(restaurant_id) ON DELETE CASCADE,
    INDEX idx_user (user_id),
    INDEX idx_restaurant (restaurant_id),
    INDEX idx_status (status),
    INDEX idx_order_date (order_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- ORDER DETAILS TABLE
-- =====================================================
CREATE TABLE IF NOT EXISTS order_details (
    order_detail_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    food_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    unit_price DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    special_instructions TEXT,
    
    FOREIGN KEY (order_id) REFERENCES `order`(order_id) ON DELETE CASCADE,
    FOREIGN KEY (food_id) REFERENCES food_item(food_id) ON DELETE CASCADE,
    INDEX idx_order (order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- INSERT DEFAULT ADMIN USER
-- =====================================================
INSERT INTO user (full_name, email, password, phone, address, role) 
VALUES ('Admin User', 'admin@foodexpress.com', 'admin123', '9999999999', 'FoodExpress HQ', 'admin')
ON DUPLICATE KEY UPDATE email = email;

-- =====================================================
-- INSERT SAMPLE RESTAURANTS
-- =====================================================
INSERT INTO restaurant (name, description, address, phone, cuisine_type, rating) VALUES
('Pizza Palace', 'Authentic Italian pizzas made with love', '123 Main Street', '1234567890', 'Italian', 4.5),
('Burger Barn', 'Juicy burgers and crispy fries', '456 Oak Avenue', '2345678901', 'American', 4.2),
('Curry House', 'Traditional Indian cuisine', '789 Spice Lane', '3456789012', 'Indian', 4.7),
('Dragon Wok', 'Authentic Chinese dishes', '321 Oriental Road', '4567890123', 'Chinese', 4.3),
('Taco Town', 'Fresh Mexican street food', '654 Fiesta Street', '5678901234', 'Mexican', 4.1);

-- =====================================================
-- INSERT SAMPLE FOOD ITEMS
-- =====================================================
INSERT INTO food_item (restaurant_id, name, description, price, category, is_vegetarian) VALUES
(1, 'Margherita Pizza', 'Classic tomato and mozzarella', 299.00, 'Pizza', TRUE),
(1, 'Pepperoni Pizza', 'Loaded with spicy pepperoni', 349.00, 'Pizza', FALSE),
(1, 'Garlic Bread', 'Crispy bread with garlic butter', 99.00, 'Sides', TRUE),
(2, 'Classic Burger', 'Beef patty with fresh veggies', 199.00, 'Burgers', FALSE),
(2, 'Cheese Burger', 'Double cheese, double flavor', 249.00, 'Burgers', FALSE),
(2, 'French Fries', 'Golden crispy fries', 79.00, 'Sides', TRUE),
(3, 'Butter Chicken', 'Creamy tomato-based curry', 299.00, 'Main Course', FALSE),
(3, 'Paneer Tikka', 'Grilled cottage cheese', 249.00, 'Starters', TRUE),
(3, 'Naan Bread', 'Fresh tandoor baked bread', 49.00, 'Breads', TRUE),
(4, 'Fried Rice', 'Wok-tossed vegetable rice', 179.00, 'Rice', TRUE),
(4, 'Chow Mein', 'Stir-fried noodles', 199.00, 'Noodles', TRUE),
(5, 'Chicken Tacos', 'Seasoned chicken in soft shell', 199.00, 'Tacos', FALSE),
(5, 'Veggie Burrito', 'Loaded vegetarian burrito', 249.00, 'Burritos', TRUE);

SELECT 'Database setup completed successfully!' AS Status;
