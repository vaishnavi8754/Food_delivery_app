package com.fooddelivery.controller.admin;

import com.fooddelivery.dao.FoodItemDAO;
import com.fooddelivery.dao.RestaurantDAO;
import com.fooddelivery.model.FoodItem;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/admin/food", "/admin/food/add", "/admin/food/edit", "/admin/food/delete"})
public class ManageFoodServlet extends HttpServlet {
    private FoodItemDAO foodDAO;
    private RestaurantDAO restaurantDAO;
    
    @Override
    public void init() {
        foodDAO = new FoodItemDAO();
        restaurantDAO = new RestaurantDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        
        String action = request.getParameter("action");
        if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("food", foodDAO.getFoodById(id));
            request.setAttribute("restaurants", restaurantDAO.getAllRestaurants());
            request.getRequestDispatcher("/views/admin/food-form.jsp").forward(request, response);
        } else if ("add".equals(action)) {
            request.setAttribute("restaurants", restaurantDAO.getAllRestaurants());
            request.getRequestDispatcher("/views/admin/food-form.jsp").forward(request, response);
        } else {
            request.setAttribute("foodItems", foodDAO.getAllFoodItems());
            request.getRequestDispatcher("/views/admin/manage-food.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        
        if (path.endsWith("/delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            foodDAO.deleteFoodItem(id);
        } else {
            FoodItem f = new FoodItem();
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                f.setFoodId(Integer.parseInt(idParam));
            }
            f.setRestaurantId(Integer.parseInt(request.getParameter("restaurantId")));
            f.setName(request.getParameter("name"));
            f.setDescription(request.getParameter("description"));
            f.setPrice(Double.parseDouble(request.getParameter("price")));
            f.setCategory(request.getParameter("category"));
            f.setVegetarian(request.getParameter("isVegetarian") != null);
            f.setAvailable(request.getParameter("isAvailable") != null);
            
            if (f.getFoodId() > 0) {
                foodDAO.updateFoodItem(f);
            } else {
                foodDAO.addFoodItem(f);
            }
        }
        response.sendRedirect(request.getContextPath() + "/admin/food");
    }
}
