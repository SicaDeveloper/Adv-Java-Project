<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<link />
		<meta charset="UTF-8" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-detail.css" />
	</head>
	<body>
		<div class="product-detail-container">
			<img
				class="product-detail-image"
				src="${product.image}"
				alt="${product.name}"
			/>
			<div class="product-detail-info">
				<div class="product-name">${product.name} temp</div>
				<div class="product-price">${product.price} 50</div>
                <div class="product-review">Review <span>5</span></div>
				<div class="product-quantity">Quantity:</div>
				<button class="add-to-cart-button">Add to Cart</button>
			    <button class="buy-now-button">Buy Now</button>
			    <div class="product-description">
			    <details>
			    <summary>
                    <div class="product-description-title">Description</div>
                </summary>
                    <div class="product-description-text">${product.description}</div>  
                </details>
			</div>
		</div>
		</div>
	</body>
</html>
