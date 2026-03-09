package com.fooddelivery.controller.common;

import com.fooddelivery.dao.RestaurantDAO;
import com.fooddelivery.model.Restaurant;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/restaurants", "/restaurant"})
public class RestaurantServlet extends HttpServlet {
    private RestaurantDAO restaurantDAO;
    
    @Override
    public void init() { restaurantDAO = new RestaurantDAO(); }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String cuisine = request.getParameter("cuisine");
        String search = request.getParameter("search");
        
        if (idParam != null) {
            // Single restaurant view
            int id = Integer.parseInt(idParam);
            Restaurant restaurant = restaurantDAO.getRestaurantById(id);
            request.setAttribute("restaurant", restaurant);
            request.getRequestDispatcher("/views/common/restaurant-detail.jsp").forward(request, response);
        } else {
            // List view
            List<Restaurant> restaurants;
            if (search != null && !search.isEmpty()) {
                restaurants = restaurantDAO.searchRestaurants(search);
                request.setAttribute("searchQuery", search);
            } else if (cuisine != null && !cuisine.isEmpty()) {
                restaurants = restaurantDAO.getRestaurantsByCuisine(cuisine);
                request.setAttribute("selectedCuisine", cuisine);
            } else {
                restaurants = restaurantDAO.getAllRestaurants();
            }
            request.setAttribute("restaurants", restaurants);
            request.setAttribute("cuisines", restaurantDAO.getAllCuisineTypes());
            request.getRequestDispatcher("/views/common/restaurants.jsp").forward(request, response);
        }
    }
}
