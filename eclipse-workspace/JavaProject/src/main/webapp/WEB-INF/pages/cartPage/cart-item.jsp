<!-- filepath: c:\Users\rames\eclipse-workspace\JavaProject\src\main\webapp\WEB-INF\pages\cartPage\cart-item.jsp -->
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>Cart Item</title>
		<link rel="stylesheet" href="../../../css/cart-item.css" />
	</head>
	<body>
		<div class="cart-item-container">
			<!-- Product Image -->
			<div class="cart-item">
				<img src="${item.imageUrl}" alt="Product Image" class="product-image" />
			</div>

			<!-- Product Details -->
			<div class="cart-item">
				<div class="product-name">
					<h4>${item.productName}</h4>
				</div>
				<div class="product-id">
					<p>Product ID: ${item.id}</p>
				</div>
				<div class="product-price">
					<p>Price: $${item.price}</p>
				</div>
			</div>

			<!-- Quantity -->
			<div class="cart-item">
				<label for="quantity-${item.id}"
					>Qty :   <input
						type="number"
						id="quantity-${item.id}"
						name="quantity"
						value="${item.quantity}"
						min="1"
						class="quantity-input"
				/></label>
			</div>

			<!-- Remove Icon -->
			<div class="cart-item">
				<button class="remove-icon" title="Remove Item" data-id="${item.id}">
					&#128465;
				</button>
			</div>
		</div>
	</body>
</html>
