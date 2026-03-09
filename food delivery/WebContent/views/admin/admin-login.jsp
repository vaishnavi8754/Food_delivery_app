<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Admin Login | FoodExpress</title>
            <link
                href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Outfit:wght@600;700;800&display=swap"
                rel="stylesheet">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        </head>

        <body>
            <div class="auth-container">
                <div class="auth-branding" style="background: linear-gradient(135deg, #1f2937 0%, #111827 100%);">
                    <div class="branding-content">
                        <div class="brand-logo">
                            <div class="brand-logo-icon">🛡️</div>
                            <span class="brand-logo-text">Admin Panel</span>
                        </div>
                        <h1 class="branding-title">FoodExpress<br>Administration</h1>
                        <p class="branding-subtitle">Manage restaurants, food items, orders, and users from one central
                            dashboard.</p>
                    </div>
                </div>
                <div class="auth-form-section">
                    <div class="auth-form-wrapper fade-in">
                        <div class="auth-form-header">
                            <h2>Admin Login 🔐</h2>
                            <p>Enter your admin credentials</p>
                        </div>
                        <div class="auth-card">
                            <c:if test="${not empty error}">
                                <div class="alert alert-error">
                                    <span class="alert-icon">⚠️</span>
                                    <span>${error}</span>
                                </div>
                            </c:if>
                            <form action="${pageContext.request.contextPath}/admin/login" method="POST">
                                <div class="form-group">
                                    <label class="form-label">Email Address</label>
                                    <div class="form-input-wrapper">
                                        <input type="email" name="email" class="form-input"
                                            placeholder="admin@email.com" required>
                                        <span class="form-input-icon">📧</span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="form-label">Password</label>
                                    <div class="form-input-wrapper">
                                        <input type="password" name="password" class="form-input"
                                            placeholder="Enter password" required>
                                        <span class="form-input-icon">🔒</span>
                                    </div>
                                </div>
                                <button type="submit" class="btn btn-primary">Sign In to Dashboard →</button>
                            </form>
                        </div>
                        <div class="auth-footer">
                            <p><a href="${pageContext.request.contextPath}/login">← Back to User Login</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </body>

        </html>