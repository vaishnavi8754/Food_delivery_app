<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>${empty food ? 'Add' : 'Edit'} Food Item | Admin</title>
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
                        <a href="${pageContext.request.contextPath}/admin/restaurants" class="nav-item">🏪
                            Restaurants</a>
                        <a href="${pageContext.request.contextPath}/admin/food" class="nav-item active">🍔 Food
                            Items</a>
                        <a href="${pageContext.request.contextPath}/admin/orders" class="nav-item">📦 Orders</a>
                        <a href="${pageContext.request.contextPath}/admin/users" class="nav-item">👥 Users</a>
                    </nav>
                </aside>
                <main class="admin-main">
                    <header class="admin-header">
                        <h1>${empty food ? 'Add New' : 'Edit'} Food Item</h1>
                        <a href="${pageContext.request.contextPath}/admin/food" class="btn btn-outline">← Back</a>
                    </header>
                    <div class="dashboard-content">
                        <div class="form-card admin-form">
                            <form action="${pageContext.request.contextPath}/admin/food" method="POST">
                                <c:if test="${not empty food}">
                                    <input type="hidden" name="id" value="${food.foodId}">
                                </c:if>
                                <div class="form-group">
                                    <label class="form-label">Restaurant *</label>
                                    <select name="restaurantId" class="form-input" required>
                                        <option value="">Select Restaurant</option>
                                        <c:forEach var="r" items="${restaurants}">
                                            <option value="${r.restaurantId}" ${food.restaurantId==r.restaurantId
                                                ? 'selected' : '' }>${r.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label class="form-label">Food Name *</label>
                                    <input type="text" name="name" class="form-input" value="${food.name}" required>
                                </div>
                                <div class="form-group">
                                    <label class="form-label">Description</label>
                                    <textarea name="description" class="form-input"
                                        rows="2">${food.description}</textarea>
                                </div>
                                <div class="form-row">
                                    <div class="form-group">
                                        <label class="form-label">Price (₹) *</label>
                                        <input type="number" name="price" class="form-input" value="${food.price}"
                                            step="0.01" min="0" required>
                                    </div>
                                    <div class="form-group">
                                        <label class="form-label">Category *</label>
                                        <input type="text" name="category" class="form-input" value="${food.category}"
                                            placeholder="e.g., Pizza, Burgers" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="form-checkbox-wrapper">
                                        <input type="checkbox" name="isVegetarian" class="form-checkbox"
                                            ${food.vegetarian ? 'checked' : '' }>
                                        <span class="form-checkbox-label">🌱 Vegetarian</span>
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label class="form-checkbox-wrapper">
                                        <input type="checkbox" name="isAvailable" class="form-checkbox" ${empty food ||
                                            food.available ? 'checked' : '' }>
                                        <span class="form-checkbox-label">Available for ordering</span>
                                    </label>
                                </div>
                                <div class="form-actions">
                                    <button type="submit" class="btn btn-primary">${empty food ? 'Add Food Item' :
                                        'Update Food Item'}</button>
                                    <a href="${pageContext.request.contextPath}/admin/food"
                                        class="btn btn-outline">Cancel</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </main>
            </div>
        </body>

        </html>