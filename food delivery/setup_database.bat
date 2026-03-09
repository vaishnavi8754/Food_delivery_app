@echo off
echo ========================================
echo   FoodExpress Database Setup
echo ========================================
echo.

set /p MYSQL_PASSWORD="Enter your MySQL root password: "

echo.
echo Setting up database...
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql" -u root -p%MYSQL_PASSWORD% < database\food_delivery_db.sql

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo   SUCCESS! Database setup complete!
    echo ========================================
    echo.
    echo Persisting database password to environment variable FOODEXPRESS_DB_PASSWORD...
    setx FOODEXPRESS_DB_PASSWORD "%MYSQL_PASSWORD%" >nul
    if %ERRORLEVEL% EQU 0 (
        echo Environment variable FOODEXPRESS_DB_PASSWORD set successfully.
        echo You can keep DBConnection.java as-is; the app will read the password from FOODEXPRESS_DB_PASSWORD.
    ) else (
        echo Failed to set environment variable persistently. You may update DBConnection.java manually.
    )
    echo.
) else (
    echo.
    echo ERROR: Database setup failed!
    echo Please check your password and try again.
)

pause
