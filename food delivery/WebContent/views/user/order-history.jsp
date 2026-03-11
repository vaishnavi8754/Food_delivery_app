<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>My Orders | FoodExpress</title>
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
                                    <a href="${pageContext.request.contextPath}/orders"
                                        class="btn btn-outline active">My
                                        Orders</a>
                                    <a href="${pageContext.request.contextPath}/logout"
                                        class="btn btn-outline">Logout</a>
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

                <section class="section">
                    <div class="container">
                        <h1 class="page-title">📋 My Orders</h1>

                        <c:if test="${param.success == 'true'}">
                            <div class="alert alert-success">
                                <span>✅ Order placed successfully!</span>
                            </div>
                        </c:if>

                        <c:if test="${empty orders}">
                            <div class="empty-state">
                                <span class="empty-icon">📋</span>
                                <h3>No orders yet</h3>
                                <p>Start ordering delicious food!</p>
                                <a href="${pageContext.request.contextPath}/restaurants" class="btn btn-primary">Browse
                                    Restaurants</a>
                            </div>
                        </c:if>

                        <div class="orders-list">
                            <c:forEach var="order" items="${orders}">
                                <div class="order-card">
                                    <div class="order-header">
                                        <div>
                                            <h3>Order #${order.orderId}</h3>
                                            <p class="order-date">
                                                <fmt:formatDate value="${order.orderDate}"
                                                    pattern="dd MMM yyyy, hh:mm a" />
                                            </p>
                                        </div>
                                        <span class="status-badge status-${order.status}">${order.status}</span>
                                    </div>
                                    <div class="order-body">
                                        <p><strong>Restaurant:</strong> ${order.restaurantName}</p>
                                        <p><strong>Total:</strong> ₹
                                            <fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00" />
                                        </p>
                                        <p><strong>Payment:</strong> ${order.paymentMethod}</p>
                                    </div>
                                    <div class="order-footer">
                                        <a href="${pageContext.request.contextPath}/orders?id=${order.orderId}"
                                            class="btn btn-outline">View Details</a>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </section>
            </body>

            </html>