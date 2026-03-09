<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>FoodExpress - Online Food Delivery</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <link
            href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Outfit:wght@600;700;800&display=swap"
            rel="stylesheet">
        <style>
            .landing-hero {
                min-height: 100vh;
                background: var(--gradient-primary);
                display: flex;
                align-items: center;
                justify-content: center;
                text-align: center;
                color: white;
                position: relative;
                overflow: hidden;
            }

            .hero-content {
                position: relative;
                z-index: 1;
                max-width: 800px;
                padding: 2rem;
            }

            .hero-logo {
                display: inline-flex;
                align-items: center;
                gap: 1rem;
                background: rgba(255, 255, 255, 0.1);
                backdrop-filter: blur(10px);
                padding: 1rem 2rem;
                border-radius: 100px;
                margin-bottom: 2rem;
            }

            .hero-logo-icon {
                font-size: 2.5rem;
            }

            .hero-logo-text {
                font-family: 'Outfit', sans-serif;
                font-size: 1.5rem;
                font-weight: 800;
            }

            .hero-title {
                font-size: 4rem;
                font-weight: 800;
                margin-bottom: 1.5rem;
                line-height: 1.1;
            }

            .hero-subtitle {
                font-size: 1.25rem;
                opacity: 0.9;
                margin-bottom: 3rem;
                max-width: 600px;
                margin: 0 auto 3rem;
            }

            .hero-buttons {
                display: flex;
                gap: 1rem;
                justify-content: center;
                flex-wrap: wrap;
            }

            .btn-hero {
                padding: 1rem 2.5rem;
                font-size: 1.1rem;
                font-weight: 600;
                border-radius: 100px;
                text-decoration: none;
                transition: all 0.3s ease;
                display: inline-flex;
                align-items: center;
                gap: 0.5rem;
            }

            .btn-hero-primary {
                background: white;
                color: var(--primary-600);
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
            }

            .btn-hero-primary:hover {
                transform: translateY(-3px);
                box-shadow: 0 15px 40px rgba(0, 0, 0, 0.3);
            }

            .btn-hero-secondary {
                background: rgba(255, 255, 255, 0.15);
                color: white;
                border: 2px solid rgba(255, 255, 255, 0.3);
            }

            .btn-hero-secondary:hover {
                background: rgba(255, 255, 255, 0.25);
                transform: translateY(-3px);
            }
        </style>
    </head>

    <body>
        <section class="landing-hero">
            <div class="hero-content">
                <div class="hero-logo">
                    <span class="hero-logo-icon">🍕</span>
                    <span class="hero-logo-text">FoodExpress</span>
                </div>
                <h1 class="hero-title">Delicious Food,<br>Delivered Fast</h1>
                <p class="hero-subtitle">Order from hundreds of restaurants and get your favorite meals delivered right
                    to your doorstep.</p>
                <div class="hero-buttons">
                    <a href="${pageContext.request.contextPath}/register" class="btn-hero btn-hero-primary">Get Started
                        →</a>
                    <a href="${pageContext.request.contextPath}/login" class="btn-hero btn-hero-secondary">Sign In</a>
                </div>
            </div>
        </section>
    </body>

    </html>