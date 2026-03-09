<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Checkout | FoodExpress</title>
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
                    </div>
                </nav>

                <section class="section">
                    <div class="container">
                        <h1 class="page-title">📦 Checkout</h1>

                        <div class="checkout-layout">
                            <div class="checkout-form-section">
                                <form action="${pageContext.request.contextPath}/checkout" method="POST"
                                    id="checkoutForm">
                                    <div class="form-card">
                                        <h3>Delivery Address</h3>
                                        <div class="form-group">
                                            <label class="form-label">Full Name</label>
                                            <input type="text" class="form-input" value="${user.fullName}" readonly>
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">Phone</label>
                                            <input type="text" class="form-input" value="${user.phone}" readonly>
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">Delivery Address</label>
                                            <textarea name="address" class="form-input" rows="3" required
                                                placeholder="Enter full delivery address">${user.address}</textarea>
                                        </div>
                                    </div>

                                    <div class="form-card">
                                        <h3>Payment Method</h3>
                                        <div class="payment-options">
                                            <label class="payment-option">
                                                <input type="radio" name="paymentMethod" value="cod" checked>
                                                <span class="payment-label">💵 Cash on Delivery</span>
                                            </label>
                                            <label class="payment-option">
                                                <input type="radio" name="paymentMethod" value="card">
                                                <span class="payment-label">💳 Credit/Debit Card</span>
                                            </label>
                                            <label class="payment-option">
                                                <input type="radio" name="paymentMethod" value="upi">
                                                <span class="payment-label">📱 UPI Payment</span>
                                            </label>
                                        </div>
                                    </div>

                                    <div class="form-card">
                                        <h3>Special Instructions</h3>
                                        <div class="form-group">
                                            <textarea name="instructions" class="form-input" rows="2"
                                                placeholder="Any special instructions for your order?"></textarea>
                                        </div>
                                    </div>

                                    <button type="submit" class="btn btn-primary btn-lg btn-block">Place Order</button>
                                </form>
                            </div>

                            <div class="order-summary-section">
                                <div class="order-summary-card">
                                    <h3>Order Summary</h3>
                                    <div class="order-items">
                                        <c:forEach var="item" items="${cartItems}">
                                            <div class="order-item">
                                                <span class="item-name">${item.foodName} x${item.quantity}</span>
                                                <span class="item-price">₹
                                                    <fmt:formatNumber value="${item.subtotal}" pattern="#,##0.00" />
                                                </span>
                                            </div>
                                        </c:forEach>
                                    </div>
                                    <hr>
                                    <div class="summary-row">
                                        <span>Subtotal</span>
                                        <span>₹
                                            <fmt:formatNumber value="${cartTotal}" pattern="#,##0.00" />
                                        </span>
                                    </div>
                                    <div class="summary-row">
                                        <span>Delivery Fee</span>
                                        <span>₹40.00</span>
                                    </div>
                                    <div class="summary-row total">
                                        <span>Total</span>
                                        <span>₹
                                            <fmt:formatNumber value="${cartTotal + 40}" pattern="#,##0.00" />
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </body>

            </html>