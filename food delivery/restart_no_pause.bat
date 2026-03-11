@echo off
set JAVA_HOME=C:\Program Files\Java\jdk-21
set CATALINA_HOME=C:\tomcat9
set PATH=%JAVA_HOME%\bin;%CATALINA_HOME%\bin;%PATH%
echo Stopping existing Java processes...
taskkill /F /IM java.exe /T 2>nul
echo Deploying FoodExpress...
xcopy /E /I /Y "c:\Users\Vaishu\OneDrive\Documents\Projects\food delivery - Copy\food delivery\WebContent" "C:\tomcat9\webapps\FoodExpress"
xcopy /E /I /Y "c:\Users\Vaishu\OneDrive\Documents\Projects\food delivery - Copy\food delivery\build_classes" "C:\tomcat9\webapps\FoodExpress\WEB-INF\classes"
echo Starting Tomcat...
cd /d C:\tomcat9\bin
call startup.bat
echo Tomcat started.
