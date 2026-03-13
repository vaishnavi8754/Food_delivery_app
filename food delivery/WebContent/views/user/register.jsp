<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <meta name="description" content="Create your FoodExpress account and start ordering delicious food">
            <title>Create Account | FoodExpress - Online Food Delivery</title>

            <!-- Google Fonts -->
            <link rel="preconnect" href="https://fonts.googleapis.com">
            <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
            <link
                href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Outfit:wght@600;700;800&display=swap"
                rel="stylesheet">

            <!-- Stylesheet -->
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=1.2">

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
                            Join the Food<br>Revolution 🚀
                        </h1>

                        <!-- Subtitle -->
                        <p class="branding-subtitle">
                            Create your free account and unlock a world of culinary delights.
                            From local favorites to international cuisines, we've got it all!
                        </p>

                        <!-- Features -->
                        <ul class="branding-features">
                            <li>Exclusive first-order discounts</li>
                            <li>Personalized recommendations</li>
                            <li>Save your favorite restaurants</li>
                            <li>Easy reorder from history</li>
                        </ul>
                    </div>
                </div>

                <!-- Right Panel - Registration Form -->
                <div class="auth-form-section">
                    <div class="auth-form-wrapper fade-in">
                        <!-- Form Header -->
                        <div class="auth-form-header">
                            <h2>Create Account 🎉</h2>
                            <p>Fill in your details to get started</p>
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

                            <!-- Registration Form -->
                            <form action="${pageContext.request.contextPath}/register" method="POST" id="registerForm">
                                <!-- Full Name Field -->
                                <div class="form-group">
                                    <label class="form-label" for="fullName">Full Name</label>
                                    <div class="form-input-wrapper">
                                        <input type="text" id="fullName" name="fullName" class="form-input"
                                            placeholder="Enter your full name" value="${fullName}" required
                                            autocomplete="name">
                                        <span class="form-input-icon">👤</span>
                                    </div>
                                </div>

                                <!-- Email & Phone Row -->
                                <div class="form-row">
                                    <!-- Email Field -->
                                    <div class="form-group">
                                        <label class="form-label" for="email">Email Address</label>
                                        <div class="form-input-wrapper">
                                            <input type="email" id="email" name="email" class="form-input"
                                                placeholder="your@email.com" value="${email}" required
                                                autocomplete="email">
                                            <span class="form-input-icon">📧</span>
                                        </div>
                                    </div>

                                    <!-- Phone Field -->
                                    <div class="form-group">
                                        <label class="form-label" for="phone">Phone Number</label>
                                        <div class="form-input-wrapper">
                                            <input type="tel" id="phone" name="phone" class="form-input"
                                                placeholder="10-digit number" value="${phone}" required maxlength="10"
                                                pattern="[0-9]{10}" autocomplete="tel">
                                            <span class="form-input-icon">📱</span>
                                        </div>
                                    </div>
                                </div>

                                <!-- Address Field -->
                                <div class="form-group">
                                    <label class="form-label" for="address">Delivery Address <span
                                            style="color: var(--gray-400); font-weight: 400;">(Optional)</span></label>
                                    <div class="form-input-wrapper">
                                        <input type="text" id="address" name="address" class="form-input"
                                            placeholder="Enter your delivery address" value="${address}"
                                            autocomplete="street-address">
                                        <span class="form-input-icon">📍</span>
                                    </div>
                                </div>

                                <!-- Password Fields Row -->
                                <div class="form-row">
                                    <!-- Password Field -->
                                    <div class="form-group">
                                        <label class="form-label" for="password">Password</label>
                                        <div class="form-input-wrapper">
                                            <input type="password" id="password" name="password" class="form-input"
                                                placeholder="Min. 6 characters" required minlength="6"
                                                autocomplete="new-password">
                                            <span class="form-input-icon">🔒</span>
                                            <span class="form-input-right-icon"
                                                onclick="togglePassword('password', this)">👁️</span>
                                        </div>
                                        <div class="password-strength" id="passwordStrength">
                                            <div class="password-strength-bar">
                                                <div class="password-strength-fill"></div>
                                            </div>
                                            <span class="password-strength-text"></span>
                                        </div>
                                    </div>

                                    <!-- Confirm Password Field -->
                                    <div class="form-group">
                                        <label class="form-label" for="confirmPassword">Confirm Password</label>
                                        <div class="form-input-wrapper">
                                            <input type="password" id="confirmPassword" name="confirmPassword"
                                                class="form-input" placeholder="Repeat password" required
                                                autocomplete="new-password">
                                            <span class="form-input-icon">🔐</span>
                                            <span class="form-input-right-icon"
                                                onclick="togglePassword('confirmPassword', this)">👁️</span>
                                        </div>
                                    </div>
                                </div>

                                <!-- Terms & Conditions -->
                                <div class="form-group">
                                    <div class="form-checkbox-wrapper">
                                        <input type="checkbox" id="terms" name="terms" class="form-checkbox" required>
                                        <label for="terms" class="form-checkbox-label">
                                            I agree to the <a href="#">Terms of Service</a> and <a href="#">Privacy
                                                Policy</a>
                                        </label>
                                    </div>
                                </div>

                                <!-- Submit Button -->
                                <button type="submit" class="btn btn-primary" id="registerBtn">
                                    <span>Create Account</span>
                                    <span>→</span>
                                </button>
                            </form>

                            <!-- Social Login Divider -->
                            <div class="social-divider">
                                <span>Or continue with</span>
                            </div>

                            <!-- Social Login Buttons -->
                            <div class="social-buttons" style="grid-template-columns: 1fr;">
                                <button type="button" class="btn btn-social btn-google" style="grid-column: span 1;">
                                    <span>🌐</span>
                                    <span>Continue with Google</span>
                                </button>
                            </div>
                        </div>

                        <!-- Footer -->
                        <div class="auth-footer">
                            <p>Already have an account? <a href="${pageContext.request.contextPath}/login">Sign In</a>
                            </p>
                        </div>
                    </div>
                </div>
            </div>

            <!-- JavaScript -->
            <script>
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

                // Password Strength Checker
                const passwordInput = document.getElementById('password');
                const strengthIndicator = document.getElementById('passwordStrength');

                passwordInput.addEventListener('input', function () {
                    const password = this.value;
                    const strength = checkPasswordStrength(password);

                    strengthIndicator.className = 'password-strength ' + strength.class;
                    strengthIndicator.querySelector('.password-strength-text').textContent = strength.text;
                });

                function checkPasswordStrength(password) {
                    if (password.length === 0) {
                        return { class: '', text: '' };
                    }

                    let score = 0;

                    // Length check
                    if (password.length >= 6) score++;
                    if (password.length >= 8) score++;
                    if (password.length >= 12) score++;

                    // Character variety
                    if (/[a-z]/.test(password)) score++;
                    if (/[A-Z]/.test(password)) score++;
                    if (/[0-9]/.test(password)) score++;
                    if (/[^a-zA-Z0-9]/.test(password)) score++;

                    if (score <= 2) {
                        return { class: 'strength-weak', text: 'Weak password' };
                    } else if (score <= 4) {
                        return { class: 'strength-medium', text: 'Medium strength' };
                    } else {
                        return { class: 'strength-strong', text: 'Strong password' };
                    }
                }

                // Form Validation
                document.getElementById('registerForm').addEventListener('submit', function (e) {
                    const fullName = document.getElementById('fullName').value.trim();
                    const email = document.getElementById('email').value.trim();
                    const phone = document.getElementById('phone').value.trim();
                    const password = document.getElementById('password').value;
                    const confirmPassword = document.getElementById('confirmPassword').value;
                    const terms = document.getElementById('terms').checked;
                    const btn = document.getElementById('registerBtn');

                    // Name validation
                    if (fullName.length < 2) {
                        e.preventDefault();
                        showAlert('Please enter your full name', 'error');
                        return;
                    }

                    // Email validation
                    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                    if (!emailRegex.test(email)) {
                        e.preventDefault();
                        showAlert('Please enter a valid email address', 'error');
                        return;
                    }

                    // Phone validation
                    const phoneRegex = /^[0-9]{10}$/;
                    if (!phoneRegex.test(phone)) {
                        e.preventDefault();
                        showAlert('Please enter a valid 10-digit phone number', 'error');
                        return;
                    }

                    // Password validation
                    if (password.length < 6) {
                        e.preventDefault();
                        showAlert('Password must be at least 6 characters long', 'error');
                        return;
                    }

                    // Password match
                    if (password !== confirmPassword) {
                        e.preventDefault();
                        showAlert('Passwords do not match', 'error');
                        return;
                    }

                    // Terms validation
                    if (!terms) {
                        e.preventDefault();
                        showAlert('Please accept the terms and conditions', 'error');
                        return;
                    }

                    // Show loading state
                    btn.innerHTML = '<span class="spinner"></span><span>Creating Account...</span>';
                    btn.disabled = true;
                });

                // Phone input - only allow numbers
                document.getElementById('phone').addEventListener('input', function () {
                    this.value = this.value.replace(/[^0-9]/g, '');
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

                    const form = document.getElementById('registerForm');
                    form.parentNode.insertBefore(alert, form);

                    // Auto remove after 5 seconds
                    setTimeout(() => {
                        alert.remove();
                    }, 5000);
                }
            </script>
        </body>

        </html>