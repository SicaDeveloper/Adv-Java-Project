package com.tempest.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.tempest.model.OrderModel;
import com.tempest.model.ProductModel;
import com.tempest.service.ProductService;
/**
 * Servlet implementation class AdminProductController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/admin/product" })
public class AdminProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ProductService productService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminProductController() {
        super();
        
        this.productService = new ProductService();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<ProductModel> productList = productService.getAllProductsInfo();
		String page = "admin-product-management.jsp";
		request.setAttribute("products", productList);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/WEB-INF/pages/admin/admin-dashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
