package com.tempest.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/check-session")
public class SessionController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        StringBuilder json = new StringBuilder();
        json.append("{");

        if (session != null) {
            Integer userId = (Integer) session.getAttribute("userId");
            String role = (String) session.getAttribute("role");
            
            json.append("\"exists\": true,");
            json.append("\"userId\": ").append(userId != null ? userId : "null").append(",");
            json.append("\"role\": \"").append(role != null ? role : "null").append("\",");
            json.append("\"sessionId\": \"").append(session.getId()).append("\"");
        } else {
            json.append("\"exists\": false");
        }

        json.append("}");
        response.getWriter().write(json.toString());
    }
} 