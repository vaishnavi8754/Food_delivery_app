package com.fooddelivery.controller.common;

import com.fooddelivery.dao.RestaurantDAO;
import com.fooddelivery.model.Restaurant;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private RestaurantDAO restaurantDAO;

    @Override
    public void init() {
        restaurantDAO = new RestaurantDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Restaurant> restaurants = restaurantDAO.getAllRestaurants();
        List<com.fooddelivery.model.Cuisine> cuisineStats = restaurantDAO.getCuisineStats();

        request.setAttribute("restaurants", restaurants);
        request.setAttribute("cuisineStats", cuisineStats);
        request.getRequestDispatcher("/views/common/home.jsp").forward(request, response);
    }
}
