# Use an image with a JDK to build your project
FROM eclipse-temurin:11-jdk-alpine AS builder

WORKDIR /app
# Copy the source files
COPY src /app/src
COPY WebContent /app/WebContent

# Download servlet-api.jar for compilation since it's provided by Tomcat at runtime
RUN wget https://repo1.maven.org/maven2/javax/servlet/javax.servlet-api/4.0.1/javax.servlet-api-4.0.1.jar -O /app/WebContent/WEB-INF/lib/servlet-api.jar

# Compile the Java classes
RUN mkdir -p /app/WebContent/WEB-INF/classes
RUN find /app/src -name "*.java" > sources.txt
RUN javac -cp "/app/WebContent/WEB-INF/lib/*:/app/WebContent/WEB-INF/classes" -d /app/WebContent/WEB-INF/classes @sources.txt

# Remove the servlet-api.jar so it doesn't conflict with Tomcat's built-in servlet API
RUN rm /app/WebContent/WEB-INF/lib/servlet-api.jar

# Use Tomcat base image for runtime
FROM tomcat:9.0-jdk11-openjdk-slim

# Remove default ROOT application
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copy the completely built webapp to the Tomcat webapps directory as ROOT
COPY --from=builder /app/WebContent /usr/local/tomcat/webapps/ROOT

EXPOSE 8080
CMD ["catalina.sh", "run"]
