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
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/payment.css?v=${System.currentTimeMillis()}">
                <!-- Leaflet Map Dependencies -->
                <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" integrity="sha256-p4NxAoJBhIIN+hmNHrzRCf9tD/miZyoHS5obTRR9BMY=" crossorigin="" />
                <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js" integrity="sha256-20nQCchB9co0qIjJZRGuk2/Z9VM+kNiyxNV1lvTlZBo=" crossorigin=""></script>
                <style>
                    #map { height: 300px; border-radius: 12px; margin-bottom: 1rem; display: none; border: 2px solid var(--primary-100); }
                    .map-controls { display: flex; gap: 0.5rem; margin-bottom: 1rem; }
                    .btn-map { display: flex; align-items: center; gap: 0.5rem; padding: 0.5rem 1rem; font-size: 0.9rem; background: #fff; border: 1px solid var(--primary-500); color: var(--primary-600); border-radius: 8px; cursor: pointer; font-weight: 600; transition: all 0.2s; }
                    .btn-map:hover { background: var(--primary-50); }
                    .location-status { font-size: 0.8rem; color: var(--gray-500); margin-top: 0.25rem; }
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
                            <a href="${pageContext.request.contextPath}/cart" class="nav-link">🛒 Cart</a>
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

                <section class="section" style="background: #f8fafc; min-height: calc(100vh - 80px);">
                    <div class="container" style="max-width: 1100px; padding: 2rem 1rem;">
                        <h1 class="page-title" style="text-align: center; margin-bottom: 3rem; font-family: 'Outfit', sans-serif; font-weight: 800; color: #1e293b;">Finalize Your Order</h1>
                        <div class="checkout-layout">
                            <!-- Left: Checkout Details -->
                            <div class="checkout-form-section">
                                <form action="${pageContext.request.contextPath}/checkout" method="POST" id="checkoutForm">
                                    <input type="hidden" name="paymentMethod" id="finalPaymentMethod" value="cod">
                                    
                                    <!-- Delivery Section -->
                                    <div class="form-card modern-card">
                                        <div class="card-section">
                                            <div class="form-group">
                                                <input type="text" name="fullName" class="form-input-minimal" value="${user.fullName}" placeholder="Enter Name" required>
                                            </div>
                                            <div class="form-group">
                                                <label class="form-label-small">Phone</label>
                                                <input type="text" name="phone" class="form-input-minimal" value="${user.phone}" placeholder="Phone Number" required>
                                            </div>
                                        </div>

                                        <div class="card-section">
                                            <label class="form-label-small">Delivery Address</label>
                                            <div class="map-controls">
                                                <button type="button" class="btn-map" id="btnPickLocation">
                                                    <span class="icon-pin">📍</span> Pick on Map
                                                </button>
                                                <button type="button" class="btn-map" id="btnLocateMe">
                                                    <span class="icon-locate">🎯</span> Locate Me
                                                </button>
                                            </div>
                                            
                                            <div id="map"></div>
                                            <div id="locationStatus" class="location-status"></div>

                                            <textarea name="address" id="addressField" class="form-input-minimal textarea-minimal" rows="3" required
                                                placeholder="Enter full delivery address">${user.address}</textarea>
                                        </div>
                                    </div>

                                    <!-- Payment Method Section -->
                                    <div class="form-card modern-card">
                                        <h2 class="section-title">Payment Method</h2>
                                        <div class="payment-selection-groups">
                                            <label class="payment-selection-item">
                                                <input type="radio" name="paymentOption" value="cod" checked onchange="togglePayBtn()">
                                                <div class="selection-box">
                                                    <div class="selection-indicator"></div>
                                                    <span class="selection-icon">💵</span>
                                                    <span class="selection-text">Cash on Delivery</span>
                                                </div>
                                            </label>
                                            <label class="payment-selection-item">
                                                <input type="radio" name="paymentOption" value="online" onchange="togglePayBtn()">
                                                <div class="selection-box">
                                                    <div class="selection-indicator"></div>
                                                    <span class="selection-icon">💳</span>
                                                    <span class="selection-text">Online Payment (Cards, UPI, Wallets)</span>
                                                </div>
                                            </label>
                                        </div>
                                    </div>

                                    <!-- Special Instructions -->
                                    <div class="form-card modern-card">
                                        <h2 class="section-title">Special Instructions</h2>
                                        <textarea name="instructions" class="form-input-minimal textarea-minimal" rows="2"
                                            placeholder="Any special instructions for your order?"></textarea>
                                    </div>

                                    <button type="button" id="submitOrderBtn" class="btn-primary-large" onclick="handleCheckout()">
                                        Place Order
                                    </button>
                                </form>
                            </div>

                            <!-- Right: Order Summary -->
                            <div class="order-summary-section">
                                <div class="order-summary-card modern-card sticky-summary">
                                    <h2 class="section-title">Order Summary</h2>
                                    <div class="order-items-list">
                                        <c:forEach var="item" items="${cartItems}">
                                            <div class="summary-item">
                                                <span class="item-desc">${item.foodName} x${item.quantity}</span>
                                                <span class="item-val">₹<fmt:formatNumber value="${item.subtotal}" pattern="#,##0" /></span>
                                            </div>
                                        </c:forEach>
                                    </div>
                                    
                                    <div class="summary-divider"></div>
                                    
                                    <div class="summary-rows">
                                        <div class="summary-row">
                                            <span>Subtotal</span>
                                            <span>₹<fmt:formatNumber value="${cartTotal}" pattern="#,##0" /></span>
                                        </div>
                                        <div class="summary-row">
                                            <span>Delivery Fee</span>
                                            <span>₹20</span>
                                        </div>
                                        <div class="summary-row total-row">
                                            <span>Total</span>
                                            <span class="total-amount">₹<fmt:formatNumber value="${cartTotal + 20}" pattern="#,##0" /></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <!-- Razorpay Style Payment Modal -->
                <div class="payment-modal-overlay" id="paymentModal">
                    <div class="payment-modal-container">
                        <!-- Left Panel: Price & Branding -->
                        <div class="payment-sidebar">
                            <div class="sidebar-header">
                                <div class="store-initial" style="background: rgba(255,255,255,0.2) !important;">F</div>
                                <div class="store-name" style="font-size: 1.25rem !important;">FoodExpress</div>
                            </div>
                            
                            <div class="price-summary-box">
                                <div class="summary-label">Price Summary</div>
                                <div class="summary-amount">₹<fmt:formatNumber value="${cartTotal + 20}" pattern="#,##0" /></div>
                            </div>

                            <div class="user-info-bar">
                                <div class="user-id">
                                    <span class="user-icon">👤</span>
                                    <span>Using as +91 ${user.phone}</span>
                                </div>
                                <span class="chevron">❯</span>
                            </div>

                            <div class="sidebar-graphics">
                                <div class="graphic-stack">
                                    <div class="graphic-rect rect-1"></div>
                                    <div class="graphic-rect rect-2"></div>
                                    <div class="graphic-rect rect-3"></div>
                                </div>
                            </div>

                            <div class="secured-by">
                                <span class="secured-text">Secured by</span>
                                <span class="razorpay-logo">Razorpay</span>
                            </div>
                        </div>

                        <!-- Right Panel: Options -->
                        <div class="payment-main">
                            <div class="main-header">
                                <span class="header-title">Payment Options</span>
                                <div class="header-controls">
                                    <span class="header-dots">•••</span>
                                    <button class="modal-close-btn" onclick="closePaymentModal()">&times;</button>
                                </div>
                            </div>

                            <div class="payment-options-layout">
                                <div class="options-sidebar">
                                    <button type="button" class="option-nav-item active" onclick="switchPaymentView('upi', this)">
                                        <span class="option-label">UPI</span>
                                        <div class="option-logos">
                                            <span class="mini-icons">📱 💳 📲</span>
                                        </div>
                                    </button>
                                    <button type="button" class="option-nav-item" onclick="switchPaymentView('cards', this)">
                                        <span class="option-label">Cards</span>
                                        <div class="option-logos">
                                            <span class="mini-icons">Visa • Master • Maestro</span>
                                        </div>
                                    </button>
                                    <button type="button" class="option-nav-item" onclick="switchPaymentView('emi', this)">
                                        <span class="option-label">EMI</span>
                                        <div class="option-logos">
                                            <span class="mini-icons">ZestMoney • EarlySalary</span>
                                        </div>
                                    </button>
                                    <button type="button" class="option-nav-item" onclick="switchPaymentView('netbanking', this)">
                                        <span class="option-label">Netbanking</span>
                                        <div class="option-logos">
                                            <span class="mini-icons">SBI • ICICI • HDFC • AXIS</span>
                                        </div>
                                    </button>
                                    <button type="button" class="option-nav-item" onclick="switchPaymentView('wallet', this)">
                                        <span class="option-label">Wallet</span>
                                        <div class="option-logos">
                                            <span class="mini-icons">Mobikwik • Freecharge</span>
                                        </div>
                                    </button>
                                </div>

                                <div class="content-area" id="paymentContentArea">
                                    <!-- UPI QR View -->
                                    <div id="view-upi" class="upi-qr-view">
                                        <div class="qr-header-row">
                                            <span class="qr-title">UPI QR</span>
                                            <div class="qr-timer-box">
                                                <span class="timer-icon">⏳</span>
                                                <span id="paymentTimer">09:59</span>
                                            </div>
                                        </div>
                                        
                                        <div class="qr-main-box">
                                            <img src="https://api.qrserver.com/v1/create-qr-code/?size=160x160&data=foodexpress-payment-mock" alt="Payment QR" class="qr-image">
                                        </div>
                                        
                                        <p class="scan-instructions">Scan the QR using any UPI App</p>
                                        <div class="upi-app-logos">
                                            <span class="app-logo-text">Google Pay | PhonePe | Paytm | Amazon Pay</span>
                                        </div>
                                        
                                        <button class="btn-payment-confirm" onclick="processOrder('UPI')">
                                            I have completed the payment
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <script>
                    function togglePayBtn() {
                        const btn = document.getElementById('submitOrderBtn');
                        const isCod = document.querySelector('input[name="paymentOption"]:checked').value === 'cod';
                        btn.textContent = isCod ? 'Place Order' : 'Proceed to Pay';
                    }

                    function handleCheckout() {
                        const isCod = document.querySelector('input[name="paymentOption"]:checked').value === 'cod';
                        if (isCod) {
                            document.getElementById('finalPaymentMethod').value = 'cod';
                            document.getElementById('checkoutForm').submit();
                        } else {
                            document.getElementById('paymentModal').style.display = 'flex';
                            startTimer();
                        }
                    }

                    function closePaymentModal() {
                        document.getElementById('paymentModal').style.display = 'none';
                    }

                    function switchPaymentView(method, element) {
                        const allNavItems = document.querySelectorAll('.option-nav-item');
                        allNavItems.forEach(item => item.classList.remove('active'));
                        if(element) element.classList.add('active');
                        
                        const contentArea = document.getElementById('paymentContentArea');
                        if (method === 'upi') {
                            contentArea.innerHTML = 
                                '<div class="upi-qr-view">' +
                                    '<div class="qr-header-row">' +
                                        '<span class="qr-title">UPI QR SCANNER</span>' +
                                        '<div class="qr-timer-box">⏳ <span id="paymentTimer">09:59</span></div>' +
                                    '</div>' +
                                    '<div class="qr-main-box">' +
                                        '<img src="https://api.qrserver.com/v1/create-qr-code/?size=180x180&data=foodexpress-pay&color=ea580c" class="qr-image" alt="Scan to Pay">' +
                                    '</div>' +
                                    '<div style="text-align:center; margin-top:0.5rem;">' +
                                        '<p style="font-weight:700; color:#333; margin:0;">Scan QR using any UPI App</p>' +
                                        '<p style="font-size:0.75rem; color:#666; margin:2px 0;">Google Pay | PhonePe | Paytm | Amazon Pay</p>' +
                                    '</div>' +
                                    '<button type="button" class="btn-payment-confirm" onclick="processOrder(\'UPI\')">I have completed the payment</button>' +
                                '</div>';
                        } else {
                            const iconMap = { 'cards': '💳', 'emi': '💎', 'netbanking': '🏦', 'wallet': '👛' };
                            const displayName = method.charAt(0).toUpperCase() + method.slice(1);
                            const icon = iconMap[method] || '🔐';
                            contentArea.innerHTML = 
                                '<div style="display:flex; flex-direction:column; align-items:center; justify-content:center; height:100%; gap:1.5rem; text-align:center;">' +
                                    '<div style="font-size: 4rem;">' + icon + '</div>' +
                                    '<div>' +
                                        '<h3 style="margin:0; color:#333;">Secure ' + displayName + ' Gateway</h3>' +
                                        '<p style="color:#666; font-size:0.9rem; margin-top:0.5rem;">Redirecting to encrypted payment channel...</p>' +
                                    '</div>' +
                                    '<button type="button" class="btn-payment-confirm" style="max-width:300px;" onclick="processOrder(\'' + method.toUpperCase() + '\')">Continue with ' + displayName + '</button>' +
                                '</div>';
                        }
                    }

                    function processOrder(method) {
                        document.getElementById('finalPaymentMethod').value = method;
                        document.getElementById('paymentModal').innerHTML = `
                            <div class="payment-modal-container" style="justify-content:center; align-items:center; flex-direction:column; gap:1.5rem;">
                                <div style="font-size: 4rem; color:#ea580c;">✅</div>
                                <h2 style="margin:0;">Payment Successful</h2>
                                <p>Processing your order...</p>
                            </div>
                        `;
                        setTimeout(() => {
                            document.getElementById('checkoutForm').submit();
                        }, 1500);
                    }

                    function startTimer() {
                        let totalSeconds = 600;
                        const timerElement = document.getElementById('paymentTimer');
                        const timerInterval = setInterval(() => {
                            const minutes = Math.floor(totalSeconds / 60);
                            const seconds = totalSeconds % 60;
                            if (timerElement) {
                                timerElement.textContent = `\${minutes.toString().padStart(2, '0')}:\${seconds.toString().padStart(2, '0')}`;
                            }
                            if (totalSeconds <= 0) clearInterval(timerInterval);
                            totalSeconds--;
                        }, 1000);
                    }

                    let map;
                    let marker;
                    const addressField = document.getElementById('addressField');
                    const locationStatus = document.getElementById('locationStatus');
                    const btnPickLocation = document.getElementById('btnPickLocation');
                    const btnLocateMe = document.getElementById('btnLocateMe');
                    const mapDiv = document.getElementById('map');

                    function initMap(lat = 12.9716, lng = 77.5946) { // Default to Bangalore
                        if (map) return;
                        
                        mapDiv.style.display = 'block';
                        map = L.map('map').setView([lat, lng], 13);
                        
                        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                            attribution: '© OpenStreetMap contributors'
                        }).addTo(map);

                        marker = L.marker([lat, lng], { draggable: true }).addTo(map);

                        marker.on('dragend', function(e) {
                            const position = marker.getLatLng();
                            reverseGeocode(position.lat, position.lng);
                        });

                        map.on('click', function(e) {
                            marker.setLatLng(e.latlng);
                            reverseGeocode(e.latlng.lat, e.latlng.lng);
                        });
                    }

                    async function reverseGeocode(lat, lng) {
                        locationStatus.textContent = "🔍 Fetching address...";
                        try {
                            const response = await fetch(`https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lng}`);
                            const data = await response.json();
                            if (data && data.display_name) {
                                addressField.value = data.display_name;
                                locationStatus.textContent = "✅ Location selected";
                            }
                        } catch (error) {
                            console.error("Geocoding error:", error);
                            locationStatus.textContent = "❌ Failed to fetch address";
                        }
                    }

                    btnPickLocation.addEventListener('click', () => {
                        if (mapDiv.style.display === 'block') {
                            mapDiv.style.display = 'none';
                            btnPickLocation.innerHTML = '📍 Pick on Map';
                        } else {
                            initMap();
                            map.invalidateSize();
                            btnPickLocation.innerHTML = '🗺️ Hide Map';
                        }
                    });

                    btnLocateMe.addEventListener('click', () => {
                        if (!navigator.geolocation) {
                            alert("Geolocation is not supported by your browser");
                            return;
                        }

                        locationStatus.textContent = "🛰️ Locating you...";
                        navigator.geolocation.getCurrentPosition((position) => {
                            const lat = position.coords.latitude;
                            const lng = position.coords.longitude;
                            
                            initMap(lat, lng);
                            map.setView([lat, lng], 16);
                            marker.setLatLng([lat, lng]);
                            reverseGeocode(lat, lng);
                        }, () => {
                            locationStatus.textContent = "❌ Unable to retrieve your location";
                        });
                    });
                </script>
            </body>

            </html>