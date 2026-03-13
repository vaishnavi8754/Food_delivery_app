<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Orders | Admin</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Outfit:wght@600;700&display=swap"
        rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css?v=${System.currentTimeMillis()}">
</head>

<body class="admin-body">
    <div class="admin-layout">
        <aside class="admin-sidebar">
            <div class="sidebar-header">
                <span class="logo-icon">🍕</span>
                <span class="logo-text">FoodExpress</span>
            </div>
            <nav class="sidebar-nav">
                <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-item">
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
                <a href="${pageContext.request.contextPath}/admin/orders" class="nav-item active">
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

        <main class="admin-main">
            <header class="admin-header">
                <h1>Order Management</h1>
                <div class="admin-user">${sessionScope.userName}</div>
            </header>

            <div class="dashboard-content">
                <!-- Stats Briefing -->
                <div class="stats-grid">
                    <c:set var="pendingCount" value="0" />
                    <c:set var="completedCount" value="0" />
                    <c:set var="totalRevenue" value="0" />
                    <c:forEach var="o" items="${orders}">
                        <c:if test="${o.status == 'pending' || o.status == 'Confirmed'}"><c:set var="pendingCount" value="${pendingCount + 1}" /></c:if>
                        <c:if test="${o.status == 'Delivered'}"><c:set var="completedCount" value="${completedCount + 1}" /></c:if>
                        <c:set var="totalRevenue" value="${totalRevenue + o.totalAmount}" />
                    </c:forEach>
                    
                    <div class="stat-card stat-orders">
                        <div class="stat-icon"><i class="fa-solid fa-cart-shopping"></i></div>
                        <div class="stat-info">
                            <h3>${orders.size()}</h3>
                            <p>Total Orders</p>
                        </div>
                    </div>
                    <div class="stat-card stat-restaurants">
                        <div class="stat-icon"><i class="fa-solid fa-spinner"></i></div>
                        <div class="stat-info">
                            <h3>${pendingCount}</h3>
                            <p>Processing</p>
                        </div>
                    </div>
                    <div class="stat-card stat-users">
                        <div class="stat-icon"><i class="fa-solid fa-circle-check"></i></div>
                        <div class="stat-info">
                            <h3>${completedCount}</h3>
                            <p>Completed</p>
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

                <!-- Search & Filter Control Hub -->
                <div class="filter-bar">
                    <form action="${pageContext.request.contextPath}/admin/orders" method="GET" class="search-box">
                        <input type="text" name="search" placeholder="Search by Order ID or Customer..." value="${currentSearch}">
                        <button type="submit" class="btn-filter">
                            <i class="fa-solid fa-magnifying-glass"></i>
                            Search
                        </button>
                    </form>
                    <div class="filter-group">
                        <form action="${pageContext.request.contextPath}/admin/orders" method="GET" id="filterForm" style="display: flex; gap: 0.75rem;">
                            <select name="status" onchange="this.form.submit()">
                                <option value="all">All Status</option>
                                <option value="pending">Pending</option>
                                <option value="Confirmed">Confirmed</option>
                                <option value="Delivered">Delivered</option>
                                <option value="Cancelled">Cancelled</option>
                            </select>
                            <select name="period" onchange="this.form.submit()">
                                <option value="today">Today</option>
                                <option value="week">Past Week</option>
                                <option value="month">Past Month</option>
                                <option value="all">Lifetime</option>
                            </select>
                        </form>
                    </div>
                </div>

                <!-- Orders Table -->
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
                                <th style="text-align: right;">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty orders}">
                                    <c:forEach var="order" items="${orders}">
                                        <tr>
                                            <td style="font-weight: 700; color: var(--primary);">#${order.orderId}</td>
                                            <td>
                                                <div class="user-info-cell">
                                                    <span class="user-name">${order.userName}</span>
                                                </div>
                                            </td>
                                            <td>
                                                <div class="user-info-cell">
                                                    <span class="user-name" style="font-size: 0.85rem;">${order.restaurantName}</span>
                                                </div>
                                            </td>
                                            <td>
                                                <span style="font-weight: 700; color: var(--text-main);">
                                                    ₹<fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00" />
                                                </span>
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
                                                    <c:if test="${order.status != 'Delivered' && order.status != 'Cancelled'}">
                                                        <form action="${pageContext.request.contextPath}/admin/orders/update" method="POST" style="display:inline">
                                                            <input type="hidden" name="id" value="${order.orderId}">
                                                            <input type="hidden" name="status" value="Delivered">
                                                            <button type="submit" class="btn-sm btn-status-toggle btn-status-active" title="Mark as Delivered">
                                                                <i class="fa-solid fa-check"></i>
                                                            </button>
                                                        </form>
                                                    </c:if>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="7">
                                            <div class="empty-state">
                                                <i class="fa-solid fa-box-open"></i>
                                                <h2>No Orders Found</h2>
                                                <p>Try adjusting your search or filters.</p>
                                            </div>
                                        </td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </main>
    </div>
</body>
</html>