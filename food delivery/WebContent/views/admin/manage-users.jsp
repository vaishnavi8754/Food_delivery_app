<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Manage Users | Admin</title>
                <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap"
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
                            <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-item">📊
                                Dashboard</a>
                            <a href="${pageContext.request.contextPath}/admin/restaurants" class="nav-item">🏪
                                Restaurants</a>
                            <a href="${pageContext.request.contextPath}/admin/food" class="nav-item">🍔 Food Items</a>
                            <a href="${pageContext.request.contextPath}/admin/orders" class="nav-item">📦 Orders</a>
                            <a href="${pageContext.request.contextPath}/admin/users" class="nav-item active">👥
                                Users</a>
                        </nav>
                        <div class="sidebar-footer"><a href="${pageContext.request.contextPath}/logout"
                                class="nav-item logout">🚪 Logout</a></div>
                    </aside>
                    <main class="admin-main">
                        <header class="admin-header">
                            <h1>Manage Users</h1>
                        </header>
                        <div class="dashboard-content">
                            <div class="table-container">
                                <table class="admin-table">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Name</th>
                                            <th>Email</th>
                                            <th>Phone</th>
                                            <th>Role</th>
                                            <th>Joined</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="user" items="${users}">
                                            <tr>
                                                <td>${user.userId}</td>
                                                <td>${user.fullName}</td>
                                                <td>${user.email}</td>
                                                <td>${user.phone}</td>
                                                <td><span class="role-badge role-${user.role}">${user.role}</span></td>
                                                <td>
                                                    <fmt:formatDate value="${user.createdAt}" pattern="dd/MM/yyyy" />
                                                </td>
                                                <td>
                                                    <c:if test="${user.role != 'admin'}">
                                                        <form
                                                            action="${pageContext.request.contextPath}/admin/users/delete"
                                                            method="POST" style="display:inline"
                                                            onsubmit="return confirm('Delete this user?')">
                                                            <input type="hidden" name="id" value="${user.userId}">
                                                            <button type="submit"
                                                                class="btn-sm btn-delete">Delete</button>
                                                        </form>
                                                    </c:if>
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