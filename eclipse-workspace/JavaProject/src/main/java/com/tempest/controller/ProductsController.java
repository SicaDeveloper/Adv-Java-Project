package com.tempest.controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

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
		// Get selected category IDs from request parameters
		String[] categoryIds = request.getParameterValues("categoryIds");
		
		List<ProductModel> products;
		if (categoryIds != null && categoryIds.length > 0) {
			// Convert String array to List<Integer>
			List<Integer> selectedCategoryIds = new ArrayList<>();
			for (String id : categoryIds) {
				try {
					selectedCategoryIds.add(Integer.parseInt(id));
				} catch (NumberFormatException e) {
					// Skip invalid category IDs
					continue;
				}
			}
			
			// Get products filtered by selected categories
			products = productService.getProductsByCategories(selectedCategoryIds);
		} else {
			// Get all products if no categories are selected
			products = productService.getAllProductsInfo();
		}
		
		// Get all categories for the filter sidebar
		List<CategoriesModel> categories = categoriesService.getAllCategoryInfo();
		
		// Set attributes for the JSP
		request.setAttribute("product", products);
		request.setAttribute("categories", categories);
		
		// Forward to the product listing page
		request.getRequestDispatcher("/WEB-INF/pages/productPage/product-listing.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
