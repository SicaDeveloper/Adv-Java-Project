package com.tempest.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import com.tempest.model.UserModel;
import com.tempest.service.UserService;

@WebServlet("/profile/*")
public class ProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    public void init() {
        userService = new UserService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Get user details from database
        UserModel user = userService.getUserByEmail(email);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            // Show profile page
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/pages/profile/profile.jsp").forward(request, response);
        } else if (pathInfo.equals("/edit")) {
            // Show edit profile page
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/pages/profile/edit-profile.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.equals("/update")) {
            // Get user from database
            UserModel user = userService.getUserByEmail(email);
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Update user details
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");

            user.setFirst_name(firstName);
            user.setLast_name(lastName);
            user.setPhone(phone);
            user.setAddress(address);

            boolean updated = userService.updateUser(user);
            if (updated) {
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/profile");
            } else {
                request.setAttribute("error", "Failed to update profile");
                request.setAttribute("user", user);
                request.getRequestDispatcher("/WEB-INF/pages/profile/edit-profile.jsp").forward(request, response);
            }
        }
    }
} 