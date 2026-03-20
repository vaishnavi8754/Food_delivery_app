<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Menu - ${restaurant.name} | FoodExpress</title>
                <link
                    href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Outfit:wght@600;700;800&display=swap"
                    rel="stylesheet">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages.css">
                <style>
                    /* ── Proximity Modal ─────────────────────────── */
                    .proximity-backdrop {
                        display: none;
                        position: fixed; inset: 0;
                        background: rgba(0,0,0,0.65);
                        backdrop-filter: blur(4px);
                        z-index: 9000;
                        align-items: center;
                        justify-content: center;
                    }
                    .proximity-backdrop.active { display: flex; }
                    .proximity-modal {
                        background: #fff;
                        border-radius: 18px;
                        padding: 32px 28px;
                        max-width: 420px;
                        width: 90%;
                        box-shadow: 0 20px 60px rgba(0,0,0,0.25);
                        text-align: center;
                        animation: modalPop .25s ease;
                    }
                    @keyframes modalPop {
                        from { transform: scale(.85); opacity:0; }
                        to   { transform: scale(1);   opacity:1; }
                    }
                    .proximity-modal .modal-icon { font-size: 3rem; margin-bottom: 12px; }
                    .proximity-modal h3 { font-size: 1.25rem; font-weight: 700; color: #1a1a1a; margin-bottom: 10px; }
                    .proximity-modal p  { font-size: .925rem; color: #555; line-height: 1.55; margin-bottom: 24px; }
                    .proximity-modal p strong { color: #e67e22; }
                    .modal-actions { display: flex; gap: 12px; }
                    .modal-actions button {
                        flex: 1; padding: 13px; border-radius: 10px;
                        font-size: .9rem; font-weight: 600; cursor: pointer;
                        border: none; transition: opacity .2s;
                    }
                    .modal-actions button:hover { opacity: .85; }
                    .btn-modal-keep   { background: #f0f0f0; color: #333; }
                    .btn-modal-clear  { background: linear-gradient(135deg, #e67e22, #f39c12); color: #fff; }

                    /* ── Toast notification ──────────────────────── */
                    .toast-container {
                        position: fixed; bottom: 90px; left: 50%; transform: translateX(-50%);
                        z-index: 9999; display: flex; flex-direction: column; gap: 10px;
                        pointer-events: none;
                    }
                    .toast {
                        background: #222; color: #fff; border-radius: 10px;
                        padding: 12px 22px; font-size: .9rem; font-weight: 500;
                        box-shadow: 0 8px 24px rgba(0,0,0,.3);
                        animation: toastIn .3s ease, toastOut .4s ease 2s forwards;
                        pointer-events: auto;
                    }
                    .toast.toast-success { background: #27ae60; }
                    .toast.toast-error   { background: #e74c3c; }
                    @keyframes toastIn  { from { opacity:0; transform: translateY(16px); } to { opacity:1; transform:translateY(0); } }
                    @keyframes toastOut { from { opacity:1; } to { opacity:0; } }

                    /* ── ADD button states ───────────────────────── */
                    .add-item-btn { transition: background .2s, transform .1s; }
                    .add-item-btn.adding { opacity: .6; pointer-events: none; }
                    .add-item-btn.added  {
                        background: #27ae60 !important; color: #fff !important;
                    }
                    /* ── Floating Cart with multi-restaurant ─────── */
                    .floating-cart {
                        position: fixed; bottom: 24px; left: 50%; transform: translateX(-50%);
                        background: linear-gradient(135deg, #e67e22, #f39c12);
                        color: #fff; padding: 14px 32px;
                        border-radius: 50px; text-decoration: none; font-weight: 700;
                        font-size: .95rem; box-shadow: 0 8px 30px rgba(230,126,34,.45);
                        transition: transform .2s, box-shadow .2s;
                        z-index: 800;
                    }
                    .floating-cart:hover {
                        transform: translateX(-50%) translateY(-3px);
                        box-shadow: 0 12px 40px rgba(230,126,34,.55);
                    }
                    /* Multi-restaurant badge on floating cart */
                    .multi-rest-badge {
                        background: rgba(255,255,255,.25); border-radius: 20px;
                        padding: 2px 9px; font-size: .78rem; margin-left: 8px;
                    }
                </style>
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
                                    <span class="cart-badge" id="nav-cart-badge">${sessionScope.cart.size()}</span>
                                </c:if>
                                <c:if test="${empty sessionScope.cart}">
                                    <span class="cart-badge" id="nav-cart-badge" style="display:none">0</span>
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

                <!-- Restaurant Header -->
                <section class="restaurant-header">
                    <div class="container">
                        <div class="restaurant-header-content">
                            <div class="restaurant-header-info">
                                <h1>${restaurant.name}</h1>
                                <p class="cuisine-badge">${restaurant.cuisineType}</p>
                                <p class="restaurant-desc">${restaurant.description}</p>
                                <div class="restaurant-meta">
                                    <span>⭐ ${restaurant.rating}</span>
                                    <span>📍 ${restaurant.address}</span>
                                    <span>📞 ${restaurant.phone}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <!-- Menu Section -->
                <section class="section menu-section">
                    <div class="container">
                        <c:if test="${empty menuItems}">
                            <div class="empty-state">
                                <p>No menu items available at the moment.</p>
                            </div>
                        </c:if>

                        <c:if test="${not empty menuItems}">
                            <!-- Group by Category -->
                            <c:set var="currentCategory" value="" />
                            <div class="menu-categories-wrapper">
                                <c:forEach var="item" items="${menuItems}">
                                    <c:if test="${item.category != currentCategory}">
                                        <c:set var="currentCategory" value="${item.category}" />
                                        <div class="category-header" id="category-${currentCategory}">
                                            <h2>${currentCategory}</h2>
                                        </div>
                                    </c:if>

                                    <div class="menu-item-row" id="item-row-${item.foodId}">
                                        <div class="item-details">
                                            <div class="veg-icon-container">
                                                <div class="veg-icon ${item.vegetarian ? 'veg' : 'non-veg'}">
                                                    <div class="dot"></div>
                                                </div>
                                            </div>
                                            <c:if test="${item.bestseller}">
                                                <div class="bestseller-tag">&#x1F525; Bestseller</div>
                                            </c:if>
                                            <c:if test="${not empty item.itemTag}">
                                                <div class="bestseller-tag <c:choose>
                                                    <c:when test=" ${item.itemTag.contains('Favorite')}">favorite
                                                    </c:when>
                                                    <c:when test="${item.itemTag.contains('Ordered')}">ordered</c:when>
                                                    <c:when test="${item.itemTag.contains('Budget')}">budget</c:when>
                                                    </c:choose>">
                                                    <c:choose>
                                                        <c:when test="${item.itemTag.contains('Favorite')}">&#x2B50;
                                                        </c:when>
                                                        <c:when test="${item.itemTag.contains('Ordered')}">&#x1F525;
                                                        </c:when>
                                                        <c:when test="${item.itemTag.contains('Budget')}">&#x1F4B0;
                                                        </c:when>
                                                        <c:otherwise>&#x2728;</c:otherwise>
                                                    </c:choose>
                                                    ${item.itemTag}
                                                </div>
                                            </c:if>
                                            <h3 class="item-name">${item.name}</h3>
                                            <div class="item-rating">
                                                <c:if test="${item.rating > 0}">
                                                    <span class="rating-star">★</span>
                                                    <span class="rating-value">${item.rating}</span>
                                                    <span class="rating-count">(${item.ratingCount})</span>
                                                </c:if>
                                            </div>
                                            <div class="item-price">
                                                <span class="currency">₹</span>
                                                <span class="amount">
                                                    <fmt:formatNumber value="${item.price}" pattern="#,###" />
                                                </span>
                                            </div>
                                            <p class="item-description">${item.description}</p>
                                        </div>
                                        <div class="item-media">
                                            <c:if test="${not empty item.imageUrl}">
                                                <div class="item-image">
                                                    <img src="${item.imageUrl}" alt="${item.name}">
                                                </div>
                                            </c:if>
                                            <!-- AJAX ADD button -->
                                            <button type="button" class="add-item-btn"
                                                    data-food-id="${item.foodId}"
                                                    data-restaurant-id="${restaurant.restaurantId}"
                                                    data-redirect="/menu?restaurantId=${restaurant.restaurantId}"
                                                    onclick="addToCart(this)">ADD</button>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>
                </section>

                <!-- Floating Cart Button (dynamic) -->
                <a href="${pageContext.request.contextPath}/cart" class="floating-cart" id="floating-cart-btn" style="display:none">
                    🛒 View Cart (<span id="floating-cart-count">0</span> items)
                    <span class="multi-rest-badge" id="multi-rest-badge" style="display:none">Multi-Restaurant</span>
                </a>

                <!-- Proximity Conflict Modal -->
                <div class="proximity-backdrop" id="proximity-modal">
                    <div class="proximity-modal">
                        <div class="modal-icon">📍</div>
                        <h3>Restaurants Too Far Apart</h3>
                        <p id="proximity-modal-msg">
                            <strong id="proximity-new-rest"></strong> is more than 3 km away from
                            <strong id="proximity-conflict-rest"></strong> (already in your cart).
                            <br><br>
                            You can only combine orders from restaurants within <strong>3 km</strong> of each other.
                        </p>
                        <div class="modal-actions">
                            <button class="btn-modal-keep" onclick="closeProximityModal()">Keep Current Cart</button>
                            <button class="btn-modal-clear" id="proximity-clear-btn" onclick="clearAndAdd()">Clear Cart &amp; Add</button>
                        </div>
                    </div>
                </div>

                <!-- Toast Container -->
                <div class="toast-container" id="toast-container"></div>

                <footer class="footer">
                    <div class="container">
                        <p>&copy; 2026 FoodExpress. All rights reserved.</p>
                    </div>
                </footer>

                <script>
                    var CTX = '${pageContext.request.contextPath}';
                    var cartSize = ${empty sessionScope.cart ? 0 : sessionScope.cart.size()};
                    var pendingFoodId = null;
                    var pendingQuantity = 1;
                    var pendingRedirect = null;
                    var cartRestaurantCount = ${empty sessionScope.cartRestaurantCount ? 0 : sessionScope.cartRestaurantCount};

                    // Initialise floating cart on page load
                    (function() {
                        if (cartSize > 0) showFloatingCart(cartSize);
                    })();

                    function addToCart(btn) {
                        var foodId   = btn.getAttribute('data-food-id');
                        var redirect = btn.getAttribute('data-redirect');
                        pendingFoodId   = foodId;
                        pendingQuantity = 1;
                        pendingRedirect = redirect;

                        btn.classList.add('adding');
                        btn.textContent = '...';

                        var params = 'foodId=' + foodId + '&quantity=1&ajax=true&redirect=' + encodeURIComponent(redirect);
                        fetch(CTX + '/cart/add', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded',
                                'X-Requested-With': 'XMLHttpRequest'
                            },
                            body: params
                        })
                        .then(function(res) { return res.json(); })
                        .then(function(data) {
                            btn.classList.remove('adding');

                            if (data.status === 'ok') {
                                cartSize = data.cartSize;
                                cartRestaurantCount = data.restaurantCount || cartRestaurantCount;
                                btn.textContent = 'ADDED ✓';
                                btn.classList.add('added');
                                setTimeout(function() {
                                    btn.textContent = 'ADD';
                                    btn.classList.remove('added');
                                }, 1800);
                                showFloatingCart(cartSize);
                                updateNavBadge(cartSize);
                                showToast('Added to cart!', 'success');
                            } else if (data.status === 'too_far') {
                                btn.textContent = 'ADD';
                                showProximityModal(data.newRestaurant, data.conflictRestaurant, data.foodId, data.quantity);
                            } else if (data.status === 'auth_required') {
                                window.location.href = CTX + '/login?auth=required';
                            } else {
                                btn.textContent = 'ADD';
                                showToast('Could not add item. Please try again.', 'error');
                            }
                        })
                        .catch(function() {
                            btn.classList.remove('adding');
                            btn.textContent = 'ADD';
                            showToast('Network error. Please try again.', 'error');
                        });
                    }

                    function showFloatingCart(count) {
                        var btn = document.getElementById('floating-cart-btn');
                        var countEl = document.getElementById('floating-cart-count');
                        var badge = document.getElementById('multi-rest-badge');
                        if (!btn) return;
                        btn.style.display = count > 0 ? 'block' : 'none';
                        countEl.textContent = count;
                        // Show multi-restaurant badge if we detect multiple restaurants
                        // (we track this via cartRestaurantCount set by server on session)
                        if (cartRestaurantCount > 1) {
                            badge.style.display = 'inline';
                        }
                    }

                    function updateNavBadge(count) {
                        var badge = document.getElementById('nav-cart-badge');
                        if (!badge) return;
                        badge.textContent = count;
                        badge.style.display = count > 0 ? 'inline-flex' : 'none';
                    }

                    function showProximityModal(newRest, conflictRest, foodId, qty) {
                        pendingFoodId   = foodId;
                        pendingQuantity = qty || 1;
                        document.getElementById('proximity-new-rest').textContent     = newRest;
                        document.getElementById('proximity-conflict-rest').textContent = conflictRest;
                        document.getElementById('proximity-modal').classList.add('active');
                    }

                    function closeProximityModal() {
                        document.getElementById('proximity-modal').classList.remove('active');
                    }

                    function clearAndAdd() {
                        closeProximityModal();
                        var params = 'foodId=' + pendingFoodId + '&quantity=' + pendingQuantity
                                   + '&force=true&ajax=true';
                        fetch(CTX + '/cart/add', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded',
                                'X-Requested-With': 'XMLHttpRequest'
                            },
                            body: params
                        })
                        .then(function(res) { return res.json(); })
                        .then(function(data) {
                            if (data.status === 'ok') {
                                cartSize = data.cartSize;
                                cartRestaurantCount = 1;
                                showFloatingCart(cartSize);
                                updateNavBadge(cartSize);
                                showToast('Cart cleared! Item added from new restaurant.', 'success');
                            }
                        });
                    }

                    function showToast(msg, type) {
                        var container = document.getElementById('toast-container');
                        var toast = document.createElement('div');
                        toast.className = 'toast toast-' + (type || 'success');
                        toast.textContent = msg;
                        container.appendChild(toast);
                        setTimeout(function() { toast.remove(); }, 2600);
                    }

                    // Close modal on backdrop click
                    document.getElementById('proximity-modal').addEventListener('click', function(e) {
                        if (e.target === this) closeProximityModal();
                    });
                </script>
            </body>

            </html>