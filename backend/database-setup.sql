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

-- Create subjects table
CREATE TABLE IF NOT EXISTS subjects (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    course_code VARCHAR(20),
    semester INTEGER NOT NULL,
    department VARCHAR(50) NOT NULL,
    regulation VARCHAR(10) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(name, semester, department, regulation)
);

-- Create study_resources table
CREATE TABLE IF NOT EXISTS study_resources (
    id SERIAL PRIMARY KEY,
    subject_id INTEGER REFERENCES subjects(id) ON DELETE CASCADE,
    contributor_name VARCHAR(100) NOT NULL,
    description TEXT,
    file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    file_size BIGINT,
    download_count INTEGER DEFAULT 0,
    semester INTEGER NOT NULL,
    department VARCHAR(50) NOT NULL,
    regulation VARCHAR(10) NOT NULL,
    uploaded_by INTEGER REFERENCES users(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample subjects for different departments and regulations
INSERT INTO subjects (name, course_code, semester, department, regulation) VALUES
-- R2019 IT Subjects
('Mathematics I', 'MATH101', 1, 'IT', 'R2019'),
('Physics', 'PHY101', 1, 'IT', 'R2019'),
('Chemistry', 'CHEM101', 1, 'IT', 'R2019'),
('English', 'ENG101', 1, 'IT', 'R2019'),
('Programming in C', 'CS101', 1, 'IT', 'R2019'),
('Mathematics II', 'MATH102', 2, 'IT', 'R2019'),
('Data Structures', 'CS201', 2, 'IT', 'R2019'),
('Digital Electronics', 'ECE201', 2, 'IT', 'R2019'),
('English II', 'ENG102', 2, 'IT', 'R2019'),
('Environmental Science', 'ENV101', 2, 'IT', 'R2019'),
('Discrete Mathematics', 'MATH201', 3, 'IT', 'R2019'),
('Computer Networks', 'CS301', 3, 'IT', 'R2019'),
('Database Management Systems', 'CS302', 3, 'IT', 'R2019'),
('Software Engineering', 'CS303', 3, 'IT', 'R2019'),
('Operating Systems', 'CS304', 3, 'IT', 'R2019'),

-- R2023 IT Subjects
('Mathematics I', 'MATH101', 1, 'IT', 'R2023'),
('Physics', 'PHY101', 1, 'IT', 'R2023'),
('Chemistry', 'CHEM101', 1, 'IT', 'R2023'),
('English', 'ENG101', 1, 'IT', 'R2023'),
('Python Programming', 'CS101', 1, 'IT', 'R2023'),
('Mathematics II', 'MATH102', 2, 'IT', 'R2023'),
('Data Structures and Algorithms', 'CS201', 2, 'IT', 'R2023'),
('Digital Electronics', 'ECE201', 2, 'IT', 'R2023'),
('English II', 'ENG102', 2, 'IT', 'R2023'),
('Environmental Science', 'ENV101', 2, 'IT', 'R2023'),

-- R2019 CT Subjects
('Mathematics I', 'MATH101', 1, 'CT', 'R2019'),
('Physics', 'PHY101', 1, 'CT', 'R2019'),
('Chemistry', 'CHEM101', 1, 'CT', 'R2019'),
('English', 'ENG101', 1, 'CT', 'R2019'),
('Programming Fundamentals', 'CS101', 1, 'CT', 'R2019'),
('Data Structures', 'CS201', 2, 'CT', 'R2019'),
('Digital Systems', 'ECE201', 2, 'CT', 'R2019'),

-- R2019 ECE Subjects
('Mathematics I', 'MATH101', 1, 'ECE', 'R2019'),
('Physics', 'PHY101', 1, 'ECE', 'R2019'),
('Chemistry', 'CHEM101', 1, 'ECE', 'R2019'),
('English', 'ENG101', 1, 'ECE', 'R2019'),
('Basic Electronics', 'ECE101', 1, 'ECE', 'R2019'),
('Digital Electronics', 'ECE201', 2, 'ECE', 'R2019'),
('Circuit Theory', 'ECE202', 2, 'ECE', 'R2019'),

-- R2019 AERO Subjects
('Mathematics I', 'MATH101', 1, 'AERO', 'R2019'),
('Physics', 'PHY101', 1, 'AERO', 'R2019'),
('Chemistry', 'CHEM101', 1, 'AERO', 'R2019'),
('English', 'ENG101', 1, 'AERO', 'R2019'),
('Engineering Graphics', 'AERO101', 1, 'AERO', 'R2019'),
('Thermodynamics', 'AERO201', 2, 'AERO', 'R2019'),
('Fluid Mechanics', 'AERO202', 2, 'AERO', 'R2019')
ON CONFLICT (name, semester, department, regulation) DO NOTHING;

-- Insert sample study resources
INSERT INTO study_resources (subject_id, contributor_name, description, file_name, file_path, file_size, download_count, semester, department, regulation, uploaded_by) VALUES
(7, 'Gagneet Kaur', 'Week 1-10', 'data_structures_notes.pdf', '/uploads/data_structures_notes.pdf', 2048576, 9394, 2, 'IT', 'R2019', 1),
(11, 'Puneet', 'Week 1-12', 'computer_networks_notes.pdf', '/uploads/computer_networks_notes.pdf', 1536000, 8046, 3, 'IT', 'R2019', 2),
(1, 'Puneet Prasar', 'Week 1-4', 'mathematics_notes.pdf', '/uploads/mathematics_notes.pdf', 1024000, 5367, 1, 'IT', 'R2019', 3),
(15, 'Thallam Visit', 'Week 1-12', 'operating_systems_notes.pdf', '/uploads/operating_systems_notes.pdf', 3072000, 4380, 3, 'IT', 'R2019', 4);

-- Verify data insertion
SELECT * FROM users;
SELECT * FROM subjects ORDER BY department, regulation, semester, name;
SELECT * FROM study_resources;
