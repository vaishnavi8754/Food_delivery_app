<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Manage Restaurants | Admin</title>
            <link
                href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Outfit:wght@600;700;800&display=swap"
                rel="stylesheet">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
        </head>

        <body class="admin-body">
            <div class="admin-layout">
                <aside class="admin-sidebar">
                    <div class="sidebar-header"><span class="logo-icon">🍕</span><span
                            class="logo-text">FoodExpress</span></div>
                    <nav class="sidebar-nav">
                        <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-item">📊 Dashboard</a>
                        <a href="${pageContext.request.contextPath}/admin/restaurants" class="nav-item active">🏪
                            Restaurants</a>
                        <a href="${pageContext.request.contextPath}/admin/food" class="nav-item">🍔 Food Items</a>
                        <a href="${pageContext.request.contextPath}/admin/orders" class="nav-item">📦 Orders</a>
                        <a href="${pageContext.request.contextPath}/admin/users" class="nav-item">👥 Users</a>
                    </nav>
                    <div class="sidebar-footer"><a href="${pageContext.request.contextPath}/logout"
                            class="nav-item logout">🚪 Logout</a></div>
                </aside>
                <main class="admin-main">
                    <header class="admin-header">
                        <h1>Manage Restaurants</h1>
                        <a href="${pageContext.request.contextPath}/admin/restaurants?action=add"
                            class="btn btn-primary">+ Add Restaurant</a>
                    </header>
                    <div class="dashboard-content">
                        <div class="table-container">
                            <table class="admin-table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Cuisine</th>
                                        <th>Address</th>
                                        <th>Rating</th>
                                        <th>Status</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="r" items="${restaurants}">
                                        <tr>
                                            <td>${r.restaurantId}</td>
                                            <td>${r.name}</td>
                                            <td>${r.cuisineType}</td>
                                            <td>${r.address}</td>
                                            <td>⭐ ${r.rating}</td>
                                            <td><span
                                                    class="status-badge ${r.active ? 'status-confirmed' : 'status-cancelled'}">${r.active
                                                    ? 'Active' : 'Inactive'}</span></td>
                                            <td class="action-btns">
                                                <a href="${pageContext.request.contextPath}/admin/restaurants?action=edit&id=${r.restaurantId}"
                                                    class="btn-sm btn-edit">Edit</a>
                                                <form
                                                    action="${pageContext.request.contextPath}/admin/restaurants/delete"
                                                    method="POST" style="display:inline"
                                                    onsubmit="return confirm('Delete this restaurant?')">
                                                    <input type="hidden" name="id" value="${r.restaurantId}">
                                                    <button type="submit" class="btn-sm btn-delete">Delete</button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </main>
            </div>
        </body>

        </html>