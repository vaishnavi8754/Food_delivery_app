package com.fooddelivery.controller.admin;

import com.fooddelivery.dao.RestaurantDAO;
import com.fooddelivery.model.Restaurant;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/admin/restaurants", "/admin/restaurants/add", "/admin/restaurants/edit", "/admin/restaurants/delete"})
public class ManageRestaurantServlet extends HttpServlet {
    private RestaurantDAO restaurantDAO;
    
    @Override
    public void init() { restaurantDAO = new RestaurantDAO(); }
    
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
            request.setAttribute("restaurant", restaurantDAO.getRestaurantById(id));
            request.getRequestDispatcher("/views/admin/restaurant-form.jsp").forward(request, response);
        } else if ("add".equals(action)) {
            request.getRequestDispatcher("/views/admin/restaurant-form.jsp").forward(request, response);
        } else {
            request.setAttribute("restaurants", restaurantDAO.getAllRestaurants());
            request.getRequestDispatcher("/views/admin/manage-restaurants.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        
        if (path.endsWith("/delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            restaurantDAO.deleteRestaurant(id);
        } else {
            Restaurant r = new Restaurant();
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                r.setRestaurantId(Integer.parseInt(idParam));
            }
            r.setName(request.getParameter("name"));
            r.setDescription(request.getParameter("description"));
            r.setAddress(request.getParameter("address"));
            r.setPhone(request.getParameter("phone"));
            r.setCuisineType(request.getParameter("cuisineType"));
            r.setActive(request.getParameter("isActive") != null);
            
            if (r.getRestaurantId() > 0) {
                restaurantDAO.updateRestaurant(r);
            } else {
                restaurantDAO.addRestaurant(r);
            }
        }
        response.sendRedirect(request.getContextPath() + "/admin/restaurants");
    }
}
