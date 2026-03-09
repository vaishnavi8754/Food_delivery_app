<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Order #${order.orderId} | FoodExpress</title>
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
                        <div class="nav-auth">
                            <a href="${pageContext.request.contextPath}/orders" class="btn btn-outline">← Back to
                                Orders</a>
                        </div>
                    </div>
                </nav>

                <section class="section">
                    <div class="container">
                        <c:if test="${param.success == 'true'}">
                            <div class="success-banner">
                                <span class="success-icon">🎉</span>
                                <h2>Order Placed Successfully!</h2>
                                <p>Thank you for your order. Your food is being prepared!</p>
                            </div>
                        </c:if>

                        <div class="order-detail-card">
                            <div class="order-detail-header">
                                <div>
                                    <h1>Order #${order.orderId}</h1>
                                    <p>
                                        <fmt:formatDate value="${order.orderDate}" pattern="dd MMMM yyyy, hh:mm a" />
                                    </p>
                                </div>
                                <span class="status-badge status-${order.status} large">${order.status}</span>
                            </div>

                            <div class="order-detail-body">
                                <div class="detail-section">
                                    <h3>Restaurant</h3>
                                    <p>${order.restaurantName}</p>
                                </div>

                                <div class="detail-section">
                                    <h3>Delivery Address</h3>
                                    <p>${order.deliveryAddress}</p>
                                </div>

                                <div class="detail-section">
                                    <h3>Order Items</h3>
                                    <table class="items-table">
                                        <thead>
                                            <tr>
                                                <th>Item</th>
                                                <th>Qty</th>
                                                <th>Price</th>
                                                <th>Subtotal</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="item" items="${order.orderItems}">
                                                <tr>
                                                    <td>${item.foodName}</td>
                                                    <td>${item.quantity}</td>
                                                    <td>₹
                                                        <fmt:formatNumber value="${item.unitPrice}"
                                                            pattern="#,##0.00" />
                                                    </td>
                                                    <td>₹
                                                        <fmt:formatNumber value="${item.subtotal}" pattern="#,##0.00" />
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>

                                <div class="detail-section total-section">
                                    <div class="total-row">
                                        <span>Subtotal</span>
                                        <span>₹
                                            <fmt:formatNumber value="${order.totalAmount - 40}" pattern="#,##0.00" />
                                        </span>
                                    </div>
                                    <div class="total-row">
                                        <span>Delivery Fee</span>
                                        <span>₹40.00</span>
                                    </div>
                                    <div class="total-row grand-total">
                                        <span>Total</span>
                                        <span>₹
                                            <fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00" />
                                        </span>
                                    </div>
                                </div>

                                <div class="detail-section">
                                    <h3>Payment</h3>
                                    <p><strong>Method:</strong> ${order.paymentMethod}</p>
                                    <p><strong>Status:</strong> ${order.paymentStatus}</p>
                                </div>
                            </div>
                        </div>

                        <div class="order-actions">
                            <a href="${pageContext.request.contextPath}/restaurants" class="btn btn-primary">Order
                                Again</a>
                            <a href="${pageContext.request.contextPath}/orders" class="btn btn-outline">View All
                                Orders</a>
                        </div>
                    </div>
                </section>
            </body>

            </html>