package com.tempest.controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import com.tempest.model.ProductModel;
/**
 * Servlet implementation class HomeController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/products" })
public class ProductsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductsController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<ProductModel> products = new ArrayList<>();
        products.add(new ProductModel(1,"Nike Shoes", "These are nike shoes and they are very cool", 30, "/resource/W+NK+24.7+DF+HZ+TOP+SOFT+KNIT.avif")); // Assuming ProductModel has an ID
        products.add(new ProductModel(2,"Adidas T-Shirt", "Comfortable sports t-shirt", 20, "/resource/adidas_tshirt.jpg"));
        products.add(new ProductModel(3,"Adidas T-Shirt", "Comfortable sports t-shirt", 20, "/resource/W+NK+24.7+DF+HZ+TOP+SOFT+KNIT.avif"));
        products.add(new ProductModel(4,"Adidas T-Shirt", "Comfortable sports t-shirt", 20, "/resource/W+NK+24.7+DF+HZ+TOP+SOFT+KNIT.avif"));
        products.add(new ProductModel(5,"Adidas T-Shirt", "Comfortable sports t-shirt", 20, "/resource/W+NK+24.7+DF+HZ+TOP+SOFT+KNIT.avif"));
        products.add(new ProductModel(6,"Adidas T-Shirt", "Comfortable sports t-shirt", 20, "/resource/W+NK+24.7+DF+HZ+TOP+SOFT+KNIT.avif"));
        // Add more ProductModel objects here

        // Set the list as a request attribute
        request.setAttribute("product", products);
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
