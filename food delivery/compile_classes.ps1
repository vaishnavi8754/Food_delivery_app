$javaFiles = Get-ChildItem -Path src -Filter *.java -Recurse | Select-Object -ExpandProperty FullName
$classpath = "build_classes;C:\tomcat9\lib\servlet-api.jar;C:\tomcat9\lib\jsp-api.jar;WebContent\WEB-INF\lib\mysql-connector-j-8.2.0.jar;WebContent\WEB-INF\lib\jstl-1.2.jar"
if (-not (Test-Path build_classes)) { New-Item -ItemType Directory -Path build_classes }
javac -d build_classes -cp $classpath $javaFiles
