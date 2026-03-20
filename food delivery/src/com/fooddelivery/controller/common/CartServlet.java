package com.fooddelivery.controller.common;

import com.fooddelivery.dao.FoodItemDAO;
import com.fooddelivery.dao.RestaurantDAO;
import com.fooddelivery.model.CartItem;
import com.fooddelivery.model.FoodItem;
import com.fooddelivery.model.Restaurant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(urlPatterns = { "/cart", "/cart/add", "/cart/update", "/cart/remove", "/cart/clear" })
public class CartServlet extends HttpServlet {
    private static final double MAX_NEARBY_KM = 3.0;

    private FoodItemDAO foodDAO;
    private RestaurantDAO restaurantDAO;

    @Override
    public void init() {
        foodDAO = new FoodItemDAO();
        restaurantDAO = new RestaurantDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login?auth=required");
            return;
        }
        List<CartItem> cart = getCart(session);
        double cartTotal = calculateTotal(cart);

        request.setAttribute("cartItems", cart);
        request.setAttribute("cartTotal", cartTotal);
        request.setAttribute("itemCount", cart.size());

        // Pass unique restaurant count for delivery fee calculation
        Set<Integer> restIds = new HashSet<>();
        for (CartItem ci : cart) restIds.add(ci.getRestaurantId());
        request.setAttribute("restaurantCount", restIds.size());

        request.getRequestDispatcher("/views/common/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            // For AJAX calls return JSON
            if (isAjax(request)) {
                sendJson(response, "{\"status\":\"auth_required\"}");
            } else {
                response.sendRedirect(request.getContextPath() + "/login?auth=required");
            }
            return;
        }
        String path = request.getServletPath();

        switch (path) {
            case "/cart/add":
                handleAddToCart(request, response, session);
                return; // handleAddToCart deals with redirect/json itself
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

    // -----------------------------------------------------------------------
    // ADD TO CART — with multi-restaurant proximity logic
    // -----------------------------------------------------------------------
    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response,
                                  HttpSession session) throws IOException {
        int foodId;
        int quantity;
        boolean force = "true".equals(request.getParameter("force"));

        try {
            foodId = Integer.parseInt(request.getParameter("foodId"));
            quantity = Integer.parseInt(
                    request.getParameter("quantity") != null ? request.getParameter("quantity") : "1");
        } catch (NumberFormatException e) {
            sendJsonOrRedirect(request, response, "{\"status\":\"error\",\"message\":\"Invalid item.\"}", "/cart");
            return;
        }

        FoodItem food = foodDAO.getFoodById(foodId);
        if (food == null) {
            sendJsonOrRedirect(request, response, "{\"status\":\"error\",\"message\":\"Item not found.\"}", "/cart");
            return;
        }

        List<CartItem> cart = getCart(session);

        // ---- Proximity check ----
        if (!cart.isEmpty() && !force) {
            // Collect distinct restaurant IDs already in cart
            Set<Integer> cartRestaurantIds = new HashSet<>();
            for (CartItem ci : cart) cartRestaurantIds.add(ci.getRestaurantId());

            boolean newRestaurant = !cartRestaurantIds.contains(food.getRestaurantId());

            if (newRestaurant) {
                // Check every existing restaurant is within MAX_NEARBY_KM of new restaurant
                boolean allNearby = true;
                String conflictRestaurantName = null;

                for (int existingId : cartRestaurantIds) {
                    if (!restaurantDAO.areRestaurantsNearby(existingId, food.getRestaurantId(), MAX_NEARBY_KM)) {
                        allNearby = false;
                        Restaurant conflictR = restaurantDAO.getRestaurantById(existingId);
                        conflictRestaurantName = conflictR != null ? conflictR.getName() : "another restaurant";
                        break;
                    }
                }

                if (!allNearby) {
                    // Restaurants are too far — prompt user
                    Restaurant newR = restaurantDAO.getRestaurantById(food.getRestaurantId());
                    String newRName = newR != null ? newR.getName() : "this restaurant";
                    String json = "{\"status\":\"too_far\","
                            + "\"newRestaurant\":\"" + escapeJson(newRName) + "\","
                            + "\"conflictRestaurant\":\"" + escapeJson(conflictRestaurantName) + "\","
                            + "\"foodId\":" + foodId + ","
                            + "\"quantity\":" + quantity + "}";
                    sendJsonOrRedirect(request, response, json, "/cart");
                    return;
                }
            }
        }

        // If force=true, clear existing cart first
        if (force) {
            cart.clear();
        }

        // Check if item already in cart
        Optional<CartItem> existing = cart.stream().filter(c -> c.getFoodId() == foodId).findFirst();
        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + quantity);
        } else {
            CartItem item = new CartItem(food.getFoodId(), food.getRestaurantId(), food.getName(),
                    food.getRestaurantName(), food.getPrice(), quantity);
            item.setImageUrl(food.getImageUrl());
            item.setVegetarian(food.isVegetarian());
            cart.add(item);
        }
        session.setAttribute("cart", cart);
        // Update restaurant count in session for UI badges
        Set<Integer> updatedRestIds = new HashSet<>();
        for (CartItem ci : cart) updatedRestIds.add(ci.getRestaurantId());
        session.setAttribute("cartRestaurantCount", updatedRestIds.size());

        String json = "{\"status\":\"ok\",\"cartSize\":" + cart.size()
                + ",\"restaurantCount\":" + updatedRestIds.size() + "}";
        String redirect = request.getParameter("redirect");
        if (isAjax(request)) {
            sendJson(response, json);
        } else if (redirect != null) {
            response.sendRedirect(request.getContextPath() + redirect);
        } else {
            response.sendRedirect(request.getContextPath() + "/cart");
        }
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

    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"))
                || "application/json".equals(request.getHeader("Accept"))
                || "true".equals(request.getParameter("ajax"));
    }

    private void sendJson(HttpServletResponse response, String json) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    private void sendJsonOrRedirect(HttpServletRequest request, HttpServletResponse response,
                                     String json, String fallbackRedirect) throws IOException {
        if (isAjax(request)) {
            sendJson(response, json);
        } else {
            response.sendRedirect(request.getContextPath() + fallbackRedirect);
        }
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }
}
