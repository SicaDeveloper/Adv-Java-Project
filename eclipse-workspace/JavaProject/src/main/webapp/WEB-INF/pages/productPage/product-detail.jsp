<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>${product.name} - Tempest</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-detail.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar.css" />
	</head>
	<body>
		<jsp:include page="/WEB-INF/pages/homePage/navbar.jsp" />
		
		<div class="product-detail-container">
			<img
				class="product-detail-image"
				src="${pageContext.request.contextPath}/resource/images/products/${product.imageUrl}"
				alt="${product.name}"
			/>
			<div class="product-detail-info">
				<div class="product-name">${product.name}</div>
				<div class="product-price">$${product.price}</div>
                <div class="product-review">Review </div>
				<div class="product-quantity">
					<label for="quantity">Quantity:</label>
					<input type="number" id="quantity" name="quantity" min="1" max="${product.quantity}" value="1" 
						onchange="validateQuantity(this)" required>
					<span class="error" id="quantityError"></span>
				</div>
				<div class="product-actions">
					<form action="${pageContext.request.contextPath}/cart/add" method="post" style="display: inline;" onsubmit="return validateForm(this)">
						<input type="hidden" name="productId" value="${product.id}">
						<input type="hidden" name="productName" value="${product.name}">
						<input type="hidden" name="quantity" value="1" id="cartQuantity">
						<button type="submit" class="add-to-cart-button">Add to Cart</button>
					</form>
					<form action="${pageContext.request.contextPath}/checkout" method="post" style="display: inline;" onsubmit="return validateForm(this)">
						<input type="hidden" name="productId" value="${product.id}">
						<input type="hidden" name="quantity" value="1" id="checkoutQuantity">
						<button type="submit" class="buy-now-button">Buy Now</button>
					</form>
				</div>
				<div class="product-description">
					<details>
						<summary class="product-description-title">Description</summary>
						<div class="product-description-text">${product.description}</div>  
					</details>
				</div>
			</div>
		</div>

		<jsp:include page="/WEB-INF/pages/homePage/footer.jsp" />

		<script>
			function validateQuantity(input) {
				const quantity = parseInt(input.value);
				const maxQuantity = parseInt(input.max);
				const errorElement = document.getElementById('quantityError');
				
				if (isNaN(quantity) || quantity < 1) {
					errorElement.textContent = 'Quantity must be at least 1';
					input.value = 1;
					return false;
				} else if (quantity > maxQuantity) {
					errorElement.textContent = `Only ${maxQuantity} items available`;
					input.value = maxQuantity;
					return false;
				} else {
					errorElement.textContent = '';
					document.getElementById('cartQuantity').value = quantity;
					document.getElementById('checkoutQuantity').value = quantity;
					return true;
				}
			}

			function validateForm(form) {
				const quantity = parseInt(document.getElementById('quantity').value);
				const maxQuantity = parseInt(document.getElementById('quantity').max);
				
				if (isNaN(quantity) || quantity < 1 || quantity > maxQuantity) {
					document.getElementById('quantityError').textContent = 'Please enter a valid quantity';
					return false;
				}
				
				return true;
			}

			// Update quantity in forms when changed
			document.getElementById('quantity').addEventListener('change', function() {
				validateQuantity(this);
			});
		</script>
	</body>
</html>
