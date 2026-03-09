@echo off
echo ========================================
echo   FoodExpress - Start Application
echo ========================================
echo.

:: Set environment variables
set JAVA_HOME=C:\Program Files\Java\jdk-21
set CATALINA_HOME=C:\tomcat9
set PATH=%JAVA_HOME%\bin;%CATALINA_HOME%\bin;%PATH%

:: Copy project to Tomcat webapps
echo Deploying FoodExpress to Tomcat...
xcopy /E /I /Y "%~dp0WebContent" "C:\tomcat9\webapps\FoodExpress"
xcopy /E /I /Y "%~dp0build_classes" "C:\tomcat9\webapps\FoodExpress\WEB-INF\classes"

echo.
echo Starting Tomcat server...
cd /d C:\tomcat9\bin
call startup.bat

echo.
echo ========================================
echo   Application Starting!
echo ========================================
echo.
echo Wait 10-15 seconds, then open:
echo.
echo   Home:  http://localhost:8080/FoodExpress/
echo   Admin: http://localhost:8080/FoodExpress/admin/login
echo.
echo Admin Login:
echo   Email:    admin@foodexpress.com
echo   Password: admin123
echo.
echo ========================================
pause
