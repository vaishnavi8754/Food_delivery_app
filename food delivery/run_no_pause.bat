@echo off
set JAVA_HOME=C:\Program Files\Java\jdk-21
set CATALINA_HOME=C:\tomcat9
set PATH=%JAVA_HOME%\bin;%CATALINA_HOME%\bin;%PATH%

echo Deploying FoodExpress to Tomcat...
xcopy /E /I /Y "WebContent" "C:\tomcat9\webapps\FoodExpress"
xcopy /E /I /Y "build_classes" "C:\tomcat9\webapps\FoodExpress\WEB-INF\classes"

echo Starting Tomcat server...
cd /d C:\tomcat9\bin
call startup.bat

echo Application Starting!
echo Home:  http://localhost:8080/FoodExpress/
