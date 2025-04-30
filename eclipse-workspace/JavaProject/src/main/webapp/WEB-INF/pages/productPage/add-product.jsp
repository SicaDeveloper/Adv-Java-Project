<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta charset="UTF-8">
<title>Add Product</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/add-product.css">
</head>
<body>
<jsp:include page="../homePage/navbar.jsp" />
<div class="add-product-bg">
    <div class="add-product-main">
        <form class="add-product-form" action="${pageContext.request.contextPath}/product/add" method="post" enctype="multipart/form-data">
            <div class="add-product-left">
                <section class="card general-info">
                <h2>Add Product</h2>
                <div class="form-group-container">
                    <div class = "form-group">
	                    <div class="form-item">
	                        <label for="productName">Name Product</label>
	                        <input type="text" id="productName" name="productName" placeholder="Product Name">
	                    </div>
	                    <div class="form-item">
	                        <label for="productDescription">Description Product</label>
	                        <textarea id="productDescription" name="productDescription" placeholder="Product Description"></textarea>
	                    </div>
	                    <div class="form-item">
	                            <label for="productPrice">Base Pricing</label>
	                            <input type="text" id="productPrice" name="productPrice" placeholder="$0.00">
	                    </div>
                    </div>
                    <div class="form-group">
	                    <div class="form-item">
	                            <label for="productQuantity">Stock</label>
	                            <input type="number" id="productQuantity" name="productQuantity" placeholder="0">
	                    </div>
	                     <div class="form-item">
	                        <label for="productCategory">Product Category</label>
	                        <select id="productCategory" name="productCategory">
	                        	<c:forEach var="category" category = "${categories}">
	                            	<option value="${category.value}">${category}</option>
	                           </c:forEach>
	                        </select>
	                    </div>
                    </div>
                    </div>
                </section>
            </div>
            <div class="add-product-right">
                <section class="card upload-img">
                    <h2>Upload Img</h2>
                    <div class="img-preview">
                        <img id="productImage" src="" alt="Product Image Preview">
                    </div>
                    <input type="file" id="productImageInput" name="productImageInput">
                </section>
                <div class="form-actions">
                    <button type="button" class="btn-secondary">Save Draft</button>
                    <button type="submit" class="btn-primary">Add Product</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>