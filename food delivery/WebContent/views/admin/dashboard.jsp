<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard | FoodExpress</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Outfit:wght@600;700&display=swap"
        rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css?v=${System.currentTimeMillis()}">
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
                <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-item active">
                    <i class="fa-solid fa-chart-pie"></i>
                    <span>Dashboard</span>
                </a>
                <a href="${pageContext.request.contextPath}/admin/restaurants" class="nav-item">
                    <i class="fa-solid fa-shop"></i>
                    <span>Restaurants</span>
                </a>
                <a href="${pageContext.request.contextPath}/admin/food" class="nav-item">
                    <i class="fa-solid fa-burger"></i>
                    <span>Food Items</span>
                </a>
                <a href="${pageContext.request.contextPath}/admin/orders" class="nav-item">
                    <i class="fa-solid fa-box"></i>
                    <span>Orders</span>
                </a>
                <a href="${pageContext.request.contextPath}/admin/users" class="nav-item">
                    <i class="fa-solid fa-users"></i>
                    <span>Users</span>
                </a>
            </nav>
            <div class="sidebar-footer">
                <a href="${pageContext.request.contextPath}/logout" class="nav-item logout">
                    <i class="fa-solid fa-right-from-bracket"></i>
                    <span>Logout</span>
                </a>
            </div>
        </aside>

        <!-- Main Content -->
        <main class="admin-main">
            <header class="admin-header">
                <div>
                    <h1>Dashboard Overview</h1>
                    <p style="margin: 0.25rem 0 0; color: var(--text-muted); font-size: 0.9rem;">Welcome back, ${sessionScope.userName}</p>
                </div>
                <div class="admin-user">Active Admin</div>
            </header>

            <div class="dashboard-content">
                <!-- Stats Cards -->
                <div class="stats-grid">
                    <div class="stat-card stat-users">
                        <div class="stat-icon"><i class="fa-solid fa-users"></i></div>
                        <div class="stat-info">
                            <h3>${totalUsers}</h3>
                            <p>Total Users</p>
                        </div>
                    </div>
                    <div class="stat-card stat-restaurants">
                        <div class="stat-icon"><i class="fa-solid fa-shop"></i></div>
                        <div class="stat-info">
                            <h3>${totalRestaurants}</h3>
                            <p>Restaurants</p>
                        </div>
                    </div>
                    <div class="stat-card stat-orders">
                        <div class="stat-icon"><i class="fa-solid fa-cart-shopping"></i></div>
                        <div class="stat-info">
                            <h3>${totalOrders}</h3>
                            <p>Orders</p>
                        </div>
                    </div>
                    <div class="stat-card stat-revenue">
                        <div class="stat-icon"><i class="fa-solid fa-indian-rupee-sign"></i></div>
                        <div class="stat-info">
                            <h3>₹<fmt:formatNumber value="${totalRevenue}" pattern="#,##0" /></h3>
                            <p>Revenue</p>
                        </div>
                    </div>
                </div>

                <!-- Recent Activity Section -->
                <div style="display: grid; grid-template-columns: 1fr; gap: 2rem; margin-top: 1rem;">
                    <div class="dashboard-section">
                        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.5rem;">
                            <h2 style="margin: 0; font-family: 'Outfit', sans-serif;">Recent Orders</h2>
                            <a href="${pageContext.request.contextPath}/admin/orders" style="color: var(--primary); font-weight: 600; text-decoration: none; font-size: 0.9rem;">View All <i class="fa-solid fa-arrow-right"></i></a>
                        </div>
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
                                        <th style="text-align: right;">Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="order" items="${recentOrders}" begin="0" end="7">
                                        <tr>
                                            <td style="font-weight: 700; color: var(--primary);">#${order.orderId}</td>
                                            <td>${order.userName}</td>
                                            <td>
                                                <span style="font-size: 0.85rem;">${order.restaurantName}</span>
                                            </td>
                                            <td>
                                                <span style="font-weight: 700;">₹<fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00" /></span>
                                            </td>
                                            <td>
                                                <c:set var="sClass" value="status-suspended" />
                                                <c:if test="${order.status == 'Delivered'}"><c:set var="sClass" value="status-active" /></c:if>
                                                <c:if test="${order.status == 'Cancelled'}"><c:set var="sClass" value="status-blocked" /></c:if>
                                                <span class="status-badge ${sClass}">${order.status}</span>
                                            </td>
                                            <td style="color: var(--text-muted); font-size: 0.85rem;">
                                                <fmt:formatDate value="${order.orderDate}" pattern="dd MMM, HH:mm" />
                                            </td>
                                            <td>
                                                <div class="action-btns" style="justify-content: flex-end;">
                                                    <a href="${pageContext.request.contextPath}/admin/orders?id=${order.orderId}" class="btn-sm btn-edit" title="View Details">
                                                        <i class="fa-solid fa-eye"></i>
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</body>
</html>