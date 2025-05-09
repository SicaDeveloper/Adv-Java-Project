package com.tempest.controller;

import java.io.IOException;
import java.util.List;

import com.tempest.model.CartModel;
import com.tempest.model.ProductModel;
import com.tempest.service.CartService;
import com.tempest.util.CookieUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class CartController
 */
@WebServlet(urlPatterns = {"/cart", "/cart/*"})
public class CartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CartService cartService;
	private static final String ROLE_COOKIE_NAME = "tempest_user_role";

	public CartController() {
		cartService = new CartService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			try {
				HttpSession session = request.getSession();
			
				int userId = Integer.parseInt(CookieUtil.getCookieValue(request, "userId"));
				
				// Get user's cart
				CartModel cart = cartService.getUserCart(userId);
				System.out.println("CartController: Retrieved cart: " + (cart != null ? cart.getId() : "null"));
				
				if (cart == null) {
					// Create new cart if user doesn't have one
					int cartId = cartService.createCart(userId);
					System.out.println("CartController: Created new cart with ID: " + cartId);
					cart = new CartModel();
					cart.setId(cartId);
				}

				// Get cart items with product details
				List<ProductModel> cartItems = cartService.getCartItems(cart.getId());
				System.out.println("CartController: Retrieved " + (cartItems != null ? cartItems.size() : 0) + " cart items");
				double subtotal = calculateSubtotal(cartItems);
				double tax = subtotal * 0.13; // 13% tax
				double total = subtotal + tax;

				request.setAttribute("cartItems", cartItems);
				request.setAttribute("subtotal", subtotal);
				request.setAttribute("tax", tax);
				request.setAttribute("total", total);
				
				System.out.println("CartController: Forwarding to cart.jsp");
				request.getRequestDispatcher("/WEB-INF/pages/cartPage/cart.jsp").forward(request, response);
			} catch (Exception e) {
				System.out.println("CartController: Error processing cart: " + e.getMessage());
				e.printStackTrace();
				request.setAttribute("error", "An error occurred while loading your cart");
				response.sendRedirect(request.getContextPath() + "/home");
			}
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		System.out.println("CartController: Processing POST request for path: " + pathInfo);
		
		// Check role from cookie
		String role = CookieUtil.getCookieValue(request, ROLE_COOKIE_NAME);
		System.out.println("CartController: User role from cookie: " + role);
		
		// Check session for user ID
		HttpSession session = request.getSession(false);
		if (session == null) {
			System.out.println("CartController: No session found");
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		Integer userId = (Integer) session.getAttribute("userId");
		System.out.println("CartController: User ID from session: " + userId);
		
		if (userId == null) {
			System.out.println("CartController: No user ID in session");
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		if (role == null || !role.equals("customer")) {
			System.out.println("CartController: User not authorized - Role: " + role);
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		try {
			CartModel cart = cartService.getUserCart(userId);
			System.out.println("CartController: Retrieved cart: " + (cart != null ? cart.getId() : "null"));
			
			if (cart == null) {
				int cartId = cartService.createCart(userId);
				System.out.println("CartController: Created new cart with ID: " + cartId);
				cart = new CartModel();
				cart.setId(cartId);
			}
			
			if (pathInfo != null && pathInfo.equals("/add")) {
				try {
					String productIdStr = request.getParameter("productId");
					System.out.println("CartController: Received productId parameter: " + productIdStr);
					
					if (productIdStr == null || productIdStr.trim().isEmpty()) {
						System.out.println("CartController: Product ID is null or empty");
						request.setAttribute("error", "Invalid product ID");
						response.sendRedirect(request.getContextPath() + "/products");
						return;
					}

					int productId = Integer.parseInt(productIdStr);
					int quantity = 1; // Default quantity
					System.out.println("CartController: Adding product ID: " + productId + " with quantity: " + quantity);

					// Add product to cart
					if (cartService.addToCart(cart.getId(), productId, quantity)) {
						System.out.println("CartController: Successfully added product to cart");
						response.sendRedirect(request.getContextPath() + "/cart");
					} else {
						System.out.println("CartController: Failed to add product to cart");
						request.setAttribute("error", "Failed to add item to cart");
						response.sendRedirect(request.getContextPath() + "/products");
					}
				} catch (NumberFormatException e) {
					System.out.println("CartController: Invalid product ID format: " + e.getMessage());
					request.setAttribute("error", "Invalid product ID format");
					response.sendRedirect(request.getContextPath() + "/products");
				} catch (Exception e) {
					System.out.println("CartController: Error adding to cart: " + e.getMessage());
					e.printStackTrace();
					request.setAttribute("error", "An error occurred while adding item to cart");
					response.sendRedirect(request.getContextPath() + "/products");
				}
			} else if (pathInfo != null && pathInfo.equals("/remove")) {
				try {
					int productId = Integer.parseInt(request.getParameter("productId"));
					
					if (cartService.removeFromCart(cart.getId(), productId)) {
						response.sendRedirect(request.getContextPath() + "/cart");
					} else {
						request.setAttribute("error", "Failed to remove item from cart");
						response.sendRedirect(request.getContextPath() + "/cart");
					}
				} catch (Exception e) {
					System.out.println("CartController: Error removing from cart: " + e.getMessage());
					e.printStackTrace();
					request.setAttribute("error", "An error occurred while removing item from cart");
					response.sendRedirect(request.getContextPath() + "/cart");
				}
			} else if (pathInfo != null && pathInfo.equals("/update")) {
				try {
					int productId = Integer.parseInt(request.getParameter("productId"));
					int quantity = Integer.parseInt(request.getParameter("quantity"));
					
					if (cartService.updateCartItemQuantity(cart.getId(), productId, quantity)) {
						response.sendRedirect(request.getContextPath() + "/cart");
					} else {
						request.setAttribute("error", "Failed to update item quantity");
						response.sendRedirect(request.getContextPath() + "/cart");
					}
				} catch (Exception e) {
					System.out.println("CartController: Error updating cart: " + e.getMessage());
					e.printStackTrace();
					request.setAttribute("error", "An error occurred while updating cart");
					response.sendRedirect(request.getContextPath() + "/cart");
				}
			}
		} catch (Exception e) {
			System.out.println("CartController: Unexpected error: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", "An unexpected error occurred");
			response.sendRedirect(request.getContextPath() + "/products");
		}
	}

	private double calculateSubtotal(List<ProductModel> cartItems) {
		if (cartItems == null) return 0.0;
		return cartItems.stream()
				.mapToDouble(item -> item.getPrice() * item.getQuantity())
				.sum();
	}
}
