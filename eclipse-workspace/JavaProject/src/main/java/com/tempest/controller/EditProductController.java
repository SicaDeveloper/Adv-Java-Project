package com.tempest.controller;

import java.io.IOException;
import java.util.List;

import com.tempest.model.CategoriesModel;
import com.tempest.model.ProductModel;
import com.tempest.service.CategoriesServices;
import com.tempest.service.ProductService;
import com.tempest.util.ImageUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * Servlet implementation class EditProductController
 */
@WebServlet("/admin/product/edit")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                maxFileSize = 1024 * 1024 * 10, // 10MB
                maxRequestSize = 1024 * 1024 * 50) // 50MB
public class EditProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService;
	private CategoriesServices categoriesService;
	private ImageUtil imageUtil;

	public void init() {
		productService = new ProductService();
		categoriesService = new CategoriesServices();
		imageUtil = new ImageUtil();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("EditProductController: doGet called");
		try {
			String productIdStr = request.getParameter("productId");
			System.out.println("EditProductController: productId from request = " + productIdStr);
			
			if (productIdStr == null || productIdStr.trim().isEmpty()) {
				System.out.println("EditProductController: No productId provided, redirecting to product list");
				response.sendRedirect(request.getContextPath() + "/admin/product");
				return;
			}

			int productId = Integer.parseInt(productIdStr);
			System.out.println("EditProductController: Fetching product with ID = " + productId);
			
			ProductModel product = productService.getProductById(productId);
			
			if (product == null) {
				System.out.println("EditProductController: Product not found, redirecting to product list");
				response.sendRedirect(request.getContextPath() + "/admin/product");
				return;
			}

			// Get the product's category
			CategoriesModel productCategory = productService.getProductCategory(productId);
			if (productCategory != null) {
				product.setCategoryId(productCategory.getId());
			}

			List<CategoriesModel> categories = categoriesService.getAllCategoryInfo();
			System.out.println("EditProductController: Found " + categories.size() + " categories");
			
			request.setAttribute("product", product);
			request.setAttribute("categories", categories);
			request.setAttribute("page", "/WEB-INF/pages/productPage/edit-product.jsp");
			
			System.out.println("EditProductController: Forwarding to edit page");
			request.getRequestDispatcher("/WEB-INF/pages/admin/admin-dashboard.jsp")
				  .forward(request, response);
				  
		} catch (NumberFormatException e) {
			System.out.println("EditProductController: Invalid product ID format");
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/admin/product");
		} catch (Exception e) {
			System.out.println("EditProductController: Error occurred");
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/admin/product");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Get product ID from request parameter
			String productIdStr = request.getParameter("productId");
			if (productIdStr == null || productIdStr.trim().isEmpty()) {
				response.sendRedirect(request.getContextPath() + "/admin/product");
				return;
			}

			int productId = Integer.parseInt(productIdStr);
			
			// Get form data
			String productName = request.getParameter("productName");
			String productDescription = request.getParameter("productDescription");
			double price = Double.parseDouble(request.getParameter("productPrice"));
			int quantity = Integer.parseInt(request.getParameter("productQuantity"));
			int categoryId = Integer.parseInt(request.getParameter("productCategory"));
			
			// Handle image upload if a new image is provided
			String imageUrl = null;
			Part imagePart = request.getPart("productImageInput");
			if (imagePart != null && imagePart.getSize() > 0) {
				imageUrl = imageUtil.getImageNameFromPart(imagePart);
			}
			
			// Create product model with all fields
			ProductModel product = new ProductModel(productId, productName, productDescription, price, quantity, imageUrl);
			
			// Update product
			if (productService.updateProduct(product)) {
				// Update product category if needed
				productService.updateProductCategory(productId, categoryId);
				response.sendRedirect(request.getContextPath() + "/admin/product");
			} else {
				// Handle update failure
				request.setAttribute("error", "Failed to update product");
				doGet(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "An error occurred while updating the product");
			doGet(request, response);
		}
	}
}
