package com.tempest.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.tempest.model.UserModel;
import com.tempest.service.UserService;

@WebServlet("/admin/customer")
public class AdminCustomerController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    public AdminCustomerController() {
        super();
    }

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<UserModel> customers = userService.getAllUserDetails();
            request.setAttribute("customers", customers);
            request.setAttribute("page", "/WEB-INF/pages/admin/admin-customer-management.jsp");
            request.getRequestDispatcher("/WEB-INF/pages/admin/admin-dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/dashboard?error=" + 
                java.net.URLEncoder.encode("Error loading customers: " + e.getMessage(), "UTF-8"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
} 