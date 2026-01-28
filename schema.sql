-- MySQL Schema for Gold & Silver Investment Tracker
-- Run this if you're using MySQL instead of H2

-- Create database
CREATE DATABASE IF NOT EXISTS gold_silver_db;
USE gold_silver_db;

-- Investments table
CREATE TABLE IF NOT EXISTS investments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    metal_type VARCHAR(10) NOT NULL,
    purchase_date DATE NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    price_per_gram DECIMAL(10,2) NOT NULL,
    grams DECIMAL(10,5) NOT NULL,
    CONSTRAINT chk_metal_type CHECK (metal_type IN ('GOLD', 'SILVER'))
);

-- Metal prices table
CREATE TABLE IF NOT EXISTS metal_prices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    metal_type VARCHAR(10) UNIQUE NOT NULL,
    price_per_gram DECIMAL(10,2) NOT NULL,
    updated_on DATE NOT NULL,
    CONSTRAINT chk_price_metal_type CHECK (metal_type IN ('GOLD', 'SILVER'))
);

-- Sample data (optional)
INSERT INTO metal_prices (metal_type, price_per_gram, updated_on) VALUES
('GOLD', 6000.00, CURDATE()),
('SILVER', 75.00, CURDATE());

-- Sample investment data (optional)
INSERT INTO investments (metal_type, purchase_date, amount, price_per_gram, grams) VALUES
('GOLD', '2024-01-15', 10000.00, 5800.00, 1.72414),
('SILVER', '2024-01-20', 5000.00, 70.00, 71.42857);
