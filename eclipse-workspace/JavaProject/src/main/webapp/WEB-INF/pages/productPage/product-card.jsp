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
	<img class="product-image" src="${pageContext.request.contextPath}${param.imageUrl}" alt="" />
		<div class="product-details">
			<div class="product-name"><a href="${pageContext.request.contextPath}/product-detail/${param.id}">${param.name}</a></div>
			<div class="product-price">$${param.price}</div>
			<div class="product-actions">
				<button onclick="" class="action-button">Buy-now</button>
				<form action="${pageContext.request.contextPath}/cart/add" method="post" style="display: inline;">
					<input type="hidden" name="productId" value="${param.id}">
					<button type="submit" class="action-button">Add to Cart</button>
				</form>
			</div>
		</div>
</div>
</body>
</html>