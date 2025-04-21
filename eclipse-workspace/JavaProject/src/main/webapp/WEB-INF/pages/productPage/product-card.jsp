<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-card.css">
</head>
<body>
<div class="product-card">
	<img class="product-image" src="${param.imageUrl}" alt="" />
		<div class="product-details">
			<div class="product-name">${param.productName}</div>
			<div class="product-price">${param.productPrice}</div>
			<div class="product-actions">
				<button class="action-button">Buy-now</button>
				<button class="action-button">Add to Cart</button>
			</div>
	</div>
</div>
</body>
</html>