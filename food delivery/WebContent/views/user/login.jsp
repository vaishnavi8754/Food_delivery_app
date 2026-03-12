<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <meta name="description"
                content="Login to FoodExpress - Order delicious food from your favorite restaurants">
            <title>Login | FoodExpress - Online Food Delivery</title>

            <!-- Google Fonts -->
            <link rel="preconnect" href="https://fonts.googleapis.com">
            <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
            <link
                href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Outfit:wght@600;700;800&display=swap"
                rel="stylesheet">

            <!-- Stylesheet -->
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=1.1">

            <!-- Favicon -->
            <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon.png">
        </head>

        <body>
            <div class="auth-container">
                <!-- Left Panel - Branding -->
                <div class="auth-branding">
                    <div class="branding-content">
                        <!-- Logo -->
                        <div class="brand-logo">
                            <div class="brand-logo-icon">🍕</div>
                            <span class="brand-logo-text">FoodExpress</span>
                        </div>

                        <!-- Title -->
                        <h1 class="branding-title">
                            Delicious Food,<br>Delivered Fast
                        </h1>

                        <!-- Subtitle -->
                        <p class="branding-subtitle">
                            Order from hundreds of restaurants and get your favorite meals
                            delivered right to your doorstep in minutes.
                        </p>

                        <!-- Features -->
                        <ul class="branding-features">
                            <li>Wide variety of cuisines</li>
                            <li>Fast & reliable delivery</li>
                            <li>Real-time order tracking</li>
                            <li>Safe & secure payments</li>
                        </ul>
                    </div>
                </div>

                <!-- Right Panel - Login Form -->
                <div class="auth-form-section">
                    <div class="auth-form-wrapper fade-in">
                        <!-- Form Header -->
                        <div class="auth-form-header">
                            <h2>Welcome Back! 👋</h2>
                            <p>Sign in to continue ordering delicious food</p>
                        </div>

                        <!-- Form Card -->
                        <div class="auth-card">
                            <!-- Error Message -->
                            <c:if test="${not empty error}">
                                <div class="alert alert-error">
                                    <span class="alert-icon">⚠️</span>
                                    <span>${error}</span>
                                </div>
                            </c:if>

                            <!-- Success Message -->
                            <c:if test="${not empty sessionScope.successMessage}">
                                <div class="alert alert-success">
                                    <span class="alert-icon">✅</span>
                                    <span>${sessionScope.successMessage}</span>
                                </div>
                                <c:remove var="successMessage" scope="session" />
                            </c:if>

                            <!-- Logout Message -->
                            <c:if test="${param.logout == 'success'}">
                                <div class="alert alert-success">
                                    <span class="alert-icon">👋</span>
                                    <span>You have been successfully logged out.</span>
                                </div>
                            </c:if>

                            <!-- Login Form -->
                            <form action="${pageContext.request.contextPath}/login" method="POST" id="loginForm">
                                <!-- Role Slider Toggle -->
                                <div class="role-slider-wrapper">
                                    <div class="role-slider" id="roleSlider">
                                        <div class="role-slider-pill"></div>
                                        <button type="button" class="role-slider-btn active" data-role="user"
                                            onclick="updateRole('user')">
                                            <span>🍕</span> User
                                        </button>
                                        <button type="button" class="role-slider-btn" data-role="admin"
                                            onclick="updateRole('admin')">
                                            <span>🛡️</span> Admin
                                        </button>
                                    </div>
                                </div>

                                <!-- Dynamic Role Indicator -->
                                <div class="text-center" style="text-align: center;">
                                    <div class="role-indicator-badge" id="roleIndicator">
                                        <span>✨</span>
                                        <span id="roleText">Premium User Access</span>
                                    </div>
                                </div>

                                <input type="hidden" name="role" id="roleInput" value="user">

                                <!-- Email Field -->
                                <div class="form-group">
                                    <label class="form-label" for="email">Email Address</label>
                                    <div class="form-input-wrapper">
                                        <input type="email" id="email" name="email" class="form-input"
                                            placeholder="Enter your email" value="${email}" required
                                            autocomplete="email">
                                        <span class="form-input-icon">📧</span>
                                    </div>
                                </div>

                                <!-- Password Field -->
                                <div class="form-group">
                                    <label class="form-label" for="password">Password</label>
                                    <div class="form-input-wrapper">
                                        <input type="password" id="password" name="password" class="form-input"
                                            placeholder="Enter your password" required autocomplete="current-password">
                                        <span class="form-input-icon">🔒</span>
                                        <span class="form-input-right-icon"
                                            onclick="togglePassword('password', this)">👁️</span>
                                    </div>
                                </div>

                                <!-- Remember Me & Forgot Password -->
                                <div class="form-actions">
                                    <div class="form-checkbox-wrapper">
                                        <input type="checkbox" id="remember" name="remember" class="form-checkbox">
                                        <label for="remember" class="form-checkbox-label">Remember me</label>
                                    </div>
                                    <a href="${pageContext.request.contextPath}/forgot-password"
                                        class="form-link">Forgot Password?</a>
                                </div>

                                <!-- Submit Button -->
                                <button type="submit" class="btn btn-primary" id="loginBtn">
                                    <span>Sign In</span>
                                    <span>→</span>
                                </button>
                            </form>

                            <!-- Social Login Divider -->
                            <div class="social-divider">
                                <span>Or continue with</span>
                            </div>

                            <!-- Social Login Buttons -->
                            <div class="social-buttons">
                                <button type="button" class="btn btn-social btn-google">
                                    <span>🌐</span>
                                    <span>Continue with Google</span>
                                </button>
                            </div>
                        </div>

                        <!-- Footer -->
                        <div class="auth-footer">
                            <p>Don't have an account? <a href="${pageContext.request.contextPath}/signup">Create
                                    Account</a></p>
                        </div>
                    </div>
                </div>
            </div>

            <!-- JavaScript -->
            <script>
                // Modern Role Update Function
                function updateRole(role) {
                    const card = document.querySelector('.auth-card');
                    const input = document.getElementById('roleInput');
                    const indicator = document.getElementById('roleIndicator');
                    const roleText = document.getElementById('roleText');
                    const btns = document.querySelectorAll('.role-slider-btn');

                    input.value = role;

                    // Update UI State
                    btns.forEach(btn => {
                        if (btn.getAttribute('data-role') === role) {
                            btn.classList.add('active');
                        } else {
                            btn.classList.remove('active');
                        }
                    });

                    if (role === 'admin') {
                        card.classList.add('admin-active');
                        indicator.innerHTML = '<span>💎</span><span>Admin Console Access</span>';
                    } else {
                        card.classList.remove('admin-active');
                        indicator.innerHTML = '<span>✨</span><span>Premium User Access</span>';
                    }
                }

                // Toggle Password Visibility
                function togglePassword(inputId, iconElement) {
                    const input = document.getElementById(inputId);
                    if (input.type === 'password') {
                        input.type = 'text';
                        iconElement.textContent = '🙈';
                    } else {
                        input.type = 'password';
                        iconElement.textContent = '👁️';
                    }
                }

                // Form Validation
                document.getElementById('loginForm').addEventListener('submit', function (e) {
                    const email = document.getElementById('email').value.trim();
                    const password = document.getElementById('password').value;
                    const btn = document.getElementById('loginBtn');

                    // Basic validation
                    if (!email || !password) {
                        e.preventDefault();
                        showAlert('Please fill in all fields', 'error');
                        return;
                    }

                    // Email validation
                    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                    if (!emailRegex.test(email)) {
                        e.preventDefault();
                        showAlert('Please enter a valid email address', 'error');
                        return;
                    }

                    // Show loading state
                    btn.innerHTML = '<span class="spinner"></span><span>Signing in...</span>';
                    btn.disabled = true;
                });

                // Show alert function
                function showAlert(message, type) {
                    const existingAlert = document.querySelector('.alert');
                    if (existingAlert) {
                        existingAlert.remove();
                    }

                    const alert = document.createElement('div');
                    alert.className = `alert alert-\${type}`;
                    alert.innerHTML = `
                <span class="alert-icon">\${type === 'error' ? '⚠️' : '✅'}</span>
                <span>\${message}</span>
            `;

                    const form = document.getElementById('loginForm');
                    form.parentNode.insertBefore(alert, form);

                    // Auto remove after 5 seconds
                    setTimeout(() => {
                        alert.remove();
                    }, 5000);
                }
            </script>
        </body>

        </html>