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
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/payment.css">
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
                                            <input type="text" name="fullName" class="form-input" value="${user.fullName}" required>
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">Phone</label>
                                            <input type="text" name="phone" class="form-input" value="${user.phone}" required>
                                        </div>
                                        <div class="form-group">
                                            <label class="form-label">Delivery Address</label>
                                            
                                            <div class="map-controls">
                                                <button type="button" class="btn-map" id="btnPickLocation">
                                                    📍 Pick on Map
                                                </button>
                                                <button type="button" class="btn-map" id="btnLocateMe">
                                                    🎯 Locate Me
                                                </button>
                                            </div>
                                            
                                            <div id="map"></div>
                                            <div id="locationStatus" class="location-status"></div>

                                            <textarea name="address" id="addressField" class="form-input" rows="3" required
                                                placeholder="Enter full delivery address">${user.address}</textarea>
                                        </div>
                                    </div>

                                    <div class="form-card">
                                        <h3>Payment Method</h3>
                                        <div class="payment-options">
                                            <label class="payment-option">
                                                <input type="radio" name="paymentOption" value="cod" checked onchange="togglePayBtn()">
                                                <span class="payment-label">💵 Cash on Delivery</span>
                                            </label>
                                            <label class="payment-option">
                                                <input type="radio" name="paymentOption" value="online" onchange="togglePayBtn()">
                                                <span class="payment-label">💳 Online Payment (Cards, UPI, Wallets)</span>
                                            </label>
                                        </div>
                                        <input type="hidden" name="paymentMethod" id="finalPaymentMethod" value="cod">
                                    </div>

                                    <div class="form-card">
                                        <h3>Special Instructions</h3>
                                        <div class="form-group">
                                            <textarea name="instructions" class="form-input" rows="2"
                                                placeholder="Any special instructions for your order?"></textarea>
                                        </div>
                                    </div>

                                    <button type="button" id="submitOrderBtn" class="btn btn-primary btn-lg btn-block" onclick="handleCheckout()">Place Order</button>
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
                                        <span>₹20.00</span>
                                    </div>
                                    <div class="summary-row total">
                                        <span>Total</span>
                                        <span>₹
                                            <fmt:formatNumber value="${cartTotal + 20}" pattern="#,##0.00" />
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
                <!-- Razorpay Style Payment Modal -->
                <div class="payment-modal-overlay" id="paymentModal">
                    <div class="payment-modal-container">
                        <!-- Sidebar -->
                        <div class="payment-sidebar">
                            <div class="sidebar-header">
                                <div class="store-initial">F</div>
                                <div class="store-name">FoodExpress</div>
                            </div>
                            
                            <div class="price-summary-box">
                                <div class="summary-label">Price Summary</div>
                                <div class="summary-amount">₹<fmt:formatNumber value="${cartTotal + 20}" pattern="#,##0" /></div>
                            </div>

                            <div class="user-info-bar">
                                <span>Using as +91 ${user.phone}</span>
                                <span style="font-size: 0.7rem; opacity: 0.7;">❯</span>
                            </div>

                            <div class="sidebar-graphic">
                                <svg width="100%" height="150" viewBox="0 0 200 100">
                                    <rect x="20" y="50" width="30" height="30" fill="rgba(255,255,255,0.2)" rx="4"/>
                                    <rect x="60" y="30" width="30" height="50" fill="rgba(255,255,255,0.2)" rx="4"/>
                                    <rect x="100" y="60" width="30" height="20" fill="rgba(255,255,255,0.2)" rx="4"/>
                                </svg>
                            </div>

                            <div class="secured-by">
                                <span style="opacity: 0.7;">Secured by</span>
                                <span style="font-weight: 800; font-family: 'Outfit';">Razorpay</span>
                            </div>
                        </div>

                        <!-- Main -->
                        <div class="payment-main">
                            <div class="main-header">
                                <h3>Payment Options</h3>
                                <button class="modal-close-btn" onclick="closePaymentModal()">&times;</button>
                            </div>

                            <div class="payment-options-layout">
                                <div class="options-sidebar">
                                    <div class="option-item active" onclick="switchPaymentView('upi', this)">
                                        <span class="option-name">UPI</span>
                                        <div class="option-icons">
                                            <span style="font-size: 8px;">GPay | PhonePe | Paytm</span>
                                        </div>
                                    </div>
                                    <div class="option-item" onclick="switchPaymentView('cards', this)">
                                        <span class="option-name">Cards</span>
                                        <div class="option-icons">
                                            <span style="font-size: 8px;">Visa | Master | RuPay</span>
                                        </div>
                                    </div>
                                    <div class="option-item" onclick="switchPaymentView('netbanking', this)">
                                        <span class="option-name">Netbanking</span>
                                        <div class="option-icons">
                                            <span style="font-size: 8px;">All Indian Banks</span>
                                        </div>
                                    </div>
                                    <div class="option-item" onclick="switchPaymentView('wallet', this)">
                                        <span class="option-name">Wallet</span>
                                        <div class="option-icons">
                                            <span style="font-size: 8px;">Amazon Pay | Mobikwik</span>
                                        </div>
                                    </div>
                                </div>

                                <div class="content-area" id="paymentContentArea">
                                    <!-- UPI View (Default) -->
                                    <div id="view-upi" class="qr-section">
                                        <div class="qr-header">
                                            <span style="font-weight: 600; font-size: 0.9rem;">UPI QR</span>
                                            <div class="qr-timer">
                                                <span>⏳</span>
                                                <span id="paymentTimer">09:59</span>
                                            </div>
                                        </div>
                                        <div class="qr-box">
                                            <img src="https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=foodexpress-payment-mock" alt="Payment QR">
                                        </div>
                                        <div class="scan-text">Scan the QR using any UPI App</div>
                                        <div class="upi-logos">
                                            <span style="font-size: 1.5rem;">📱 💳 📲</span>
                                        </div>
                                        <button class="btn btn-primary" style="width: 100%; margin-top: 1rem;" onclick="processOrder('UPI')">I've Scanned and Paid</button>
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

                    function switchPaymentView(view, element) {
                        document.querySelectorAll('.option-item').forEach(i => i.classList.remove('active'));
                        element.classList.add('active');
                        
                        const contentArea = document.getElementById('paymentContentArea');
                        if(view === 'upi') {
                            contentArea.innerHTML = `
                                <div class="qr-section">
                                    <div class="qr-header">
                                        <span style="font-weight: 600; font-size: 0.9rem;">UPI QR</span>
                                        <div class="qr-timer"><span>⏳</span><span>09:59</span></div>
                                    </div>
                                    <div class="qr-box">
                                        <img src="https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=foodexpress-pay" alt="QR">
                                    </div>
                                    <div class="scan-text">Scan the QR using any UPI App</div>
                                    <button class="btn btn-primary" style="width: 100%; margin-top: 1rem;" onclick="processOrder('UPI')">Paid via App</button>
                                </div>
                            `;
                        } else {
                            contentArea.innerHTML = `
                                <div style="display:flex; flex-direction:column; gap:1.5rem; justify-content:center; height:100%; text-align:center;">
                                    <div style="font-size: 3rem;">🔐</div>
                                    <h4 style="margin:0; color:#333;">Secure ${view.toUpperCase()} Integration</h4>
                                    <p style="font-size:0.9rem; color:#666;">Redirecting to secure gateway...</p>
                                    <button class="btn btn-primary" onclick="processOrder('${view.toUpperCase()}')">Complete Payment</button>
                                </div>
                            `;
                        }
                    }

                    function processOrder(method) {
                        document.getElementById('finalPaymentMethod').value = method;
                        document.getElementById('paymentModal').innerHTML = `
                            <div class="payment-modal-container" style="justify-content:center; align-items:center; flex-direction:column; gap:1.5rem;">
                                <div style="font-size: 4rem; color:#00845a;">✅</div>
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