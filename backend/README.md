# AceGrade Backend

This is the Spring Boot backend for the AceGrade application that handles user authentication and database operations.

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- PostgreSQL 12 or higher
- pgAdmin 4

## Database Setup

1. **Create Database:**
   ```sql
   CREATE DATABASE acegrade;
   ```

2. **Create Users Table:**
   ```sql
   CREATE TABLE users (
       id SERIAL PRIMARY KEY,
       first_name VARCHAR(50),
       last_name VARCHAR(50),
       register_number VARCHAR(20) UNIQUE,
       department VARCHAR(50),
       college_email VARCHAR(100) UNIQUE,
       password VARCHAR(255)
   );
   ```

3. **Insert Sample Data (Optional):**
   ```sql
   INSERT INTO users (first_name, last_name, register_number, department, college_email, password) 
   VALUES 
   ('John', 'Doe', '20CS001', 'Computer Science', 'john.doe@college.edu', 'password123'),
   ('Jane', 'Smith', '20CS002', 'Computer Science', 'jane.smith@college.edu', 'password123');
   ```

## Configuration

1. **Update Database Credentials:**
   Edit `src/main/resources/application.properties` and update:
   ```properties
   spring.datasource.username=your_postgres_username
   spring.datasource.password=your_postgres_password
   ```

2. **Database URL:**
   Make sure the database URL points to your PostgreSQL instance:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/acegrade
   ```

## Running the Application

1. **Navigate to backend directory:**
   ```bash
   cd backend
   ```

2. **Build the project:**
   ```bash
   mvn clean install
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

## API Endpoints

### Authentication

- **POST** `/api/auth/login` - User login
  - Request Body:
    ```json
    {
        "collegeEmail": "user@college.edu",
        "password": "password123"
    }
    ```
  - Response (Success):
    ```json
    {
        "success": true,
        "message": "Login successful!",
        "userInfo": {
            "id": 1,
            "firstName": "John",
            "lastName": "Doe",
            "registerNumber": "20CS001",
            "department": "Computer Science",
            "collegeEmail": "john.doe@college.edu"
        }
    }
    ```
  - Response (Failure):
    ```json
    {
        "success": false,
        "message": "Invalid email or password. Please check your credentials and try again."
    }
    ```

- **GET** `/api/auth/health` - Health check
  - Response: `"Login service is running!"`

## CORS Configuration

The application is configured to allow requests from:
- `http://localhost:3000`
- `http://127.0.0.1:5500`
- `file://` (for local HTML files)

## Troubleshooting

1. **Database Connection Issues:**
   - Ensure PostgreSQL is running
   - Check database credentials in `application.properties`
   - Verify database exists and table is created

2. **Port Already in Use:**
   - Change the port in `application.properties`:
     ```properties
     server.port=8081
     ```

3. **CORS Issues:**
   - Make sure the frontend is served from allowed origins
   - Check browser console for CORS errors

## Development

- The application uses Spring Boot 3.2.0
- JPA/Hibernate for database operations
- Validation for request data
- CORS enabled for frontend integration
