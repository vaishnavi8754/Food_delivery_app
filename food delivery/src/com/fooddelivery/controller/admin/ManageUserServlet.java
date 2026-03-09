package com.fooddelivery.controller.admin;

import com.fooddelivery.dao.UserDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/admin/users", "/admin/users/delete"})
public class ManageUserServlet extends HttpServlet {
    private UserDAO userDAO;
    
    @Override
    public void init() { userDAO = new UserDAO(); }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }
        
        request.setAttribute("users", userDAO.getAllUsers());
        request.getRequestDispatcher("/views/admin/manage-users.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getServletPath().endsWith("/delete")) {
            int userId = Integer.parseInt(request.getParameter("id"));
            userDAO.deleteUser(userId);
        }
        response.sendRedirect(request.getContextPath() + "/admin/users");
    }
}
