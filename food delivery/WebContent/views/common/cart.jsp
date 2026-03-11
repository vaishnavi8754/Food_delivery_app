<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Cart | FoodExpress</title>
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
                            <a href="${pageContext.request.contextPath}/cart" class="nav-link active">
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
                        <h1 class="page-title">🛒 Your Cart</h1>

                        <c:choose>
                            <c:when test="${empty cartItems}">
                                <div class="empty-state">
                                    <span class="empty-icon">🛒</span>
                                    <h3>Your cart is empty</h3>
                                    <p>Add some delicious items to get started!</p>
                                    <a href="${pageContext.request.contextPath}/restaurants"
                                        class="btn btn-primary">Browse Restaurants</a>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="cart-layout">
                                    <div class="cart-items">
                                        <c:forEach var="item" items="${cartItems}">
                                            <div class="cart-item">
                                                <div class="cart-item-info">
                                                    <h4>${item.foodName}</h4>
                                                    <p class="cart-item-restaurant">${item.restaurantName}</p>
                                                    <p class="cart-item-price">₹
                                                        <fmt:formatNumber value="${item.price}" pattern="#,##0.00" />
                                                        each
                                                    </p>
                                                </div>
                                                <div class="cart-item-actions">
                                                    <form action="${pageContext.request.contextPath}/cart/update"
                                                        method="POST" class="quantity-form">
                                                        <input type="hidden" name="foodId" value="${item.foodId}">
                                                        <button type="submit" name="quantity"
                                                            value="${item.quantity - 1}" class="qty-btn">−</button>
                                                        <span class="qty-value">${item.quantity}</span>
                                                        <button type="submit" name="quantity"
                                                            value="${item.quantity + 1}" class="qty-btn">+</button>
                                                    </form>
                                                    <span class="cart-item-subtotal">₹
                                                        <fmt:formatNumber value="${item.subtotal}" pattern="#,##0.00" />
                                                    </span>
                                                    <form action="${pageContext.request.contextPath}/cart/remove"
                                                        method="POST">
                                                        <input type="hidden" name="foodId" value="${item.foodId}">
                                                        <button type="submit" class="remove-btn">🗑️</button>
                                                    </form>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>

                                    <div class="cart-summary">
                                        <h3>Order Summary</h3>
                                        <div class="summary-row">
                                            <span>Subtotal</span>
                                            <span>₹
                                                <fmt:formatNumber value="${cartTotal}" pattern="#,##0.00" />
                                            </span>
                                        </div>
                                        <div class="summary-row">
                                            <span>Delivery Fee</span>
                                            <span>₹20.00</span>
                                        </div>
                                        <div class="summary-row total">
                                            <span>Total</span>
                                            <span>₹
                                                <fmt:formatNumber value="${cartTotal + 20}" pattern="#,##0.00" />
                                            </span>
                                        </div>
                                        <div class="payment-methods-summary">
                                            <p class="summary-label">Available Payment Options:</p>
                                            <div class="payment-icons">
                                                <span title="Cash on Delivery">💵 COD</span>
                                                <span title="Credit/Debit Card">💳 Card</span>
                                                <span title="UPI">📱 UPI</span>
                                            </div>
                                        </div>

                                        <c:choose>
                                            <c:when test="${not empty sessionScope.user}">
                                                <a href="${pageContext.request.contextPath}/checkout"
                                                    class="btn btn-primary btn-block">Proceed to Payment</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="${pageContext.request.contextPath}/login"
                                                    class="btn btn-primary btn-block">Login to Order</a>
                                            </c:otherwise>
                                        </c:choose>
                                        <form action="${pageContext.request.contextPath}/cart/clear" method="POST">
                                            <button type="submit" class="btn btn-outline btn-block">Clear Cart</button>
                                        </form>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </section>

                <footer class="footer">
                    <div class="container">
                        <p>&copy; 2026 FoodExpress. All rights reserved.</p>
                    </div>
                </footer>
            </body>

            </html>