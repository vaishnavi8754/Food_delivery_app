package com.fooddelivery.controller.user;

import com.fooddelivery.dao.UserDAO;
import com.fooddelivery.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ForgotPasswordServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/user/forgot-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("ForgotPasswordServlet: Action=" + action);
        
        if ("verify".equals(action)) {
            String email = request.getParameter("email");
            if (email != null) email = email.trim();
            
            User user = userDAO.getUserByEmail(email);
            
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("resetEmail", email);
                request.setAttribute("step", "reset");
                request.getRequestDispatcher("/views/user/forgot-password.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Email address not found!");
                request.getRequestDispatcher("/views/user/forgot-password.jsp").forward(request, response);
            }
        } else if ("reset".equals(action)) {
            HttpSession session = request.getSession();
            String email = (String) session.getAttribute("resetEmail");
            String newPassword = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            
            if (email == null) {
                response.sendRedirect(request.getContextPath() + "/forgot-password");
                return;
            }
            
            if (newPassword != null && newPassword.equals(confirmPassword)) {
                User user = userDAO.getUserByEmail(email);
                if (user != null && userDAO.updatePassword(user.getUserId(), newPassword)) {
                    session.removeAttribute("resetEmail");
                    session.setAttribute("successMessage", "Password reset successfully! Please login with your new password.");
                    response.sendRedirect(request.getContextPath() + "/login");
                } else {
                    request.setAttribute("step", "reset");
                    request.setAttribute("error", "Failed to update password. Please try again.");
                    request.getRequestDispatcher("/views/user/forgot-password.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("step", "reset");
                request.setAttribute("error", "Passwords do not match!");
                request.getRequestDispatcher("/views/user/forgot-password.jsp").forward(request, response);
            }
        }
    }
}
