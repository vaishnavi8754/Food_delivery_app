package com.fooddelivery.controller.common;

import com.fooddelivery.dao.FoodItemDAO;
import com.fooddelivery.dao.RestaurantDAO;
import com.fooddelivery.model.FoodItem;
import com.fooddelivery.model.Restaurant;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {
    private FoodItemDAO foodDAO;
    private RestaurantDAO restaurantDAO;
    
    @Override
    public void init() {
        foodDAO = new FoodItemDAO();
        restaurantDAO = new RestaurantDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String restaurantIdParam = request.getParameter("restaurantId");
        
        if (restaurantIdParam != null) {
            int restaurantId = Integer.parseInt(restaurantIdParam);
            Restaurant restaurant = restaurantDAO.getRestaurantById(restaurantId);
            List<FoodItem> menuItems = foodDAO.getFoodByRestaurant(restaurantId);
            
            request.setAttribute("restaurant", restaurant);
            request.setAttribute("menuItems", menuItems);
            request.getRequestDispatcher("/views/common/menu.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/restaurants");
        }
    }
}
