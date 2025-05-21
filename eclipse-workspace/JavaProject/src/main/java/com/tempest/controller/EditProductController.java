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
		try {
			String productIdStr = request.getParameter("productId");
			
			if (productIdStr == null || productIdStr.trim().isEmpty()) {
				response.sendRedirect(request.getContextPath() + "/admin/product");
				return;
			}

			int productId = Integer.parseInt(productIdStr);
			
			ProductModel product = productService.getProductById(productId);
			
			if (product == null) {
				response.sendRedirect(request.getContextPath() + "/admin/product");
				return;
			}

			// Get the product's category
			CategoriesModel productCategory = productService.getProductCategory(productId);
			if (productCategory != null) {
				product.setCategoryId(productCategory.getId());
			}

			List<CategoriesModel> categories = categoriesService.getAllCategoryInfo();
			
			
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
			String imageUrl = productService.getProductById(productId).getImageUrl();
			Part imagePart = request.getPart("productImageInput");
			if (imagePart != null && imagePart.getSize() > 0) {
				imageUrl = imageUtil.getImageNameFromPart(imagePart);
			}
			
			// Create product model with all fields including category ID
			ProductModel product = new ProductModel(productId, productName, productDescription, price, quantity, imageUrl);
			product.setCategoryId(categoryId);
			
			// Update product
			if (productService.updateProduct(product)) {
				response.sendRedirect(request.getContextPath() + "/admin/product");
			} else {
				request.setAttribute("error", "Failed to update product");
				doGet(request, response);
			}
			
		} catch (NumberFormatException e) {
			System.out.println("EditProductController: Invalid number format");
			e.printStackTrace();
			request.setAttribute("error", "Invalid number format in form data");
			doGet(request, response);
		} catch (Exception e) {
			System.out.println("EditProductController: Error occurred");
			e.printStackTrace();
			request.setAttribute("error", "An error occurred while updating the product");
			doGet(request, response);
		}
	}
}
