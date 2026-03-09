<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>${empty restaurant ? 'Add' : 'Edit'} Restaurant | Admin</title>
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
                </aside>
                <main class="admin-main">
                    <header class="admin-header">
                        <h1>${empty restaurant ? 'Add New' : 'Edit'} Restaurant</h1>
                        <a href="${pageContext.request.contextPath}/admin/restaurants" class="btn btn-outline">←
                            Back</a>
                    </header>
                    <div class="dashboard-content">
                        <div class="form-card admin-form">
                            <form action="${pageContext.request.contextPath}/admin/restaurants" method="POST">
                                <c:if test="${not empty restaurant}">
                                    <input type="hidden" name="id" value="${restaurant.restaurantId}">
                                </c:if>
                                <div class="form-group">
                                    <label class="form-label">Restaurant Name *</label>
                                    <input type="text" name="name" class="form-input" value="${restaurant.name}"
                                        required>
                                </div>
                                <div class="form-group">
                                    <label class="form-label">Description</label>
                                    <textarea name="description" class="form-input"
                                        rows="3">${restaurant.description}</textarea>
                                </div>
                                <div class="form-row">
                                    <div class="form-group">
                                        <label class="form-label">Cuisine Type *</label>
                                        <select name="cuisineType" class="form-input" required>
                                            <option value="">Select Cuisine</option>
                                            <option value="Italian" ${restaurant.cuisineType=='Italian' ? 'selected'
                                                : '' }>Italian</option>
                                            <option value="Indian" ${restaurant.cuisineType=='Indian' ? 'selected' : ''
                                                }>Indian</option>
                                            <option value="Chinese" ${restaurant.cuisineType=='Chinese' ? 'selected'
                                                : '' }>Chinese</option>
                                            <option value="Mexican" ${restaurant.cuisineType=='Mexican' ? 'selected'
                                                : '' }>Mexican</option>
                                            <option value="American" ${restaurant.cuisineType=='American' ? 'selected'
                                                : '' }>American</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="form-label">Phone</label>
                                        <input type="tel" name="phone" class="form-input" value="${restaurant.phone}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="form-label">Address *</label>
                                    <input type="text" name="address" class="form-input" value="${restaurant.address}"
                                        required>
                                </div>
                                <div class="form-group">
                                    <label class="form-checkbox-wrapper">
                                        <input type="checkbox" name="isActive" class="form-checkbox" ${empty restaurant
                                            || restaurant.active ? 'checked' : '' }>
                                        <span class="form-checkbox-label">Active (visible to customers)</span>
                                    </label>
                                </div>
                                <div class="form-actions">
                                    <button type="submit" class="btn btn-primary">${empty restaurant ? 'Add Restaurant'
                                        : 'Update Restaurant'}</button>
                                    <a href="${pageContext.request.contextPath}/admin/restaurants"
                                        class="btn btn-outline">Cancel</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </main>
            </div>
        </body>

        </html>