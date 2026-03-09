<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Menu - ${restaurant.name} | FoodExpress</title>
                <link
                    href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Outfit:wght@600;700;800&display=swap"
                    rel="stylesheet">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages.css">
            </head>

            <body>
                <!-- Navigation -->
                <nav class="navbar">
                    <div class="nav-container">
                        <a href="${pageContext.request.contextPath}/home" class="nav-logo">
                            <span class="logo-icon">🍕</span>
                            <span class="logo-text">FoodExpress</span>
                        </a>
                        <div class="nav-links">
                            <a href="${pageContext.request.contextPath}/home" class="nav-link">Home</a>
                            <a href="${pageContext.request.contextPath}/restaurants" class="nav-link">Restaurants</a>
                            <a href="${pageContext.request.contextPath}/cart" class="nav-link cart-link">
                                🛒 Cart
                                <c:if test="${not empty sessionScope.cart}">
                                    <span class="cart-badge">${sessionScope.cart.size()}</span>
                                </c:if>
                            </a>
                        </div>
                        <div class="nav-auth">
                            <c:if test="${not empty sessionScope.user}">
                                <a href="${pageContext.request.contextPath}/orders" class="btn btn-outline">My
                                    Orders</a>
                                <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline">Logout</a>
                            </c:if>
                            <c:if test="${empty sessionScope.user}">
                                <a href="${pageContext.request.contextPath}/login" class="btn btn-outline">Login</a>
                            </c:if>
                        </div>
                    </div>
                </nav>

                <!-- Restaurant Header -->
                <section class="restaurant-header">
                    <div class="container">
                        <div class="restaurant-header-content">
                            <div class="restaurant-header-info">
                                <h1>${restaurant.name}</h1>
                                <p class="cuisine-badge">${restaurant.cuisineType}</p>
                                <p class="restaurant-desc">${restaurant.description}</p>
                                <div class="restaurant-meta">
                                    <span>⭐ ${restaurant.rating}</span>
                                    <span>📍 ${restaurant.address}</span>
                                    <span>📞 ${restaurant.phone}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <!-- Menu Section -->
                <section class="section menu-section">
                    <div class="container">
                        <c:if test="${empty menuItems}">
                            <div class="empty-state">
                                <p>No menu items available at the moment.</p>
                            </div>
                        </c:if>

                        <c:if test="${not empty menuItems}">
                            <!-- Group by Category -->
                            <c:set var="currentCategory" value="" />
                            <div class="menu-categories-wrapper">
                                <c:forEach var="item" items="${menuItems}">
                                    <c:if test="${item.category != currentCategory}">
                                        <c:set var="currentCategory" value="${item.category}" />
                                        <div class="category-header" id="category-${currentCategory}">
                                            <h2>${currentCategory}</h2>
                                        </div>
                                    </c:if>

                                    <div class="menu-item-row">
                                        <div class="item-details">
                                            <div class="veg-icon-container">
                                                <div class="veg-icon ${item.vegetarian ? 'veg' : 'non-veg'}">
                                                    <div class="dot"></div>
                                                </div>
                                            </div>
                                            <h3 class="item-name">${item.name}</h3>
                                            <div class="item-rating">
                                                <c:if test="${item.rating > 0}">
                                                    <span class="rating-star">★</span>
                                                    <span class="rating-value">${item.rating}</span>
                                                    <span class="rating-count">(${item.ratingCount})</span>
                                                </c:if>
                                            </div>
                                            <div class="item-price">
                                                <span class="currency">₹</span>
                                                <span class="amount">
                                                    <fmt:formatNumber value="${item.price}" pattern="#,###" />
                                                </span>
                                            </div>
                                            <p class="item-description">${item.description}</p>
                                        </div>
                                        <div class="item-media">
                                            <c:if test="${not empty item.imageUrl}">
                                                <div class="item-image">
                                                    <img src="${item.imageUrl}" alt="${item.name}">
                                                </div>
                                            </c:if>
                                            <form action="${pageContext.request.contextPath}/cart/add" method="POST"
                                                class="add-btn-form">
                                                <input type="hidden" name="foodId" value="${item.foodId}">
                                                <input type="hidden" name="quantity" value="1">
                                                <input type="hidden" name="redirect"
                                                    value="/menu?restaurantId=${restaurant.restaurantId}">
                                                <button type="submit" class="add-item-btn">ADD</button>
                                            </form>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>
                </section>

                <!-- Floating Cart Button -->
                <c:if test="${not empty sessionScope.cart}">
                    <a href="${pageContext.request.contextPath}/cart" class="floating-cart">
                        🛒 View Cart (${sessionScope.cart.size()} items)
                    </a>
                </c:if>

                <footer class="footer">
                    <div class="container">
                        <p>&copy; 2026 FoodExpress. All rights reserved.</p>
                    </div>
                </footer>
            </body>

            </html>