# 🚀 FoodExpress - Quick Start Guide

## ✅ What's Already Done For You

| Component | Status | Location |
|-----------|--------|----------|
| **Java JDK 21** | ✅ Installed | `C:\Program Files\Java\jdk-21` |
| **MySQL Server 8.0** | ✅ Running | Port 3306 |
| **Apache Tomcat 9.0.113** | ✅ Installed | `C:\tomcat9` |
| **MySQL Connector JAR** | ✅ Downloaded | `WebContent/WEB-INF/lib/` |
| **JSTL JAR** | ✅ Downloaded | `WebContent/WEB-INF/lib/` |
| **Eclipse Project Files** | ✅ Created | `.project`, `.classpath` |

---

## 🔐 YOU Need To Do (2 Simple Steps)

### Step 1: Set Up Database
1. Open **MySQL Workbench** (search in Windows Start Menu)
2. Connect to your local MySQL server
3. Go to **File** → **Open SQL Script**
4. Open: `database/food_delivery_db.sql`
5. Click the ⚡ **Execute** button (lightning icon)

### Step 2: Update Your Password
1. Open file: `src/com/fooddelivery/dao/DBConnection.java`
2. Find **line 16**
3. Change:
   ```java
   private static final String DB_PASSWORD = "";
   ```
   To:
   ```java
   private static final String DB_PASSWORD = "YOUR_MYSQL_PASSWORD";
   ```

---

## ▶️ Run The Application

### Option A: Using Eclipse (Recommended)
1. Download Eclipse IDE for Enterprise Java: https://www.eclipse.org/downloads/
2. Open Eclipse
3. Go to **File** → **Import** → **Existing Projects into Workspace**
4. Browse to: `C:\Users\Vaishu\OneDrive\Documents\Projects\food delivery\food delivery`
5. Click **Finish**
6. Go to **Window** → **Show View** → **Servers**
7. Right-click → **New** → **Server** → Select **Apache Tomcat v9.0**
8. Browse to `C:\tomcat9` and click **Finish**
9. Right-click project → **Run As** → **Run on Server**

### Option B: Direct Deployment (Alternative)
1. Double-click `start_app.bat` in the project folder

---

## 🌐 Access The Application

| Page | URL |
|------|-----|
| **Home** | http://localhost:8080/FoodExpress/ |
| **User Login** | http://localhost:8080/FoodExpress/login |
| **Admin Panel** | http://localhost:8080/FoodExpress/admin/login |

### Admin Credentials
- **Email**: `admin@foodexpress.com`
- **Password**: `admin123`

---

## 📁 Project Files Summary

```
food delivery/
├── src/                    # Java source code
├── WebContent/
│   ├── WEB-INF/
│   │   ├── lib/           # ✅ JARs ready
│   │   └── web.xml        # Servlet config
│   ├── css/               # Stylesheets
│   ├── views/             # JSP pages
│   └── index.jsp          # Landing page
├── database/
│   └── food_delivery_db.sql  # Run this in MySQL
├── .project               # ✅ Eclipse project file
├── .classpath             # ✅ Eclipse classpath
├── setup_database.bat     # Database setup script
└── start_app.bat          # Quick start script
```

---

🎉 **That's it! Just do the 2 steps above and you're ready to go!**
