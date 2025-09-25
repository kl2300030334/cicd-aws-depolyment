#!/bin/bash

# Food Delivery Backend Startup Script

echo "Starting Food Delivery Backend..."

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Java is not installed. Please install Java 17 or higher."
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Maven is not installed. Please install Maven 3.6 or higher."
    exit 1
fi

# Check if MySQL is running
if ! pgrep -x "mysqld" > /dev/null; then
    echo "MySQL is not running. Please start MySQL service."
    exit 1
fi

# Create uploads directory if it doesn't exist
mkdir -p uploads

# Build the project
echo "Building the project..."
mvn clean install -DskipTests

# Check if build was successful
if [ $? -ne 0 ]; then
    echo "Build failed. Please check the errors above."
    exit 1
fi

# Start the application
echo "Starting the application..."
mvn spring-boot:run








