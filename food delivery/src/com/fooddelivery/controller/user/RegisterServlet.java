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
 * Register Servlet - Handles user registration
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }
    
    /**
     * Handle GET request - Display registration page
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
        
        // Forward to registration page
        request.getRequestDispatcher("/views/user/register.jsp").forward(request, response);
    }
    
    /**
     * Handle POST request - Process registration form
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get form parameters
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String address = request.getParameter("address");
        String terms = request.getParameter("terms");
        
        // Store form data for repopulation on error
        request.setAttribute("fullName", fullName);
        request.setAttribute("email", email);
        request.setAttribute("phone", phone);
        request.setAttribute("address", address);
        
        // Validate required fields
        if (fullName == null || fullName.trim().isEmpty()) {
            request.setAttribute("error", "Full name is required");
            request.getRequestDispatcher("/views/user/register.jsp").forward(request, response);
            return;
        }
        
        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("error", "Email is required");
            request.getRequestDispatcher("/views/user/register.jsp").forward(request, response);
            return;
        }
        
        // Validate email format
        if (!isValidEmail(email.trim())) {
            request.setAttribute("error", "Please enter a valid email address");
            request.getRequestDispatcher("/views/user/register.jsp").forward(request, response);
            return;
        }
        
        if (phone == null || phone.trim().isEmpty()) {
            request.setAttribute("error", "Phone number is required");
            request.getRequestDispatcher("/views/user/register.jsp").forward(request, response);
            return;
        }
        
        // Validate phone format (10 digits)
        if (!isValidPhone(phone.trim())) {
            request.setAttribute("error", "Please enter a valid 10-digit phone number");
            request.getRequestDispatcher("/views/user/register.jsp").forward(request, response);
            return;
        }
        
        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Password is required");
            request.getRequestDispatcher("/views/user/register.jsp").forward(request, response);
            return;
        }
        
        // Validate password strength
        if (password.length() < 6) {
            request.setAttribute("error", "Password must be at least 6 characters long");
            request.getRequestDispatcher("/views/user/register.jsp").forward(request, response);
            return;
        }
        
        // Check password match
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match");
            request.getRequestDispatcher("/views/user/register.jsp").forward(request, response);
            return;
        }
        
        // Check terms acceptance
        if (terms == null) {
            request.setAttribute("error", "Please accept the terms and conditions");
            request.getRequestDispatcher("/views/user/register.jsp").forward(request, response);
            return;
        }
        
        // Check if email already exists
        if (userDAO.emailExists(email.trim())) {
            request.setAttribute("error", "This email is already registered. Please login or use a different email.");
            request.getRequestDispatcher("/views/user/register.jsp").forward(request, response);
            return;
        }
        
        // Create user object
        User user = new User();
        user.setFullName(fullName.trim());
        user.setEmail(email.trim().toLowerCase());
        user.setPhone(phone.trim());
        user.setPassword(password); // In production, hash the password!
        user.setAddress(address != null ? address.trim() : "");
        user.setRole("user");
        
        // Register user
        boolean registered = userDAO.registerUser(user);
        
        if (registered) {
            // Registration successful - redirect to login with success message
            request.getSession().setAttribute("successMessage", "Registration successful! Please login with your credentials.");
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            request.setAttribute("error", "Registration failed. Please try again later.");
            request.getRequestDispatcher("/views/user/register.jsp").forward(request, response);
        }
    }
    
    /**
     * Validate email format
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    
    /**
     * Validate phone format (10 digits)
     */
    private boolean isValidPhone(String phone) {
        String phoneRegex = "^[0-9]{10}$";
        return phone.matches(phoneRegex);
    }
}
