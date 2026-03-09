<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Admin Dashboard | FoodExpress</title>
                <link
                    href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Outfit:wght@600;700;800&display=swap"
                    rel="stylesheet">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
            </head>

            <body class="admin-body">
                <div class="admin-layout">
                    <!-- Sidebar -->
                    <aside class="admin-sidebar">
                        <div class="sidebar-header">
                            <span class="logo-icon">🍕</span>
                            <span class="logo-text">FoodExpress</span>
                        </div>
                        <nav class="sidebar-nav">
                            <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-item active">📊
                                Dashboard</a>
                            <a href="${pageContext.request.contextPath}/admin/restaurants" class="nav-item">🏪
                                Restaurants</a>
                            <a href="${pageContext.request.contextPath}/admin/food" class="nav-item">🍔 Food Items</a>
                            <a href="${pageContext.request.contextPath}/admin/orders" class="nav-item">📦 Orders</a>
                            <a href="${pageContext.request.contextPath}/admin/users" class="nav-item">👥 Users</a>
                        </nav>
                        <div class="sidebar-footer">
                            <a href="${pageContext.request.contextPath}/logout" class="nav-item logout">🚪 Logout</a>
                        </div>
                    </aside>

                    <!-- Main Content -->
                    <main class="admin-main">
                        <header class="admin-header">
                            <h1>Dashboard</h1>
                            <span class="admin-user">Welcome, ${sessionScope.userName}</span>
                        </header>

                        <div class="dashboard-content">
                            <!-- Stats Cards -->
                            <div class="stats-grid">
                                <div class="stat-card stat-users">
                                    <div class="stat-icon">👥</div>
                                    <div class="stat-info">
                                        <h3>${totalUsers}</h3>
                                        <p>Total Users</p>
                                    </div>
                                </div>
                                <div class="stat-card stat-restaurants">
                                    <div class="stat-icon">🏪</div>
                                    <div class="stat-info">
                                        <h3>${totalRestaurants}</h3>
                                        <p>Restaurants</p>
                                    </div>
                                </div>
                                <div class="stat-card stat-orders">
                                    <div class="stat-icon">📦</div>
                                    <div class="stat-info">
                                        <h3>${totalOrders}</h3>
                                        <p>Total Orders</p>
                                    </div>
                                </div>
                                <div class="stat-card stat-revenue">
                                    <div class="stat-icon">💰</div>
                                    <div class="stat-info">
                                        <h3>₹
                                            <fmt:formatNumber value="${totalRevenue}" pattern="#,##0" />
                                        </h3>
                                        <p>Total Revenue</p>
                                    </div>
                                </div>
                            </div>

                            <!-- Recent Orders -->
                            <div class="dashboard-section">
                                <h2>Recent Orders</h2>
                                <div class="table-container">
                                    <table class="admin-table">
                                        <thead>
                                            <tr>
                                                <th>Order ID</th>
                                                <th>Customer</th>
                                                <th>Restaurant</th>
                                                <th>Amount</th>
                                                <th>Status</th>
                                                <th>Date</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="order" items="${recentOrders}" begin="0" end="9">
                                                <tr>
                                                    <td>#${order.orderId}</td>
                                                    <td>${order.userName}</td>
                                                    <td>${order.restaurantName}</td>
                                                    <td>₹
                                                        <fmt:formatNumber value="${order.totalAmount}"
                                                            pattern="#,##0.00" />
                                                    </td>
                                                    <td><span
                                                            class="status-badge status-${order.status}">${order.status}</span>
                                                    </td>
                                                    <td>
                                                        <fmt:formatDate value="${order.orderDate}"
                                                            pattern="dd/MM/yyyy" />
                                                    </td>
                                                    <td><a href="${pageContext.request.contextPath}/admin/orders?id=${order.orderId}"
                                                            class="btn-sm">View</a></td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </main>
                </div>
            </body>

            </html>