package com.fooddelivery.controller.user;

import com.fooddelivery.dao.UserDAO;
import com.fooddelivery.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Login Servlet - Handles user authentication
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    /**
     * Handle GET request - Display login page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        // Forward to login page
        request.getRequestDispatcher("/views/user/login.jsp").forward(request, response);
    }

    /**
     * Handle POST request - Process login form
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form parameters
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        // Validate input
        if (email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {

            request.setAttribute("error", "Please enter both email and password");
            request.getRequestDispatcher("/views/user/login.jsp").forward(request, response);
            return;
        }

        // Validate credentials
        User user = userDAO.validateUser(email.trim().toLowerCase(), password);

        if (user != null) {
            // Create session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("userName", user.getFullName());
            session.setAttribute("userEmail", user.getEmail());
            session.setAttribute("userRole", user.getRole());

            // Set session timeout (30 minutes)
            session.setMaxInactiveInterval(30 * 60);

            // Redirect based on role
            if ("admin".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/home");
            }

        } else {
            // Invalid credentials
            request.setAttribute("error", "Invalid email or password. Please try again.");
            request.setAttribute("email", email); // Preserve email for convenience
            request.getRequestDispatcher("/views/user/login.jsp").forward(request, response);
        }
    }
}
