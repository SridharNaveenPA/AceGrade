-- AceGrade Database Setup Script
-- Run this script in your PostgreSQL database

-- Create database (run this as superuser)
-- CREATE DATABASE acegrade;

-- Connect to acegrade database and run the following:

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    register_number VARCHAR(20) UNIQUE,
    department VARCHAR(50),
    college_email VARCHAR(100) UNIQUE,
    password VARCHAR(255)
);

-- Insert sample test data
INSERT INTO users (first_name, last_name, register_number, department, college_email, password) 
VALUES 
('John', 'Doe', '20CS001', 'Computer Science', 'john.doe@college.edu', 'password123'),
('Jane', 'Smith', '20CS002', 'Computer Science', 'jane.smith@college.edu', 'password123'),
('Mike', 'Johnson', '20IT001', 'Information Technology', 'mike.johnson@college.edu', 'password123'),
('Sarah', 'Wilson', '20ECE001', 'Electronics Engineering', 'sarah.wilson@college.edu', 'password123'),
('David', 'Brown', '20MECH001', 'Mechanical Engineering', 'david.brown@college.edu', 'password123')
ON CONFLICT (register_number) DO NOTHING;

-- Verify data insertion
SELECT * FROM users;
