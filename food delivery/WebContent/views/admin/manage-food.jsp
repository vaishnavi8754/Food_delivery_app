<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Food Items | Admin</title>
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
                <a href="${pageContext.request.contextPath}/admin/food" class="nav-item active">
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

        <main class="admin-main">
            <header class="admin-header">
                <h1>Food Menu Management</h1>
                <div style="display: flex; align-items: center; gap: 1.5rem;">
                    <a href="${pageContext.request.contextPath}/admin/food?action=add" class="btn-filter" style="text-decoration: none;">
                        <i class="fa-solid fa-plus"></i>
                        Add Food Item
                    </a>
                    <div class="admin-user">${sessionScope.userName}</div>
                </div>
            </header>

            <div class="dashboard-content">
                <!-- Stats Briefing -->
                <div class="stats-grid">
                    <c:set var="vegCount" value="0" />
                    <c:set var="availableCount" value="0" />
                    <c:forEach var="f" items="${foodItems}">
                        <c:if test="${f.vegetarian}"><c:set var="vegCount" value="${vegCount + 1}" /></c:if>
                        <c:if test="${f.available}"><c:set var="availableCount" value="${availableCount + 1}" /></c:if>
                    </c:forEach>
                    
                    <div class="stat-card stat-users">
                        <div class="stat-icon"><i class="fa-solid fa-burger"></i></div>
                        <div class="stat-info">
                            <h3>${foodItems.size()}</h3>
                            <p>Total Items</p>
                        </div>
                    </div>
                    <div class="stat-card stat-restaurants">
                        <div class="stat-icon"><i class="fa-solid fa-leaf"></i></div>
                        <div class="stat-info">
                            <h3>${vegCount}</h3>
                            <p>Vegetarian</p>
                        </div>
                    </div>
                    <div class="stat-card stat-revenue">
                        <div class="stat-icon"><i class="fa-solid fa-drumstick-bite"></i></div>
                        <div class="stat-info">
                            <h3>${foodItems.size() - vegCount}</h3>
                            <p>Non-Veg</p>
                        </div>
                    </div>
                    <div class="stat-card stat-orders">
                        <div class="stat-icon"><i class="fa-solid fa-circle-check"></i></div>
                        <div class="stat-info">
                            <h3>${availableCount}</h3>
                            <p>In Stock</p>
                        </div>
                    </div>
                </div>

                <!-- Search & Filter Control Hub -->
                <div class="filter-bar">
                    <form action="${pageContext.request.contextPath}/admin/food" method="GET" class="search-box">
                        <input type="text" name="search" placeholder="Search by food name or category..." value="${currentSearch}">
                        <button type="submit" class="btn-filter">
                            <i class="fa-solid fa-magnifying-glass"></i>
                            Search
                        </button>
                    </form>
                    <div class="filter-group">
                        <form action="${pageContext.request.contextPath}/admin/food" method="GET" id="filterForm" style="display: flex; gap: 0.75rem;">
                            <select name="category" onchange="this.form.submit()">
                                <option value="all">All Categories</option>
                                <option value="Main Course">Main Course</option>
                                <option value="Starters">Starters</option>
                                <option value="Desserts">Desserts</option>
                                <option value="Beverages">Beverages</option>
                            </select>
                            <select name="type" onchange="this.form.submit()">
                                <option value="all">All Types</option>
                                <option value="veg">Veg Only</option>
                                <option value="non-veg">Non-Veg Only</option>
                            </select>
                        </form>
                    </div>
                </div>

                <!-- Food Items Table -->
                <div class="table-container">
                    <table class="admin-table">
                        <thead>
                            <tr>
                                <th>Food Item</th>
                                <th>Restaurant</th>
                                <th>Category</th>
                                <th>Price</th>
                                <th>Status</th>
                                <th style="text-align: right;">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty foodItems}">
                                    <c:forEach var="food" items="${foodItems}">
                                        <tr>
                                            <td>
                                                <div class="user-avatar-wrapper">
                                                    <div class="user-avatar ${food.vegetarian ? 'food-veg' : 'food-nonveg'}">
                                                        <i class="fa-solid ${food.vegetarian ? 'fa-leaf' : 'fa-drumstick-bite'}"></i>
                                                    </div>
                                                    <div class="user-info-cell">
                                                        <span class="user-name">${food.name} <c:if test="${food.vegetarian}"><span style="color: var(--success); font-size: 0.7rem; margin-left: 5px;">⬤</span></c:if></span>
                                                        <div class="user-meta">
                                                            <span>${food.category}</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>
                                                <span style="font-size: 0.9rem; font-weight: 500;">${food.restaurantName}</span>
                                            </td>
                                            <td>
                                                <span class="role-badge" style="background: var(--bg-main); color: var(--text-muted); border: 1px solid var(--border);">${food.category}</span>
                                            </td>
                                            <td>
                                                <span style="font-weight: 700; color: var(--text-main);">
                                                    ₹<fmt:formatNumber value="${food.price}" pattern="#,##0.00" />
                                                </span>
                                            </td>
                                            <td>
                                                <span class="status-badge ${food.available ? 'status-active' : 'status-blocked'}">
                                                    ${food.available ? 'Available' : 'Unavailable'}
                                                </span>
                                            </td>
                                            <td>
                                                <div class="action-btns" style="justify-content: flex-end;">
                                                    <a href="${pageContext.request.contextPath}/admin/food?action=edit&id=${food.foodId}" class="btn-sm btn-edit" title="Edit Item">
                                                        <i class="fa-solid fa-pen-to-square"></i>
                                                    </a>
                                                    <form action="${pageContext.request.contextPath}/admin/food/delete" method="POST" style="display:inline" onsubmit="return confirm('Delete this food item?')">
                                                        <input type="hidden" name="id" value="${food.foodId}">
                                                        <button type="submit" class="btn-sm btn-delete" title="Delete Item">
                                                            <i class="fa-solid fa-trash-can"></i>
                                                        </button>
                                                    </form>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="6">
                                            <div class="empty-state">
                                                <i class="fa-solid fa-utensils"></i>
                                                <h2>No Food Items Found</h2>
                                                <p>Start by adding some delicious dishes to the menu.</p>
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