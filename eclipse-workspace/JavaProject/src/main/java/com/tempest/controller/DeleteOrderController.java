package com.tempest.controller;

import java.io.IOException;

import com.tempest.service.OrderService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/order/delete")
public class DeleteOrderController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderService orderService;

    public void init() {
        orderService = new OrderService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String orderIdStr = request.getParameter("orderId");
            if (orderIdStr == null || orderIdStr.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/admin/order");
                return;
            }

            int orderId = Integer.parseInt(orderIdStr);
            if (orderService.deleteOrder(orderId)) {
                response.sendRedirect(request.getContextPath() + "/admin/order");
            } else {
                request.setAttribute("error", "Failed to delete order");
                request.getRequestDispatcher("/admin/order").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while deleting the order");
            request.getRequestDispatcher("/admin/order").forward(request, response);
        }
    }
} 