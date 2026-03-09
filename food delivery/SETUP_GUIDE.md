# 🚀 FoodExpress - Quick Setup Guide

Welcome! This guide will help you set up the FoodExpress application on your Windows machine.

---

## 📦 Step 1: Install Required Software

### 1.1 Install Java JDK 8+
1. Download JDK from: https://www.oracle.com/java/technologies/downloads/
2. Run the installer
3. Add Java to PATH:
   - Open "System Properties" → "Advanced" → "Environment Variables"
   - Under "System variables", find "Path" and click "Edit"
   - Add: `C:\Program Files\Java\jdk-XX\bin` (replace XX with your version)

### 1.2 Install MySQL Server
1. Download MySQL from: https://dev.mysql.com/downloads/mysql/
2. Run the installer and follow the wizard
3. Remember your **root password** - you'll need it!
4. Make sure MySQL service is running

### 1.3 Install Apache Tomcat 9.x
1. Download from: https://tomcat.apache.org/download-90.cgi
2. Download the **Windows Service Installer** or **ZIP file**
3. Extract to a folder like: `C:\tomcat9`

### 1.4 Download Required JAR Files
You need to place these JARs in `WebContent/WEB-INF/lib/`:

1. **MySQL Connector/J**
   - Download: https://dev.mysql.com/downloads/connector/j/
   - File: `mysql-connector-java-8.x.x.jar`

2. **JSTL Library**
   - Download: https://mvnrepository.com/artifact/javax.servlet/jstl/1.2
   - File: `jstl-1.2.jar`

---

## 🗄 Step 2: Database Setup

### 2.1 Create the Database
Open MySQL Command Line or MySQL Workbench and run:

```sql
-- Option 1: Run the entire SQL file
SOURCE C:/Users/Vaishu/OneDrive/Documents/Projects/food delivery/food delivery/database/food_delivery_db.sql;

-- Option 2: Or copy-paste the contents of food_delivery_db.sql
```

### 2.2 Verify Database
```sql
USE food_delivery_db;
SHOW TABLES;
```

You should see: `user`, `restaurant`, `food_item`, `order`, `order_details`

---

## ⚙ Step 3: Configure Database Connection

Edit the file: `src/com/fooddelivery/dao/DBConnection.java`

Update these lines with your MySQL credentials:
```java
private static final String DB_PASSWORD = "YOUR_MYSQL_PASSWORD";
```

---

## 🏃 Step 4: Run the Application

### Option A: Using Eclipse IDE (Recommended)
1. Open Eclipse IDE for Enterprise Java
2. Go to **File** → **Import** → **Existing Projects into Workspace**
3. Browse to: `C:\Users\Vaishu\OneDrive\Documents\Projects\food delivery\food delivery`
4. Click **Finish**
5. Right-click project → **Run As** → **Run on Server**
6. Select Apache Tomcat 9.0
7. Click **Finish**

### Option B: Manual Deployment
1. Copy the entire project folder to `C:\tomcat9\webapps\FoodExpress`
2. Start Tomcat: Run `C:\tomcat9\bin\startup.bat`
3. Open browser: http://localhost:8080/FoodExpress/

---

## 🔗 Step 5: Access the Application

| Page | URL |
|------|-----|
| **Home** | http://localhost:8080/FoodExpress/ |
| **User Login** | http://localhost:8080/FoodExpress/login |
| **Admin Login** | http://localhost:8080/FoodExpress/admin/login |

### Default Admin Credentials
- **Email**: `admin@foodexpress.com`
- **Password**: `admin123`

---

## ❗ Troubleshooting

### "java is not recognized"
- Java is not in PATH. Add `C:\Program Files\Java\jdk-XX\bin` to your PATH

### "mysql is not recognized"
- MySQL is not in PATH. Add `C:\Program Files\MySQL\MySQL Server 8.0\bin` to your PATH

### "Database connection failed"
- Make sure MySQL service is running
- Check username/password in `DBConnection.java`
- Ensure `mysql-connector-java-8.x.x.jar` is in `WEB-INF/lib/`

### "JSTL tags not working"
- Make sure `jstl-1.2.jar` is in `WEB-INF/lib/`

---

## 📁 Project Structure Summary

```
food delivery/
├── src/com/fooddelivery/       # Java source code
│   ├── model/                  # Data models (User, Restaurant, etc.)
│   ├── dao/                    # Database access layer
│   └── controller/             # Servlets (MVC controllers)
├── WebContent/
│   ├── WEB-INF/
│   │   ├── web.xml            # Servlet configuration
│   │   └── lib/               # JAR files (add here!)
│   ├── css/                   # Stylesheets
│   ├── views/                 # JSP pages
│   └── index.jsp              # Landing page
└── database/
    └── food_delivery_db.sql   # Database schema
```

---

🎉 **You're all set! Enjoy building with FoodExpress!**
