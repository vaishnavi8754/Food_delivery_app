package com.fooddelivery.controller.admin;

import com.fooddelivery.dao.UserDAO;
import com.fooddelivery.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/admin/users", "/admin/users/delete", "/admin/users/status", "/admin/users/view"})
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
        
        String action = request.getParameter("action");
        if ("view".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            User user = userDAO.getUserById(id);
            request.setAttribute("viewUser", user);
            // This will be handled by a modal trigger in the JSP, but we provide data
        }

        String search = request.getParameter("search");
        String roleFilter = request.getParameter("role");
        String statusFilter = request.getParameter("status");

        List<User> users;
        if (search != null && !search.trim().isEmpty()) {
            users = userDAO.searchUsers(search.trim());
        } else if ((roleFilter != null && !"all".equals(roleFilter)) || (statusFilter != null && !"all".equals(statusFilter))) {
            users = userDAO.getUsersByFilter(roleFilter, statusFilter);
        } else {
            users = userDAO.getAllUsers();
        }

        request.setAttribute("users", users);
        request.setAttribute("currentSearch", search);
        request.setAttribute("currentRole", roleFilter);
        request.setAttribute("currentStatus", statusFilter);
        request.getRequestDispatcher("/views/admin/manage-users.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if (path.endsWith("/delete")) {
            int userId = Integer.parseInt(request.getParameter("id"));
            userDAO.deleteUser(userId);
        } else if (path.endsWith("/status")) {
            int userId = Integer.parseInt(request.getParameter("id"));
            String status = request.getParameter("status");
            userDAO.updateUserStatus(userId, status);
        }
        response.sendRedirect(request.getContextPath() + "/admin/users");
    }
}
