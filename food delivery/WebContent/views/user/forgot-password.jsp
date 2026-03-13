<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Forgot Password | FoodExpress</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Outfit:wght@600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=1.1">
    <style>
        .auth-card {
            max-width: 450px;
            margin: 0 auto;
        }
        .step-indicator {
            display: flex;
            justify-content: center;
            gap: 1rem;
            margin-bottom: 2rem;
        }
        .step-dot {
            width: 10px;
            height: 10px;
            border-radius: 50%;
            background: var(--border);
            transition: var(--transition);
        }
        .step-dot.active {
            background: var(--primary);
            transform: scale(1.3);
            box-shadow: 0 0 10px rgba(249, 115, 22, 0.4);
        }
    </style>
</head>

<body>
    <div class="auth-container">
        <div class="auth-form-section" style="width: 100%; max-width: 600px; margin: 0 auto;">
            <div class="auth-form-wrapper fade-in">
                <div class="auth-form-header">
                    <div class="brand-logo" style="justify-content: center; margin-bottom: 1.5rem;">
                        <div class="brand-logo-icon">🍕</div>
                        <span class="brand-logo-text">FoodExpress</span>
                    </div>
                    <h2>Account Recovery</h2>
                    <p>${step == 'reset' ? 'Set a new secure password for your account' : 'Enter your email to reset your password'}</p>
                </div>

                <div class="auth-card">
                    <div class="step-indicator">
                        <div class="step-dot ${step == 'reset' ? '' : 'active'}"></div>
                        <div class="step-dot ${step == 'reset' ? 'active' : ''}"></div>
                    </div>

                    <c:if test="${not empty error}">
                        <div class="alert alert-error">
                            <span class="alert-icon">⚠️</span>
                            <span>${error}</span>
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/forgot-password" method="POST">
                        <c:choose>
                            <c:when test="${step == 'reset'}">
                                <input type="hidden" name="action" value="reset">
                                <div class="form-group">
                                    <label class="form-label">Email Context</label>
                                    <div class="form-input-wrapper" style="opacity: 0.7;">
                                        <input type="text" class="form-input" value="${sessionScope.resetEmail}" disabled>
                                        <span class="form-input-icon">📧</span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="form-label" for="password">New Password</label>
                                    <div class="form-input-wrapper">
                                        <input type="password" id="password" name="password" class="form-input" placeholder="Min 6 characters" required>
                                        <span class="form-input-icon">🔒</span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="form-label" for="confirmPassword">Confirm Password</label>
                                    <div class="form-input-wrapper">
                                        <input type="password" id="confirmPassword" name="confirmPassword" class="form-input" placeholder="Repeat new password" required>
                                        <span class="form-input-icon">🔒</span>
                                    </div>
                                </div>
                                <button type="submit" class="btn btn-primary">
                                    <span>Reset Password</span>
                                    <span>→</span>
                                </button>
                            </c:when>
                            <c:otherwise>
                                <input type="hidden" name="action" value="verify">
                                <div class="form-group">
                                    <label class="form-label" for="email">Email Address</label>
                                    <div class="form-input-wrapper">
                                        <input type="email" id="email" name="email" class="form-input" placeholder="Enter your registered email" required>
                                        <span class="form-input-icon">📧</span>
                                    </div>
                                </div>
                                <button type="submit" class="btn btn-primary">
                                    <span>Continue</span>
                                    <span>→</span>
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </form>

                    <div class="auth-footer" style="margin-top: 2rem;">
                        <p>Remember your password? <a href="${pageContext.request.contextPath}/login">Back to Login</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>
