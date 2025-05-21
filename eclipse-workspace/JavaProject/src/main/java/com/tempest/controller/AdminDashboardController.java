package com.tempest.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tempest.model.OrderModel;
import com.tempest.model.ProductModel;
import com.tempest.model.UserModel;
import com.tempest.service.ProductService;
import com.tempest.service.UserService;
/**
 * Servlet implementation class AdminDashboardController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/admin/dashboard" })
public class AdminDashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService;
	private UserService userService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminDashboardController() {
		super();
		this.productService = new ProductService();
		this.userService = new UserService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Get total number of products
			List<ProductModel> products = productService.getAllProductsInfo();
			request.setAttribute("totalProducts", products != null ? products.size() : 0);

			// Get total number of customers
			List<UserModel> customers = userService.getAllUserDetails();
			request.setAttribute("totalCustomers", customers != null ? customers.size() : 0);

			// Set the page attribute
			String page = "admin-dashboard-statistics.jsp";
			request.setAttribute("page", page);
			request.getRequestDispatcher("/WEB-INF/pages/admin/admin-dashboard.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error loading dashboard: " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/pages/admin/admin-dashboard.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
