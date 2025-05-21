package com.tempest.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.tempest.model.UserModel;
import com.tempest.service.UserService;
import com.tempest.util.ValidationUtil;

@WebServlet("/admin/customer/edit")
public class EditCustomerController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String userId = request.getParameter("id");
            if (userId != null && !userId.isEmpty()) {
                UserModel user = userService.getUserById(Integer.parseInt(userId));
                if (user != null) {
                    request.setAttribute("user", user);
                    request.setAttribute("page", "/WEB-INF/pages/customer/edit-customer.jsp");
                    request.getRequestDispatcher("/WEB-INF/pages/admin/admin-dashboard.jsp").forward(request, response);
                    return;
                }
            }
            response.sendRedirect(request.getContextPath() + "/admin/customer");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/admin/customer?error=" + 
                java.net.URLEncoder.encode("Error loading customer: " + e.getMessage(), "UTF-8"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String error = null;
        try {
            String userId = request.getParameter("id");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String roleStr = request.getParameter("role");

            // Validate input
            if (!ValidationUtil.isValidEmail(email)) {
                error = "Invalid email format";
                throw new IllegalArgumentException(error);
            }
            if (!ValidationUtil.isValidPhoneNumber(phone)) {
                error = "Invalid phone number format";
                throw new IllegalArgumentException(error);
            }

            // Create user with the role from form submission
            UserModel user = new UserModel(
                firstName, 
                lastName, 
                email, 
                phone, 
                address, 
                UserModel.Roles.valueOf(roleStr.toLowerCase())
            );
            user.setUser_id(Integer.parseInt(userId));

            boolean success = userService.updateUser(user);
            if (!success) {
                error = "Failed to update customer";
                throw new Exception(error);
            }
            
            response.sendRedirect(request.getContextPath() + "/admin/customer");
            
        } catch (Exception e) {
            if (error == null) {
                error = "Error updating customer: " + e.getMessage();
            }
            request.setAttribute("error", error);
            request.setAttribute("user", new UserModel(
                request.getParameter("firstName"),
                request.getParameter("lastName"),
                request.getParameter("email"),
                request.getParameter("phone"),
                request.getParameter("address"),
                UserModel.Roles.customer // Default to customer role if error occurs
            ));
            request.setAttribute("page", "/WEB-INF/pages/customer/edit-customer.jsp");
            request.getRequestDispatcher("/WEB-INF/pages/admin/admin-dashboard.jsp")
                   .forward(request, response);
        }
    }
} 