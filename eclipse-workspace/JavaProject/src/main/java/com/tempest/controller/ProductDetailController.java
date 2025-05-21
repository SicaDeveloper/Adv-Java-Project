package com.tempest.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.tempest.service.ProductService;
import com.tempest.model.ProductModel;
import com.tempest.util.ValidationUtil;
import com.tempest.util.ErrorHandler;

/**
 * Servlet implementation class ProductDetailControler
 */
@WebServlet("/product/detail/*")
public class ProductDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductDetailController() {
        super();
    }

    public void init() {
        productService = new ProductService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String pathInfo = request.getPathInfo();
			if (pathInfo == null || pathInfo.equals("/")) {
				ErrorHandler.handleNotFoundError(request, response, "Product ID is required");
				return;
			}

			String productIdStr = pathInfo.substring(1);
			if (!ValidationUtil.isValidProductId(productIdStr)) {
				ErrorHandler.handleValidationError(request, response, "Invalid product ID format", "/WEB-INF/pages/error/not-found.jsp");
				return;
			}

			int productId = Integer.parseInt(productIdStr);
			ProductModel product = productService.getProductById(productId);
			
			if (product == null) {
				ErrorHandler.handleNotFoundError(request, response, "Product not found");
				return;
			}

			request.setAttribute("product", product);
			request.getRequestDispatcher("/WEB-INF/pages/productPage/product-detail.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			ErrorHandler.handleValidationError(request, response, "Invalid product ID format", "/WEB-INF/pages/error/not-found.jsp");
		} catch (Exception e) {
			ErrorHandler.handleDatabaseError(request, response, e, "/WEB-INF/pages/error/not-found.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String productIdStr = request.getParameter("productId");
			String quantityStr = request.getParameter("quantity");
			
			if (productIdStr == null || quantityStr == null) {
				ErrorHandler.handleValidationError(request, response, "Missing required parameters", "/WEB-INF/pages/error/not-found.jsp");
				return;
			}

			if (!ValidationUtil.isValidProductId(productIdStr) || !ValidationUtil.isValidQuantity(quantityStr)) {
				ErrorHandler.handleValidationError(request, response, "Invalid input format", "/WEB-INF/pages/error/not-found.jsp");
				return;
			}

			int productId = Integer.parseInt(productIdStr);
			int quantity = Integer.parseInt(quantityStr);
			
			ProductModel product = productService.getProductById(productId);
			if (product == null) {
				ErrorHandler.handleNotFoundError(request, response, "Product not found");
				return;
			}
			
			if (quantity < 1 || quantity > product.getQuantity()) {
				request.setAttribute("error", "Invalid quantity. Available quantity: " + product.getQuantity());
				request.setAttribute("product", product);
				request.getRequestDispatcher("/WEB-INF/pages/productPage/product-detail.jsp").forward(request, response);
				return;
			}
			
			doGet(request, response);
			
		} catch (NumberFormatException e) {
			ErrorHandler.handleValidationError(request, response, "Invalid input format", "/WEB-INF/pages/error/not-found.jsp");
		} catch (Exception e) {
			ErrorHandler.handleDatabaseError(request, response, e, "/WEB-INF/pages/error/not-found.jsp");
		}
	}

}
