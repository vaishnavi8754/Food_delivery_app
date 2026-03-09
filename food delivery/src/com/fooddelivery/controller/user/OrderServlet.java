package com.fooddelivery.controller.user;

import com.fooddelivery.dao.OrderDAO;
import com.fooddelivery.model.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/orders", "/checkout", "/order/place"})
public class OrderServlet extends HttpServlet {
    private OrderDAO orderDAO;
    
    @Override
    public void init() { orderDAO = new OrderDAO(); }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String path = request.getServletPath();
        User user = (User) session.getAttribute("user");
        
        if ("/checkout".equals(path)) {
            @SuppressWarnings("unchecked")
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            if (cart == null || cart.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }
            double total = cart.stream().mapToDouble(CartItem::getSubtotal).sum();
            request.setAttribute("cartItems", cart);
            request.setAttribute("cartTotal", total);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/views/user/checkout.jsp").forward(request, response);
        } else {
            String orderId = request.getParameter("id");
            if (orderId != null) {
                Order order = orderDAO.getOrderById(Integer.parseInt(orderId));
                request.setAttribute("order", order);
                request.getRequestDispatcher("/views/user/order-detail.jsp").forward(request, response);
            } else {
                List<Order> orders = orderDAO.getOrdersByUser(user.getUserId());
                request.setAttribute("orders", orders);
                request.getRequestDispatcher("/views/user/order-history.jsp").forward(request, response);
            }
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }
        
        // Get form data
        String address = request.getParameter("address");
        String paymentMethod = request.getParameter("paymentMethod");
        String instructions = request.getParameter("instructions");
        
        // Create order
        Order order = new Order();
        order.setUserId(user.getUserId());
        order.setRestaurantId(cart.get(0).getRestaurantId());
        order.setTotalAmount(cart.stream().mapToDouble(CartItem::getSubtotal).sum());
        order.setDeliveryAddress(address);
        order.setPaymentMethod(paymentMethod);
        order.setSpecialInstructions(instructions);
        
        int orderId = orderDAO.createOrder(order, cart);
        
        if (orderId > 0) {
            session.removeAttribute("cart");
            response.sendRedirect(request.getContextPath() + "/orders?id=" + orderId + "&success=true");
        } else {
            request.setAttribute("error", "Failed to place order. Please try again.");
            doGet(request, response);
        }
    }
}
