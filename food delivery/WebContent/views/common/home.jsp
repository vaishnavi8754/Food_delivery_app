<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Home | FoodExpress</title>
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

                    <div class="location-selector" onclick="alert('Location selection coming soon!')">
                        <span class="location-icon">📍</span>
                        <span class="location-text">Setup your precise location</span>
                        <span class="location-arrow">▼</span>
                    </div>

                    <div class="nav-links">
                        <a href="${pageContext.request.contextPath}/home" class="nav-link active">Home</a>
                        <a href="${pageContext.request.contextPath}/restaurants" class="nav-link">Restaurants</a>
                        <a href="${pageContext.request.contextPath}/cart" class="nav-link cart-link">
                            🛒 Cart
                            <c:if test="${not empty sessionScope.cart}">
                                <span class="cart-badge">${sessionScope.cart.size()}</span>
                            </c:if>
                        </a>
                    </div>
                    <div class="nav-auth">
                        <c:choose>
                            <c:when test="${not empty sessionScope.user}">
                                <span class="user-greeting">Hi, ${sessionScope.userName}</span>
                                <a href="${pageContext.request.contextPath}/orders" class="btn btn-outline">My
                                    Orders</a>
                                <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline">Logout</a>
                            </c:when>
                            <c:otherwise>
                                <a href="${pageContext.request.contextPath}/login" class="btn btn-outline">Login</a>
                                <a href="${pageContext.request.contextPath}/signup" class="btn btn-primary-sm">Sign
                                    Up</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </nav>

            <!-- Hero Section -->
            <section class="hero">
                <div class="hero-wrapper">
                    <div class="hero-background">
                        <img src="https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=1920&q=80"
                            alt="Delicious Food Background">
                    </div>
                    <div class="hero-overlay"></div>
                    <div class="hero-content">
                        <div class="hero-badge">🚀 Fast & Reliable Delivery</div>
                        <h1>Order Online Delivery<br>Restaurants Near Me</h1>
                        <p>Discover the best food & drinks in your city. From local favorites to international cuisines,
                            we've got you covered.</p>
                        <form action="${pageContext.request.contextPath}/restaurants" method="GET" class="search-form">
                            <input type="text" name="search" placeholder="Search for restaurant and food..."
                                class="search-input">
                            <button type="submit" class="btn btn-primary">Search</button>
                        </form>
                    </div>
                </div>
            </section>

            <!-- Food Category Scroller -->
            <section class="category-scroller-section">
                <div class="section-header">
                    <h2 class="section-title-sm">What's on your mind?</h2>
                    <div class="scroller-controls">
                        <button class="scroll-btn"
                            onclick="document.querySelector('.category-scroller').scrollLeft -= 300">←</button>
                        <button class="scroll-btn"
                            onclick="document.querySelector('.category-scroller').scrollLeft += 300">→</button>
                    </div>
                </div>
                <div class="category-scroller">
                    <div class="scroller-item"
                        onclick="location.href='${pageContext.request.contextPath}/restaurants?search=idli'">
                        <div class="scroller-image">
                            <img src="${pageContext.request.contextPath}/images/idli.png" alt="Idli">
                        </div>
                        <span>Idli</span>
                    </div>
                    <div class="scroller-item"
                        onclick="location.href='${pageContext.request.contextPath}/restaurants?search=dosa'">
                        <div class="scroller-image">
                            <img src="${pageContext.request.contextPath}/images/dosa.png" alt="Dosa">
                        </div>
                        <span>Dosa</span>
                    </div>
                    <div class="scroller-item"
                        onclick="location.href='${pageContext.request.contextPath}/restaurants?search=vada'">
                        <div class="scroller-image">
                            <img src="${pageContext.request.contextPath}/images/vada.png" alt="Vada">
                        </div>
                        <span>Vada</span>
                    </div>
                    <div class="scroller-item"
                        onclick="location.href='${pageContext.request.contextPath}/restaurants?search=cakes'">
                        <div class="scroller-image">
                            <img src="${pageContext.request.contextPath}/images/cakes.png" alt="Cakes">
                        </div>
                        <span>Cakes</span>
                    </div>
                    <div class="scroller-item"
                        onclick="location.href='${pageContext.request.contextPath}/restaurants?search=bath'">
                        <div class="scroller-image">
                            <img src="https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=200" alt="Bath">
                        </div>
                        <span>Bath</span>
                    </div>
                    <div class="scroller-item"
                        onclick="location.href='${pageContext.request.contextPath}/restaurants?search=tea'">
                        <div class="scroller-image">
                            <img src="https://images.unsplash.com/photo-1594631252845-29fc4586c566?w=200" alt="Tea">
                        </div>
                        <span>Tea</span>
                    </div>
                    <div class="scroller-item"
                        onclick="location.href='${pageContext.request.contextPath}/restaurants?search=pongal'">
                        <div class="scroller-image">
                            <img src="https://images.unsplash.com/photo-1621658537360-dfcb008fe19f?w=200" alt="Pongal">
                        </div>
                        <span>Pongal</span>
                    </div>
                </div>
            </section>

            <!-- Cuisines Section -->
            <section class="section">
                <div class="container">
                    <h2 class="section-title">Explore Cuisines</h2>
                    <div class="cuisine-grid">
                        <c:forEach var="cuisine" items="${cuisineStats}">
                            <a href="${pageContext.request.contextPath}/restaurants?cuisine=${cuisine.name}"
                                class="cuisine-card">
                                <div class="cuisine-image">
                                    <c:choose>
                                        <c:when test="${not empty cuisine.imageUrl}">
                                            <img src="${cuisine.imageUrl}" alt="${cuisine.name}">
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="cuisineLower" value="${cuisine.name.toLowerCase()}" />
                                            <c:choose>
                                                <c:when test="${cuisineLower == 'italian'}">
                                                    <img src="https://images.unsplash.com/photo-1513104890138-7c749659a591?w=100&h=100&fit=crop"
                                                        alt="Italian">
                                                </c:when>
                                                <c:when test="${cuisineLower == 'indian'}">
                                                    <img src="https://images.unsplash.com/photo-1585937421612-70a008356fbe?w=100&h=100&fit=crop"
                                                        alt="Indian">
                                                </c:when>
                                                <c:when test="${cuisineLower == 'chinese'}">
                                                    <img src="https://images.unsplash.com/photo-1585032226651-759b368d7246?w=100&h=100&fit=crop"
                                                        alt="Chinese">
                                                </c:when>
                                                <c:when test="${cuisineLower == 'mexican'}">
                                                    <img src="https://images.unsplash.com/photo-1565299585323-38d6b0865b47?w=100&h=100&fit=crop"
                                                        alt="Mexican">
                                                </c:when>
                                                <c:when test="${cuisineLower == 'american'}">
                                                    <img src="https://images.unsplash.com/photo-1550547660-d9450f859349?w=100&h=100&fit=crop"
                                                        alt="American">
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="cuisine-placeholder">🍽️</div>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <span class="cuisine-name">${cuisine.name}</span>
                                <span class="cuisine-stats">${cuisine.restaurantCount} Restaurants, ${cuisine.dishCount}
                                    Dishes</span>
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </section>

            <!-- Restaurants Section -->
            <section class="section section-gray">
                <div class="container">
                    <h2 class="section-title">Popular Restaurants</h2>
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

            <!-- Footer -->
            <footer class="footer">
                <div class="container">
                    <div class="footer-content">
                        <div class="footer-brand">
                            <span class="logo-icon">🍕</span>
                            <span class="logo-text">FoodExpress</span>
                            <p>Delivering happiness to your doorstep</p>
                        </div>
                        <div class="footer-links">
                            <h4>Quick Links</h4>
                            <div class="footer-links-container">
                                <a href="${pageContext.request.contextPath}/home">Home</a>
                                <a href="${pageContext.request.contextPath}/restaurants">Restaurants</a>
                                <a href="${pageContext.request.contextPath}/orders">My Orders</a>
                                <a href="${pageContext.request.contextPath}/cart">View Cart</a>
                            </div>
                        </div>
                    </div>
                    <div class="footer-bottom">
                        <p>&copy; 2026 FoodExpress. All rights reserved.</p>
                    </div>
                </div>
            </footer>
        </body>

        </html>