@echo off
echo Starting AceGrade Backend Server...
echo.

echo Checking if Maven is installed...
mvn --version
if %errorlevel% neq 0 (
    echo Maven is not installed or not in PATH
    echo Please install Maven and try again
    pause
    exit /b 1
)

echo.
echo Starting Spring Boot application...
echo Backend will be available at: http://localhost:8080
echo Health check: http://localhost:8080/api/auth/health
echo.

cd backend
mvn spring-boot:run

pause
