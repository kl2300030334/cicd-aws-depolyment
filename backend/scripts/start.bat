@echo off
REM Food Delivery Backend Startup Script for Windows

echo Starting Food Delivery Backend...

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Java is not installed. Please install Java 17 or higher.
    pause
    exit /b 1
)

REM Check if Maven is installed
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Maven is not installed. Please install Maven 3.6 or higher.
    pause
    exit /b 1
)

REM Create uploads directory if it doesn't exist
if not exist "uploads" mkdir uploads

REM Build the project
echo Building the project...
mvn clean install -DskipTests

REM Check if build was successful
if %errorlevel% neq 0 (
    echo Build failed. Please check the errors above.
    pause
    exit /b 1
)

REM Start the application
echo Starting the application...
mvn spring-boot:run

pause








