package com.tempest.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tempest.model.CategoriesModel;
import com.tempest.model.ProductModel;
import com.tempest.service.CategoriesServices;
import com.tempest.service.ProductService;
import com.tempest.util.ImageUtil;

/**
 * Servlet implementation class AddProductController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/admin/product/add" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
					maxFileSize = 1024 * 1024 * 10, // 10MB
					maxRequestSize = 1024 * 1024 * 50) // 50MB
public class AddProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoriesServices categoriesServices;
	private ProductService productService;
	private ImageUtil imageUtil;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddProductController() {
        super();
        this.categoriesServices = new CategoriesServices();
        this.productService = new ProductService();
        this.imageUtil = new ImageUtil();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		List<CategoriesModel> categoryList = categoriesServices.getAllCategoryInfo();
		
		request.setAttribute("categories",categoryList);
		
		String page = "/WEB-INF/pages/productPage/add-product.jsp";
		
		request.setAttribute("page", page);
		
		request.getRequestDispatcher("/WEB-INF/pages/admin/admin-dashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String product_name = request.getParameter("productName");
		String product_description = request.getParameter("productDescription");
		int quantity = Integer.parseInt(request.getParameter("productQuantity"));
		double price  = Double.parseDouble(request.getParameter("productPrice"));
		String category  = request.getParameter("productCategory");
		String imageUrl;
		Part image = request.getPart("productImageInput");
		String imageName = imageUtil.getImageNameFromPart(image);
		
		// Upload the image to the products directory
		String rootPath = request.getServletContext().getRealPath("/");
		if (imageUtil.uploadImage(image, rootPath, "products")) {
			// Construct the full image URL path
			imageUrl = request.getContextPath() + "/resource/images/products/" + imageName;
			ProductModel product = new ProductModel(product_name, product_description, price, quantity, imageUrl);	
			if (productService.createProduct(product)) {
				response.sendRedirect(request.getContextPath() + "/admin/dashboard");
			} else {
				request.setAttribute("error", "Failed to create product");
			}
		} else {
			request.setAttribute("error", "Failed to upload product image");
		}
	}


}
