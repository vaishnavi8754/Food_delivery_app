package com.fooddelivery.controller.user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Logout Servlet - Handles user logout
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Handle GET request - Process logout
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get current session
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // Invalidate the session
            session.invalidate();
        }
        
        // Redirect to login page with message
        response.sendRedirect(request.getContextPath() + "/login?logout=success");
    }
    
    /**
     * Handle POST request - Same as GET
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
