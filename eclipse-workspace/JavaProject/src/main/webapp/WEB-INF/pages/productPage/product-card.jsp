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
	<img class="product-image" src="${pageContext.request.contextPath}/resource/images/products/${param.imageUrl}" alt="${param.name}" onerror="this.src='${pageContext.request.contextPath}/resource/images/placeholder.png'" />
		<div class="product-details">
			<div class="product-name"><a href="${pageContext.request.contextPath}/product/detail/${param.id}">${param.name}</a></div>
			<div class="product-price">$${param.price}</div>
			<div class="product-actions">
				<button onclick="handleBuyNow(${param.id}, ${param.quantity})" class="action-button">Buy-now</button>
				<form action="${pageContext.request.contextPath}/cart/add" method="post" style="display: inline;" 
					  onsubmit="return validateAddToCart(this, ${param.quantity})">
					<input type="hidden" name="productId" value="${param.id}">
					<input type="hidden" name="productName" value="${param.name}">
					<input type="hidden" name="quantity" value="1">
					<button type="submit" class="action-button">Add to Cart</button>
				</form>
			</div>
			<c:if test="${not empty error}">
				<div class="error-message">${error}</div>
			</c:if>
		</div>
</div>
</body>
</html>	