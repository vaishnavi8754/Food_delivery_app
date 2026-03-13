@echo off
set "TOMCAT_LIB=C:\tomcat9\lib"
set "WEB_INF_LIB=WebContent\WEB-INF\lib"
set "SRC_DIR=src"
set "BIN_DIR=WebContent\WEB-INF\classes"

if not exist "%BIN_DIR%" mkdir "%BIN_DIR%"

set "CP=%TOMCAT_LIB%\servlet-api.jar;%TOMCAT_LIB%\jsp-api.jar;%WEB_INF_LIB%\mysql-connector-j-8.2.0.jar;%WEB_INF_LIB%\jstl-1.2.jar;%SRC_DIR%"

echo Compiling Java files...
for /r "%SRC_DIR%" %%f in (*.java) do (
    echo Compiling %%f
    javac -cp "%CP%" -d "%BIN_DIR%" "%%f"
)

if %ERRORLEVEL% equ 0 (
    echo Compilation successful!
) else (
    echo Compilation failed with error code %ERRORLEVEL%
)
