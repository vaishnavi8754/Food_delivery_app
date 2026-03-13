<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Restaurants | Admin</title>
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
                <a href="${pageContext.request.contextPath}/admin/restaurants" class="nav-item active">
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

        <main class="admin-main">
            <header class="admin-header">
                <h1>Restaurant Management</h1>
                <div style="display: flex; align-items: center; gap: 1.5rem;">
                    <a href="${pageContext.request.contextPath}/admin/restaurants?action=add" class="btn-filter" style="text-decoration: none;">
                        <i class="fa-solid fa-plus"></i>
                        Add Restaurant
                    </a>
                    <div class="admin-user">${sessionScope.userName}</div>
                </div>
            </header>

            <div class="dashboard-content">
                <!-- Stats Briefing -->
                <div class="stats-grid">
                    <c:set var="activeCount" value="0" />
                    <c:set var="topRatedCount" value="0" />
                    <c:forEach var="r" items="${restaurants}">
                        <c:if test="${r.active}"><c:set var="activeCount" value="${activeCount + 1}" /></c:if>
                        <c:if test="${r.rating >= 4.5}"><c:set var="topRatedCount" value="${topRatedCount + 1}" /></c:if>
                    </c:forEach>
                    
                    <div class="stat-card stat-users">
                        <div class="stat-icon"><i class="fa-solid fa-shop"></i></div>
                        <div class="stat-info">
                            <h3>${restaurants.size()}</h3>
                            <p>Total Shops</p>
                        </div>
                    </div>
                    <div class="stat-card stat-restaurants">
                        <div class="stat-icon"><i class="fa-solid fa-circle-check"></i></div>
                        <div class="stat-info">
                            <h3>${activeCount}</h3>
                            <p>Active</p>
                        </div>
                    </div>
                    <div class="stat-card stat-revenue">
                        <div class="stat-icon"><i class="fa-solid fa-star"></i></div>
                        <div class="stat-info">
                            <h3>${topRatedCount}</h3>
                            <p>Top Rated</p>
                        </div>
                    </div>
                    <div class="stat-card stat-orders">
                        <div class="stat-icon"><i class="fa-solid fa-clock-rotate-left"></i></div>
                        <div class="stat-info">
                            <h3>${restaurants.size() - activeCount}</h3>
                            <p>Pending/Closed</p>
                        </div>
                    </div>
                </div>

                <!-- Search & Filter Control Hub -->
                <div class="filter-bar">
                    <form action="${pageContext.request.contextPath}/admin/restaurants" method="GET" class="search-box">
                        <input type="text" name="search" placeholder="Search by restaurant name or cuisine..." value="${currentSearch}">
                        <button type="submit" class="btn-filter">
                            <i class="fa-solid fa-magnifying-glass"></i>
                            Search
                        </button>
                    </form>
                    <div class="filter-group">
                        <form action="${pageContext.request.contextPath}/admin/restaurants" method="GET" id="filterForm" style="display: flex; gap: 0.75rem;">
                            <select name="type" onchange="this.form.submit()">
                                <option value="all">All Cuisines</option>
                                <option value="Indian">Indian</option>
                                <option value="American">American</option>
                                <option value="Italian">Italian</option>
                                <option value="Chinese">Chinese</option>
                            </select>
                            <select name="status" onchange="this.form.submit()">
                                <option value="all">All Status</option>
                                <option value="active">Active</option>
                                <option value="inactive">Inactive</option>
                            </select>
                        </form>
                    </div>
                </div>

                <!-- Restaurants Table -->
                <div class="table-container">
                    <table class="admin-table">
                        <thead>
                            <tr>
                                <th>Restaurant</th>
                                <th>Cuisine</th>
                                <th>Rating</th>
                                <th>Status</th>
                                <th style="text-align: right;">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty restaurants}">
                                    <c:forEach var="res" items="${restaurants}">
                                        <tr>
                                            <td>
                                                <div class="user-avatar-wrapper">
                                                    <div class="user-avatar" style="background: linear-gradient(135deg, #6366f1, #a855f7);">
                                                        ${res.name.substring(0,1)}
                                                    </div>
                                                    <div class="user-info-cell">
                                                        <span class="user-name">${res.name}</span>
                                                        <div class="user-meta">
                                                            <span><i class="fa-solid fa-location-dot"></i> ${res.address}</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                            <td><span class="role-badge" style="background: var(--bg-main); color: var(--text-muted); border: 1px solid var(--border);">${res.cuisineType}</span></td>
                                            <td>
                                                <div style="display: flex; align-items: center; gap: 0.5rem; font-weight: 700;">
                                                    <i class="fa-solid fa-star" style="color: #f59e0b;"></i>
                                                    ${res.rating}
                                                </div>
                                            </td>
                                            <td>
                                                <span class="status-badge ${res.active ? 'status-active' : 'status-blocked'}">
                                                    ${res.active ? 'Active' : 'Inactive'}
                                                </span>
                                            </td>
                                            <td>
                                                <div class="action-btns" style="justify-content: flex-end;">
                                                    <a href="${pageContext.request.contextPath}/admin/restaurants?action=edit&id=${res.restaurantId}" class="btn-sm btn-edit" title="Edit Restaurant">
                                                        <i class="fa-solid fa-pen-to-square"></i>
                                                    </a>
                                                    <form action="${pageContext.request.contextPath}/admin/restaurants/delete" method="POST" style="display:inline" onsubmit="return confirm('Delete this restaurant and all its menu items?')">
                                                        <input type="hidden" name="id" value="${res.restaurantId}">
                                                        <button type="submit" class="btn-sm btn-delete" title="Delete Restaurant">
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
                                        <td colspan="5">
                                            <div class="empty-state">
                                                <i class="fa-solid fa-shop-slash"></i>
                                                <h2>No Restaurants Found</h2>
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