package com.tempest.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.tempest.service.CartService;

@WebServlet("/cart/add")
public class AddToCartController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CartService cartService;

    public AddToCartController() {
        super();
        this.cartService = new CartService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get the current user's cart ID from session
            HttpSession session = request.getSession();
            Integer cartId = (Integer) session.getAttribute("cartId");
            
            if (cartId == null) {
                // If no cart exists, create a new one
                cartId = cartService.createCart(1); // Using user ID 1 as default for now
                session.setAttribute("cartId", cartId);
            }

            // Get product ID from request
            int productId = Integer.parseInt(request.getParameter("productId"));
            
            // Add product to cart with default quantity of 1
            if (cartService.addToCart(cartId, productId, 1)) {
                response.sendRedirect(request.getContextPath() + "/cart");
            } else {
                request.setAttribute("error", "Failed to add product to cart");
                response.sendRedirect(request.getContextPath() + "/");
            }
        } catch (NumberFormatException e) {
            System.out.println("AddToCartController: Invalid product ID format");
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/");
        } catch (Exception e) {
            System.out.println("AddToCartController: Error occurred");
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
} 