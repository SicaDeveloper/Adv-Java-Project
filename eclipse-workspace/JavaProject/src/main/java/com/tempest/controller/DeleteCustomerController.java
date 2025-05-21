package com.tempest.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.tempest.service.UserService;

@WebServlet("/admin/customer/delete")
public class DeleteCustomerController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String userId = request.getParameter("id");
            if (userId != null && !userId.isEmpty()) {
                boolean success = userService.deleteUser(Integer.parseInt(userId));
                if (success) {
                    response.sendRedirect(request.getContextPath() + "/admin/customer");
                } else {
                    throw new Exception("Failed to delete customer");
                }
            } else {
                throw new IllegalArgumentException("Customer ID is required");
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error deleting customer: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/customer");
        }
    }
} 