package com.tempest.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.tempest.model.CartModel;
import com.tempest.service.CartService;
import com.tempest.service.ProductService;

@WebServlet("/cart/add")
public class AddToCartController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CartService cartService;
    private ProductService productService;

    public AddToCartController() {
        super();
        this.cartService = new CartService();
        this.productService = new ProductService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Check if user is logged in
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // Get and validate product ID
            String productIdStr = request.getParameter("productId");
            if (productIdStr == null || productIdStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Product ID is required");
            }
            int productId = Integer.parseInt(productIdStr);

            // Validate product exists
            if (productService.getProductById(productId) == null) {
                throw new IllegalArgumentException("Product does not exist");
            }

            // Get or create cart
            CartModel cart = cartService.getUserCart(userId);
            if (cart == null) {
                int newCartId = cartService.createCart(userId);
                if (newCartId == -1) {
                    throw new Exception("Failed to create cart");
                }
                cart = cartService.getCartById(newCartId);
            }

            // Get quantity (default to 1 if not specified)
            int quantity = 1;
            String quantityStr = request.getParameter("quantity");
            if (quantityStr != null && !quantityStr.trim().isEmpty()) {
                quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    throw new IllegalArgumentException("Quantity must be greater than 0");
                }
            }

            // Add product to cart
            if (cartService.addToCart(cart.getId(), productId, quantity)) {
                // Success - redirect to cart page
                response.sendRedirect(request.getContextPath() + "/cart");
            } else {
                // Failed to add to cart
                session.setAttribute("error", "Failed to add product to cart");
                response.sendRedirect(request.getContextPath() + "/products");
            }

        } catch (NumberFormatException e) {
            session.setAttribute("error", "Invalid product ID or quantity format");
            response.sendRedirect(request.getContextPath() + "/products");
        } catch (IllegalArgumentException e) {
            session.setAttribute("error", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/products");
        } catch (Exception e) {
            session.setAttribute("error", "An error occurred while processing your request");
            response.sendRedirect(request.getContextPath() + "/products");
        }
    }
} 