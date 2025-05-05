package com.tempest.controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.tempest.model.ProductModel;
import com.tempest.service.ProductService;

/**
 * Servlet implementation class HomeController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/Home", "/" })
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ProductService productService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeController() {
        super();
        this.productService = new ProductService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<ProductModel> products = productService.getAllProductsInfo();
        request.setAttribute("featuredProducts", products);
		request.getRequestDispatcher("/WEB-INF/pages/homePage/home.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
