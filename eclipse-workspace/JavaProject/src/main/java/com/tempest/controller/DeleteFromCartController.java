package com.tempest.controller;

import java.io.IOException;

import com.tempest.model.CartModel;
import com.tempest.service.CartService;
import com.tempest.util.ValidationUtil;
import com.tempest.util.ErrorHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class RemoveFromCartController
 */
@WebServlet("/cart/delete")
public class DeleteFromCartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CartService cartService;

	public void init() {
		cartService = new CartService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("userId") == null) {
				ErrorHandler.handleAuthenticationError(request, response, "Please login to manage your cart");
				return;
			}

			int userId = (int) session.getAttribute("userId");
			String productIdStr = request.getParameter("productId");

			if (!ValidationUtil.isValidProductId(productIdStr)) {
				ErrorHandler.handleValidationError(request, response, "Invalid product ID", 
					"/WEB-INF/pages/cartPage/cart.jsp");
				return;
			}

			int productId = Integer.parseInt(productIdStr);
			
			// Get the user's cart
			CartModel userCart = cartService.getUserCart(userId);
			if (userCart == null) {
				ErrorHandler.handleNotFoundError(request, response, "Cart not found");
				return;
			}
			
			int cartId = userCart.getId();

			// Remove the item from cart
			if (!cartService.removeFromCart(cartId, productId)) {
				ErrorHandler.handleDatabaseError(request, response, 
					new Exception("Failed to remove item from cart"), "/WEB-INF/pages/cartPage/cart.jsp");
				return;
			}

			// Redirect back to cart page
			response.sendRedirect(request.getContextPath() + "/cart");
		} catch (NumberFormatException e) {
			ErrorHandler.handleValidationError(request, response, "Invalid input format", 
				"/WEB-INF/pages/cartPage/cart.jsp");
		} catch (Exception e) {
			ErrorHandler.handleDatabaseError(request, response, e, "/WEB-INF/pages/cartPage/cart.jsp");
		}
	}

}
