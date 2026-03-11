package com.fooddelivery.controller.common;

import com.fooddelivery.dao.RestaurantDAO;
import com.fooddelivery.model.Restaurant;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RestaurantDAO restaurantDAO;

    @Override
    public void init() {
        restaurantDAO = new RestaurantDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("DEBUG: Entering HomeServlet.doGet()");
        
        List<Restaurant> restaurants = new ArrayList<>();
        List<com.fooddelivery.model.Cuisine> cuisineStats = new ArrayList<>();
        
        try {
            if (restaurantDAO != null) {
                restaurants = restaurantDAO.getAllRestaurants();
                cuisineStats = restaurantDAO.getCuisineStats();
            }
            System.out.println("DEBUG: DAO calls complete. Restaurants: " + (restaurants != null ? restaurants.size() : 0));
        } catch (Exception e) {
            System.err.println("DEBUG: HomeServlet Error: " + e.getMessage());
            e.printStackTrace();
        }

        request.setAttribute("restaurants", restaurants != null ? restaurants : new ArrayList<Restaurant>());
        request.setAttribute("cuisineStats", cuisineStats != null ? cuisineStats : new ArrayList<com.fooddelivery.model.Cuisine>());
        
        System.out.println("DEBUG: Forwarding to /views/common/home.jsp");
        request.getRequestDispatcher("/views/common/home.jsp").forward(request, response);
    }
}
