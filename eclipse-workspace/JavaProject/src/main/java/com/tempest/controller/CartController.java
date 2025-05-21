package com.tempest.controller;

import java.io.IOException;
import java.util.List;

import com.tempest.model.CartModel;
import com.tempest.model.ProductModel;
import com.tempest.service.CartService;
import com.tempest.service.ProductService;
import com.tempest.util.CookieUtil;
import com.tempest.util.ValidationUtil;
import com.tempest.util.ErrorHandler;

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
	private ProductService productService;
	private static final String ROLE_COOKIE_NAME = "tempest_user_role";

	public void init() {
		cartService = new CartService();
		productService = new ProductService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("userId") == null) {
				ErrorHandler.handleAuthenticationError(request, response, "Please login to view your cart");
				return;
			}

			int userId = (int) session.getAttribute("userId");
			
			// Get the user's cart
			CartModel userCart = cartService.getUserCart(userId);
			if (userCart == null) {
				// If no cart exists, create a new one
				int cartId = cartService.createCart(userId);
				if (cartId == -1) {
					ErrorHandler.handleDatabaseError(request, response, 
						new Exception("Failed to create cart"), "/WEB-INF/pages/cartPage/cart.jsp");
					return;
				}
				userCart = cartService.getCartById(cartId);
			}
			
			// Get cart items using the cart ID
			List<ProductModel> cartItems = cartService.getCartItems(userCart.getId());
			
			if (cartItems == null) {
				ErrorHandler.handleDatabaseError(request, response, new Exception("Failed to fetch cart items"), 
					"/WEB-INF/pages/cartPage/cart.jsp");
				return;
			}

			request.setAttribute("cartItems", cartItems);
			request.getRequestDispatcher("/WEB-INF/pages/cartPage/cart.jsp").forward(request, response);
		} catch (Exception e) {
			ErrorHandler.handleDatabaseError(request, response, e, "/WEB-INF/pages/cartPage/cart.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
		if (action == null) {
			ErrorHandler.handleNotFoundError(request, response, "Invalid cart action");
			return;
		}

		try {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("userId") == null) {
				ErrorHandler.handleAuthenticationError(request, response, "Please login to manage your cart");
				return;
			}

			int userId = (int) session.getAttribute("userId");
			String productIdStr = request.getParameter("productId");
			String quantityStr = request.getParameter("quantity");

			if (!ValidationUtil.isValidProductId(productIdStr)) {
				ErrorHandler.handleValidationError(request, response, "Invalid product ID", 
					"/WEB-INF/pages/cartPage/cart.jsp");
				return;
			}

			int productId = Integer.parseInt(productIdStr);
			
			// Get the user's cart
			CartModel userCart = cartService.getUserCart(userId);
			if (userCart == null) {
				// If no cart exists, create a new one
				int cartId = cartService.createCart(userId);
				if (cartId == -1) {
					ErrorHandler.handleDatabaseError(request, response, 
						new Exception("Failed to create cart"), "/WEB-INF/pages/cartPage/cart.jsp");
					return;
				}
				userCart = cartService.getCartById(cartId);
			}
			
			int cartId = userCart.getId();

			switch (action) {
				case "/add":
					if (!ValidationUtil.isValidQuantity(quantityStr)) {
						ErrorHandler.handleValidationError(request, response, "Invalid quantity", 
							"/WEB-INF/pages/cartPage/cart.jsp");
						return;
					}
					int quantity = Integer.parseInt(quantityStr);
					ProductModel product = productService.getProductById(productId);
					
					if (product == null) {
						ErrorHandler.handleNotFoundError(request, response, "Product not found");
						return;
					}

					if (quantity > product.getQuantity()) {
						ErrorHandler.handleValidationError(request, response, 
							"Requested quantity exceeds available stock", "/WEB-INF/pages/cartPage/cart.jsp");
						return;
					}

					if (!cartService.addToCart(cartId, productId, quantity)) {
						ErrorHandler.handleDatabaseError(request, response, 
							new Exception("Failed to add item to cart"), "/WEB-INF/pages/cartPage/cart.jsp");
						return;
					}
					break;

				case "/update":
					if (!ValidationUtil.isValidQuantity(quantityStr)) {
						ErrorHandler.handleValidationError(request, response, "Invalid quantity", 
							"/WEB-INF/pages/cartPage/cart.jsp");
						return;
					}
					quantity = Integer.parseInt(quantityStr);
					product = productService.getProductById(productId);
					
					if (product == null) {
						ErrorHandler.handleNotFoundError(request, response, "Product not found");
						return;
					}

					if (quantity > product.getQuantity()) {
						ErrorHandler.handleValidationError(request, response, 
							"Requested quantity exceeds available stock", "/WEB-INF/pages/cartPage/cart.jsp");
						return;
					}

					if (!cartService.updateCartItemQuantity(cartId, productId, quantity)) {
						ErrorHandler.handleDatabaseError(request, response, 
							new Exception("Failed to update cart item"), "/WEB-INF/pages/cartPage/cart.jsp");
						return;
					}
					break;

				case "/delete":
				case "/remove":
					if (!cartService.removeFromCart(cartId, productId)) {
						ErrorHandler.handleDatabaseError(request, response, 
							new Exception("Failed to remove item from cart"), "/WEB-INF/pages/cartPage/cart.jsp");
						return;
					}
					break;

				default:
					ErrorHandler.handleNotFoundError(request, response, "Invalid cart action");
					return;
			}

			response.sendRedirect(request.getContextPath() + "/cart");
		} catch (NumberFormatException e) {
			ErrorHandler.handleValidationError(request, response, "Invalid input format", 
				"/WEB-INF/pages/cartPage/cart.jsp");
		} catch (Exception e) {
			ErrorHandler.handleDatabaseError(request, response, e, "/WEB-INF/pages/cartPage/cart.jsp");
		}
	}

	private double calculateSubtotal(List<ProductModel> cartItems) {
		if (cartItems == null) return 0.0;
		return cartItems.stream()
				.mapToDouble(item -> item.getPrice() * item.getQuantity())
				.sum();
	}
}
