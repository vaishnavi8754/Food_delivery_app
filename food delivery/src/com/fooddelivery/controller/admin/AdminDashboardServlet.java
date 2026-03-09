package com.fooddelivery.controller.admin;

import com.fooddelivery.dao.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private UserDAO userDAO;
    private RestaurantDAO restaurantDAO;
    private OrderDAO orderDAO;
    
    @Override
    public void init() {
        userDAO = new UserDAO();
        restaurantDAO = new RestaurantDAO();
        orderDAO = new OrderDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        
        request.setAttribute("totalUsers", userDAO.getAllUsers().size());
        request.setAttribute("totalRestaurants", restaurantDAO.getAllRestaurants().size());
        request.setAttribute("totalOrders", orderDAO.getOrderCount());
        request.setAttribute("totalRevenue", orderDAO.getTotalRevenue());
        request.setAttribute("recentOrders", orderDAO.getAllOrders());
        
        request.getRequestDispatcher("/views/admin/dashboard.jsp").forward(request, response);
    }
}
