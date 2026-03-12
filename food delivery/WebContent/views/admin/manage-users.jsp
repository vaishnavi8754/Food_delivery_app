<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Manage Users | Admin</title>
                <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Outfit:wght@600;700&display=swap"
                    rel="stylesheet">
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
            </head>
            
            <body class="admin-body">
                <div class="admin-layout">
                    <aside class="admin-sidebar">
                        <div class="sidebar-header">
                            <span class="logo-icon">🍕</span>
                            <span class="logo-text">FoodExpress</span>
                        </div>
                        <nav class="sidebar-nav">
                            <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-item">
                                <i class="fa-solid fa-chart-pie"></i>
                                <span>Dashboard</span>
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/restaurants" class="nav-item">
                                <i class="fa-solid fa-shop"></i>
                                <span>Restaurants</span>
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/food" class="nav-item">
                                <i class="fa-solid fa-burger"></i>
                                <span>Food Items</span>
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/orders" class="nav-item">
                                <i class="fa-solid fa-box"></i>
                                <span>Orders</span>
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/users" class="nav-item active">
                                <i class="fa-solid fa-users"></i>
                                <span>Users</span>
                            </a>
                        </nav>
                        <div class="sidebar-footer">
                            <a href="${pageContext.request.contextPath}/logout" class="nav-item logout">
                                <i class="fa-solid fa-right-from-bracket"></i>
                                <span>Logout</span>
                            </a>
                        </div>
                    </aside>
                    <main class="admin-main">
                        <header class="admin-header">
                            <h1>Manage Users</h1>
                            <div class="admin-user">${sessionScope.userName}</div>
                        </header>
                        <div class="dashboard-content">
                            <!-- Filter Bar -->
                            <div class="filter-bar">
                                <form action="${pageContext.request.contextPath}/admin/users" method="GET" class="search-box">
                                    <input type="text" name="search" placeholder="Search by name, email or phone..." value="${currentSearch}">
                                    <button type="submit" class="btn-filter">
                                        <i class="fa-solid fa-magnifying-glass"></i>
                                        Search
                                    </button>
                                </form>
                                <div class="filter-group">
                                    <form action="${pageContext.request.contextPath}/admin/users" method="GET" style="display: flex; gap: 0.75rem;">
                                        <select name="role" onchange="this.form.submit()">
                                            <option value="all" ${currentRole == 'all' ? 'selected' : ''}>All Roles</option>
                                            <option value="customer" ${currentRole == 'customer' ? 'selected' : ''}>Customer</option>
                                            <option value="admin" ${currentRole == 'admin' ? 'selected' : ''}>Admin</option>
                                            <option value="delivery_partner" ${currentRole == 'delivery_partner' ? 'selected' : ''}>Delivery Partner</option>
                                        </select>
                                        <select name="status" onchange="this.form.submit()">
                                            <option value="all" ${currentStatus == 'all' ? 'selected' : ''}>All Status</option>
                                            <option value="active" ${currentStatus == 'active' ? 'selected' : ''}>Active Only</option>
                                            <option value="blocked" ${currentStatus == 'blocked' ? 'selected' : ''}>Blocked Only</option>
                                            <option value="suspended" ${currentStatus == 'suspended' ? 'selected' : ''}>Suspended</option>
                                        </select>
                                    </form>
                                </div>
                            </div>
                            
                            <div class="table-container">
                                <table class="admin-table">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>User Info</th>
                                            <th>Role</th>
                                            <th>Status</th>
                                            <th style="text-align: center;">Orders</th>
                                            <th>Spent</th>
                                            <th>Joined</th>
                                            <th style="text-align: right;">Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:choose>
                                            <c:when test="${not empty users}">
                                                <c:forEach var="user" items="${users}">
                                                    <tr>
                                                        <td style="color: var(--text-muted); font-weight: 500;">#${user.userId}</td>
                                                        <td>
                                                            <div class="user-info-cell">
                                                                <span class="user-name">${user.fullName}</span>
                                                                <div class="user-meta">
                                                                    <i class="fa-regular fa-envelope"></i> ${user.email}
                                                                </div>
                                                                <div class="user-meta">
                                                                    <i class="fa-solid fa-phone-flip" style="font-size: 0.7rem;"></i> ${user.phone}
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <td><span class="role-badge role-${user.role}">${user.role.replace('_', ' ')}</span></td>
                                                        <td><span class="status-badge status-${user.status != null ? user.status : 'active'}">${user.status != null ? user.status : 'Active'}</span></td>
                                                        <td style="text-align: center; font-weight: 600;">${user.totalOrders}</td>
                                                        <td style="font-weight: 600; color: var(--text-main);">₹<fmt:formatNumber value="${user.totalSpent}" pattern="#,##0.00" /></td>
                                                        <td style="color: var(--text-muted); font-size: 0.8rem;">
                                                            <fmt:formatDate value="${user.createdAt}" pattern="dd MMM yyyy" />
                                                        </td>
                                                        <td>
                                                            <div class="action-btns" style="justify-content: flex-end;">
                                                                <button class="btn-sm btn-edit" 
                                                                        data-id="${user.userId}"
                                                                        data-name="${user.fullName}"
                                                                        data-email="${user.email}"
                                                                        data-phone="${user.phone}"
                                                                        data-address="${user.address}"
                                                                        data-role="${user.role.replace('_', ' ')}"
                                                                        data-status="${user.status != null ? user.status : 'active'}"
                                                                        data-joined="<fmt:formatDate value="${user.createdAt}" pattern="dd MMM yyyy" />"
                                                                        data-orders="${user.totalOrders}"
                                                                        data-spent="₹<fmt:formatNumber value="${user.totalSpent}" pattern="#,##0.00" />"
                                                                        onclick="showProfile(this)">
                                                                    <i class="fa-solid fa-user-gear"></i>
                                                                    Profile
                                                                </button>
                                                                
                                                                <c:if test="${user.role != 'admin'}">
                                                                    <form action="${pageContext.request.contextPath}/admin/users/status" method="POST" style="display:inline">
                                                                        <input type="hidden" name="id" value="${user.userId}">
                                                                        <c:choose>
                                                                            <c:when test="${user.status == 'blocked'}">
                                                                                <input type="hidden" name="status" value="active">
                                                                                <button type="submit" class="btn-sm btn-status-toggle btn-status-active">
                                                                                    <i class="fa-solid fa-user-check"></i>
                                                                                    Unblock
                                                                                </button>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <input type="hidden" name="status" value="blocked">
                                                                                <button type="submit" class="btn-sm btn-status-toggle btn-status-blocked">
                                                                                    <i class="fa-solid fa-user-slash"></i>
                                                                                    Block
                                                                                </button>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </form>
                                                                    
                                                                    <form action="${pageContext.request.contextPath}/admin/users/delete" method="POST" style="display:inline" onsubmit="return confirm('Absolutely delete this user account?')">
                                                                        <input type="hidden" name="id" value="${user.userId}">
                                                                        <button type="submit" class="btn-sm btn-delete" title="Delete Account">
                                                                            <i class="fa-solid fa-trash-can"></i>
                                                                        </button>
                                                                    </form>
                                                                </c:if>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <tr>
                                                    <td colspan="8">
                                                        <div class="empty-state">
                                                            <i class="fa-solid fa-user-astronaut"></i>
                                                            <h2>No Users Found</h2>
                                                            <p>Try adjusting your search or filters to find what you're looking for.</p>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:otherwise>
                                        </c:choose>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </main>
                </div>

                <!-- User Profile Modal -->
                <div id="profileModal" class="modal-overlay">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h2 id="modalUserName">User Profile</h2>
                            <button class="close-modal" onclick="closeModal()">&times;</button>
                        </div>
                        <div class="profile-grid">
                            <div class="profile-item">
                                <label>User ID</label>
                                <p id="modalUserId">-</p>
                            </div>
                            <div class="profile-item">
                                <label>Status</label>
                                <p id="modalStatus">-</p>
                            </div>
                            <div class="profile-item">
                                <label>Email Address</label>
                                <p id="modalEmail">-</p>
                            </div>
                            <div class="profile-item">
                                <label>Phone Number</label>
                                <p id="modalPhone">-</p>
                            </div>
                            <div class="profile-item profile-full">
                                <label>Default Address</label>
                                <p id="modalAddress">-</p>
                            </div>
                            <div class="profile-item">
                                <label>Role</label>
                                <p id="modalRole">-</p>
                            </div>
                            <div class="profile-item">
                                <label>Joined Date</label>
                                <p id="modalJoined">-</p>
                            </div>
                            <div class="profile-item">
                                <label>Total Orders</label>
                                <p id="modalOrders">0</p>
                            </div>
                            <div class="profile-item">
                                <label>Total Spending</label>
                                <p id="modalSpent">₹0.00</p>
                            </div>
                        </div>
                        <div style="margin-top: 2rem; display: flex; justify-content: flex-end;">
                            <button class="btn-sm" onclick="closeModal()" style="padding: 0.75rem 1.5rem;">Close</button>
                        </div>
                    </div>
                </div>

                <script>
                    function showProfile(btn) {
                        const data = btn.dataset;
                        document.getElementById('modalUserName').innerText = data.name;
                        document.getElementById('modalUserId').innerText = '#' + data.id;
                        document.getElementById('modalStatus').innerText = data.status;
                        document.getElementById('modalEmail').innerText = data.email;
                        document.getElementById('modalPhone').innerText = data.phone;
                        document.getElementById('modalAddress').innerText = data.address || 'Address not provided';
                        document.getElementById('modalRole').innerText = data.role.replace('_', ' ');
                        document.getElementById('modalJoined').innerText = data.joined;
                        document.getElementById('modalOrders').innerText = data.orders;
                        document.getElementById('modalSpent').innerText = data.spent;
                        
                        document.getElementById('profileModal').style.display = 'flex';
                    }

                    function closeModal() {
                        document.getElementById('profileModal').style.display = 'none';
                    }

                    window.onclick = function(event) {
                        const modal = document.getElementById('profileModal');
                        if (event.target == modal) {
                            closeModal();
                        }
                    }
                </script>
            </body>

            </html>