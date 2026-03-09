package com.fooddelivery.controller.admin;

import com.fooddelivery.dao.OrderDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/admin/orders", "/admin/orders/update"})
public class ManageOrderServlet extends HttpServlet {
    private OrderDAO orderDAO;
    
    @Override
    public void init() { orderDAO = new OrderDAO(); }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        
        String orderId = request.getParameter("id");
        if (orderId != null) {
            request.setAttribute("order", orderDAO.getOrderById(Integer.parseInt(orderId)));
            request.getRequestDispatcher("/views/admin/order-detail.jsp").forward(request, response);
        } else {
            request.setAttribute("orders", orderDAO.getAllOrders());
            request.getRequestDispatcher("/views/admin/manage-orders.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String status = request.getParameter("status");
        orderDAO.updateOrderStatus(orderId, status);
        response.sendRedirect(request.getContextPath() + "/admin/orders?id=" + orderId);
    }
}
