<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Product</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/edit-product.css">
</head>
<body>
<div class="add-product-bg">
    <div class="add-product-main">
        <form class="add-product-form" action="${pageContext.request.contextPath}/admin/product/edit" method="post" enctype="multipart/form-data">
            <input type="hidden" name="productId" value="${product.id}">
            <div class="card">
                <h2>Edit Product</h2>
                <div class="form-content">
                    <div class="form-left">
                        <div class="form-group basic-info">
                            <h3>Basic Information</h3>
                            <div class="form-row">
                                <div class="form-item">
                                    <label for="productId">Product ID</label>
                                    <input type="text" id="productId" value="${product.id}" disabled>
                                </div>
                                <div class="form-item">
                                    <label for="productName">Product Name</label>
                                    <input type="text" id="productName" name="productName" value="${product.name}" placeholder="Product Name" required>
                                </div>
                            </div>
                        </div>
                        
                        <div class="form-group details">
                            <h3>Product Details</h3>
                            <div class="form-item">
                                <label for="productDescription">Description</label>
                                <textarea id="productDescription" name="productDescription" placeholder="Product Description" required>${product.description}</textarea>
                            </div>
                        </div>

                        <div class="form-group pricing-info">
                            <h3>Pricing & Inventory</h3>
                            <div class="form-row">
                                <div class="form-item">
                                    <label for="productPrice">Base Price</label>
                                    <input type="text" id="productPrice" name="productPrice" value="${product.price}" placeholder="$0.00" required>
                                </div>
                                <div class="form-item">
                                    <label for="productQuantity">Stock</label>
                                    <input type="number" id="productQuantity" name="productQuantity" value="${product.quantity}" placeholder="0" required>
                                </div>
                                <div class="form-item">
                                    <label for="productCategory">Category</label>
                                    <select id="productCategory" name="productCategory" required>
                                        <c:forEach var="category" items="${categories}">
                                            <c:set var="isSelected" value="${category.id == product.categoryId}" />
                                            <option value="${category.id}" ${isSelected ? 'selected' : ''}>${category.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-right">
                        <div class="form-group">
                            <h3>Product Image</h3>
                            <div class="img-preview">
                                <img id="productImage" src="${pageContext.request.contextPath}/uploads/${product.imageUrl}" alt="Product Image Preview">
                            </div>
                            <input type="file" id="productImageInput" name="productImageInput" accept="image/*">
                        </div>
                    </div>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn-primary">Update Product</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>