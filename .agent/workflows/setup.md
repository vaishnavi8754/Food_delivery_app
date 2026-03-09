---
description: How to set up the FoodExpress food delivery project from scratch
---

# FoodExpress Project Setup Workflow

## Prerequisites Installation

1. **Install Java JDK 8+**
   - Download from: https://www.oracle.com/java/technologies/downloads/
   - Add to PATH: `C:\Program Files\Java\jdk-XX\bin`

2. **Install MySQL Server 8.x**
   - Download from: https://dev.mysql.com/downloads/mysql/
   - Remember your root password

3. **Install Apache Tomcat 9.x**
   - Download from: https://tomcat.apache.org/download-90.cgi
   - Extract to: `C:\tomcat9`

## Project Setup Steps

4. **Add Required JAR Files to `WebContent/WEB-INF/lib/`:**
   - `mysql-connector-java-8.x.x.jar` (from https://dev.mysql.com/downloads/connector/j/)
   - `jstl-1.2.jar` (from https://mvnrepository.com/artifact/javax.servlet/jstl/1.2)

5. **Set up the Database:**
   ```powershell
   mysql -u root -p < database/food_delivery_db.sql
   ```

6. **Configure Database Connection:**
   - Edit `src/com/fooddelivery/dao/DBConnection.java`
   - Update `DB_PASSWORD` with your MySQL password

7. **Run in Eclipse:**
   - Import project → Run on Server → Apache Tomcat 9.0
   - Or deploy to `C:\tomcat9\webapps\FoodExpress`

8. **Access the Application:**
   - Home: http://localhost:8080/FoodExpress/
   - Admin: http://localhost:8080/FoodExpress/admin/login
   - Admin credentials: `admin@foodexpress.com` / `admin123`
