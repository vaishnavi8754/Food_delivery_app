package com.fooddelivery.controller.common;

import com.fooddelivery.dao.FoodItemDAO;
import com.fooddelivery.model.CartItem;
import com.fooddelivery.model.FoodItem;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns = {"/cart", "/cart/add", "/cart/update", "/cart/remove", "/cart/clear"})
public class CartServlet extends HttpServlet {
    private FoodItemDAO foodDAO;
    
    @Override
    public void init() { foodDAO = new FoodItemDAO(); }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<CartItem> cart = getCart(session);
        double cartTotal = calculateTotal(cart);
        
        request.setAttribute("cartItems", cart);
        request.setAttribute("cartTotal", cartTotal);
        request.setAttribute("itemCount", cart.size());
        request.getRequestDispatcher("/views/common/cart.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        HttpSession session = request.getSession();
        
        switch (path) {
            case "/cart/add":
                addToCart(request, session);
                break;
            case "/cart/update":
                updateCart(request, session);
                break;
            case "/cart/remove":
                removeFromCart(request, session);
                break;
            case "/cart/clear":
                session.removeAttribute("cart");
                break;
        }
        
        String redirect = request.getParameter("redirect");
        if (redirect != null) {
            response.sendRedirect(request.getContextPath() + redirect);
        } else {
            response.sendRedirect(request.getContextPath() + "/cart");
        }
    }
    
    private void addToCart(HttpServletRequest request, HttpSession session) {
        int foodId = Integer.parseInt(request.getParameter("foodId"));
        int quantity = Integer.parseInt(request.getParameter("quantity") != null ? request.getParameter("quantity") : "1");
        
        FoodItem food = foodDAO.getFoodById(foodId);
        if (food == null) return;
        
        List<CartItem> cart = getCart(session);
        
        // Check if item already in cart
        Optional<CartItem> existing = cart.stream().filter(c -> c.getFoodId() == foodId).findFirst();
        
        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + quantity);
        } else {
            CartItem item = new CartItem(food.getFoodId(), food.getRestaurantId(), food.getName(), 
                food.getRestaurantName(), food.getPrice(), quantity);
            item.setVegetarian(food.isVegetarian());
            cart.add(item);
        }
        
        session.setAttribute("cart", cart);
    }
    
    private void updateCart(HttpServletRequest request, HttpSession session) {
        int foodId = Integer.parseInt(request.getParameter("foodId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        
        List<CartItem> cart = getCart(session);
        
        if (quantity <= 0) {
            cart.removeIf(c -> c.getFoodId() == foodId);
        } else {
            cart.stream().filter(c -> c.getFoodId() == foodId).findFirst()
                .ifPresent(c -> c.setQuantity(quantity));
        }
        
        session.setAttribute("cart", cart);
    }
    
    private void removeFromCart(HttpServletRequest request, HttpSession session) {
        int foodId = Integer.parseInt(request.getParameter("foodId"));
        List<CartItem> cart = getCart(session);
        cart.removeIf(c -> c.getFoodId() == foodId);
        session.setAttribute("cart", cart);
    }
    
    @SuppressWarnings("unchecked")
    private List<CartItem> getCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
    
    private double calculateTotal(List<CartItem> cart) {
        return cart.stream().mapToDouble(CartItem::getSubtotal).sum();
    }
}
