-- Food Delivery App Database Schema
-- This file contains the SQL schema for the food delivery application

-- Create database (run this first)
-- CREATE DATABASE food_delivery_db;
-- USE food_delivery_db;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Foods table
CREATE TABLE IF NOT EXISTS foods (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    price DECIMAL(10,2) NOT NULL CHECK (price > 0),
    image_url VARCHAR(255),
    category VARCHAR(100),
    is_available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Orders table
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL CHECK (total_amount > 0),
    delivery_fee DECIMAL(10,2) DEFAULT 2.00,
    status ENUM('PENDING', 'CONFIRMED', 'PREPARING', 'OUT_FOR_DELIVERY', 'DELIVERED', 'CANCELLED') DEFAULT 'PENDING',
    payment_status ENUM('PENDING', 'PAID', 'FAILED', 'REFUNDED') DEFAULT 'PENDING',
    session_url VARCHAR(500),
    street VARCHAR(200) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    zipcode VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Order items table
CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    food_id BIGINT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(10,2) NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (food_id) REFERENCES foods(id) ON DELETE CASCADE
);

-- Cart items table
CREATE TABLE IF NOT EXISTS cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    food_id BIGINT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (food_id) REFERENCES foods(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_food (user_id, food_id)
);

-- Indexes for better performance
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_foods_category ON foods(category);
CREATE INDEX idx_foods_available ON foods(is_available);
CREATE INDEX idx_orders_user_id ON orders(user_id);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_order_items_order_id ON order_items(order_id);
CREATE INDEX idx_cart_items_user_id ON cart_items(user_id);

-- Sample data (optional - for testing)
-- Previous demo rows commented out to avoid duplicates
-- INSERT INTO foods (name, description, price, image_url, category) VALUES
-- ('Margherita Pizza', 'Classic pizza with tomato sauce, mozzarella, and fresh basil', 12.99, 'food_1.png', 'Pizza'),
-- ('Pepperoni Pizza', 'Pizza topped with pepperoni and mozzarella cheese', 14.99, 'food_2.png', 'Pizza'),
-- ('Chicken Burger', 'Juicy chicken patty with lettuce, tomato, and mayo', 9.99, 'food_3.png', 'Burgers'),
-- ('Beef Burger', 'Classic beef burger with cheese, lettuce, and tomato', 11.99, 'food_4.png', 'Burgers'),
-- ('Caesar Salad', 'Fresh romaine lettuce with caesar dressing and croutons', 8.99, 'food_5.png', 'Salads'),
-- ('Greek Salad', 'Mixed greens with feta cheese, olives, and olive oil', 9.99, 'food_6.png', 'Salads'),
-- ('Chicken Wings', 'Crispy chicken wings with your choice of sauce', 7.99, 'food_7.png', 'Appetizers'),
-- ('French Fries', 'Golden crispy french fries', 4.99, 'food_8.png', 'Sides'),
-- ('Coca Cola', 'Refreshing cola drink', 2.99, 'food_9.png', 'Beverages'),
-- ('Orange Juice', 'Fresh squeezed orange juice', 3.99, 'food_10.png', 'Beverages');

-- Seed data provided by user; paths map to /images/** (uploads directory)
INSERT INTO foods (name, description, price, image_url, category, is_available) VALUES
('Greek salad', 'Food provides essential nutrients for overall health and well-being', 12.00, '/images/food_1.png', 'Salad', true),
('Veg salad', 'Food provides essential nutrients for overall health and well-being', 18.00, '/images/food_2.png', 'Salad', true),
('Clover Salad', 'Food provides essential nutrients for overall health and well-being', 16.00, '/images/food_3.png', 'Salad', true),
('Chicken Salad', 'Food provides essential nutrients for overall health and well-being', 24.00, '/images/food_4.png', 'Salad', true),
('Lasagna Rolls', 'Food provides essential nutrients for overall health and well-being', 14.00, '/images/food_5.png', 'Rolls', true),
('Peri Peri Rolls', 'Food provides essential nutrients for overall health and well-being', 12.00, '/images/food_6.png', 'Rolls', true),
('Chicken Rolls', 'Food provides essential nutrients for overall health and well-being', 20.00, '/images/food_7.png', 'Rolls', true),
('Veg Rolls', 'Food provides essential nutrients for overall health and well-being', 15.00, '/images/food_8.png', 'Rolls', true),
('Ripple Ice Cream', 'Food provides essential nutrients for overall health and well-being', 14.00, '/images/food_9.png', 'Deserts', true),
('Fruit Ice Cream', 'Food provides essential nutrients for overall health and well-being', 22.00, '/images/food_10.png', 'Deserts', true),
('Jar Ice Cream', 'Food provides essential nutrients for overall health and well-being', 10.00, '/images/food_11.png', 'Deserts', true),
('Vanilla Ice Cream', 'Food provides essential nutrients for overall health and well-being', 12.00, '/images/food_12.png', 'Deserts', true),
('Chicken Sandwich', 'Food provides essential nutrients for overall health and well-being', 12.00, '/images/food_13.png', 'Sandwich', true),




