# AceGrade Login System Setup Guide

This guide will help you set up the complete AceGrade login system with PostgreSQL database, Spring Boot backend, and HTML frontend.

## System Architecture

- **Frontend**: HTML/CSS/JavaScript (Port: Static files)
- **Backend**: Spring Boot (Port: 8080)
- **Database**: PostgreSQL (Port: 5432)

## Prerequisites

1. **Java 17+** - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
2. **Maven 3.6+** - Download from [Apache Maven](https://maven.apache.org/download.cgi)
3. **PostgreSQL 12+** - Download from [PostgreSQL](https://www.postgresql.org/download/)
4. **pgAdmin 4** - Download from [pgAdmin](https://www.pgadmin.org/download/)

## Step 1: Database Setup

### 1.1 Install and Start PostgreSQL
1. Install PostgreSQL on your system
2. Start the PostgreSQL service
3. Open pgAdmin 4 and connect to your PostgreSQL server

### 1.2 Create Database and Table
1. In pgAdmin 4, create a new database named `acegrade`
2. Run the following SQL script in the Query Tool:

```sql
-- Create users table
CREATE TABLE users (
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
('Mike', 'Johnson', '20IT001', 'Information Technology', 'mike.johnson@college.edu', 'password123');
```

### 1.3 Note Database Credentials
- Database Name: `acegrade`
- Username: `postgres` (or your PostgreSQL username)
- Password: Your PostgreSQL password
- Port: `5432` (default)

## Step 2: Backend Setup

### 2.1 Configure Database Connection
1. Open `backend/src/main/resources/application.properties`
2. Update the database credentials:

```properties
# Update these values with your PostgreSQL credentials
spring.datasource.username=postgres
spring.datasource.password=your_postgres_password
```

### 2.2 Build and Run Backend
1. Open terminal/command prompt
2. Navigate to the backend directory:
   ```bash
   cd backend
   ```
3. Build the project:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The backend will start on `http://localhost:8080`

### 2.3 Test Backend
Open your browser and go to `http://localhost:8080/api/auth/health`
You should see: `"Login service is running!"`

## Step 3: Frontend Setup

### 3.1 Serve Frontend Files
You can serve the frontend files in several ways:

#### Option A: Using Python (if installed)
```bash
# Navigate to frontend directory
cd frontend

# Python 3
python -m http.server 8000

# Python 2
python -m SimpleHTTPServer 8000
```

#### Option B: Using Node.js (if installed)
```bash
# Install http-server globally
npm install -g http-server

# Navigate to frontend directory
cd frontend

# Start server
http-server -p 8000
```

#### Option C: Using Live Server (VS Code Extension)
1. Install "Live Server" extension in VS Code
2. Right-click on `frontend/index.html`
3. Select "Open with Live Server"

### 3.2 Access the Application
1. Open your browser
2. Go to `http://localhost:8000` (or the port you're using)
3. Click "Sign In" to go to the login page

## Step 4: Testing the Login System

### 4.1 Test Login
1. Go to the login page
2. Use one of the test credentials:
   - Email: `john.doe@college.edu`
   - Password: `password123`
3. Click "LOGIN"
4. You should see a success message and be redirected to the dashboard

### 4.2 Test Invalid Login
1. Try with wrong credentials
2. You should see an error message

## Troubleshooting

### Common Issues

1. **Database Connection Failed**
   - Check if PostgreSQL is running
   - Verify database credentials in `application.properties`
   - Ensure database `acegrade` exists

2. **Backend Won't Start**
   - Check if port 8080 is available
   - Verify Java and Maven are installed correctly
   - Check console for error messages

3. **Frontend Can't Connect to Backend**
   - Ensure backend is running on port 8080
   - Check browser console for CORS errors
   - Verify you're serving the frontend from an HTTP server (not file://)

4. **CORS Errors**
   - Make sure you're accessing the frontend via HTTP server
   - Check that backend CORS configuration allows your frontend origin

### Port Conflicts

If port 8080 is in use:
1. Change the port in `backend/src/main/resources/application.properties`:
   ```properties
   server.port=8081
   ```
2. Update the frontend JavaScript in `login.html`:
   ```javascript
   const response = await fetch('http://localhost:8081/api/auth/login', {
   ```

## File Structure

```
AceGrade/
├── backend/
│   ├── src/main/java/com/acegrade/
│   │   ├── AceGradeApplication.java
│   │   ├── entity/User.java
│   │   ├── repository/UserRepository.java
│   │   ├── controller/LoginController.java
│   │   └── dto/
│   │       ├── LoginRequest.java
│   │       └── LoginResponse.java
│   ├── src/main/resources/
│   │   └── application.properties
│   ├── pom.xml
│   ├── database-setup.sql
│   └── README.md
├── frontend/
│   ├── index.html
│   ├── login.html
│   ├── dashboard.html
│   ├── styles.css
│   └── FONTS/ and IMAGES/ directories
└── SETUP_GUIDE.md
```

## Security Notes

⚠️ **Important Security Considerations:**

1. **Password Storage**: The current implementation stores passwords in plain text. In production, use proper password hashing (BCrypt).

2. **HTTPS**: Use HTTPS in production for secure communication.

3. **Input Validation**: The backend includes basic validation, but consider additional security measures.

4. **Database Security**: Ensure your PostgreSQL database is properly secured with strong passwords.

## Next Steps

1. **Add Password Hashing**: Implement BCrypt for password security
2. **Add Session Management**: Implement proper session handling
3. **Add Registration**: Create user registration functionality
4. **Add Password Reset**: Implement password reset functionality
5. **Add Input Sanitization**: Prevent SQL injection and XSS attacks

## Support

If you encounter any issues:
1. Check the console logs for error messages
2. Verify all prerequisites are installed correctly
3. Ensure all services (PostgreSQL, backend) are running
4. Check network connectivity between frontend and backend
