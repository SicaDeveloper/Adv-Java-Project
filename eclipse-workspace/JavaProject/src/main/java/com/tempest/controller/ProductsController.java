package com.tempest.controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tempest.model.CategoriesModel;
import com.tempest.model.ProductModel;
import com.tempest.service.CategoriesServices;
import com.tempest.service.ProductService;
/**
 * Servlet implementation class HomeController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/products" })
public class ProductsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ProductService productService;
    private CategoriesServices categoriesService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductsController() {
    	super();
    	this.productService = new ProductService();
    	this.categoriesService = new CategoriesServices();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<ProductModel> products = productService.getAllProductsInfo();
		List<CategoriesModel> categories = categoriesService.getAllCategoryInfo();
        // Add more ProductModel objects here

        // Set the list as a request attribute
        request.setAttribute("product", products);
        request.setAttribute("categories", categories);
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/pages/productPage/product-listing-page.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
