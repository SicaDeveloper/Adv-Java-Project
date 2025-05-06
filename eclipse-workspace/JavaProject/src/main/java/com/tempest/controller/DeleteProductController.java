package com.tempest.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.tempest.model.ProductModel;
import com.tempest.service.ProductService;

/**
 * Servlet implementation class DeleteProductController
 */
@WebServlet("/admin/product/delete")
public class DeleteProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService;

    public DeleteProductController() {
        super();
        this.productService = new ProductService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get product ID from request parameter
            String productIdStr = request.getParameter("productId");
            if (productIdStr == null || productIdStr.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/admin/product");
                return;
            }

            int productId = Integer.parseInt(productIdStr);
            
            // Get the product to be deleted
            ProductModel product = productService.getProductById(productId);
            if (product == null) {
                response.sendRedirect(request.getContextPath() + "/admin/product");
                return;
            }

            // Delete the product
            if (productService.removeProduct(product)) {
                response.sendRedirect(request.getContextPath() + "/admin/product");
            } else {
                request.setAttribute("error", "Failed to delete product");
                response.sendRedirect(request.getContextPath() + "/admin/product");
            }
        } catch (NumberFormatException e) {
            System.out.println("DeleteProductController: Invalid product ID format");
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/product");
        } catch (Exception e) {
            System.out.println("DeleteProductController: Error occurred");
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/product");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
} 