<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Order #${order.orderId} | Admin</title>
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
                            <a href="${pageContext.request.contextPath}/admin/orders" class="nav-item active">📦
                                Orders</a>
                            <a href="${pageContext.request.contextPath}/admin/users" class="nav-item">👥 Users</a>
                        </nav>
                    </aside>
                    <main class="admin-main">
                        <header class="admin-header">
                            <h1>Order #${order.orderId}</h1>
                            <a href="${pageContext.request.contextPath}/admin/orders" class="btn btn-outline">← Back</a>
                        </header>
                        <div class="dashboard-content">
                            <div class="order-detail-grid">
                                <div class="detail-card">
                                    <h3>Order Information</h3>
                                    <p><strong>Order ID:</strong> #${order.orderId}</p>
                                    <p><strong>Date:</strong>
                                        <fmt:formatDate value="${order.orderDate}" pattern="dd MMMM yyyy, HH:mm" />
                                    </p>
                                    <p><strong>Customer:</strong> ${order.userName}</p>
                                    <p><strong>Restaurant:</strong> ${order.restaurantName}</p>
                                    <p><strong>Address:</strong> ${order.deliveryAddress}</p>
                                </div>
                                <div class="detail-card">
                                    <h3>Update Status</h3>
                                    <form action="${pageContext.request.contextPath}/admin/orders/update" method="POST">
                                        <input type="hidden" name="orderId" value="${order.orderId}">
                                        <select name="status" class="form-input">
                                            <option value="pending" ${order.status=='pending' ? 'selected' : '' }>
                                                Pending</option>
                                            <option value="confirmed" ${order.status=='confirmed' ? 'selected' : '' }>
                                                Confirmed</option>
                                            <option value="preparing" ${order.status=='preparing' ? 'selected' : '' }>
                                                Preparing</option>
                                            <option value="out_for_delivery" ${order.status=='out_for_delivery'
                                                ? 'selected' : '' }>Out for Delivery</option>
                                            <option value="delivered" ${order.status=='delivered' ? 'selected' : '' }>
                                                Delivered</option>
                                            <option value="cancelled" ${order.status=='cancelled' ? 'selected' : '' }>
                                                Cancelled</option>
                                        </select>
                                        <button type="submit" class="btn btn-primary" style="margin-top:10px">Update
                                            Status</button>
                                    </form>
                                </div>
                            </div>
                            <div class="detail-card">
                                <h3>Order Items</h3>
                                <table class="admin-table">
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
                                                    <fmt:formatNumber value="${item.unitPrice}" pattern="#,##0.00" />
                                                </td>
                                                <td>₹
                                                    <fmt:formatNumber value="${item.subtotal}" pattern="#,##0.00" />
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                    <tfoot>
                                        <tr class="total-row">
                                            <td colspan="3"><strong>Total</strong></td>
                                            <td><strong>₹
                                                    <fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00" />
                                                </strong></td>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>
                        </div>
                    </main>
                </div>
            </body>

            </html>