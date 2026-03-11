<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Home | FoodExpress</title>
            <link
                href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Outfit:wght@600;700;800&display=swap"
                rel="stylesheet">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages.css">
            <!-- Leaflet Map Dependencies -->
            <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" integrity="sha256-p4NxAoJBhIIN+hmNHrzRCf9tD/miZyoHS5obTRR9BMY=" crossorigin="" />
            <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js" integrity="sha256-20nQCchB9co0qIjJZRGuk2/Z9VM+kNiyxNV1lvTlZBo=" crossorigin=""></script>
            <style>
                .modal-overlay { display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.6); z-index: 2000; align-items: center; justify-content: center; backdrop-filter: blur(4px); }
                .modal-content { background: white; width: 90%; max-width: 600px; border-radius: 20px; overflow: hidden; box-shadow: var(--shadow-2xl); position: relative; animation: modalIn 0.3s ease-out; }
                @keyframes modalIn { from { transform: translateY(30px); opacity: 0; } to { transform: translateY(0); opacity: 1; } }
                .modal-header { padding: 1.5rem; border-bottom: 1px solid var(--gray-100); display: flex; justify-content: space-between; align-items: center; }
                .modal-header h3 { font-family: 'Outfit', sans-serif; font-size: 1.25rem; }
                .close-modal { background: none; border: none; font-size: 1.5rem; cursor: pointer; color: var(--gray-400); }
                .modal-body { padding: 1.5rem; }
                #homeMap { height: 350px; border-radius: 12px; margin-bottom: 1.5rem; border: 1px solid var(--gray-200); }
                .modal-footer { padding: 1rem 1.5rem; background: var(--gray-50); display: flex; justify-content: flex-end; gap: 1rem; }
                .selected-address-box { margin-bottom: 1rem; padding: 1rem; background: var(--primary-50); border-radius: 10px; font-size: 0.9rem; color: var(--gray-700); border-left: 4px solid var(--primary-500); }
                .address-textarea { width: 100%; border: none; background: transparent; font-family: inherit; font-size: 0.9rem; color: var(--gray-800); resize: none; outline: none; }
                .map-search-container { position: relative; margin-bottom: 1rem; }
                .map-search-input { width: 100%; padding: 0.875rem 1rem 0.875rem 2.5rem; border: 1px solid var(--gray-200); border-radius: 12px; font-size: 0.95rem; transition: all 0.2s; }
                .map-search-input:focus { outline: none; border-color: var(--primary-500); box-shadow: 0 0 0 4px var(--primary-50); }
                .search-icon-inside { position: absolute; left: 1rem; top: 50%; transform: translateY(-50%); color: var(--gray-400); font-size: 0.9rem; }
                .search-results-dropdown { position: absolute; top: 100%; left: 0; width: 100%; background: white; border-radius: 12px; box-shadow: var(--shadow-xl); z-index: 2100; margin-top: 0.5rem; max-height: 250px; overflow-y: auto; display: none; border: 1px solid var(--gray-100); }
                .search-result-item { padding: 0.75rem 1rem; cursor: pointer; border-bottom: 1px solid var(--gray-50); font-size: 0.9rem; display: flex; align-items: flex-start; gap: 0.75rem; }
                .search-result-item:hover { background: var(--primary-25); }
                .search-result-item i { margin-top: 2px; color: var(--gray-400); }
            </style>
        </head>

        <body>
            <!-- Navigation -->
            <nav class="navbar">
                <div class="nav-container">
                    <a href="${pageContext.request.contextPath}/home" class="nav-logo">
                        <span class="logo-icon">🍕</span>
                        <span class="logo-text">FoodExpress</span>
                    </a>

                    <div class="location-selector" id="openLocationModal">
                        <span class="location-icon">📍</span>
                        <span class="location-text" id="currentLocationText">Bangalore, India</span>
                        <span class="location-arrow">▼</span>
                    </div>

                    <div class="nav-links">
                        <a href="${pageContext.request.contextPath}/home" class="nav-link active">Home</a>
                        <a href="${pageContext.request.contextPath}/restaurants" class="nav-link">Restaurants</a>
                        <a href="${pageContext.request.contextPath}/cart" class="nav-link cart-link">
                            🛒 Cart
                            <c:if test="${not empty sessionScope.cart}">
                                <span class="cart-badge">${sessionScope.cart.size()}</span>
                            </c:if>
                        </a>
                    </div>
                    <div class="nav-auth">
                        <c:choose>
                            <c:when test="${not empty sessionScope.user}">
                                <span class="user-greeting">Hi, ${sessionScope.userName}</span>
                                <a href="${pageContext.request.contextPath}/orders" class="btn btn-outline">My
                                    Orders</a>
                                <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline">Logout</a>
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

            <!-- Hero Section -->
            <section class="hero">
                <div class="hero-wrapper">
                    <div class="hero-background">
                        <img src="https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=1920&q=80"
                            alt="Delicious Food Background">
                    </div>
                    <div class="hero-overlay"></div>
                    <div class="hero-content">
                        <div class="hero-badge">🚀 Fast & Reliable Delivery</div>
                        <h1>Order Online Delivery<br>Restaurants Near Me</h1>
                        <p>Discover the best food & drinks in your city. From local favorites to international cuisines,
                            we've got you covered.</p>
                        <form action="${pageContext.request.contextPath}/restaurants" method="GET" class="search-form">
                            <input type="text" name="search" placeholder="Search for restaurant and food..."
                                class="search-input">
                            <button type="submit" class="btn btn-primary">Search</button>
                        </form>
                    </div>
                </div>
            </section>

            <!-- Food Category Scroller -->
            <section class="category-scroller-section">
                <div class="section-header">
                    <h2 class="section-title-sm">What's on your mind?</h2>
                    <div class="scroller-controls">
                        <button class="scroll-btn"
                            onclick="document.querySelector('.category-scroller').scrollLeft -= 300">←</button>
                        <button class="scroll-btn"
                            onclick="document.querySelector('.category-scroller').scrollLeft += 300">→</button>
                    </div>
                </div>
                <div class="category-scroller">
                    <div class="scroller-item"
                        onclick="location.href='${pageContext.request.contextPath}/restaurants?search=idli'">
                        <div class="scroller-image">
                            <img src="${pageContext.request.contextPath}/images/idli.png" alt="Idli">
                        </div>
                        <span>Idli</span>
                    </div>
                    <div class="scroller-item"
                        onclick="location.href='${pageContext.request.contextPath}/restaurants?search=dosa'">
                        <div class="scroller-image">
                            <img src="${pageContext.request.contextPath}/images/dosa.png" alt="Dosa">
                        </div>
                        <span>Dosa</span>
                    </div>
                    <div class="scroller-item"
                        onclick="location.href='${pageContext.request.contextPath}/restaurants?search=vada'">
                        <div class="scroller-image">
                            <img src="${pageContext.request.contextPath}/images/vada.png" alt="Vada">
                        </div>
                        <span>Vada</span>
                    </div>
                    <div class="scroller-item"
                        onclick="location.href='${pageContext.request.contextPath}/restaurants?search=cakes'">
                        <div class="scroller-image">
                            <img src="${pageContext.request.contextPath}/images/cakes.png" alt="Cakes">
                        </div>
                        <span>Cakes</span>
                    </div>
                    <div class="scroller-item"
                        onclick="location.href='${pageContext.request.contextPath}/restaurants?search=bath'">
                        <div class="scroller-image">
                            <!-- Corrected: South Indian Bath (Upma/Chow-Chow Bath) -->
                            <img src="https://images.unsplash.com/photo-1626777552726-4a6b54c97e46?w=200&h=200&fit=crop" alt="Bath">
                        </div>
                        <span>Bath</span>
                    </div>
                    <div class="scroller-item"
                        onclick="location.href='${pageContext.request.contextPath}/restaurants?search=tea'">
                        <div class="scroller-image">
                            <img src="${pageContext.request.contextPath}/images/tea.png" alt="Tea">
                        </div>
                        <span>Tea</span>
                    </div>
                    <div class="scroller-item"
                        onclick="location.href='${pageContext.request.contextPath}/restaurants?search=pongal'">
                        <div class="scroller-image">
                            <img src="https://images.unsplash.com/photo-1621658537360-dfcb008fe19f?w=200&h=200&fit=crop" alt="Pongal">
                        </div>
                        <span>Pongal</span>
                    </div>
                </div>
            </section>

            <!-- Cuisines Section -->
            <section class="section">
                <div class="container">
                    <h2 class="section-title">Explore Cuisines</h2>
                    <div class="cuisine-grid">
                        <c:forEach var="cuisine" items="${cuisineStats}">
                            <a href="${pageContext.request.contextPath}/restaurants?cuisine=${cuisine.name}"
                                class="cuisine-card">
                                <div class="cuisine-image">
                                    <c:choose>
                                        <c:when test="${not empty cuisine.imageUrl}">
                                            <img src="${cuisine.imageUrl}" alt="${cuisine.name}">
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="cuisineLower" value="${cuisine.name.toLowerCase()}" />
                                            <c:choose>
                                                <c:when test="${cuisineLower == 'italian'}">
                                                    <img src="https://images.unsplash.com/photo-1513104890138-7c749659a591?w=100&h=100&fit=crop"
                                                        alt="Italian">
                                                </c:when>
                                                <c:when test="${cuisineLower == 'indian'}">
                                                    <img src="https://images.unsplash.com/photo-1585937421612-70a008356fbe?w=100&h=100&fit=crop"
                                                        alt="Indian">
                                                </c:when>
                                                <c:when test="${cuisineLower == 'chinese'}">
                                                    <img src="https://images.unsplash.com/photo-1585032226651-759b368d7246?w=100&h=100&fit=crop"
                                                        alt="Chinese">
                                                </c:when>
                                                <c:when test="${cuisineLower == 'mexican'}">
                                                    <img src="https://images.unsplash.com/photo-1565299585323-38d6b0865b47?w=100&h=100&fit=crop"
                                                        alt="Mexican">
                                                </c:when>
                                                <c:when test="${cuisineLower == 'american'}">
                                                    <img src="https://images.unsplash.com/photo-1550547660-d9450f859349?w=100&h=100&fit=crop"
                                                        alt="American">
                                                </c:when>
                                                <c:when test="${cuisineLower == 'beverages'}">
                                                    <img src="https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?w=100&h=100&fit=crop"
                                                        alt="Beverages">
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="cuisine-placeholder">🍽️</div>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <span class="cuisine-name">${cuisine.name}</span>
                                <span class="cuisine-stats">${cuisine.restaurantCount} Restaurants, ${cuisine.dishCount}
                                    Dishes</span>
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </section>

            <!-- Restaurants Section -->
            <section class="section section-gray">
                <div class="container">
                    <h2 class="section-title">Popular Restaurants</h2>
                    <div class="restaurant-grid">
                        <c:forEach var="restaurant" items="${restaurants}">
                            <div class="restaurant-card">
                                <div class="restaurant-image">
                                    <img src="${not empty restaurant.imageUrl ? restaurant.imageUrl : 'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=400'}"
                                        alt="${restaurant.name}">
                                    <span class="restaurant-rating">⭐ ${restaurant.rating}</span>
                                </div>
                                <div class="restaurant-info">
                                    <h3 class="restaurant-name">${restaurant.name}</h3>
                                    <p class="restaurant-cuisine">${restaurant.cuisineType}</p>
                                    <p class="restaurant-address">📍 ${restaurant.address}</p>
                                    <a href="${pageContext.request.contextPath}/menu?restaurantId=${restaurant.restaurantId}"
                                        class="btn btn-primary btn-block">View Menu</a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </section>

            <!-- Footer -->
            <footer class="footer">
                <div class="container">
                    <div class="footer-content">
                        <div class="footer-brand">
                            <span class="logo-icon">🍕</span>
                            <span class="logo-text">FoodExpress</span>
                            <p>Delivering happiness to your doorstep</p>
                        </div>
                        <div class="footer-links">
                            <h4>Quick Links</h4>
                            <div class="footer-links-container">
                                <a href="${pageContext.request.contextPath}/home">Home</a>
                                <a href="${pageContext.request.contextPath}/restaurants">Restaurants</a>
                                <a href="${pageContext.request.contextPath}/orders">My Orders</a>
                                <a href="${pageContext.request.contextPath}/cart">View Cart</a>
                            </div>
                        </div>
                    </div>
                    <div class="footer-bottom">
                        <p>&copy; 2026 FoodExpress. All rights reserved.</p>
                    </div>
                </div>
            </footer>

            <!-- Location Picker Modal -->
            <div class="modal-overlay" id="locationModal">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3>Select Delivery Location</h3>
                        <button class="close-modal" id="closeLocationModal">&times;</button>
                    </div>
                    <div class="modal-body">
                        <div class="map-search-container">
                            <span class="search-icon-inside">🔍</span>
                            <input type="text" id="mapSearchInput" class="map-search-input" placeholder="Type your area or building name...">
                            <div id="searchResultsDropdown" class="search-results-dropdown"></div>
                        </div>
                        <div class="selected-address-box">
                            <textarea id="modalAddressBox" class="address-textarea" rows="2" placeholder="Searching for address..."></textarea>
                        </div>
                        <div id="homeMap"></div>
                        <div class="map-controls">
                            <button type="button" class="btn btn-outline" id="btnHomeLocateMe">
                                🎯 Use Current Location
                            </button>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary" id="btnConfirmLocation">Confirm Location</button>
                    </div>
                </div>
            </div>

            <script>
                let homeMap;
                let homeMarker;
                let selectedAddress = "";
                const locationModal = document.getElementById('locationModal');
                const openLocationModal = document.getElementById('openLocationModal');
                const closeLocationModal = document.getElementById('closeLocationModal');
                const btnHomeLocateMe = document.getElementById('btnHomeLocateMe');
                const btnConfirmLocation = document.getElementById('btnConfirmLocation');
                const modalAddressBox = document.getElementById('modalAddressBox');
                const currentLocationText = document.getElementById('currentLocationText');

                function initHomeMap(lat = 12.9716, lng = 77.5946) {
                    if (homeMap) {
                        homeMap.setView([lat, lng], 13);
                        homeMarker.setLatLng([lat, lng]);
                        return;
                    }
                    
                    homeMap = L.map('homeMap').setView([lat, lng], 13);
                    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                        attribution: '© OpenStreetMap contributors'
                    }).addTo(homeMap);

                    homeMarker = L.marker([lat, lng], { draggable: true }).addTo(homeMap);
                    
                    homeMarker.on('dragend', function(e) {
                        updateHomeAddress(homeMarker.getLatLng().lat, homeMarker.getLatLng().lng);
                    });

                    homeMap.on('click', function(e) {
                        homeMarker.setLatLng(e.latlng);
                        updateHomeAddress(e.latlng.lat, e.latlng.lng);
                    });
                    
                    updateHomeAddress(lat, lng);
                }

                async function updateHomeAddress(lat, lng) {
                    modalAddressBox.value = "🔍 Finding address...";
                    try {
                        const response = await fetch(`https://nominatim.openstreetmap.org/reverse?format=json&lat=\${lat}&lon=\${lng}`);
                        const data = await response.json();
                        if (data && data.display_name) {
                            selectedAddress = data.display_name;
                            modalAddressBox.value = selectedAddress;
                        } else {
                            modalAddressBox.value = "";
                            modalAddressBox.placeholder = "Address not found. Please type manually.";
                        }
                    } catch (error) {
                        modalAddressBox.value = "";
                        modalAddressBox.placeholder = "Error fetching address. Please type manually.";
                    }
                }

                openLocationModal.addEventListener('click', () => {
                    locationModal.style.display = 'flex';
                    setTimeout(() => {
                        initHomeMap();
                        homeMap.invalidateSize();
                    }, 100);
                });

                closeLocationModal.addEventListener('click', () => {
                    locationModal.style.display = 'none';
                });

                window.addEventListener('click', (e) => {
                    if (e.target === locationModal) locationModal.style.display = 'none';
                });

                btnHomeLocateMe.addEventListener('click', () => {
                    if (navigator.geolocation) {
                        modalAddressBox.value = "🛰️ Connecting to GPS...";
                        navigator.geolocation.getCurrentPosition((pos) => {
                            const lat = pos.coords.latitude;
                            const lng = pos.coords.longitude;
                            homeMap.setView([lat, lng], 16);
                            homeMarker.setLatLng([lat, lng]);
                            updateHomeAddress(lat, lng);
                        }, () => {
                            modalAddressBox.value = "";
                            modalAddressBox.placeholder = "Unable to access location. Please search manually.";
                        });
                    }
                });

                btnConfirmLocation.addEventListener('click', () => {
                    const currentVal = modalAddressBox.value.trim();
                    if (currentVal && !currentVal.startsWith('🔍') && !currentVal.startsWith('🛰️')) {
                        selectedAddress = currentVal;
                        const shortAddress = selectedAddress.split(',').slice(0, 2).join(',');
                        currentLocationText.textContent = shortAddress;
                        // Store in session storage for persistence across pages
                        sessionStorage.setItem('deliveryLocation', selectedAddress);
                        locationModal.style.display = 'none';
                    } else if (!currentVal) {
                        alert("Please select a location on the map or type an address.");
                    }
                });

                // Load saved location if exists
                const savedLocation = sessionStorage.getItem('deliveryLocation');
                if (savedLocation) {
                    currentLocationText.textContent = savedLocation.split(',').slice(0, 2).join(',');
                }

                // Map Search Implementation
                const mapSearchInput = document.getElementById('mapSearchInput');
                const searchResultsDropdown = document.getElementById('searchResultsDropdown');
                let searchTimeout;

                mapSearchInput.addEventListener('input', () => {
                    clearTimeout(searchTimeout);
                    const query = mapSearchInput.value.trim();
                    if (query.length < 3) {
                        searchResultsDropdown.style.display = 'none';
                        return;
                    }

                    searchTimeout = setTimeout(async () => {
                        try {
                            const response = await fetch(`https://nominatim.openstreetmap.org/search?format=json&q=\${encodeURIComponent(query)}&limit=5`);
                            const data = await response.json();
                            displaySearchResults(data);
                        } catch (error) {
                            console.error("Search error:", error);
                        }
                    }, 500);
                });

                function displaySearchResults(results) {
                    searchResultsDropdown.innerHTML = '';
                    if (results.length === 0) {
                        searchResultsDropdown.style.display = 'none';
                        return;
                    }

                    results.forEach(result => {
                        const item = document.createElement('div');
                        item.className = 'search-result-item';
                        item.innerHTML = `📍 <span>\${result.display_name}</span>`;
                        item.addEventListener('click', () => {
                            const lat = parseFloat(result.lat);
                            const lon = parseFloat(result.lon);
                            homeMap.setView([lat, lon], 16);
                            homeMarker.setLatLng([lat, lon]);
                            selectedAddress = result.display_name;
                            modalAddressBox.value = selectedAddress;
                            searchResultsDropdown.style.display = 'none';
                            mapSearchInput.value = '';
                        });
                        searchResultsDropdown.appendChild(item);
                    });
                    searchResultsDropdown.style.display = 'block';
                }

                // Close dropdown when clicking outside
                document.addEventListener('click', (e) => {
                    if (!mapSearchInput.contains(e.target) && !searchResultsDropdown.contains(e.target)) {
                        searchResultsDropdown.style.display = 'none';
                    }
                });
            </script>
        </body>

        </html>