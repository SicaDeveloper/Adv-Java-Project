<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
<title>Add Product</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/add-product.css">
</head>
<body>
	<jsp:include page="../homePage/navbar.jsp" />
    <div class="add-product-container">
        <div class="add-product-header">
            <h1>Add Product</h1>
        </div>
        <form class="product-form" action="${pageContext.request.contextPath}/product/add" method="post" >
            <div class="product-details">
                <div class="product-details-item">
                    <label for="productName">Product Name:</label>
                    <input type="text" id="productName" name="productName">
                </div>
                <div class="product-details-item">
                    <label for="productPrice">Product Price:</label>
                    <input type="text" id="productPrice" name="productPrice">
                </div>
                <div class="product-details-item">
                    <label for="productDescription">Product Description:</label>
                    <textarea id="productDescription" name="productDescription"></textarea>
                </div>
                <div class="product-details-item">
                    <label for="productQuantity">Product Quantity:</label>
                    <input type="number" id="productQuantity" name="productQuantity">
                </div>
            </div>
            <div class="product-image">
                <div class="image-header">Product Image</div>
                <img id="productImage" src="" alt="">
                <input type="file" id="productImageInput" name="productImageInput">
            </div>
            <div class="product-category">
                <label for="productCategory">Product Category:</label>
                <select id="productCategory" name="productCategory">
                    <option value="electronics">Electronics</option>
                    <option value="clothing">Clothing</option>
                    <option value="home">Home</option>
                    <option value="books">Books</option>
                    <option value="toys">Toys</option>
                </select>
            </div>
            <input type="submit" value="Add Product">
        </form>
    </div>
</body>
</html>