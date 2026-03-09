# 🍕 FoodExpress - Online Food Delivery Application

A complete web-based Online Food Delivery Application developed using Java JEE technologies following the MVC (Model-View-Controller) architecture.

## 📋 Table of Contents
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
- [Database Setup](#database-setup)
- [Running the Application](#running-the-application)
- [Default Credentials](#default-credentials)
- [URL Mappings](#url-mappings)

## ✨ Features

### User Module
- ✅ User registration and login
- ✅ Browse restaurants and food menus
- ✅ Search restaurants by name or cuisine
- ✅ Filter restaurants by cuisine type
- ✅ Add food items to cart
- ✅ Place and view orders
- ✅ View order history and details

### Admin Module
- ✅ Admin authentication
- ✅ Dashboard with statistics
- ✅ Manage restaurants (CRUD)
- ✅ Manage food items (CRUD)
- ✅ View and manage user orders
- ✅ Update order status
- ✅ Manage users

## 🛠 Technology Stack

| Layer | Technology |
|-------|------------|
| **Front-End** | JSP, HTML5, CSS3, JavaScript |
| **Back-End** | Java JEE, Servlets, JDBC |
| **Database** | MySQL |
| **Server** | Apache Tomcat 9.x |
| **Architecture** | MVC (Model-View-Controller) |
| **Design Pattern** | DAO (Data Access Object) |

## 📁 Project Structure

```
FoodExpress/
├── src/
│   └── com/fooddelivery/
│       ├── model/                    # Java Beans
│       │   ├── User.java
│       │   ├── Restaurant.java
│       │   ├── FoodItem.java
│       │   ├── Order.java
│       │   ├── OrderDetails.java
│       │   └── CartItem.java
│       ├── dao/                      # Data Access Objects
│       │   ├── DBConnection.java
│       │   ├── UserDAO.java
│       │   ├── RestaurantDAO.java
│       │   ├── FoodItemDAO.java
│       │   └── OrderDAO.java
│       └── controller/               # Servlets
│           ├── user/
│           │   ├── LoginServlet.java
│           │   ├── RegisterServlet.java
│           │   ├── LogoutServlet.java
│           │   └── OrderServlet.java
│           ├── common/
│           │   ├── HomeServlet.java
│           │   ├── RestaurantServlet.java
│           │   ├── MenuServlet.java
│           │   └── CartServlet.java
│           └── admin/
│               ├── AdminLoginServlet.java
│               ├── AdminDashboardServlet.java
│               ├── ManageRestaurantServlet.java
│               ├── ManageFoodServlet.java
│               ├── ManageOrderServlet.java
│               └── ManageUserServlet.java
├── WebContent/
│   ├── WEB-INF/
│   │   └── web.xml
│   ├── css/
│   │   ├── style.css
│   │   ├── pages.css
│   │   └── admin.css
│   ├── views/
│   │   ├── user/
│   │   ├── common/
│   │   └── admin/
│   └── index.jsp
└── database/
    └── food_delivery_db.sql
```

## 📋 Prerequisites

1. **Java JDK 8 or higher**
   - Download: https://www.oracle.com/java/technologies/downloads/

2. **Apache Tomcat 9.x**
   - Download: https://tomcat.apache.org/download-90.cgi

3. **MySQL Server 8.x**
   - Download: https://dev.mysql.com/downloads/mysql/

4. **Eclipse IDE for Enterprise Java** (Recommended)
   - Download: https://www.eclipse.org/downloads/

5. **MySQL Connector/J (JDBC Driver)**
   - Download: https://dev.mysql.com/downloads/connector/j/

6. **JSTL Library**
   - Download: https://mvnrepository.com/artifact/javax.servlet/jstl/1.2

## 🚀 Setup Instructions

### Step 1: Clone/Download the Project
```bash
# If using git
git clone <repository-url>

# Or download and extract the ZIP file
```

### Step 2: Import into Eclipse
1. Open Eclipse IDE
2. Go to **File** → **Import** → **Existing Projects into Workspace**
3. Browse to the project folder
4. Click **Finish**

### Step 3: Add Required Libraries
Add the following JAR files to `WebContent/WEB-INF/lib/`:
- `mysql-connector-java-8.x.x.jar`
- `jstl-1.2.jar`

### Step 4: Configure Database Connection
Edit `src/com/fooddelivery/dao/DBConnection.java`:
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/food_delivery_db";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password"; // ← Update this
```

### Step 5: Configure Tomcat Server
1. In Eclipse, go to **Window** → **Show View** → **Servers**
2. Right-click → **New** → **Server**
3. Select **Apache Tomcat v9.0**
4. Browse to your Tomcat installation directory
5. Click **Finish**

## 🗄 Database Setup

### Step 1: Open MySQL Workbench or Command Line

### Step 2: Run the SQL Script
```sql
-- Open and execute the file: database/food_delivery_db.sql
SOURCE /path/to/food_delivery_db.sql;
```

### Step 3: Verify Database
```sql
USE food_delivery_db;
SHOW TABLES;
SELECT * FROM user;
SELECT * FROM restaurant;
```

## ▶️ Running the Application

### Method 1: Using Eclipse
1. Right-click on the project
2. Select **Run As** → **Run on Server**
3. Choose your Tomcat server
4. Click **Finish**

### Method 2: Deploy WAR File
1. Right-click project → **Export** → **WAR file**
2. Copy the WAR file to Tomcat's `webapps/` folder
3. Start Tomcat: `bin/startup.bat` (Windows) or `bin/startup.sh` (Linux/Mac)

### Access the Application
- **Home Page**: http://localhost:8080/FoodExpress/
- **User Login**: http://localhost:8080/FoodExpress/login
- **Admin Login**: http://localhost:8080/FoodExpress/admin/login

## 🔑 Default Credentials

### Admin Account
| Field | Value |
|-------|-------|
| Email | `admin@foodexpress.com` |
| Password | `admin123` |

### Sample User (Register a new account)
| Field | Value |
|-------|-------|
| Email | Any valid email |
| Password | Min 6 characters |

## 🗺 URL Mappings

### User URLs
| URL | Description |
|-----|-------------|
| `/` | Landing page |
| `/login` | User login |
| `/register` | User registration |
| `/logout` | Logout |
| `/home` | Home page with restaurants |
| `/restaurants` | All restaurants |
| `/menu?restaurantId=1` | Restaurant menu |
| `/cart` | Shopping cart |
| `/checkout` | Checkout page |
| `/orders` | Order history |

### Admin URLs
| URL | Description |
|-----|-------------|
| `/admin/login` | Admin login |
| `/admin/dashboard` | Admin dashboard |
| `/admin/restaurants` | Manage restaurants |
| `/admin/food` | Manage food items |
| `/admin/orders` | Manage orders |
| `/admin/users` | Manage users |

## 📸 Screenshots

### User Interface
- Modern, responsive design
- Orange/white color scheme
- Split-screen login/register pages
- Restaurant cards with ratings
- Interactive cart management

### Admin Panel
- Dark sidebar navigation
- Statistics dashboard
- CRUD operations for all entities
- Order status management

## 🔧 Troubleshooting

### Common Issues

1. **Database Connection Failed**
   - Verify MySQL is running
   - Check username/password in DBConnection.java
   - Ensure MySQL Connector JAR is in lib folder

2. **404 Error**
   - Check if Tomcat is running
   - Verify context path in URL
   - Check web.xml servlet mappings

3. **JSTL Tags Not Working**
   - Ensure jstl-1.2.jar is in WEB-INF/lib
   - Add taglib directive at top of JSP files

4. **Session Issues**
   - Clear browser cookies
   - Restart Tomcat server

## 📄 License

This project is created for educational purposes as a Final Year Engineering Project.

## 👥 Authors

- Student Name
- University Name
- Year: 2024

---

**FoodExpress** - Delivering Happiness to Your Doorstep! 🍕🚀
