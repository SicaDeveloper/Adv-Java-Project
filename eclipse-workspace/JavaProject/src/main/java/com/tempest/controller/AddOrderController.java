package com.tempest.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.tempest.model.OrderModel;
import com.tempest.model.ProductModel;
import com.tempest.model.UserModel;
import com.tempest.service.OrderService;
import com.tempest.service.ProductService;
import com.tempest.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/order/add")
public class AddOrderController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderService orderService;
    private ProductService productService;
    private UserService userService;

    public void init() {
        orderService = new OrderService();
        productService = new ProductService();
        userService = new UserService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Get user from session
        UserModel currentUser = (UserModel) session.getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Initialize selected products if not exists
        if (session.getAttribute("selectedProducts") == null) {
            session.setAttribute("selectedProducts", new ArrayList<ProductModel>());
        }
        
        // Get all available products
        List<ProductModel> availableProducts = productService.getAllProductsInfo();
        request.setAttribute("availableProducts", availableProducts);
        
        // Get all customers
        List<UserModel> customers = userService.getAllUserDetails();
        request.setAttribute("customers", customers);
        
        // Calculate totals
        List<ProductModel> selectedProducts = (List<ProductModel>) session.getAttribute("selectedProducts");
        double subtotal = calculateSubtotal(selectedProducts);
        double shipping = 8.99;
        double total = subtotal + shipping;
        
        request.setAttribute("selectedProducts", selectedProducts);
        request.setAttribute("subtotal", subtotal);
        request.setAttribute("shipping", shipping);
        request.setAttribute("total", total);
        
        // Set the page attribute to add-order.jsp and forward to admin-dashboard.jsp
        request.setAttribute("page", "/WEB-INF/pages/orderPage/add-order.jsp");
        request.getRequestDispatcher("/WEB-INF/pages/admin/admin-dashboard.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Get user from session
        UserModel currentUser = (UserModel) session.getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String action = request.getParameter("action");
        
        if ("addProduct".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("selectedProductId"));
            
            if (!isProductAlreadySelected(session, productId)) {
                ProductModel product = productService.getProductById(productId);
                if (product != null) {
                    List<ProductModel> selectedProducts = (List<ProductModel>) session.getAttribute("selectedProducts");
                    selectedProducts.add(product);
                    session.setAttribute("selectedProducts", selectedProducts);
                }
            }
            
            response.sendRedirect(request.getRequestURI());
            return;
        }
        
        if ("removeProduct".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            List<ProductModel> selectedProducts = (List<ProductModel>) session.getAttribute("selectedProducts");
            selectedProducts.removeIf(p -> p.getId() == productId);
            session.setAttribute("selectedProducts", selectedProducts);
            
            response.sendRedirect(request.getRequestURI());
            return;
        }
        
        if ("updateQuantity".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            List<ProductModel> selectedProducts = (List<ProductModel>) session.getAttribute("selectedProducts");
            for (ProductModel product : selectedProducts) {
                if (product.getId() == productId) {
                    product.setQuantity(quantity);
                    break;
                }
            }
            session.setAttribute("selectedProducts", selectedProducts);
            
            response.sendRedirect(request.getRequestURI());
            return;
        }
        
        if ("createOrder".equals(action)) {
            List<ProductModel> selectedProducts = (List<ProductModel>) session.getAttribute("selectedProducts");
            if (selectedProducts == null || selectedProducts.isEmpty()) {
                request.setAttribute("error", "Please select at least one product");
                doGet(request, response);
                return;
            }
            
            // Get customer ID from request parameter
            String customerIdStr = request.getParameter("customerId");
            System.out.println("Customer ID from request: " + customerIdStr);
            
            if (customerIdStr == null || customerIdStr.trim().isEmpty()) {
                System.out.println("No customer ID found in request");
                request.setAttribute("error", "Please select a customer");
                doGet(request, response);
                return;
            }
            
            int customerId = Integer.parseInt(customerIdStr);
            System.out.println("Parsed customer ID: " + customerId);
            
            // Create order
            OrderModel order = new OrderModel();
            order.setUserId(customerId);
            order.setStatus("pending");
            order.setOrderDate(new Date(System.currentTimeMillis()));
            
            // Calculate total amount
            double subtotal = calculateSubtotal(selectedProducts);
            double shipping = 8.99;
            double total = subtotal + shipping;
            order.setTotalAmount(total);
            
            // Get product IDs with their quantities
            List<Integer> productIds = new ArrayList<>();
            for (ProductModel product : selectedProducts) {
                // Add product ID as many times as its quantity
                for (int i = 0; i < product.getQuantity(); i++) {
                    productIds.add(product.getId());
                }
            }
            
            // Create order with products
            if (orderService.createOrder(order, productIds)) {
                // Clear selected products
                session.removeAttribute("selectedProducts");
                response.sendRedirect(request.getContextPath() + "/admin/order");
            } else {
                request.setAttribute("error", "Failed to create order");
                doGet(request, response);
            }
            return;
        }
    }
    
    private boolean isProductAlreadySelected(HttpSession session, int productId) {
        List<ProductModel> selectedProducts = (List<ProductModel>) session.getAttribute("selectedProducts");
        return selectedProducts.stream().anyMatch(p -> p.getId() == productId);
    }
    
    private double calculateSubtotal(List<ProductModel> products) {
        return products.stream()
                      .mapToDouble(p -> p.getPrice())
                      .sum();
    }
} 