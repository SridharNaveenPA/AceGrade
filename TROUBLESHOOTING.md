# AceGrade Troubleshooting Guide

## Connection Error Issues

### Problem: "Connection Error! Unable to connect to the server"

This error typically occurs when the frontend cannot reach the backend API. Here are the steps to resolve it:

### 1. Check Backend Server Status

**Start the Backend Server:**
```bash
cd backend
mvn spring-boot:run
```

**Verify the server is running:**
- Open your browser and go to: `http://localhost:8080/api/auth/health`
- You should see: "Login service is running!"

### 2. Check Database Connection

**Verify PostgreSQL is running:**
- Make sure PostgreSQL is running on your system
- Check if the database `acegrade` exists
- Verify the connection details in `application.properties`

**Run the database setup:**
```sql
-- Execute the database-setup.sql file in pgAdmin4
-- This will create the necessary tables and sample data
```

### 3. Check Port Configuration

**Backend Port:** 8080 (configured in application.properties)
**Frontend:** Should connect to http://localhost:8080

### 4. Test Login Credentials

**Sample test credentials (from database-setup.sql):**
- Email: `john.doe@college.edu`
- Password: `password123`

- Email: `jane.smith@college.edu`
- Password: `password123`

### 5. Common Issues and Solutions

#### Issue: "Connection refused"
**Solution:** Backend server is not running
- Start the backend with `mvn spring-boot:run`

#### Issue: "404 Not Found"
**Solution:** Wrong API endpoint
- Check the URL in login.html (should be `http://localhost:8080/api/auth/login`)

#### Issue: "CORS error"
**Solution:** CORS configuration issue
- The backend is configured to allow CORS from multiple origins
- If still having issues, try opening the frontend from a local server

#### Issue: "Database connection failed"
**Solution:** PostgreSQL not running or wrong credentials
- Check PostgreSQL service is running
- Verify database credentials in application.properties
- Make sure the `acegrade` database exists

### 6. Step-by-Step Testing

1. **Test Backend Health:**
   ```
   GET http://localhost:8080/api/auth/health
   Expected: "Login service is running!"
   ```

2. **Test Database Connection:**
   - Check if you can connect to PostgreSQL
   - Verify the `acegrade` database exists
   - Check if tables `users`, `subjects`, `study_resources` exist

3. **Test Login API:**
   ```
   POST http://localhost:8080/api/auth/login
   Content-Type: application/json
   
   {
     "collegeEmail": "john.doe@college.edu",
     "password": "password123"
   }
   ```

4. **Test Frontend:**
   - Open login.html in browser
   - Try logging in with test credentials
   - Check browser console for any errors

### 7. Debug Information

**Check Backend Logs:**
- Look for any error messages in the console when starting the backend
- Check for database connection errors

**Check Browser Console:**
- Open Developer Tools (F12)
- Look for network errors in the Console tab
- Check the Network tab to see if the API call is being made

### 8. Quick Fix Commands

**Restart Backend:**
```bash
cd backend
mvn clean
mvn spring-boot:run
```

**Reset Database:**
```sql
-- Drop and recreate the database
DROP DATABASE IF EXISTS acegrade;
CREATE DATABASE acegrade;
-- Then run database-setup.sql
```

**Clear Browser Cache:**
- Hard refresh the page (Ctrl+F5)
- Clear browser cache and cookies

### 9. Contact Information

If you're still experiencing issues after following these steps, please provide:
- Backend console logs
- Browser console errors
- Database connection status
- Operating system details
