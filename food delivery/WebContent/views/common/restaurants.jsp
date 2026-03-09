<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Restaurants | FoodExpress</title>
            <link
                href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Outfit:wght@600;700;800&display=swap"
                rel="stylesheet">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages.css">
        </head>

        <body>
            <nav class="navbar">
                <div class="nav-container">
                    <a href="${pageContext.request.contextPath}/home" class="nav-logo">
                        <span class="logo-icon">🍕</span>
                        <span class="logo-text">FoodExpress</span>
                    </a>
                    <div class="nav-links">
                        <a href="${pageContext.request.contextPath}/home" class="nav-link">Home</a>
                        <a href="${pageContext.request.contextPath}/restaurants" class="nav-link active">Restaurants</a>
                        <a href="${pageContext.request.contextPath}/cart" class="nav-link cart-link">🛒 Cart
                            <c:if test="${not empty sessionScope.cart}"><span
                                    class="cart-badge">${sessionScope.cart.size()}</span></c:if>
                        </a>
                    </div>
                    <div class="nav-auth">
                        <c:if test="${not empty sessionScope.user}">
                            <a href="${pageContext.request.contextPath}/orders" class="btn btn-outline">My Orders</a>
                            <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline">Logout</a>
                        </c:if>
                        <c:if test="${empty sessionScope.user}">
                            <a href="${pageContext.request.contextPath}/login" class="btn btn-outline">Login</a>
                        </c:if>
                    </div>
                </div>
            </nav>

            <section class="section">
                <div class="container">
                    <div class="page-header">
                        <h1 class="page-title">🍽️ All Restaurants</h1>
                        <form action="${pageContext.request.contextPath}/restaurants" method="GET"
                            class="search-inline">
                            <input type="text" name="search" placeholder="Search..." value="${searchQuery}"
                                class="search-input-sm">
                            <button type="submit" class="btn btn-primary-sm">Search</button>
                        </form>
                    </div>

                    <!-- Cuisine Filters -->
                    <div class="filter-bar">
                        <a href="${pageContext.request.contextPath}/restaurants"
                            class="filter-chip ${empty selectedCuisine ? 'active' : ''}">All</a>
                        <c:forEach var="cuisine" items="${cuisines}">
                            <a href="${pageContext.request.contextPath}/restaurants?cuisine=${cuisine}"
                                class="filter-chip ${selectedCuisine == cuisine ? 'active' : ''}">${cuisine}</a>
                        </c:forEach>
                    </div>

                    <c:if test="${not empty searchQuery}">
                        <p class="search-result">Showing results for: <strong>"${searchQuery}"</strong></p>
                    </c:if>

                    <c:if test="${empty restaurants}">
                        <div class="empty-state">
                            <h3>No restaurants found</h3>
                            <p>Try a different search or filter</p>
                        </div>
                    </c:if>

                    <div class="restaurant-grid">
                        <c:forEach var="restaurant" items="${restaurants}">
                            <div class="restaurant-card">
                                <div class="restaurant-image">
                                    <img src="${not empty restaurant.imageUrl ? restaurant.imageUrl : 'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=400'}"
                                        alt="${restaurant.name}">
                                    <span class="restaurant-rating">⭐ ${restaurant.rating}</span>
                                </div>
                                <div class="restaurant-info">
                                    <h3 class="restaurant-name">${restaurant.name}</h3>
                                    <p class="restaurant-cuisine">${restaurant.cuisineType}</p>
                                    <p class="restaurant-address">📍 ${restaurant.address}</p>
                                    <a href="${pageContext.request.contextPath}/menu?restaurantId=${restaurant.restaurantId}"
                                        class="btn btn-primary btn-block">View Menu</a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </section>

            <footer class="footer">
                <div class="container">
                    <p>&copy; 2026 FoodExpress. All rights reserved.</p>
                </div>
            </footer>
        </body>

        </html>