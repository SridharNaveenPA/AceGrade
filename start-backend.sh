#!/bin/bash

echo "Starting AceGrade Backend Server..."
echo

echo "Checking if Maven is installed..."
if ! command -v mvn &> /dev/null; then
    echo "Maven is not installed or not in PATH"
    echo "Please install Maven and try again"
    exit 1
fi

echo "Maven version:"
mvn --version
echo

echo "Starting Spring Boot application..."
echo "Backend will be available at: http://localhost:8080"
echo "Health check: http://localhost:8080/api/auth/health"
echo

cd backend
mvn spring-boot:run
