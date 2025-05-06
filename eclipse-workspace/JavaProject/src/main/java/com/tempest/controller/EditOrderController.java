package com.tempest.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.tempest.model.OrderModel;
import com.tempest.service.OrderService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/order/edit")
public class EditOrderController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderService orderService;

    public void init() {
        orderService = new OrderService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String orderIdStr = request.getParameter("orderId");
            if (orderIdStr == null || orderIdStr.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/admin/order");
                return;
            }

            int orderId = Integer.parseInt(orderIdStr);
            OrderModel order = orderService.getOrderById(orderId);

            if (order == null) {
                response.sendRedirect(request.getContextPath() + "/admin/order");
                return;
            }

            request.setAttribute("order", order);
            request.setAttribute("page", "/WEB-INF/pages/orderPage/edit-order.jsp");
            request.getRequestDispatcher("/WEB-INF/pages/admin/admin-dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/order");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            int userId = Integer.parseInt(request.getParameter("userId"));
            double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));
            String status = request.getParameter("status");

            OrderModel order = new OrderModel();
            order.setId(orderId);
            order.setUserId(userId);
            order.setTotalAmount(totalAmount);
            order.setStatus(status);
            order.setOrderDate(new Date(System.currentTimeMillis()));

            // Get existing product IDs for this order
            List<Integer> productIds = orderService.getOrderProducts(orderId);
            if (productIds == null) {
                productIds = new ArrayList<>();
            }

            if (orderService.updateOrder(order, productIds)) {
                response.sendRedirect(request.getContextPath() + "/admin/order");
            } else {
                request.setAttribute("error", "Failed to update order");
                doGet(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while updating the order");
            doGet(request, response);
        }
    }
} 