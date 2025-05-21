package com.tempest.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jakarta.servlet.ServletException;

public class ErrorHandler {
    public static void handleError(HttpServletRequest request, HttpServletResponse response, 
                                 String errorMessage, String redirectPath) throws ServletException, IOException {
        request.setAttribute("error", errorMessage);
        request.getRequestDispatcher(redirectPath).forward(request, response);
    }

    public static void handleDatabaseError(HttpServletRequest request, HttpServletResponse response, 
                                         Exception e, String redirectPath) throws ServletException, IOException {
        String errorMessage = "Database error occurred. Please try again later.";
        System.err.println("Database Error: " + e.getMessage());
        handleError(request, response, errorMessage, redirectPath);
    }

    public static void handleValidationError(HttpServletRequest request, HttpServletResponse response, 
                                           String errorMessage, String redirectPath) throws ServletException, IOException {
        request.setAttribute("validationError", errorMessage);
        request.getRequestDispatcher(redirectPath).forward(request, response);
    }

    public static void handleAuthenticationError(HttpServletRequest request, HttpServletResponse response, 
                                               String errorMessage) throws ServletException, IOException {
        request.setAttribute("authError", errorMessage);
        request.getRequestDispatcher("/WEB-INF/pages/loginAndRegistrationPage/login.jsp").forward(request, response);
    }

    public static void handleAuthorizationError(HttpServletRequest request, HttpServletResponse response, 
                                              String errorMessage) throws ServletException, IOException {
        request.setAttribute("authError", errorMessage);
        request.getRequestDispatcher("/WEB-INF/pages/error/unauthorized.jsp").forward(request, response);
    }

    public static void handleNotFoundError(HttpServletRequest request, HttpServletResponse response, 
                                         String errorMessage) throws ServletException, IOException {
        request.setAttribute("error", errorMessage);
        request.getRequestDispatcher("/WEB-INF/pages/error/not-found.jsp").forward(request, response);
    }
} 